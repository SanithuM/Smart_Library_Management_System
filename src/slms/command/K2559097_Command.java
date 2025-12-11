package slms.command;

public interface K2559097_Command {
    void execute();
    // Attempt to undo the command. Implementations should make a best-effort reversal.
    void undo();
}
