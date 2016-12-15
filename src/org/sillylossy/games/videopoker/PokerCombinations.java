package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardRank;
import org.sillylossy.games.common.cards.CardSuit;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;

class PokerCombinations {

    private static final int PAIR_REPEATS = 1;
    private static final int THREE_REPEATS = 2;
    private static final int FOUR_REPEATS = 3;
    private static final Comparator<Card> aceIsLowComparator = new CardComparator(false);
    private static final Comparator<Card> aceIsHighComparator = new CardComparator(true);
    private final CardRank minPairRank;
    private final int numberOfRepeats;
    private final EnumSet<CardRank> repeatsSet;
    private final Card[] cards;

    private PokerCombinations(int repeats, EnumSet<CardRank> set, Card[] cards, CardRank minPairRank) {
        numberOfRepeats = repeats;
        repeatsSet = set;
        this.cards = cards;
        this.minPairRank = minPairRank;
    }

    static String toString(Combination combination) {
        switch (combination) {
            case OTHER:
                return "Other";
            case ONE_PAIR:
                return "One pair (jacks or better)";
            case TWO_PAIR:
                return "Two pairs";
            case THREE_CARDS:
                return "Three of kind";
            case STRAIGHT:
                return "Straight";
            case FLUSH:
                return "Flush";
            case FULL_HOUSE:
                return "Full house";
            case FOUR_CARDS:
                return "Four of kind";
            case STRAIGHT_FLUSH:
                return "Straight flush";
            case ROYAL_FLUSH:
                return "Royal flush";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static int getRankValue(CardRank rank, boolean aceIsHigh) {
        switch (rank) {
            case ACE:
                return aceIsHigh ? 14 : 1;
            case EIGHT:
                return 8;
            case FIVE:
                return 5;
            case FOUR:
                return 4;
            case JACK:
                return 11;
            case KING:
                return 13;
            case NINE:
                return 9;
            case QUEEN:
                return 12;
            case SEVEN:
                return 7;
            case SIX:
                return 6;
            case TEN:
                return 10;
            case THREE:
                return 3;
            case TWO:
                return 2;
            default:
                throw new IllegalArgumentException();
        }
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

    Combination getBestCombination() {
        if (hasRoyalFlush()) {
            return Combination.ROYAL_FLUSH;
        }
        if (hasStraightFlush()) {
            return Combination.STRAIGHT_FLUSH;
        }
        if (hasFour()) {
            return Combination.FOUR_CARDS;
        }
        if (hasFullHouse()) {
            return Combination.FULL_HOUSE;
        }
        if (hasFlush()) {
            return Combination.FLUSH;
        }
        if (hasStraight()) {
            return Combination.STRAIGHT;
        }
        if (hasThree()) {
            return Combination.THREE_CARDS;
        }
        if (hasTwoPair()) {
            return Combination.TWO_PAIR;
        }
        if (hasOnePair()) {
            return Combination.ONE_PAIR;
        }
        return Combination.OTHER;
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
        for (int i = 0; i < cards.length - 1; i++) {
            int current = getRankValue(cards[i].getCardRank(), aceIsHigh);
            int next = getRankValue(cards[i + 1].getCardRank(), aceIsHigh);
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
                && repeatsSet.size() == 1
                && getRankValue(repeatsSet.iterator().next(), true) >= getRankValue(minPairRank, true);
    }

    boolean hasTwoPair() {
        return numberOfRepeats == THREE_REPEATS && repeatsSet.size() == 2;
    }

    boolean hasThree() {
        return numberOfRepeats == THREE_REPEATS && repeatsSet.size() == 1;
    }

    boolean hasFour() {
        return numberOfRepeats == FOUR_REPEATS && repeatsSet.size() == 1;
    }

    boolean hasFullHouse() {
        return numberOfRepeats == FOUR_REPEATS && repeatsSet.size() == 2;
    }

    enum Combination {
        OTHER, ONE_PAIR, TWO_PAIR, THREE_CARDS, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_CARDS, STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    private static final class CardComparator implements Comparator<Card> {

        boolean aceIsHigh;

        CardComparator(boolean aceIsHigh) {
            this.aceIsHigh = aceIsHigh;
        }

        @Override
        public int compare(Card o1, Card o2) {
            return Integer.compare(getRankValue(o1.getCardRank(), aceIsHigh), getRankValue(o2.getCardRank(), aceIsHigh));
        }
    }

}
