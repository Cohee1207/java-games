package org.sillylossy.games.blackjack;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.game.CardGame;
import org.sillylossy.games.common.game.StatEvent;
import org.sillylossy.games.common.ui.images.CardImageController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Blackjack game model.
 */
public class BlackjackGame extends CardGame {

    /**
     * Name of the game.
     */
    public static final String GAME_NAME = "Blackjack";
    /**
     * How many value points needed for "blackjack".
     */
    private final static int BLACKJACK = 21;
    /**
     * A dealer assigned to a game instance.
     */
    private final Dealer dealer = new Dealer();

    public static Image getIcon() throws IOException {
        return ImageIO.read(BlackjackGame.class.getResourceAsStream(GAME_NAME + CardImageController.IMAGE_EXT));
    }

    String getDealerValue() {
        return getValue(dealer.getHand().getCards()) + " points";
    }

    /**
     * Returns a numeric value that represents a hand's value.
     * Cards with number have value of that number. Cards with picture (jack, queen, king)
     * have 10 points value. Ace is valued 1 or 11 (as player wants).
     */
    int getValue(Card[] hand) {
        int i = 0;
        int j = 0;
        for (Card card : hand) {
            switch (card.getCardRank()) {
                case ACE:
                    i += 11;
                    j += 1;
                    break;
                case EIGHT:
                    i += 8;
                    j += 8;
                    break;
                case FIVE:
                    i += 5;
                    j += 5;
                    break;
                case FOUR:
                    i += 4;
                    j += 4;
                    break;
                case JACK:
                    i += 10;
                    j += 10;
                    break;
                case KING:
                    i += 10;
                    j += 10;
                    break;
                case NINE:
                    i += 9;
                    j += 9;
                    break;
                case QUEEN:
                    i += 10;
                    j += 10;
                    break;
                case SEVEN:
                    i += 7;
                    j += 7;
                    break;
                case SIX:
                    i += 6;
                    j += 6;
                    break;
                case TEN:
                    i += 10;
                    j += 10;
                    break;
                case THREE:
                    i += 3;
                    j += 3;
                    break;
                case TWO:
                    i += 2;
                    j += 2;
                    break;
                default:
                    break;
            }
        }
        return i > BlackjackGame.BLACKJACK ? j : i;
    }

    /**
     * Determines whether a player has "blackjack" (2 cards, total 21 value).
     */
    private boolean isBlackJack(Card[] hand) {
        return hand.length == 2 && getValue(hand) == BLACKJACK;
    }

    /**
     * Performs a bet game event. Deals cards to players and sets bet to player account.
     *
     * @param bet bet amount
     */
    @Override
    public void betAction(int bet) {
        dealCards();
        player.setBet(bet);
        player.decreaseScore(bet);
    }

    /**
     * Performs a double game event. If player has sufficient score, double's his bet and performs hit event.
     *
     * @return taken card or null if player has insufficient score
     */
    Card doubleAction() {
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
    @Override
    public int getMaxBet() {
        return player.getScore() / 2;
    }

    /**
     * Gets minimum bet(twentieth part of player's score).
     */
    @Override
    public int getMinBet() {
        return player.getScore() / 20;
    }

    /**
     * Gets game's dealer.
     */
    Dealer getDealer() {
        return dealer;
    }

    /**
     * Performs a hit game action. Draws a card, adds it to a hand and returns that card.
     *
     * @return taken card
     */
    Card hitAction() {
        Card card = deck.draw();
        player.getHand().addCard(card);
        return card;
    }

    @Override
    public String getResult() {
        String result = "Can't identify result";
        int bet = player.getBet();
        Card[] playerCards = player.getHand().getCards();
        Card[] dealerCards = dealer.getHand().getCards();
        int dealerValue = getValue(dealerCards);
        int playerValue = getValue(playerCards);
        boolean playerBlackjack = isBlackJack(playerCards);
        boolean dealerBlackjack = isBlackJack(dealerCards);
        int increase = 0;
        StatEvent statEvent = StatEvent.PUSH;
        if (!playerBlackjack && dealerBlackjack) {
            statEvent = StatEvent.LOST;
            result = "You've lost. Dealer has blackjack";
        } else if (playerBlackjack && dealerBlackjack) {
            increase = bet;
            statEvent = StatEvent.PUSH;
            result = "Push. Dealer has blackjack too";
        } else if (playerBlackjack) {
            increase = bet * 3;
            statEvent = StatEvent.WON;
            result = "You've won: blackjack";
        } else if (playerValue > BLACKJACK) {
            statEvent = StatEvent.LOST;
            result = "You've lost: bust";
        } else if (dealerValue > BLACKJACK && playerValue <= BLACKJACK) {
            increase = 2 * bet;
            statEvent = StatEvent.WON;
            result = "You've won: dealer bust";
        } else if (playerValue > dealerValue) {
            increase = 2 * bet;
            statEvent = StatEvent.WON;
            result = "You've won: you have more points than dealer";
        } else if (playerValue == dealerValue) {
            increase = bet;
            statEvent = StatEvent.PUSH;
            result = "Push. Your points with dealer are equal.";
        } else if (playerValue < dealerValue) {
            statEvent = StatEvent.LOST;
            result = "You've lost: dealer has more points";
        }
        player.increaseScore(increase);
        Main.getGameController().addStatEvent(player, statEvent);
        Main.saveData();
        return result;
    }

    /**
     * Clears dealer's and player's hand, sets stand flag to false and bet to 0.
     */
    @Override
    public void reset() {
        player.getHand().clear();
        player.setBet(0);
        dealer.getHand().clear();
    }

    @Override
    public boolean shouldEnd() {
        Card[] playerCards = player.getHand().getCards();
        if (isBlackJack(playerCards)) {
            return true;
        } else if (getValue(playerCards) > BLACKJACK) {
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

}
