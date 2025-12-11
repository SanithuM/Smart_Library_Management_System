package slms.model;

import slms.strategy.K2559097_FineStrategy;

public abstract class K2559097_User implements K2559097_Observer {
    protected String userID;
    protected String name;
    protected String email;
    protected String contactNumber;
    protected K2559097_FineStrategy fineStrategy;
    protected java.util.List<K2559097_BorrowRecord> borrowHistory = new java.util.ArrayList<>();

    public K2559097_User(String userID, String name, K2559097_FineStrategy fineStrategy) {
        this.userID = userID;
        this.name = name;
        this.fineStrategy = fineStrategy;
    }

    // The Strategy Pattern in action: The User delegates calculation to the strategy object
    public double calculateFine(int daysLate) {
        return fineStrategy.calculateFine(daysLate);
    }

    // Observer Pattern: This method is called when the Book notifies observers
    @Override
    public void update(String message) {
        System.out.println("Notification for user " + name + ": " + message);
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    // Contact info accessors
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    // Borrow history management
    public void addBorrowRecord(K2559097_BorrowRecord r) { borrowHistory.add(r); }
    public java.util.List<K2559097_BorrowRecord> getBorrowHistory() { return borrowHistory; }
    public int getCurrentBorrowedCount() {
        int c = 0;
        for (K2559097_BorrowRecord r : borrowHistory) if (!r.isReturned()) c++;
        return c;
    }

    // Membership-specific policies
    public abstract int getLoanPeriodDays();
    public abstract int getBorrowLimit();
    // Return a human-readable membership type
    public abstract String getMembershipType();
}
