package org.sillylossy.games.common.ui;

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

    GameController getGameController() {
        return Main.getGameController();
    }

    GamePanel getGamePanel() {
        return Main.getUI().getMainPanel().getGamePanel();
    }

    Game getGameInstance() {
        return Main.getGameInstance();
    }

    MainPanel getMainPanel() {
        return Main.getUI().getMainPanel();
    }

    GameInterface getUI() {
        return Main.getUI();
    }

}
