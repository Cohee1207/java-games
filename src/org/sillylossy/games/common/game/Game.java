package org.sillylossy.games.common.game;

import org.sillylossy.games.common.players.Player;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public String getRules() {
        InputStream inputStream = getClass().getResourceAsStream("Rules.html");
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            int result = bis.read();
            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
            return buf.toString();
        } catch (IOException e) {
            return e.toString();
        }
    }

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
