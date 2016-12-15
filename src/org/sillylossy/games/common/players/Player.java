package org.sillylossy.games.common.players;

/**
 * Represents a human player.
 */
public class Player extends Participant implements java.io.Serializable {

    /**
     * Initial score of a player.
     */
    private final static int INITIAL_SCORE = 500;

    /**
     * Player's name.
     */
    private final String playerName;

    /**
     * Last player's bet.
     */
    private transient int bet;

    /**
     * Player's score.
     */
    private int score;

    /**
     * Creates a player with specified name.
     *
     * @param name player's name
     */
    public Player(String name) {
        score = INITIAL_SCORE;
        playerName = name;
    }

    /**
     * Decreases player score with amount.
     */
    public void decreaseScore(int amount) {
        score -= amount;
    }

    /**
     * Returns player's bet.
     */
    public int getBet() {
        return bet;
    }

    /**
     * Sets player's bet with amount.
     */
    public void setBet(int amount) {
        this.bet = amount;
    }

    /**
     * Gets player's name.
     */
    public String getName() {
        return playerName;
    }

    /**
     * Возвращает счет игрока.
     */
    public int getScore() {
        return score;
    }

    /**
     * Increases player score with amount.
     */
    public void increaseScore(int amount) {
        score += amount;
    }

    /**
     * Resets a player's score to it's initial value.
     */
    public void resetScore() {
        this.score = INITIAL_SCORE;
    }

    /**
     * Return a string representation of a player (his name and score).
     */
    @Override
    public String toString() {
        return String.format("%s [score = %s $]", playerName, score);
    }
}