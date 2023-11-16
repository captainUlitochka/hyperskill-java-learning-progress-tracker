package tracker;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ScoresTest {

    @ParameterizedTest
    @CsvSource({"'Ann Doe 123@123.com', '1000 1 2 3 4'"})
    @Disabled
    void addValidScores(String student, String score) {
        Journal journal = new Journal();
        journal.addStudent(student);
        assertTrue(journal.addPoints(score));
    }
}
