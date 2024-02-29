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
        when(person.getId()).thenReturn(1);
        when(person.getName()).thenReturn("John");
        when(person.getSurname()).thenReturn("Doe");
        when(person.getPhone()).thenReturn("123456789");
        when(person.getAge()).thenReturn(25);

        PersonI foundPerson = database.findById(1);
        assertThat(foundPerson).isNull();

        database.addPerson(person);

        foundPerson = database.findById(1);

        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getId()).isEqualTo(1);
        assertThat(foundPerson.getName()).isEqualTo("John");
        assertThat(foundPerson.getSurname()).isEqualTo("Doe");
        assertThat(foundPerson.getPhone()).isEqualTo("123456789");
        assertThat(foundPerson.getAge()).isEqualTo(25);

        verify(person).setId(1);
        verifyNoMoreInteractions(person);
    }
}