import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadlines extends Task {

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate by;

    public Deadlines(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon(){
        return "D";
    }

    @Override
    public String toPrintStatus() {
        return super.toPrintStatus() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toSaveString() {
        return getTypeIcon() + " | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
