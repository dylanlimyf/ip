/**
 * Deletes a task from the list.
 */
public class DeleteCommand extends Command {

    private final int index;

    /**
     * Creates a delete command.
     *
     * @param index zero-based index to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (tasks.size() == 0) {
            throw new DukeException("no tasks to delete yet");
        }
        Task removed = tasks.remove(index);
        ui.showDeleted(removed, tasks.size());
        save(tasks, storage);
    }
}
