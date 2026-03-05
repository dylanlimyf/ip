public class DeleteCommand extends Command {

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (tasks.size() == 0) {
            throw new DukeException("no tasks to delete yet");
        }
        Task removed = tasks.remove(index);
        ui.showDeleted(removed, tasks.size());
        save(tasks, storage, ui);
    }
}
