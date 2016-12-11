package org.sillylossy.games.common.ui;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.game.Statistics;
import org.sillylossy.games.common.players.Player;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

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
    boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Asks for confirm and exits the game if the answer is "Yes".
     */
    private void exit() {
        if (Main.getGameController().isInGame()) {
            if (confirm("This will end your current game. Sure?")) {
                dispose();
            }
        } else {
            dispose();
        }
    }
    /**
     * Window closing event listener.
     */
    private final class WindowClosingListener extends WindowAdapter {
        /**
         * Calls GUI's "exit" handler.
         */
        @Override
        public void windowClosing(WindowEvent e) {
            exit();
        }
    }


    /**
     * "Statistics" menu item action listener.
     */
    public final class StatMenuItemListener extends GameListener {
        /**
         * Gets a statistics map from a game controller and forms a table model from it.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            getMainPanel().flipToStatistics();
            Map<Player, Statistics> stats = getGameController().getStatistics();
            int count = stats.size();
            Player[] players = stats.keySet().toArray(new Player[count]);
            String[] labels = new String[]{"Player", "Total games", "Games won", "Games lost", "Games drawn"};
            Object[][] vector = new Object[players.length][labels.length];
            for (int i = 0; i < vector.length; i++) {
                int won = stats.get(players[i]).getGamesWon();
                int lost = stats.get(players[i]).getGamesLost();
                int stay = stats.get(players[i]).getGamesPushed();
                int total = won + lost + stay;
                vector[i][0] = players[i].toString();
                vector[i][1] = total;
                vector[i][2] = won;
                vector[i][3] = lost;
                vector[i][4] = stay;
            }
            getMainPanel().setStats(new DefaultTableModel(vector, labels));
        }
    }

    /**
     * Window state change event listener.
     */
    private final class WindowStateChangeListener implements WindowStateListener {
        /**
         * Redraws cards when window state changes.
         */
        @Override
        public void windowStateChanged(WindowEvent e) {
            mainPanel.getGamePanel().redraw();
        }
    }

    /**
     * "Exit" menu item action listener.
     */
    private final class ExitMenuItemListener implements ActionListener {
        /**
         * Calls GUI's "exit" method.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            exit();
        }
    }

    /**
     * "How to play" menu item action listener.
     */
    private final class HowToPlayMenuItemListener implements ActionListener {
        /**
         * Opens game rules in default browser.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("https://goo.gl/oh5oT2"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * "About" menu item action listener.
     */
    private final class AboutMenuItemListener implements ActionListener {
        /**
         * Shows information about this program.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            alert( "Author: SillyLossy (http://github.com/sillylossy).\n\n" +
                   "Card images: https://code.google.com/archive/p/vector-playing-cards/ \n" +
                   "Card back image: http://svg-cards.sourceforge.net/ \n");
        }
    }
}
