public class Main {

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    public Main(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
    }

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

    public static void main(String[] args) {
        new Main("data/list.txt").run();
    }
}
