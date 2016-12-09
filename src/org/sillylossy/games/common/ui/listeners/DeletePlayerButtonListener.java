package org.sillylossy.games.common.ui.listeners;

import java.awt.event.ActionEvent;

/**
 * "Delete player" button action listener.
 */
public final class DeletePlayerButtonListener extends GameListener {
    /**
     * Asks for a confirmation. If an answer is "Yes" then player is deleted.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (getUI().confirm("Are you sure?")) {
            if (!getGameController().deletePlayer(getMainPanel().getSelectedPlayer())) {
                getUI().alert(getGameController().getLastError());
            }
            getUI().getMainPanel().flipToPlayerSelection();
        }
    }
}