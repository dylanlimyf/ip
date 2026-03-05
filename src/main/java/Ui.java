import java.util.Scanner;

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

    public Ui() {
        in = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.print(LINE + LOGO + LINE + GREETING + DO_REQUEST + LINE);
    }

    public String readCommand() {
        if (!in.hasNextLine()) {
            return null;
        }
        return in.nextLine();
    }

    public void showGoodbye() {
        System.out.print(GOODBYE);
    }

    public void showLoadingSkipped(int skipped) {
        String suffix = (skipped == 1) ? "" : "s";
        System.out.print("skipped " + skipped + " corrupted saved task" + suffix + "\n" + LINE);
    }

    public void showLoadingTruncated() {
        System.out.print("task list full; some saved tasks were not loaded\n" + LINE);
    }

    public void showLoadingError(String details) {
        if (details == null || details.isBlank()) {
            System.out.print("could not load saved tasks, starting fresh\n" + LINE);
        } else {
            System.out.print("could not load saved tasks (" + details + "), starting fresh\n" + LINE);
        }
    }

    public void showError(String message) {
        System.out.print(message + "\n" + LINE);
    }

    public void showSaveError(String details) {
        if (details == null || details.isBlank()) {
            System.out.print("could not save tasks\n" + LINE);
        } else {
            System.out.print("could not save tasks: " + details + "\n" + LINE);
        }
    }

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

    public void showAdded(Task task, int taskCount) {
        System.out.print("added: \n" + "  " + task.toPrintStatus() + "\n"
                + "now you need to settle " + taskCount + " tasks\n" + LINE);
    }

    public void showDeleted(Task task, int taskCount) {
        System.out.println("aight bet, task removed you lazy ahh\n" + task.toPrintStatus()
                + "\nnumber of tasks left: " + taskCount + "\n" + LINE);
    }

    public void showMarked(Task task) {
        System.out.print("shiok, ok this task settled\n" + "  " + task.toPrintStatus() + "\n" + LINE);
    }

    public void showUnmarked(Task task) {
        System.out.print("so like did u do it already or not, make up ur mind\n"
                + "  " + task.toPrintStatus() + "\n" + LINE);
    }
}
