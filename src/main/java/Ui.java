import java.util.Scanner;

/**
 * Handles all user-facing input and output.
 */
public class Ui {

    private static final String LINE = "____________________________________________________________\n";
    private static final String LOGO =
            "          _____                    _____            _____                    _____                    _____                    _____                                            _____          \n" +
                    "         /\\    \\                  /\\    \\          /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                 ______                   /\\    \\         \n" +
                    "        /::\\    \\                /::\\____\\        /::\\    \\                /::\\____\\                /::\\    \\                /::\\    \\               |::|   |                 /::\\    \\        \n" +
                    "       /::::\\    \\              /:::/    /       /::::\\    \\              /:::/    /               /::::\\    \\              /::::\\    \\              |::|   |                /::::\\    \\       \n" +
                    "      /::::::\\    \\            /:::/    /       /::::::\\    \\            /:::/    /               /::::::\\    \\            /::::::\\    \\             |::|   |               /::::::\\    \\      \n" +
            "     /:::/\\:::\\    \\          /:::/    /       /:::/\\:::\\    \\          /:::/    /               /:::/\\:::\\    \\          /:::/\\:::\\    \\            |::|   |              /:::/\\:::\\    \\     \n" +
            "    /:::/__\\:::\\    \\        /:::/    /       /:::/__\\:::\\    \\        /:::/____/               /:::/__\\:::\\    \\        /:::/__\\:::\\    \\           |::|   |             /:::/__\\:::\\    \\    \n" +
            "    \\:::\\   \\:::\\    \\      /:::/    /       /::::\\   \\:::\\    \\       |::|    |               /::::\\   \\:::\\    \\      /::::\\   \\:::\\    \\          |::|   |            /::::\\   \\:::\\    \\    \n" +
            "  ___\\:::\\   \\:::\\    \\    /:::/    /       /::::::\\   \\:::\\    \\      |::|    |     _____    /::::::\\   \\:::\\    \\    /::::::\\   \\:::\\    \\         |::|   |           /::::::\\   \\:::\\    \\   \n" +
            " /\\   \\:::\\   \\:::\\    \\  /:::/    /       /:::/\\:::\\   \\:::\\    \\     |::|    |    /\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\  ______|::|___|___ ____  /:::/\\:::\\   \\:::\\    \\  \n" +
            "/::\\   \\:::\\   \\:::\\____\\/:::/____/       /:::/  \\:::\\   \\:::\\____\\    |::|    |   /::\\____\\/:::/__\\:::\\   \\:::\\____\\/:::/__\\:::\\   \\:::\\____\\|:::::::::::::::::|    |/:::/__\\:::\\   \\:::\\____\\ \n" +
            "\\:::\\   \\:::\\   \\::/    /\\:::\\    \\       \\::/    \\:::\\  /:::/    /    |::|    |  /:::/    /\\:::\\   \\:::\\   \\::/    /\\:::\\   \\:::\\   \\::/    /|:::::::::::::::::|____|\\:::\\   \\:::\\   \\::/    / \n" +
            " \\:::\\   \\:::\\   \\/____/  \\:::\\    \\       \\/____/ \\:::\\/:::/    /     |::|    | /:::/    /  \\:::\\   \\:::\\   \\/____/  \\:::\\   \\:::\\   \\/____/  ~~~~~~|::|~~~|~~~       \\:::\\   \\:::\\   \\/____/  \n" +
            "  \\:::\\   \\:::\\    \\       \\:::\\    \\               \\::::::/    /      |::|____|/:::/    /    \\:::\\   \\:::\\    \\       \\:::\\   \\:::\\    \\            |::|   |           \\:::\\   \\:::\\    \\      \n" +
            "   \\:::\\   \\:::\\____\\       \\:::\\    \\               \\::::/    /       |:::::::::::/    /      \\:::\\   \\:::\\____\\       \\:::\\   \\:::\\____\\           |::|   |            \\:::\\   \\:::\\____\\     \n" +
            "    \\:::\\  /:::/    /        \\:::\\    \\              /:::/    /        \\::::::::::/____/        \\:::\\   \\::/    /        \\:::\\   \\::/    /           |::|   |             \\:::\\   \\::/    /       \n" +
            "     \\:::\\/:::/    /          \\:::\\    \\            /:::/    /          ~~~~~~~~~~               \\:::\\   \\/____/          \\:::\\   \\/____/            |::|   |              \\:::\\   \\/____/      \n" +
            "      \\::::::/    /            \\:::\\    \\          /:::/    /                                     \\:::\\    \\               \\:::\\    \\                |::|   |               \\:::\\    \\          \n" +
            "       \\::::/    /              \\:::\\____\\        /:::/    /                                       \\:::\\____\\               \\:::\\____\\               |::|   |                \\:::\\____\\         \n" +
            "        \\::/    /                \\::/    /        \\::/    /                                         \\::/    /                \\::/    /               |::|___|                 \\::/    /         \n" +
            "         \\/____/                  \\/____/          \\/____/                                           \\/____/                  \\/____/                 ~~                       \\/____/          \n";
    private static final String GREETING = "morning boss\n";
    private static final String DO_REQUEST = "ok boss, what do you want to settle tdy\n";
    private static final String GOODBYE = "ok pangkang, byebye\n";

    private final Scanner in;

    /**
     * Creates a UI backed by standard input.
     */
    public Ui() {
        in = new Scanner(System.in);
    }

    /**
     * Displays the welcome message and logo.
     */
    public void showWelcome() {
        System.out.print(LINE + LOGO + LINE + GREETING + DO_REQUEST + LINE);
    }

    /**
     * Reads the next command line from the user.
     *
     * @return the raw input line, or null if input is closed
     */
    public String readCommand() {
        if (!in.hasNextLine()) {
            return null;
        }
        return in.nextLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.print(GOODBYE);
    }

    /**
     * Informs the user about skipped corrupted tasks during load.
     *
     * @param skipped number of skipped entries
     */
    public void showLoadingSkipped(int skipped) {
        String suffix = (skipped == 1) ? "" : "s";
        System.out.print("skipped " + skipped + " corrupted saved task" + suffix + "\n" + LINE);
    }

    /**
     * Informs the user that some tasks were truncated during load.
     */
    public void showLoadingTruncated() {
        System.out.print("task list full; some saved tasks were not loaded\n" + LINE);
    }

    /**
     * Reports a load error.
     *
     * @param details error details (may be blank)
     */
    public void showLoadingError(String details) {
        if (details == null || details.isBlank()) {
            System.out.print("could not load saved tasks, starting fresh\n" + LINE);
        } else {
            System.out.print("could not load saved tasks (" + details + "), starting fresh\n" + LINE);
        }
    }

    /**
     * Displays an error message.
     *
     * @param message message to display
     */
    public void showError(String message) {
        System.out.print(message + "\n" + LINE);
    }

    /**
     * Reports a save error.
     *
     * @param details error details (may be blank)
     */
    public void showSaveError(String details) {
        if (details == null || details.isBlank()) {
            System.out.print("could not save tasks\n" + LINE);
        } else {
            System.out.print("could not save tasks: " + details + "\n" + LINE);
        }
    }

    /**
     * Displays the current task list.
     *
     * @param tasks task list to show
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.print("no tasks yet\n" + LINE);
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.print((i + 1) + ". " + tasks.get(i).toPrintStatus() + "\n");
        }
        System.out.print(LINE);
    }

    /**
     * Displays find results.
     *
     * @param tasks task list to search
     * @param keyword search term
     */
    public void showFindResults(TaskList tasks, String keyword) {
        StringBuilder output = new StringBuilder();
        int matchCount = tasks.find(keyword, (index, task) -> output.append(index)
                .append(". ")
                .append(task.toPrintStatus())
                .append("\n"));
        if (matchCount == 0) {
            System.out.print("dont have such task...\n" + LINE);
            return;
        }
        System.out.print("Here are the matching tasks in your list:\n" + output + LINE);
    }

    /**
     * Shows a confirmation for adding a task.
     *
     * @param task added task
     * @param taskCount new task count
     */
    public void showAdded(Task task, int taskCount) {
        System.out.print("added: \n" + "  " + task.toPrintStatus() + "\n"
                + "now you need to settle " + taskCount + " tasks\n" + LINE);
    }

    /**
     * Shows a confirmation for deleting a task.
     *
     * @param task removed task
     * @param taskCount new task count
     */
    public void showDeleted(Task task, int taskCount) {
        System.out.println("aight bet, task removed you lazy ahh\n" + task.toPrintStatus()
                + "\nnumber of tasks left: " + taskCount + "\n" + LINE);
    }

    /**
     * Shows a confirmation for marking a task.
     *
     * @param task marked task
     */
    public void showMarked(Task task) {
        System.out.print("shiok, ok this task settled\n" + "  " + task.toPrintStatus() + "\n" + LINE);
    }

    /**
     * Shows a confirmation for unmarking a task.
     *
     * @param task unmarked task
     */
    public void showUnmarked(Task task) {
        System.out.print("so like did u do it already or not, make up ur mind\n"
                + "  " + task.toPrintStatus() + "\n" + LINE);
    }
}
