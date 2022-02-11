package model;

import java.util.ArrayList;

// Represents the current collection of prompts that exist in the game library
public class PromptLibrary {
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

    // EFFECTS: Searches for a Prompt with the given promptNum in the PromptLibrary. If found, returns the text
    //          of the prompt. If not, returns "PROMPT_NOT_FOUND".
    public String retrievePrompt(int promptNum) {
        for (Prompt p : internalArray) {
            if (promptNum == p.getPromptNum()) {
                return p.getText();
            }
        }
        return "PROMPT_NOT_FOUND";
    }

    // EFFECTS: Produces the amount of prompts inside the current PromptLibrary
    public int getSize() {
        return internalArray.size();
    }
}
