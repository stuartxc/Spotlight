package persistence;


import model.Player;
import model.Prompt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlayer(String name, String status, int points, int playerNum, String response, Player player) {
        assertEquals(name, player.getName());
        assertEquals(status, player.getStatus());
        assertEquals(points, player.getPoints());
        assertEquals(playerNum, player.getPlayerNum());
        assertEquals(response, player.getResponse());
    }

    protected void checkPrompt(String text, int promptNum, boolean used, Prompt prompt) {
        assertEquals(text, prompt.getText());
        assertEquals(promptNum, prompt.getPromptNum());
        assertEquals(used, prompt.getUsed());
    }
}
