package org.sillylossy.games.common.ui;

import org.sillylossy.games.common.ui.images.ImageController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * An abstraction of all game panels.
 */
public abstract class GamePanel extends JPanel {

    /**
     * An active images controller.
     */
    protected final ImageController images = new ImageController();

    /**
     * Constructs a game panel - sets listener and colors.
     */
    GamePanel() {
        addComponentListener(new ResizePanelListener());
        setBorder(new LineBorder(Color.GRAY, 1, true));
        gameArea.setBackground(new Color(34, 139, 34));
        setLayout(new BorderLayout());
        add(createStatusBar(), BorderLayout.SOUTH);
        add(gameArea, BorderLayout.CENTER);
    }

    protected final JPanel gameArea = new JPanel();

    /**
     * Redraws a game panel.
     */
    public abstract void redraw();

    /**
     * Label with games status (current player, hand value, etc).
     */
    protected final JLabel lblStatus = new JLabel();

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

    public abstract void processResults();

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
