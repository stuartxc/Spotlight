package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player playerTest;

    @BeforeEach
    public void runBefore() {
        playerTest = new Player("Test");
    }

    @Test
    public void constructorTest() {
        assertEquals("Test", playerTest.getName());
        assertEquals("Responder", playerTest.getStatus());
        assertEquals(0, playerTest.getPoints());
    }

    @Test
    public void addPointTest() {
        playerTest.addPoint();
        assertEquals(1, playerTest.getPoints());
    }

}
