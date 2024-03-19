package bb.example.services;

import bb.example.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;

public class Utilities {

    public static String buildPersonsList(Collection<Person> persons) {
        StringBuilder result = new StringBuilder();
        for (Person person : persons) {
            result.append(person).append("\n");
        }
        return result.toString();
    }

    public static String saveFileTxt(String fileName, String userDirectory, Collection<Person> persons) {
        Path filePath = Paths.get(userDirectory, fileName);

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
