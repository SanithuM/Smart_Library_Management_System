package slms.ui;

import slms.model.*;
import slms.builder.K2559097_BookBuilder;
import slms.state.K2559097_BorrowedState;

import java.time.LocalDate;

public class K2559097_BorrowReturnTest {
    public static void main(String[] args) {
        // Create a student and a book
        K2559097_User student = new K2559097_Student("S99", "Test Student");
        K2559097_Book book = new K2559097_BookBuilder("B99", "Test Driven Development", "999").setAuthor("Kent Beck").build();

        // Simulate that the book was borrowed 20 days ago and due 6 days ago (overdue by 6 days)
        LocalDate borrowDate = LocalDate.now().minusDays(20);
        LocalDate dueDate = LocalDate.now().minusDays(6);
        K2559097_BorrowRecord record = new K2559097_BorrowRecord(student.getUserID(), student.getName(), borrowDate, dueDate);

        // Put the book into borrowed state and register the active borrow
        book.setState(new K2559097_BorrowedState());
        book.getBorrowHistory().add(record);
        student.addBorrowRecord(record);
        book.attach(student);

        System.out.println("Before return: Borrow record due=" + record.getDueDate() + ", returned=" + record.getReturnDate());

        // Now return the book — this should calculate fines using Student strategy (50 LKR/day)
        book.returnBook(student);

        System.out.println("After return: Borrow record returnDate=" + record.getReturnDate() + ", finePaid=" + record.getFinePaid());
    }
}
