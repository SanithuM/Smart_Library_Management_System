package slms.model;

import java.time.LocalDate;

public class K2559097_BorrowRecord {
    private String userID;
    private String userName;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double finePaid;

    public K2559097_BorrowRecord(String userID, String userName, LocalDate borrowDate, LocalDate dueDate) {
        this.userID = userID;
        this.userName = userName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.finePaid = 0.0;
    }

    public String getUserID() { return userID; }
    public String getUserName() { return userName; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getFinePaid() { return finePaid; }

    public void setReturnDate(LocalDate d) { this.returnDate = d; }
    public void setFinePaid(double f) { this.finePaid = f; }

    public boolean isReturned() { return returnDate != null; }
}
