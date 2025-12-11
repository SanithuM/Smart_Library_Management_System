package slms.command;

import slms.model.K2559097_Book;
import slms.model.K2559097_User;

public class K2559097_BorrowCommand implements K2559097_Command {
    private K2559097_Book book;
    private K2559097_User user;

    public K2559097_BorrowCommand(K2559097_Book book, K2559097_User user) {
        this.book = book;
        this.user = user;
    }

    @Override
    public void execute() {
        System.out.println("[Command Log] " + user.getName() + " is attempting to borrow: " + book.getTitle());
        book.borrowBook(user);
    }

    @Override
    public void undo() {
        // Undo borrow by returning the book (best-effort)
        System.out.println("[Command Undo] " + user.getName() + " undoing borrow: " + book.getTitle());
        book.returnBook(user);
    }
}
