package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads PlayerList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayerList readPlayers() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayerList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PlayerList from JSON object and returns it
    private PlayerList parsePlayerList(JSONObject jsonObject) {

        PlayerList players = new PlayerList();
        addPlayers(players, jsonObject);
        return players;
    }

    // MODIFIES: players
    // EFFECTS: parses the players from JSON object and adds them to PlayerList
    private void addPlayers(PlayerList players, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(players, nextPlayer);
        }
    }

    // MODIFIES: players
    // EFFECTS: parses Player from JSON object and adds it to PlayerList
    private void addPlayer(PlayerList players, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String status = jsonObject.getString("status");
        int points = jsonObject.getInt("points");
        int playerNum = jsonObject.getInt("playerNum");
        String response = jsonObject.getString("response");

        Player player = new Player(name);
        player.setStatus(status);
        player.setPoints(points);
        player.setPlayerNum(playerNum);
        player.setResponse(response);
        players.addPlayer(player);
    }

    // EFFECTS: reads PromptLibrary from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PromptLibrary readPrompts() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePromptLibrary(jsonObject);
    }

    // EFFECTS: parses PromptLibrary from JSON object and returns it
    private PromptLibrary parsePromptLibrary(JSONObject jsonObject) {

        PromptLibrary prompts = new PromptLibrary();
        addPrompts(prompts, jsonObject);
        return prompts;
    }

    // MODIFIES: prompts
    // EFFECTS: parses the prompts from JSON object and adds them to PromptLibrary
    private void addPrompts(PromptLibrary prompts, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("prompts");
        for (Object json : jsonArray) {
            JSONObject nextPrompt = (JSONObject) json;
            addPrompt(prompts, nextPrompt);
        }
    }

    // MODIFIES: prompts
    // EFFECTS: parses Prompt from JSON object and adds it to PromptLibrary
    private void addPrompt(PromptLibrary prompts, JSONObject jsonObject) {
        String text = jsonObject.getString("text");
        int promptNum = jsonObject.getInt("promptNum");
        boolean used = jsonObject.getBoolean("used");

        Prompt prompt = new Prompt(text);
        prompt.setPromptNum(promptNum);
        prompt.setUsed(used);
        prompts.addPrompt(prompt);
    }

    // EFFECTS: reads Round from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Round readRound() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRound(jsonObject);
    }

    // EFFECTS: parses Round from JSON object and returns it
    private Round parseRound(JSONObject jsonObject) {
        Round round = new Round();

        round.setCurrentRound(jsonObject.getInt("currentRound"));

        return round;
    }

}