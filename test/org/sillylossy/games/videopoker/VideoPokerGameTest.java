package org.sillylossy.games.videopoker;

import org.junit.Assert;
import org.junit.Test;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.CardFace;
import org.sillylossy.games.common.cards.CardSuit;

import static org.sillylossy.games.videopoker.PokerRepeats.getRepeats;

public class VideoPokerGameTest {
    @Test
    public void testOnePairDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardFace.ACE, CardSuit.CLUBS),
                new Card(CardFace.ACE, CardSuit.DIAMONDS),
                new Card(CardFace.EIGHT, CardSuit.HEARTS),
                new Card(CardFace.FIVE, CardSuit.SPADES),
                new Card(CardFace.KING, CardSuit.DIAMONDS)
        };
        Assert.assertTrue(getRepeats(cards).hasOnePair());
    }

    @Test
    public void testThreeDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardFace.ACE, CardSuit.CLUBS),
                new Card(CardFace.ACE, CardSuit.DIAMONDS),
                new Card(CardFace.ACE, CardSuit.HEARTS),
                new Card(CardFace.FIVE, CardSuit.SPADES),
                new Card(CardFace.KING, CardSuit.DIAMONDS)
        };
        Assert.assertTrue(getRepeats(cards).hasThree());
    }

    @Test
    public void testFourDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardFace.ACE, CardSuit.CLUBS),
                new Card(CardFace.ACE, CardSuit.DIAMONDS),
                new Card(CardFace.ACE, CardSuit.HEARTS),
                new Card(CardFace.ACE, CardSuit.SPADES),
                new Card(CardFace.KING, CardSuit.DIAMONDS)
        };
        Assert.assertTrue(getRepeats(cards).hasFour());
    }

    @Test
    public void testFullHouseDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardFace.ACE, CardSuit.CLUBS),
                new Card(CardFace.ACE, CardSuit.DIAMONDS),
                new Card(CardFace.ACE, CardSuit.HEARTS),
                new Card(CardFace.KING, CardSuit.SPADES),
                new Card(CardFace.KING, CardSuit.DIAMONDS)
        };
        Assert.assertTrue(getRepeats(cards).hasFullHouse());
    }

    @Test
    public void testTwoPairDetection() throws Exception {
        Card[] cards = new Card[]{
                new Card(CardFace.ACE, CardSuit.CLUBS),
                new Card(CardFace.ACE, CardSuit.DIAMONDS),
                new Card(CardFace.EIGHT, CardSuit.HEARTS),
                new Card(CardFace.EIGHT, CardSuit.SPADES),
                new Card(CardFace.KING, CardSuit.DIAMONDS)
        };
        Assert.assertTrue(getRepeats(cards).hasTwoPair());
    }
}