package org.sillylossy.games.common.ui.images;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CardImage {
    /**
     * An active images controller.
     */
    private static final CardImageController images = new CardImageController();
    private final JLabel label;
    private Card card;
    private boolean isFlipped = false;

    public CardImage(Card card, boolean flipOnClick) {
        this.card = card;
        this.label = new JLabel();
        if (flipOnClick) {
            this.label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    flip();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        updateIcon();
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card newCard) {
        card = newCard;
    }

    public JLabel getLabel() {
        return label;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void updateIcon() {
        label.setIcon(isFlipped ?
                new ImageIcon(images.getBackImage(getDesiredHeight())) :
                new ImageIcon(images.getCardImage(card, getDesiredHeight())));
    }

    public void flip() {
        isFlipped = !isFlipped;
        updateIcon();
    }

    private int getDesiredHeight() {
        return Main.getUI().getHeight() / 3;
    }
}
