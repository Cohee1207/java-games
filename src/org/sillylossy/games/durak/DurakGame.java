package org.sillylossy.games.durak;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardRank;
import org.sillylossy.games.common.cards.CardSuit;
import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.game.CardGame;
import org.sillylossy.games.common.game.StatEvent;
import org.sillylossy.games.common.players.Participant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DurakGame extends CardGame {

    public static final String GAME_NAME = "Durak";
    private final AIPlayer opponent = new AIPlayer(this);
    boolean opponentTakes = false;
    boolean opponentsTurn = false;
    private Map<Card, Card> tableCards = new HashMap<>();
    private Card trumpCard = new Card(CardRank.ACE, CardSuit.DIAMONDS);

    private Map<Card, Card> getTableCards() {
        return tableCards;
    }

    Card getTrumpCard() {
        return trumpCard;
    }

    @Override
    public void betAction(int bet) {
        player.setBet(bet);
        player.decreaseScore(bet);
        setDeck(Deck.getShortDeck());
        trumpCard = deck.getLast();
        dealCards();
    }

    void nextTurn() {
        refillCards();
    }

    private void refillCards() {
        refill(opponent);
        refill(player);
    }

    List<Card> filterCards(Card clicked) {
        List<Card> cards = new ArrayList<>();
        for (Card card : player.getHand().getCards()) {
            if (canBeat(card, clicked)) {
                cards.add(card);
            }
        }
        return cards;
    }

    int getValue(Card card) {
        switch (card.getCardRank()) {
            case SIX:
                return 1;
            case SEVEN:
                return 2;
            case EIGHT:
                return 3;
            case NINE:
                return 4;
            case TEN:
                return 5;
            case JACK:
                return 6;
            case QUEEN:
                return 7;
            case KING:
                return 8;
            case ACE:
                return 9;
        }
        return 0;
    }

    private boolean canBeat(Card card, Card clicked) {
        CardSuit trumpSuit = trumpCard.getCardSuit();
        if (card.getCardSuit() == trumpSuit && clicked.getCardSuit() != trumpSuit) {
            return true;
        } else if (clicked.getCardSuit() == trumpSuit && card.getCardSuit() != trumpSuit) {
            return false;
        } else if (getValue(card) > getValue(clicked)) {
            return true;
        }
        return false;
    }

    public void handCardClick(Card card) {
        if (opponentTakes && !opponentsTurn) {
            opponent.getHand().addCard(card);
        } else {
            tableCards.put(card, null);
            if (opponent.canBeat(card)) {
                tableCards.put(card, opponent.getResponse(card));
            } else {
                opponentTakes = true;
            }
        }
    }

    @Override
    public int getMaxBet() {
        return player.getScore();
    }

    @Override
    public int getMinBet() {
        return 1;
    }

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public boolean shouldEnd() {
        return deck.isEmpty() && player.getHand().isEmpty() || opponent.getHand().isEmpty();
    }

    @Override
    public String getResult() {
        String result;
        boolean playerEmpty = player.getHand().isEmpty();
        boolean opponentEmpty = opponent.getHand().isEmpty();
        int bet = player.getBet();
        StatEvent statEvent;
        int increase = 0;
        if (playerEmpty && opponentEmpty) {
            result = "Draw.";
            statEvent = StatEvent.DRAW;
            increase = bet;
        } else if (playerEmpty) {
            result = "You've won.";
            statEvent = StatEvent.WON;
            increase = bet * 2;
        } else {
            result = "You've lost.";
            statEvent = StatEvent.LOST;
        }
        Main.getGameController().addStatEvent(player, statEvent);
        player.increaseScore(increase);
        Main.saveData();
        return result;
    }

    @Override
    public void reset() {
        player.getHand().clear();
        player.setBet(0);
        opponent.getHand().clear();
    }

    @Override
    protected void dealCards() {
        refill(opponent);
        refill(player);
    }

    private void refill(Participant p) {
        final int START_CARDS = 6;
        while (p.getHand().size() < START_CARDS) {
            p.getHand().addCard(deck.draw());
        }
    }

    void takeAction() {
        for (Map.Entry<Card, Card> entry : tableCards.entrySet()) {
            if (entry.getKey() != null) {
                player.getHand().addCard(entry.getKey());
            }
            if (entry.getValue() != null) {
                player.getHand().addCard(entry.getValue());
            }
        }
        tableCards.clear();
        opponentsTurn = true;
        nextTurn();
    }

    public AIPlayer getOpponent() {
        return opponent;
    }
}
