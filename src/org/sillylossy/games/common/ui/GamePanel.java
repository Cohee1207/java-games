package org.sillylossy.games.common.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * An abstraction of all game panels.
 */
public abstract class GamePanel extends JPanel {

    protected final JPanel gameArea = new JPanel();
    /**
     * Label with games status (current player, hand value, etc).
     */
    protected final JLabel lblStatus = new JLabel();

    /**
     * Constructs a game panel - sets listener and colors.
     */
    GamePanel() {
        addComponentListener(new ResizePanelListener());
        gameArea.setBackground(new Color(34, 139, 34));
        setLayout(new BorderLayout());
        add(createStatusBar(), BorderLayout.SOUTH);
        add(gameArea, BorderLayout.CENTER);
    }

    /**
     * Redraws a game panel.
     */
    public abstract void redraw();

    /**
     * Creates status bar with status label.
     */
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.add(lblStatus);
        return statusBar;
    }

    /**
     * Updates player's status in status bar.
     */
    protected abstract void updateStatus();

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
