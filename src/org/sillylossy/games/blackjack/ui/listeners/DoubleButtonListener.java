package org.sillylossy.games.blackjack.ui.listeners;

import org.sillylossy.games.blackjack.game.BlackjackGame;
import org.sillylossy.games.blackjack.ui.BlackjackPanel;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.ui.listeners.GameListener;

import java.awt.event.ActionEvent;

/**
 * "Double" game action button action listener.
 */
public final class DoubleButtonListener extends GameListener {
    /**
     * Calls "double bet" game event. If player has insufficient score, shows a message.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Card card = ((BlackjackGame) getGameInstance()).doubleAction();
        if (card == null) {
            getUI().alert("You don't have enough score to double your bet.");
            return;
        }
        BlackjackPanel panel = (BlackjackPanel) getGamePanel();
        panel.cardButtonAction(card);
    }
}