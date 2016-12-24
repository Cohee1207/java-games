package org.sillylossy.games.common.ui;

import org.sillylossy.games.common.resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

/**
 * An abstraction of all game panels.
 */
public abstract class GamePanel extends JPanel {

    protected static final String NEW_GAME_BUTTON_TEXT = "New game";
    protected static final Color BACKGROUND_COLOR = new Color(34, 139, 34);
    protected ResourceManager mgr = ResourceManager.getInstance();

    /**
     * Constructs a game panel - sets listener and colors.
     */
    GamePanel() {
        setBackground(BACKGROUND_COLOR);
    }

    /**
     * Redraws a game panel.
     */
    protected abstract void redraw();

    public abstract void clear();

    /**
     * Updates player's status in status bar.
     */
    protected abstract void updateStatus();

    protected abstract void initGame();

    /**
     * Starts a game.
     */
    public abstract void start();

    protected abstract void processResults();
}
