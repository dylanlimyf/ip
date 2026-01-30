

public class Task {
    boolean isDone;
    String taskName;

    public Task(String taskName){
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getStatusIcon(){
        return (isDone ? "X" : " ");
    }

    public String toPrintStatus(){
        return "[" + getStatusIcon() + "]" + taskName;
    }
}