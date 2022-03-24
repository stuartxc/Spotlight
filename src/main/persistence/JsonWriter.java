package persistence;

import model.PlayerList;
import model.PromptLibrary;
import model.Round;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter((destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of PlayerList to file
    public void writePlayers(PlayerList players) {
        JSONObject json = players.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of PromptLibrary to file
    public void writePrompts(PromptLibrary prompts) {
        JSONObject json = prompts.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Round to file
    public void writeRound(Round round) {
        JSONObject json = round.toJson();
        saveToFile(json.toString(TAB));

    }

    // POSSIBLE TODO: could be used to create single json files rather than having to save each one separately, which
    //          TODO: is how it currently works.
    // MODIFIES: this
    // EFFECTS: writes JSON representation of PlayerList and PromptLibrary, combined in a single file
    //    public void writePlayersAndPrompts(PlayerList players, PromptLibrary prompts) {
    //        JSONObject playersJson = players.toJson();
    //        JSONObject promptsJson = prompts.toJson();
    //
    //        JSONObject combined = new JSONObject();
    //
    //        combined.put("players", playersJson.get("players"));
    //        combined.put("prompts", promptsJson.get("prompts"));
    //        saveToFile(combined.toString(TAB));
    //    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}