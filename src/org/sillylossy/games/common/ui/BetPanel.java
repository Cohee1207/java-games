package org.sillylossy.games.common.ui;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.game.BetGame;
import org.sillylossy.games.common.players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class BetPanel extends GamePanel {
    /**
     * JSpinner for betting.
     */
    private final JSpinner betSpinner = new JSpinner();

    /**
     * Spinner model holds values used for betting (min, max, step).
     */
    private final SpinnerNumberModel betSpinnerModel = new SpinnerNumberModel();

    /**
     * "Accept" bet button.
     */
    private final JButton btnBet = new JButton("Accept");
    private final JPanel betPanel = new JPanel();

    /**
     * Creates constraints for game's components.
     *
     * @param y    vertical coordinate
     * @param fill filling flag
     * @return component's constraints objects
     */
    protected static GridBagConstraints getGBC(int y, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = fill;
        gbc.gridx = 0;
        gbc.gridy = y;
        return gbc;
    }

    private BetGame getGame() {
        return (BetGame) Main.getGame();
    }

    /**
     * Gets selected bet value.
     */
    private int getBetValue() {
        return (int) betSpinner.getValue();
    }

    /**
     * Checks player's score. If it's too low, shows a proposal to reset score.
     *
     * @return check result. false if player has enough score or did reset it.
     * true if player didn't want to reset score
     */
    private boolean checkScore() {
        Player player = getGame().getPlayer();
        final int LOW_SCORE = 20;
        if (player.getScore() <= LOW_SCORE) {
            if (Main.getUI().confirm(" You almost have 0 $ \n Reset score?")) {
                player.resetScore();
                return false;
            } else {
                Main.getUI().getMainPanel().flipToPlayerSelection();
                return true;
            }
        }
        return false;
    }

    /**
     * Creates label with specified text, white foreground and Arial font.
     *
     * @param text text on the label
     */
    protected JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(255, 255, 255));
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        return lbl;
    }

    protected abstract void setActionButtons(boolean b);

    /**
     * Shows bet selection mode.
     */
    protected void flipToBets() {
        if (checkScore()) {
            return;
        }
        setBetButtons(true);
        setActionButtons(false);
        updateStatus();
        int min = getGame().getMinBet();
        if (min <= 0) {
            min = 1;
        }
        int max = getGame().getMaxBet();
        betSpinnerModel.setMinimum(min);
        betSpinnerModel.setMaximum(max);
        betSpinnerModel.setStepSize(max / 10);
        betSpinnerModel.setValue(min);
    }

    /**
     * Sets bet buttons on / off depending on boolean value.
     */
    private void setBetButtons(boolean b) {
        betPanel.setVisible(b);
    }

    protected abstract JPanel createGameActions();

    /**
     * Creates action buttons (game, bets) panel.
     */
    protected JPanel createActionsPanel() {
        JPanel actionPanel = new JPanel();
        actionPanel.add(createGameActions());
        actionPanel.add(createBetPanel());
        return actionPanel;
    }

    private JPanel createBetPanel() {
        betPanel.add(new JLabel("Bet:"));
        betPanel.add(Box.createHorizontalStrut(20));
        betSpinner.setModel(betSpinnerModel);
        betPanel.add(betSpinner);
        betPanel.add(btnBet);
        ((JSpinner.DefaultEditor) betSpinner.getEditor()).getTextField().setColumns(5);
        btnBet.addActionListener(new AcceptBetButtonAction());
        return betPanel;
    }

    /**
     * "Accept" bet button action listener.
     */
    public final class AcceptBetButtonAction extends AbstractAction {
        /**
         * Updates UI, calls start game event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            setBetButtons(false);
            setActionButtons(true);
            getGame().betAction(getBetValue());
            updateStatus();
            initGame();
            Main.getUI().getMainPanel().flipToGame();
            redraw();
            if (Main.getGame().shouldEnd()) {
                processResults();
            }
        }
    }
}
