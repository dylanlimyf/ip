import java.util.Objects;
import java.util.Scanner;

public class Slave {

    public static void main(String[] args) {

        String line = "____________________________________________________________\n";
        String greeting = "morning boss\n";
        String doRequest = "ok boss, what do you want to settle tdy\n";
        String goodbye = "ok pangkang, byebye\n";


        String[] list = new String[100];
        System.out.println(line + greeting + doRequest + line);
        Scanner in = new Scanner(System.in);

        //keeps track of the number of items in the list
        int count = 0;

        while (true){
            String input = in.nextLine();

            //if user inputs "bye", the bot will exit
            if (Objects.equals(input, "bye")) {
                System.out.print(goodbye);
                return;
            }

            //if user inputs list, list of tasks to do is created
            if (Objects.equals(input, "list")){
                for (int j = 0; j < count; j++){
                    System.out.print((j + 1) + ". " + list[j] + "\n");
                }
                System.out.print(line);
                continue;
            }

            list[count] = input;
            System.out.print("added: " + input + "\n" + line);

            count++;
        }
    }
}
