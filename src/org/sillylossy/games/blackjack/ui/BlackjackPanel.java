package org.sillylossy.games.blackjack.ui;

import org.sillylossy.games.blackjack.game.BlackjackGame;
import org.sillylossy.games.blackjack.ui.listeners.AcceptBetButtonListener;
import org.sillylossy.games.blackjack.ui.listeners.DoubleButtonListener;
import org.sillylossy.games.blackjack.ui.listeners.HitButtonListener;
import org.sillylossy.games.blackjack.ui.listeners.StandButtonListener;
import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.players.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Game panel for blackjack.
 */
public class BlackjackPanel extends org.sillylossy.games.common.ui.GamePanel {

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
     * JSpinner for betting.
     */
    private final JSpinner betSpinner = new JSpinner();
    /**
     * Spinner model holds values used for betting (min, max, step).
     */
    private final SpinnerNumberModel betSpinnerModel = new SpinnerNumberModel();
    /**
     * "Accept" bet button.
     */
    private final JButton btnBet = new JButton("Accept");
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
     * Label with games status (current player, hand value, etc).
     */
    private final JLabel lblStatus = new JLabel();

    /**
     * Creates a game panel with all needed visuals.
     */
    public BlackjackPanel() {
        setLayout(createGameLayout());
        dealersCardsPanel.setBackground(new Color(0, 100, 0));
        playersCardsPanel.setBackground(new Color(0, 100, 0));
        add(lblDealersCards, getGBC(0, GridBagConstraints.VERTICAL));
        add(dealersCardsPanel, getGBC(1, GridBagConstraints.BOTH));
        add(lblPlayersCards, getGBC(2, GridBagConstraints.VERTICAL));
        add(playersCardsPanel, getGBC(3, GridBagConstraints.BOTH));
        add(createActionButtonsPanel(), getGBC(4, GridBagConstraints.BOTH));
        add(createStatusBar(), getGBC(5, GridBagConstraints.BOTH));
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
     * Gets selected bet value.
     */
    public int getBetValue() {
        return (int) betSpinner.getValue();
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
     * Creates action buttons (game, bets) panel.
     */
    private JPanel createActionButtonsPanel() {
        JPanel actionButtonsPanel = new JPanel();
        btnHit.addActionListener(new HitButtonListener());
        actionButtonsPanel.add(btnHit);
        btnStand.addActionListener(new StandButtonListener());
        actionButtonsPanel.add(btnStand);
        btnDouble.addActionListener(new DoubleButtonListener());
        actionButtonsPanel.add(btnDouble);
        actionButtonsPanel.add(Box.createHorizontalStrut(20));
        actionButtonsPanel.add(new JLabel("Bet:"));
        betSpinner.setModel(betSpinnerModel);
        actionButtonsPanel.add(betSpinner);
        actionButtonsPanel.add(btnBet);
        btnBet.addActionListener(new AcceptBetButtonListener());
        return actionButtonsPanel;
    }

    /**
     * Creates status bar with status label.
     */
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.add(lblStatus);
        return statusBar;
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
    public void cardButtonAction(Card card) {
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
     * Checks player's score. If it's too low, shows a proposal to reset score.
     *
     * @return check result. false if player has enough score or did reset it.
     * true if player didn't want to reset score
     */
    private boolean checkScore() {
        Player player = gameInstance.getPlayer();
        final int LOW_SCORE = 20;
        if (player.getScore() <= LOW_SCORE) {
            if (Main.getUI().confirm(" You almost have 0 $ \n Reset score?")) {
                player.resetScore();
                return false;
            } else {
                Main.getUI().getMainPanel().flipToPlayerSelection();
                return true;
            }
        }
        return false;
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
    public void updateStatus() {
        Player player = gameInstance.getPlayer();
        String status = "Player: " + player.toString() +
                " bet: " + player.getBet() + "$ " +
                "Hand value: " + player.getHand().getValue();
        lblStatus.setText(status);
    }

    /**
     * Processes game results, shows a result message.
     */
    public void processResults() {
        gameInstance.getDealer().play(gameInstance.getDeck());
        displayDealersCards();
        invalidate();
        Main.getUI().alert(gameInstance.getResult());
        flipToBets();
    }

    /**
     * Shows bet selection mode.
     */
    private void flipToBets() {
        if (checkScore()) {
            return;
        }
        setBetButtons(true);
        setActionButtons(false);
        clearCards();
        updateStatus();
        int min = gameInstance.getMinBet();
        int max = gameInstance.getMaxBet();
        betSpinnerModel.setMinimum(min);
        betSpinnerModel.setMaximum(max);
        betSpinnerModel.setStepSize(max / 10);
        betSpinnerModel.setValue(min);
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

    /**
     * Sets bet buttons on / off depending on boolean value.
     */
    public void setBetButtons(boolean b) {
        betSpinner.setEnabled(b);
        btnBet.setEnabled(b);
    }
}
