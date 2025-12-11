package slms.command;

import java.util.ArrayList;
import java.util.List;

public class K2559097_LibraryInvoker {
    // Keeps a record of all commands executed (History)
    private List<K2559097_Command> commandHistory = new ArrayList<>();

    public void executeCommand(K2559097_Command command) {
        command.execute();
        commandHistory.add(command);
    }

    public void showHistory() {
        System.out.println("\n--- System Command History ---");
        System.out.println("Total commands executed: " + commandHistory.size());
    }

    public void undoLast() {
        if (commandHistory.isEmpty()) {
            System.out.println("No commands to undo.");
            return;
        }
        K2559097_Command last = commandHistory.remove(commandHistory.size() - 1);
        try {
            last.undo();
            System.out.println("Last command undone.");
        } catch (Exception e) {
            System.out.println("Failed to undo last command: " + e.getMessage());
        }
    }
}
