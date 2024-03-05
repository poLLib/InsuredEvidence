package bb.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Scanner;

/**
 * The class interacts with the user.
 *
 * @author pollib
 */
public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final PersonDatabase database;

    public UserInterface(PersonDatabase database) {
        this.database = database;
    }

    public String menuSelection(String inputOption) {

        int inputSelection = enterNumber(inputOption);
        String result = "";

        switch (inputSelection) {
            case 1 -> result = addInsured();
            case 2 -> result = displayAllInsured();
            case 3 -> result = displayInsured();
            case 4 -> result = modifyInsured();
            case 5 -> result = deleteInsured();
            case 6 -> result = createFile();
            case 7 -> result = "Goodbye";
            default -> result = "Enter number from 1 to 7";
        }
        return result;
    }

    private int enterNumber(String input) {
        while (isValidNumber(input) || input.isEmpty()) {
            System.out.println("Invalid number. Please enter number.");
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    private String addInsured() {
        String name = enterUserName("name");
        String surname = enterUserName("surname");
        String tel = enterPhoneNumber();
        int age = enterAge();

        database.addPerson(new PersonImpl(name, surname, tel, age)); // Add the insured person to the database

        return "A new insured person has been added.";
    }

    private String enterUserName(String name) {
        while (true) {
            System.out.printf("Enter the %s:\n", name);
            String userName = scanner.nextLine().trim();

            if (doesContainsChar(userName)) {
                return userName;
            } else {
                System.out.println("You must enter only letters of the alphabet");

            }
        }
    }

    private boolean doesContainsChar(String string) {
        return (!containsInvalidCharacters(string) && !string.isEmpty());
    }

    private boolean containsInvalidCharacters(String input) {
        return !input.chars().allMatch(letter -> Character.isAlphabetic(letter) || letter == ' ' || letter == '-');
    }

    private String enterPhoneNumber() {
        while (true) {
            System.out.println("Enter the phone number (9 digits without the area code):");
            String userPhoneNumber = scanner.nextLine().trim();

            if (doesContainNineDigitString(userPhoneNumber)) {
                return userPhoneNumber;
            }
        }
    }

    private boolean doesContainNineDigitString(String string) {
        if (isValidNumber(string) && !string.isEmpty()) {
            System.out.println("The phone number cannot contain letters or special characters and must be 9-digit number");
        } else if (string.length() != 9) {
            System.out.println("Enter a 9-digit number");
        } else {
            return true;
        }
        return false;
    }

    private boolean isValidNumber(String input) {
        return !input.chars().allMatch(Character::isDigit);
    }

    private int enterAge() {
        System.out.println("Enter the age:");
        while (true) {
            int age = enterNumber(scanner.nextLine().trim());

            if (doesContainRangeOfDigits(age)) {
                return age;
            }
        }
    }

    private boolean doesContainRangeOfDigits(int age) {
        if (!(age > 0 && age <= 100)) {
            System.out.println("The age must be in the range of 1-100");
            return false;
        }
        return true;
    }

    private String displayAllInsured() {
        Collection<PersonI> persons = database.listOfAllPersons();
        if (persons.isEmpty()) {
            return "No insured individuals are recorded in the database";
        }
        StringBuilder result = new StringBuilder();
        for (PersonI person : persons) {
            result.append(person).append("\n");
        }
        return result.toString();
    }

    private String displayInsured() {

        System.out.println("Enter the name or surname:");
        String inputNameSurname = scanner.nextLine().trim();

        Collection<PersonI> foundPersons = database.findSpecificPerson(inputNameSurname);
        StringBuilder result = new StringBuilder();
        for (PersonI person : foundPersons) {
            result.append(person).append("\n");
        }

        if (foundPersons.isEmpty()) {
            return "This name is not recorded in the database";
        }
        return result.toString();
    }

    private String modifyInsured() {
        System.out.println("Enter the ID of the person you are looking for:");
        String input = scanner.nextLine();
        int inputId = enterNumber(input);
        PersonI foundPerson = database.findById(inputId);

        if (foundPerson == null) {
            return "The database does not contain the ID you entered";
        } else {
            System.out.println(foundPerson);
            String newName = enterUserName("new name");
            String newSurname = enterUserName("new surname");
            String newTel = enterPhoneNumber();

            database.editPerson(inputId, newName, newSurname, newTel);
            return "The person has been modified to:\n" + foundPerson;
        }
    }

    private String deleteInsured() {
        database.listOfAllPersons();
        System.out.println("Enter the ID of the person you would like to delete");
        String input = scanner.nextLine().trim();
        int inputId = enterNumber(input);

        if (database.deletePerson(inputId)) {
            return "The insured individual has been deleted";
        } else {
            return "Person with the given ID not found";
        }
    }

    private String createFile() {
        System.out.println("Enter a name of the file");
        String fileName = scanner.nextLine().trim() + ".txt";
        System.out.println("Enter a name of the folder where you would like to save the file");
        String userDirectory = scanner.nextLine().trim();
        Path filePath = Paths.get(userDirectory, fileName);
        Collection<PersonI> persons = database.listOfAllPersons();

        try {
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, "Database of insured persons:" + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            for (PersonI person : persons) {
                Files.writeString(filePath, String.format("%s, %s, %s, %d" + System.lineSeparator(), person.getName(), person.getSurname(), person.getPhone(), person.getAge()), StandardOpenOption.APPEND);
            }
            return "The file was created";
        } catch (IOException e) {
            return "An error occurred while creating the file: " + e.getMessage();
        }
    }
}