package bb.example;
import java.util.ArrayList;

/**
  This class serves as a database for searching and storing insured individuals in an ArrayList.

  @author pollib
 */

public class DatabaseOfInsured {
    private final ArrayList<InsuredPerson> insuredPersons;

    // Creating an instance of an ArrayList representing the database
    public DatabaseOfInsured() {
        insuredPersons = new ArrayList<>();
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
    public void listOfAllPersons() {
        // Loop for printing all insured individuals
        for (InsuredPerson insuredPerson : insuredPersons) {
            System.out.println(insuredPerson);
        }
        // Output if the database is empty
        if (insuredPersons.isEmpty()) {
            System.out.println("No insured individuals are recorded in the database");
        }
    }

    /**
     * Method finds and prints a specific insured individual
     *
     * @param inputName - specify which input the method should ask for
     */
    public void findSpecificPerson(String inputName) {
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
     * @param id           - enter the ID of the person to modify
     * @param newName      - new name
     * @param newSurname   - new last name
     * @param newPhone     - new phone number
     */
    public void editPerson(int id, String newName, String newSurname, String newPhone) {
        InsuredPerson person = findById(id);
        person.setName(newName);
        person.setSurname(newSurname);
        person.setPhone(newPhone);
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

    /**
     * Method for searching a person by ID
     *
     * @param id - enter the ID of recorded person
     * @return - InsuredPerson
     */
    public InsuredPerson findById(int id) {
        InsuredPerson insuredPerson = null;
        // Loop for searching for a match in the user's input with ID's stored in the database
        for (InsuredPerson person : insuredPersons) {
            if (person.getId() == id) {
                insuredPerson = person;
                System.out.println(person);
            }
        }
        return insuredPerson;
    }


}
