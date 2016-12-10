package org.sillylossy.games.blackjack.ui.listeners;

import org.sillylossy.games.blackjack.game.BlackjackGame;
import org.sillylossy.games.blackjack.ui.BlackjackPanel;
import org.sillylossy.games.common.ui.listeners.GameListener;

import java.awt.event.ActionEvent;

/**
 * "Accept" bet button action listener.
 */
public final class AcceptBetButtonListener extends GameListener {
    /**
     * Updates UI, calls start game event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BlackjackPanel panel = (BlackjackPanel) getGamePanel();
        BlackjackGame game = (BlackjackGame) getGameInstance();
        panel.setBetButtons(false);
        panel.setActionButtons(true);
        game.betAction(panel.getBetValue());
        panel.updateStatus();
        getMainPanel().flipToGame();
        panel.redraw();
        if (getGameInstance().shouldEnd()) {
            panel.processResults();
        }
    }
}