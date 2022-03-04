package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Simple tests for the Round class
public class RoundTest {

    Round tr;

    @Test
    public void roundMethodsTest() {
        tr = new Round();
        assertEquals(0, tr.getCurrentRound());

        tr.incrementRound();
        assertEquals(1, tr.getCurrentRound());
        tr.incrementRound();
        assertEquals(2, tr.getCurrentRound());
        tr.decrementRound();
        assertEquals(1, tr.getCurrentRound());
        tr.decrementRound();
        assertEquals(0, tr.getCurrentRound());

        tr.setCurrentRound(10);
        assertEquals(10, tr.getCurrentRound());
        tr.setCurrentRound(10000);
        assertEquals(10000, tr.getCurrentRound());
    }
}
