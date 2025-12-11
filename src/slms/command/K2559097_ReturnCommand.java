package slms.command;

import slms.model.K2559097_Book;
import slms.model.K2559097_User;

public class K2559097_ReturnCommand implements K2559097_Command {
    private K2559097_Book book;
    private K2559097_User user;

    public K2559097_ReturnCommand(K2559097_Book book, K2559097_User user) {
        this.book = book;
        this.user = user;
    }

    @Override
    public void execute() {
        System.out.println("[Command Log] " + user.getName() + " is returning: " + book.getTitle());
        book.returnBook(user);
    }

    @Override
    public void undo() {
        // Undo return by attempting to re-borrow (best-effort)
        System.out.println("[Command Undo] " + user.getName() + " undoing return: " + book.getTitle());
        book.borrowBook(user);
    }
}
