package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardFace;

import java.util.Arrays;
import java.util.EnumSet;

class PokerRepeats {

    private static final int PAIR_REPEATS = 1;
    private static final int THREE_REPEATS = 2;
    private static final int FOUR_REPEATS = 3;
    private int numberOfRepeats;
    private EnumSet<CardFace> facesSet;

    private PokerRepeats(int repeats, EnumSet<CardFace> set) {
        numberOfRepeats = repeats;
        facesSet = set;
    }

    static PokerRepeats getRepeats(Card[] cards) {
        int repeats = 0;
        EnumSet<CardFace> pairsSet = EnumSet.noneOf(CardFace.class);
        Arrays.sort(cards, VideoPokerGame.getOrderComparator());
        for (int i = 0; i < cards.length - 1; i++) {
            if (cards[i].getCardFace() == cards[i + 1].getCardFace()) {
                repeats++;
                pairsSet.add(cards[i].getCardFace());
            }
        }
        return new PokerRepeats(repeats, pairsSet);
    }

    boolean hasOnePair() {
        return numberOfRepeats == PAIR_REPEATS && getFacesSet().size() == 1;
    }

    boolean hasTwoPair() {
        return numberOfRepeats == THREE_REPEATS && getFacesSet().size() == 2;
    }

    boolean hasThree() {
        return numberOfRepeats == THREE_REPEATS && getFacesSet().size() == 1;
    }

    boolean hasFour() {
        return numberOfRepeats == FOUR_REPEATS && getFacesSet().size() == 1;
    }

    boolean hasFullHouse() {
        return numberOfRepeats == FOUR_REPEATS && getFacesSet().size() == 2;
    }

    private EnumSet<CardFace> getFacesSet() {
        return facesSet;
    }
}
