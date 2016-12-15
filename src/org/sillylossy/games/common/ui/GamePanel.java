package org.sillylossy.games.common.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * An abstraction of all game panels.
 */
public abstract class GamePanel extends JPanel {

    protected static final String NEW_GAME_BUTTON_TEXT = "New game";
    protected static final Color BACKGROUND_COLOR = new Color(34, 139, 34);

    /**
     * Constructs a game panel - sets listener and colors.
     */
    GamePanel() {
        addComponentListener(new ResizePanelListener());
        setBackground(BACKGROUND_COLOR);
    }

    /**
     * Redraws a game panel.
     */
    public abstract void redraw();

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

    /**
     * Panel resizing event handler.
     */
    private final class ResizePanelListener extends ComponentAdapter {
        /**
         * Redraws a cards when panel is resized.
         */
        @Override
        public void componentResized(ComponentEvent event) {
            redraw();
        }
    }
}
