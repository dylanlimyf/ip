public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;

    public boolean isExit() {
        return false;
    }

    protected void save(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.save(tasks);
        } catch (Exception e) {
            ui.showSaveError(e.getMessage());
        }
    }
}
