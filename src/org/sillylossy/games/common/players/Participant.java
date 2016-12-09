package org.sillylossy.games.common.players;

import org.sillylossy.games.common.cards.Hand;

/**
 * Represents a game participant that has hand.
 */
public abstract class Participant {

    /**
     * A participant's hand.
     */
    private transient Hand hand;

    /**
     * Gets a participant's hand.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Sets a hand field with param value.
     */
    public void setHand(Hand hand) {
        this.hand = hand;
    }

}