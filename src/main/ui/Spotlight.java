package ui;

import model.Prompt;
import model.PromptLibrary;
import model.Player;
import model.PlayerList;

import java.util.Scanner;

public class Spotlight {
    private PromptLibrary prompts;
    private PlayerList players;
    private Scanner input;
    private int pointsToWin;

    // EFFECTS: runs the Spotlight application
    public Spotlight() {
        runSpotlight();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSpotlight() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: processes the user's commands
    private void processCommand() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: initializes the PromptLibrary and PlayerList
    private void init() {
        // stub
    }

    // EFFECTS: displays menu of options for the user to choose from
    private void displayMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Creates a new Prompt and adds it to the PromptLibrary
    private void addNewPrompt() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Begins playing Spotlight, which requires the user to input at least 3 players before starting the
    //          rounds, as well as a number that represents how the amount a player needs to win.
    private void beginSpotlight() {
        // stub
    }

    // REQUIRES: Size of PlayerList >= 3, pointsToWin > 0
    // MODIFIES: this
    // EFFECTS: Starts the rounds, allowing players to see, answer, and judge prompts
    private void playRounds() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Ends the current game, returns the user to the main menu
    private void gameOver() {
        // stub
    }

}
