import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline date.
 */
public class Deadlines extends Task {

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate by;

    /**
     * Creates a deadline task.
     *
     * @param description task description
     * @param by deadline date
     */
    public Deadlines(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeIcon(){
        return "D";
    }

    /**
     * Returns the printable form including the deadline date.
     */
    @Override
    public String toPrintStatus() {
        return super.toPrintStatus() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the storage format including the deadline date.
     */
    @Override
    public String toSaveString() {
        return getTypeIcon() + " | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
