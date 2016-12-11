package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.game.CardGame;

public class VideoPokerGame extends CardGame {

    public static final String GAME_NAME = "Video Poker";


    @Override
    public String getGameName() {
        return GAME_NAME;
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
    protected void reset() {

    }

    @Override
    protected void dealCards() {
        final int POKER_CARDS = 5;
        for (int i = 0; i < POKER_CARDS; i++) {
            player.getHand().addCard(deck.draw());
        }
    }

    @Override
    public void betAction(int bet) {

    }

    @Override
    public int getMaxBet() {
        return 0;
    }

    @Override
    public int getMinBet() {
        return 0;
    }
}

