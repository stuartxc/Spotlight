package ui;

import model.EventLog;
import model.Event;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.WindowEvent;

// Runs the Spotlight application and sets up the GUI frame.
public class GraphicalUserInterface extends JFrame {

    private static final int WIDTH = 612;
    private static final int HEIGHT = 450;

    private Spotlight spotlight;

    // EFFECTS: Runs the application
    public static void main(String[] args) {
        new GraphicalUserInterface();
    }

    // EFFECTS: Constructor for the GUI frame and the Spotlight panel
    public GraphicalUserInterface() {
        super("Spotlight");
        setupGUI();
        initializeSpotlight();
    }

    // MODIFIES: this
    // EFFECTS: Sets basic, functional values for the GUI. Prints the EventLog when the GUI is closed.
    private void setupGUI() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.toString());
                }
                //THEN you can exit the program
                System.exit(0);
            }
        });
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // MODIFIES: this, spotlight
    // EFFECTS: Initializes the spotlight object and creates its default settings
    private void initializeSpotlight() {
        spotlight = new Spotlight();
        spotlight.setGui(this);
        spotlight.setBackground(Color.red);
        spotlight.setLayout(new DefaultMenuLayout(spotlight, BoxLayout.PAGE_AXIS));

        spotlight.setSize(new Dimension(612, 450));
        add(spotlight, BorderLayout.CENTER);

        spotlight.init();
        spotlight.displayMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: Displays a message to the user about ending the application and then closes the window
    public void exitApp() {
        JOptionPane.showMessageDialog(this, "Thanks for playing!", "Exiting...",
                JOptionPane.INFORMATION_MESSAGE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
