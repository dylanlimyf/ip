public class Task {
    protected boolean isDone = false;
    protected final String description;

    public Task(String description) {
        this.description = description;
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    //gets the status of the task, if done will be marked with X, else nothing
    public String getStatusIcon(){
        return (isDone ? "X" : " ");
    }

    public String getTypeIcon(){
        return "?";
    }

    public String toPrintStatus(){
        return "[" + getTypeIcon() + "]" + "[" + getStatusIcon() + "]" +
                " " + description;
    }

    public String toSaveString() {
        return getTypeIcon() + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    public boolean matches(String keyword) {
        return description.contains(keyword);
    }
}
