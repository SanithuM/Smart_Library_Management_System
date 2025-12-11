package slms.state;

import slms.model.K2559097_Book;
import slms.model.K2559097_User;

public class K2559097_BorrowedState implements K2559097_BookState {
    @Override
    public void handleBorrow(K2559097_Book book, K2559097_User user) {
        System.out.println("Error: The book '" + book.getTitle() + "' is currently borrowed by someone else.");
    }

    @Override
    public void handleReturn(K2559097_Book book, K2559097_User user) {
        // Complete return processing
        book.completeReturn(user);
    }

    @Override
    public void handleReserve(K2559097_Book book, K2559097_User user) {
        book.completeReserve(user);
    }
}
