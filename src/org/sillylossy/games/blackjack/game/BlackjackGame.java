package org.sillylossy.games.blackjack.game;

import org.sillylossy.games.blackjack.players.Dealer;
import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.cards.Hand;
import org.sillylossy.games.common.game.CardGame;
import org.sillylossy.games.common.game.StatEvent;

/**
 * Blackjack game model.
 */
public class BlackjackGame extends CardGame {

    /**
     * Name of the game.
     */
    public static final String GAME_NAME = "Blackjack";
    /**
     * A dealer assigned to a game instance.
     */
    private final Dealer dealer = new Dealer();

    /**
     * Performs a bet game event. Deals cards to players and sets bet to player account.
     *
     * @param bet bet amount
     */
    public void betAction(int bet) {
        dealCards();
        player.setBet(bet);
    }

    /**
     * Performs a double game event. If player has sufficient score, double's his bet and performs hit event.
     *
     * @return taken card or null if player has insufficient score
     */
    public Card doubleAction() {
        int newBet = player.getBet() * 2;
        if (player.getScore() < newBet) {
            return null;
        }
        player.setBet(newBet);
        return hitAction();
    }

    /**
     * Gets maximum bet (half of player's score).
     */
    public int getMaxBet() {
        return player.getScore() / 2;
    }

    /**
     * Gets minimum bet(twentieth part of player's score).
     */
    public int getMinBet() {
        return player.getScore() / 20;
    }

    /**
     * Gets game's dealer.
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Performs a hit game action. Draws a card, adds it to a hand and returns that card.
     *
     * @return taken card
     */
    public Card hitAction() {
        Card card = deck.draw();
        player.getHand().addCard(card);
        return card;
    }

    @Override
    public String getResult() {
        String result = "Can't identify result";
        int bet = player.getBet();
        if (!player.getHand().isBlackJack() && dealer.getHand().isBlackJack()) {
            player.decreaseScore(bet);
            Main.getGameController().addStatEvent(player, StatEvent.LOST);
            result = "You've lost. Dealer has blackjack";
        } else if (player.getHand().isBlackJack() && dealer.getHand().isBlackJack()) {
            Main.getGameController().addStatEvent(player, StatEvent.PUSH);
            result = "Push. Dealer has blackjack too";
        } else if (player.getHand().isBlackJack() && !dealer.getHand().isBlackJack()) {
            player.increaseScore(Math.round(bet * 1.5f));
            Main.getGameController().addStatEvent(player, StatEvent.WON);
            result = "You've won: blackjack";
        } else if (player.getHand().getValue() > Hand.BLACKJACK) {
            player.decreaseScore(bet);
            Main.getGameController().addStatEvent(player, StatEvent.LOST);
            result = "You've lost: overtake";
        } else if (dealer.getHand().getValue() > Hand.BLACKJACK && player.getHand().getValue() <= Hand.BLACKJACK) {
            player.increaseScore(bet);
            Main.getGameController().addStatEvent(player, StatEvent.WON);
            result = "You've won: dealer overtake";
        } else if (player.getHand().getValue() > dealer.getHand().getValue()) {
            player.increaseScore(bet);
            Main.getGameController().addStatEvent(player, StatEvent.WON);
            result = "You've won: you have more points than dealer";
        } else if (player.getHand().getValue() == dealer.getHand().getValue()) {
            Main.getGameController().addStatEvent(player, StatEvent.PUSH);
            result = "Push. Your points with dealer are equal.";
        } else if (player.getHand().getValue() < dealer.getHand().getValue()) {
            player.decreaseScore(player.getBet());
            Main.getGameController().addStatEvent(player, StatEvent.LOST);
            result = "You've lost: dealer has more points (" + dealer.getHand().getValue() + ")";
        }
        reset();
        Main.saveData();
        return result;
    }

    /**
     * Clears dealer's and player's hand, sets stand flag to false and bet to 0.
     */
    @Override
    protected void reset() {
        player.getHand().clear();
        player.setBet(0);
        player.setStand(false);
        dealer.getHand().clear();
    }

    @Override
    public boolean shouldEnd() {
        if (player.getHand().isBlackJack()) {
            return true;
        } else if (player.getHand().getValue() > Hand.BLACKJACK) {
            return true;
        } else if (player.isStand()) {
            return true;
        }
        return false;
    }

    @Override
    protected void dealCards() {
        setDeck(Deck.create());
        dealer.getHand().addCard(deck.draw());
        dealer.getHand().addCard(deck.draw());
        player.getHand().addCard(deck.draw());
        player.getHand().addCard(deck.draw());
    }

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    /**
     * Performs a stand action.
     */
    public void standAction() {
        player.setStand(true);
    }

}
