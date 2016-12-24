package org.sillylossy.games.common.game;

import org.sillylossy.games.common.cards.Deck;

/**
 * Represents an abstract card game.
 */
public abstract class CardGame extends BetGame {
    /**
     * Active card deck.
     */
    protected Deck deck;

    /**
     * Gets an active card deck.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Sets card card with value from param.
     */
    protected void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**
     * Deals cards to players;
     */
    protected abstract void dealCards();

    /**
     * Returns a string representation of the game.
     *
     * @return name of the game
     */
    @Override
    public String toString() {
        return getGameName();
    }
}
