package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.players.Player;
import org.sillylossy.games.common.ui.BetPanel;
import org.sillylossy.games.common.ui.images.CardImage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.sillylossy.games.videopoker.PokerCombinations.Combination;
import static org.sillylossy.games.videopoker.PokerCombinations.combinationStringMap;


public class VideoPokerPanel extends BetPanel {

    private final JButton btnPlay = new JButton("Play");
    private List<CardImage> cardImages = new ArrayList<>();

    private JPanel cardsPanel;
    private JTable payTable = new JTable();
    private JScrollPane payTablePane = new JScrollPane(payTable);
    private JLabel lblReplaceHint = createLabel("Click on cards you want to replace");
    private VideoPokerGame gameInstance = (VideoPokerGame) Main.getGameInstance();

    public VideoPokerPanel() {
        cardsPanel = new JPanel();
        payTable.setEnabled(false);
        gameArea.setLayout(createGameLayout());
        gameArea.add(payTablePane, getGBC(0, GridBagConstraints.BOTH));
        gameArea.add(cardsPanel, getGBC(1, GridBagConstraints.HORIZONTAL));
        gameArea.add(lblReplaceHint, getGBC(2, GridBagConstraints.NONE));
        gameArea.add(createActionsPanel(), getGBC(3, GridBagConstraints.BOTH));
        cardsPanel.setBackground(new Color(34, 139, 34));
    }

    private DefaultTableModel createTableModel() {
        Object[][] vector = new Object[Combination.values().length][2];
        Map<PokerCombinations.Combination, Integer> table = gameInstance.getPayTable();
        int i = 0;
        for (Map.Entry<PokerCombinations.Combination, String> entry : combinationStringMap.entrySet()) {
            vector[i][0] = entry.getValue();
            vector[i][1] = table.get(entry.getKey());
            ++i;
        }
        return new DefaultTableModel(vector, new String[]{"Combination", "Pay"});
    }

    /**
     * Creates grid bad layout for game.
     */
    private GridBagLayout createGameLayout() {
        GridBagLayout gbl = new GridBagLayout();
        gbl.rowHeights = new int[]{0, 0, 0, 0};
        gbl.columnWidths = new int[]{100};
        gbl.columnWeights = new double[]{1.0};
        gbl.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0};
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
        cardsPanel.removeAll();
    }

    @Override
    public void updateStatus() {
        Player player = gameInstance.getPlayer();
        String status = "Player: " + player.toString() +
                " bet: " + player.getBet() + "$ ";
        lblStatus.setText(status);
    }

    @Override
    public void start() {
        flipToBets();
    }

    @Override
    public void processResults() {
        Main.getUI().alert(gameInstance.getResult());
        clear();
        start();
    }

    @Override
    public void setActionButtons(boolean b) {
        btnPlay.setEnabled(b);
        payTablePane.setVisible(b);
        lblReplaceHint.setVisible(b);
        cardsPanel.setVisible(b);
    }

    @Override
    protected JPanel createGameActions() {
        JPanel actions = new JPanel();
        btnPlay.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (CardImage image : cardImages) {
                    if (image.isFlipped()) {
                        image.setCard(gameInstance.changeCard(image.getCard()));
                        image.flip();
                    }
                }
                processResults();
            }
        });
        actions.add(btnPlay);
        return actions;
    }

    @Override
    protected void initGame() {
        for (Card card : gameInstance.getPlayer().getHand().getCards()) {
            CardImage image = new CardImage(card, true);
            cardImages.add(image);
            cardsPanel.add(image.getLabel());
        }
        payTable.setModel(createTableModel());
    }
}
