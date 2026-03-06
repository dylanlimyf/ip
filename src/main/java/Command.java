/**
 * Represents an executable user command.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks task list
     * @param ui UI for output
     * @param storage storage for persistence
     * @throws DukeException if execution fails with a user-correctable error
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;

    /**
     * Indicates if this command should terminate the app.
     *
     * @return true if the app should exit
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Saves the task list.
     *
     * @throws DukeException if saving fails
     */
    protected void save(TaskList tasks, Storage storage) throws DukeException {
        try {
            storage.save(tasks);
        } catch (Exception e) {
            String details = e.getMessage();
            if (details == null || details.isBlank()) {
                throw new DukeException("could not save tasks");
            }
            throw new DukeException("could not save tasks: " + details);
        }
    }
}
