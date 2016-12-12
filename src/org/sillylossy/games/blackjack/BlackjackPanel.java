package org.sillylossy.games.blackjack;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.players.Player;
import org.sillylossy.games.common.ui.images.CardImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private ArrayList<CardImage> playersCardsImages = new ArrayList<>();
    private ArrayList<CardImage> dealersCardsImages = new ArrayList<>();

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

    @Override
    protected void initGame() {
        CardImage image;
        for (Card card : gameInstance.getDealer().getHand().getCards()) {
            image = new CardImage(card, false);
            dealersCardsImages.add(image);
            dealersCardsPanel.add(image.getLabel());
        }
        dealersCardsImages.get(dealersCardsImages.size() - 1).flip();
        for (Card card : gameInstance.getPlayer().getHand().getCards()) {
            image = new CardImage(card, false);
            playersCardsImages.add(image);
            playersCardsPanel.add(image.getLabel());
        }
    }

    /**
     * Clear and revalidate card panels.
     */
    private void clearCards() {
        playersCardsPanel.removeAll();
        playersCardsImages.clear();
        dealersCardsPanel.removeAll();
        dealersCardsImages.clear();
        playersCardsPanel.revalidate();
        dealersCardsPanel.revalidate();
    }

    /**
     * Displays player's card images with height = window / 3 (experimental value).
     */
    private void displayPlayersCards() {
        if (gameInstance.getPlayer() == null) {
            return;
        }
        for (CardImage image : playersCardsImages) {
            image.updateIcon();
        }
    }

    /**
     * Processes game event with taking cards.
     *
     * @param card taken card
     */
    private void cardButtonAction(Card card) {
        CardImage image = new CardImage(card, false);
        playersCardsImages.add(image);
        playersCardsPanel.add(image.getLabel());
        updateStatus();
        if (gameInstance.shouldEnd()) {
            processResults();
        }
    }

    /**
     * Displays dealer's open card and a card back.
     */
    private void displayOpenCard() {
        Card card = gameInstance.getDealer().getOpenCard();
        if (card == null) {
            return;
        }
        for (CardImage image : dealersCardsImages) {
            image.updateIcon();
        }
    }

    /**
     * Displays dealer's cards.
     */
    private void displayDealersCards() {
        for (CardImage image : dealersCardsImages) {
            image.updateIcon();
        }
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
    private final class HitButtonListener implements ActionListener {
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
    private final class DoubleButtonListener implements ActionListener {
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
    private final class StandButtonListener implements ActionListener {
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
