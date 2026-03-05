/**
 * Adds a task to the list.
 */
public class AddCommand extends Command {

    private final Task task;

    /**
     * Creates an add command for the given task.
     *
     * @param task task to add
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        tasks.add(task);
        ui.showAdded(task, tasks.size());
        save(tasks, storage, ui);
    }
}
