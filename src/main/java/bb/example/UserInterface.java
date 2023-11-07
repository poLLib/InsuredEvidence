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
        String tel = validatePhone();

        // Age validation, the number must be in the range of 0-100
        int age = validateAge();

        database.addPerson(name, surname, tel, age); // Add the insured person to the database
        System.out.println("A new insured person has been added.");
    }

    /**
     * The user requests to display all insured persons
     */
    public void displayAllInsured() {
        database.writePerson(); // Display from the database
    }

    /**
     * The user requests to display specific insured persons by name, surname, or their parts
     */
    public void displayInsured() {

        System.out.println("Enter the name or surname:");
        String inputNameSurname = sc.nextLine().trim();

        database.findPerson(inputNameSurname); // Display from the database
    }

    /**
     * The method requests data for modifying an insured person, which is subsequently processed by the database
     * (NOTE: Watch out for duplicate names)
     */
    public void modifyInsured() {
        System.out.println("Enter the name you are looking for:");
        String inputName = sc.nextLine().trim();
        System.out.println("Enter the surname you are looking for:");
        String inputSurname = sc.nextLine().trim();

        String newName = validateLetters("new name");
        String newSurname = validateLetters("new surname");
        String newTel = validatePhone();

        System.out.println("The insured person has been modified.");
        database.editPerson(inputName, inputSurname, newName, newSurname, newTel);
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
        boolean letterValidation = false;

        while (!letterValidation) {
            System.out.printf("Enter the %s:\n", nameSurname);
            returnName = sc.nextLine().trim();

            for (char letter : returnName.toLowerCase().toCharArray()) {
                if (!Character.isAlphabetic(letter) && letter != ' ' && letter != '-') {
                    letterValidation = false;
                    break;
                } else {
                    letterValidation = true;
                }
            }
            if (!letterValidation)
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
     * Validation of numbers in a string through the ASCII table
     * Validation of a 9-digit number
     *
     * @return phone number (String)
     */
    public String validatePhone() {
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

    public int validateAge() {
        int age = 0;
        boolean ageValidation = false;

        while (!ageValidation) {
            System.out.println("Enter the age:");
            String inputAge = sc.nextLine();

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
