package bb.example.services.iohandler;

/**
 * The class handle outputs of the program.
 *
 * @author pollib
 */
@FunctionalInterface
public interface OutputHandler {
    void print(String message);
}
