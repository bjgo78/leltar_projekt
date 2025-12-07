package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PCTest {

    @Test
    void testPCCreation() {
        PC pc = new PC(1, "Lenovo", "ThinkPad T14", "Kovács János");

        assertEquals(1, pc.getId());
        assertEquals("Lenovo", pc.getBrand());
        assertEquals("ThinkPad T14", pc.getVersion());
        assertEquals("Kovács János", pc.getOwnerName());
    }

    @Test
    void testPCGetters() {
        PC pc = new PC(2, "HP", "EliteBook 840", "Nagy Edit");

        assertAll("PC properties",
                () -> assertEquals(2, pc.getId()),
                () -> assertEquals("HP", pc.getBrand()),
                () -> assertEquals("EliteBook 840", pc.getVersion()),
                () -> assertEquals("Nagy Edit", pc.getOwnerName())
        );
    }

    @Test
    void testPCWithNullOwner() {
        PC pc = new PC(3, "Dell", "Latitude 5420", null);

        assertEquals(3, pc.getId());
        assertEquals("Dell", pc.getBrand());
        assertEquals("Latitude 5420", pc.getVersion());
        assertNull(pc.getOwnerName());
    }

    @Test
    void testPCWithEmptyOwnerName() {
        PC pc = new PC(4, "Asus", "ExpertBook B1", "");

        assertEquals(4, pc.getId());
        assertEquals("Asus", pc.getBrand());
        assertEquals("ExpertBook B1", pc.getVersion());
        assertEquals("", pc.getOwnerName());
    }

    @Test
    void testPCWithHungarianCharacters() {
        PC pc = new PC(5, "Apple", "MacBook Pro", "Szabó Éva");

        assertEquals(5, pc.getId());
        assertEquals("Apple", pc.getBrand());
        assertEquals("MacBook Pro", pc.getVersion());
        assertEquals("Szabó Éva", pc.getOwnerName());
    }

    @Test
    void testPCWithSpecialVersion() {
        PC pc = new PC(6, "MSI", "Titan GT77", "Tóth István");

        assertEquals(6, pc.getId());
        assertEquals("MSI", pc.getBrand());
        assertEquals("Titan GT77", pc.getVersion());
        assertEquals("Tóth István", pc.getOwnerName());
    }

    @Test
    void testPCWithComplexOwnerName() {
        PC pc = new PC(7, "Acer", "Swift 3", "Dr. Horváth Béla PhD");

        assertEquals(7, pc.getId());
        assertEquals("Acer", pc.getBrand());
        assertEquals("Swift 3", pc.getVersion());
        assertEquals("Dr. Horváth Béla PhD", pc.getOwnerName());
    }
}