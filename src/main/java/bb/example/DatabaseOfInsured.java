package bb.example;
import java.util.ArrayList;

/*
  This class serves as a database for searching and storing insured individuals in an ArrayList.

  @author pollib
 */

/**
 * TO-DO-LIST DatabazePoj !!!!!!!!!!
 * * Modify upravPoj using setX()
 * Modify pridejPoj() to assign a unique ID to each new insured individual using a static method.
 * Modify smazPoj() method to delete only based on the ID number or date of creation.
 * Load/save to a file.
 */
public class DatabaseOfInsured {
    private final ArrayList<InsuredPerson> insuredPersons;

    public DatabaseOfInsured() {
        insuredPersons = new ArrayList<>();     // Creating an instance of an ArrayList representing the database
    }

    /**
     * Method adds a new insured individual to the database
     *
     * @param name    - name of the insured individual
     * @param surname - last name of the insured individual
     * @param phone   - phone number of the insured individual
     * @param age     - age of the insured individual
     */
    public void addPerson(String name, String surname, String phone, int age) {
        insuredPersons.add(new InsuredPerson(name, surname, phone, age));
    }

    /**
     * Method prints out all insured individuals in the database
     */
    public void writePerson() {
        // Loop for printing all insured individuals
        for (InsuredPerson insuredPerson : insuredPersons) {
            System.out.println(insuredPerson);
        }
        if (insuredPersons.isEmpty()) {
            System.out.println("No insured individuals are recorded in the database");
        }
    }

    /**
     * Method finds and prints a specific insured individual
     *
     * @param inputName - specify which input the method should ask for
     */
    public void findPerson(String inputName) {
        boolean found = false;
        // Loop for searching for a match in the user's input with names and last names stored in the database
        for (InsuredPerson insuredPerson : insuredPersons) {
            if (insuredPerson.getName().contains(inputName) || insuredPerson.getSurname().contains(inputName)) {
                System.out.println(insuredPerson);
                found = true;
            }
        }
        // Output if the database is empty or the searched insured individual is not recorded
        if (insuredPersons.isEmpty()) {
            System.out.println("The database is empty");
        } else if (!found) {
            System.out.println("This name is not recorded in the database");
        }
    }

    /**
     * Method for modifying the details of a specific insured individual
     * (CAUTION: watch for duplicate names)
     *
     * @param inputName    - the name being searched
     * @param inputSurname - the last name being searched
     * @param newName      - new name
     * @param newSurname   - new last name
     * @param newPhone     - new phone number
     */
    public void editPerson(String inputName, String inputSurname, String newName, String newSurname, String newPhone) {
        for (InsuredPerson insuredPerson : insuredPersons) {
            if (inputName.equals(insuredPerson.getName()) && inputSurname.equals(insuredPerson.getSurname())) {
                insuredPerson.setName(newName);
                insuredPerson.setSurname(newSurname);
                insuredPerson.setPhone(newPhone);
            }
        }
    }

    /**
     * Method for deleting a specific insured individual
     *
     * @param inputName    - enter the exact name
     * @param inputSurname - enter the exact last name
     */
    public void deletePerson(String inputName, String inputSurname) {
        // Loop for searching for a match in the user's input with names and last names stored in the database
        for (InsuredPerson insuredPerson : insuredPersons) {
            if (inputName.equalsIgnoreCase(insuredPerson.getName()) && inputSurname.equalsIgnoreCase(insuredPerson.getSurname())) {
                insuredPersons.remove(insuredPerson);
                System.out.println("The insured individual has been deleted");
            } else {
                System.out.println("The insured individual could not be found");
            }
        }
    }
}
