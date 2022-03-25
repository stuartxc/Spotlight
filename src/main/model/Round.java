package model;

import org.json.JSONObject;
import persistence.Writable;

// A counter representing the current round of the game.
public class Round implements Writable {

    private int currentRound;

    // EFFECTS: creates a new Round counter with a default starting counter of 0
    public Round() {
        currentRound = 0;
    }

    // Simple getter and setter methods
    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int num) {
        this.currentRound = num;
    }

    public void incrementRound() {
        this.currentRound++;
    }

    public void decrementRound() {
        this.currentRound--;
    }


    // EFFECTS: Returns this Round as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("currentRound", currentRound);
        return json;
    }
}
