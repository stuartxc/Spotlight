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
    public void retrievePlayerByNameTest() {
        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");

        plTest.addPlayer(testPlayer1);
        assertEquals(testPlayer1, plTest.retrievePlayerByName("Test1"));

        plTest.addPlayer(testPlayer2);
        assertEquals(testPlayer2, plTest.retrievePlayerByName("Test2"));
        assertEquals(testPlayer1, plTest.retrievePlayerByName("Test1"));

        assertEquals("ERROR: PLAYER_NOT_FOUND", plTest.retrievePlayerByName("Test3").getName());
    }

    @Test
    public void retrievePlayerByNumTest() {
        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");

        plTest.addPlayer(testPlayer1);
        assertEquals(testPlayer1, plTest.retrievePlayerByNum(1));

        plTest.addPlayer(testPlayer2);
        assertEquals(testPlayer2, plTest.retrievePlayerByNum(2));
        assertEquals(testPlayer1, plTest.retrievePlayerByNum(1));

        assertEquals("ERROR: PLAYER_NOT_FOUND", plTest.retrievePlayerByNum(3).getName());
    }

    @Test
    public void getAllPlayerNamesTest() {
        assertEquals("", plTest.getAllPlayerNames());

        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");
        plTest.addPlayer(testPlayer1);
        plTest.addPlayer(testPlayer2);

        assertEquals("Test1, Test2", plTest.getAllPlayerNames());
    }

    @Test
    public void setAllRespondingTest() {
        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");
        testPlayer1.setStatus("Spotlight");
        testPlayer2.setStatus("Judge");

        assertEquals("Spotlight", testPlayer1.getStatus());
        assertEquals("Judge", testPlayer2.getStatus());

        plTest.addPlayer(testPlayer1);
        plTest.addPlayer(testPlayer2);
        plTest.setAllResponding();

        assertEquals("Responding", testPlayer1.getStatus());
        assertEquals("Responding", testPlayer2.getStatus());
    }

    @Test
    public void clearAllResponsesTest() {
        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");
        testPlayer1.setResponse("Ice cream in my underwear");
        testPlayer2.setResponse("Using a deodorant stick as mayonnaise");

        assertEquals("Ice cream in my underwear", testPlayer1.getResponse());
        assertEquals("Using a deodorant stick as mayonnaise", testPlayer2.getResponse());

        plTest.addPlayer(testPlayer1);
        plTest.addPlayer(testPlayer2);
        plTest.clearAllResponses();

        assertEquals("", testPlayer1.getResponse());
        assertEquals("", testPlayer2.getResponse());
    }

    @Test
    public void haveAllRespondedTest() {
        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");
        testPlayer1.setResponse("Ice cream in my underwear");

        plTest.addPlayer(testPlayer1);
        plTest.addPlayer(testPlayer2);
        assertFalse(plTest.haveAllResponded());

        testPlayer2.setResponse("Using a deodorant stick as mayonnaise");
        assertTrue(plTest.haveAllResponded());

        Player testPlayer3 = new Player("Test3");
        testPlayer3.setStatus("Judge");
        plTest.addPlayer(testPlayer3);
        // Player3 should not be considered, since they are the judge
        assertTrue(plTest.haveAllResponded());
    }

    @Test
    public void getAllStillRespondingTest() {
        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");
        testPlayer1.setResponse("Ice cream in my underwear");

        plTest.addPlayer(testPlayer1);
        plTest.addPlayer(testPlayer2);
        assertEquals("Test2", plTest.getAllStillResponding());

        testPlayer2.setResponse("Using a deodorant stick as mayonnaise");
        assertEquals("", plTest.getAllStillResponding());

        plTest.clearAllResponses();
        testPlayer2.setResponse("Using a deodorant stick as mayonnaise");

        assertEquals("Test1", plTest.getAllStillResponding());
    }

    @Test
    public void getAllStillRespondingJudgeTest() {
        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");
        testPlayer1.setResponse("Ice cream in my underwear");
        Player testPlayer3 = new Player("Test3");
        testPlayer3.setStatus("Judge");
        plTest.addPlayer(testPlayer3);


        // Player3 should not be considered, since they are the judge
        plTest.addPlayer(testPlayer1);
        plTest.addPlayer(testPlayer2);
        assertEquals("Test2", plTest.getAllStillResponding());

        testPlayer2.setResponse("Using a deodorant stick as mayonnaise");
        assertEquals("", plTest.getAllStillResponding());

        plTest.clearAllResponses();
        testPlayer2.setResponse("Using a deodorant stick as mayonnaise");

        assertEquals("Test1", plTest.getAllStillResponding());
    }

    @Test
    public void getAllPlayerResponsesTest() {
        assertEquals("", plTest.getAllPlayerResponses());

        // This method should only be called when all Players have a response already set.
        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");
        testPlayer1.setResponse("Ice cream in my underwear");
        testPlayer2.setResponse("Using a deodorant stick as mayonnaise");
        plTest.addPlayer(testPlayer1);
        plTest.addPlayer(testPlayer2);

        assertEquals("Test1: Ice cream in my underwear. Test2: Using a deodorant stick as mayonnaise",
                plTest.getAllPlayerResponses());

        Player testPlayer3 = new Player("Test3");
        testPlayer3.setStatus("Judge");
        plTest.addPlayer(testPlayer3);
        // should ignore the judge
        assertEquals("Test1: Ice cream in my underwear. Test2: Using a deodorant stick as mayonnaise",
                plTest.getAllPlayerResponses());
    }

    @Test
    public void getAllPlayerPointsTest() {
        assertEquals("", plTest.getAllPlayerPoints());

        Player testPlayer1 = new Player("Test1");
        Player testPlayer2 = new Player("Test2");
        testPlayer1.addPoint();
        plTest.addPlayer(testPlayer1);
        plTest.addPlayer(testPlayer2);

        assertEquals("Test1: 1. Test2: 0", plTest.getAllPlayerPoints());

        testPlayer2.addPoint();
        assertEquals("Test1: 1. Test2: 1", plTest.getAllPlayerPoints());
    }
}