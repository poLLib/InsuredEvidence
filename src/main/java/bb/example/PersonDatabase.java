package bb.example;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A database for searching and storing insured individuals.
 *
 * @author pollib
 */
public class PersonDatabase {
    private final SortedMap<Integer, Person> personsMap;

    private int nextId = 1;

    public PersonDatabase() {
        personsMap = Collections.synchronizedSortedMap(new TreeMap<>());
    }

    /**
     * Adding a new insured individual into the database. Each new initialization increases the ID.
     *
     */
    public void addPerson(String name, String surname, String phone, int age) {
        Person newPerson = new Person(nextId++, name, surname, phone, age);
        personsMap.put(newPerson.id(), newPerson);
    }

    public Collection<Person> listOfAllPersons() {
        return Collections.unmodifiableCollection(personsMap.values());
    }

    /**
     * Looks up the insured individual by the name or part of it.
     *
     * @param inputName a name or part of name to be asked for
     * @return LinkedHashMap of searched persons
     */
    public Collection<Person> findSpecificPerson(String inputName) {
        return personsMap.values().stream()
                .filter(person -> person.name().contains(inputName) || person.surname().contains(inputName))
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
        Person person = findById(id);
        Person editedPerson = new Person(person.id(), newName, newSurname, newPhone, person.age());
        personsMap.replace(person.id(), editedPerson);
    }

    /**
     * Deletes the insured individual by ID.
     *
     * @param inputId the ID of the person to be deleted
     * @return true if the person was deleted
     */
    public boolean deletePerson(int inputId) {
        return personsMap.remove(inputId) != null;
    }

    /**
     * Looks up a person by ID.
     *
     * @param id the ID of recorded person to be found
     * @return the person with given ID or (returns null if not found)
     */
    public Person findById(int id) {
        return personsMap.get(id);
    }
}
