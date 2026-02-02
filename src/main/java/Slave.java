import java.util.Objects;
import java.util.Scanner;
import java.lang.String;


public class Slave {

    public static void main(String[] args) {

        String line = "____________________________________________________________\n";
        String greeting = "morning boss\n";
        String doRequest = "ok boss, what do you want to settle tdy\n";
        String goodbye = "ok pangkang, byebye\n";


        Task[] listOfTasks = new Task[100];

        //welcome line
        System.out.println(line + greeting + doRequest + line);

        Scanner in = new Scanner(System.in);

        //keeps track of the number of items in the listOfTasks
        int numberOfTasks = 0;

        while (true){
            String input = in.nextLine();
            Task task = new Task(input);

            //if user inputs "bye", the bot will exit
            if (Objects.equals(input, "bye")) {
                System.out.print(goodbye);
                return;
            }

            //if user inputs listOfTasks, list of tasks to do is created
            // will also print the status of the task
            if (Objects.equals(input, "list")){
                for (int j = 0; j < numberOfTasks; j++){
                    System.out.print((j + 1) + ". " + listOfTasks[j].toPrintStatus() + "\n");
                }
                System.out.print(line);
                continue;
            }

            //TODO: handle cases where user will enter markkk, unmarkk etc, the bug is at the checking
            // condition

            //if user inputs mark, it will mark as done in isDone, else if unmark then mark as undone
            else if (input.startsWith("mark") || input.startsWith("unmark")) {
                String[] splitText = input.split(" ");


                // checks the format of the mark message
                if (splitText.length < 2) {
                    System.out.print("input 'mark <number>' please, you trying to game the system\n" + line);
                    continue;
                }

                // checks if the task number is a number
                if (!splitText[1].matches("\\d+")) {
                    System.out.print("task number is not a number???\n" + line);
                    continue;
                }

                // minus one due to the position of the task in the task array is 1 less than the
                // number the user inputs
                int taskNumber = Integer.parseInt(splitText[1]) - 1;


                // checks if the task number marked is within the number of tasks that has been
                // added to the list
                if (taskNumber < 0 || taskNumber >= numberOfTasks){
                    System.out.print("wtf is this task number, make it make sense" + "\n" + line);
                    continue;
                }

                else if (input.startsWith("mark")) {
                    listOfTasks[taskNumber].isDone = true;
                    System.out.print("shiok, ok this task settled" + "\n");

                } else {
                    listOfTasks[taskNumber].isDone = false;
                        System.out.print("so like did u do it already or not, make up ur mind" + "\n");

                }

                System.out.print("  " + listOfTasks[taskNumber].toPrintStatus() + "\n" + line);

            }


            else {

                //listOfTasks to keep track of what the user inputs, adds to the listOfTasks
                listOfTasks[numberOfTasks] = task;
                System.out.print("added: " + input + "\n" + line);

                numberOfTasks++;
            }
        }
    }
}
