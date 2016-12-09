package org.sillylossy.games.common.game;

import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.cards.Hand;
import org.sillylossy.games.common.players.Player;

/**
 * Represents an abstract card game.
 */
public abstract class CardGame {
    /**
     * Active card deck.
     */
    protected Deck deck;

    /**
     * Player assigned to game session.
     */
    protected Player player;

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
     * Gets an active player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets an active player and creates hand for him.
     *
     * @param player player that needs to be activated
     */
    public void setPlayer(Player player) {
        player.setHand(new Hand());
        this.player = player;
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
     * Returns a value that indicates whether a game should end.
     */
    public abstract boolean shouldEnd();

    /**
     * Deals cards to players;
     */
    protected abstract void dealCards();

    /**
     * Gets a name of the game.
     *
     * @return string that contains game name
     */
    public abstract String getGameName();

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
