package bb.example;

/**
 * @author pollib
 */
public class Main {
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();

        /* Start of the user session loop
           The user selects a command by entering a number
         */
        userInterface.menuSelectionLoop();
    }
}
