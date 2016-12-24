package org.sillylossy.games.common.game;

import org.sillylossy.games.common.players.Player;

/**
 * Interface for all the games in this app.
 */
public abstract class Game {

    protected Player player;

    public abstract String getGameName();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract boolean shouldEnd();

    /**
     * Updates player score and statistics depending on game result.
     *
     * @return string representation of game result
     */
    public abstract String getResult();

    /**
     * Resets the game.
     */
    public abstract void reset();
}
