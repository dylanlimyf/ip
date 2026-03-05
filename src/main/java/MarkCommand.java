public class MarkCommand extends Command {

    private final int index;
    private final boolean markDone;

    public MarkCommand(int index, boolean markDone) {
        this.index = index;
        this.markDone = markDone;
    }

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

        save(tasks, storage, ui);
    }
}
