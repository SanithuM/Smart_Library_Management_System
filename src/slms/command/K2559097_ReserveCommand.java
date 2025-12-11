package slms.command;

import slms.model.K2559097_Book;
import slms.model.K2559097_User;

public class K2559097_ReserveCommand implements K2559097_Command {
    private K2559097_Book book;
    private K2559097_User user;

    public K2559097_ReserveCommand(K2559097_Book book, K2559097_User user) {
        this.book = book;
        this.user = user;
    }

    @Override
    public void execute() {
        System.out.println("[Command Log] " + user.getName() + " is reserving: " + book.getTitle());
        book.reserveBook(user);
    }

    @Override
    public void undo() {
        System.out.println("[Command Undo] " + user.getName() + " undoing reserve: " + book.getTitle());
        // Remove from reservation queue if present
        try {
            book.getReservationQueue().removeIf(u -> u.getUserID().equals(user.getUserID()));
            // Detach observer if attached
            book.detach(user);
        } catch (Exception e) {
            // best-effort
        }
    }
}
