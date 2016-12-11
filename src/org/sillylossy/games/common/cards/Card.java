package org.sillylossy.games.common.cards;

/**
 * Represents a playing card.
 */
public class Card {

    /**
     * Contains a card rank enum value.
     */
    private final CardRank cardRank;

    /**
     * Contains a card suit enum value.
     */
    private final CardSuit cardSuit;

    /**
     * Sets a fields values from params.
     *
     * @param cardRank a card's rank
     * @param cardSuit a card's suit
     */
    public Card(CardRank cardRank, CardSuit cardSuit) {
        super();
        this.cardRank = cardRank;
        this.cardSuit = cardSuit;
    }

    /**
     * Gets a card's rank.
     */
    public final CardRank getCardRank() {
        return cardRank;
    }

    /**
     * Returns a hash code based on field values.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardRank == null) ? 0 : cardRank.hashCode());
        result = prime * result + ((cardSuit == null) ? 0 : cardSuit.hashCode());
        return result;
    }

    /**
     * Returns a string representation of a card.
     */
    @Override
    public String toString() {
        return String.format("%s_of_%s", getRankString(), getSuitString());
    }

    /**
     * Gets a string that represents card rank.
     */
    private String getRankString() {
        switch (cardRank) {
            case ACE:
                return "ace";
            case EIGHT:
                return "8";
            case FIVE:
                return "5";
            case FOUR:
                return "4";
            case JACK:
                return "jack";
            case KING:
                return "king";
            case NINE:
                return "9";
            case QUEEN:
                return "queen";
            case SEVEN:
                return "7";
            case SIX:
                return "6";
            case TEN:
                return "10";
            case THREE:
                return "3";
            case TWO:
                return "2";
            default:
                return " ";
        }
    }

    /**
     * Gets a string that represents card suit.
     */
    private String getSuitString() {
        switch (cardSuit) {
            case CLUBS:
                return "clubs";
            case DIAMONDS:
                return "diamonds";
            case HEARTS:
                return "hearts";
            case SPADES:
                return "spades";
            default:
                return " ";
        }
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }
}
