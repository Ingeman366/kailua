package utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UITest {
    private ByteArrayInputStream testIn;


    @Test
	@DisplayName("Test only valid integer type input")
    void TestReadInteger() {
        testIn = new ByteArrayInputStream("1\n3\n5\n0\n".getBytes());
        System.setIn(testIn);
        UI ui = new UI();

        assertEquals(1, ui.readInteger());
        assertEquals(3, ui.readInteger());
        assertEquals(5, ui.readInteger());
        assertEquals(0, ui.readInteger());
    } // End of test

    @Test
	@DisplayName("Test only valid letters in line input")
    void TestReadLine() {
        testIn = new ByteArrayInputStream("Hello World\nJames Is A Mover\nChilling\nZero\n".getBytes());
        System.setIn(testIn);
        UI ui = new UI();

        assertEquals("Hello World", ui.readLine());
        assertEquals("James Is A Mover", ui.readLine());
        assertEquals("Chilling", ui.readLine());
        assertEquals("Zero", ui.readLine());
    } // End of test

    @Test
	@DisplayName("Test only valid letters in word input")
    void TestReadNext() {
        testIn = new ByteArrayInputStream("Hello     \nJames     \nChilling   \nZero       \n".getBytes());
        System.setIn(testIn);
        UI ui = new UI();

        assertEquals("Hello", ui.readNext());
        assertEquals("James", ui.readNext());
        assertEquals("Chilling", ui.readNext());
        assertEquals("Zero", ui.readNext());
    } // End of test

    @Test
	@DisplayName("Test only valid boolean type input")
    void TestReadBoolean() {
        testIn = new ByteArrayInputStream("1\n2\n".getBytes());
        System.setIn(testIn);
        UI ui = new UI();

        assertTrue(ui.readBoolean());
        assertFalse(ui.readBoolean());
    } // End of test

    @Test
	@DisplayName("Test only valid double type input")
    void TestReadDouble() {
        testIn = new ByteArrayInputStream("1.00\n3.23\n5.21\n0.0\n".getBytes());
        System.setIn(testIn);
        UI ui = new UI();
        assertEquals(1.00, ui.readDouble());
        assertEquals(3.23, ui.readDouble());
        assertEquals(5.21, ui.readDouble());
        assertEquals(0.0, ui.readDouble());
    } // End of test

    @Test
	@DisplayName("Test only valid Date input")
    void TestReadDate() {
        testIn = new ByteArrayInputStream(("""
                1993
                10
                11
                2020
                03
                02
                2023
                18
                23
                2020
                12
                12
                """).getBytes());
        System.setIn(testIn);
        UI ui = new UI();

        assertEquals(LocalDate.parse("1993-10-11"), ui.readDate());
        assertEquals(LocalDate.parse("2020-03-02"), ui.readDate());
        assertThrows(DateTimeException.class, () -> LocalDate.parse("2023-18-23"));
        assertEquals(LocalDate.parse("2020-12-12"), ui.readDate());
    } // End of test

    @Test
	@DisplayName("Test returned value of InvalidNumberInput")
    void TestInvalidNumberInput() {
        UI ui = new UI();
        assertEquals("Invalid input was given", ui.invalidChoiceInput());
    } // End of test
}