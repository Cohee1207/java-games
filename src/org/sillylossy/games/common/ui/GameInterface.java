package org.sillylossy.games.common.ui;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.ui.listeners.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;

/**
 * Main game GUI class.
 */
public class GameInterface extends JFrame {

    /**
     * A default game window size.
     */
    private static final Dimension defaultWindowSize = new Dimension(800, 600);

    /**
     * A minimal window size.
     */
    private static final Dimension minWindowSize = new Dimension(640, 520);

    /**
     * Main panel of GUI.
     */
    private final MainPanel mainPanel;

    /**
     * Creates a GUI and assign listeners.
     */
    public GameInterface() {
        setLookAndFeel();
        addWindowListener(new WindowClosingListener());
        addWindowStateListener(new WindowStateChangeListener());
        setJMenuBar(createMenu());
        setMinimumSize(minWindowSize);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        mainPanel = new MainPanel();
        setContentPane(mainPanel);
        setSize(defaultWindowSize);
        setLocationRelativeTo(null);
        mainPanel.flipToGameSelection();
        setVisible(true);
    }

    /**
     * Gets a reference to main panel object.
     */
    public MainPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Creates main menu.
     */
    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu game = new JMenu("Game");
        menuBar.add(game);
        JMenuItem statistics = new JMenuItem("Statistics");
        statistics.addActionListener(new StatMenuItemListener());
        game.add(statistics);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitMenuItemListener());
        game.add(exit);
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem howToPlay = new JMenuItem("How to play");
        howToPlay.addActionListener(new HowToPlayMenuItemListener());
        help.add(howToPlay);
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new AboutMenuItemListener());
        help.add(about);
        return menuBar;
    }

    /**
     * Sets a look and feel of an application to "Nimbus" LaF or system-default if it's unavailable.
     * When system-default fails, LaF would be default ("Metal").
     */
    private void setLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Shows an information window with desired text.
     *
     * @param message message text
     */
    public void alert(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Asks a confirmation from player.
     *
     * @param message a message that will be displayed
     * @return true if player pressed "Yes", else - false
     */
    public boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Asks for confirm and exits the game if the answer is "Yes".
     */
    public void exit() {
        if (Main.getGameController().isInGame()) {
            if (confirm("This will end your current game. Sure?")) {
                dispose();
            }
        } else {
            dispose();
        }
    }

}
