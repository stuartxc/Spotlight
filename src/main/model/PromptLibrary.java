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
    //          position in the PromptLibrary. Also logs an Event that says which # prompt was added at this time.
    public void addPrompt(Prompt prompt) {
        internalArray.add(prompt);
        prompt.setPromptNum(internalArray.size());
        EventLog.getInstance().logEvent(new Event("Prompt #" + prompt.getPromptNum() + " was added!"));
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

    // EFFECTS: returns a string containing the text of all prompts in the current PromptLibrary
    public String getAllPromptText() {
        String allPrompts = "";

        for (Prompt p : internalArray) {
            allPrompts = allPrompts + "\n- " + p.getText();
        }

        if (allPrompts.length() <= 3) {
            return "";
        }
        return allPrompts.substring(1);
    }

    // EFFECTS: returns this PromptLibrary as a json object
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
