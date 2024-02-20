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
    private final SortedMap<Integer, PersonImpl> personsMap;
    private int nextId = 1;

    public PersonDatabase() {
        personsMap = new TreeMap<>();
    }

    /**
     * Adding a new insured individual into the database. Each new initialization increases the ID.
     *
     */
    public void addPerson(String name, String surname, String phone, int age) {
        PersonImpl newPerson = new PersonImpl(nextId++, name, surname, phone, age);
        personsMap.put(newPerson.getId(), newPerson);
    }

    public Collection<PersonI> listOfAllPersons() {
        return Collections.unmodifiableCollection(personsMap.values());
    }

    /**
     * Looks up the insured individual by the name or part of it.
     *
     * @param inputName a name or part of name to be asked for
     * @return LinkedHashMap of searched persons
     */
    public Collection<PersonImpl> findSpecificPerson(String inputName) {
        return personsMap.values().stream()
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
        PersonI person = findById(id);
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
        return personsMap.remove(inputId) != null;
    }

    /**
     * Looks up a person by ID.
     *
     * @param id the ID of recorded person to be found
     * @return the person with given ID or (returns null if not found)
     */
    public PersonI findById(int id) {
        return personsMap.get(id);
    }

    public static class PersonImpl implements PersonI {
        private String name;
        private String surname;
        private String phone;
        private int age;
        private final int id;

        public PersonImpl(int id, String name, String surname, String phone, int age) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.phone = phone;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String newName) {
            this.name = newName;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Insured person: " + name + " " + surname + ", phone: " + phone + ", age: " + age + ", ID-" + id;
        }
    }
}
