package model;

// A class that represents players; each player has an associated Player object for them.
public class Player {
    private String name;
    private String status;
    private int points;
    private int playerNum;
    private String response;

    // EFFECTS: Constructs a player with the given name, the status "Responder", and initial points 0
    //          Additionally, playerNum is initially 0, but it will change immediately once entering a PlayerList.
    //          Response is also "": it will change from user input, and it will reset to "" every round.
    public Player(String name) {
        this.name = name;
        status = "Responder";
        points = 0;
        playerNum = 0;
        response = "";
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

    // EFFECTS: gets the PlayerNum of the current Player
    public int getPlayerNum() {
        return this.playerNum;
    }

    // EFFECTS: sets the PlayerNum of the current Player
    public void setPlayerNum(int num) {
        this.playerNum = num;
    }

    // EFFECTS: retrieves the Response of the current Player
    public String getResponse() {
        return this.response;
    }

    // EFFECTS: modifies the Response of the current Player
    public void setResponse(String response) {
        this.response = response;
    }
}
