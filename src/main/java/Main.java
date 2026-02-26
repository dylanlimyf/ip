import java.util.Scanner;

public class Main {

    private static final String LINE = "____________________________________________________________\n";
    private static final String GREETING = "morning boss\n";
    private static final String DO_REQUEST = "ok boss, what do you want to settle tdy\n";
    private static final String GOODBYE = "ok pangkang, byebye\n";

    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;
    private static int toSettleCount = 0;
    private static final Storage storage = new Storage("data/list.txt");


    public static void main(String[] args) {
        printWelcome();

        try {
            Storage.ReadResult readResult = storage.readFile(tasks);
            taskCount = readResult.getCount();

            if (readResult.getSkipped() > 0) {
                String suffix = (readResult.getSkipped() == 1) ? "" : "s";
                System.out.print("skipped " + readResult.getSkipped()
                        + " corrupted saved task" + suffix + "\n" + LINE);
            }
            if (readResult.isTruncated()) {
                System.out.print("task list full; some saved tasks were not loaded\n" + LINE);
            }
        } catch (Exception e) {
            String details = e.getMessage();
            if (details == null || details.isBlank()) {
                System.out.print("could not load saved tasks, starting fresh\n" + LINE);
            } else {
                System.out.print("could not load saved tasks (" + details + "), starting fresh\n" + LINE);
            }
            taskCount = 0;
        }

        Scanner in = new Scanner(System.in);
        while (true) {
            if (!in.hasNextLine()) {
                System.out.print(GOODBYE);
                return;
            }
            String input = in.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            try {
                boolean shouldExit = handleInput(input);
                if (shouldExit) {
                    return;
                }
            } catch (DukeException e) {
                // for user-correctable errors
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
    private static boolean handleInput(String input) throws DukeException {
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
                saveTasks();

                yield false;
            }
            case "todo", "deadline", "event" -> {
                handleAddTask(input);
                saveTasks();

                yield false;
            }
            case "delete" -> {
                deleteTask(input);
                saveTasks();

                yield false;
            }
            default -> {
                throw new DukeException("unknown command\ntry list, todo, deadline, event to make events\n"
                        + "list to show all tasks \nbye if you hate me");
            }
        };
    }

    private static void deleteTask(String input) throws DukeException {
        String[] parts = input.split("\\s+");

        if (taskCount == 0) {
            throw new DukeException("no tasks to delete yet");
        }
        if (parts.length < 2) {
            throw new DukeException("bro so which number task do you want me to delete?\ntry delete <task number>");
        }

        int index = parseTaskIndex(parts[1]);
        Task removed = tasks[index];
        for (int i = index; i < taskCount - 1; i++){
            tasks[i] = tasks[i + 1];
        }

        tasks[taskCount - 1] = null;
        taskCount--;

        System.out.println("aight bet, task removed you lazy ahh\n" + removed.toPrintStatus()
                + "\nnumber of tasks left: " + taskCount + "\n" + LINE);
    }

    private static void handleList() {
        if (taskCount == 0) {
            System.out.print("no tasks yet\n" + LINE);
            return;
        }
        for (int i = 0; i < taskCount; i++) {
            System.out.print((i + 1) + ". " + tasks[i].toPrintStatus() + "\n");
        }
        System.out.print(LINE);
    }


    private static void handleAddTask(String input) throws DukeException {
        if (taskCount >= tasks.length) {
            throw new DukeException("task list full, delete something first");
        }

        String[] parts = input.trim().split("\\s+", 2); // split into: command + rest
        String cmd = parts[0];
        String rest = (parts.length > 1) ? parts[1].trim() : "";

        switch (cmd) {

        case "todo":
            if (rest.isBlank()) {
                throw new DukeException("todo but nothing to do? give me something");
            }
            tasks[taskCount] = new ToDo(rest);
            break;

        case "event":

            if (rest.isBlank()) {
                throw new DukeException("event but nothing to describe? give me something");
            }
            if (rest.contains("/from") && rest.contains("/to")){
                rest = rest.replace("/from", " (from:")
                           .replace("/to", " to:") + ")";
            } else {
                throw new DukeException("bro like an event has a start time and end time, yours is ahh."
                        + " give me the start by using /from and end time using /to");
            }

            tasks[taskCount] = new Events(rest);
            break;

        case "deadline":

            if (rest.isBlank()) {
                throw new DukeException("deadline but no task... give me one");
            }
            if (rest.contains("/by")){
                rest = rest.replace("/by", " by:");
            } else {
                throw new DukeException("deadline but no date... give me one by setting /by <deadline>");
            }

            tasks[taskCount] = new Deadlines(rest);
            break;
        default:
            throw new DukeException("unknown task type: " + cmd);
        }

        System.out.print("added: \n" + "  " + tasks[taskCount].toPrintStatus() + "\n" +
                         "now you need to settle " + (taskCount + 1) + " tasks\n" + LINE);

        // TODO change the taskcount so that it accurately tracks the todocount instead of all the tasks
        taskCount++;
    }

    private static void handleMarkUnmark(String input) throws DukeException {
        String[] parts = input.split("\\s+");

        // must be exactly: mark <number> or unmark <number>
        if (parts.length < 2) {
            throw new DukeException("bro put a task number: mark 1 / unmark 1");
        }
        if (taskCount == 0) {
            throw new DukeException("no tasks to mark or unmark yet");
        }

        int idx = parseTaskIndex(parts[1]);

        boolean markDone = input.startsWith("mark");
        if (markDone) tasks[idx].mark();
        else tasks[idx].unmark();

        if (markDone) {
            System.out.print("shiok, ok this task settled\n");
        } else {
            System.out.print("so like did u do it already or not, make up ur mind\n");
        }

        System.out.print("  " + tasks[idx].toPrintStatus() + "\n" + LINE);
    }

    // converts 1-based input to 0-based index, throws on invalid input
    private static int parseTaskIndex(String oneBasedNumber) throws DukeException {
        try {
            int idx = Integer.parseInt(oneBasedNumber) - 1;
            if (idx < 0 || idx >= taskCount) {
                throw new DukeException("task number must be between 1 and " + taskCount);
            }
            return idx;
        } catch (NumberFormatException e) {
            throw new DukeException("task number must be a number eh (e.g. 2)");
        }
    }

    private static void saveTasks() {
        try {
            storage.writeFile(tasks, taskCount);
        } catch (Exception e) {
            String details = e.getMessage();
            if (details == null || details.isBlank()) {
                System.out.print("could not save tasks\n" + LINE);
            } else {
                System.out.print("could not save tasks: " + details + "\n" + LINE);
            }
        }
    }
}
