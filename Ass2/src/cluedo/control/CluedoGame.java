package cluedo.control;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;

import cluedo.model.Player;
import cluedo.model.board.Board;
import cluedo.model.board.DoorSquare;
import cluedo.model.board.InhabitableSquare;
import cluedo.model.board.Square;
import cluedo.model.cards.Card;
import cluedo.model.cards.CharacterCard;
import cluedo.model.cards.RoomCard;
import cluedo.model.cards.WeaponCard;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.Dice;
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

	/**
	 * The various states that the game can be in
	 *
	 * @author Cameron Bryers and Hannah Craighead
	 *
	 */
	public enum GameState {
		WELCOME, SETUP_PLAYERS, SETUP_INDIVIDUAL, GENERAL, SUGGESTION, ACCUSATION, GAME_WIN,
			GAME_LOST, GAME_OVER, START_TURN, NOTIFICATION;
	}

	private GameState state = GameState.WELCOME; // Default screen is welcome

	// Starting positions for the characters
	public static final Point MRS_WHITE_START = new Point(0, 9);
	public static final Point MR_GREEN_START = new Point(0, 14);
	public static final Point MRS_PEACOCK_START = new Point(6, 23);
	public static final Point COLONEL_MUSTARD_START = new Point(17, 0);
	public static final Point MISS_SCARLET_START = new Point(24, 7);
	public static final Point PROFESSOR_PLUM_START = new Point(19, 23);

	public final static boolean DEBUG = false;
	public final static int NUM_OF_DICE = 2;

	private int numOfPlayers;

	// Solution cards
	private Set<Card> envelope;

	// Cards that are in player's hands
	private List<Card> deck;

	// All players in the game
	private List<Player> players;
	
	// All players (may have been removed)
	private List<Player> allPlayers;

	// Current player index
	private int current;

	// Dice that the game uses
	private Dice dice1;
	private Dice dice2;

	// The board for this game
	private Board board;

	public CluedoGame() {
		state = GameState.WELCOME;
		board = new Board("cluedo.txt");
	}	

	public Board getBoard() {
		return board;
	}

	/**
	 * Sets the players in the game
	 */
	public void addPlayers(List<Player> players) {
		this.players = players;
		allPlayers = players;
	}

	/**
	 * Is responsible for setting up the game itself so the players can begin
	 * This includes creating a board and deck then selecting the cards for the
	 * envelope and dealing
	 */
	public void setUp() {
		board = new Board("cluedo.txt");
		board.addPlayers(players);
		createDeck();
		deal();
		dice1 = new Dice(); // Note: need to add in letting the player choose a
		// second dice
		dice2 = new Dice();
	}

	public Player getCurrentPlayer() {
		return players.get(current);
	}

	/**
	 * Gets the next player to have a turn
	 * @return the next player to play
	 */
	public final Player getNextPlayer() {
		int curIndex = players.indexOf(current);
		return players.get((curIndex + 1) % players.size());
	}

	public void nextPlayer() {
		if (current < players.size() - 1) {
			current++;
		} else { // full round has been completed
			current = 0;
		}
	}

	/**
	 * Rolls the dice for a player and then returns the images of the dice for
	 * drawing on the player panel
	 */

	public BufferedImage[] getDiceRoll() {
		BufferedImage[] dice = new BufferedImage[2];
		dice1.roll();
		dice[0] = dice1.getRollImage();
		dice2.roll();
		dice[1] = dice2.getRollImage();
		return dice;
	}

	/**
	 * Gets current roll
	 * @return roll value
	 */
	public int getRoll(){
		return dice1.getValue() + dice2.getValue();
	}

	/**
	 * Creates the game deck and envelope	 
	 * 
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

		envelope = new HashSet<Card>();

		// Generate random criminals
		envelope.add(suspects.remove(randomNumber(0, 5)));
		envelope.add(rooms.remove(randomNumber(0, 8)));
		envelope.add(weapons.remove(randomNumber(0, 5)));

		// Add remaining cards to the deck
		deck = new ArrayList<Card>();
		deck.addAll(suspects);
		deck.addAll(rooms);
		deck.addAll(weapons);

		Collections.shuffle(deck);
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

	public final int getNumOfPlayers() {
		return numOfPlayers;
	}

	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState g) {
		state = g;
	}

	/**
	 * Checks which players can refute a suggestion
	 * @param sugg is the suggestion
	 * @param room is the room it was made from
	 * @return a list of players who can refute the suggestion
	 */
	public List<String> suggestion(String[] sugg, String room){
		List<String> names = new ArrayList<String>();
		String suspect = sugg[0];
		String weapon = sugg[1];		
	
		for(Player p : allPlayers){
			for(Card c : p.getHand()){
				String card = c.getObject().getName();				
				if(card.equals(room) || card.equals(suspect) || card.equals(weapon)){
					names.add(p.getName());
				}
			}
		}
		return names;
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

	public List<Player> getPlayers() {
		return players;
	}

	public final List<Card> getDeck() {
		return deck;
	}

	public final Set<Card> getEnvelope() {
		return envelope;
	}

	/**
	 * Moves a player to a given square
	 * @param end is the given sqaure
	 */
	public void moveTo(Square end) {
		Player currentP = players.get(current);
		InhabitableSquare start = ((InhabitableSquare)board.squareAt(currentP.getX(), currentP.getY()));
		start.removePlayer(); // removes from current

		if(end instanceof DoorSquare){
			((DoorSquare)end).getRoom().addPlayer(currentP); // randomly allocates a square in the room
		}
		else{
			((InhabitableSquare)end).addPlayer(currentP); // adds to the square
			currentP.move(end.getX(), end.getY());
		}
	}

	/**
	 * Indicates whether an accusation is true or not
	 * @param answers is the accusation
	 * @return boolean indicating correctness
	 */
	public boolean accusation(String[] answers) {
		String suspect = answers[0];
		String weapon = answers[1];
		String room = answers[2];
		
		// Checks if the envelope contains all cards
		for(Card c : envelope){
			String card = c.getObject().toString();
			if(!card.equals(suspect) && !card.equals(weapon) && !card.equals(room)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Removes a player from the game
	 */
	public void removePlayer(){
		players.remove(current);
		if(current > 0){
			current--;
		}
		else{
			current = players.size()-1;
		}
	}

}
