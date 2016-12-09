package org.sillylossy.games.common.ui.listeners;

import org.sillylossy.games.common.Main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Window closing event listener.
 */
public final class WindowClosingListener extends WindowAdapter {
    /**
     * Calls GUI's "exit" handler.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        Main.getUI().exit();
    }
}