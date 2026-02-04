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
            String input = in.nextLine().trim();

            if (input.isEmpty()) {
                // ignore blank enter / spaces-only input
                continue;
            }

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
                String[] splitText = input.trim().split("\\s+");

                // must be exactly: mark <number> or unmark <number>
                if (splitText.length < 2) {
                    System.out.print("bro put a task number: mark 1 / unmark 1\n" + line);
                    continue;
                }

                int taskNumber;

                try {
                    taskNumber = Integer.parseInt(splitText[1]) - 1;
                } catch (NumberFormatException e) {
                    System.out.print("task number must be a number eh (e.g. mark 2)\n" + line);
                    continue;
                }

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
                Task task = new Task(input);
                listOfTasks[numberOfTasks] = task;
                System.out.print("added: " + input + "\n" + line);

                numberOfTasks++;
            }
        }
    }
}
