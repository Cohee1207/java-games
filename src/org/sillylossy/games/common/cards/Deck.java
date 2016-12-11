package org.sillylossy.games.common.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents a deck of cards.
 */
public class Deck {

    /**
     * Computed field that contains a deck's length.
     */
    private static final int deckLength = CardSuit.values().length * CardRank.values().length;

    /**
     * Contains a basic (ordered) deck.
     */
    public static final List<Card> INITIAL_DECK = createInitial();

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
    public static Deck create() {
        Deck deck = new Deck();
        deck.cards = new Stack<>();
        deck.cards.addAll(INITIAL_DECK);
        java.util.Collections.shuffle(deck.cards);
        return deck;
    }

    /**
     * Constructs an initial deck creating a card of every card suit and rank combination.
     */
    private static List<Card> createInitial() {
        List<Card> list = new ArrayList<>(deckLength);
        CardSuit[] suits = CardSuit.values();
        CardRank[] ranks = CardRank.values();
        for (CardSuit suit : suits) {
            for (CardRank rank : ranks) {
                list.add(new Card(rank, suit));
            }
        }
        return list;
    }

    /**
     * Gets one card. That card is deleted from a deck.
     */
    public Card draw() {
        return cards.pop();
    }
}