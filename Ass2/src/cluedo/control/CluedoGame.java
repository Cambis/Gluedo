package cluedo.control;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cluedo.model.Player;
import cluedo.model.cards.Card;
import cluedo.model.cards.CharacterCard;
import cluedo.model.cards.RoomCard;
import cluedo.model.cards.WeaponCard;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.Room;
import cluedo.model.gameObjects.Weapon;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Room.RoomType;
import cluedo.model.gameObjects.Weapon.WeaponType;

/**
 * Runs
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class CluedoGame {

	// Starting positions for the characters
	public static final Point MRS_WHITE_START = new Point(0, 9);
	public static final Point MR_GREEN_START = new Point(0, 14);
	public static final Point MRS_PEACOCK_START = new Point(6, 24);
	public static final Point COLONEL_MUSTARD_START = new Point(17, 0);
	public static final Point MISS_SCARLET_START = new Point(24, 7);
	public static final Point PROFESSOR_PLUM_START = new Point(19, 24);

	public final static boolean DEBUG = false;
	public final static int NUM_OF_DICE = 2;

	// Solution cards
	private Set<Card> enevelope;

	// Cards that are in player's hands
	private Set<Card> deck;

	// All players in the game
	private List<Player> players;
	
	public CluedoGame() {
		
		// TODO create players
		
		// Deal cards
		createDeck();
		deal();
		
	}

	/**
	 * Creates the game deck and envelope
	 *
	 * @return
	 */
	private void createDeck() {

		List<Card> suspects = new ArrayList<Card>();
		List<Card> rooms = new ArrayList<Card>();
		List<Card> weapons = new ArrayList<Card>();

		// Add suspects, rooms and weapons.
		for (Suspect s : Suspect.values())
			suspects.add(new CharacterCard(new CluedoCharacter(s)));

		for (int i = 0; i < RoomType.values().length - 1; i++)
			rooms.add(new RoomCard(new Room(RoomType.values()[i])));

		for (WeaponType w : WeaponType.values())
			weapons.add(new WeaponCard(new Weapon(w)));

		List<Card> envelope = new ArrayList<Card>();

		// Generate random criminals
		envelope.add(suspects.remove(randomNumber(0, 5)));
		envelope.add(rooms.remove(randomNumber(0, 8)));
		envelope.add(weapons.remove(randomNumber(0, 5)));

		// Add remaining cards to the deck
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(suspects);
		deck.addAll(rooms);
		deck.addAll(weapons);

		Collections.shuffle(deck);

		// List<List<Card>> cards = new ArrayList<List<Card>>();
		// cards.add(deck);
		// cards.add(envelope);
	}

	/**
	 * Deal cards to players
	 */
	private void deal() {

		// Number of cards in each player's hand
		int numOfCards = deck.size() / players.size();

		Iterator<Card> iter = deck.iterator();

		for (Player player : players) {

			// Create a hand
			Set<Card> hand = new HashSet<Card>();

			// Add cards to the hand
			for (int j = 0; j < numOfCards; j++)
				hand.add(iter.next());

			// Give player the cards
			player.setHand(hand);
		}
	}

	/**
	 * Generate a random number between a given min and max. Used to choose the
	 * murderer, murder weapon and crime scene. It is also used to get a
	 * player's roll
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
}
