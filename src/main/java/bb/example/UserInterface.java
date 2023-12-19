package bb.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        database.addPerson("tom", "bil", "123456789", 45);
        database.addPerson("kul", "bil", "123456789", 45);
        database.addPerson("dom", "uil", "123456789", 45);
    }

    /**
     * Menu selection
     */
    public void menuSelectionLoop() {
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

            int inputOption = enterNumber();

            switch (inputOption) {
                case 1 -> addInsured();
                case 2 -> displayAllInsured();
                case 3 -> displayInsured();
                case 4 -> modifyInsured();
                case 5 -> deleteInsured();
                case 6 -> createFile();
                case 7 -> {
                    System.out.print("Goodbye");
                    return;
                }
                default -> System.out.println("Enter number from 1 to 7");
            }
        }
    }

    /**
     * User input to add a new insured person to the database
     */
    private void addInsured() {
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
    private void displayAllInsured() {
        // Output if the database is empty
        List<InsuredPerson> foundPersons = database.listOfAllPersons();
        if (foundPersons.isEmpty()) {
            System.out.println("No insured individuals are recorded in the database");
        }
        printPersonsOfList(foundPersons); // Display from the database
    }

    /**
     * The user requests to display specific insured persons by name, surname, or their parts
     */
    private void displayInsured() {

        System.out.println("Enter the name or surname:");
        String inputNameSurname = sc.nextLine().trim();

        List<InsuredPerson> foundPersons = database.findSpecificPerson(inputNameSurname);
        printPersonsOfList(foundPersons);

        // Output if the database is empty or the searched insured individual is not recorded
        if (foundPersons.isEmpty()) {
            System.out.println("This name is not recorded in the database");
        }
    }

    private void printPersonsOfList(List<InsuredPerson> persons) {
        for (InsuredPerson person : persons) {
            System.out.println(person);
        }
    }

    /**
     * The method requests data for modifying an insured person, which is subsequently processed by the database
     */
    private void modifyInsured() {
        System.out.println("Enter the ID of the person you are looking for:");
        int inputId = enterNumber();

        if (database.findById(inputId) == null) {
            System.out.println("The database does not contain the ID you entered");
        } else {
            System.out.println(database.findById(inputId));
            String newName = enterLetters("new name");
            String newSurname = enterLetters("new surname");
            String newTel = enterNumberOfPhone();

            database.editPerson(inputId, newName, newSurname, newTel);
            System.out.println("The person has been modified to:\n" + database.findById(inputId));
        }
    }


    /**
     * Deletion of an insured person by entering the ID
     */
    private void deleteInsured() {
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
    private void createFile() {
        System.out.println("Enter a name of the file");
        String fileName = sc.nextLine().trim() + ".txt";
        System.out.println("Enter a path of the folder where you would like to save the file");
        String userPath = sc.nextLine().trim();

        try {
            Path filePath = Paths.get(userPath, fileName);
            List<InsuredPerson> persons = database.listOfAllPersons();
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            for (InsuredPerson person : persons) {
                String personDetails = String.format("%s, %s, %s, %d%n", person.getName(), person.getSurname(), person.getPhone(), person.getAge());
                Files.writeString(filePath, personDetails, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
            System.out.println("The file was created");
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    /**
     * Method for validating letters using the isAlphabetic() method on the Character class
     *
     * @param inputName - Specify whether you want to ask for a name or surname
     * @return - name or surname
     */
    private String enterLetters(String inputName) {
        while (true) {
            System.out.printf("Enter the %s:\n", inputName);
            String name = sc.nextLine().trim();
            if (!containsInvalidCharacters(name)) {
                return name;
            }
            System.out.println("You must enter only letters of the alphabet");
        }
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
    private int enterNumber() {
        while (true) {
            int number = 0;
            String inputNumber = sc.nextLine().trim();

            if (isValidNumber(inputNumber)) {
                number = Integer.parseInt(inputNumber);
                return number;
            } else {
                System.out.println("Invalid number");
            }
        }
    }

    /**
     * Validation of numbers in a String
     * Validation of a 9-digit number
     *
     * @return phone number (String)
     */
    private String enterNumberOfPhone() {
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
    private int enterAge() {
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
    private boolean isValidNumber(String input) {
        boolean validation = true;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                validation = false;
            }
        }
        return validation;
    }
}