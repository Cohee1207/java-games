package org.sillylossy.games.common.ui;

import org.sillylossy.games.blackjack.BlackjackGame;
import org.sillylossy.games.blackjack.BlackjackPanel;
import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.game.Game;
import org.sillylossy.games.common.players.Player;
import org.sillylossy.games.videopoker.VideoPokerGame;
import org.sillylossy.games.videopoker.VideoPokerPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.sillylossy.games.common.Main.getGameController;

/**
 * Main content panel of GUI.
 */
public class MainPanel extends JPanel {

    /**
     * String ID of game panel.
     */
    private static final String GAME_PANEL = "GamePanel";

    /**
     * String ID of player selection panel.
     */
    private static final String PLAYER_SELECTOR = "PlayerSelectionPanel";

    /**
     * String ID of statistics panel.
     */
    private static final String STAT_PANEL = "StatPanel";

    /**
     * String ID of game selection panel.
     */
    private static final String GAME_SELECTOR = "GameSelector";

    /**
     * Panel's layout manager. Used to switch sub-panels.
     */
    private final CardLayout gameLayout = new CardLayout();

    /**
     * A GUI-list of players.
     */
    private final JList<Player> playersList = new JList<>();

    /**
     * A GUI-table of statistics.
     */
    private final JTable statTable = new JTable();
    private final JList<String> gamesList = new JList<>();
    /**
     * Reference to game panel of selected game.
     */
    private GamePanel gamePanel;
    /**
     * A flag that indicates whether a player is in game.
     */
    private boolean inGame = false;

    /**
     * Constructs main panel: sets layout, adds sub-panels.
     */
    MainPanel() {
        setLayout(gameLayout);
        add(createPlayerSelectionPanel(), PLAYER_SELECTOR);
        add(createStatPanel(), STAT_PANEL);
        add(createGameSelector(), GAME_SELECTOR);
    }

    /**
     * Creates statistics panel.
     */
    private JPanel createStatPanel() {
        JPanel statPanel = new JPanel();
        statPanel.setLayout(new BorderLayout());
        statTable.setEnabled(false);
        statPanel.add(new JScrollPane(statTable));
        JButton btnReturn = new JButton("Return");
        btnReturn.addActionListener(new ReturnButtonAction());
        statPanel.add(btnReturn, BorderLayout.SOUTH);
        return statPanel;
    }

    /**
     * Creates game selection panel.
     *
     * @return a reference to created JPanel
     */
    private JPanel createGameSelector() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        gamesList.setModel(listModel);
        listModel.addElement(BlackjackGame.GAME_NAME);
        listModel.addElement(VideoPokerGame.GAME_NAME);
        panel.add(new JScrollPane(gamesList), BorderLayout.CENTER);
        JButton btnAccept = new JButton("Accept");
        btnAccept.addActionListener(new SelectGameButtonActionListener());
        panel.add(btnAccept, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Shows game selection panel.
     */
    public void flipToGameSelection() {
        Main.getUI().setTitle("Select a game");
        setInGame(false);
        gameLayout.show(this, GAME_SELECTOR);
    }

    /**
     * Shows statistics panel.
     */
    void flipToStatistics() {
        gameLayout.show(this, STAT_PANEL);
    }

    /**
     * Gets a selected player from JList.
     */
    private Player getSelectedPlayer() {
        return playersList.getSelectedValue();
    }

    /**
     * Sets statistics to JTable.
     *
     * @param defaultTableModel filled table data model
     */
    void setStats(DefaultTableModel defaultTableModel) {
        statTable.setModel(defaultTableModel);
    }

    /**
     * Creates player selection panel.
     */
    private JPanel createPlayerSelectionPanel() {
        JPanel playerSelectPanel = new JPanel();
        playerSelectPanel.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        playerSelectPanel.add(new JScrollPane(playersList), BorderLayout.CENTER);
        JButton btnNewPlayer = new JButton("New player");
        btnNewPlayer.addActionListener(new NewPlayerButtonAction());
        buttonPanel.add(btnNewPlayer);
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener(new SelectPlayerButtonAction());
        buttonPanel.add(btnConfirm);
        JButton btnDelete = new JButton("Delete player");
        btnDelete.addActionListener(new DeletePlayerButtonAction());
        buttonPanel.add(btnDelete);
        playerSelectPanel.add(buttonPanel, BorderLayout.SOUTH);
        return playerSelectPanel;
    }

    /**
     * Show game panel.
     */
    void flipToGame() {
        if (!isInGame()) {
            gamePanel.start();
        }
        setInGame(true);
        Main.getUI().setTitle(Main.getGame().getGameName());
        gameLayout.show(this, GAME_PANEL);
    }

    /**
     * Shows a player selection panel with list filled with registered players.
     */
    void flipToPlayerSelection() {
        setInGame(false);
        Main.getUI().setTitle("Select a player");
        DefaultListModel<Player> listModel = new DefaultListModel<>();
        for (Player player : getGameController().getPlayers()) {
            listModel.addElement(player);
        }
        playersList.setModel(listModel);
        gameLayout.show(this, PLAYER_SELECTOR);
    }

    /**
     * Gets a game panel.
     */
    GamePanel getGamePanel() {
        return gamePanel;
    }

    void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Gets an in-game flag.
     */
    boolean isInGame() {
        return inGame;
    }

    /**
     * Sets an in-game flag.
     */
    void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * "Delete player" button action listener.
     */
    private final class DeletePlayerButtonAction extends AbstractAction {
        /**
         * Asks for a confirmation. If an answer is "Yes" then player is deleted.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            if (Main.getUI().confirm("Are you sure?")) {
                if (!getGameController().deletePlayer(getSelectedPlayer())) {
                    Main.getUI().alert(getGameController().getLastError());
                }
                flipToPlayerSelection();
            }
        }
    }

    /**
     * "Select player" button event listener.
     */
    private final class SelectPlayerButtonAction extends AbstractAction {
        /**
         * If a player is selected, sets it active. Else shows an error message.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            Player selected = getSelectedPlayer();
            if (getGameController().setActivePlayer(selected)) {
                flipToGame();
            } else {
                String error = getGameController().getLastError();
                Main.getUI().alert(error);
            }
        }
    }

    /**
     * "Return" button event listener.
     */
    private final class ReturnButtonAction extends AbstractAction {
        /**
         * Show game panel, player selection or game selection.
         * Depends on selection states.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isInGame()) {
                flipToGame();
            } else if (getGamePanel() == null) {
                flipToGameSelection();
            } else {
                flipToPlayerSelection();
            }
        }
    }

    /**
     * "New player" button action listener.
     */
    private final class NewPlayerButtonAction extends AbstractAction {
        /**
         * Registers a player if the player's name matches the necessary conditions.
         * Sets player active. If an error occurs, shows a message.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog("Enter your name");
            if (name == null || name.isEmpty()) {
                return;
            }
            name = name.trim();
            if (getGameController().register(name)) {
                flipToGame();
            } else {
                String error = getGameController().getLastError();
                Main.getUI().alert(error);
            }
        }
    }

    private class SelectGameButtonActionListener extends AbstractAction {
        /**
         * Sets an active game.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Game game;
            GamePanel panel;
            Player player = null;
            if (Main.getGame() != null) {
                player = Main.getGame().getPlayer();
            }
            switch (gamesList.getSelectedValue()) {
                case BlackjackGame.GAME_NAME:
                    game = new BlackjackGame();
                    panel = new BlackjackPanel();
                    break;
                case VideoPokerGame.GAME_NAME:
                    game = new VideoPokerGame();
                    panel = new VideoPokerPanel();
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
            Main.setGame(game);
            MainPanel.this.setGamePanel(panel);
            if (player == null) {
                flipToPlayerSelection();
            } else {
                Main.getGameController().setActivePlayer(player);
                flipToGame();
            }
            add(gamePanel, GAME_PANEL);
        }
    }
}
