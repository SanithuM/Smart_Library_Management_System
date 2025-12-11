package slms.ui;

import slms.model.*;
import slms.builder.K2559097_BookBuilder;
import slms.state.K2559097_BorrowedState;

public class K2559097_ReservationTest {
    public static void main(String[] args) {
        // Setup users and book
        K2559097_User borrower = new K2559097_Student("S10", "Borrower");
        K2559097_User reserver = new K2559097_Student("S11", "Reserver");

        K2559097_Book book = new K2559097_BookBuilder("B10", "Clean Code", "111").setAuthor("Martin").build();

        // Borrow the book (simulate)
        book.setState(new K2559097_BorrowedState());
        book.completeBorrow(borrower); // this will attach borrower as observer

        // Reserver places a reservation while book is borrowed
        book.reserveBook(reserver);

        System.out.println("Reservation queue size (should be 1): " + book.getReservationQueue().size());

        // Now borrower returns the book — system should notify reserver
        book.returnBook(borrower);

        // After return, reservation queue should be empty and reserver should have been attached
        System.out.println("Reservation queue size (after return, should be 0): " + book.getReservationQueue().size());
    }
}
