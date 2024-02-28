package bb.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonDatabaseTest {
    @Mock
    PersonI mockedPerson;
    @InjectMocks
    PersonDatabase database;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPerson() {
        when(mockedPerson.getId()).thenReturn(1);
        database.addPerson("John", "Doe", "123456789", 25);

        verify(mockedPerson).setName("John");
        verify(mockedPerson).setSurname("Doe");
        verify(mockedPerson).setPhone("123456789");
        verify(mockedPerson).setAge(25);

        PersonI addedPerson = database.findById(1);

        assertThat(addedPerson).isNotNull();
        assertThat(addedPerson.getName()).isEqualTo("John");
        assertThat(addedPerson.getSurname()).isEqualTo("Doe");
        assertThat(addedPerson.getPhone()).isEqualTo("123456789");
        assertThat(addedPerson.getAge()).isEqualTo(25);
    }
}