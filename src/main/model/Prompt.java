package model;

import org.json.JSONObject;
import persistence.Writable;

// A class representing the prompts in the game that have a chance of appearing to the players, each round
public class Prompt implements Writable {
    // public static final String BLANK = "_____";

    private String text;
    private int promptNum;
    private boolean used;

    // EFFECTS: Constructs a new Prompt with the given text.
    //          - The new Prompt should have an initial promptNum of 0, which will be changed immediately upon entering
    //          the PromptLibrary
    //          - Additionally, used should initially be false.
    public Prompt(String text) {
        this.text = text;
        promptNum = 0;
        used = false;
    }

    // EFFECTS: Retrieves the text from the current Prompt
    public String getText() {
        return this.text;
    }

    // EFFECTS: Retrieves the number of the current Prompt
    public int getPromptNum() {
        return this.promptNum;
    }

    // MODIFIES: this
    // EFFECTS: Changes the current Prompt's promptNum to the given num
    public void setPromptNum(int num) {
        this.promptNum = num;
    }

    // EFFECTS: Retrieves whether the current Prompt has been used yet
    public boolean getUsed() {
        return this.used;
    }

    // MODIFIES: this
    // EFFECTS: Sets the current Prompt's used to the given boolean
    public void setUsed(boolean bool) {
        this.used = bool;
    }

    // EFFECTS: Returns this Prompt as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("text", text);
        json.put("promptNum", promptNum);
        json.put("used", used);
        return json;
    }
}
