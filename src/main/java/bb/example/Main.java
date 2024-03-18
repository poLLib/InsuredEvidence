package bb.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author pollib
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteArrayOutputStream);

        UserInterface userInterface = new UserInterface(new PersonDatabase());

            /* Start of the user session loop
               The user selects a command by entering a number
             */
        while (true) {
            System.out.println("""

                    --------Insured Records----------

                    Choose an action:
                    1 - Add a new insured person
                    2 - List all insured individuals
                    3 - Search for an insured person
                    4 - Modify an insured person
                    5 - Delete an insured person
                    6 - Create a file
                    7 - End
                    --------------------------------------
                    Enter the action number:""");

            String option = scanner.nextLine();

            String result = userInterface.menuSelection(option);

            try {
                byteArrayOutputStream.write(result.getBytes());
                System.out.println(byteArrayOutputStream.toString(StandardCharsets.UTF_8));

                if (result.equals("Goodbye")) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byteArrayOutputStream.reset();
        }
        scanner.close();
    }
}