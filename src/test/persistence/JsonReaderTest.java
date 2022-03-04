package persistence;

import model.Player;
import model.PlayerList;
import model.Prompt;
import model.PromptLibrary;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testPlayerReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayerList players = reader.readPlayers();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testPlayerReaderEmptyPlayerList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayersEmptyPrompts.json");
        testEmptyPlayersHelper(reader);

        JsonReader reader2 = new JsonReader("./data/testReaderEmptyPlayersNormalPrompts.json");
        testEmptyPlayersHelper(reader2);
    }

    private void testEmptyPlayersHelper(JsonReader reader) {
        try {
            PlayerList players = reader.readPlayers();
            // assertEquals("My work room", wr.getName());
            assertEquals(0, players.getAmtPlayers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testPlayerReaderNormalPlayerList() {
        JsonReader reader = new JsonReader("./data/testReaderNormalPlayersEmptyPrompts.json");
        testNormalPlayersHelper(reader);

        JsonReader reader2 = new JsonReader("./data/testReaderNormalPlayersNormalPrompts.json");
        testNormalPlayersHelper(reader2);

    }

    private void testNormalPlayersHelper(JsonReader reader) {
        try {
            PlayerList players = reader.readPlayers();
            // assertEquals("My work room", wr.getName());
            List<Player> pList = players.getAllPlayers();
            assertEquals(2, pList.size());
            checkPlayer("mowmow", "Judge", 1,
                    1, "", pList.get(0));
            checkPlayer("poohpooh", "Responding", 3,
                    2, "I love you!", pList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testPromptReaderEmptyPromptLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayersEmptyPrompts.json");
        testEmptyPromptsHelper(reader);

        JsonReader reader2 = new JsonReader("./data/testReaderNormalPlayersEmptyPrompts.json");
        testEmptyPromptsHelper(reader2);
    }

    private void testEmptyPromptsHelper(JsonReader reader) {
        try {
            PromptLibrary prompts = reader.readPrompts();
            // assertEquals("My work room", wr.getName());
            assertEquals(0, prompts.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testPromptReaderNormalPromptLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayersNormalPrompts.json");
        testNormalPromptsHelper(reader);

        JsonReader reader2 = new JsonReader("./data/testReaderNormalPlayersNormalPrompts.json");
        testNormalPromptsHelper(reader2);
    }

    private void testNormalPromptsHelper(JsonReader reader) {
        try {
            PromptLibrary prompts = reader.readPrompts();
            // assertEquals("My work room", wr.getName());
            List<Prompt> pList = prompts.getAllPrompts();
            assertEquals(2, pList.size());
            checkPrompt("What is [S]'s most embarrassing memory?", 1, false, pList.get(0));
            checkPrompt("Who was [S]'s first crush?", 2, true, pList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
