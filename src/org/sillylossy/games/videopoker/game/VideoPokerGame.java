package org.sillylossy.games.videopoker.game;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardFace;
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

    private boolean hasFlush(Card[] cards) {
        CardFace flushFace = cards[0].getCardFace();
        for (Card card : cards) {
            if (flushFace != card.getCardFace()) {
                return false;
            }
        }
        return  true;
    }

    @Override
    public String getResult() {
        return "";
    }

    @Override
    protected void reset() {

    }

    @Override
    protected void dealCards() {

    }
}
