package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee(1, "Kovács János", "Fejlesztő");

        assertEquals(1, employee.getId());
        assertEquals("Kovács János", employee.getName());
        assertEquals("Fejlesztő", employee.getJobTitle());
    }

    @Test
    void testEmployeeGetters() {
        Employee employee = new Employee(2, "Nagy Edit", "Projektmenedzser");

        assertAll("Employee properties",
                () -> assertEquals(2, employee.getId()),
                () -> assertEquals("Nagy Edit", employee.getName()),
                () -> assertEquals("Projektmenedzser", employee.getJobTitle())
        );
    }

    @Test
    void testEmployeeWithEmptyName() {
        Employee employee = new Employee(3, "", "Gyakornok");

        assertEquals("", employee.getName());
        assertEquals("Gyakornok", employee.getJobTitle());
    }

    @Test
    void testEmployeeWithHungarianCharacters() {
        Employee employee = new Employee(4, "Szabó Éva", "Üzemeltető");

        assertEquals(4, employee.getId());
        assertEquals("Szabó Éva", employee.getName());
        assertEquals("Üzemeltető", employee.getJobTitle());
    }

    @Test
    void testEmployeeWithSpecialJobTitle() {
        Employee employee = new Employee(5, "Tóth István", "Rendszergazda");

        assertEquals(5, employee.getId());
        assertEquals("Tóth István", employee.getName());
        assertEquals("Rendszergazda", employee.getJobTitle());
    }
}