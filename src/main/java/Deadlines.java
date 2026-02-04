public class Deadlines extends Task {

    public Deadlines(String taskName) {
        super(taskName);
    }

    @Override
    public String getTypeIcon(){
        return "D";
    }

}
