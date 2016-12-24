package org.sillylossy.games.common.players;

import org.sillylossy.games.common.cards.Hand;

/**
 * Represents a game participant that has hand.
 */
public abstract class Participant {

    /**
     * A participant's hand.
     */
    private transient Hand hand = new Hand();

    /**
     * Gets a participant's hand.
     */
    public Hand getHand() {
        return hand;
    }

}