/**
 * Marks or unmarks a task.
 */
public class MarkCommand extends Command {

    private final int index;
    private final boolean markDone;

    /**
     * Creates a mark/unmark command.
     *
     * @param index zero-based index
     * @param markDone true to mark, false to unmark
     */
    public MarkCommand(int index, boolean markDone) {
        this.index = index;
        this.markDone = markDone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (tasks.size() == 0) {
            throw new DukeException("no tasks to mark or unmark yet");
        }

        if (markDone) {
            tasks.mark(index);
            ui.showMarked(tasks.get(index));
        } else {
            tasks.unmark(index);
            ui.showUnmarked(tasks.get(index));
        }

        save(tasks, storage);
    }
}
