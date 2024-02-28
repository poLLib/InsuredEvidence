package bb.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDatabaseTest {
    @Mock
    PersonI mockedPerson;
    @Mock
    Collection<PersonI> mockedDatabase;
    @InjectMocks
    PersonDatabase database;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addingNewPersonIntoDatabaseTest() {
        database.addPerson("John", "Doe", "123456789", 25);
        mockedPerson = database.findById(1);

        assertThat(mockedPerson).isNotNull();
        assertThat(mockedPerson.getName()).isEqualTo("John");
        assertThat(mockedPerson.getSurname()).isEqualTo("Doe");
        assertThat(mockedPerson.getPhone()).isEqualTo("123456789");
        assertThat(mockedPerson.getAge()).isEqualTo(25);
    }

    @Test
    void sizeOfDatabaseTest() {
        database.addPerson("John", "Doe", "123456789", 25);
        database.addPerson("Jim", "Jarmush", "753159654", 49);
        database.addPerson("Stanley", "Kubrick", "357159456", 60);
        database.addPerson("Tim", "Burton", "789654123", 33);

        mockedDatabase = database.listOfAllPersons();

        assertThat(mockedDatabase.size()).isEqualTo(4);
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
        mockedPerson = database.findById(1);

        assertThat(mockedPerson.getId()).isEqualTo(1);
        assertThat(mockedPerson.getName()).isEqualTo("Honza");
        assertThat(mockedPerson.getSurname()).isEqualTo("Řezník");
        assertThat(mockedPerson.getPhone()).isEqualTo("735845657");
    }

    @Test
    void deletingPersonTest() {
        database.addPerson("John", "Doe", "123456789", 25);
        database.addPerson("Jim", "Jarmush", "753159654", 49);
        database.addPerson("Stanley", "Kubrick", "357159456", 60);

        database.deletePerson(1);
        mockedDatabase = database.listOfAllPersons();
        mockedPerson = database.findById(2);

        assertThat(mockedDatabase.size()).isEqualTo(2);
        assertThat(mockedPerson.getId()).isEqualTo(2);
        assertThat(mockedPerson.getName()).isEqualTo("Jim");

    }
}