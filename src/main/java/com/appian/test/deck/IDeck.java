package com.appian.test.deck;

import java.security.NoSuchAlgorithmException;

/**
 * Represent a deck of poker-style playing cards. (Fifty-two playing cards in
 * four suits: hearts, spades, clubs, diamonds, with face values of Ace, 2-10,
 * Jack, Queen, and King.)
 * 
 * @author fmallado
 * @see ArrayDeck
 * @since 1.0.0.0
 */
public interface IDeck {

	/**
	 * Restart the deck with 52 new cards and the cards in the deck being randomly
	 * permuted.
	 * 
	 * @throws NoSuchAlgorithmException NoSuchAlgorithmException if no algorithm is
	 *                                  available for SecureRandom object.
	 */
	void shuffle() throws NoSuchAlgorithmException;

	/**
	 * Returns the {@code card} at the top position in this deck.
	 *
	 * @return the {@code card} at the top position in this deck.
	 * @throws IllegalStateException if the deck is empty. Can't deal from an empty
	 *                               deck.
	 */
	Card dealOneCard() throws IllegalStateException;

	/**
	 * Returns {@code true} if this deck contains no cards.
	 *
	 * @return {@code true} if this deck contains no cards
	 */
	boolean empty();

	/**
	 * Returns the size of the deck
	 * 
	 * @return size of the deck
	 */
	int size();
}
