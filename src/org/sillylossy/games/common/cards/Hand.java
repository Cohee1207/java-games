package org.sillylossy.games.common.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand (cards set of game participant).
 */
public class Hand {

    /**
     * A list of cards in hand.
     */
    private final List<Card> cards = new ArrayList<>();

    /**
     * Adds card to a hand.
     *
     * @param card card that needs to be added
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Clears a hand (deletes all cards from list).
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Copies all the cards from hand into a new array.
     */
    public Card[] getCards() {
        return cards.toArray(new Card[cards.size()]);
    }

    public void replaceCard(Card oldCard, Card newCard) {
        cards.remove(oldCard);
        cards.add(newCard);
    }

    /**
     * Returns a top card from a hand.
     *
     * @return top card or null if the hand is empty
     */
    public Card peek() {
        if (cards.size() == 0) {
            return null;
        }
        return cards.get(0);
    }

}
