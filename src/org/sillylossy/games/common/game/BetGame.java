package org.sillylossy.games.common.game;

public interface BetGame {
    void betAction(int bet);

    int getMaxBet();

    int getMinBet();
}
