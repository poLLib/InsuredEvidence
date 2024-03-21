package bb.example.services;

/**
 * The class validates the user's input.
 *
 * @author pollib
 */
public class Validators {
    public static boolean isValidNumber(String input) {
        return !input.isBlank() && input.chars().allMatch(Character::isDigit);
    }

    public static boolean doesContainChar(String input) {
        return (input.chars().allMatch(letter -> Character.isAlphabetic(letter) || letter == ' ' || letter == '-') && !input.isBlank());
    }

    public static boolean isAgeValid(int age) {
        return (age >= 0 && age <= 150);
    }
}
