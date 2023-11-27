package bb.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
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
    }

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
                6 - Save a *.txt file of insured persons
                7 - End
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
        String name = validateLettersInName("name");
        String surname = validateLettersInName("surname");

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
        System.out.println(database.listOfAllPersons()); // Display from the database
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
                    String newName = validateLettersInName("new name");
                    String newSurname = validateLettersInName("new surname");
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
     * Deletion of an insured person by entering the ID
     */
    public void deleteInsured() {

        System.out.println("Enter the ID of the person you want to remove from database:");
        String inputId = sc.nextLine().trim();

        database.deletePerson(validateId(inputId)); // Delete from the database
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
    public String validateLettersInName(String nameSurname) {
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
                System.out.println("You must enter only letters of the alphabet");
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
     * Method for validation of ID's
     * The numbers and if the ID exists in the database
     *
     * @param inputId - The number of ID given by user
     * @return - ID (int)
     */

    public int validateId(String inputId) {
        boolean hasDigits = false;

        for (char c : inputId.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigits = true;
                break;
            }
        }

        if (!hasDigits) {
            System.out.println("The input does not contain any digits. Please enter a valid ID.");
            return 0; // nebo jiná hodnota, kterou chcete vrátit v případě neplatného vstupu
        }

        int id;

        try {
            id = Integer.parseInt(inputId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a valid number.");
            return 0; // nebo jiná hodnota, kterou chcete vrátit v případě neplatného čísla
        }

        if (database.findById(id) == null) {
            System.out.println("The database does not contain the ID you entered");
            return 0; // nebo jiná hodnota, kterou chcete vrátit v případě neexistujícího ID v databázi
        }

        return id;
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
                numberValidation = false;
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
            if (age <= 0) {
                ageValidation = false;
                System.out.println("Enter the age as an absolute number");
            } else if (age > 100) {
                ageValidation = false;
                System.out.println("The age is too high");
            }
        }
        return age;
    }

    /**
     * The process of creating a new *.txt file of the insured persons either to the original location or one of user's choice
     * Using Java.io
     */
    public void createFile() {
        System.out.println("How would you like to name your file?");
        String inputNameOfFile = sc.nextLine().trim().toLowerCase() + ".txt";

        boolean pathToFile = true;
        while (pathToFile) {
            System.out.println("The original path to the file is \"\\Home\\User\\Database of Insured Persons\" (example: C:\\Users\\Database of Insured Persons).\nDo you wish to change the path? YES / NO");
            String inputPath = sc.nextLine().trim().toLowerCase();

            // If the user chooses to keep the original path to the file
            if (inputPath.contains("no")) {
                File file = new File(System.getProperty("user.home") + File.separator + "Database of Insured Persons" + File.separator + inputNameOfFile);
                handleFileExistence(file);
                pathToFile = false;
            }
            // If the user chooses to change the path to the file
            else if (inputPath.contains("yes")) {
                File file = userPath(inputNameOfFile);
                handleFileExistence(file);
                pathToFile = false;
            }
        }
    }

    /**
     * Method to save the file to the original path
     * For Windows users -> C:\User
     * For Linux and Apple users -> /home/user/
     *
     * @param nameOfFile - The input of the user to name the file
     */
    private void saveFile(String nameOfFile) {
        File file = new File(System.getProperty("user.home") + File.separator + "Database of Insured Persons" + File.separator + nameOfFile);
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(database.listOfAllPersons());
            bw.flush();
            System.out.println("File created");
        } catch (Exception e) {
            System.out.println("Error has occurred");
        }
    }

    /**
     * Method to save the file to the new path
     * For Windows users -> C:\User\...
     * For Linux and Apple users -> /home/user/...
     *
     * @param absolutePath - The input of the user to change tha path to the file
     */
    private void saveFileNewPath(String absolutePath) {
        File file = new File(absolutePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(database.listOfAllPersons());
            bw.flush();
            System.out.println("File created");
        } catch (Exception e) {
            System.out.println("Error has occurred");
        }
    }

    /**
     * Method to change the path to the file
     *
     * @param - inputNameOfFile
     * @return - File with changed path
     */
    private File userPath(String inputNameOfFile) {
        System.out.println("You can choose the path, create a new folders, examples:\n" +
                "For Windows -> C:\\[user]\\[new name of folder]\\\n" +
                "For Linux and Apple -> /home/[user]/[name of folder]\n" +
                "Enter your path:\n");
        String newPathInput = sc.nextLine().trim();
        File file = new File(newPathInput, inputNameOfFile);  // New instance of file with the new path

        return file;
    }

    /**
     * Handles the existence of a file if already exists
     *
     * @param file - represents the file to be checked and potentially modified
     */
    private void handleFileExistence(File file) {
        try {
            while (Files.exists(file.toPath())) {
                System.out.println("The file already exists. Do you wish to specify a new path? YES / NO");
                String inputNewPath = sc.nextLine().trim().toLowerCase();

                if (inputNewPath.contains("yes")) {
                    file = userPath(file.getName());
                } else if (inputNewPath.contains("no")) {
                    break;
                } else {
                    System.out.println("You must enter either 'YES' or 'NO'");
                }
            }

            if (!Files.exists(file.toPath())) {
                saveFileNewPath(file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error has occurred");
        }
    }
}

