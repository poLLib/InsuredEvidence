package bb.example;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * A database for searching and storing insured individuals in an LinkedHashMap.
 *
 * @author pollib
 */

public class DatabaseOfInsured {
    private final LinkedHashMap<Integer, InsuredPerson> insuredPersons;
    private int nextId = 1;

    // Creating an instance of an ArrayList representing the database
    public DatabaseOfInsured() {
        insuredPersons = new LinkedHashMap<>();
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
        insuredPersons.put(nextId, newPerson);
        nextId++;
    }

    /**
     * Gives a LinkedHashMap of all insured individuals in the database.
     *
     * @return LinkedHashMap of insured persons
     */
    public Collection<InsuredPerson> listOfAllPersons() {
        return Collections.unmodifiableCollection(insuredPersons.values());
    }

    /**
     * Looks up the insured individual by the name or part of it.
     *
     * @param inputName a name or part of name to be asked for
     * @return LinkedHashMap of searched persons
     */
    public Collection<InsuredPerson> findSpecificPerson(String inputName) {
        return insuredPersons.values().stream()
                .filter(person -> person.getName().contains(inputName) || person.getSurname().contains(inputName))
                .toList();
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
     * @return true if the person was deleted
     */
    public boolean deletePerson(int inputId) {
        // Loop for searching for a match in the user's input ID's stored in the database
        for (Integer idPerson : insuredPersons.keySet()) {
            if (idPerson == inputId) {
                insuredPersons.remove(idPerson);
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
        for (InsuredPerson person : insuredPersons.values()) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }
}
