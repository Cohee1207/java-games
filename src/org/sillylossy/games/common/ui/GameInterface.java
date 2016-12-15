package org.sillylossy.games.common.ui;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.game.Statistics;
import org.sillylossy.games.common.players.Player;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * Main game GUI class.
 */
public class GameInterface extends JFrame {

    /**
     * A minimal window size.
     */
    private static final Dimension WINDOW_SIZE = new Dimension(690, 560);

    static {
        GameInterface.setLookAndFeel();
    }

    /**
     * Main panel of GUI.
     */
    private final MainPanel mainPanel = new MainPanel();
    /**
     * Label with games status (current player, hand value, etc).
     */
    private final JLabel lblStatus = new JLabel();

    /**
     * Creates a GUI and assign listeners.
     */
    public GameInterface() {
        addWindowListener(new WindowClosingListener());
        setJMenuBar(createMenu());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
        setSize(WINDOW_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Sets a look and feel of an application to "Nimbus" LaF or system-default if it's unavailable.
     * When system-default fails, LaF would be default ("Metal").
     */
    private static void setLookAndFeel() {
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
        statistics.addActionListener(new StatMenuItemAction());
        game.add(statistics);
        JMenuItem changePlayer = new JMenuItem("Change player");
        changePlayer.addActionListener(new ChangePlayerMenuItemListener());
        JMenuItem changeGame = new JMenuItem("Change game");
        changeGame.addActionListener(new ChangeGameMenuItemListener());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitMenuItemAction());
        game.add(changeGame);
        game.add(changePlayer);
        game.add(exit);
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem howToPlay = new JMenuItem("How to play");
        howToPlay.addActionListener(new HowToPlayMenuItemAction());
        help.add(howToPlay);
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new AboutMenuItemAction());
        help.add(about);
        return menuBar;
    }

    /**
     * Creates status bar with status label.
     */
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.add(lblStatus);
        return statusBar;
    }

    public void updateStatus(String status) {
        lblStatus.setText(status);
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
        if (mainPanel.isInGame()) {
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
    public final class StatMenuItemAction extends AbstractAction {
        /**
         * Gets a statistics map from a game controller and forms a table model from it.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            mainPanel.flipToStatistics();
            Map<Player, Statistics> stats = Main.getGameController().getStatistics();
            int count = stats.size();
            Player[] players = stats.keySet().toArray(new Player[count]);
            String[] labels = new String[]{"Player", "Total games", "Games won", "Games lost", "Games drawn"};
            Object[][] vector = new Object[players.length][labels.length];
            for (int i = 0; i < vector.length; i++) {
                int won = stats.get(players[i]).getGamesWon();
                int lost = stats.get(players[i]).getGamesLost();
                int stay = stats.get(players[i]).getGamesDrawn();
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
     * "Exit" menu item action listener.
     */
    private final class ExitMenuItemAction extends AbstractAction {
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
    private final class HowToPlayMenuItemAction extends AbstractAction {
        /**
         * Opens game rules in default browser.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.getGame() != null) {
                new RulesBrowser(Main.getGame().getRules());
            }
        }
    }

    /**
     * "About" menu item action listener.
     */
    private final class AboutMenuItemAction extends AbstractAction {
        /**
         * Shows information about this program.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            alert("Author: SillyLossy (http://github.com/sillylossy).\n\n" +
                    "Card images: https://code.google.com/archive/p/vector-playing-cards/ \n" +
                    "Card back image: http://svg-cards.sourceforge.net/ \n");
        }
    }

    private class ChangeGameMenuItemListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.setGame(null);
            mainPanel.setGamePanel(null);
            mainPanel.setInGame(false);
            mainPanel.flipToGameSelection();
        }
    }

    private class ChangePlayerMenuItemListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.getGame() == null) {
                return;
            }
            Main.getGame().reset();
            Main.getGame().setPlayer(null);
            mainPanel.getGamePanel().clear();
            mainPanel.setInGame(false);
            mainPanel.flipToPlayerSelection();
        }
    }
}
