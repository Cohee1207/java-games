package org.sillylossy.games.blackjack.ui.listeners;

import org.sillylossy.games.blackjack.game.BlackjackGame;
import org.sillylossy.games.blackjack.ui.BlackjackPanel;
import org.sillylossy.games.common.ui.listeners.GameListener;

import java.awt.event.ActionEvent;

/**
 * "Stand" button action listener.
 */
public final class StandButtonListener extends GameListener {
    /**
     * Performs "stand" game action.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        BlackjackPanel panel = (BlackjackPanel) getGamePanel();
        BlackjackGame game = (BlackjackGame) getGameInstance();
        game.standAction();
        panel.processResults();
    }
}