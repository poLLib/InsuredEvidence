package bb.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDatabaseTest {
    PersonDatabase database;

    @BeforeEach
    void beforeEach() {
        database = new PersonDatabase();
    }

    @Test
    @DisplayName("Should add person to database")
    void shouldAddPersonTest() {
        Person person = database.findById(1);
        assertThat(person).isNull();

        database.addPerson("John", "Doe", "123456789", 25);

        person = database.findById(1);

        assertThat(person).isNotNull();
        assertThat(person.id()).isEqualTo(1);
        assertThat(person.name()).isEqualTo("John");
        assertThat(person.surname()).isEqualTo("Doe");
        assertThat(person.phone()).isEqualTo("123456789");
        assertThat(person.age()).isEqualTo(25);
    }

    @Test
    @DisplayName("Should return Collection of persons")
    void shouldReturnPersonsCollectionTest() {
        Collection<Person> persons = database.listOfAllPersons();
        assertThat(persons).isEmpty();

        database.addPerson("John", "Doe", "123456789", 25);
        persons = database.listOfAllPersons();

        assertThat(persons).isNotEmpty();
        assertThat(persons.size()).isEqualTo(1);
        assertThat(persons).contains(database.findById(1));
    }

    @Test
    @DisplayName("Should find person in database")
    void shouldReturnCollectionPersonsByPartNameTest() {
        Collection<Person> persons = database.findSpecificPerson("oe");
        assertThat(persons).isEmpty();

        database.addPerson("John", "Doe", "123456789", 25);
        database.addPerson("John", "Doe", "123456789", 25);
        database.addPerson("Homer", "Simpson", "123456789", 25);
        persons = database.findSpecificPerson("oe");

        assertThat(persons).isNotEmpty();
        assertThat(persons.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should edit person in database")
    void shouldEditPersonTest() {
        Person person = database.findById(1);
        assertThat(person).isNull();

        database.addPerson("John", "Doe", "123456789", 25);
        database.editPerson(1, "Homer", "Simpson", "987654321");
        person = database.findById(1);

        assertThat(person).isNotNull();
        assertThat(person.id()).isEqualTo(1);
        assertThat(person.name()).isEqualTo("Homer");
        assertThat(person.surname()).isEqualTo("Simpson");
        assertThat(person.phone()).isEqualTo("987654321");
        assertThat(person.age()).isEqualTo(25);
    }

    @Test
    @DisplayName("Should delete person from database")
    void shouldDeletePersonTest() {
        Person person = database.findById(1);
        assertThat(person).isNull();

        database.addPerson("John", "Doe", "123456789", 25);
        person = database.findById(1);
        assertThat(person).isNotNull();

        database.deletePerson(1);
        person = database.findById(1);
        assertThat(person).isNull();
    }
}
