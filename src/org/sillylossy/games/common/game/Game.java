package org.sillylossy.games.common.game;

import org.sillylossy.games.common.players.Player;

/**
 * Interface for all the games in this app.
 */
public abstract class Game {
    public abstract String getGameName();

    protected Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract boolean shouldEnd();
}
