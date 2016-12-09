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
    protected ImageController images = new ImageController();

    /**
     * Constructs a game panel - sets listener and colors.
     */
    protected GamePanel() {
        addComponentListener(new ResizePanelListener());
        setBorder(new LineBorder(Color.GRAY, 1, true));
        setBackground(new Color(34, 139, 34));
    }

    /**
     * Redraws a game panel.
     */
    public abstract void redraw();

    /**
     * Starts a game.
     */
    public abstract void start();

    /**
     * Panel resizing event handler.
     */
    final class ResizePanelListener extends ComponentAdapter {
        /**
         * Redraws a cards when panel is resized.
         */
        @Override
        public void componentResized(ComponentEvent event) {
            redraw();
        }
    }
}
