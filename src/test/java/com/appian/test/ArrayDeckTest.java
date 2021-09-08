package com.appian.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.appian.test.deck.ArrayDeck;
import com.appian.test.deck.Card;
import com.appian.test.deck.IDeck;
import com.appian.test.deck.Suit;

/**
 * Unit test for ArrayDeck.
 * 
 * @author fmallado
 * @see ArrayDeck
 * @since 1.0.0.0
 */
class ArrayDeckTest {
	
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(ArrayDeckTest.class);

	/**
	 * Deck size
	 */
	private static final int DECK_SIZE = 52;

	/**
	 * Random property
	 */
	private static Random rand;

	/**
	 * Verify that the deck is always created with {@code DECK_SIZE} cards and that
	 * after removing several cards from the deck and shuffling it, the deck has
	 * {@code DECK_SIZE} cards again.
	 * 
	 * @throws NoSuchAlgorithmException if no algorithm is available for
	 *                                  SecureRandom object.
	 */
	@Test
	void testDeckSize() throws NoSuchAlgorithmException {
		logger.info("##### Start testDeckSize #####");
		logger.info("Create new deck");
		IDeck deck = new ArrayDeck();
		assertEquals(DECK_SIZE, deck.size(), "The deck does not have the correct number of cards.");
		int cardsDealt = getRamdonCardsDealt();
		logger.info("{} cards are dealt", cardsDealt);
		for (int i = 0; i < cardsDealt; i++) {
			logger.info(deck.dealOneCard());
		}
		assertEquals((DECK_SIZE - cardsDealt), deck.size(), "The deck does not have the correct number of cards.");
		logger.info("shuffle");
		deck.shuffle();
		assertEquals(DECK_SIZE, deck.size(), "The deck does not have the correct number of cards after shuffling.");
		int i = 0;
		while (!deck.empty()) {
			i++;
			logger.info("{} deak card is: {}", i, deck.dealOneCard());
		}
		logger.info("{} cards are dealt", i);
		assertEquals(DECK_SIZE, i, "Not all the cards in the deck have been dealt.");
		assertEquals(0, deck.size(), "The deck must be empty.");
		logger.info("##### End testDeckSize #####");
	}

	/**
	 * The deck is shuffled twice and after dealing all the cards the order is
	 * compared
	 * 
	 * @throws NoSuchAlgorithmException if no algorithm is available for
	 *                                  SecureRandom object.
	 */
	@Test
	void testShuffle() throws NoSuchAlgorithmException {
		logger.info("##### Start testShuffle #####");
		logger.info("Create new deck");
		IDeck deck = new ArrayDeck();
		logger.info("shuffle");
		deck.shuffle();
		assertEquals(DECK_SIZE, deck.size(), "The deck does not have the correct number of cards.");
		logger.info("The cards in the deck are stored in a list (deckTemp1).");
		List<Card> deckTemp1 = new ArrayList<Card>();
		while (!deck.empty()) {
			deckTemp1.add(deck.dealOneCard());
		}
		assertEquals(DECK_SIZE, deckTemp1.size(), "The deck does not have the correct number of cards.");
		logger.info("shuffle");
		deck.shuffle();
		assertEquals(DECK_SIZE, deck.size(), "The deck does not have the correct number of cards after shuffling.");
		logger.info("The cards in the deck are stored in a list (deckTemp2).");
		List<Card> deckTemp2 = new ArrayList<Card>();
		while (!deck.empty()) {
			deckTemp2.add(deck.dealOneCard());
		}
		assertEquals(DECK_SIZE, deckTemp1.size(), "The deck does not have the correct number of cards.");
		logger.info("both lists are compared to verify that the order of the cards dealt is different.");
		assertNotEquals(deckTemp1, deckTemp2,
				"The two times the cards have been dealt, they have been dealt in the same order.");
		logger.info("##### End testShuffle #####");
	}

	/**
	 * Verify the correct throwing of exceptions in card creation.
	 */
	@Test
	void testCreateInvalidCards() {
		logger.info("##### testCreateInvalidCards #####");

		logger.info("Trying to create a card without a suit");
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Card(Card.ACE, null),
				"The creation of a card without a suit is allowed.");
		assertTrue(thrown.getMessage().contains("Suit must be non-null"),
				"La excepcion lanzada no tiene el mensaje correcto.");

		logger.info("Trying to create a card with a lower rank than allowed");
		thrown = assertThrows(IllegalArgumentException.class, () -> new Card(0, Suit.DIAMONDS),
				"The creation of a card with an incorrect rank is allowed ");
		assertTrue(thrown.getMessage().contains("The rank must be between"),
				"The exception thrown does not have the correct message");

		logger.info("Trying to create a card with a higher rank than allowed ");
		thrown = assertThrows(IllegalArgumentException.class, () -> new Card(15, Suit.SPADES),
				"The creation of a card with an incorrect rank is allowed ");
		assertTrue(thrown.getMessage().contains("The rank must be between"),
				"The exception thrown does not have the correct message");

		logger.info("##### End testCreateInvalidCards #####");
	}

	/**
	 * Verify that after dealing the 52 cards in the deck, when trying to deal the
	 * 53 card, the appropriate exception is thrown.
	 * 
	 * @throws NoSuchAlgorithmException if no algorithm is available for
	 *                                  SecureRandom object.
	 */
	@Test
	void testDealWithEmptyDeck() throws NoSuchAlgorithmException {
		logger.info("##### Start testDealWithEmptyDeck #####");
		logger.info("Create new deck");
		IDeck deck = new ArrayDeck();
		logger.info("shuffle");
		deck.shuffle();
		assertEquals(DECK_SIZE, deck.size(), "The deck does not have the correct number of cards.");
		logger.info("The 52 cards of the deck are dealt.");
		for (int i = 0; i < DECK_SIZE; i++) {
			deck.dealOneCard();
		}
		assertEquals(0, deck.size(), "The deck does not have the correct number of cards.");
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> deck.dealOneCard(),
				"The deck should be empty.");
		assertTrue(thrown.getMessage().contains("The deck is empty"),
				"The exception thrown does not have the correct message");
		logger.info("##### End testDealWithEmptyDeck #####");
	}

	/**
	 * Verify that all the cards are in the deck 
	 * 
	 * @throws NoSuchAlgorithmException if no algorithm is available for
	 *                                  SecureRandom object.
	 */
	@Test
	void testAllCardsInDeck() throws NoSuchAlgorithmException {
		logger.info("##### Start testAllCardsInDeck #####");
		logger.info("Create new deck");
		IDeck deck = new ArrayDeck();
		logger.info("shuffle");
		deck.shuffle();
		assertEquals(DECK_SIZE, deck.size(), "The deck does not have the correct number of cards.");
		logger.info("The cards in the deck are stored in a list (deckTemp1).");
		List<Card> deckTemp1 = new ArrayList<Card>();
		while (!deck.empty()) {
			deckTemp1.add(deck.dealOneCard());
		}
		assertEquals(DECK_SIZE, deckTemp1.size(), "The deck does not have the correct number of cards.");
		logger.info("It is verified if the 52 cards are in the deck.");
		source().forEach(e -> {
			assertTrue(deckTemp1.contains(e), "Cards are missing from the deck ");
		});
		logger.info("##### End testAllCardsInDeck #####");
	}

	/**
	 * Verify that there are no repeat cards. 
	 * @throws NoSuchAlgorithmException if no algorithm is available for
	 *                                  SecureRandom object.
	 */
	@Test
	void testRepeatCards() throws NoSuchAlgorithmException {
		logger.info("##### Start testRepeatCards #####");
		logger.info("Create new deck");
		IDeck deck = new ArrayDeck();
		logger.info("shuffle");
		deck.shuffle();
		assertEquals(DECK_SIZE, deck.size(), "The deck does not have the correct number of cards.");
		int cardsDealt = getRamdonCardsDealt();
		logger.info("{} cards are dealt", cardsDealt);
		List<Card> deckTemp1 = new ArrayList<Card>();
		for (int i = 0; i < cardsDealt; i++) {
			deckTemp1.add(deck.dealOneCard());
		}
		assertEquals((DECK_SIZE - cardsDealt), deck.size(), "The deck does not have the correct number of cards.");
		
		List<Card> deckTemp2 = new ArrayList<Card>();
		while (!deck.empty()) {
			deckTemp2.add(deck.dealOneCard());
		}
		
		source().forEach(e -> {
			assertNotEquals(deckTemp1.contains(e), deckTemp2.contains(e), "The card must be only in a list ");
		});
		logger.info("##### End testRepeatCards #####");
	}

	/**
	 * test Card constructor.
	 * @param card Card to create.
	 */
	@ParameterizedTest
    @MethodSource("source")
	void testCreateCard(Card card) {
		logger.info("##### Start testCreateCard #####");
		logger.info("Trying to create the card: {}", card);
		assertNotNull(new Card(card.rank(), card.suit()), "Failed to create card.");
		logger.info("##### End testCreateCard #####");
	}

	/**
	 * Returns all the values of the cards in a deck 
	 * @return all the values of the cards in a deck 
	 */
	static Stream<Card> source() {
		return Stream.of(new Card(Card.ACE, Suit.SPADES), new Card(2, Suit.SPADES), new Card(3, Suit.SPADES),
				new Card(4, Suit.SPADES), new Card(5, Suit.SPADES), new Card(6, Suit.SPADES), new Card(7, Suit.SPADES),
				new Card(8, Suit.SPADES), new Card(9, Suit.SPADES), new Card(10, Suit.SPADES),
				new Card(Card.JACK, Suit.SPADES), new Card(Card.QUEEN, Suit.SPADES), new Card(Card.KING, Suit.SPADES),
				new Card(Card.ACE, Suit.CLUBS), new Card(2, Suit.CLUBS), new Card(3, Suit.CLUBS),
				new Card(4, Suit.CLUBS), new Card(5, Suit.CLUBS), new Card(6, Suit.CLUBS), new Card(7, Suit.CLUBS),
				new Card(8, Suit.CLUBS), new Card(9, Suit.CLUBS), new Card(10, Suit.CLUBS),
				new Card(Card.JACK, Suit.CLUBS), new Card(Card.QUEEN, Suit.CLUBS), new Card(Card.KING, Suit.CLUBS),
				new Card(Card.ACE, Suit.DIAMONDS), new Card(2, Suit.DIAMONDS), new Card(3, Suit.DIAMONDS),
				new Card(4, Suit.DIAMONDS), new Card(5, Suit.DIAMONDS), new Card(6, Suit.DIAMONDS),
				new Card(7, Suit.DIAMONDS), new Card(8, Suit.DIAMONDS), new Card(9, Suit.DIAMONDS),
				new Card(10, Suit.DIAMONDS), new Card(Card.JACK, Suit.DIAMONDS), new Card(Card.QUEEN, Suit.DIAMONDS),
				new Card(Card.KING, Suit.DIAMONDS), new Card(Card.ACE, Suit.HEARTS), new Card(2, Suit.HEARTS),
				new Card(3, Suit.HEARTS), new Card(4, Suit.HEARTS), new Card(5, Suit.HEARTS), new Card(6, Suit.HEARTS),
				new Card(7, Suit.HEARTS), new Card(8, Suit.HEARTS), new Card(9, Suit.HEARTS), new Card(10, Suit.HEARTS),
				new Card(Card.JACK, Suit.HEARTS), new Card(Card.QUEEN, Suit.HEARTS), new Card(Card.KING, Suit.HEARTS));
	}

	/**
	 * Returns a random number of cards to be dealt 
	 * @return random number of cards to be dealt 
	 * @throws NoSuchAlgorithmException
	 */
	static int getRamdonCardsDealt() throws NoSuchAlgorithmException {
		if (rand == null) {
			rand = SecureRandom.getInstanceStrong();
		}
		return rand.nextInt(DECK_SIZE);
	}
}
