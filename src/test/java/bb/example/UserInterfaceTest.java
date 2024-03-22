package bb.example;

import bb.example.services.iohandler.InputHandler;
import bb.example.services.iohandler.OutputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserInterfaceTest {
    @Mock
    InputHandler inputHandler;
    @Mock
    OutputHandler outputHandler;
    @Mock
    PersonDatabase database;
    UserInterface userInterface;

    @BeforeEach
    void setUp() {
        userInterface = new UserInterface(database, inputHandler, outputHandler);
    }

    @Nested
    class TestingNumberInput {
        @Test
        @DisplayName("Should return \"Enter number from 1 to 7\" when user input is not number from 1 to 7 in menu")
        void shouldReturnMustBeNumberInRangeWhenInputIsOutOfBoundInMenuTest() {
            when(inputHandler.getInput())
                    .thenReturn("0", "8", "7");
            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(outputHandler, times(2)).print("Enter number from 1 to 7");
        }

        @Test
        @DisplayName("Should return \"Invalid number. Please enter number.\" when user input is not number")
        void shouldReturnInvalidNumberWhenInputIsLetterInMenuTest() {
            when(inputHandler.getInput())
                    .thenReturn("input", " ", "", "7");
            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(outputHandler, times(3)).print("Invalid number. Please enter number.");
            verifyNoMoreInteractions(inputHandler);
        }
    }

    @Nested
    class TestingAddPerson {
        @Test
        @DisplayName("Should return String must have only letters when user input is not letters in name")
        void shouldReturnOnlyLettersWhenInputIsNotLetterInNameTest() {
            when(inputHandler.getInput())
                    .thenReturn("1", "123", "John", "", " ", "Doe", "123456789", "25")
                    .thenReturn("7");
            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).addPerson("John", "Doe", "123456789", 25);
            verify(outputHandler, times(3)).print("You must enter only letters of the alphabet");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String of successful added person when user input is 1")
        void shouldReturnAddedPersonWhenInputOneAndPersonGivenTest() {
            when(inputHandler.getInput())
                    .thenReturn("1", "John", "Doe", "123456789", "25")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            assertThat(database).isNotNull();

            verify(database).addPerson("John", "Doe", "123456789", 25);
            verify(outputHandler).print("A new insured person has been successfully added.");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String must have only digits when input does not have in phone number")
        void shouldReturnOnlyDigitsWhenInputIsNotDigitsInPhoneNumberTest() {
            when(inputHandler.getInput())
                    .thenReturn("1", "John", "Doe", " ", "", "abc", "123456789", "25")
                    .thenReturn("7");
            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).addPerson("John", "Doe", "123456789", 25);
            verify(outputHandler, times(3)).print("The phone number cannot contain letters or special characters and must be 9-digit number");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String range of age when input is out of bound")
        void shouldReturnAgeRangeWhenInputOutTest() {
            when(inputHandler.getInput())
                    .thenReturn("1", "John", "Doe", "123456789", "151", "150")
                    .thenReturn("7");
            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).addPerson("John", "Doe", "123456789", 150);
            verify(outputHandler).print("The age must be in the range of 0-150");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String must have 9 digits when input more or less in phone number")
        void shouldReturnMustHaveNineDigitsWhenInputIsMoreOrLessInPhoneNumberTest() {
            when(inputHandler.getInput())
                    .thenReturn("1", "John", "Doe", "0", "1111111111", "10", "123456789", "25")
                    .thenReturn("7");
            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).addPerson("John", "Doe", "123456789", 25);
            verify(outputHandler, times(3)).print("Enter a 9-digit number");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }
    }

    @Nested
    class TestingListOfPersons {
        @Test
        @DisplayName("Should return String of persons in database")
        void shouldReturnPersonsStringTest() {
            when(database.listOfAllPersons())
                    .thenReturn(Collections.singletonList(new Person(1, "John", "Doe", "123456789", 25)));
            when(inputHandler.getInput())
                    .thenReturn("2")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).listOfAllPersons();
            verify(outputHandler).print("Insured person: John Doe, phone: 123456789, age: 25, ID-1");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String of no person when empty database")
        void shouldReturnNoPersonStringWhenEmptyTest() {
            when(inputHandler.getInput())
                    .thenReturn("2")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).listOfAllPersons();
            verify(outputHandler).print("No insured individuals are recorded in the database");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }
    }

    @Nested
    class TestingFindSpecificPerson {
        @Test
        @DisplayName("Should return String of found persons")
        void shouldReturnFoundPersonStringTest() {
            List<Person> persons = new ArrayList<>();
            persons.add(new Person(1, "John", "Doe", "123456789", 25));
            persons.add(new Person(2, "Homer", "Simpson", "987654321", 52));

            when(database.findSpecificPerson("on"))
                    .thenReturn(persons);
            when(inputHandler.getInput())
                    .thenReturn("3", "on")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).findSpecificPerson("on");
            verify(outputHandler).print("Insured person: John Doe, phone: 123456789, age: 25, ID-1\n" +
                    "Insured person: Homer Simpson, phone: 987654321, age: 52, ID-2");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String of no person found")
        void shouldReturnNoFoundPersonStringWhenNoNameFoundTest() {
            when(database.findSpecificPerson("John")).thenReturn(Collections.emptyList());
            when(inputHandler.getInput())
                    .thenReturn("3", "John")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(outputHandler).print("This name is not recorded in the database");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

    }

    @Nested
    class TestingEditPerson {
        @Test
        @DisplayName("Should return String edited person")
        void shouldReturnEditedPersonTest() {
            Person person = new Person(1, "John", "Doe", "123456789", 25);
            Person editedPerson = new Person(1, "Homer", "Simpson", "987654321", 25);

            when(database.findById(1)).thenReturn(person).thenReturn(editedPerson);
            when(inputHandler.getInput())
                    .thenReturn("4", "1") // Simulate user selecting option to modify a person
                    .thenReturn("Homer", "Simpson", "987654321") // Simulate user input for new person details
                    .thenReturn("7"); // Simulate user exiting the menu

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database, times(2)).findById(1);
            verify(database).editPerson(1, "Homer", "Simpson", "987654321");
            verify(outputHandler).print("The person has been modified to:\n" +
                    "Insured person: Homer Simpson, phone: 987654321, age: 25, ID-1");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String no person found")
        void shouldReturnNoPersonFoundTestWhenNoIdFoundTest() {
            when(database.findById(anyInt())).thenReturn(null);
            when(inputHandler.getInput())
                    .thenReturn("4", "1")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database, times(1)).findById(1);
            verify(outputHandler).print("The database does not contain the ID you entered");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

    }

    @Nested
    class TestingDeletePerson {
        @Test
        @DisplayName("Should return String person deleted")
        void shouldReturnPersonDeletedStringTest() {
            when(database.deletePerson(1)).thenReturn(true);
            when(inputHandler.getInput())
                    .thenReturn("5", "1")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).listOfAllPersons();
            verify(database).deletePerson(1);
            verify(outputHandler).print("The insured individual has been deleted");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String no person found to delete")
        void shouldReturnNoFoundPersonDeletedStringWhenNoFoundIdTest() {
            when(database.deletePerson(1)).thenReturn(false);
            when(inputHandler.getInput())
                    .thenReturn("5", "1")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(database).listOfAllPersons();
            verify(database).deletePerson(1);
            verify(outputHandler).print("Person with the given ID not found");
            verifyNoMoreInteractions(database);
            verifyNoMoreInteractions(inputHandler);
        }
    }

    @Nested
    class TestingCreateFile {
        @Test
        @DisplayName("Should return String file created")
        void shouldReturnFileCreatedStringTest() {
            when(inputHandler.getInput())
                    .thenReturn("6", "file", "persons")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(outputHandler).print("The file was created");
            verifyNoMoreInteractions(inputHandler);
        }

        @Test
        @DisplayName("Should return String file created")
        void shouldReturnIOExceptionTest() {
            when(inputHandler.getInput())
                    .thenReturn("1", "John", "Don", "123456789", "25")
                    .thenReturn("6", "file", "persons")
                    .thenReturn("7");

            userInterface.menuSelectionLoop(inputHandler, outputHandler);

            verify(outputHandler).print("The file was created");
            verifyNoMoreInteractions(inputHandler);
        }
    }

    @Test
    @DisplayName("Should return \"Goodbye\" when user input is 7")
    void shouldExitAndReturnGoodbyeWhenInputSevenTest() {
        when(inputHandler.getInput()).thenReturn("7");
        userInterface.menuSelectionLoop(inputHandler, outputHandler);

        verify(outputHandler).print("Goodbye");
        verifyNoMoreInteractions(inputHandler);
    }
}