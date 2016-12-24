package org.sillylossy.games.durak;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardSuit;
import org.sillylossy.games.common.players.Participant;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class AIPlayer extends Participant {

    private DurakGame durakGame;

    AIPlayer(DurakGame game) {
        durakGame = game;
    }

    boolean canBeat(Card card) {
        return durakGame.filterCards(card).size() != 0;
    }

    Card getResponse(Card card) {
        List<Card> filtered = durakGame.filterCards(card);
        Collections.sort(filtered, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                CardSuit trumpSuit = durakGame.getTrumpCard().getCardSuit();
                int value1 = durakGame.getValue(o1);
                int value2 = durakGame.getValue(o2);
                if (o1.getCardSuit() == trumpSuit) {
                    value1 += 10;
                }
                if (o2.getCardSuit() == trumpSuit) {
                    value2 += 10;
                }
                return Integer.compare(value1, value2);
            }
        });
        return filtered.get(0);
    }
}
