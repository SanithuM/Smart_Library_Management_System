package slms.state;

import slms.model.K2559097_Book;
import slms.model.K2559097_User;

public class K2559097_AvailableState implements K2559097_BookState {
    @Override
    public void handleBorrow(K2559097_Book book, K2559097_User user) {
        // Delegate business logic to Book
        book.completeBorrow(user);
    }

    @Override
    public void handleReturn(K2559097_Book book, K2559097_User user) {
        System.out.println("Error: This book is already in the library. You cannot return it.");
    }

    @Override
    public void handleReserve(K2559097_Book book, K2559097_User user) {
        book.completeReserve(user);
        book.setState(new K2559097_ReservedState());
    }
}
