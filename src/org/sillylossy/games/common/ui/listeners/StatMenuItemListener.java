package org.sillylossy.games.common.ui.listeners;

import org.sillylossy.games.common.game.Statistics;
import org.sillylossy.games.common.players.Player;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * "Statistics" menu item action listener.
 */
public final class StatMenuItemListener extends GameListener {
    /**
     * Gets a statistics map from a game controller and forms a table model from it.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        getMainPanel().flipToStatistics();
        Map<Player, Statistics> stats = getGameController().getStatistics();
        int count = stats.size();
        Player[] players = stats.keySet().toArray(new Player[count]);
        String[] labels = new String[]{"Player", "Total games", "Games won", "Games lost", "Games drawn"};
        Object[][] vector = new Object[players.length][labels.length];
        for (int i = 0; i < vector.length; i++) {
            int won = stats.get(players[i]).getGamesWon();
            int lost = stats.get(players[i]).getGamesLost();
            int stay = stats.get(players[i]).getGamesPushed();
            int total = won + lost + stay;
            vector[i][0] = players[i].toString();
            vector[i][1] = total;
            vector[i][2] = won;
            vector[i][3] = lost;
            vector[i][4] = stay;
        }
        getMainPanel().setStats(new DefaultTableModel(vector, labels));
    }
}