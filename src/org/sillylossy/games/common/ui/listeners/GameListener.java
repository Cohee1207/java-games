package org.sillylossy.games.common.ui.listeners;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.game.Game;
import org.sillylossy.games.common.game.GameController;
import org.sillylossy.games.common.ui.GameInterface;
import org.sillylossy.games.common.ui.GamePanel;
import org.sillylossy.games.common.ui.MainPanel;

import java.awt.event.ActionListener;

/**
 * A superclass for all game-related action listeners. Provides a proxy-methods
 * for it's descenders. Allows not to think about the details of implementation of the internal relations.
 */
public abstract class GameListener implements ActionListener {

    protected GameListener() {
    }

    protected GameController getGameController() {
        return Main.getGameController();
    }

    protected GamePanel getGamePanel() {
        return Main.getUI().getMainPanel().getGamePanel();
    }

    protected Game getGameInstance() {
        return Main.getGameInstance();
    }

    protected MainPanel getMainPanel() {
        return Main.getUI().getMainPanel();
    }

    protected GameInterface getUI() {
        return Main.getUI();
    }

}
