package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.players.Player;
import org.sillylossy.games.common.ui.BetPanel;
import org.sillylossy.games.common.ui.images.CardImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


public class VideoPokerPanel extends BetPanel {

    private final JButton btnPlay = new JButton("Play");
    private List<CardImage> cardImages = new ArrayList<>();

    private JPanel cardsPanel;
    private JLabel lblReplaceHint = createLabel("Click on cards that you want to replace");
    private VideoPokerGame gameInstance = (VideoPokerGame) Main.getGameInstance();

    public VideoPokerPanel() {
        cardsPanel = new JPanel();
        gameArea.setLayout(createGameLayout());
        gameArea.add(lblReplaceHint, getGBC(0, GridBagConstraints.NONE));
        gameArea.add(cardsPanel, getGBC(1, GridBagConstraints.BOTH));
        gameArea.add(createActionsPanel(), getGBC(2, GridBagConstraints.BOTH));
        cardsPanel.setBackground(new Color(34, 139, 34));
    }

    /**
     * Creates grid bad layout for game.
     */
    private GridBagLayout createGameLayout() {
        GridBagLayout gbl = new GridBagLayout();
        gbl.rowHeights = new int[]{0, 0, 0};
        gbl.columnWidths = new int[]{100};
        gbl.columnWeights = new double[]{1.0};
        gbl.rowWeights = new double[]{0.0, 1.0, 0.0};
        return gbl;
    }

    @Override
    public void redraw() {
        for (CardImage image : cardImages) {
            image.updateIcon();
        }
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

    }

    @Override
    public void setActionButtons(boolean b) {
        btnPlay.setEnabled(b);
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
                gameInstance.getResult();
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
    }
}
