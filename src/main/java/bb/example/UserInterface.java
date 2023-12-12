package bb.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used for interaction between user input and the program (you can think of it as the front-end).
 * <p>
 * Here you will find the following methods:
 * - Display the main menu --> displayMenu()
 * - Menu selection --> menuSelectionLoop()
 * - Adding an insured person --> addInsured()
 * - Listing insured individuals --> listAllInsured()
 * - Searching and listing a specific insured person --> viewInsured()
 * - Modifying insured person's data --> modifyInsured()
 * - Deleting an insured person --> deleteInsured()
 * - Creating a txt file --> createFile()
 * - Ending the program
 * - Validation of ID
 * - Validation of letters and numbers
 * - Validation of age
 * - Validation of phone number
 *
 * @author pollib
 */
public class UserInterface {
    private final Scanner sc = new Scanner(System.in); // Creating an instance for user's input
    private final DatabaseOfInsured database;

    // Creating an instance of the database
    public UserInterface() {
        database = new DatabaseOfInsured();
    }

    /**
     * Menu selection
     */
    public void menuSelectionLoop() {
        boolean isEndProgram = false;
        while (!isEndProgram) {
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

            int inputOption = enterNumber();

            switch (inputOption) {
                case 1 -> addInsured();
                case 2 -> displayAllInsured();
                case 3 -> displayInsured();
                case 4 -> modifyInsured();
                case 5 -> deleteInsured();
                case 6 -> createFile();
                case 7 -> {
                    System.out.printf(endProgram());
                    isEndProgram = true;
                }
                default -> errorMessage(Error.MENU);
            }
        }
    }

    /**
     * User input to add a new insured person to the database
     */
    public void addInsured() {
        // Validation methods returning a string for name and surname
        String name = enterLetters("name");
        String surname = enterLetters("surname");

        // Phone number validation, can only have 9 digits
        String tel = enterNumberOfPhone();

        // Age validation, the number must be in the range of 0-100
        int age = enterAge();

        database.addPerson(name, surname, tel, age); // Add the insured person to the database
        System.out.println("A new insured person has been added.");
    }

    /**
     * The user requests to display all insured persons
     */
    public void displayAllInsured() {
        // Output if the database is empty
        if (database.listOfAllPersons().isEmpty()) {
            System.out.println("\nNo insured individuals are recorded in the database");
        }
        System.out.println("\n" + database.listOfAllPersons()); // Display from the database

    }

    /**
     * The user requests to display specific insured persons by name, surname, or their parts
     */
    public void displayInsured() {

        System.out.println("Enter the name or surname:");
        String inputNameSurname = sc.nextLine().trim();

        System.out.println(database.findSpecificPerson(inputNameSurname));

        // Output if the database is empty or the searched insured individual is not recorded
        if (database.findSpecificPerson(inputNameSurname).isEmpty()) {
            System.out.println("This name is not recorded in the database");
        }

        database.findSpecificPerson(inputNameSurname); // Display from the database

    }

    /**
     * The method requests data for modifying an insured person, which is subsequently processed by the database
     */
    public void modifyInsured() {
        while (true) {
            System.out.println("Enter the ID of the person you are looking for:");
            int inputId = enterNumber();
            boolean isValid = false;

            if (inputId >= 0) {
                if (database.findById(inputId) == null) {
                    System.out.println("The database does not contain the ID you entered");
                } else {
                    String newName = enterLetters("new name");
                    String newSurname = enterLetters("new surname");
                    String newTel = enterNumberOfPhone();

                    database.editPerson(inputId, newName, newSurname, newTel);
                    System.out.println("The insured person has been modified.");
                }
                break; // exit the loop if the ID is valid
            } else {
                System.out.println("The number of ID cannot contain letters or special characters, it must be as an absolute number");
            }
        }
    }

    /**
     * Deletion of an insured person by entering the ID
     */
    public void deleteInsured() {
        System.out.println("Enter the ID of the person you would like to delete");
        int inputId = enterNumber();

        if (database.deletePerson(inputId)) {
            System.out.println("The insured individual has been deleted");
        } else {
            System.out.println("Person with the given ID not found");
        }
    }

    /**
     * Creating a txt file of insured persons
     */
    public void createFile() {
        System.out.println("Enter a name of the file");
        String fileName = sc.nextLine().trim() + ".txt";
        System.out.println("Enter a path of the folder where you would like to save the file");
        String userPath = sc.nextLine().trim();

        try {
            Path filePath = Paths.get(userPath + fileName).toAbsolutePath();
            List<String> listOfPersons = Collections.singletonList(database.listOfAllPersons().toString());
            Files.write(filePath, listOfPersons, StandardOpenOption.CREATE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ending the program
     *
     * @return - farewell
     */
    public String endProgram() {
        return "Goodbye";
    }

    /**
     * Method for validating letters using the isAlphabetic() method on the Character class
     *
     * @param name - Specify whether you want to ask for a name or surname
     * @return - name or surname
     */
    public String enterLetters(String name) {
        String returnName;

        do {
            System.out.printf("Enter the %s:\n", name);
            returnName = sc.nextLine().trim();

            if (containsInvalidCharacters(returnName)) {
                System.out.println("You must enter only letters of the alphabet");
            }

        } while (containsInvalidCharacters(returnName));

        return returnName;
    }

    /**
     * @param input - String to be validated for letters
     * @return true for invalid characters
     */
    private boolean containsInvalidCharacters(String input) {
        for (char letter : input.toLowerCase().toCharArray()) {
            if (!(Character.isAlphabetic(letter) || letter == ' ' || letter == '-')) {
                return true;
            }
        }
        return false;
    }


    /**
     * Method validate number of user's input
     *
     * @return - number (int)
     */
    public int enterNumber() {
        while (true) {
            System.out.println("Enter a number");
            int number = 0;
            String inputNumber = sc.nextLine().trim();
            boolean isValid = true;

            if (isValidNumber(inputNumber)) {
                number = Integer.parseInt(inputNumber);
                return number;
            } else {
                errorMessage(Error.NUMBER);
            }
        }
    }

    /**
     * Validation of numbers in a String
     * Validation of a 9-digit number
     *
     * @return phone number (String)
     */
    public String enterNumberOfPhone() {
        while (true) {
            System.out.println("Enter the phone number (9 digits without the area code):");
            String inputPhoneNumber = sc.nextLine().trim();

            if (!isValidNumber(inputPhoneNumber)) {
                System.out.println("The phone number cannot contain letters or special characters and must be 9-digit number");
            } else if (inputPhoneNumber.length() != 9) {
                System.out.println("Enter a 9-digit number");
            } else {
                return inputPhoneNumber;
            }
        }
    }

    /**
     * Method for validating numbers in age
     * Validate the range of age 0-100
     *
     * @return age (int)
     */
    public int enterAge() {
        System.out.println("Enter the age:");
        while (true) {
            int age = enterNumber();

            if ((age > 0 && age <= 100)) {
                return age;
            } else {
                System.out.println("The age must be in the range of 1-100");
            }
        }
    }

    /**
     * @param input - String to be checked if contains numbers
     * @return - true if only numbers
     */
    public boolean isValidNumber(String input) {
        boolean validation = true;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                validation = false;
            }
        }
        return validation;
    }

    /**
     * Display an error
     *
     * @param error - enum of errors according to use
     */
    private void errorMessage(Error error) {
        if (error.equals(Error.MENU)) {
            System.out.println("Enter number from 1 to 6");
        } else if (error.equals(Error.NUMBER)) {
            System.out.println("Invalid number");
        }
    }

    /**
     * Enum of errors
     */
    enum Error {
        MENU,
        NUMBER,

    }
}