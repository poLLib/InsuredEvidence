package bb.example.services;

public class Validators {
    public static boolean isValidNumber(String input) {
        return !input.isEmpty() && !input.chars().allMatch(Character::isDigit);
    }

    public static boolean doesContainsChar(String string) {
        return (!containsInvalidCharacters(string) && !string.isEmpty());
    }

    public static boolean containsInvalidCharacters(String input) {
        return !input.chars().allMatch(letter -> Character.isAlphabetic(letter) || letter == ' ' || letter == '-');
    }

    public static boolean doesContainRangeOfDigits(int age) {
        return (!(age > 0 && age <= 100));
    }
}
