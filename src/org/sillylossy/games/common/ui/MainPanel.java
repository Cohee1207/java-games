package org.sillylossy.games.common.ui;

import org.sillylossy.games.blackjack.game.BlackjackGame;
import org.sillylossy.games.blackjack.ui.BlackjackPanel;
import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.game.CardGame;
import org.sillylossy.games.common.players.Player;
import org.sillylossy.games.common.ui.listeners.DeletePlayerButtonListener;
import org.sillylossy.games.common.ui.listeners.NewPlayerButtonListener;
import org.sillylossy.games.common.ui.listeners.ReturnButtonListener;
import org.sillylossy.games.common.ui.listeners.SelectPlayerButtonListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

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
    private JList<Player> playersList = new JList<>();

    /**
     * A GUI-table of statistics.
     */
    private JTable statTable = new JTable();

    /**
     * Reference to game panel of selected game.
     */
    private GamePanel gamePanel;

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
        btnReturn.addActionListener(new ReturnButtonListener());
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
        JList<CardGame> gamesList = new JList<>();
        DefaultListModel<CardGame> listModel = new DefaultListModel<>();
        gamesList.setModel(listModel);
        listModel.addElement(new BlackjackGame());
        panel.add(new JScrollPane(gamesList), BorderLayout.CENTER);
        JButton btnAccept = new JButton("Accept");
        btnAccept.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardGame selected = gamesList.getSelectedValue();
                Main.setGameInstance(selected);
                setGame(selected);
                flipToPlayerSelection();
            }
        });
        panel.add(btnAccept, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Shows game selection panel.
     */
    public void flipToGameSelection() {
        gameLayout.show(this, GAME_SELECTOR);
    }

    /**
     * Shows statistics panel.
     */
    public void flipToStatistics() {
        if (Main.getGameInstance() != null) {
            gameLayout.show(this, STAT_PANEL);
        } else {
            Main.getUI().alert("Select a game first.");
        }
    }

    /**
     * Gets a selected player from JList.
     */
    public Player getSelectedPlayer() {
        return playersList.getSelectedValue();
    }

    /**
     * Sets statistics to JTable.
     *
     * @param defaultTableModel filled table data model
     */
    public void setStats(DefaultTableModel defaultTableModel) {
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
        btnNewPlayer.addActionListener(new NewPlayerButtonListener());
        buttonPanel.add(btnNewPlayer);
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener(new SelectPlayerButtonListener());
        buttonPanel.add(btnConfirm);
        JButton btnDelete = new JButton("Delete player");
        btnDelete.addActionListener(new DeletePlayerButtonListener());
        buttonPanel.add(btnDelete);
        playerSelectPanel.add(buttonPanel, BorderLayout.SOUTH);
        return playerSelectPanel;
    }

    /**
     * Show game panel.
     */
    public void flipToGame() {
        Main.getGameController().setInGame(true);
        gameLayout.show(this, GAME_PANEL);
    }

    /**
     * Shows a player selection panel with list filled with registered players.
     */
    public void flipToPlayerSelection() {
        Main.getGameController().setInGame(false);
        DefaultListModel<Player> listModel = new DefaultListModel<>();
        for (Player player : Main.getGameController().getPlayers()) {
            listModel.addElement(player);
        }
        playersList.setModel(listModel);
        gameLayout.show(this, PLAYER_SELECTOR);
    }

    /**
     * Gets a game panel.
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Sets an active game.
     *
     * @param selected a reference to "Game object"
     */
    private void setGame(CardGame selected) {
        if (Objects.equals(selected.getGameName(), BlackjackGame.GAME_NAME)) {
            gamePanel = new BlackjackPanel();
            add(gamePanel, GAME_PANEL);
        } else {
            // TODO: Add more games
            throw new UnsupportedOperationException();
        }
    }
}
