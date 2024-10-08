package bb.example;

import bb.example.services.Utilities;
import bb.example.services.Validators;
import bb.example.services.iohandler.InputHandler;
import bb.example.services.iohandler.OutputHandler;

import java.util.Collection;

/**
 * The class interacts with the user.
 *
 * @author pollib
 */
public class UserInterface {
    private final PersonDatabase database;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public UserInterface(PersonDatabase database, InputHandler inputHandler, OutputHandler outputHandler) {
        this.database = database;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    /**
     * Main method displays choice of actions and goes in a loop until user exits.
     *
     * @param inputHandler User input
     * @param outputHandler Print of program's output
     */
    public void menuSelectionLoop(InputHandler inputHandler, OutputHandler outputHandler) {
        while (true) {
            outputHandler.print("""

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

            String input = inputHandler.getInput();
            int inputOption = enterNumber(input);

            switch (inputOption) {
                case 1 -> addInsured();
                case 2 -> displayAllInsured();
                case 3 -> displaySpecificInsured();
                case 4 -> modifyInsured();
                case 5 -> deleteInsured();
                case 6 -> createFile();
                case 7 -> {
                    outputHandler.print("Goodbye");
                    return;
                }
                default -> outputHandler.print("Enter number from 1 to 7");
            }
        }
    }
    private int enterNumber(String input) {
        while (!Validators.isValidNumber(input)) {
            outputHandler.print("Invalid number. Please enter number.");
            input = inputHandler.getInput();
        }
        return Integer.parseInt(input);
    }

    private void addInsured() {
        String name = enterUserName("name");
        String surname = enterUserName("surname");
        String tel = enterPhoneNumber();
        int age = enterAge();

        database.addPerson(name, surname, tel, age); // Add the insured person to the database

        outputHandler.print("A new insured person has been successfully added.");
    }

    private String enterUserName(String name) {
        while (true) {
            outputHandler.print("Enter the " + name + ":");
            String userName = inputHandler.getInput();

            if (Validators.doesContainChar(userName)) {
                return userName;
            } else {
                outputHandler.print("You must enter only letters of the alphabet");

            }
        }
    }

    private String enterPhoneNumber() {
        while (true) {
            outputHandler.print("Enter the phone number (9 digits without the area code):");
            String userPhoneNumber = inputHandler.getInput();

            if (doesContainNineDigitString(userPhoneNumber)) {
                return userPhoneNumber;
            }
        }
    }

    private boolean doesContainNineDigitString(String string) {
        if (!Validators.isValidNumber(string)) {
            outputHandler.print("The phone number cannot contain letters or special characters and must be 9-digit number");
        } else if (string.length() != 9) {
            outputHandler.print("Enter a 9-digit number");
        } else {
            return true;
        }
        return false;
    }


    private int enterAge() {
        outputHandler.print("Enter the age:");
        while (true) {
            int age = enterNumber(inputHandler.getInput());

            if (Validators.isAgeValid(age)) {
                return age;
            } else {
                outputHandler.print("The age must be in the range of 0-150");
            }
        }
    }


    private void displayAllInsured() {
        Collection<Person> persons = database.listOfAllPersons();
        if (persons.isEmpty()) {
            outputHandler.print("No insured individuals are recorded in the database");
        }

        outputHandler.print(Utilities.buildPersonsList(persons));
    }

    private void displaySpecificInsured() {

        outputHandler.print("Enter the name or surname:");
        String inputNameSurname = inputHandler.getInput();

        Collection<Person> persons = database.findSpecificPerson(inputNameSurname);

        if (persons.isEmpty()) {
            outputHandler.print("This name is not recorded in the database");
        } else {
            outputHandler.print(Utilities.buildPersonsList(persons));
        }
    }

    private void modifyInsured() {
        outputHandler.print("Enter the ID of the person you are looking for:");
        String input = inputHandler.getInput();
        int inputId = enterNumber(input);
        Person person = database.findById(inputId);

        if (person == null) {
            outputHandler.print("The database does not contain the ID you entered");
        } else {
            outputHandler.print(String.valueOf(person));
            String newName = enterUserName("new name");
            String newSurname = enterUserName("new surname");
            String newTel = enterPhoneNumber();

            if (Validators.isValidNumber(newTel) && doesContainNineDigitString(newTel)) {
                database.editPerson(inputId, newName, newSurname, newTel);
                outputHandler.print("The person has been modified to:\n" + database.findById(inputId));
            } else {
                outputHandler.print("Invalid phone number format.");
            }
        }
    }

    private void deleteInsured() {
        outputHandler.print(String.valueOf(database.listOfAllPersons()));
        outputHandler.print("Enter the ID of the person you would like to delete");
        String input = inputHandler.getInput();
        int inputId = enterNumber(input);

        if (database.deletePerson(inputId)) {
            outputHandler.print("The insured individual has been deleted");
        } else {
            outputHandler.print("Person with the given ID not found");
        }
    }

    private void createFile() {
        outputHandler.print("Enter a name of the file");
        String fileName = inputHandler.getInput() + ".txt";
        outputHandler.print("Enter a name of the folder where you would like to save the file [USER.HOME]/...");
        String userDirectory = inputHandler.getInput();

        Collection<Person> persons = database.listOfAllPersons();

        outputHandler.print(Utilities.saveFileTxt(fileName, userDirectory, persons));
    }
}