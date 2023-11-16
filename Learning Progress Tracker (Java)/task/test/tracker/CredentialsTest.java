package tracker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CredentialsTest {

    @ParameterizedTest
    @ValueSource(strings = {"Ann Doe ann@doe.com", "Jean-Claude O'Neill j.cld@m.work.com", "Robert Jemison Van de Graaff robert.g@xyz.to"})
    void goodCredentials(String input) {
        Student student = new Student(input);
        assertEquals(input, student.getFirstName() + " " + student.getLastName() + " " + student.getEmail());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "help", " "})
    void badCredentials(String input) {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new Student(input));
        assertEquals(String.format(Messages.ADD_ERROR.getMessage(), "credentials"), thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A Doe ann@doe.com", "J-0 Claude j.cld@m.work.com", "123 Bob realdeal@yeah.yeah"})
    void credentialsWithBadFirstName(String input) {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new Student(input));
        assertEquals(String.format(Messages.ADD_ERROR.getMessage(), "first name"), thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ann D ann@doe.com", "Jean-Claude B2 j.cld@m.work.com", "Bob - realdeal@yeah.yeah"})
    void credentialsWithBadLastName(String input) {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new Student(input));
        assertEquals(String.format(Messages.ADD_ERROR.getMessage(), "last name"), thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ann Doe doe @doe.com", "Jean-Claude O'Neill j*c!l&dm.work.com", "John Ronald Reuel Tolkien writer.com"})
    void credentialsWithBadEmail(String input) {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new Student(input));
        assertEquals(String.format(Messages.ADD_ERROR.getMessage(), "email"), thrown.getMessage());
    }


    @ParameterizedTest
    @CsvSource({"'Ann Doe user@doe.com', 'John Doe user@doe.com'"})
    void credentialsWithDuplicateEmail(String firstInput, String secondInput) {
        Journal journal = new Journal();
        assertTrue(journal.addStudent(firstInput));
        assertFalse(journal.addStudent(secondInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ann Doe ann@doe.com", "Jean-Claude O'Neill j.cld@m.work.com", "Robert Jemison Van de Graaff robert.g@xyz.to"})
    void checkStudentIdAdded(String input) {
        Journal journal = new Journal();
        assertTrue(journal.addStudent(input));
    }


}
