package org.sillylossy.games.common.cards;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Represents a deck of cards.
 */
public class Deck {

    /**
     * Contains a basic (ordered) deck.
     */
    public static final List<Card> FULL_DECK = createDeck(CardRank.values());

    private static final CardRank[] SHORT_RANKS = new CardRank[]{
            CardRank.SIX,
            CardRank.SEVEN,
            CardRank.EIGHT,
            CardRank.NINE,
            CardRank.TEN,
            CardRank.JACK,
            CardRank.QUEEN,
            CardRank.KING,
            CardRank.ACE
    };

    private static final List<Card> SHORT_DECK = createDeck(SHORT_RANKS);

    /**
     * A collections of cards that are in the deck.
     */
    private Stack<Card> cards = new Stack<>();

    /**
     * Private constructor.
     * Instances are created with factory method.
     */
    private Deck() {
        // empty body
    }

    /**
     * Creates a new deck. This methods takes a basic deck, copies it to a new collection and then shuffles it.
     */
    private static Deck getShuffled(List<Card> cards) {
        Deck deck = new Deck();
        deck.cards = new Stack<>();
        deck.cards.addAll(cards);
        java.util.Collections.shuffle(deck.cards);
        return deck;
    }

    /**
     * Constructs an initial deck creating a card of every card suit and rank combination.
     */
    private static List<Card> createDeck(CardRank[] ranks) {
        List<Card> list = new ArrayList<>();
        CardSuit[] suits = CardSuit.values();
        for (CardSuit suit : suits) {
            for (CardRank rank : ranks) {
                list.add(new Card(rank, suit));
            }
        }
        return list;
    }

    public static Deck getShortDeck() {
        return getShuffled(SHORT_DECK);
    }

    public static Deck getFullDeck() {
        return getShuffled(FULL_DECK);
    }

    /**
     * Gets one card. That card is deleted from a deck.
     */
    public Card draw() {
        try {
            return cards.pop();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card getLast() {
        return cards.get(0);
    }

    public int cardsLeft() {
        return cards.size();
    }
}