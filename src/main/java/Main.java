import java.util.Scanner;

public class Main {

    private static final String LINE = "____________________________________________________________\n";
    private static final String GREETING = "morning boss\n";
    private static final String DO_REQUEST = "ok boss, what do you want to settle tdy\n";
    private static final String GOODBYE = "ok pangkang, byebye\n";

    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;
    private static int toSettleCount = 0;


    public static void main(String[] args) {
        printWelcome();

        Scanner in = new Scanner(System.in);
        while (true) {
            String input = in.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            try {
                boolean shouldExit = handleInput(input);
                if (shouldExit) {
                    return;
                }
            } catch (IllegalArgumentException e) {
                // for errors you intentionally throw (later)
                System.out.print(e.getMessage() + "\n" + LINE);
            } catch (Exception e) {
                // for unexpected errors (prevents crashing + stacktrace)
                System.out.print("something went wrong on my end... try again\n" + LINE);
            }
        }
    }

    private static void printWelcome() {
        System.out.print(LINE + GREETING + DO_REQUEST + LINE);
    }

    // returns true if program should exit
    private static boolean handleInput(String input) {
        input = input.trim();
        if (input.isEmpty()) return false;

        String[] parts = input.split("\\s+", 2);
        String cmd = parts[0];

        return switch (cmd) {
            case "bye" -> {
                System.out.print(GOODBYE);
                yield true;
            }
            case "list" -> {
                handleList();
                yield false;
            }
            case "mark", "unmark" -> {
                handleMarkUnmark(input);
                yield false;
            }
            case "todo", "deadline", "event" -> {
                handleAddTask(input);
                yield false;
            }
            default -> {
                System.out.print("unknown command\ntry list, todo, deadline, event to make events\nlist to show all tasks \nbye if you hate me\n" + LINE);
                yield false;
            }
        };
    }


    private static void handleList() {
        for (int i = 0; i < taskCount; i++) {
            System.out.print((i + 1) + ". " + tasks[i].toPrintStatus() + "\n");
        }
        System.out.print(LINE);
    }


    private static void handleAddTask(String input) {
        String[] parts = input.trim().split("\\s+", 2); // split into: command + rest
        String cmd = parts[0];
        String rest = (parts.length > 1) ? parts[1] : "";

        switch (cmd) {

        case "todo":
            if (rest.isBlank()) {
                System.out.print("todo but nothing to do? give me something\n" + LINE);
                return;
            }
            tasks[taskCount] = new ToDo(rest);
            break;

        case "event":

            if (rest.contains("/from") && rest.contains("/to")){
                rest = rest.replace("/from", " (from:")
                           .replace("/to", " to:") + ")";
            } else {
                System.out.print("bro like an event has a start time and end time, yours is ahh." +
                                 " give me the start by using /from and end time using /to \n" + LINE);
                return;
            }

            tasks[taskCount] = new Events(rest);
            break;

        case "deadline":

            if (rest.contains("/by")){
                rest = rest.replace("/by", " by:");
            } else {
                System.out.print("deadline but no date... give me one by setting /by <deadline> \n" + LINE);
                return;
            }

            tasks[taskCount] = new Deadlines(rest);
            break;
        default:
            System.out.print("unknown task type: " + cmd + "\n" + LINE);
            return; // don't increment taskCount
        }

        System.out.print("added: \n" + "  " + tasks[taskCount].toPrintStatus() + "\n" +
                         "now you need to settle " + (taskCount + 1) + " tasks\n" + LINE);

        // TODO change the taskcount so that it accurately tracks the todocount instead of all the tasks
        taskCount++;
    }

    private static void handleMarkUnmark(String input) {
        String[] parts = input.split("\\s+");

        // must be exactly: mark <number> or unmark <number>
        if (parts.length < 2) {
            System.out.print("bro put a task number: mark 1 / unmark 1\n" + LINE);
            return;
        }

        Integer idx = parseTaskIndex(parts[1]);
        if (idx == null) {
            System.out.print("task number must be a number eh (e.g. mark 2)\n" + LINE);
            return;
        }

        if (!isValidIndex(idx)) {
            System.out.print("wtf is this task number, make it make sense\n" + LINE);
            return;
        }

        boolean markDone = input.startsWith("mark");
        tasks[idx].isDone = markDone;

        if (markDone) {
            System.out.print("shiok, ok this task settled\n");
        } else {
            System.out.print("so like did u do it already or not, make up ur mind\n");
        }

        System.out.print("  " + tasks[idx].toPrintStatus() + "\n" + LINE);
    }

    // converts "2" -> 1, returns null if not a number
    private static Integer parseTaskIndex(String oneBasedNumber) {
        try {
            return Integer.parseInt(oneBasedNumber) - 1;
        } catch (NumberFormatException e) {
            return null;
        }
    }


    private static boolean isValidIndex(int idx) {
        return idx >= 0 && idx < taskCount;
    }
}
