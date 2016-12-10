package org.sillylossy.games.blackjack.ui.listeners;

import org.sillylossy.games.blackjack.game.BlackjackGame;
import org.sillylossy.games.blackjack.ui.BlackjackPanel;
import org.sillylossy.games.common.ui.listeners.GameListener;

import java.awt.event.ActionEvent;

/**
 * "Hit" game button action listener.
 */
public final class HitButtonListener extends GameListener {
    /**
     * Calls a "take card" game event. Adds a taken card to panel.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BlackjackPanel panel = (BlackjackPanel) getGamePanel();
        BlackjackGame game = (BlackjackGame) getGameInstance();
        panel.cardButtonAction(game.hitAction());
    }
}