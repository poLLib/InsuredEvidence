package bb.example.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ValidatorsTest {
    @Test
    @DisplayName("Should validate String \"123\" for digits")
    void shouldValidateStringDigitOneTwoThreeTest() {
        boolean isValid = Validators.isValidNumber("123");
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate String \"-123\" for digits")
    void shouldValidateStringDigitMinusOneTwoThreeTest() {
        boolean isValid = Validators.isValidNumber("-123");
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should validate String \"000\" for digits")
    void shouldValidateStringDigitTripleZerosTest() {
        boolean isValid = Validators.isValidNumber("000");
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should validate String \"abc\" for digits")
    void shouldValidateStringDigitAbcTest() {
        boolean isValid = Validators.isValidNumber("abc");
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should validate String \"1a1\" for digits")
    void shouldValidateStringDigitMixedStringTest() {
        boolean isValid = Validators.isValidNumber("1a1");
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should validate String \" \" for digits")
    void shouldValidateStringDigitWhiteSpaceTest() {
        boolean isValid = Validators.isValidNumber(" ");
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should validate String \"\" for digits")
    void shouldValidateStringDigitEmptyStringTest() {
        boolean isValid = Validators.isValidNumber("");
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should contains only Characters in String \"John\"")
    void shouldContainsCharactersInNameTest() {
        boolean doesContainChar = Validators.doesContainChar("Jonh");
        assertThat(doesContainChar).isTrue();
    }

    @Test
    @DisplayName("Should contains only Characters in String \"John Doe\"")
    void shouldContainsCharactersInNameWhiteSpaceTest() {
        boolean doesContainChar = Validators.doesContainChar("John Doe");
        assertThat(doesContainChar).isTrue();
    }

    @Test
    @DisplayName("Should contains only Characters in String \"John-Doe\"")
    void shouldContainsCharactersInNameHyphenTest() {
        boolean doesContainChar = Validators.doesContainChar("John-Doe");
        assertThat(doesContainChar).isTrue();
    }

    @Test
    @DisplayName("Should contains only Characters in String \"123\"")
    void shouldContainsCharactersInNumbersTest() {
        boolean doesContainChar = Validators.doesContainChar("123");
        assertThat(doesContainChar).isFalse();
    }

    @Test
    @DisplayName("Should contains only Characters in empty String \"\"")
    void shouldContainsCharactersInEmptyStringTest() {
        boolean doesContainChar = Validators.doesContainChar("");
        assertThat(doesContainChar).isFalse();
    }
}