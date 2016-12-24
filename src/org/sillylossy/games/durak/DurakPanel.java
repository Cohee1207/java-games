package org.sillylossy.games.durak;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardRank;
import org.sillylossy.games.common.cards.CardSuit;
import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.resources.CardImage;
import org.sillylossy.games.common.ui.CardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public final class DurakPanel extends CardPanel {

    private Vector<CardImage> playersCardImages = new Vector<>();
    private BufferedImage deckImage;
    private JPanel deck = new DeckPanel();
    private JPanel opponentsCards = new JPanel();
    private JPanel playersCards = new JPanel();
    private JButton btnTake = new JButton("Take");
    private JButton btnStop = new JButton("Stop");
    private JButton btnContinue = new JButton("Continue");

    public DurakPanel() {
        setLayout(new BorderLayout());
        opponentsCards.setBackground(BACKGROUND_COLOR);
        playersCards.setBackground(BACKGROUND_COLOR);
        add(createActionsPanel(), BorderLayout.SOUTH);
        JPanel gameArea = new JPanel();
        add(gameArea, BorderLayout.CENTER);
        GridBagLayout layout = new GridBagLayout();
        layout.rowWeights = new double[]{0.3, 0.4, 0.3};
        layout.rowHeights = new int[]{0, 0, 0};
        layout.columnWidths = new int[]{0};
        layout.columnWeights = new double[]{1.0};
        gameArea.setLayout(layout);
        gameArea.add(opponentsCards, getGBC(0, GridBagConstraints.BOTH));
        gameArea.add(playersCards, getGBC(2, GridBagConstraints.BOTH));
        JPanel table = new JPanel();
        gameArea.add(table, getGBC(1, GridBagConstraints.BOTH));
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWeights = new double[]{0.3, 0.7};
        gbl.rowHeights = new int[]{150};
        gbl.rowWeights = new double[]{0.0};
        table.setLayout(gbl);
        table.setBackground(BACKGROUND_COLOR);
        table.add(deck, getGBC(0));
        JPanel tableCards = new JPanel();
        tableCards.setBackground(BACKGROUND_COLOR);
        table.add(tableCards, getGBC(1));
    }

    @Override
    protected void redraw() {
        drawDeck();
        drawPlayersCards();
        drawOpponentsCards();
    }

    private void drawOpponentsCards() {
        Card[] opponentsHand = getGame().getOpponent().getHand().getCards();
        int onPanel = opponentsCards.getComponentCount();
        int inHand = opponentsHand.length;
        if (inHand > onPanel) {
            for (int i = 0; i < (inHand - onPanel); i++) {
                opponentsCards.add(new JLabel(new ImageIcon(mgr.getBackImage())));
            }
        } else if (onPanel > inHand) {
            for (int i = 0; i < (onPanel - inHand); i++) {
                opponentsCards.remove(i);
            }
        }
    }

    private void drawPlayersCards() {
        Card[] playersHand = getGame().getPlayer().getHand().getCards();
        for (Card card : playersHand) {
            if (!containsImage(playersCardImages, card)) {
                CardImage image = new CardImage(card, new CardMouseListener(card));
                playersCardImages.add(image);
                playersCards.add(image.getLabel());
            }
        }
        java.util.List<CardImage> toRemove = new ArrayList<>();
        for (CardImage image : playersCardImages) {
            if (!containsCard(playersHand, image.getCard())) {
                toRemove.add(image);
            }
        }
        for (CardImage image : toRemove) {
            playersCardImages.remove(image);
            playersCards.remove(image.getLabel());
        }
    }

    private boolean containsCard(Card[] playersHand, Card card) {
        for (Card crd : playersHand) {
            if (card.equals(crd)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsImage(Iterable<CardImage> images, Card card) {
        for (CardImage image : images) {
            if (image.getCard().equals(card)) {
                return true;
            }
        }
        return false;
    }

    private void drawDeck() {
        deckImage = new BufferedImage(deck.getWidth(), deck.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Deck deck = getGame().getDeck();
        Card trumpCard = getGame().getTrumpCard();
        Graphics g = deckImage.getGraphics();
        if (deck == null || trumpCard == null) {
            return;
        }
        if (!deck.isEmpty()) {
            int marginX = 5;
            int marginY = 5;
            int cardsLeft = deck.cardsLeft();
            Image cardImg = mgr.getCardImage(trumpCard);
            g.drawImage(cardImg, marginX * 4 + cardsLeft, marginY, this);
            Image backImg = mgr.getBackImage();
            for (int i = 0; i < cardsLeft - 1; i++) {
                g.drawImage(backImg, marginX++, marginY, this);
            }
        } else {
            // draw suit icon
        }
    }

    private DurakGame getGame() {
        return (DurakGame) Main.getGame();
    }

    private GridBagConstraints getGBC(int x) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        return constraints;
    }

    @Override
    public void clear() {
        opponentsCards.removeAll();
        playersCards.removeAll();
    }

    @Override
    protected void updateStatus() {

    }

    @Override
    protected void initGame() {
        repaint();

    }

    @Override
    public void start() {
        flipToBets();
    }

    @Override
    protected void processResults() {

    }

    @Override
    protected void setActionButtons(boolean b) {
        btnTake.setVisible(b);
        btnStop.setVisible(b);
        btnContinue.setVisible(b);
    }

    @Override
    protected JPanel createGameActions() {
        JPanel actionButtons = new JPanel();
        actionButtons.add(btnTake);
        btnTake.addActionListener(new TakeButtonAction());
        actionButtons.add(btnStop);
        actionButtons.add(btnContinue);
        return actionButtons;
    }

    @Override
    public int getImageHeight() {
        return Math.round(Main.getUI().getHeight() / 4.5f);
    }

    private class CardMouseListener implements MouseListener {

        private Card card;

        private CardMouseListener(Card card) {
            this.card = card;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (ThreadLocalRandom.current().nextInt() % 2 == 0) {
                getGame().getOpponent().getHand().addCard(new Card(CardRank.ACE, CardSuit.DIAMONDS));
                getGame().getPlayer().getHand().addCard(new Card(CardRank.ACE, CardSuit.DIAMONDS));
            } else {
                getGame().getOpponent().getHand().removeCard(new Card(CardRank.ACE, CardSuit.DIAMONDS));
                getGame().getPlayer().getHand().removeCard(new Card(CardRank.ACE, CardSuit.DIAMONDS));
            }
            redraw();
        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }

    private class DeckPanel extends JPanel {

        private DeckPanel() {
            setBackground(BACKGROUND_COLOR);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (deckImage != null) {
                g.drawImage(deckImage, 0, 0, this);
            }
        }
    }

    private final class TakeButtonAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            getGame().takeAction();
        }
    }
}
