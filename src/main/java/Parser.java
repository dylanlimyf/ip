import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static Command parse(String input) throws DukeException {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new DukeException("unknown command\ntry list, todo, deadline, event to make events\n"
                    + "list to show all tasks \nbye if you hate me");
        }

        String[] parts = trimmed.split("\\s+", 2);
        String cmd = parts[0];
        String rest = (parts.length > 1) ? parts[1].trim() : "";

        return switch (cmd) {
        case "bye" -> new ExitCommand();
        case "list" -> new ListCommand();
        case "mark" -> new MarkCommand(parseIndex(trimmed,
                "bro put a task number: mark 1 / unmark 1"), true);
        case "unmark" -> new MarkCommand(parseIndex(trimmed,
                "bro put a task number: mark 1 / unmark 1"), false);
        case "delete" -> new DeleteCommand(parseIndex(trimmed,
                "bro so which number task do you want me to delete?\ntry delete <task number>"));
        case "todo" -> parseTodo(rest);
        case "deadline" -> parseDeadline(rest);
        case "event" -> parseEvent(rest);
        default -> throw new DukeException("unknown command\ntry list, todo, deadline, event to make events\n"
                + "list to show all tasks \nbye if you hate me");
        };
    }

    private static Command parseTodo(String rest) throws DukeException {
        if (rest.isBlank()) {
            throw new DukeException("todo but nothing to do? give me something");
        }
        return new AddCommand(new ToDo(rest));
    }

    private static Command parseDeadline(String rest) throws DukeException {
        if (rest.isBlank()) {
            throw new DukeException("deadline but no task... give me one");
        }
        if (!rest.contains("/by")) {
            throw new DukeException("deadline but no date... give me one by setting /by <deadline>");
        }

        String[] parts = rest.split("\\s+/by\\s+", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new DukeException("deadline but no date... give me one by setting /by <deadline>");
        }

        String description = parts[0].trim();
        String byRaw = parts[1].trim();

        try {
            LocalDate by = LocalDate.parse(byRaw);
            if (by.isBefore(LocalDate.now())) {
                throw new DukeException("siao ah, what kind of task deadline is before today");
            }
            return new AddCommand(new Deadlines(description, by));
        } catch (DateTimeParseException e) {
            throw new DukeException("deadline date must be yyyy-mm-dd (e.g. 2019-10-15)");
        }
    }

    private static Command parseEvent(String rest) throws DukeException {
        if (rest.isBlank()) {
            throw new DukeException("event but nothing to describe? give me something");
        }
        if (rest.contains("/from") && rest.contains("/to")) {
            rest = rest.replace("/from", " (from:")
                    .replace("/to", " to:") + ")";
        } else {
            throw new DukeException("bro like an event has a start time and end time, yours is ahh."
                    + " give me the start by using /from and end time using /to");
        }
        return new AddCommand(new Events(rest));
    }

    private static int parseIndex(String input, String missingMessage) throws DukeException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length < 2) {
            throw new DukeException(missingMessage);
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("task number must be a number eh (e.g. 2)");
        }
    }
}
