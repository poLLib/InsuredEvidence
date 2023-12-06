package bb.example;

import java.nio.charset.Charset;
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
 * - Ending the program
 * - Validation of ID
 * - Validation of letters and numbers
 * - Validation of age
 * - Validation of phone number
 *
 * @author pollib
 */
public class UserInterface {
    private final Scanner sc = new Scanner(System.in, Charset.defaultCharset()); // Creating an instance for user input
    private final DatabaseOfInsured database;

    // Creating an instance of the database
    public UserInterface() {
        database = new DatabaseOfInsured();
    }

    /**
     * Menu selection
     *
     */
    public void menuSelectionLoop() {
        boolean end = false;
        String errorMessage = "Enter a number from 1 to 7";
        while (!end) {
            System.out.println("""

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
                    Enter the action number:""");
            try {
                int input = Integer.parseInt(sc.nextLine().trim());
                if (input >= 1 && input <= 7) {
                    Options selectedOption = Options.values()[input - 1];

                    switch (selectedOption) {
                        case ONE -> addInsured();
                        case TWO -> displayAllInsured();
                        case THREE -> displayInsured();
                        case FOUR -> modifyInsured();
                        case FIVE -> deleteInsured();
/*
                        case SIX -> createFile();
*/
                        case SEVEN -> {
                            System.out.printf(endProgram());
                            end = true;
                        }
                        default -> System.out.println("Unknown action");
                    }
                } else {
                    System.out.println(errorMessage); // Output for incorrect command, out of range of 1 - 7
                }
            } catch (Exception e) {
                System.out.println(errorMessage); // Output for incorrect command, null or symbols
            }
        }
    }

    /**
     * Enum representing different options for managing insured records.
     * Each option is associated with a numeric value and a corresponding action.
     */
    enum Options {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN
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
        int id;
        boolean validationOfId = false;
        while (!validationOfId) {
            System.out.println("Enter the ID of the person you are looking for:");
            String inputId = sc.nextLine().trim();

            validationOfId = validateNumbers(inputId); // Validate numbers through the ASCII table

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
     * Deletion of an insured person by entering the ID
     */
    public void deleteInsured() {

        if (database.deletePerson(validateId())) {
            System.out.println("The insured individual has been deleted");
        } else {
            System.out.println("Person with the given ID not found");
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
    public String validateLetters(String name) {
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
     * Method for validating numbers
     *
     * @param input - String to be checked for letters by each character
     * @return - true = the character is a number
     */
    public boolean validateNumbers(String input) {
        boolean validation = true;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                validation = false;
            }
        }
        return validation;
    }

    /**
     * Method for validation of ID's
     * The numbers and if the ID exists in the database
     *
     * @return - ID (int)
     */

    private int validateId() {
        int id = 0;
        boolean validation = false;
        while (!validation) {
            System.out.println("Enter the ID of the person you want to remove from database:");
            String inputId = sc.nextLine().trim();

            try {
                id = Integer.parseInt(inputId);
                validation = true;

            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format. Please enter a valid number.");
            }
        }
        return id;
    }

    /**
     * Validation of numbers in a String
     * Validation of a 9-digit number
     * Removing spaces between digits
     *
     * @return phone number (String)
     */
    public String validateNumberOfPhone() {
        boolean numberValidation = false;
        String inputPhoneNumber = "";

        while (!numberValidation) {
            System.out.println("Enter the phone number (without the area code):");
            inputPhoneNumber = sc.nextLine().replace(" ", "").trim();

            numberValidation = validateNumbers(inputPhoneNumber);

            if (!numberValidation) {
                System.out.println("The phone number cannot contain letters or special characters");
            }
            if (!(inputPhoneNumber.length() == 9)) {
                numberValidation = false;
                System.out.println("Enter a 9-digit number");
            }
        }
        return inputPhoneNumber;
    }

    /**
     * Method for validating numbers in age
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

            if (validateNumbers(inputAge)) {
                age = Integer.parseInt(inputAge);
                ageValidation = true;
            }

            // Validation of the range 0-100
            if (!ageValidation) {
                System.out.println("Age cannot contain letters or special characters");
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
}

/**
 * The process of creating a new *.txt file of the insured persons either to the original location or one of user's choice
 * Using Java.io
 * <p>
 * Method to save the file to the original path
 * For Windows users -> C:\User
 * For Linux and Apple users -> /home/user/
 *
 * @param nameOfFile - The input of the user to name the file
 * <p>
 * Method to save the file to the new path
 * For Windows users -> C:\User\...
 * For Linux and Apple users -> /home/user/...
 * @param absolutePath - The input of the user to change tha path to the file
 * <p>
 * Method to change the path to the file
 * @param - inputNameOfFile
 * @return - File with changed path
 * <p>
 * Handles the existence of a file if already exists
 * @param file - represents the file to be checked and potentially modified
 * <p>
 * Method to save the file to the original path
 * For Windows users -> C:\User
 * For Linux and Apple users -> /home/user/
 * @param nameOfFile - The input of the user to name the file
 * <p>
 * Method to save the file to the new path
 * For Windows users -> C:\User\...
 * For Linux and Apple users -> /home/user/...
 * @param absolutePath - The input of the user to change tha path to the file
 * <p>
 * Method to change the path to the file
 * @param - inputNameOfFile
 * @return - File with changed path
 * <p>
 * Handles the existence of a file if already exists
 * @param file - represents the file to be checked and potentially modified
 *//*
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

    *//**
 * Method to save the file to the original path
 * For Windows users -> C:\User
 * For Linux and Apple users -> /home/user/
 *
 * @param nameOfFile - The input of the user to name the file
 *//*
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

    *//**
 * Method to save the file to the new path
 * For Windows users -> C:\User\...
 * For Linux and Apple users -> /home/user/...
 *
 * @param absolutePath - The input of the user to change tha path to the file
 *//*
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

    *//**
 * Method to change the path to the file
 *
 * @param - inputNameOfFile
 * @return - File with changed path
 * <p>
 * Handles the existence of a file if already exists
 * @param file - represents the file to be checked and potentially modified
 *//*
    private File userPath(String inputNameOfFile) {
        System.out.println("You can choose the path, create a new folders, examples:\n" +
                "For Windows -> C:\\[user]\\[new name of folder]\\\n" +
                "For Linux and Apple -> /home/[user]/[name of folder]\n" +
                "Enter your path:\n");
        String newPathInput = sc.nextLine().trim();
        File file = new File(newPathInput, inputNameOfFile);  // New instance of file with the new path

        return file;
    }

    *//**
 * Handles the existence of a file if already exists
 *
 * @param file - represents the file to be checked and potentially modified
 *//*
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

*/