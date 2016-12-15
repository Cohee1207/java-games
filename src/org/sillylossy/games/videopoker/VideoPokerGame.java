package org.sillylossy.games.videopoker;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardRank;
import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.game.CardGame;
import org.sillylossy.games.common.game.StatEvent;
import org.sillylossy.games.common.ui.images.CardImageController;
import org.sillylossy.games.videopoker.PokerCombinations.Combination;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class VideoPokerGame extends CardGame {

    public static final String GAME_NAME = "Video poker";

    private Map<Combination, Integer> payTable;

    public static Image getIcon() throws IOException {
        return ImageIO.read(VideoPokerGame.class.getResourceAsStream(GAME_NAME + CardImageController.IMAGE_EXT));
    }

    private Map<Combination, Integer> createPayTable(int bet) {
        Map<Combination, Integer> table = new EnumMap<>(Combination.class);
        table.put(Combination.OTHER, 0);
        table.put(Combination.ONE_PAIR, bet);
        table.put(Combination.TWO_PAIR, bet * 2);
        table.put(Combination.THREE_CARDS, bet * 3);
        table.put(Combination.STRAIGHT, bet * 4);
        table.put(Combination.FLUSH, bet * 6);
        table.put(Combination.FULL_HOUSE, bet * 9);
        table.put(Combination.FOUR_CARDS, bet * 25);
        table.put(Combination.STRAIGHT_FLUSH, bet * 50);
        table.put(Combination.ROYAL_FLUSH, bet * 250);
        return table;
    }

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public boolean shouldEnd() {
        return false;
    }

    public String getResult() {
        String result = "";
        final CardRank MIN_CARD = CardRank.JACK;
        int pay = 0;
        Card[] cards = getPlayer().getHand().getCards();
        PokerCombinations combinations = PokerCombinations.getCombinations(cards, MIN_CARD);
        StatEvent statEvent = StatEvent.LOST;
        switch (combinations.getBestCombination()) {
            case OTHER:
                result = PokerCombinations.toString(Combination.OTHER) + ". You've lost your bet";
                statEvent = StatEvent.LOST;
                break;
            case ONE_PAIR:
                pay = payTable.get(Combination.ONE_PAIR);
                result = PokerCombinations.toString(Combination.ONE_PAIR) + ". You keep your bet";
                statEvent = StatEvent.PUSH;
                break;
            case TWO_PAIR:
                pay = payTable.get(Combination.TWO_PAIR);
                result = PokerCombinations.toString(Combination.TWO_PAIR) + ". You've won " + pay + "$";
                statEvent = StatEvent.WON;
                break;
            case THREE_CARDS:
                pay = payTable.get(Combination.THREE_CARDS);
                result = PokerCombinations.toString(Combination.THREE_CARDS) + ". You've won " + pay + "$";
                statEvent = StatEvent.WON;
                break;
            case STRAIGHT:
                pay = payTable.get(Combination.STRAIGHT);
                result = PokerCombinations.toString(Combination.STRAIGHT) + ". You've won " + pay + "$";
                statEvent = StatEvent.WON;
                break;
            case FLUSH:
                pay = payTable.get(Combination.FLUSH);
                result = PokerCombinations.toString(Combination.FLUSH) + ". You've won " + pay + "$";
                statEvent = StatEvent.WON;
                break;
            case FULL_HOUSE:
                pay = payTable.get(Combination.FOUR_CARDS);
                result = PokerCombinations.toString(Combination.FULL_HOUSE) + ". You've won " + pay + "$";
                statEvent = StatEvent.WON;
                break;
            case FOUR_CARDS:
                pay = payTable.get(Combination.FOUR_CARDS);
                result = PokerCombinations.toString(Combination.FOUR_CARDS) + ". You've won " + pay + "$";
                statEvent = StatEvent.WON;
                break;
            case STRAIGHT_FLUSH:
                pay = payTable.get(Combination.STRAIGHT_FLUSH);
                result = PokerCombinations.toString(Combination.STRAIGHT_FLUSH) + ". You've won " + pay + "$";
                statEvent = StatEvent.WON;
                break;
            case ROYAL_FLUSH:
                pay = payTable.get(Combination.ROYAL_FLUSH);
                result = PokerCombinations.toString(Combination.ROYAL_FLUSH) + ". You've won " + pay + "$";
                statEvent = StatEvent.WON;
                break;
        }
        Main.getGameController().addStatEvent(player, statEvent);
        player.increaseScore(pay);
        reset();
        Main.saveData();
        return result;
    }

    void discard() {
        player.increaseScore(player.getBet());
        reset();
    }

    @Override
    public void reset() {
        player.getHand().clear();
        player.setBet(0);
    }

    @Override
    protected void dealCards() {
        setDeck(Deck.create());
        final int POKER_CARDS = 5;
        for (int i = 0; i < POKER_CARDS; i++) {
            player.getHand().addCard(deck.draw());
        }
    }

    @Override
    public void betAction(int bet) {
        player.setBet(bet);
        payTable = createPayTable(bet);
        player.decreaseScore(bet);
        dealCards();
    }

    @Override
    public int getMaxBet() {
        return player.getScore();
    }

    @Override
    public int getMinBet() {
        return player.getScore() / 50;
    }

    Card changeCard(Card card) {
        Card newCard = deck.draw();
        player.getHand().replaceCard(card, newCard);
        return newCard;
    }

    Map<Combination, Integer> getPayTable() {
        return payTable;
    }
}

