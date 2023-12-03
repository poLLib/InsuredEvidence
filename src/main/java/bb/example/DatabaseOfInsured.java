package bb.example;

import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as a database for searching and storing insured individuals in an ArrayList.
 *
 * @author pollib
 */

public class DatabaseOfInsured {
    private final ArrayList<InsuredPerson> insuredPersons;
    private int nextId = 1;

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
        InsuredPerson newPerson = new InsuredPerson(name, surname, phone, age);
        newPerson.setId(nextId);
        nextId++;
        insuredPersons.add(newPerson);
    }

    /**
     * Method returns all insured individuals in the database
     *
     * @return - List of insured persons
     */
    public List<InsuredPerson> listOfAllPersons() {
        return new ArrayList<>(insuredPersons);
    }

    /**
     * Method finds specific insured individual
     *
     * @param inputName - specify which name or part of name the method should ask for
     */
    public List<InsuredPerson> findSpecificPerson(String inputName) {
        ArrayList<InsuredPerson> foundPerson = new ArrayList<>();

        // Loop for searching for a match in the user's input with names and last names stored in the database
        for (InsuredPerson insuredPerson : insuredPersons) {
            if (insuredPerson.getName().contains(inputName) || insuredPerson.getSurname().contains(inputName)) {
                foundPerson.add(insuredPerson);
            }
        }
        return foundPerson;
    }

    /**
     * Method for modifying the details of a specific insured individual
     *
     * @param id         - enter the ID of the person to modify
     * @param newName    - new name
     * @param newSurname - new last name
     * @param newPhone   - new phone number
     */
    public void editPerson(int id, String newName, String newSurname, String newPhone) {
        InsuredPerson person = findById(id);
        person.setName(newName);
        person.setSurname(newSurname);
        person.setPhone(newPhone);
    }

    /**
     * Method for deleting a specific insured individual by ID
     *
     * @param inputId - enter the exact ID of the person
     */
    public boolean deletePerson(int inputId) {
        // Loop for searching for a match in the user's input ID's stored in the database
        for (InsuredPerson insuredPerson : insuredPersons) {
            if (inputId == insuredPerson.getId()) {
                insuredPersons.remove(insuredPerson);
                return true; // Deleting successful
            }
        }
        return false; // Person was not found
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
