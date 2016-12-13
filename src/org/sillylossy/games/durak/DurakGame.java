package org.sillylossy.games.durak;

import org.sillylossy.games.common.game.CardGame;
import org.sillylossy.games.common.players.Participant;

public class DurakGame extends CardGame {

    private Participant opponent = new AIPlayer();

    @Override
    public void betAction(int bet) {
        player.setBet(bet);
        dealCards();
    }

    @Override
    public int getMaxBet() {
        return 0;
    }

    @Override
    public int getMinBet() {
        return 0;
    }

    @Override
    public String getGameName() {
        return "Durak";
    }

    @Override
    public boolean shouldEnd() {
        return false;
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    protected void dealCards() {
        final int START_CARDS = 6;
        for (int i = 0; i < START_CARDS; i++) {
            player.getHand().addCard(deck.draw());
            opponent.getHand().addCard(deck.draw());
        }
    }
}
