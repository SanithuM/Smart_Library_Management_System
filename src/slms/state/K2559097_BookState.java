package slms.state;

import slms.model.K2559097_Book;
import slms.model.K2559097_User;

public interface K2559097_BookState {
    void handleBorrow(K2559097_Book book, K2559097_User user);
    void handleReturn(K2559097_Book book, K2559097_User user);
    void handleReserve(K2559097_Book book, K2559097_User user);
}
