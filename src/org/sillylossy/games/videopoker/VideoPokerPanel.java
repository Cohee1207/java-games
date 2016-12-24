package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.players.Player;
import org.sillylossy.games.common.resources.CardImage;
import org.sillylossy.games.common.ui.CardPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.sillylossy.games.videopoker.PokerCombinations.Combination;

public class VideoPokerPanel extends CardPanel {

    private static final String PLAY_BUTTON_TEXT = "Play";

    private final JButton btnPlay = new JButton();

    private final JButton btnDiscard = new JButton("Discard");

    private final List<CardImage> cardImages = new ArrayList<>();

    private final JPanel cardsPanel = new JPanel();

    private final JTable payTable = new JTable();

    private final JScrollPane payTablePane = new JScrollPane(payTable);

    private final JLabel lblHint = createLabel("Click on cards you want to replace");
    private final ActionListener newGameButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            clear();
            start();
            btnPlay.removeActionListener(this);
            btnPlay.setText(PLAY_BUTTON_TEXT);
            btnPlay.addActionListener(playButtonListener);
            btnDiscard.setEnabled(true);
        }
    };
    private final ActionListener playButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (CardImage image : cardImages) {
                if (image.isFlipped()) {
                    image.setCard(getGame().changeCard(image.getCard()));
                    image.flip();
                    image.updateIcon();
                }
            }
            processResults();
        }
    };

    public VideoPokerPanel() {
        payTable.setEnabled(false);
        setLayout(createGameLayout());
        add(payTablePane, getGBC(0, GridBagConstraints.BOTH));
        add(lblHint, getGBC(1, GridBagConstraints.VERTICAL));
        add(cardsPanel, getGBC(2, GridBagConstraints.HORIZONTAL));
        add(createActionsPanel(), getGBC(3, GridBagConstraints.BOTH));
        cardsPanel.setBackground(BACKGROUND_COLOR);
    }

    private VideoPokerGame getGame() {
        return (VideoPokerGame) Main.getGame();
    }

    private DefaultTableModel createTableModel() {
        Object[][] vector = new Object[Combination.values().length][2];
        Map<PokerCombinations.Combination, Integer> table = getGame().getPayTable();
        int i = 0;
        for (Combination value : Combination.values()) {
            vector[i][0] = PokerCombinations.toString(value);
            vector[i][1] = table.get(value);
            ++i;
        }
        return new DefaultTableModel(vector, new String[]{"Combination", "Payout"});
    }

    /**
     * Creates grid bad layout for game.
     */
    private GridBagLayout createGameLayout() {
        GridBagLayout gbl = new GridBagLayout();
        gbl.rowHeights = new int[]{0, 0, 0, 0};
        gbl.columnWidths = new int[]{100};
        gbl.columnWeights = new double[]{1.0};
        gbl.rowWeights = new double[]{0.9, 0.0, 0.0, 0.0};
        return gbl;
    }

    @Override
    public void redraw() {
        for (CardImage image : cardImages) {
            image.updateIcon();
        }
    }

    @Override
    public void clear() {
        cardImages.clear();
        setActionButtons(false);
        cardsPanel.removeAll();
        lblHint.setText(" ");
    }

    @Override
    public void updateStatus() {
        Player player = getGame().getPlayer();
        String status = "Player: " + player.toString() +
                " bet: " + player.getBet() + "$ ";
        Main.getUI().updateStatus(status);
    }

    @Override
    public void start() {
        flipToBets();
    }

    @Override
    protected void processResults() {
        lblHint.setText(getGame().getResult());
        btnPlay.setText(NEW_GAME_BUTTON_TEXT);
        btnPlay.removeActionListener(playButtonListener);
        btnPlay.addActionListener(newGameButtonListener);
        btnDiscard.setEnabled(false);
    }

    @Override
    protected void setActionButtons(boolean b) {
        btnPlay.setVisible(b);
        btnDiscard.setVisible(b);
        payTablePane.setVisible(b);
        lblHint.setVisible(b);
        cardsPanel.setVisible(b);
    }

    @Override
    protected JPanel createGameActions() {
        JPanel actions = new JPanel();
        btnDiscard.addActionListener(new DiscardButtonAction());
        btnPlay.addActionListener(playButtonListener);
        btnPlay.setText(PLAY_BUTTON_TEXT);
        actions.add(btnPlay);
        actions.add(btnDiscard);
        return actions;
    }

    @Override
    public int getImageHeight() {
        return Main.getUI().getHeight() / 3;
    }

    @Override
    protected void initGame() {
        for (Card card : getGame().getPlayer().getHand().getCards()) {
            CardImage image = new CardImage(card, true);
            cardImages.add(image);
            cardsPanel.add(image.getLabel());
        }
        payTable.setModel(createTableModel());
    }

    private class DiscardButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getGame().discard();
            clear();
            start();
        }
    }
}
