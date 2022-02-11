package ui;

import model.Prompt;
import model.PromptLibrary;
import model.Player;
import model.PlayerList;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

// MUCH OF THIS UI IS BASED OFF OF THE TELLERAPP PROJECT FROM THE 210 INSTRUCTORS!
// Methods like runSpotlight(), processCommand(), init(), and displayMenu() are more or less copied from there.

// Represents the entire Spotlight app and all the UI features needed to run it.
public class Spotlight {
    private PromptLibrary prompts;
    private PlayerList players;
    private Scanner input;
    private int round = 0;

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
            playIfEnoughPlayers();
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

    // EFFECTS: Allows rounds to start playing if there are at least 3 players. If not, goes back to game menu.
    private void playIfEnoughPlayers() {
        if (players.getAmtPlayers() < 3) {
            System.out.println("Not enough players! Please add more before continuing.");
            beginSpotlight();
        } else {
            round = 0;
            playRounds();
        }
    }

    // REQUIRES: Size of PlayerList >= 3
    // MODIFIES: this
    // EFFECTS: Starts the rounds, allowing players to see, answer, and judge prompts
    private void playRounds() {
        round++;
        setJudgeAndSpotlight();
        displayPrompt();



        players.setAllResponding();
    }

    // REQUIRES: round > 0
    // MODIFIES: this
    // EFFECTS: Sets certain players to be the judge or the one in the spotlight, according to
    //          the current round number. Announces it while doing this.
    private void setJudgeAndSpotlight() {
        System.out.println("Choosing spotlight and judge...");

        int numPlayers = players.getAmtPlayers();
        // TODO: implement a better way to cycle through the spotlight and judge players. Currently, round % numPlayers
        //       can sometimes be 0, and there is no player with playerNum 0.
        Player spotlight = players.retrievePlayerByNum(round % numPlayers);
        Player judge = players.retrievePlayerByNum((round % numPlayers) + 1);

        spotlight.setStatus("Spotlight");
        judge.setStatus("Judge");

        System.out.println("This round, " + spotlight.getName() + " is the player in the spotlight and "
                + judge.getName() + " is the judge!");
    }

    // MODIFIES: this
    // EFFECTS: Displays a random prompt from the current library. If there is any "responding" player who hasn't
    //          responded yet, then allows for more responses to be made to the current prompt. If everyone who needs
    //          to respond has already responded, then goes to the judging screen.
    private void displayPrompt() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = ThreadLocalRandom.current().nextInt(1, prompts.getSize() + 1);
        Prompt display = prompts.retrievePrompt(randomNum);

        System.out.println("Prompt: " + display.getText());

        if (players.haveAllResponded()) {
            displayResponses();
            judgeWinner();
        } else {
            System.out.println("If you would like to skip this prompt, enter \"SKIP\".");
            System.out.println("If you would like to exit the game, enter \"EXIT GAME\".");

            System.out.println("Players that still have to enter a response: " + players.getAllStillResponding());
            System.out.println("\nPlease enter the name of the person who will respond next.");
            playerResponses();
        }

    }

    // MODIFIES: this
    // EFFECTS: Allows the user to skip the current Prompt, exit the game, or choose a Player to respond to the
    //          current Prompt. The game will continue according to whatever the user picks.
    private void playerResponses() {
        String userResponse = "";
        userResponse = input.next();

        if (userResponse.equals("SKIP")) {
            System.out.println("Skipping...");
            round--;
            playRounds();
        } else if (userResponse.equals("EXIT GAME")) {
            System.out.println("Exiting...");
            gameOver();
            beginSpotlight();
        } else if (players.retrievePlayerByName(userResponse).getName().equals("ERROR: PLAYER_NOT_FOUND")) {
            System.out.println("Invalid response. Please try again.");
            playerResponses();
        } else {
            enterResponse(players.retrievePlayerByName(userResponse));
        }

    }

    // MODIFIES: this
    // EFFECTS: Prompts the user for a response, then edits the response of the given Player to match that
    private void enterResponse(Player player) {
        System.out.println(player.getName() + ", please enter your response:");

        String response = "";
        response = input.next();
        player.setResponse(response);

        displayPrompt();
    }

    // EFFECTS: Displays a list of the Players and their associated responses, and prompts the judge to make their
    //          decision
    private void displayResponses() {
        System.out.println("The responses are all in! Here are all the players and their responses.");
        System.out.println("\n" + players.getAllPlayerResponses());
        System.out.println("\nJudge, please find the response you like the best, then enter the name of the person "
                + "who created that response. This will give them a point!");
        System.out.println("\nThe next round will start once you do so.");

        judgeWinner();

    }

    // MODIFIES: this
    // EFFECTS: Allows the judge to look at the responses and add a point to whomever they deem to be the winner
    private void judgeWinner() {
        String judgedName = "";
        judgedName = input.next();

        Player winner = players.retrievePlayerByName(judgedName);

        if (winner.getName().equals("ERROR: PLAYER_NOT_FOUND")) {
            System.out.println("Invalid response. Please try again.");
            judgeWinner();
        } else {
            winner.addPoint();
            System.out.println("Congratulations, " + judgedName + "! You earned a point!");
            System.out.println("You now have " + winner.getPoints() + " points.");
            players.clearAllResponses();
            playRounds();
            System.out.println("\nThe next round is starting!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Ends the current game, returns the user to the main menu
    private void gameOver() {
        System.out.println("The game is now over!");
        System.out.println("The points of each player are as follows: " + players.getAllPlayerPoints());
        beginSpotlight();
    }

    // EFFECTS: displays some instructions for how to play Spotlight and how to create prompts.
    // TODO: as of right now, this is for future phases.
    private void displayInstructions() {
        System.out.println("An instructions guide is coming soon. For now, please refer to the README.md file.");
    }

}
