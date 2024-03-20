package bb.example;

import bb.example.services.IOHandler.InputHandler;
import bb.example.services.IOHandler.OutputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

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
    @DisplayName("")
    void shouldEnterNumber() {
        when(inputHandler.getInput()).thenReturn("0");

    }
}