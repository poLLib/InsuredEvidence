package bb.example;

import java.util.Scanner;

/**
 * @author pollib
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface userInterface = new UserInterface(new PersonDatabase(), scanner::nextLine, System.out::println);

            /* Start of the user session loop
               The user selects a command by entering a number
             */
        userInterface.menuSelectionLoop(scanner::nextLine);
    }
}