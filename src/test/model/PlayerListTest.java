package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerListTest {

    PlayerList plTest;

    @BeforeEach
    public void runBefore() {
        plTest = new PlayerList();
    }

    @Test
    public void constructorTest() {
        assertEquals(0, plTest.getAmtPlayers());
    }

    @Test
    public void addPlayerTest() {
        plTest.addPlayer(new Player("Test1"));
        assertEquals(1, plTest.getAmtPlayers());

        plTest.addPlayer(new Player("Test2"));
        assertEquals(2, plTest.getAmtPlayers());
    }

    @Test
    public void getAllPlayerNamesTest() {
        plTest.addPlayer(new Player("Test1"));
        plTest.addPlayer(new Player("Test2"));

        assertEquals("Test1, Test2", plTest.getAllPlayerNames());
    }

}