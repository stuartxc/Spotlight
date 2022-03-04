package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            PlayerList players = new PlayerList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testRoundWriterAndReader() {
        try {
            Round round = new Round();
            round.setCurrentRound(3);
            JsonWriter writer = new JsonWriter("./data/testReadAndWriteRound");
            writer.open();
            writer.writeRound(round);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReadAndWriteRound");
            round = reader.readRound();
            assertEquals(3, round.getCurrentRound());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyPlayersEmptyPrompts() {
        try {
            PlayerList players = new PlayerList();
            PromptLibrary prompts = new PromptLibrary();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlayers.json");
            JsonWriter writer2 = new JsonWriter("./data/testWriterEmptyPrompts.json");
            writer.open();
            writer.writePlayers(players);
            writer.close();
            writer2.open();
            writer2.writePrompts(prompts);
            writer2.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlayers.json");
            JsonReader reader2 = new JsonReader("./data/testWriterEmptyPrompts.json");
            players = reader.readPlayers();
            prompts = reader2.readPrompts();
            assertEquals(0, players.getAmtPlayers());
            assertEquals(0, prompts.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNormalPlayersNormalPrompts() {
        PlayerList players = new PlayerList();
        players.addPlayer(new Player("mowmow"));
        players.addPlayer(new Player("poohpooh"));

        PromptLibrary prompts = new PromptLibrary();
        prompts.addPrompt(new Prompt("What is [S]'s weirdest snack?"));
        prompts.addPrompt(new Prompt("Who is [S]'s worst enemy?"));

        JsonWriter writer = new JsonWriter("./data/testWriterNormalPlayers.json");
        JsonWriter writer2 = new JsonWriter("./data/testWriterNormalPrompts.json");

        try {
            writer.open();
            writer.writePlayers(players);
            writer.close();
            writer2.open();
            writer2.writePrompts(prompts);
            writer2.close();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
        normalPlayersAndPromptsHelper(players, prompts);
    }

    private void normalPlayersAndPromptsHelper(PlayerList players, PromptLibrary prompts) {
        try {
            JsonReader reader = new JsonReader("./data/testWriterNormalPlayers.json");
            JsonReader reader2 = new JsonReader("./data/testWriterNormalPrompts.json");

            players = reader.readPlayers();
            prompts = reader2.readPrompts();
            List<Player> pList = players.getAllPlayers();
            List<Prompt> pLibrary = prompts.getAllPrompts();

            assertEquals(2, pList.size());
            assertEquals(2, pLibrary.size());
            checkPlayer("mowmow", "Responder", 0, 1, "", pList.get(0));
            checkPlayer("poohpooh", "Responder", 0, 2, "", pList.get(1));
            checkPrompt("What is [S]'s weirdest snack?", 1, false, pLibrary.get(0));
            checkPrompt("Who is [S]'s worst enemy?", 2, false, pLibrary.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
