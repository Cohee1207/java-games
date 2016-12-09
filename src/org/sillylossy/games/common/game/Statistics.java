package org.sillylossy.games.common.game;

/**
 * Contains statistics (number of wins, loses, etc.) of a player and methods to manipulate it.
 */
public class Statistics implements java.io.Serializable {

    /**
     * Holds a number of games lost.
     */
    private int gamesLost;

    /**
     * Holds a number of games pushed.
     */
    private int gamesPushed;

    /**
     * Holds a number of games won.
     */
    private int gamesWon;

    /**
     * Adds one loss.
     */
    void addLose() {
        ++gamesLost;
    }

    /**
     * Adds one push.
     */
    void addPush() {
        ++gamesPushed;
    }

    /**
     * Adds one win.
     */
    void addWin() {
        ++gamesWon;
    }

    /**
     * Gets a number of games lost.
     */
    public final int getGamesLost() {
        return gamesLost;
    }

    /**
     * Gets a number of games pushed.
     */
    public final int getGamesPushed() {
        return gamesPushed;
    }

    /**
     * Gets a number of games won.
     */
    public final int getGamesWon() {
        return gamesWon;
    }
}
