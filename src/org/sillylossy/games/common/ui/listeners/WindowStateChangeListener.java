package org.sillylossy.games.common.ui.listeners;

import org.sillylossy.games.common.Main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * Window state change event listener.
 */
public final class WindowStateChangeListener implements WindowStateListener {
    /**
     * Redraws cards when window state changes.
     */
    @Override
    public void windowStateChanged(WindowEvent e) {
        Main.getUI().getMainPanel().getGamePanel().redraw();
    }
}