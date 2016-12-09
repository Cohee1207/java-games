package org.sillylossy.games.common.ui.listeners;

import org.sillylossy.games.common.players.Player;

import java.awt.event.ActionEvent;

/**
 * "Select player" button event listener.
 */
public final class SelectPlayerButtonListener extends GameListener {
    /**
     * If a player is selected, sets it active. Else shows an error message.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Player selected = getMainPanel().getSelectedPlayer();
        if (getGameController().setActivePlayer(selected)) {
            getMainPanel().flipToGame();
            getGamePanel().start();
        } else {
            String error = getGameController().getLastError();
            getUI().alert(error);
        }
    }
}