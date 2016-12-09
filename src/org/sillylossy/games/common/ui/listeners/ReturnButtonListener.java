package org.sillylossy.games.common.ui.listeners;

import java.awt.event.ActionEvent;

/**
 * "Return" button event listener.
 */
public final class ReturnButtonListener extends GameListener {
    /**
     * Show game panel, player selection or game selection.
     * Depends on selection states.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (getGameController().isInGame()) {
            getMainPanel().flipToGame();
        } else if (getMainPanel().getGamePanel() == null) {
            getMainPanel().flipToGameSelection();
        } else {
            getMainPanel().flipToPlayerSelection();
        }
    }
}