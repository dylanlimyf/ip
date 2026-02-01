

public class Task {
    boolean isDone;
    String taskName;

    public Task(String taskName){
        this.taskName = taskName;
        this.isDone = false;
    }

    //gets the status of the task, if done will be marked with X, else nothing
    public String getStatusIcon(){
        return (isDone ? "X" : " ");
    }

    //printer to print the status of the task
    public String toPrintStatus(){
        return "[" + getStatusIcon() + "]" + " " + taskName;
    }
}