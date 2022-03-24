package ui;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

// Runs the Spotlight application and sets up the GUI.
public class GraphicalUserInterface extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 490;

    private JPanel panel;
    private Spotlight spotlight;

    private ActionListener addPromptListener;

    public static void main(String[] args) {
        new GraphicalUserInterface();
    }

    public GraphicalUserInterface() {
        super("Spotlight");
        setupGUI();
        initializeSpotlight();
    }

    private void setupGUI() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void initializeSpotlight() {
        spotlight = new Spotlight();
        spotlight.setGui(this);
        spotlight.setBackground(Color.red);
        spotlight.setLayout(new DefaultMenuLayout(spotlight, BoxLayout.PAGE_AXIS));

        spotlight.setSize(new Dimension(700, 490));
        add(spotlight, BorderLayout.CENTER);

        spotlight.init();
        spotlight.displayMainMenu();
    }

    public void exitApp() {
        JOptionPane.showMessageDialog(this, "Thanks for playing!", "Exiting...",
                JOptionPane.INFORMATION_MESSAGE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
