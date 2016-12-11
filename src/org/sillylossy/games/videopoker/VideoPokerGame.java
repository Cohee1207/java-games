package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardFace;
import org.sillylossy.games.common.game.CardGame;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class VideoPokerGame extends CardGame {

    public static final String GAME_NAME = "Video Poker";
    private static Map<CardFace, Integer> aceIsLowValues = getAceIsLowValues();
    private static Comparator<Card> aceIsLowComparator = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return Integer.compare(aceIsLowValues.get(o1.getCardFace()), aceIsLowValues.get(o2.getCardFace()));
        }
    };
    private static Map<CardFace, Integer> aceIsHighValues = getAceIsHighValues();
    private static Comparator<Card> aceIsHighComparator = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return Integer.compare(aceIsHighValues.get(o1.getCardFace()), aceIsHighValues.get(o2.getCardFace()));
        }
    };

    static Comparator<Card> getOrderComparator() {
        return aceIsHighComparator;
    }

    private static Map<CardFace, Integer> getAceIsLowValues() {
        Map<CardFace, Integer> values = new HashMap<>(CardFace.values().length);
        putCommonValues(values);
        values.put(CardFace.ACE, 1);
        return values;
    }

    private static void putCommonValues(Map<CardFace, Integer> map) {
        map.put(CardFace.TWO, 2);
        map.put(CardFace.THREE, 3);
        map.put(CardFace.FOUR, 4);
        map.put(CardFace.FIVE, 5);
        map.put(CardFace.SIX, 6);
        map.put(CardFace.SEVEN, 7);
        map.put(CardFace.EIGHT, 8);
        map.put(CardFace.NINE, 9);
        map.put(CardFace.TEN, 10);
        map.put(CardFace.JACK, 11);
        map.put(CardFace.QUEEN, 12);
        map.put(CardFace.KING, 13);
    }

    private static Map<CardFace, Integer> getAceIsHighValues() {
        Map<CardFace, Integer> values = new HashMap<>(CardFace.values().length);
        putCommonValues(values);
        values.put(CardFace.ACE, 14);
        return values;
    }

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public boolean shouldEnd() {
        return false;
    }

    private boolean hasStraight(Card[] cards) {
        return hasStraight(cards, true) || hasStraight(cards, false);
    }

    private boolean hasStraight(Card[] cards, boolean aceIsHigh) {
        Arrays.sort(cards, aceIsHigh ? aceIsHighComparator : aceIsLowComparator);
        Map<CardFace, Integer> values = aceIsHigh ? aceIsHighValues : aceIsLowValues;
        for (int i = 0; i < cards.length - 1; i++) {
            if (values.get(cards[i].getCardFace()) != values.get(cards[i + 1].getCardFace()) + 1) {
                return false;
            }
        }
        return true;
    }

    private boolean hasFlush(Card[] cards) {
        CardFace flushFace = cards[0].getCardFace();
        for (Card card : cards) {
            if (flushFace != card.getCardFace()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getResult() {
        Card[] cards = player.getHand().getCards();
        int result = PokerCombinations.HIGH_CARD;
        if (hasStraight(cards)) {
            result = PokerCombinations.STRAIGHT;
        } else if (hasFlush(cards)) {
            result = PokerCombinations.FLUSH;
        }
        return Integer.toString(result);
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

    private class PokerCombinations {
        static final int HIGH_CARD = 0;
        static final int ONE_PAIR = 1;
        static final int TWO_PAIR = 2;
        static final int THREE_OF_KIND = 3;
        static final int STRAIGHT = 4;
        static final int FLUSH = 5;
        static final int FULL_HOUSE = 6;
        static final int FOUR_OF_KIND = 7;
        static final int STRAIGHT_FLUSH = 8;
        static final int ROYAL_FLUSH = 9;
    }
}

