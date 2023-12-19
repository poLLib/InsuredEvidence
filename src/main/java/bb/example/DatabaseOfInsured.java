package bb.example;

import java.util.ArrayList;
import java.util.List;

/**
 * A database for searching and storing insured individuals in an ArrayList.
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
     * Adding a new insured individual into the database.
     *
     * @param name of the insured individual
     * @param surname of the insured individual
     * @param phone of the insured individual
     * @param age of the insured individual
     */
    public void addPerson(String name, String surname, String phone, int age) {
        InsuredPerson newPerson = new InsuredPerson(name, surname, phone, age);
        newPerson.setId(nextId);
        nextId++;
        insuredPersons.add(newPerson);
    }

    /**
     * Gives a List of all insured individuals in the database.
     *
     * @return List of insured persons
     */
    public List<InsuredPerson> listOfAllPersons() {
        return new ArrayList<>(insuredPersons);
    }

    /**
     * Looks up the insured individual by the name or part of it.
     *
     * @param inputName a name or part of name to be asked for
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
     * Modifies the details of a specific insured individual.
     *
     * @param id the ID of the person to be modified
     * @param newName the new name of the modifying person
     * @param newSurname the new surname of the modifying person
     * @param newPhone the new phone number of the modifying person
     */
    public void editPerson(int id, String newName, String newSurname, String newPhone) {
        InsuredPerson person = findById(id);
        person.setName(newName);
        person.setSurname(newSurname);
        person.setPhone(newPhone);
    }

    /**
     * Deletes the insured individual by ID.
     *
     * @param inputId the ID of the person to be deleted
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
     * Looks up a person by ID.
     *
     * @param id the ID of recorded person to be found
     * @return the person with given ID or (returns null if not found)
     */
    public InsuredPerson findById(int id) {
        // Loop for searching for a match in the user's input with ID's stored in the database
        for (InsuredPerson person : insuredPersons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }
}
