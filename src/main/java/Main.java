/**
 * Entry point and coordinator for the task app.
 */
public class Main {

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

// This comment is added to accomplish the requirement for merge-pull-request
    
    /**
     * Creates the app with a storage file path.
     *
     * @param filePath storage file location
     */
    public Main(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
    }

    /**
     * Runs the main input loop.
     */
    public void run() {
        ui.showWelcome();
        loadTasks();

        while (true) {
            String input = ui.readCommand();
            if (input == null) {
                ui.showGoodbye();
                return;
            }

            input = input.trim();
            if (input.isEmpty()) {
                continue;
            }

            try {
                Command command = Parser.parse(input);
                command.execute(tasks, ui, storage);
                if (command.isExit()) {
                    return;
                }
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("something went wrong on my end... try again");
            }
        }
    }

    /**
     * Loads tasks from storage, falling back to an empty list on failure.
     */
    private void loadTasks() {
        try {
            Storage.LoadResult result = storage.load();
            tasks = result.getTasks();

            if (result.getSkipped() > 0) {
                ui.showLoadingSkipped(result.getSkipped());
            }
            if (result.isTruncated()) {
                ui.showLoadingTruncated();
            }
        } catch (Exception e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Program entry point.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        new Main("data/list.txt").run();
    }
}
