package ui;

import model.Prompt;
import model.PromptLibrary;
import model.Player;
import model.PlayerList;

import java.util.Scanner;

// MUCH OF THIS UI IS BASED OFF OF THE TELLERAPP PROJECT FROM THE 210 INSTRUCTORS!
// Methods like runSpotlight(), processCommand(), init(), and displayMenu() are more or less copied from there.

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
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("EXIT GAME")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for playing!");

    }

    // MODIFIES: this
    // EFFECTS: processes the user's commands
    private void processCommand(String command) {
        if (command.equals("Add")) {
            addNewPrompt();
        } else if (command.equals("Begin")) {
            beginSpotlight();
        } else if (command.equals("Instructions")) {
            displayInstructions();
        } else {
            System.out.println("Please enter either Add, Begin, or EXIT GAME");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the PromptLibrary and PlayerList
    private void init() {
        prompts = new PromptLibrary();
        players = new PlayerList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options for the user to choose from
    private void displayMenu() {
        System.out.println("\nWelcome to Spotlight! \nPlease select from:");
        System.out.println("\tAdd -> Add a new prompt to the prompts that you can play with!");
        System.out.println("\tBegin -> Begin the game!");
        System.out.println("\tInstructions -> How to play");
        System.out.println("\tEXIT GAME -> Exit the application.");
    }

    // MODIFIES: this
    // EFFECTS: Creates a new Prompt and adds it to the PromptLibrary
    private void addNewPrompt() {
        String userText = "";
        System.out.println("Enter a new prompt to be used in the game "
                + "(use \"you\" to refer to the player in the spotlight), or "
                + "enter \"Cancel\" to cancel.");

        userText = input.next();

        if (userText.equals("Cancel")) {
            System.out.println("Back to main menu.");
        } else if (userText.length() < 5) {
            System.out.println("Prompt too short! Sorry, please try a longer one. \nBack to main menu.");
        } else {
            Prompt prompt = new Prompt(userText);
            prompts.addPrompt(prompt);
            System.out.println("Prompt successfully added!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Begins playing Spotlight, which requires the user to create at least 3 players before starting the
    //          rounds
    private void beginSpotlight() {
        String selection = "";
        System.out.println("Spotlight: Please make a selection. At least 3 players are needed to play!");
        System.out.println("\tAdd player -> Add another player to the current list of players.");
        System.out.println("\tPlay -> Start playing with the current players.");
        System.out.println("\tBack -> Return to the main menu");
        System.out.println("\nThe current players are: " + players.getAllPlayerNames());

        selection = input.next();

        if (selection.equals("Add player")) {
            addNewPlayer();
        } else if (selection.equals("Play")) {
            playRounds();
        } else if (selection.equals("Back")) {
            System.out.println("Returning to the main menu...");
        } else {
            System.out.println("Selection invalid, please try again.");
            beginSpotlight();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new Player with the given name to the current PlayerList
    private void addNewPlayer() {
        String userName = "";
        System.out.println("Enter a name for the new player, or enter \"Cancel\" to cancel.");

        userName = input.next();

        if (userName.equals("Cancel")) {
            System.out.println("Back to game menu.");
        } else if (userName.length() < 1) {
            System.out.println("Invalid name. Please try again. \tBack to game menu.");
        } else {
            Player player = new Player(userName);
            players.addPlayer(player);
            System.out.println("Player successfully added!");
        }
        beginSpotlight();
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

    // EFFECTS: displays some instructions for how to play Spotlight and how to create prompts.
    private void displayInstructions() {

    }

}
