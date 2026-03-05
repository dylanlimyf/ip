/**
 * Represents user-correctable errors in command processing.
 */
public class DukeException extends Exception {
    /**
     * Creates a DukeException with a message.
     *
     * @param message error message
     */
    public DukeException(String message) {
        super(message);
    }
}
