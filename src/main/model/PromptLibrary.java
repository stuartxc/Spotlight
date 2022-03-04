package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents the current collection of prompts that exist in the game library
public class PromptLibrary implements Writable {
    private ArrayList<Prompt> internalArray;

    // EFFECTS: Constructs a new PromptLibrary, consisting of the default library's prompts only
    public PromptLibrary() {
        internalArray = new ArrayList<>();
        // TODO: Add default prompts for the default library
    }

    // MODIFIES: this
    // EFFECTS: Adds the given Prompt to the current game's PromptLibrary, assigning it a promptNum equivalent to its
    //          position in the PromptLibrary
    public void addPrompt(Prompt prompt) {
        internalArray.add(prompt);
        prompt.setPromptNum(internalArray.size());
    }

    // EFFECTS: Searches for a Prompt with the given promptNum in the PromptLibrary. If found, returns the prompt.
    //          If not, returns a dummy Prompt with the text "ERROR: PROMPT_NOT_FOUND".
    public Prompt retrievePrompt(int promptNum) {
        for (Prompt p : internalArray) {
            if (promptNum == p.getPromptNum()) {
                return p;
            }
        }
        return (new Prompt("ERROR: PROMPT_NOT_FOUND"));
    }

    // EFFECTS: Produces the amount of prompts inside the current PromptLibrary
    public int getSize() {
        return internalArray.size();
    }

    // EFFECTS: Returns the list of all the prompts
    public List<Prompt> getAllPrompts() {
        return internalArray;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("prompts", promptsToJson());
        return json;
    }

    // EFFECTS: returns things in this PromptLibrary as a JSON array
    private JSONArray promptsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Prompt p : internalArray) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}
