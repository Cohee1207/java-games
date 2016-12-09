package org.sillylossy.games.common.ui.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * "New player" button action listener.
 */
public final class NewPlayerButtonListener extends GameListener {
    /**
     * Registers a player if the player's name matches the necessary conditions.
     * Sets player active. If an error occurs, shows a message.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = JOptionPane.showInputDialog("Enter your name");
        if (name == null || name.isEmpty()) {
            return;
        }
        name = name.trim();
        if (getGameController().register(name)) {
            getMainPanel().flipToGame();
            getGamePanel().start();
        } else {
            String error = getGameController().getLastError();
            getUI().alert(error);
        }
    }
}