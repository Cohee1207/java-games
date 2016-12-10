package org.sillylossy.games.common.game;

import org.sillylossy.games.common.players.Player;

/**
 * Interface for all the games in this app.
 */
public interface Game {
    String getGameName();

    Player getPlayer();

    void setPlayer(Player player);

    boolean shouldEnd();
}
