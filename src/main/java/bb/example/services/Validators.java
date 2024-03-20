package bb.example.services;

public class Validators {
    public static boolean isValidNumber(String input) {
        return !input.isBlank() && input.chars().allMatch(Character::isDigit);
    }

    public static boolean doesContainChar(String input) {
        return (input.chars().allMatch(letter -> Character.isAlphabetic(letter) || letter == ' ' || letter == '-') && !input.isBlank());
    }

    public static boolean doesContainRangeOfDigits(int age) {
        return (age > 0 && age <= 100);
    }
}
