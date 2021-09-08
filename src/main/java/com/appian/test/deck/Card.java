package com.appian.test.deck;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a card from a deck of poker-style playing cards (Fifty-two playing
 * cards in four suits: hearts, spades, clubs, diamonds, with face values of
 * Ace, 2-10, Jack, Queen, and King.)
 * 
 * @author fmallado
 * @since 1.0.0.0
 */
public record Card(int rank, Suit suit) implements Serializable {

	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(Card.class);

	/*
	 * Codes for the non-numeric cards. Cards 2 through 10 have their numerical
	 * values for their codes
	 */

	/**
	 * Code for the ACE cards
	 */
	public static final int ACE = 1;

	/**
	 * Code for the JACK cards
	 */
	public static final int JACK = 11;

	/**
	 * Code for the QUEEN cards
	 */
	public static final int QUEEN = 12;

	/**
	 * Code for the KING cards
	 */
	public static final int KING = 13;

	/**
	 * Minimum rank for the value of the cards
	 */
	protected static final int MIN_RANK = 1;

	/**
	 * Maximum rank for the value of the cards
	 */
	protected static final int MAX_RANK = 13;

	/**
	 * Construct a Card with a given rank and suit.
	 * 
	 * @param rank rank of the poker-style playing cards (values of Ace, 2-10, Jack,
	 *             Queen, and King
	 * @param suit suit of the poker-style playing cards (hearts, spades, clubs,
	 *             diamonds).
	 * @throws IllegalArgumentException if suit is null or if the ranking is not
	 *                                  between {@code MIN_RANK} and
	 *                                  {@code MAX_RANK} inclusive
	 */
	public Card {
		logger.trace("Start of the card constructor. rank:{}, Suit:{}", rank, suit);
		// It is validated if the suit of the card is not empty
		if (suit == null) {
			throw new IllegalArgumentException("Suit must be non-null");
		}

		// It is valid if the card number is in the range
		if (rank < MIN_RANK || rank > MAX_RANK) {
			throw new IllegalArgumentException(String.format(
					"The value %d is invalid. The rank must be between %d and %d inclusive", rank, MIN_RANK, MAX_RANK));
		}
		logger.trace("End of the card constructor.");
	}

	@Override
	public String toString() {
		String rankString = null;
		String suitString = null;
		String colorString = null;

		// The card suit icon and color are assigned
		switch (suit) {
		case SPADES:
			suitString = "\u2660";
			colorString = "Black";
			break;
		case HEARTS:
			suitString = "\u2665";
			colorString = "Red";
			break;
		case DIAMONDS:
			suitString = "\u2666";
			colorString = "Red";
			break;
		case CLUBS:
			suitString = "\u2663";
			colorString = "Black";
			break;
		}

		// The card rank name are assigned
		switch (rank) {
		case ACE:
			rankString = "ACE";
			break;
		case JACK:
			rankString = "JACK";
			break;
		case QUEEN:
			rankString = "QUEEN";
			break;
		case KING:
			rankString = "KING";
			break;
		default:
			rankString = String.valueOf(rank);
		}

		// The output is formatted
		return String.format("[%s %s] - %s", rankString, suitString, colorString);
	}
}
