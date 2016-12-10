package org.sillylossy.games.blackjack.players;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.cards.Hand;
import org.sillylossy.games.common.players.Participant;

/**
 * Represents a dealer. Game is played against that participant.
 */
public class Dealer extends Participant {

    /**
     * Initializes a dealer with new hand.
     */
    public Dealer() {
        setHand(new Hand());
    }

    /**
     * Peeks an open card of dealer.
     */
    public Card getOpenCard() {
        return getHand().peek();
    }

    /**
     * Dealer makes his play here (takes cards until hand value less than 17).
     *
     * @param deck reference to deck object
     */
    public void play(Deck deck) {
        while (getHand().getValue() < 17) {
            getHand().addCard(deck.draw());
        }
    }
}
