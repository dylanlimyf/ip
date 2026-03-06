/**
 * Base class for all task types.
 */
public class Task {
    protected boolean isDone = false;
    protected final String description;

    /**
     * Creates a task with the given description.
     *
     * @param description task description
     */
    public Task(String description) {
        this.description = description;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Returns the status icon for printing.
     *
     * @return "X" if done, otherwise blank
     */
    public String getStatusIcon(){
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the type icon for printing/saving.
     *
     * @return task type icon (default "?", subclasses override)
     */
    public String getTypeIcon(){
        return "?";
    }

    /**
     * Returns the printable form of the task.
     *
     * @return printable status line
     */
    public String toPrintStatus(){
        return "[" + getTypeIcon() + "]" + "[" + getStatusIcon() + "]" +
                " " + description;
    }

    /**
     * Returns the storage format line.
     *
     * @return save string
     */
    public String toSaveString() {
        return getTypeIcon() + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns true if the task matches the keyword.
     *
     * @param keyword search term
     * @return true if matched
     */
    public boolean matches(String keyword) {
        return description.contains(keyword);
    }
}
