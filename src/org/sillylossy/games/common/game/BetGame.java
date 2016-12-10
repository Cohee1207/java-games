package org.sillylossy.games.common.game;

public interface BetGame extends Game {
    void betAction(int bet);

    int getMaxBet();

    int getMinBet();
}
