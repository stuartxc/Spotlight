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
        player.setPlayerNum(internalList.size());
    }

    // REQUIRES: the given Player is a member of the PlayerList
    // EFFECTS: Removes the given Player from the PlayerList
    // TODO: Not necessary for the first phase of the project, but this could be useful later.
    public void removePlayer(Player player) {
        // stub
    }

    // EFFECTS: Searches for a Player with the given playerName in the PlayerList. If found, returns that Player.
    //          If not, returns a dummy Player named "ERROR: PLAYER_NOT_FOUND"
    public Player retrievePlayerByName(String playerName) {
        for (Player p : internalList) {
            if (playerName.equals(p.getName())) {
                return p;
            }
        }
        return (new Player("ERROR: PLAYER_NOT_FOUND"));
    }

    // EFFECTS: Searches for a Player with the given playerNum in the PlayerList. If found, returns that Player.
    //          If not, returns a dummy Player named "ERROR: PLAYER_NOT_FOUND"
    public Player retrievePlayerByNum(int playerNum) {
        for (Player p : internalList) {
            if (playerNum == p.getPlayerNum()) {
                return p;
            }
        }
        return (new Player("ERROR: PLAYER_NOT_FOUND"));
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

    // MODIFIES: this
    // EFFECTS: resets all Players' statuses to "Responding"
    public void setAllResponding() {
        for (Player p : internalList) {
            p.setStatus("Responding");
        }
    }

    // MODIFIES: this
    // EFFECTS: resets all Players' Responses to ""
    public void clearAllResponses() {
        for (Player p : internalList) {
            p.setResponse("");
        }
    }

    // EFFECTS: returns true if all non-judge players have responded, otherwise false
    public boolean haveAllResponded() {
        for (Player p : internalList) {
            if (p.getResponse().equals("") && !(p.getStatus().equals("Judge"))) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: gets a string containing the names of all the players who haven't responded yet, UNLESS they are the
    //          current judge.
    public String getAllStillResponding() {
        String stillResponding = "";

        for (Player p : internalList) {
            if (p.getResponse().equals("") && !(p.getStatus().equals("Judge"))) {
                stillResponding = stillResponding + ", " + p.getName();
            }
        }

        if (stillResponding.length() <= 2) {
            return "";
        }
        return stillResponding.substring(2, stillResponding.length());
    }

    // EFFECTS: gets a string that contains every Player paired with their response, each pair separated by a period,
    //          doesn't include the judge.
    public String getAllPlayerResponses() {
        String allPlayersResponses = "";

        for (Player p : internalList) {
            if (!(p.getStatus().equals("Judge"))) {
                allPlayersResponses = allPlayersResponses + ". " + p.getName() + ": " + p.getResponse();
            }
        }

        if (allPlayersResponses.length() <= 2) {
            return "";
        }
        return allPlayersResponses.substring(2, allPlayersResponses.length());
    }

    // EFFECTS: gets a string that contains every Player paired with their point total, each pair separated by a
    //          period.
    public String getAllPlayerPoints() {
        String allPlayersPoints = "";

        for (Player p : internalList) {
            allPlayersPoints = allPlayersPoints + ". " + p.getName() + ": " + p.getPoints();
        }

        if (allPlayersPoints.length() <= 2) {
            return "";
        }
        return allPlayersPoints.substring(2, allPlayersPoints.length());
    }

    // EFFECTS: Returns the size of the current PlayerList
    public int getAmtPlayers() {
        return internalList.size();
    }
}
