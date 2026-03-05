/**
 * Represents a todo task.
 */
public class ToDo extends Task {

    /**
     * Creates a todo task.
     *
     * @param taskName description
     */
    public ToDo(String taskName) {
        super(taskName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeIcon(){
        return "T";
    }

}
