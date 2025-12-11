package slms.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import slms.state.*;

public class K2559097_Book implements K2559097_Subject, K2559097_BookComponent {

    // Core attributes
    private String bookID;
    private String title;
    private String isbn;

    // Optional attributes (managed by Builder)
    private String author;
    private String category;
    private String edition;
    private List<String> tags;

    // State and Observer attributes
    private K2559097_BookState state;
    private List<K2559097_Observer> observers;
    // Borrowing and reservation tracking
    private List<K2559097_BorrowRecord> borrowHistory;
    private List<K2559097_User> reservationQueue;
    private int borrowCount;

    // Constructors
    public K2559097_Book(String bookID, String title, String isbn) {
        this.bookID = bookID;
        this.title = title;
        this.isbn = isbn;
        this.tags = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.borrowHistory = new ArrayList<>();
        this.reservationQueue = new ArrayList<>();
        this.borrowCount = 0;
        this.state = new K2559097_AvailableState(); // Default state
    }

    // Getters
    public String getBookID() { return bookID; }
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getEdition() { return edition; }
    public List<String> getTags() { return tags; }

    // Setters for Builder
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setEdition(String edition) { this.edition = edition; }
    public void setTitle(String title) { this.title = title; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void addTag(String tag) { this.tags.add(tag); }

    // --- Decorator Pattern Implementation ---
    @Override
    public String getDescription() {
        // Base description of the book
        String desc = title + " by " + (author != null ? author : "Unknown Author");
        if (category != null && !category.isEmpty()) desc += " [" + category + "]";
        return desc;
    }

    // --- Existing State & Observer Logic (Same as Step 2) ---
    public void setState(K2559097_BookState state) { this.state = state; }
    public K2559097_BookState getState() { return state; }

    // user-aware operations
    public void borrowBook(K2559097_User user) { state.handleBorrow(this, user); }
    public void returnBook(K2559097_User user) { state.handleReturn(this, user); }
    public void reserveBook(K2559097_User user) { state.handleReserve(this, user); }

    // Internal helpers to complete actions (called by states)
    public void completeBorrow(K2559097_User user) {
        // Enforce borrowing limit
        if (user.getCurrentBorrowedCount() >= user.getBorrowLimit()) {
            System.out.println("Error: User '" + user.getName() + "' has reached borrow limit.");
            return;
        }

        java.time.LocalDate now = LocalDate.now();
        java.time.LocalDate due = now.plusDays(user.getLoanPeriodDays());
        K2559097_BorrowRecord record = new K2559097_BorrowRecord(user.getUserID(), user.getName(), now, due);

        borrowHistory.add(record);
        user.addBorrowRecord(record);
        borrowCount++;
        // Attach borrower as observer so they can receive notifications
        this.attach(user);
        this.setState(new K2559097_BorrowedState());
        System.out.println("Success: You have borrowed the book '" + this.getTitle() + "'. Due: " + due);
    }

    public void completeReturn(K2559097_User user) {
        java.time.LocalDate now = LocalDate.now();
        // Find the matching active borrow record for this user
        K2559097_BorrowRecord active = null;
        for (K2559097_BorrowRecord r : borrowHistory) {
            if (!r.isReturned() && r.getUserID().equals(user.getUserID())) { active = r; break; }
        }
        if (active == null) {
            System.out.println("Error: No active borrow record found for this user on this book.");
            return;
        }

        active.setReturnDate(now);
        long daysLate = java.time.temporal.ChronoUnit.DAYS.between(active.getDueDate(), now);
        if (daysLate > 0) {
            double fine = user.calculateFine((int) daysLate);
            active.setFinePaid(fine);
            System.out.println("Book returned. Late by " + daysLate + " days. Fine: " + fine + " LKR.");
        } else {
            System.out.println("Success: You have returned '" + this.getTitle() + "'.");
        }

        // Detach the returning user from observers (they no longer need notifications for this book)
        this.detach(user);

        // If there's a reservation queue, notify next and set Reserved, otherwise Available
        if (!reservationQueue.isEmpty()) {
            K2559097_User next = reservationQueue.remove(0);
            // Attach only the next reserver and notify them via Subject API (Observer Pattern)
            this.attach(next);
            String message = "The book '" + this.getTitle() + "' is now available for you, " + next.getName() + ".";
            this.notifyObservers(message);
            this.setState(new K2559097_ReservedState());
        } else {
            this.setState(new K2559097_AvailableState());
        }
    }

    public void completeReserve(K2559097_User user) {
        if (!reservationQueue.contains(user)) {
            reservationQueue.add(user);
            System.out.println("Success: You have placed a hold/reservation on '" + this.getTitle() + "'.");
        } else {
            System.out.println("Notice: You already have a reservation for this book.");
        }
    }

    // Accessors for histories/queues
    public List<K2559097_BorrowRecord> getBorrowHistory() { return borrowHistory; }
    public List<K2559097_User> getReservationQueue() { return reservationQueue; }
    public int getBorrowCount() { return borrowCount; }

    @Override
    public void attach(K2559097_Observer o) { if (!observers.contains(o)) observers.add(o); }
    @Override
    public void detach(K2559097_Observer o) { observers.remove(o); }
    @Override
    public void notifyObservers(String msg) { for(K2559097_Observer o : observers) o.update(msg); }
}
