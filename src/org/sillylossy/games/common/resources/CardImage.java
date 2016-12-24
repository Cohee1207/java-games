package org.sillylossy.games.common.resources;

import org.sillylossy.games.common.cards.Card;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CardImage {
    /**
     * An active resources controller.
     */
    private final JLabel label = new JLabel();
    private Card card;
    private boolean isFlipped = false;

    public CardImage(Card card, boolean flipOnClick) {
        this.card = card;
        if (flipOnClick) {
            this.label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    flip();
                    updateIcon();
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

    public CardImage(Card card, MouseListener mouseListener) {
        this.card = card;
        this.label.addMouseListener(mouseListener);
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
                new ImageIcon(ResourceManager.getInstance().getBackImage()) :
                new ImageIcon(ResourceManager.getInstance().getCardImage(card)));
    }

    public void flip() {
        isFlipped = !isFlipped;
        updateIcon();
    }
}
