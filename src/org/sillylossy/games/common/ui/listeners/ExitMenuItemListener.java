package org.sillylossy.games.common.ui.listeners;

import org.sillylossy.games.common.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * "Exit" menu item action listener.
 */
public final class ExitMenuItemListener implements ActionListener {
    /**
     * Calls GUI's "exit" method.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Main.getUI().exit();
    }
}