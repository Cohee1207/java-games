package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardRank;
import org.sillylossy.games.common.cards.CardSuit;

import java.util.*;

class PokerCombinations {

    private static final int PAIR_REPEATS = 1;

    private static final int THREE_REPEATS = 2;

    private static final int FOUR_REPEATS = 3;
    static Map<Combination, String> combinationStringMap = getCombinationStrings();
    private static Map<CardRank, Integer> aceIsLowValues = getRankValues(false);
    private static Comparator<Card> aceIsHighComparator = new CardComparator(getRankValues(true));
    private static Comparator<Card> aceIsLowComparator = new CardComparator(getRankValues(false));
    private static Map<CardRank, Integer> aceIsHighValues = getRankValues(true);
    private CardRank minPairRank;
    private int numberOfRepeats;
    private EnumSet<CardRank> rankEnumSet;
    private Card[] cards;

    private PokerCombinations(int repeats, EnumSet<CardRank> set, Card[] cards, CardRank minPairRank) {
        numberOfRepeats = repeats;
        rankEnumSet = set;
        this.cards = cards;
        this.minPairRank = minPairRank;
    }

    private static Map<Combination, String> getCombinationStrings() {
        EnumMap<Combination, String> map = new EnumMap<>(Combination.class);
        map.put(Combination.HIGH_CARD, "High card");
        map.put(Combination.ONE_PAIR, "One pair (jacks or better)");
        map.put(Combination.TWO_PAIR, "Two pairs");
        map.put(Combination.THREE_CARDS, "Three of kind");
        map.put(Combination.STRAIGHT, "Straight");
        map.put(Combination.FLUSH, "Flush");
        map.put(Combination.FULL_HOUSE, "Full house");
        map.put(Combination.FOUR_CARDS, "Four of kind");
        map.put(Combination.STRAIGHT_FLUSH, "Straight flush");
        map.put(Combination.ROYAL_FLUSH, "Royal flush");
        return map;
    }

    private static Map<CardRank, Integer> getRankValues(boolean aceHigh) {
        final int HIGH = 14, LOW = 1;
        Map<CardRank, Integer> values = new EnumMap<>(CardRank.class);
        putCommonValues(values);
        values.put(CardRank.ACE, aceHigh ? HIGH : LOW);
        return values;
    }

    private static void putCommonValues(Map<CardRank, Integer> map) {
        map.put(CardRank.TWO, 2);
        map.put(CardRank.THREE, 3);
        map.put(CardRank.FOUR, 4);
        map.put(CardRank.FIVE, 5);
        map.put(CardRank.SIX, 6);
        map.put(CardRank.SEVEN, 7);
        map.put(CardRank.EIGHT, 8);
        map.put(CardRank.NINE, 9);
        map.put(CardRank.TEN, 10);
        map.put(CardRank.JACK, 11);
        map.put(CardRank.QUEEN, 12);
        map.put(CardRank.KING, 13);
    }

    static PokerCombinations getCombinations(Card[] cards, CardRank minPairRank) {
        int repeats = 0;
        EnumSet<CardRank> pairsSet = EnumSet.noneOf(CardRank.class);
        Arrays.sort(cards, aceIsHighComparator);
        for (int i = 0; i < cards.length - 1; i++) {
            if (cards[i].getCardRank() == cards[i + 1].getCardRank()) {
                repeats++;
                pairsSet.add(cards[i].getCardRank());
            }
        }
        return new PokerCombinations(repeats, pairsSet, cards, minPairRank);
    }

    String getString(Combination combination) {
        String string = combinationStringMap.get(combination);
        return string != null ? string : "Unknown";
    }

    Combination getBestCombination() {
        Combination best = Combination.HIGH_CARD;
        if (hasOnePair()) {
            best = Combination.ONE_PAIR;
        }
        if (hasTwoPair()) {
            best = Combination.TWO_PAIR;
        }
        if (hasThree()) {
            best = Combination.THREE_CARDS;
        }
        if (hasStraight()) {
            best = Combination.STRAIGHT;
        }
        if (hasFlush()) {
            best = Combination.FLUSH;
        }
        if (hasFullHouse()) {
            best = Combination.FULL_HOUSE;
        }
        if (hasFour()) {
            best = Combination.FOUR_CARDS;
        }
        if (hasStraightFlush()) {
            best = Combination.STRAIGHT_FLUSH;
        }
        if (hasRoyalFlush()) {
            best = Combination.ROYAL_FLUSH;
        }
        return best;
    }

    boolean hasStraight() {
        return hasStraight(true) || hasStraight(false);
    }

    boolean hasStraightFlush() {
        return hasFlush() && hasStraight();
    }

    boolean hasRoyalFlush() {
        return hasFlush() && hasStraight(true) && cards[0].getCardRank() == CardRank.TEN;
    }

    private boolean hasStraight(boolean aceIsHigh) {
        Arrays.sort(cards, aceIsHigh ? aceIsHighComparator : aceIsLowComparator);
        Map<CardRank, Integer> values = aceIsHigh ? aceIsHighValues : aceIsLowValues;
        for (int i = 0; i < cards.length - 1; i++) {
            int current = values.get(cards[i].getCardRank());
            int next = values.get(cards[i + 1].getCardRank());
            if (next - current != 1) {
                return false;
            }
        }
        return true;
    }

    boolean hasFlush() {
        CardSuit flushSuit = cards[0].getCardSuit();
        for (Card card : cards) {
            if (flushSuit != card.getCardSuit()) {
                return false;
            }
        }
        return true;
    }

    boolean hasOnePair() {
        return numberOfRepeats == PAIR_REPEATS
                && rankEnumSet.size() == 1
                && aceIsHighValues.get(rankEnumSet.iterator().next()) >= aceIsHighValues.get(minPairRank);
    }

    boolean hasTwoPair() {
        return numberOfRepeats == THREE_REPEATS && rankEnumSet.size() == 2;
    }

    boolean hasThree() {
        return numberOfRepeats == THREE_REPEATS && rankEnumSet.size() == 1;
    }

    boolean hasFour() {
        return numberOfRepeats == FOUR_REPEATS && rankEnumSet.size() == 1;
    }

    boolean hasFullHouse() {
        return numberOfRepeats == FOUR_REPEATS && rankEnumSet.size() == 2;
    }

    enum Combination {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_CARDS, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_CARDS, STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    private static final class CardComparator implements Comparator<Card> {
        Map<CardRank, Integer> values;

        CardComparator(Map<CardRank, Integer> map) {
            this.values = map;
        }

        @Override
        public int compare(Card o1, Card o2) {
            return Integer.compare(values.get(o1.getCardRank()), values.get(o2.getCardRank()));
        }
    }

}
