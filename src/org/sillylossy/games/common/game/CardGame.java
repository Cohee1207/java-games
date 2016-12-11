package org.sillylossy.games.common.game;

import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.cards.Hand;
import org.sillylossy.games.common.players.Player;

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
     * Sets an active player and creates hand for him.
     *
     * @param player player that needs to be activated
     */
    @Override
    public void setPlayer(Player player) {
        super.setPlayer(player);
        player.setHand(new Hand());
    }

    /**
     * Updates player score and statistics depending on game result.
     *
     * @return string representation of game result
     */
    public abstract String getResult();

    /**
     * Resets the game.
     */
    protected abstract void reset();

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
