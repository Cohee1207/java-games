package org.sillylossy.games.common.game;

import org.sillylossy.games.blackjack.game.StatEvent;
import org.sillylossy.games.blackjack.game.Statistics;
import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a game controller that handles player accounts and statistics.
 */
public class GameController implements java.io.Serializable {

    /**
     * A list of registered players.
     */
    private final List<Player> registeredPlayers = new ArrayList<>();

    /**
     * Map of statistics assigned to a certain players.
     */
    private final HashMap<Player, Statistics> statistics = new HashMap<>();

    /**
     * A flag that indicates whether a player is in game.
     */
    private boolean inGame = false;

    /**
     * Holds last set string error description.
     */
    private transient String lastError;

    /**
     * Adds statistics event to player.
     *
     * @param player player that needs to have updated stats
     * @param event  a statistic event
     */
    public void addStatEvent(Player player, StatEvent event) {
        Statistics statObj = getStatistics().getOrDefault(player, null);
        if (statObj == null) {
            statObj = new Statistics();
            statistics.put(player, statObj);
        }
        switch (event) {
            case BLACKJACK:
                statObj.addBlackjack();
                break;
            case LOST:
                statObj.addLose();
                break;
            case WON:
                statObj.addWin();
                break;
            case PUSH:
                statObj.addPush();
            default:
                break;
        }
    }

    /**
     * Deletes a player from game controller.
     *
     * @param player player that needs to be deleted
     * @return true if the operation is successful, else - false
     */
    public boolean deletePlayer(Player player) {
        if (player == null) {
            setLastError("Select a player first.");
            return false;
        }
        registeredPlayers.remove(player);
        getStatistics().remove(player);
        Main.saveData();
        return true;
    }

    /**
     * Gets last error.
     */
    public String getLastError() {
        return lastError;
    }

    /**
     * Sets last error with string from param.
     */
    private void setLastError(String msg) {
        lastError = msg;
    }

    /**
     * Gets a list of registered players.
     */
    public List<Player> getPlayers() {
        return registeredPlayers;
    }

    /**
     * Gets a map of statistics.
     */
    public HashMap<Player, Statistics> getStatistics() {
        return statistics;
    }

    /**
     * Gets an in-game flag.
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Sets an in-game flag.
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Registers a player with specified name.
     *
     * @param username desired name
     * @return true if the operation is successful, else - false
     */
    public boolean register(String username) {
        final int MAX_LEN = 15;
        if (username.length() > MAX_LEN) {
            setLastError(String.format("Name shouldn't be longer than %d.", MAX_LEN));
            return false;
        }
        if (findPlayer(username) != null) {
            setLastError(String.format("Player '%s' already exists!", username));
            return false;
        }
        Player player = new Player(username);
        registeredPlayers.add(player);
        setActivePlayer(player);
        Main.saveData();
        return true;
    }

    /**
     * Finds a player that username matches the param.
     *
     * @param string query string
     * @return a reference to a player found or null if not found
     */
    private Player findPlayer(String string) {
        for (Player player : registeredPlayers) {
            if (player.getName().equals(string))
                return player;
        }
        return null;
    }

    /**
     * Sets a player for game instance.
     *
     * @param selected selected player
     * @return true if the operation is successful, else - false
     */
    public boolean setActivePlayer(Player selected) {
        if (selected == null) {
            setLastError("Please select a player or register.");
            return false;
        }
        Main.getGameInstance().setPlayer(selected);
        return true;
    }
}
