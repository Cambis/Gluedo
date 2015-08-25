package cluedo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cluedo.model.cards.Card;
import cluedo.model.cards.CharacterCard;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Dice;

public class CardPanel extends JPanel {

	// Stores all players hands
	private Map<String, BufferedImage[]> cardMap;
	
	// Stores currently used images
	private JLabel[] cardImages;
	private JLabel[] diceImages;
	
	// Panel for displaying images
	private JPanel cards;
	
	// Number of cards a player has
	private int size;

	public CardPanel(int size) {
		this.size = size;
		cardMap = new HashMap<String, BufferedImage[]>();
		cardImages = new JLabel[size];
		diceImages = new JLabel[2];
		init();
	}

	/**
	 * Initialises Card Panel
	 */

	private void init() {		
		setLayout(new GridLayout(1, 1));

		cards = new JPanel(new GridLayout(1, size + 3, 20, 20));
		cards.setBackground(Color.BLACK);

		// Set up dice
		for (int i = 0; i < 2; i++) {
			diceImages[i] = new JLabel();
			diceImages[i].setHorizontalAlignment(JLabel.CENTER);
			diceImages[i].setBackground(Color.BLACK);
			cards.add(diceImages[i]);
		}

		// Add an empty space
		cards.add(new JLabel());

		// Set up cards
		for (int i = 0; i < size; i++) {
			cardImages[i] = new JLabel();
			cardImages[i].setHorizontalAlignment(JLabel.CENTER);
			cardImages[i].setBackground(Color.BLACK);
			cards.add(cardImages[i]);
		}

		add(cards);

		// Sets up size and background colour of JPanel
		this.setPreferredSize(new Dimension(600, 100));
		this.setBackground(Color.black);
		this.setVisible(false);
	}
	
	/**
	 * Used to associate a hand of cards with a given player
	 * 
	 * @param name is the name of a player
	 * @param playerCards is the hand of cards belonging to them
	 */
	
	public void addCards(String name, Set<Card> playerCards) {

		BufferedImage[] imagesCard = new BufferedImage[playerCards.size()];

		int count = 0;
		for (Card c : playerCards)
			imagesCard[count++] = c.getImage();
		cardMap.put(name, imagesCard);
		setUpCards(name);
	}
	
	/**
	 * Allocates the hand for a player
	 * @param name is the name of the player's cards to display
	 */
	private void setUpCards(String name) {
		// Set up cards
		for (int i = 0; i < cardMap.get(name).length; i++)
			cardMap.get(name)[i] = CluedoMainFrame.resizeImage(cardMap.get(name)[i], 0.1, 0.1);
	}
	
	/**
	 * Sets the card panel to reflect the current player
	 * 
	 * @param name is the name of the current player
	 * @param dice is the images correlating to their roll
	 */

	public void setPlayer(String name, BufferedImage[] dice) {
		setDice(dice);
		setCards(name);
	}
	
	/**
	 * Displays dice for a player
	 * @param dice is the images of a roll
	 */

	private void setDice(BufferedImage[] dice) {
		// Set up dice
		for (int i = 0; i < dice.length; i++)
			diceImages[i].setIcon(new ImageIcon(CluedoMainFrame.resizeImage(dice[i], 0.2, 0.2)));

	}	

	/**
	 * Displays the cards of a player
	 * @param name is the name of the player 
	 */
	private void setCards(String name) {
		for (int i = 0; i < cardMap.get(name).length; i++)
			cardImages[i].setIcon(new ImageIcon(cardMap.get(name)[i]));
	}
	
	/**
	 * Hide cards from view
	 */
	
	public void hideCards() {
		for (JLabel card : cardImages)
			card.setVisible(false);
	}

	/**
	 * Show cards
	 */
	
	public void toggleCards() {		
		for (JLabel card : cardImages)
			card.setVisible(!card.isVisible());
		
		cards.repaint();
	}

	public static void main(String args[]) {

		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		BufferedImage[] testCards = new BufferedImage[2];		

		Card mrsScar = new CharacterCard(new CluedoCharacter(Suspect.MISS_SCARLET));
		Card colMust = new CharacterCard(new CluedoCharacter(Suspect.COLONEL_MUSTARD));

		testCards[0] = mrsScar.getImage();
		testCards[1] = colMust.getImage();

		Dice die1, die2;
		die1 = die2 = new Dice();
		die1.roll();
		die2.roll();		

		CardPanel panel = new CardPanel(2);	

		frame.add(panel);
		frame.pack();
		frame.repaint();
		
	}
}
