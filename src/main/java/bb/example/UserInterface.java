package bb.example;

import java.io.BufferedWriter;
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
    private final Scanner sc = new Scanner(System.in);
    private final PersonDatabase database;

    public UserInterface(PersonDatabase database) {
        this.database = database;
    }

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

    private void addInsured() {
        String name = enterUserName("name");
        String surname = enterUserName("surname");
        String tel = enterPhoneNumber();
        int age = enterAge();

        database.addPerson(name, surname, tel, age); // Add the insured person to the database
        System.out.println("A new insured person has been added.");
    }

    private String enterUserName(String name) {
        while (true) {
            System.out.printf("Enter the %s:\n", name);
            String userName = sc.nextLine().trim();

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
            String userPhoneNumber = sc.nextLine().trim();

            if (doesContainNineDigitString(userPhoneNumber)) {
                return userPhoneNumber;
            }
        }
    }

    private boolean doesContainNineDigitString(String string) {
        if (!isValidNumber(string) && !string.isEmpty()) {
            System.out.println("The phone number cannot contain letters or special characters and must be 9-digit number");
        } else if (string.length() != 9) {
            System.out.println("Enter a 9-digit number");
        } else {
            return true;
        }
        return false;
    }

    private boolean isValidNumber(String input) {
        return input.chars().allMatch(Character::isDigit);
    }

    private int enterAge() {
        System.out.println("Enter the age:");
        while (true) {
            int age = enterNumber();

            if (doesContainRangeOfDigits(age)) {
                return age;
            }
        }
    }

    private int enterNumber() {
        while (true) {
            String inputNumber = sc.nextLine().trim();

            if (isValidNumber(inputNumber) && !inputNumber.isEmpty()) {
                return Integer.parseInt(inputNumber);
            } else {
                System.out.println("Invalid number");
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

    private void displayAllInsured() {
        Collection<PersonI> persons = database.listOfAllPersons();
        if (persons.isEmpty()) {
            System.out.println("No insured individuals are recorded in the database");
        }
        persons.forEach(System.out::println);
    }

    private void displayInsured() {

        System.out.println("Enter the name or surname:");
        String inputNameSurname = sc.nextLine().trim();

        Collection<PersonDatabase.PersonImpl> foundPersons = database.findSpecificPerson(inputNameSurname);
        foundPersons.forEach(System.out::println);

        if (foundPersons.isEmpty()) {
            System.out.println("This name is not recorded in the database");
        }
    }

    private void modifyInsured() {
        System.out.println("Enter the ID of the person you are looking for:");
        int inputId = enterNumber();
        PersonI foundPerson = database.findById(inputId);

        if (foundPerson == null) {
            System.out.println("The database does not contain the ID you entered");
        } else {
            System.out.println(foundPerson);
            String newName = enterUserName("new name");
            String newSurname = enterUserName("new surname");
            String newTel = enterPhoneNumber();

            database.editPerson(inputId, newName, newSurname, newTel);
            System.out.println("The person has been modified to:\n" + foundPerson);
        }
    }

    private void deleteInsured() {
        database.listOfAllPersons();
        System.out.println("\nEnter the ID of the person you would like to delete");
        int inputId = enterNumber();

        if (database.deletePerson(inputId)) {
            System.out.println("The insured individual has been deleted");
        } else {
            System.out.println("Person with the given ID not found");
        }
    }

    private void createFile() {
        System.out.println("Enter a name of the file");
        String fileName = sc.nextLine().trim() + ".txt";
        System.out.println("Enter a path of the folder where you would like to save the file");
        String userPath = sc.nextLine().trim();
        Path filePath = Paths.get(userPath, fileName);
        Collection<PersonI> persons = database.listOfAllPersons();

        try (BufferedWriter w = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE)) {
            for (PersonI person : persons) {
                w.write(String.format("%s, %s, %s, %d%n", person.getName(), person.getSurname(), person.getPhone(), person.getAge()));
            }
            System.out.println("The file was created");
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

}