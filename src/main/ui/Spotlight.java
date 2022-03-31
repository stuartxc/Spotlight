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
import java.util.concurrent.ThreadLocalRandom;

// Represents the entire Spotlight app and all the GUI features needed to run it, inside the GUI frame.
public class Spotlight extends JPanel {
    private static final String PLAYERS_STORE = "./data/playerlist.json";
    private static final String PROMPTS_STORE = "./data/promptlibrary.json";
    private static final String ROUND_STORE = "./data/round.json";

    private PromptLibrary prompts;
    private PlayerList players;
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
    }

    // Setter method:
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
    // EFFECTS: initializes the PromptLibrary, PlayerList and Round, as well as their respective JSONReaders and Writers
    public void init() {
        prompts = new PromptLibrary();
        players = new PlayerList();
        round = new Round();

        playersReader = new JsonReader(PLAYERS_STORE);
        playersWriter = new JsonWriter(PLAYERS_STORE);
        promptsReader = new JsonReader(PROMPTS_STORE);
        promptsWriter = new JsonWriter(PROMPTS_STORE);
        roundReader = new JsonReader(ROUND_STORE);
        roundWriter = new JsonWriter(ROUND_STORE);
    }

    // MODIFIES: this, gui
    // EFFECTS: displays the main menu of options for the user to choose from, as well as a nice spotlight image
    public void displayMainMenu() {
        removeAll();

        JLabel welcome = new JLabel("Welcome to Spotlight! Please select from the following options:");
        add(welcome);

        mainMenuButtons();

        JLabel spotlightImg = new JLabel(new ImageIcon("./data/spotlight.jpg"));
        add(spotlightImg);

        updateGui();
    }

    // MODIFIES: this
    // EFFECTS: Creates the buttons and displays them for the main menu
    public void mainMenuButtons() {
        JButton addButton = new JButton("Add a new prompt to the prompts that you can play with!");
        add(addButton);
        addButton.addActionListener(e -> addNewPrompt());

        JButton viewButton = new JButton("View all prompts that are in the current library.");
        add(viewButton);
        viewButton.addActionListener(e -> viewPrompts());

        createBeginSpotlightButton();
        createLoadButton();

        JButton instructionsButton = new JButton("Instructions: How to play.");
        add(instructionsButton);
        instructionsButton.addActionListener(e -> displayInstructions());

        JButton exitButton = new JButton("Exit the application.");
        add(exitButton);
        exitButton.addActionListener(e -> gui.exitApp());
    }

    // MODIFIES: this
    // EFFECTS: helper for creating the button that allows players to enter the game menu
    //          - only allows them to proceed if there is at least one prompt in the PromptLibrary
    private void createBeginSpotlightButton() {
        JButton beginButton = new JButton("Game: Enter the game menu!");
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
    // EFFECTS: Prompts the user to create a new Prompt. If they decide to do so, then also adds it to the PromptLibrary
    public void addNewPrompt() {
        String userText = JOptionPane.showInputDialog(gui, "Enter a new prompt to be used in the game "
                + "(use \"S\" to refer to the player in the spotlight).");

        if (userText != null) {
            userText = userText.trim();
        }

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

    // MODIFIES: this
    // EFFECTS: helper for creating the button that allows players to load a previously saved game
    private void createLoadButton() {
        JButton loadButton = new JButton("Load: Resume playing with the previously saved game state.");
        add(loadButton);
        loadButton.addActionListener(e -> loadAndPlay());
    }

    // MODIFIES: this
    // EFFECTS: helper for creating the button that takes users back to the main menu
    private void createBackToMainMenuButton() {
        JButton backButton = new JButton("Return to the main menu.");
        add(backButton);
        backButton.addActionListener(e -> displayMainMenu());
    }

    // MODIFIES: this, gui
    // EFFECTS: creates the screen that allows players to view all the prompts currently in the promptlibrary
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

    // REQUIRES: Size of PromptLibrary >= 1
    // MODIFIES: this, gui
    // EFFECTS: Displays the game menu
    public void beginSpotlight() {
        removeAll();

        JLabel gameMenu = new JLabel("Welcome to the game menu:");
        add(gameMenu);

        JButton addButton = new JButton("Add player");
        add(addButton);
        addButton.addActionListener(e -> addNewPlayer());
        JButton removeButton = new JButton("Remove player");
        add(removeButton);
        removeButton.addActionListener(e -> removePlayer());
        JButton playButton = new JButton("Start playing with the current players.");
        add(playButton);
        playButton.addActionListener(e -> playIfEnoughPlayers());
        createLoadButton();
        createBackToMainMenuButton();

        JLabel currentPlayers = new JLabel("The current players are: " + players.getAllPlayerNames());
        add(currentPlayers);

        updateGui();
    }


    // MODIFIES: this
    // EFFECTS: prompts the player to create a new Player. If they do so, adds the Player with the given name
    //          to the current PlayerList.
    //          - only adds the Player if there is no other Player with the same name.
    public void addNewPlayer() {
        String userName = JOptionPane.showInputDialog("Enter a name for the new player.");
        if (userName != null) {
            userName = userName.trim();
        }

        if (userName == null) {
            JOptionPane.showMessageDialog(gui, "Back to game menu.", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (userName.length() < 1) {
            JOptionPane.showMessageDialog(gui, "Invalid name. Please try again. \tBack to game menu.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (playerExists(userName)) {
            JOptionPane.showMessageDialog(gui, "This player already exists; please use a different one. "
                            + "\tBack to game menu.","Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            Player player = new Player(userName);
            players.addPlayer(player);
            JOptionPane.showMessageDialog(gui, "Player successfully added!", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
            beginSpotlight();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the player to remove a Player. If they do so, removes the Player with the given name
    //          from the PlayerList.
    public void removePlayer() {
        String userName = JOptionPane.showInputDialog("Enter the name of the player that you wish to remove.");
        if (userName != null) {
            userName = userName.trim();
        }

        if (userName == null) {
            JOptionPane.showMessageDialog(gui, "Back to game menu.", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (!(playerExists(userName))) {
            JOptionPane.showMessageDialog(gui, "This player does not exist! "
                    + "\tBack to game menu.","Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            Player toRemove = players.retrievePlayerByName(userName);
            players.removePlayer(toRemove);
            JOptionPane.showMessageDialog(gui, "Player successfully removed!", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
            beginSpotlight();
        }
    }

    // EFFECTS: helper for deciding whether a Player with the given name already exists
    private boolean playerExists(String userName) {
        for (Player p : players.getAllPlayers()) {
            if (p.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Starts rounds and game if there are at least 3 players. If not, goes back to game menu.
    public void playIfEnoughPlayers() {
        if (players.getAmtPlayers() < 3) {
            JOptionPane.showMessageDialog(gui, "Not enough players! Please add more before continuing.",
                    "Insufficient amount of players!", JOptionPane.WARNING_MESSAGE);
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
            round.decrementRound();
            playRounds();
            JOptionPane.showMessageDialog(gui, "Successfully loaded the previous save!",
                    "Continue game", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(gui, "Unable to read from file: one of " + PLAYERS_STORE
                            + ", " + PROMPTS_STORE + ", and/or" + ROUND_STORE,"READ ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (JSONException je) {
            JOptionPane.showMessageDialog(gui, "Error: no saved game files were found",
                    "NO SAVE ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    // REQUIRES: Size of PlayerList >= 3
    // MODIFIES: this
    // EFFECTS: Starts the rounds, allowing players to see, answer, and judge prompts
    public void playRounds() {
        removeAll();

        round.incrementRound();
        players.setAllResponding();
        setJudgeAndSpotlight();
        displayPrompt();

        updateGui();
    }

    // REQUIRES: round > 0
    // MODIFIES: this
    // EFFECTS: Sets certain players to be the judge or the one in the spotlight, according to
    //          the current round number (this will rotate through the PlayerList so that each person gets to be
    //          in the spotlight or be the judge).
    public void setJudgeAndSpotlight() {
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

        JLabel chosenMsg = new JLabel("This round, " + spotlight.getName() + " is the player in the spotlight "
                + "and " + judge.getName() + " is the judge!");
        add(chosenMsg);
    }

    // MODIFIES: this
    // EFFECTS: Displays a random prompt from the current library.
    //          If there is any "responding" player who hasn't responded yet, then allows for more responses to be made
    //          to the current prompt.
    //          If everyone who needs to respond has already responded, then goes to the judging screen.
    //          Provides the option for players to save the current game state.
    //          Provides the option for players to choose the next person to respond.
    //          Provides the option for players to return to the game menu.
    public void displayPrompt() {
        chooseCurrentPrompt();
        if (players.haveAllResponded()) {
            removeAll();
            displayResponses();
            updateGui();
        } else {
            createSkipButton();
            JButton saveButton = new JButton("SAVE: Save the game to continue at a later time.");
            add(saveButton);
            saveButton.addActionListener(e -> saveGame());

            JButton exitButton = new JButton("EXIT: Exit the game and return to the game menu.");
            add(exitButton);
            exitButton.addActionListener(e -> gameOver());

            JButton respondButton = new JButton("RESPOND: Choose a player to respond to the prompt.");
            add(respondButton);
            respondButton.addActionListener(e -> playerResponses());

            JLabel needResponse = new JLabel("Players that still have to enter a response: "
                    + players.getAllStillResponding());
            add(needResponse);
        }
    }

    // MODIFIES: this
    // EFFECTS: helper that chooses a random prompt for the current round and displays it on the screen
    private void chooseCurrentPrompt() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = ThreadLocalRandom.current().nextInt(1, prompts.getSize() + 1);
        Prompt display = prompts.retrievePrompt(randomNum);

        JLabel roundPrompt = new JLabel("\nPrompt: " + display.getText());
        add(roundPrompt);
    }

    // MODIFIES: this
    // EFFECTS: helper that creates the skip button while the game is running. When pressed, the skip
    //          button asks the player to confirm they want to skip before doing so.
    private void createSkipButton() {
        JButton skipButton = new JButton("SKIP: Skip the current prompt (no points will be awarded).");
        add(skipButton);
        skipButton.addActionListener(e -> {
            int userResponse = JOptionPane.showConfirmDialog(gui, "Are you sure you want to skip?");

            if (userResponse == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(gui, "Skipping round: The prompt will be re-chosen randomly!",
                        "Spotlight", JOptionPane.INFORMATION_MESSAGE);
                round.decrementRound();
                playRounds();
            } else if (userResponse == JOptionPane.NO_OPTION || userResponse == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(gui, "Back to the current round.", "Spotlight",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Displays a prompt for users to decide which Player will provide the response
    public void playerResponses() {
        String userResponse = JOptionPane.showInputDialog(gui, "Please enter the player who wishes to respond:");
        if (userResponse != null) {
            userResponse = userResponse.trim();
        }

        if (userResponse == null) {
            JOptionPane.showMessageDialog(gui, "Back to game.", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (players.retrievePlayerByName(userResponse).getName().equals("ERROR: PLAYER_NOT_FOUND")) {
            JOptionPane.showMessageDialog(gui, "No such player found. Please try again.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            enterResponse(players.retrievePlayerByName(userResponse));
        }

    }

    // MODIFIES: this, gui
    // EFFECTS: Prompts the user for a response, then updates the Response of the given Player to match what they say.
    //          Also updates the game screen to reflect the change in getAllPlayersStillResponding.
    public void enterResponse(Player player) {
        String response = JOptionPane.showInputDialog(gui, player.getName() + ", please enter your response:");
        player.setResponse(response);
        JOptionPane.showMessageDialog(gui, "Response saved!", "Confirmation",
                JOptionPane.INFORMATION_MESSAGE);

        removeAll();
        setJudgeAndSpotlight();
        displayPrompt();
        updateGui();
    }

    // EFFECTS: Displays a list of the Players and their associated responses, and allows the judge to make their
    //          decision when they are ready
    public void displayResponses() {
        JLabel responsesIn = new JLabel("The responses are in! Here is every player and their response.");
        add(responsesIn);
        JTextArea responses = new JTextArea("\n" + players.getAllPlayerResponses());
        responses.setEditable(false);
        responses.setBackground(Color.red);
        add(responses);

        JLabel judgeMsg = new JLabel("Judge, please find the response you like the best. When you are ready,"
                + " please click the \"JUDGE\" button and enter the name of the person who created that response.");
        add(judgeMsg);
        JLabel nextRound = new JLabel("This will give them a point! The next round will start once you do so.");
        add(nextRound);

        JButton judgeButton = new JButton("JUDGE");
        add(judgeButton);
        judgeButton.addActionListener(e -> judgeWinner());
    }

    // MODIFIES: this
    // EFFECTS: Allows the judge to look at the responses and add a point to whomever they deem to be the winner,
    //          then adds the point and informs the winner accordingly
    public void judgeWinner() {
        String judgedName = JOptionPane.showInputDialog(gui, "Who is the winner of this round?");

        if (judgedName == null) {
            JOptionPane.showMessageDialog(gui, "Cancelled.", "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            Player winner = players.retrievePlayerByName(judgedName);

            if (winner.getName().equals("ERROR: PLAYER_NOT_FOUND")) {
                JOptionPane.showMessageDialog(gui, "No such player found. Please try again.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                winner.addPoint();
                JOptionPane.showMessageDialog(gui, "Congratulations, " + judgedName + "! You earned a point!"
                        + "\n You now have " + winner.getPoints() + " point(s).", "Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);
                players.clearAllResponses();
                JOptionPane.showMessageDialog(gui, "The next round is starting!", "Spotlight",
                        JOptionPane.INFORMATION_MESSAGE);
                playRounds();
            }
        }
    }

    // MODIFIES: playerlist.json, promptlibrary.json, round.json
    // EFFECTS: saves the entire game, including the states of all players and prompts, including the round number,
    //          to file
    public void saveGame() {
        int userResponse = JOptionPane.showConfirmDialog(gui, "This action will overwrite the previously saved"
                + " game. Are you sure you want to continue?");

        if (userResponse == JOptionPane.YES_OPTION) {
            writeToFile();
            JOptionPane.showMessageDialog(gui, "Saved player information, prompt information, "
                    + "and current game state!", "Spotlight", JOptionPane.INFORMATION_MESSAGE);
        } else if (userResponse == JOptionPane.NO_OPTION || userResponse == JOptionPane.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(gui, "Game was not saved.", "Spotlight",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: playerlist.json, promptlibrary.json, round.json
    // EFFECTS: helper that opens and writes all the JSONStores
    private void writeToFile() {
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
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(gui, "Unable to write to file: one of "
                            + PLAYERS_STORE + ", " + PROMPTS_STORE + ", and/or" + ROUND_STORE,
                    "Spotlight", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    // MODIFIES: this
    // EFFECTS: Ends the current game and displays the points that players ended up with,
    //          then returns the user to the game menu
    public void gameOver() {
        JOptionPane.showMessageDialog(gui, "The game is now over!\nThe points of each player are as follows:\n"
                        + players.getAllPlayerPoints(),
                "Spotlight", JOptionPane.INFORMATION_MESSAGE);
        players.setAllPlayerPoints(0);
        beginSpotlight();
    }

    // MODIFIES: this, gui
    // EFFECTS: displays a screen with instructions for how to play Spotlight, as well as a picture of a spotlight
    public void displayInstructions() {
        removeAll();
        JLabel instructionHeading = new JLabel("Game instructions:");
        add(instructionHeading);

        JTextArea instructions = new JTextArea("Each round, one player is assigned to be the judge, and one "
                + "player will be in the spotlight.\nEveryone but the judge will be given a prompt regarding the player"
                + " in the spotlight to answer as they please. \nOnce they answer the prompt, the judge will award a "
                + "point to the person whose answer they like the best.\n\n For example:\n Player A, B, C, J, and S are"
                + " playing a game!\n This round, J is the Judge and S is in the Spotlight.\n A, B, C, and S are given"
                + " the prompt: \"S's most embarrassing memory is...\"\n Once A, B, and C, and S have all given their"
                + " response, J will award a point to the player whose answer they like the best.");
        instructions.setEditable(false);
        instructions.setBackground(Color.red);
        add(instructions);

        createBackToMainMenuButton();

        updateGui();
    }

}
