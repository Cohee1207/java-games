package org.sillylossy.games.blackjack.players;

import org.sillylossy.games.blackjack.game.BlackjackGame;
import org.sillylossy.games.common.Main;
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
     * Dealer makes his play here (takes cards until hand value less than STOP_VALUE).
     *
     * @param deck reference to deck object
     */
    public void play(Deck deck) {
        final int STOP_VALUE = 17;
        while (((BlackjackGame)Main.getGameInstance()).getValue(getHand().getCards()) < STOP_VALUE) {
            getHand().addCard(deck.draw());
        }
    }
}
