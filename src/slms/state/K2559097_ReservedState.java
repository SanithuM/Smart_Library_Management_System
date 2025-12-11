package slms.state;

import slms.model.K2559097_Book;
import slms.model.K2559097_User;

public class K2559097_ReservedState implements K2559097_BookState {
    @Override
    public void handleBorrow(K2559097_Book book, K2559097_User user) {
        // If the user is first in reservation queue, allow borrow
        java.util.List<K2559097_User> q = book.getReservationQueue();
        if (!q.isEmpty() && q.get(0).getUserID().equals(user.getUserID())) {
            // remove from queue and allow borrow
            q.remove(0);
            book.completeBorrow(user);
        } else {
            System.out.println("Error: This book is reserved for another user.");
        }
    }

    @Override
    public void handleReturn(K2559097_Book book, K2559097_User user) {
        System.out.println("Error: A reserved book cannot be 'returned' (it is on the hold shelf).");
    }

    @Override
    public void handleReserve(K2559097_Book book, K2559097_User user) {
        System.out.println("Error: This book is already reserved.");
    }
}
