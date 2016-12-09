package org.sillylossy.games.common.ui.listeners;

import org.sillylossy.games.common.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * "About" menu item action listener.
 */
public final class AboutMenuItemListener implements ActionListener {
    /**
     * Shows information about this program.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Main.getUI().alert(
                "Author: SillyLossy (http://github.com/sillylossy).\n\n" +
                        "Card images: https://code.google.com/archive/p/vector-playing-cards/ \n" +
                        "Card back image: http://svg-cards.sourceforge.net/ \n");
    }
}