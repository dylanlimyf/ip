import java.util.Objects;
import java.util.Scanner;

public class Bobby {
    public static void main(String[] args) {
        String line = "____________________________________________________________\n";
        String greeting = "Hello! I'm Bobby\n";
        String doRequest = "What can I do for you?\n";
        String goodbye = "So you hate me. Okay bye.\n";

        System.out.println(line + greeting + doRequest + line);

        while (true) {
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();

            if (Objects.equals(input, "bye")) {
                System.out.print(goodbye);
                return;
            }

            System.out.print(input + "\n" + line);

        }
    }
}
