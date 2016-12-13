package org.sillylossy.games.blackjack;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.players.Player;
import org.sillylossy.games.common.ui.images.CardImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Game panel for blackjack.
 */
public class BlackjackPanel extends org.sillylossy.games.common.ui.BetPanel {

    private static final String YOUR_CARDS_TEXT = "Your cards";
    /**
     * Panel for other player's cards.
     */
    private final JPanel dealersCardsPanel = new JPanel();
    /**
     * Label for other player's cards panel.
     */
    private final JLabel lblDealersCards = createLabel("Dealer: draw on 16, stand on 17");
    /**
     * Label for player's cards panel.
     */
    private final JLabel lblPlayersCards = createLabel(YOUR_CARDS_TEXT);
    /**
     * Panel for player's cards.
     */
    private final JPanel playersCardsPanel = new JPanel();
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
    private final ArrayList<CardImage> playersCardsImages = new ArrayList<>();
    private final ArrayList<CardImage> dealersCardsImages = new ArrayList<>();
    private final JPanel actionButtonsPanel = new JPanel();
    private final JButton btnNewGame = new JButton("New game");

    /**
     * Creates a game area on panel with all needed visuals.
     */
    public BlackjackPanel() {
        gameArea.setLayout(createGameLayout());
        dealersCardsPanel.setBackground(BACKGROUND_COLOR.darker());
        playersCardsPanel.setBackground(BACKGROUND_COLOR.darker());
        gameArea.add(lblDealersCards, getGBC(0, GridBagConstraints.VERTICAL));
        gameArea.add(dealersCardsPanel, getGBC(1, GridBagConstraints.BOTH));
        gameArea.add(lblPlayersCards, getGBC(2, GridBagConstraints.VERTICAL));
        gameArea.add(playersCardsPanel, getGBC(3, GridBagConstraints.BOTH));
        btnNewGame.addActionListener(new NewGameButtonAction());
        actionButtonsPanel.add(btnNewGame);
        btnNewGame.setVisible(false);
        JPanel actionsPanel = createActionsPanel();
        JPanel panel = new JPanel();
        panel.add(btnNewGame);
        actionsPanel.add(panel);
        gameArea.add(actionsPanel, getGBC(4, GridBagConstraints.BOTH));
    }

    /**
     * Blackjack game instance.
     */
    private BlackjackGame getGame() {
        return (BlackjackGame) Main.getGame();
    }

    @Override
    protected void initGame() {
        CardImage image;
        for (Card card : getGame().getDealer().getHand().getCards()) {
            image = new CardImage(card, false);
            dealersCardsImages.add(image);
            dealersCardsPanel.add(image.getLabel());
        }
        image = dealersCardsImages.get(dealersCardsImages.size() - 1);
        image.flip();
        image.updateIcon();
        for (Card card : getGame().getPlayer().getHand().getCards()) {
            image = new CardImage(card, false);
            playersCardsImages.add(image);
            playersCardsPanel.add(image.getLabel());
        }
    }

    /**
     * Clear and revalidate card panels.
     */
    @Override
    public void clear() {
        playersCardsPanel.removeAll();
        playersCardsImages.clear();
        dealersCardsPanel.removeAll();
        dealersCardsImages.clear();
        lblPlayersCards.setText(YOUR_CARDS_TEXT);
    }

    /**
     * Displays player's card images with height = window / 3 (experimental value).
     */
    private void displayPlayersCards() {
        if (getGame().getPlayer() == null) {
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
        if (getGame().shouldEnd()) {
            processResults();
        }
    }

    /**
     * Displays dealer's open card and a card back.
     */
    private void displayOpenCard() {
        Card card = getGame().getDealer().getOpenCard();
        if (card == null) {
            return;
        }
        for (CardImage image : dealersCardsImages) {
            image.updateIcon();
        }
    }

    /**
     * Displays dealer's cards.
     * @param play cards taken by dealer
     */
    private void displayDealersCards(List<Card> play) {
        for (CardImage image : dealersCardsImages) {
            if (image.isFlipped()) {
                image.flip();
            }
            image.updateIcon();
        }
        for (Card card : play) {
            CardImage image = new CardImage(card, false);
            dealersCardsImages.add(image);
            dealersCardsPanel.add(image.getLabel());
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
        Player player = getGame().getPlayer();
        String status = "Player: " + player.toString() +
                " bet: " + player.getBet() + "$ " +
                "Hand value: " + getGame().getValue(player.getHand().getCards());
        lblStatus.setText(status);
    }

    /**
     * Processes game results, shows a result message.
     */
    protected void processResults() {
        displayDealersCards(getGame().getDealer().play(getGame().getDeck()));
        lblPlayersCards.setText(getGame().getResult());
        actionButtonsPanel.setVisible(false);
        btnNewGame.setVisible(true);
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
        if (!btnNewGame.isVisible()) {
            displayOpenCard();
        } else {
            displayDealersCards(new ArrayList<Card>());
        }
    }

    /**
     * Sets action buttons on / off depending on boolean value.
     */
    public void setActionButtons(boolean b) {
        actionButtonsPanel.setVisible(b);
        lblPlayersCards.setVisible(b);
        lblDealersCards.setVisible(b);
        playersCardsPanel.setVisible(b);
        dealersCardsPanel.setVisible(b);
    }

    @Override
    protected JPanel createGameActions() {
        btnHit.addActionListener(new HitButtonAction());
        actionButtonsPanel.add(btnHit);
        btnStand.addActionListener(new StandButtonAction());
        actionButtonsPanel.add(btnStand);
        btnDouble.addActionListener(new DoubleButtonAction());
        actionButtonsPanel.add(btnDouble);
        return actionButtonsPanel;
    }

    private final class NewGameButtonAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            clear();
            start();
            btnNewGame.setVisible(false);
        }
    }

    /**
     * "Hit" game button action listener.
     */
    private final class HitButtonAction extends AbstractAction {
        /**
         * Calls a "take card" game event. Adds a taken card to panel.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            cardButtonAction(getGame().hitAction());
        }
    }

    /**
     * "Double" game action button action listener.
     */
    private final class DoubleButtonAction extends AbstractAction {
        /**
         * Calls "double bet" game event. If player has insufficient score, shows a message.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = getGame().doubleAction();
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
    private final class StandButtonAction extends AbstractAction {
        /**
         * Performs "stand" game action.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            getGame().standAction();
            processResults();
        }
    }

}
