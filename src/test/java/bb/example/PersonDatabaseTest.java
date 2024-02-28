package bb.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonDatabaseTest {

    @Mock
    PersonI person;

    PersonDatabase database;

    @BeforeEach
    void beforeEach() {
        database = new PersonDatabase();
    }

    @Test
    @DisplayName("Should add person to database")
    void shouldAddPersonTest() {
        PersonI addedPerson = database.findById(1);
        assertThat(addedPerson).isNull();

        database.addPerson("John", "Doe", "123456789", 25);

        addedPerson = database.findById(1);

        assertThat(addedPerson).isNotNull();
        assertThat(addedPerson.getName()).isEqualTo("John");
        assertThat(addedPerson.getSurname()).isEqualTo("Doe");
        assertThat(addedPerson.getPhone()).isEqualTo("123456789");
        assertThat(addedPerson.getAge()).isEqualTo(25);

        verifyNoMoreInteractions(person);
    }
}