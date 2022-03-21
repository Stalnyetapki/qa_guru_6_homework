package guru.qa;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс с простыми тестами")
public class SimpleTest {

    @Test
    @DisplayName("Ожидаемо зеленый тест")
    void simpleGreenTest() {
        assertTrue(3 > 2);
    }

    @Test
    @Disabled
    @DisplayName("Ожидаемо красный тест")
    void simpleRedTest() {
        assertTrue(3 < 2);
    }

    @Test
    @Disabled("bug: JIRA-12232")
    void simpleBrokenTest() {
        throw new IllegalStateException("Broken :(");
    }
}