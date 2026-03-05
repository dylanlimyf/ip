/**
 * Represents an event task.
 */
public class Events extends Task{

    /**
     * Creates an event task.
     *
     * @param taskName description
     */
    public Events(String taskName) {
        super(taskName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeIcon(){
        return "E";
    }

}
