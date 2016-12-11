package org.sillylossy.games.blackjack.ui;

import org.sillylossy.games.blackjack.game.BlackjackGame;
import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.players.Player;
import org.sillylossy.games.common.ui.GameListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Game panel for blackjack.
 */
public class BlackjackPanel extends org.sillylossy.games.common.ui.BetPanel {

    /**
     * Panel for other player's cards.
     */
    private final JPanel dealersCardsPanel = new JPanel();

    /**
     * Label for other player's cards panel.
     */
    private final JLabel lblDealersCards = createLabel("Dealer's cards");

    /**
     * Label for player's cards panel.
     */
    private final JLabel lblPlayersCards = createLabel("Your cards");

    /**
     * Panel for player's cards.
     */
    private final JPanel playersCardsPanel = new JPanel();

    /**
     * Blackjack game instance.
     */
    private final BlackjackGame gameInstance = (BlackjackGame) Main.getGameInstance();

    /**
     * "Double" game button.
     */
    private final JButton btnDouble = new JButton("Double");

    /**
     * "Hit" game button.
     */
    private final JButton btnHit = new JButton("Hit");

    /**
     * "Stand" game button.
     */
    private final JButton btnStand = new JButton("Stand");

    /**
     * Creates a game area on panel with all needed visuals.
     */
    public BlackjackPanel() {
        gameArea.setLayout(createGameLayout());
        dealersCardsPanel.setBackground(new Color(0, 100, 0));
        playersCardsPanel.setBackground(new Color(0, 100, 0));
        gameArea.add(lblDealersCards, getGBC(0, GridBagConstraints.VERTICAL));
        gameArea.add(dealersCardsPanel, getGBC(1, GridBagConstraints.BOTH));
        gameArea.add(lblPlayersCards, getGBC(2, GridBagConstraints.VERTICAL));
        gameArea.add(playersCardsPanel, getGBC(3, GridBagConstraints.BOTH));
        gameArea.add(createActionsPanel(), getGBC(4, GridBagConstraints.BOTH));
    }

    /**
     * Creates constraints for game's components.
     *
     * @param y    vertical coordinate
     * @param fill filling flag
     * @return component's constraints objects
     */
    private static GridBagConstraints getGBC(int y, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = fill;
        gbc.gridx = 0;
        gbc.gridy = y;
        return gbc;
    }

    /**
     * Clear and revalidate card panels.
     */
    private void clearCards() {
        playersCardsPanel.removeAll();
        dealersCardsPanel.removeAll();
        playersCardsPanel.revalidate();
        dealersCardsPanel.revalidate();
    }

    /**
     * Displays player's card images with height = window / 3 (experimental value).
     */
    private void displayPlayersCards() {
        int height = Main.getUI().getHeight() / 3;
        playersCardsPanel.removeAll();
        if (gameInstance.getPlayer() == null) {
            return;
        }
        Card[] cards = gameInstance.getPlayer().getHand().getCards();
        for (Card card : cards) {
            playersCardsPanel.add(images.getCardImage(card, height));
        }
    }

    /**
     * Processes game event with taking cards.
     *
     * @param card taken card
     */
    private void cardButtonAction(Card card) {
        int height = Main.getUI().getHeight() / 3;
        playersCardsPanel.add(images.getCardImage(card, height));
        updateStatus();
        if (gameInstance.shouldEnd()) {
            processResults();
        }
    }

    /**
     * Displays dealer's open card and a card back.
     */
    private void displayOpenCard() {
        int height = Main.getUI().getHeight() / 3;
        dealersCardsPanel.removeAll();
        Card card = gameInstance.getDealer().getOpenCard();
        if (card == null) {
            return;
        }
        dealersCardsPanel.add(images.getCardImage(card, height));
        dealersCardsPanel.add(images.getBackImage(height));
    }

    /**
     * Displays dealer's cards.
     */
    private void displayDealersCards() {
        int height = Main.getUI().getHeight() / 3;
        Card[] cards = gameInstance.getDealer().getHand().getCards();
        dealersCardsPanel.removeAll();
        for (Card card : cards) {
            dealersCardsPanel.add(images.getCardImage(card, height));
        }
    }

    /**
     * Creates label with specified text, white foreground and Arial font.
     *
     * @param text text on the label
     */
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(255, 255, 255));
        lbl.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        return lbl;
    }

    @Override
    public void start() {
        flipToBets();
    }

    /**
     * Updates player's status in status bar.
     */
    protected void updateStatus() {
        Player player = gameInstance.getPlayer();
        String status = "Player: " + player.toString() +
                " bet: " + player.getBet() + "$ " +
                "Hand value: " + gameInstance.getValue(player.getHand().getCards());
        lblStatus.setText(status);
    }

    /**
     * Processes game results, shows a result message.
     */
    protected void processResults() {
        gameInstance.getDealer().play(gameInstance.getDeck());
        displayDealersCards();
        invalidate();
        Main.getUI().alert(gameInstance.getResult());
        flipToBets();
        clearCards();
    }

    /**
     * Creates grid bad layout for game.
     */
    private GridBagLayout createGameLayout() {
        GridBagLayout gbl = new GridBagLayout();
        gbl.rowHeights = new int[]{25, 0, 25, 0, 0, 0};
        gbl.columnWidths = new int[]{100};
        gbl.columnWeights = new double[]{1.0};
        gbl.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        return gbl;
    }

    @Override
    public void redraw() {
        displayPlayersCards();
        displayOpenCard();
    }

    /**
     * Sets action buttons on / off depending on boolean value.
     */
    public void setActionButtons(boolean b) {
        btnHit.setEnabled(b);
        btnStand.setEnabled(b);
        btnDouble.setEnabled(b);
        lblPlayersCards.setVisible(b);
        playersCardsPanel.setVisible(b);
        dealersCardsPanel.setVisible(b);
        lblDealersCards.setVisible(b);
    }

    @Override
    protected JPanel createGameActions() {
        JPanel actionButtonsPanel = new JPanel();
        btnHit.addActionListener(new HitButtonListener());
        actionButtonsPanel.add(btnHit);
        btnStand.addActionListener(new StandButtonListener());
        actionButtonsPanel.add(btnStand);
        btnDouble.addActionListener(new DoubleButtonListener());
        actionButtonsPanel.add(btnDouble);
        actionButtonsPanel.add(Box.createHorizontalStrut(20));
        return  actionButtonsPanel;
    }

    /**
     * "Hit" game button action listener.
     */
    private final class HitButtonListener extends GameListener {
        /**
         * Calls a "take card" game event. Adds a taken card to panel.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            cardButtonAction(gameInstance.hitAction());
        }
    }

    /**
     * "Double" game action button action listener.
     */
    private final class DoubleButtonListener extends GameListener {
        /**
         * Calls "double bet" game event. If player has insufficient score, shows a message.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = gameInstance.doubleAction();
            if (card == null) {
                Main.getUI().alert("You don't have enough score to double your bet.");
                return;
            }
            cardButtonAction(card);
        }
    }

    /**
     * "Stand" button action listener.
     */
    private final class StandButtonListener extends GameListener {
        /**
         * Performs "stand" game action.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            gameInstance.standAction();
            processResults();
        }
    }

}
