package org.sillylossy.games.common.ui.listeners;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * "How to play" menu item action listener.
 */
public final class HowToPlayMenuItemListener implements ActionListener {
    /**
     * Opens game rules in default browser.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://goo.gl/oh5oT2"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }
}