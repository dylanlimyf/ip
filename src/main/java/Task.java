public class Task {
    boolean isDone = false;
    String taskName;

    public Task(String taskName){
        this.taskName = taskName;
    }

    //gets the status of the task, if done will be marked with X, else nothing
    public String getStatusIcon(){
        return (isDone ? "X" : " ");
    }

    public String getTypeIcon(){
        return "?";
    }

    //printer to print the status of the task
    public String toPrintStatus(){
        return "[" + getTypeIcon() + "]" + "[" + getStatusIcon() + "]" +
                " " + taskName;
    }

}