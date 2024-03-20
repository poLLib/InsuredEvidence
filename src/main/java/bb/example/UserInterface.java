package bb.example;

import bb.example.services.IOHandler.InputHandler;
import bb.example.services.IOHandler.OutputHandler;
import bb.example.services.Utilities;
import bb.example.services.Validators;

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

    public void menuSelectionLoop(InputHandler inputHandler) {
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

            String input = inputHandler.getInput();
            int inputOption = enterNumber(input);

            switch (inputOption) {
                case 1 -> outputHandler.print(addInsured());
                case 2 -> outputHandler.print(displayAllInsured());
                case 3 -> outputHandler.print(displayInsured());
                case 4 -> outputHandler.print(modifyInsured());
                case 5 -> outputHandler.print(deleteInsured());
                case 6 -> outputHandler.print(createFile());
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

    private String addInsured() {
        String name = enterUserName("name");
        String surname = enterUserName("surname");
        String tel = enterPhoneNumber();
        int age = enterAge();

        database.addPerson(name, surname, tel, age); // Add the insured person to the database

        return "A new insured person has been successfully added.";
    }

    private String enterUserName(String name) {
        while (true) {
            System.out.printf("Enter the %s:\n", name);
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
            System.out.println("Enter the phone number (9 digits without the area code):");
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
        System.out.println("Enter the age:");
        while (true) {
            int age = enterNumber(inputHandler.getInput());

            if (Validators.doesContainRangeOfDigits(age)) {
                return age;
            } else {
                outputHandler.print("The age must be in the range of 1-100");
            }
        }
    }


    private String displayAllInsured() {
        Collection<Person> persons = database.listOfAllPersons();
        if (persons.isEmpty()) {
            return "No insured individuals are recorded in the database";
        }

        return Utilities.buildPersonsList(persons);
    }

    private String displayInsured() {

        System.out.println("Enter the name or surname:");
        String inputNameSurname = inputHandler.getInput();

        Collection<Person> persons = database.findSpecificPerson(inputNameSurname);

        if (persons.isEmpty()) {
            return "This name is not recorded in the database";
        } else {
            return Utilities.buildPersonsList(persons);
        }
    }

    private String modifyInsured() {
        outputHandler.print("Enter the ID of the person you are looking for:");
        String input = inputHandler.getInput();
        int inputId = enterNumber(input);
        Person person = database.findById(inputId);

        if (person == null) {
            return "The database does not contain the ID you entered";
        } else {
            System.out.println(person);
            String newName = enterUserName("new name");
            String newSurname = enterUserName("new surname");
            String newTel = enterPhoneNumber();

            database.editPerson(inputId, newName, newSurname, newTel);
            return "The person has been modified to:\n" + database.findById(inputId);
        }
    }

    private String deleteInsured() {
        System.out.println(database.listOfAllPersons());
        outputHandler.print("Enter the ID of the person you would like to delete");
        String input = inputHandler.getInput();
        int inputId = enterNumber(input);

        if (database.deletePerson(inputId)) {
            return "The insured individual has been deleted";
        } else {
            return "Person with the given ID not found";
        }
    }

    private String createFile() {
        System.out.println("Enter a name of the file");
        String fileName = inputHandler.getInput() + ".txt";
        System.out.println("Enter a name of the folder where you would like to save the file [USER.HOME]/...");
        String userDirectory = inputHandler.getInput();

        Collection<Person> persons = database.listOfAllPersons();

        return Utilities.saveFileTxt(fileName, userDirectory, persons);
    }
}