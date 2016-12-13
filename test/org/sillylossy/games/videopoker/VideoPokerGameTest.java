package org.sillylossy.games.videopoker;

import org.junit.Assert;
import org.junit.Test;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardRank;
import org.sillylossy.games.common.cards.CardSuit;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.sillylossy.games.videopoker.PokerCombinations.getCombinations;

public class VideoPokerGameTest {
    private static void shuffleArray(Object[] array) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = 0; i < array.length - 1; ++i) {
            int index = rnd.nextInt(i + 1);
            Object o = array[index];
            array[index] = array[i];
            array[i] = o;
        }
    }

    @Test
    public void testOnePairDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.ACE, CardSuit.DIAMONDS),
                new Card(CardRank.EIGHT, CardSuit.HEARTS),
                new Card(CardRank.FIVE, CardSuit.SPADES),
                new Card(CardRank.KING, CardSuit.DIAMONDS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasOnePair());
    }

    @Test
    public void testThreeDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.ACE, CardSuit.DIAMONDS),
                new Card(CardRank.ACE, CardSuit.HEARTS),
                new Card(CardRank.FIVE, CardSuit.SPADES),
                new Card(CardRank.KING, CardSuit.DIAMONDS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasThree());
    }

    @Test
    public void testFourDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.ACE, CardSuit.DIAMONDS),
                new Card(CardRank.ACE, CardSuit.HEARTS),
                new Card(CardRank.ACE, CardSuit.SPADES),
                new Card(CardRank.KING, CardSuit.DIAMONDS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasFour());
    }

    @Test
    public void testFullHouseDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.ACE, CardSuit.DIAMONDS),
                new Card(CardRank.ACE, CardSuit.HEARTS),
                new Card(CardRank.KING, CardSuit.SPADES),
                new Card(CardRank.KING, CardSuit.DIAMONDS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasFullHouse());
    }

    @Test
    public void testTwoPairDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.ACE, CardSuit.DIAMONDS),
                new Card(CardRank.EIGHT, CardSuit.HEARTS),
                new Card(CardRank.EIGHT, CardSuit.SPADES),
                new Card(CardRank.KING, CardSuit.DIAMONDS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasTwoPair());
    }

    @Test
    public void testFlushDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.KING, CardSuit.CLUBS),
                new Card(CardRank.TWO, CardSuit.CLUBS),
                new Card(CardRank.EIGHT, CardSuit.CLUBS),
                new Card(CardRank.QUEEN, CardSuit.CLUBS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasFlush());
    }

    @Test
    public void testStraightDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.NINE, CardSuit.CLUBS),
                new Card(CardRank.KING, CardSuit.DIAMONDS),
                new Card(CardRank.QUEEN, CardSuit.HEARTS),
                new Card(CardRank.JACK, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.DIAMONDS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasStraight());
        cards = new Card[]{
                new Card(CardRank.EIGHT, CardSuit.CLUBS),
                new Card(CardRank.TWO, CardSuit.DIAMONDS),
                new Card(CardRank.QUEEN, CardSuit.HEARTS),
                new Card(CardRank.ACE, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.DIAMONDS)
        };
        shuffleArray(cards);
        Assert.assertFalse(getCombinations(cards, CardRank.TWO).hasStraight());
    }

    @Test
    public void testStraightFlushDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.TWO, CardSuit.DIAMONDS),
                new Card(CardRank.THREE, CardSuit.DIAMONDS),
                new Card(CardRank.FOUR, CardSuit.DIAMONDS),
                new Card(CardRank.FIVE, CardSuit.DIAMONDS),
                new Card(CardRank.SIX, CardSuit.DIAMONDS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasStraightFlush());
    }

    @Test
    public void testRoyalFlushDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.KING, CardSuit.CLUBS),
                new Card(CardRank.QUEEN, CardSuit.CLUBS),
                new Card(CardRank.JACK, CardSuit.CLUBS),
                new Card(CardRank.TEN, CardSuit.CLUBS)
        };
        shuffleArray(cards);
        Assert.assertTrue(getCombinations(cards, CardRank.TWO).hasRoyalFlush());
    }
}