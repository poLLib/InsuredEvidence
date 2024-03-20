package bb.example;

import bb.example.services.IOHandler.InputHandler;
import bb.example.services.IOHandler.OutputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserInterfaceTest {
    @Mock
    InputHandler inputHandler;
    @Mock
    OutputHandler outputHandler;
    UserInterface userInterface;

    @BeforeEach
    void setUp() {
        userInterface = new UserInterface(new PersonDatabase(), inputHandler, outputHandler);
    }

    @Test
    @DisplayName("Should return \"Enter number from 1 to 7\" when user input is not number from 1 to 7 in menu")
    void shouldReturnMustBeNumberInRangeWhenInputIsOutOfBoundInMenuTest() {
        when(inputHandler.getInput())
                .thenReturn("0", "8", "7");
        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler, times(2)).print("Enter number from 1 to 7");
    }

    @Test
    @DisplayName("Should return \"Invalid number. Please enter number.\" when user input is not number")
    void shouldReturnInvalidNumberWhenInputIsLetterInMenuTest() {
        when(inputHandler.getInput())
                .thenReturn("input", " ", "", "7");
        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler, times(3)).print("Invalid number. Please enter number.");
    }

    @Test
    @DisplayName("Should return String of successful added person when user input is 1")
    void shouldReturnAddedPersonWhenInputOneAndPersonGivenTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Doe", "123456789", "25")
                .thenReturn("7");
        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("A new insured person has been successfully added.");
    }

    @Test
    @DisplayName("Should return String must have only letters when user input is not letters in name")
    void shouldReturnOnlyLettersWhenInputIsNotLetterInNameTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "123", "John", "", " ", "Doe", "123456789", "25")
                .thenReturn("7");
        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler, times(3)).print("You must enter only letters of the alphabet");
    }

    @Test
    @DisplayName("Should return String must have only digits when input does not have in phone number")
    void shouldReturnOnlyDigitsWhenInputIsNotDigitsInPhoneNumberTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Doe", " ", "", "abc", "123456789", "25")
                .thenReturn("7");
        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler, times(3)).print("The phone number cannot contain letters or special characters and must be 9-digit number");
    }

    @Test
    @DisplayName("Should return String range of age when input is out of bound")
    void shouldReturnAgeRangeWhenInputOutTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Doe", "123456789", "0", "101")
                .thenReturn("7");
        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler, times(2)).print("The age must be in the range of 1-100");
    }

    @Test
    @DisplayName("Should return String must have 9 digits when input more or less in phone number")
    void shouldReturnMustHaveNineDigitsWhenInputIsMoreOrLessInPhoneNumberTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Doe", "0", "8", "10", "123456789", "25")
                .thenReturn("7");
        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler, times(3)).print("Enter a 9-digit number");
    }

    @Test
    @DisplayName("Should return String of persons in database")
    void shouldReturnPersonsStringTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Doe", "123456789", "25")
                .thenReturn("2")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("Insured person: John Doe, phone: 123456789, age: 25, ID-1\n");
    }

    @Test
    @DisplayName("Should return String of no person when empty database")
    void shouldReturnNoPersonStringWhenEmptyTest() {
        when(inputHandler.getInput())
                .thenReturn("2")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("No insured individuals are recorded in the database");
    }

    @Test
    @DisplayName("Should return String of found persons")
    void shouldReturnFoundPersonStringTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Don", "123456789", "25")
                .thenReturn("1", "Homer", "Simpson", "987654321", "52")
                .thenReturn("3", "on")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("""
                Insured person: John Don, phone: 123456789, age: 25, ID-1
                Insured person: Homer Simpson, phone: 987654321, age: 52, ID-2
                """);
    }

    @Test
    @DisplayName("Should return String of no person found")
    void shouldReturnNoFoundPersonStringWhenNoNameFoundTest() {
        when(inputHandler.getInput())
                .thenReturn("3", "John")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("This name is not recorded in the database");
    }

    @Test
    @DisplayName("Should return String edited person")
    void shouldReturnEditedPersonTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Doe", "123456789", "25")
                .thenReturn("4", "1")
                .thenReturn("Homer", "Simpson", "987654321")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("The person has been modified to:\n" +
                "Insured person: Homer Simpson, phone: 987654321, age: 25, ID-1");
    }

    @Test
    @DisplayName("Should return String no person found")
    void shouldReturnNoPersonFoundTestWhenNoIdFoundTest() {
        when(inputHandler.getInput())
                .thenReturn("4", "1")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("The database does not contain the ID you entered");
    }

    @Test
    @DisplayName("Should return String person deleted")
    void shouldReturnPersonDeletedStringTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Doe", "123456789", "25")
                .thenReturn("5", "1")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("The insured individual has been deleted");
    }

    @Test
    @DisplayName("Should return String no person found to delete")
    void shouldReturnNoFoundPersonDeletedStringWhenNoFoundIdTest() {
        when(inputHandler.getInput())
                .thenReturn("5", "1")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("Person with the given ID not found");
    }

    @Test
    @DisplayName("Should return String file created")
    void shouldReturnFileCreatedStringTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Don", "123456789", "25")
                .thenReturn("6", "file", "persons")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("The file was created");
    }

    @Test
    @DisplayName("Should return String file created")
    void shouldReturnIOExceptionTest() {
        when(inputHandler.getInput())
                .thenReturn("1", "John", "Don", "123456789", "25")
                .thenReturn("6", "file", "persons")
                .thenReturn("7");

        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("The file was created");
    }

    @Test
    @DisplayName("Should return \"Goodbye\" when user input is 7")
    void shouldExitAndReturnGoodbyeWhenInputSevenTest() {
        when(inputHandler.getInput()).thenReturn("7");
        userInterface.menuSelectionLoop(inputHandler);

        verify(outputHandler).print("Goodbye");
        verifyNoMoreInteractions(inputHandler);
        verifyNoMoreInteractions(outputHandler);
    }
}