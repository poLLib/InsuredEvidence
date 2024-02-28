package bb.example;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDatabaseTest {

    PersonDatabase database;

    @Test
    void addingNewPersonIntoDatabaseTest() {
        database.addPerson("John", "Doe", "123456789", 25);
        PersonI addedPerson = database.findById(1);

        assertThat(addedPerson).isNotNull();
        assertThat(addedPerson.getName()).isEqualTo("John");
        assertThat(addedPerson.getSurname()).isEqualTo("Doe");
        assertThat(addedPerson.getPhone()).isEqualTo("123456789");
        assertThat(addedPerson.getAge()).isEqualTo(25);
    }

    @Test
    void sizeOfDatabaseTest() {
        database.addPerson("John", "Doe", "123456789", 25);
        database.addPerson("Jim", "Jarmush", "753159654", 49);
        database.addPerson("Stanley", "Kubrick", "357159456", 60);
        database.addPerson("Tim", "Burton", "789654123", 33);

        Collection<PersonI> listPersons = database.listOfAllPersons();

        assertThat(listPersons.size()).isEqualTo(4);
    }

    @Test
    void searchingForPersonInDatabaseByPartOfNameOrSurnameStringTest() {
        database.addPerson("John", "Doe", "123456789", 25);
        database.addPerson("Jim", "Jarmush", "753159654", 49);
        database.addPerson("Stanley", "Kubrick", "357159456", 60);
        database.addPerson("Tim", "Burton", "789654123", 33);
        database.addPerson("Luke", "Bimbo", "789654123", 33);

        Collection<PersonDatabase.PersonImpl> foundPersons = database.findSpecificPerson("im");

        assertThat(foundPersons.size()).isEqualTo(3);
    }

    @Test
    void editingNameSurnameAndPhoneNumberString() {
        database.addPerson("John", "Doe", "123456789", 25);

        database.editPerson(1, "Honza", "Řezník", "735845657");
        PersonI editedPerson = database.findById(1);

        assertThat(editedPerson.getId()).isEqualTo(1);
        assertThat(editedPerson.getName()).isEqualTo("Honza");
        assertThat(editedPerson.getSurname()).isEqualTo("Řezník");
        assertThat(editedPerson.getPhone()).isEqualTo("735845657");
    }

    @Test
    void deletingPersonTest() {
        database.addPerson("John", "Doe", "123456789", 25);
        database.addPerson("Jim", "Jarmush", "753159654", 49);
        database.addPerson("Stanley", "Kubrick", "357159456", 60);

        database.deletePerson(1);
        Collection<PersonI> databaseAfterDelete = database.listOfAllPersons();
        PersonI personIdTwo = database.findById(2);

        assertThat(databaseAfterDelete.size()).isEqualTo(2);
        assertThat(personIdTwo.getId()).isEqualTo(2);
        assertThat(personIdTwo.getName()).isEqualTo("Jim");

    }
}