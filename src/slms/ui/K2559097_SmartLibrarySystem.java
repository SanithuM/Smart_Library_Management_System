package slms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import slms.model.*;
import slms.builder.K2559097_BookBuilder;
import slms.decorator.K2559097_FeaturedBook;
import slms.command.K2559097_Command;
import slms.command.K2559097_LibraryInvoker;

public class K2559097_SmartLibrarySystem {

    // --- Simple In-Memory Database ---
    private static List<K2559097_Book> libraryBooks = new ArrayList<>();
    private static List<K2559097_User> users = new ArrayList<>();
    private static K2559097_LibraryInvoker invoker = new K2559097_LibraryInvoker();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        preloadData();

        System.out.println("==========================================");
        System.out.println("   SMART LIBRARY SYSTEM                   ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Login as Librarian (Manage Books)");
            System.out.println("2. Login as Member (Borrow/Return)");
            System.out.println("3. View Command History");
            System.out.println("4. Undo Last Command");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    librarianMenu();
                    break;
                case "2":
                    memberMenu();
                    break;
                case "3":
                    invoker.showHistory();
                    break;
                case "4":
                    // Undo last executed command (best-effort)
                    invoker.undoLast();
                    break;
                case "5":
                    System.out.println("Exiting system...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // --- SUB-MENU: LIBRARIAN (Builder Pattern & Decorator Pattern) ---
    private static void librarianMenu() {
        while (true) {
            System.out.println("\n--- LIBRARIAN PANEL ---");
            System.out.println("1. Add New Book (Builder Pattern)");
            System.out.println("2. List All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Remove Book");
            System.out.println("5. Reports");
            System.out.println("6. Register New User");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select action: ");
            String choice = scanner.nextLine();

            if (choice.equals("0")) break;

            if (choice.equals("1")) {
                System.out.print("Enter Book ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                System.out.print("Enter Author (Optional - press enter to skip): ");
                String author = scanner.nextLine();
                System.out.print("Enter Category (Optional - press enter to skip): ");
                String category = scanner.nextLine();

                // USING BUILDER PATTERN
                K2559097_BookBuilder builder = new K2559097_BookBuilder(id, title, isbn);
                if (!author.isEmpty()) builder.setAuthor(author);
                if (!category.isEmpty()) builder.setCategory(category);

                K2559097_Book newBook = builder.build();

                // Ask for Decorator
                System.out.print("Is this a 'Featured' book? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    K2559097_BookComponent featured = new K2559097_FeaturedBook(newBook);
                    System.out.println("Marked as: " + featured.getDescription());
                }

                libraryBooks.add(newBook);
                System.out.println("Success: Book added to library.");

            } else if (choice.equals("2")) {
                System.out.println("\n--- Library Collection ---");
                for (K2559097_Book b : libraryBooks) {
                    String status = (b.getState() instanceof slms.state.K2559097_AvailableState) ? "[Available]" : "[Unavailable]";
                    System.out.println(status + " " + b.getDescription());
                }
            } else if (choice.equals("3")) {
                System.out.print("Enter Book ID to update: ");
                String id = scanner.nextLine();
                K2559097_Book book = findBookByID(id);
                if (book == null) { System.out.println("Book not found."); continue; }
                System.out.print("New Title (leave empty to keep): ");
                String t = scanner.nextLine(); if (!t.isEmpty()) book.setTitle(t);
                System.out.print("New Author (leave empty to keep): ");
                String a = scanner.nextLine(); if (!a.isEmpty()) book.setAuthor(a);
                System.out.print("New ISBN (leave empty to keep): ");
                String i = scanner.nextLine(); if (!i.isEmpty()) book.setIsbn(i);
                System.out.print("New Category (leave empty to keep): ");
                String c = scanner.nextLine(); if (!c.isEmpty()) book.setCategory(c);
                System.out.println("Book updated.");

            } else if (choice.equals("4")) {
                System.out.print("Enter Book ID to remove: ");
                String id = scanner.nextLine();
                K2559097_Book book = findBookByID(id);
                if (book == null) { System.out.println("Book not found."); continue; }
                libraryBooks.remove(book);
                System.out.println("Book removed from library.");

            } else if (choice.equals("5")) {
                showReports();

            } else if (choice.equals("6")) {
                // Register new user (Librarian action)
                System.out.print("Enter new User ID (e.g., S02): ");
                String uid = scanner.nextLine();
                System.out.print("Enter Name: ");
                String uname = scanner.nextLine();
                System.out.print("Enter Email (optional): ");
                String uemail = scanner.nextLine();
                System.out.print("Enter Contact Number (optional): ");
                String ucontact = scanner.nextLine();
                System.out.println("Select Membership Type: 1. Student 2. Faculty 3. Guest");
                String t = scanner.nextLine();

                K2559097_User newUser = null;
                if (t.equals("1")) newUser = new slms.model.K2559097_Student(uid, uname);

                else if (t.equals("2")) newUser = new slms.model.K2559097_Faculty(uid, uname);
                else newUser = new slms.model.K2559097_Guest(uid, uname);
                if (newUser != null) {
                    if (!uemail.isEmpty()) newUser.setEmail(uemail);
                    if (!ucontact.isEmpty()) newUser.setContactNumber(ucontact);
                    users.add(newUser);
                    System.out.println("User registered: " + newUser.getName() + " (" + newUser.getMembershipType() + ")");
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // --- SUB-MENU: MEMBER (Command Pattern & State Pattern) ---
    private static void memberMenu() {
        // 1. Identify User
        System.out.print("\nEnter your User ID (e.g., S01, F01): ");
        String userId = scanner.nextLine();
        K2559097_User currentUser = findUser(userId);

        if (currentUser == null) {
            System.out.println("User not found! Please ask a librarian to register you.");
            return;
        }

        // Notify user about due/overdue items on login
        notifyDueAndOverdueOnLogin(currentUser);

        while (true) {
            System.out.println("Welcome, " + currentUser.getName());
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Reserve Book");
            System.out.println("4. Check My Fines (Strategy Pattern)");
            System.out.println("5. View My Borrowed Books / Due Dates");
            System.out.println("6. View All Books in Library");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select action: ");
            String action = scanner.nextLine();

            if (action.equals("0")) break;

            if (action.equals("4")) {
                System.out.print("Enter days late: ");
                int days = Integer.parseInt(scanner.nextLine());
                double fine = currentUser.calculateFine(days);
                System.out.println("Your calculated fine is: " + fine + " LKR");
                continue;
            } else if (action.equals("5")) {
                showUserBorrowedBooks(currentUser);
                continue;
            } else if (action.equals("6")) {
                // Display all books for the member
                System.out.println("\n--- Library Collection ---");
                for (K2559097_Book b : libraryBooks) {
                    String status;
                    if (b.getState() instanceof slms.state.K2559097_AvailableState) status = "Available";
                    else if (b.getState() instanceof slms.state.K2559097_BorrowedState) status = "Borrowed";
                    else if (b.getState() instanceof slms.state.K2559097_ReservedState) status = "Reserved";
                    else status = "Unknown";
                    System.out.println("ID: " + b.getBookID() + " | " + b.getTitle() + " | Author: " + (b.getAuthor() == null ? "Unknown" : b.getAuthor()) + " | Status: " + status);
                }
                continue;
            }

            System.out.print("Enter Book Title (exact match): ");
            String bookTitle = scanner.nextLine();
            K2559097_Book targetBook = findBook(bookTitle);

            if (targetBook == null) {
                System.out.println("Book not found.");
                continue;
            }

            // 3. Execute Command
            K2559097_Command command = null;
            switch (action) {
                case "1": // Borrow
                    command = new slms.command.K2559097_BorrowCommand(targetBook, currentUser);
                    break;
                case "2": // Return
                    command = new slms.command.K2559097_ReturnCommand(targetBook, currentUser);
                    break;
                case "3": // Reserve
                    command = new slms.command.K2559097_ReserveCommand(targetBook, currentUser);
                    break;
                default:
                    System.out.println("Invalid action.");
            }

            if (command != null) {
                invoker.executeCommand(command);
            }
        }
    }

    private static void notifyDueAndOverdueOnLogin(K2559097_User user) {
        java.time.LocalDate today = java.time.LocalDate.now();
        int reminderDays = 3; // Notify if due within 3 days
        for (K2559097_BorrowRecord r : user.getBorrowHistory()) {
            if (!r.isReturned()) {
                long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, r.getDueDate());
                if (daysLeft < 0) {
                    user.update("Overdue: '" + r.getUserName() + "' had a book due on " + r.getDueDate() + " (" + Math.abs(daysLeft) + " days overdue)");
                } else if (daysLeft <= reminderDays) {
                    user.update("Reminder: you have a book due on " + r.getDueDate() + " (" + daysLeft + " days left)");
                }
            }
        }
    }

    // --- HELPERS ---
    private static void preloadData() {
        // Create Default Users
        K2559097_User s = new slms.model.K2559097_Student("S01", "John (Student)");
        s.setEmail("john.student@example.com");
        s.setContactNumber("+94-77-1234567");
        users.add(s);

        K2559097_User f = new slms.model.K2559097_Faculty("F01", "Dr. Smith (Faculty)");
        f.setEmail("dr.smith@example.com");
        f.setContactNumber("+94-77-7654321");
        users.add(f);

        // Create Default Books
        K2559097_Book b1 = new K2559097_BookBuilder("B01", "Java", "123").setAuthor("Gosling").build();
        K2559097_Book b2 = new K2559097_BookBuilder("B02", "Patterns", "456").setAuthor("GangOf4").build();

        libraryBooks.add(b1);
        libraryBooks.add(b2);
    }

    private static K2559097_User findUser(String id) {
        for (K2559097_User u : users) {
            if (u.getUserID().equalsIgnoreCase(id)) return u;
        }
        // Fallback to defaults if known
        if (id.equalsIgnoreCase("S01") && users.size() > 0) return users.get(0);
        if (id.equalsIgnoreCase("F01") && users.size() > 1) return users.get(1);
        return null;
    }

    private static K2559097_Book findBook(String title) {
        for (K2559097_Book b : libraryBooks) {
            if (b.getTitle().equalsIgnoreCase(title)) return b;
        }
        return null;
    }

    private static K2559097_Book findBookByID(String id) {
        for (K2559097_Book b : libraryBooks) {
            if (b.getBookID().equalsIgnoreCase(id)) return b;
        }
        return null;
    }

    // --- Reports ---
    private static void showReports() {
        System.out.println("\n--- LIBRARIAN REPORTS ---");
        System.out.println("1. Most Borrowed Books");
        System.out.println("2. Active Borrowers");
        System.out.println("3. Overdue Books");
        System.out.print("Select report: ");
        String r = scanner.nextLine();
        switch (r) {
            case "1":
                mostBorrowedBooks();
                break;
            case "2":
                activeBorrowers();
                break;
            case "3":
                overdueBooks();
                break;
            default:
                System.out.println("Invalid report.");
        }
    }

    private static void mostBorrowedBooks() {
        System.out.println("\nTop borrowed books:");
        libraryBooks.stream()
                .sorted((a,b) -> Integer.compare(b.getBorrowCount(), a.getBorrowCount()))
                .limit(10)
                .forEach(b -> System.out.println(b.getBorrowCount() + " borrows - " + b.getDescription() + " (ID: " + b.getBookID() + ")"));
    }

    private static void activeBorrowers() {
        System.out.println("\nActive borrowers (by number of borrows):");
        users.stream()
                .sorted((a,b) -> Integer.compare(b.getCurrentBorrowedCount(), a.getCurrentBorrowedCount()))
                .forEach(u -> System.out.println(u.getCurrentBorrowedCount() + " current borrows - " + u.getName() + " (" + u.getMembershipType() + ")"));
    }

    private static void overdueBooks() {
        System.out.println("\nOverdue books:");
        java.time.LocalDate today = java.time.LocalDate.now();
        for (K2559097_Book b : libraryBooks) {
            for (K2559097_BorrowRecord r : b.getBorrowHistory()) {
                if (!r.isReturned() && r.getDueDate().isBefore(today)) {
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(r.getDueDate(), today);
                    System.out.println("Book: " + b.getDescription() + " (ID: " + b.getBookID() + ") - Borrowed by: " + r.getUserName() + " (Due: " + r.getDueDate() + ") - " + daysOverdue + " days overdue");
                }
            }
        }
    }

    private static void showUserBorrowedBooks(K2559097_User user) {
        System.out.println("\n--- Your Current Borrowed Books ---");
        java.time.LocalDate today = java.time.LocalDate.now();
        boolean any = false;
        for (K2559097_BorrowRecord r : user.getBorrowHistory()) {
            if (!r.isReturned()) {
                any = true;
                long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, r.getDueDate());
                String status = (daysLeft < 0) ? (Math.abs(daysLeft) + " days overdue") : (daysLeft + " days remaining");
                System.out.println("- " + r.getUserName() + " borrowed on " + r.getBorrowDate() + ": Due " + r.getDueDate() + " (" + status + ")");
            }
        }
        if (!any) System.out.println("You have no currently borrowed books.");
    }
}
