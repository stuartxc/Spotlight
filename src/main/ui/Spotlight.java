package ui;

import model.Prompt;
import model.PromptLibrary;
import model.Player;
import model.PlayerList;
import model.Round;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// MUCH OF THIS UI IS BASED OFF OF THE TELLERAPP PROJECT FROM THE 210 INSTRUCTORS!
// Methods like runSpotlight(), processCommand(), init(), and displayMenu() are more or less copied from there.

// Represents the entire Spotlight app and all the UI features needed to run it.
public class Spotlight extends JPanel {
    private static final String PLAYERS_STORE = "./data/playerlist.json";
    private static final String PROMPTS_STORE = "./data/promptlibrary.json";
    private static final String ROUND_STORE = "./data/round.json";

    private PromptLibrary prompts;
    private PlayerList players;
    // private Scanner input;
    private Round round;

    private JsonWriter playersWriter;
    private JsonReader playersReader;
    private JsonWriter promptsWriter;
    private JsonReader promptsReader;
    private JsonWriter roundWriter;
    private JsonReader roundReader;

    private GraphicalUserInterface gui;

    // EFFECTS: runs the Spotlight application
    public Spotlight() {
        super();
        // runSpotlight();
    }

    // Getters and setters:
    public void setGui(GraphicalUserInterface gui) {
        this.gui = gui;
    }

    // MODIFIES: gui
    // EFFECTS: refreshes the components of the GUI and repaints it
    public void updateGui() {
        gui.pack();
        gui.revalidate();
        gui.validate();
        gui.repaint();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
//    public void runSpotlight() {
//
//        boolean keepGoing = true;
//        String command = null;
//
//        init();
//
//        while (keepGoing) {
//            displayMainMenu();
//            command = input.next();
//
//            if (command.equals("EXIT GAME")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//
//        System.out.println("\nThank you for playing!");
//
//    }

    // MODIFIES: this
    // EFFECTS: processes the user's commands
//    public void processCommand(String command) {
//        if (command.equals("Add")) {
//            addNewPrompt();
//        } else if (command.equals("Begin")) {
//            beginSpotlight();
//        } else if (command.equals("Instructions")) {
//            displayInstructions();
//        } else {
//            System.out.println("Please enter either Add, Begin, or EXIT GAME");
//        }
//    }

    // MODIFIES: this
    // EFFECTS: initializes the PromptLibrary and PlayerList
    public void init() {
        prompts = new PromptLibrary();
        players = new PlayerList();
        round = new Round();
        // input = new Scanner(System.in);
        // input.useDelimiter("\n");

        playersReader = new JsonReader(PLAYERS_STORE);
        playersWriter = new JsonWriter(PLAYERS_STORE);
        promptsReader = new JsonReader(PROMPTS_STORE);
        promptsWriter = new JsonWriter(PROMPTS_STORE);
        roundReader = new JsonReader(ROUND_STORE);
        roundWriter = new JsonWriter(ROUND_STORE);
    }

    // EFFECTS: displays menu of options for the user to choose from
//    public void displayMainMenu() {
//        System.out.println("\nWelcome to Spotlight! \nPlease select from:");
//        System.out.println("\tAdd -> Add a new prompt to the prompts that you can play with!");
//        System.out.println("\tBegin -> Begin the game!");
//        System.out.println("\tInstructions -> How to play");
//        System.out.println("\tEXIT GAME -> Exit the application.");
//    }

    public void displayMainMenu() {
        removeAll();

        JLabel welcome = new JLabel("Welcome to Spotlight! Please select from the following options:");
        add(welcome);

        mainMenuButtons();
        updateGui();
    }

    public void mainMenuButtons() {
        JButton addButton = new JButton("Add a new prompt to the prompts that you can play with!");
        add(addButton);
        addButton.addActionListener(e -> addNewPrompt());

        JButton viewButton = new JButton("View all prompts that are in the current library.");
        add(viewButton);
        viewButton.addActionListener(e -> viewPrompts());

        createBeginSpotlightButton();

        JButton instructionsButton = new JButton("Instructions: How to play.");
        add(instructionsButton);
        instructionsButton.addActionListener(e -> displayInstructions());

        JButton exitButton = new JButton("Exit the application.");
        add(exitButton);
        exitButton.addActionListener(e -> gui.exitApp());
    }

    private void createBeginSpotlightButton() {
        JButton beginButton = new JButton("Begin the game!");
        add(beginButton);
        beginButton.addActionListener(e -> {
            if (prompts.getSize() >= 1) {
                beginSpotlight();
            } else {
                JOptionPane.showMessageDialog(gui, "No prompts are added yet! "
                        + "Please create at least one prompt.", "Not enough prompts", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Creates a new Prompt and adds it to the PromptLibrary
//    public void addNewPrompt() {
//        String userText = "";
//        System.out.println("Enter a new prompt to be used in the game "
//                + "(use \"S\" to refer to the player in the spotlight), or "
//                + "enter \"Cancel\" to cancel.");
//
//        userText = input.next();
//
//        if (userText.equals("Cancel")) {
//            System.out.println("Back to main menu.");
//        } else if (userText.length() < 5) {
//            System.out.println("Prompt too short! Sorry, please try a longer one. \nBack to main menu.");
//        } else {
//            Prompt prompt = new Prompt(userText);
//            prompts.addPrompt(prompt);
//            System.out.println("Prompt successfully added!");
//        }
//    }

    public void addNewPrompt() {
        String userText = JOptionPane.showInputDialog(gui, "Enter a new prompt to be used in the game "
                + "(use \"S\" to refer to the player in the spotlight).");

        if (userText == null) {
            JOptionPane.showMessageDialog(gui, "Back to main menu.", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (userText.length() < 5) {
            JOptionPane.showMessageDialog(gui, "Prompt too short! Sorry, this will not be added - please try a"
                    + " longer one. \nBack to main menu.", "Confirmation", JOptionPane.WARNING_MESSAGE);
        } else {
            Prompt prompt = new Prompt(userText);
            prompts.addPrompt(prompt);
            JOptionPane.showMessageDialog(gui, "Prompt successfully added!", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void createBackToMainMenuButton() {
        JButton backButton = new JButton("Return to the main menu.");
        add(backButton);
        backButton.addActionListener(e -> displayMainMenu());
    }

    public void viewPrompts() {
        removeAll();
        JTextArea allPrompts = new JTextArea("The current prompts are: \n " + prompts.getAllPromptText(),
                prompts.getSize(), 1);
        allPrompts.setEditable(false);
        allPrompts.setBackground(Color.red);
        add(allPrompts);

        createBackToMainMenuButton();

        updateGui();
    }

    // MODIFIES: this
    // EFFECTS: Begins playing Spotlight, which requires the user to create at least 3 players before starting the
    //          rounds
//    public void beginSpotlight() {
//        String selection = "";
//        System.out.println("Spotlight: Please make a selection. At least 3 players are needed to play!");
//        System.out.println("\tAdd player -> Add another player to the current list of players.");
//        System.out.println("\tPlay -> Start playing with the current players.");
//        System.out.println("\tLoad -> Resume playing with the previously saved game state");
//        System.out.println("\tBack -> Return to the main menu");
//        System.out.println("\nThe current players are: " + players.getAllPlayerNames());
//        selection = input.next();
//
//        if (selection.equals("Add player")) {
//            addNewPlayer();
//        } else if (selection.equals("Play")) {
//            playIfEnoughPlayers();
//        } else if (selection.equals("Load")) {
//            loadAndPlay();
//        } else if (selection.equals("Back")) {
//            System.out.println("Returning to the main menu...");
//            System.out.println("\tThis may take multiple tries. If needed, please enter \"Back\" again.");
//        } else {
//            System.out.println("Selection invalid, please try again.");
//            beginSpotlight();
//        }
//    }

    public void beginSpotlight() {
        removeAll();

        JLabel gameMenu = new JLabel("Welcome to the game menu:");
        add(gameMenu);

        JButton addButton = new JButton("Add player");
        add(addButton);
        addButton.addActionListener(e -> addNewPlayer());
        JButton playButton = new JButton("Start playing with the current players.");
        add(playButton);
        playButton.addActionListener(e -> playIfEnoughPlayers());
        JButton loadButton = new JButton("Resume playing with the previously saved game state.");
        add(loadButton);
        loadButton.addActionListener(e -> loadAndPlay());
        createBackToMainMenuButton();

        JLabel currentPlayers = new JLabel("The current players are: " + players.getAllPlayerNames());
        add(currentPlayers);

        updateGui();
    }


    // MODIFIES: this
    // EFFECTS: adds a new Player with the given name to the current PlayerList


    public void addNewPlayer() {
        String userName = JOptionPane.showInputDialog("Enter a name for the new player.");

        if (userName == null) {
            JOptionPane.showMessageDialog(gui, "Back to game menu.", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (userName.length() < 1) {
            JOptionPane.showMessageDialog(gui, "Invalid name. Please try again. \tBack to game menu.",
                    "Confirmation", JOptionPane.WARNING_MESSAGE);
        } else {
            Player player = new Player(userName);
            players.addPlayer(player);
            JOptionPane.showMessageDialog(gui, "Player successfully added!", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
            beginSpotlight();
        }
    }

    // MODIFIES: this
    // EFFECTS: Allows rounds to start playing if there are at least 3 players. If not, goes back to game menu.
    public void playIfEnoughPlayers() {
        if (players.getAmtPlayers() < 3) {
            System.out.println("Not enough players! Please add more before continuing.");
            beginSpotlight();
        } else {
            round.setCurrentRound(0);
            playRounds();
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads the previously saved game and plays from that saved state
    //          - As of the current iteration, loading a saved game will NOT carry their saved answers into the
    //          newly loaded game, even if they are written into the json file. This is more of a design choice,
    //          since I think people would find it better if they had the option to restate their previous answer
    //          or to choose a new one instead.
    public void loadAndPlay() {
        try {
            players = playersReader.readPlayers();
            prompts = promptsReader.readPrompts();
            round = roundReader.readRound();
        } catch (IOException e) {
            System.out.println("Unable to read from file: one of " + PLAYERS_STORE + ", " + PROMPTS_STORE
                    + ", and/or" + ROUND_STORE);
        } catch (JSONException je) {
            System.out.println("Error: no saved game files were found");
        }
        round.decrementRound();
        playRounds();
    }

    // REQUIRES: Size of PlayerList >= 3, PromptLibrary >= 1
    // MODIFIES: this
    // EFFECTS: Starts the rounds, allowing players to see, answer, and judge prompts
    public void playRounds() {
        round.incrementRound();
        players.setAllResponding();
        setJudgeAndSpotlight();
        displayPrompt();

    }

    // REQUIRES: round > 0
    // MODIFIES: this
    // EFFECTS: Sets certain players to be the judge or the one in the spotlight, according to
    //          the current round number. Announces it while doing this.
    public void setJudgeAndSpotlight() {
        System.out.println("Choosing spotlight and judge...");

        int numPlayers = players.getAmtPlayers();
        int spotlightNum = round.getCurrentRound() % numPlayers;
        int judgeNum = (round.getCurrentRound() % numPlayers) + 1;

        if (spotlightNum == 0) {
            spotlightNum = numPlayers;
        }

        Player spotlight = players.retrievePlayerByNum(spotlightNum);
        Player judge = players.retrievePlayerByNum(judgeNum);

        spotlight.setStatus("Spotlight");
        judge.setStatus("Judge");

        System.out.println("This round, " + spotlight.getName() + " is the player in the spotlight and "
                + judge.getName() + " is the judge!");
    }

    // MODIFIES: this
    // EFFECTS: Displays a random prompt from the current library. If there is any "responding" player who hasn't
    //          responded yet, then allows for more responses to be made to the current prompt. If everyone who needs
    //          to respond has already responded, then goes to the judging screen.
    public void displayPrompt() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = ThreadLocalRandom.current().nextInt(1, prompts.getSize() + 1);
        Prompt display = prompts.retrievePrompt(randomNum);

        System.out.println("\nPrompt: " + display.getText());

        if (players.haveAllResponded()) {
            displayResponses();
            judgeWinner();
        } else {
            System.out.println("If you would like to skip this prompt, enter \"SKIP\".");
            System.out.println("If you would like to save the game to continue at a later time, enter \"SAVE\""
                    + "\t Warning!: if you choose to save, the previously saved game will be overwritten.");
            System.out.println("If you would like to exit the game, enter \"EXIT GAME\".");

            System.out.println("Players that still have to enter a response: " + players.getAllStillResponding());
            System.out.println("\nPlease enter the name of the person who will respond next.");
            playerResponses();
        }

    }

    // MODIFIES: this
    // EFFECTS: Allows the user to skip the current Prompt, exit the game, or choose a Player to respond to the
    //          current Prompt. The game will continue according to whatever the user picks.
    public void playerResponses() {
        String userResponse = "";
        // userResponse = input.next();

        if (userResponse.equals("SKIP")) {
            System.out.println("Skipping...");
            round.decrementRound();
            playRounds();
        } else if (userResponse.equals("SAVE")) {
            System.out.println("Saving...");
            saveGame();
            displayPrompt();
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
    public void enterResponse(Player player) {
        System.out.println(player.getName() + ", please enter your response:");

        String response = "";
        // response = input.next();
        player.setResponse(response);
        System.out.println("Response saved!");

        displayPrompt();
    }

    // EFFECTS: Displays a list of the Players and their associated responses, and prompts the judge to make their
    //          decision
    public void displayResponses() {
        System.out.println("The responses are in! Here is every player and their response.");
        System.out.println("\n" + players.getAllPlayerResponses());
        System.out.println("\nJudge, please find the response you like the best, then enter the name of the person "
                + "who created that response. This will give them a point!");
        System.out.println("\tThe next round will start once you do so.");

        judgeWinner();

    }

    // MODIFIES: this
    // EFFECTS: Allows the judge to look at the responses and add a point to whomever they deem to be the winner
    public void judgeWinner() {
        String judgedName = "";
        // judgedName = input.next();

        Player winner = players.retrievePlayerByName(judgedName);

        if (winner.getName().equals("ERROR: PLAYER_NOT_FOUND")) {
            System.out.println("Invalid response. Please try again.");
            judgeWinner();
        } else {
            winner.addPoint();
            System.out.println("Congratulations, " + judgedName + "! You earned a point!");
            System.out.println("You now have " + winner.getPoints() + " points.");
            players.clearAllResponses();
            System.out.println("\nThe next round is starting!");
            playRounds();
        }
    }

    // MODIFIES: playerlist.json, promptlibrary.json, round.json
    // EFFECTS: saves the entire game, including the states of all players and prompts, including the round number,
    //          to file
    public void saveGame() {
        try {
            playersWriter.open();
            playersWriter.writePlayers(players);
            playersWriter.close();
            promptsWriter.open();
            promptsWriter.writePrompts(prompts);
            promptsWriter.close();
            roundWriter.open();
            roundWriter.writeRound(round);
            roundWriter.close();
            System.out.println("Saved player information, prompt information, and current game state!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: one of " + PLAYERS_STORE + ", " + PROMPTS_STORE
                    + ", and/or" + ROUND_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Ends the current game, returns the user to the main menu
    public void gameOver() {
        System.out.println("The game is now over!");
        System.out.println("The points of each player are as follows: " + players.getAllPlayerPoints());
        players.setAllPlayerPoints(0);
        beginSpotlight();
    }

    // EFFECTS: displays some instructions for how to play Spotlight and how to create prompts.
    public void displayInstructions() {
        removeAll();
        JLabel instructionHeading = new JLabel("Game instructions:");
        add(instructionHeading);

        JTextArea instructions = new JTextArea("Each round, one player is assigned to be the judge, and one "
                + "player will be in the spotlight.\nEveryone but the judge will be given a prompt regarding the player"
                + " in the spotlight to answer as they please. \nOnce they answer the prompt, the judge will award a "
                + "point to the person whose answer they like the best.\n\n For example:\n Player A, B, C, J, and S are"
                + " playing a game!\n This round, J is the Judge and S is in the Spotlight.\n A, B, C, and S are given"
                + " the prompt: \"S's most embarassing memory is...\"\n Once A, B, and C, and S have all given their"
                + " response, J will award a point to the player whose answer they like the best.");
        instructions.setEditable(false);
        instructions.setBackground(Color.red);
        add(instructions);

        createBackToMainMenuButton();

        updateGui();
    }

}
