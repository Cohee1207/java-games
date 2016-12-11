package org.sillylossy.games.common.game;

public abstract class BetGame extends Game {
    public abstract void betAction(int bet);

    public abstract int getMaxBet();

    public abstract int getMinBet();
}
