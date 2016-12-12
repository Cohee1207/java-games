package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.Deck;
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
        player.getHand().clear();
        player.setBet(0);
        player.setStand(false);
    }

    @Override
    protected void dealCards() {
        setDeck(Deck.create());
        final int POKER_CARDS = 5;
        for (int i = 0; i < POKER_CARDS; i++) {
            player.getHand().addCard(deck.draw());
        }
    }

    @Override
    public void betAction(int bet) {
        player.setBet(bet);
        dealCards();
    }

    @Override
    public int getMaxBet() {
        return player.getScore();
    }

    @Override
    public int getMinBet() {
        return (int) Math.ceil(player.getScore() / 50d);
    }

    Card changeCard(Card card) {
        Card newCard = deck.draw();
        player.getHand().replaceCard(card, newCard);
        return newCard;
    }

}

