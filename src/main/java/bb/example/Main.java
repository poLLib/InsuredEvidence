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
        boolean end = false;
        while (!end) {
            System.out.printf(userInterface.displayMenu()); // Displays the main menu
            try {
                int input = userInterface.menuSelection();
                if (input >= 1 && input <= 6) {
                    switch (input) {

                        /* Command to register a new insured person --> @name @surname @tel @age */
                        case 1 -> userInterface.addInsured();

                        /* Command to list all insured individuals */
                        case 2 -> userInterface.displayAllInsured();

                        /* Command to search for a specific insured person by name or surname content --> @name @surname */
                        case 3 -> userInterface.displayInsured();

                        /* Command to modify insured person's data / search by name and surname */
                        case 4 -> userInterface.modifyInsured();

                        /* Command to delete a specific insured person */
                        case 5 -> userInterface.deleteInsured();

                        /* Command to end the loop */
                        case 6 -> {
                            System.out.printf(userInterface.endProgram());
                            end = true;
                        }
                    }
                } else {
                    System.out.println("Enter a number from 1 to 6"); // Output for incorrect command
                }
            } catch (Exception e) {
                System.out.println("Enter a number from 1 to 6"); // Output for incorrect command
            }
        }
    }
}
