package org.sillylossy.games.common.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand (cards set of game participant).
 */
public class Hand {

    /**
     * How many value points needed for "blackjack".
     */
    public final static int BLACKJACK = 21;

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

    /**
     * Returns a numeric value that represents a hand's value.
     * Cards with number have value of that number. Cards with picture (jack, queen, king)
     * have 10 points value. Ace is valued 1 or 11 (as player wants).
     */
    public int getValue() {
        int i = 0;
        int j = 0;
        for (Card card : cards) {
            switch (card.getCardFace()) {
                case ACE:
                    i += 11;
                    j += 1;
                    break;
                case EIGHT:
                    i += 8;
                    j += 8;
                    break;
                case FIVE:
                    i += 5;
                    j += 5;
                    break;
                case FOUR:
                    i += 4;
                    j += 4;
                    break;
                case JACK:
                    i += 10;
                    j += 10;
                    break;
                case KING:
                    i += 10;
                    j += 10;
                    break;
                case NINE:
                    i += 9;
                    j += 9;
                    break;
                case QUEEN:
                    i += 10;
                    j += 10;
                    break;
                case SEVEN:
                    i += 7;
                    j += 7;
                    break;
                case SIX:
                    i += 6;
                    j += 6;
                    break;
                case TEN:
                    i += 10;
                    j += 10;
                    break;
                case THREE:
                    i += 3;
                    j += 3;
                    break;
                case TWO:
                    i += 2;
                    j += 2;
                    break;
                default:
                    break;
            }
        }
        return i > BLACKJACK ? j : i;
    }

    /**
     * Determines whether a player has "blackjack" (2 cards, total 21 value).
     */
    public boolean isBlackJack() {
        return cards.size() == 2 && getValue() == BLACKJACK;
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
