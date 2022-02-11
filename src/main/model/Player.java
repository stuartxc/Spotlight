package model;

// A class that represents players; each player has an associated Player object for them.
public class Player {
    private String name;
    private String status;
    private int points;

    // EFFECTS: Constructs a player with the given name, the status "Responder", and initial points 0
    public Player(String name) {
        this.name = name;
        status = "Responder";
        points = 0;
    }

    // EFFECTS: Retrieves the name of the player
    public String getName() {
        return this.name;
    }

    // EFFECTS: Retrieves the status of the player: "Responder", "Spotlight", or "Judge"
    public String getStatus() {
        return this.status;
    }

    // MODIFIES: this
    // EFFECTS: Sets the status of the Player to the given string
    public void setStatus(String status) {
        this.status = status;
    }

    // EFFECTS: Retrieves the points of the player
    public int getPoints() {
        return this.points;
    }

    // MODIFIES: this
    // EFFECTS: Adds 1 to the Player's point total
    public void addPoint() {
        this.points = this.points + 1;
    }

}
