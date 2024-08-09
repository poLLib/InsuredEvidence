package bb.example.services;

import bb.example.Person;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The class provides service for UI.
 *
 * @author pollib
 */
public class Utilities {


    /**
     * Creates a readable list of people below each other.
     *
     * @param persons Collection of persons for the list
     * @return String of list of the people
     */
    public static String buildPersonsList(Collection<Person> persons) {
        return persons.stream().map(Person::toString).collect(Collectors.joining("\n"));
    }

    /**
     * Saves the database into the text file.
     *
     * @param fileName      Name of the file to be saved
     * @param userDirectory Directory where the file will be saved, default is home directory, user can adjust it
     * @param persons       Database of the people
     * @return String whether the creation was successful or not
     */
    public static String saveFileTxt(String fileName, String userDirectory, Collection<Person> persons) {
        Path filePath = Paths.get(System.getProperty("user.home") + File.separator + userDirectory, fileName);

        try {
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, "Database of insured persons:" + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            for (Person person : persons) {
                Files.writeString(filePath, String.format("%s, %s, %s, %d" + System.lineSeparator(), person.name(), person.surname(), person.phone(), person.age()), StandardOpenOption.APPEND);
            }
            return "The file was created";
        } catch (
                IOException e) {
            return "An error occurred while creating the file: " + e.getMessage();
        }
    }
}
