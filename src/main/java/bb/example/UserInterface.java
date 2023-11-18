package bb.example;

import java.util.Scanner;

/**
 * This class is used for interaction between user input and the program (you can think of it as the front-end).
 * <p>
 * Here you will find the following methods:
 * - Display the main menu
 * - Menu selection
 * - Adding an insured person --> addInsured()
 * - Listing insured individuals --> listAllInsured()
 * - Searching and listing a specific insured person --> viewInsured()
 * - Modifying insured person's data --> modifyInsured()
 * - Deleting an insured person --> deleteInsured()
 * - Ending the program
 * - Validation of letters and numbers
 * - Validation of age
 * - Validation of phone number
 *
 * @author pollib
 */
public class UserInterface {
    private final Scanner sc = new Scanner(System.in, "Windows-1250"); // Creating an instance for user input
    private final DatabaseOfInsured database;

    // Creating an instance of the database
    public UserInterface() {
        database = new DatabaseOfInsured();
    } // Creating an instance of the database

    /**
     * Display the main menu with action options
     *
     * @return - returns the entire menu
     */
    public String displayMenu() {
        return """

                --------Insured Records----------

                Choose an action:
                1 - Add a new insured person
                2 - List all insured individuals
                3 - Search for an insured person
                4 - Modify an insured person
                5 - Delete an insured person
                6 - End
                --------------------------------------
                Enter the action number:""";
    }

    /**
     * Menu selection
     *
     * @return - returns the numeric input
     */
    public int menuSelection() {
        return Integer.parseInt(sc.nextLine());
    }

    /**
     * User input to add a new insured person to the database
     */
    public void addInsured() {
        // Validation methods returning a string for name and surname
        String name = validateLetters("name");
        String surname = validateLetters("surname");

        // Phone number validation, can only have 9 digits
        String tel = validateNumberOfPhone();

        // Age validation, the number must be in the range of 0-100
        int age = validateAge();

        database.addPerson(name, surname, tel, age); // Add the insured person to the database
        System.out.println("A new insured person has been added.");
    }

    /**
     * The user requests to display all insured persons
     */
    public void displayAllInsured() {
        database.listOfAllPersons(); // Display from the database
    }

    /**
     * The user requests to display specific insured persons by name, surname, or their parts
     */
    public void displayInsured() {

        System.out.println("Enter the name or surname:");
        String inputNameSurname = sc.nextLine().trim();

        database.findSpecificPerson(inputNameSurname); // Display from the database
    }

    /**
     * The method requests data for modifying an insured person, which is subsequently processed by the database
     * (NOTE: Watch out for duplicate names)
     */
    public void modifyInsured() {
        int id;
        boolean validationOfId = false;
        while (!validationOfId) {
            System.out.println("Enter the ID of the person you are looking for:");
            String inputId = sc.nextLine().trim();

            for (char c : inputId.toCharArray()) {
                validationOfId = validateNumbers(c); // Validate numbers through the ASCII table
            }
            if (validationOfId) {
                id = Integer.parseInt(inputId);

                if (database.findById(id) == null) {
                    System.out.println("The database does not contain the ID you entered");
                    validationOfId = false;
                } else {
                    String newName = validateLetters("new name");
                    String newSurname = validateLetters("new surname");
                    String newTel = validateNumberOfPhone();

                    database.editPerson(id, newName, newSurname, newTel);
                    System.out.println("The insured person has been modified.");
                }
            } else {
                System.out.println("The number of ID cannot contain letters or special characters, it must be as an absolute number");
            }
        }
    }

    /**
     * Deletion of an insured person by entering their name and surname
     */
    public void deleteInsured() {

        System.out.println("Enter the name:");
        String inputName = sc.nextLine().trim();
        System.out.println("Enter the surname:");
        String inputSurname = sc.nextLine().trim();

        database.deletePerson(inputName, inputSurname); // Delete from the database
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
     * @param nameSurname - Specify whether you want to ask for a name or surname
     * @return - name or surname
     */
    public String validateLetters(String nameSurname) {
        String returnName = "";
        boolean validationOfLetters = true;

        while (validationOfLetters) {
            System.out.printf("Enter the %s:\n", nameSurname);
            returnName = sc.nextLine().trim();

            for (char letter : returnName.toLowerCase().toCharArray()) {
                if (!Character.isAlphabetic(letter) && letter != ' ' && letter != '-') {
                    validationOfLetters = true;
                    break;
                } else {
                    validationOfLetters = false;
                }
            }
            if (validationOfLetters)
                System.out.println("You must enter only letters of the Czech alphabet");
        }
        return returnName;
    }

    /**
     * Method for validating numbers through the ASCII table
     *
     * @param c - individual character in the string
     * @return - true = all characters in the string are numbers
     */
    public boolean validateNumbers(char c) {
        return ((int) c >= 48) && ((int) c <= 57);
    }

    /**
     * Validation of numbers in a string through the ASCII table (using method validateNumbers(char))
     * Validation of a 9-digit number
     * Removing spaces between digits
     *
     * @return phone number (String)
     */
    public String validateNumberOfPhone() {
        boolean numberValidation = false;
        String inputPhone = "";

        while (!numberValidation) {
            System.out.println("Enter the phone number (without the area code):");
            inputPhone = sc.nextLine().replace(" ", "").trim();

            for (char c : inputPhone.toCharArray()) {
                numberValidation = validateNumbers(c); // Validate numbers through the ASCII table
            }

            if (!numberValidation) {
                System.out.println("The phone number cannot contain letters or special characters");
            }
            if (inputPhone.length() == 9) {
                numberValidation = true;
                return inputPhone;
            } else {
                System.out.println("Enter a 9-digit number");
            }
        }
        return inputPhone;
    }

    /**
     * Method for validating numbers in age through the ASCII table (using method validateNumbers(char))
     * Validate the range of age 0-100
     *
     * @return age (int)
     */
    public int validateAge() {
        int age = 0;
        boolean ageValidation = false;

        while (!ageValidation) {
            System.out.println("Enter the age:");
            String inputAge = sc.nextLine().trim();

            for (char c : inputAge.toCharArray()) {
                ageValidation = validateNumbers(c); // Validate numbers through the ASCII table
            }

            // Validation of the range 0-100
            if (!ageValidation) {
                System.out.println("Age cannot contain letters or special characters");
            } else {
                age = Integer.parseInt(inputAge);
            }
            if (age < 0) {
                ageValidation = false;
                System.out.println("Enter the age as an absolute number");
            } else if (age > 100) {
                ageValidation = false;
                System.out.println("The age is too high");
            }
        }
        return age;
    }

}