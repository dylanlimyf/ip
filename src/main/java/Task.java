public class Task {
    boolean isDone = false;
    String taskName;

    public Task(String taskName){
        this.taskName = taskName;
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
                " " + taskName;
    }

    public String toSaveString() {
        return getTypeIcon() + " | " + (isDone ? "1" : "0") + " | " + taskName;
    }
}