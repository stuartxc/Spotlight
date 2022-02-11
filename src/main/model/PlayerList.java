package model;

import java.util.LinkedList;

// A list of all the players in the current game.
public class PlayerList {

    private LinkedList<Player> internalList;

    // EFFECTS: Constructs a PlayerList with 0 players initially
    public PlayerList() {
        internalList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds the given Player to the PlayerList
    public void addPlayer(Player player) {
        internalList.add(player);
    }

    // REQUIRES: the given Player is a member of the PlayerList
    // EFFECTS: Removes the given Player from the PlayerList
    // TODO: Not necessary for the first phase of the project, but this could be useful later.
    public void removePlayer(Player player) {
        // stub
    }

    // EFFECTS: returns a string containing the names of all players in the current PlayerList
    public String getAllPlayerNames() {
        String allPlayers = "";

        for (Player p : internalList) {
            allPlayers = allPlayers + ", " + p.getName();
        }

        if (allPlayers.length() <= 2) {
            return "";
        }

        return allPlayers.substring(2, allPlayers.length());
    }

    // EFFECTS: Returns the size of the current PlayerList
    public int getAmtPlayers() {
        return internalList.size();
    }
}
