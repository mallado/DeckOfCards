package com.appian.test.deck;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Array implementation of the {@code IDeck} interface. For this implementation
 * an array of cards is used to generate the deck.
 * 
 * @author fmallado
 * @see IDeck
 * @since 1.0.0.0
 */
public final class ArrayDeck implements IDeck, Serializable {

	/**
	 * serialVersionUID for Serializable inteface.
	 */
	private static final long serialVersionUID = -8366504421852901296L;

	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(ArrayDeck.class);

	/**
	 * The array buffer into which the elements of the ArrayDeck are stored.
	 */
	private final Card[] cards;

	/**
	 * The size of the ArrayDeck (the number of {@code card} it contains).
	 */
	private int size;

	/**
	 * Random property to permute the cards in the deck
	 */
	private Random rand;

	/**
	 * Constructs an deck of poker-style playing cards. (Fifty-two playing cards in
	 * four suits: hearts, spades, clubs, diamonds, with face values of Ace, 2-10,
	 * Jack, Queen, and King.).
	 * 
	 * @throws NoSuchAlgorithmException if no algorithm is available for
	 *                                  SecureRandom object.
	 */
	public ArrayDeck() throws NoSuchAlgorithmException {
		logger.trace("Start of the ArrayDeck constructor.");
		// The size of the deck is calculated
		size = Suit.values().length * (Card.MAX_RANK - Card.MIN_RANK + 1);
		logger.debug("Creating a deck of {} cards", size);
		cards = new Card[size];
		// The deck is initialized and reordered randomly
		shuffle();
		logger.trace("End of the ArrayDeck constructor.");
	}

	/**
	 * Restore the deck with its 52 cards.
	 */
	private void restore() {
		logger.trace("Start the reset method.");
		int i = 0;
		// All cards are created
		for (Suit suit : Suit.values()) {
			// For each suits
			for (int rank = Card.MIN_RANK; rank <= Card.MAX_RANK; rank++) {
				// With the rank and suits we have a card
				cards[i] = new Card(rank, suit);
				i++;
			}
		}
		// the deck has all the cards created
		size = i;
		logger.debug("The deck is restored with its {} cards in order.", size);
		logger.trace("End the reset method.");
	}

	/**
	 * Restart the deck with 52 new cards and the cards in the deck being randomly
	 * permuted.
	 * 
	 * @throws NoSuchAlgorithmException if no algorithm is available for
	 *                                  SecureRandom object.
	 */
	public void shuffle() throws NoSuchAlgorithmException {
		logger.trace("Start the shuffle method.");
		// The state of the deck is restored to have all the cards
		restore();
		logger.debug("start of random permutation.");
		if (this.rand == null) {
			this.rand = SecureRandom.getInstanceStrong();
		}
		// The deck is iterated over to randomly permuted the cards.
		for (int i = 0; i < size; i++) {
			int newPosition = this.rand.nextInt(size);
			logger.debug("Exchanging card from position {} for {}", i + 1, newPosition);
			Card tmp = cards[newPosition];
			cards[newPosition] = cards[i];
			cards[i] = tmp;
		}
		logger.debug("End of random permutation.");
		logger.trace("End the shuffle method.");
	}

	/**
	 * Returns the {@code card} at the top position in this deck.
	 *
	 * @return the {@code card} at the top position in this deck.
	 * @throws IllegalStateException if the deck is empty. Can't deal from an empty
	 *                               deck.
	 */
	public Card dealOneCard() {
		logger.trace("Start the dealOneCard method.");
		// Check if the deck is empty
		if (empty()) {
			throw new IllegalStateException("The deck is empty. Can't deal from an empty deck.");
		}
		// The stack pointer moves and the card is returned
		size--;
		Card card = cards[size];
		logger.debug("return card: {}, {} cards left in the deck.", card, size);
		logger.trace("End the dealOneCard method.");
		return card;
	}

	/**
	 * Returns {@code true} if this deck contains no cards.
	 *
	 * @return {@code true} if this deck contains no cards
	 */
	public boolean empty() {
		return size == 0;
	}

	/**
	 * Returns the size of the deck
	 * 
	 * @return size of the deck
	 */
	public int size() {
		return size;
	}

}
