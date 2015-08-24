package cluedo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

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

	private JLabel[] cardImages;
	private JLabel[] diceImages;
	private JPanel cards;

	public CardPanel(int size) {

		// setLayout(new GridLayout(1, size + 3, 20, 20));
		setLayout(new GridLayout(1, 1));

//		JPanel info = new JPanel();
//		info.add(new JLabel("Your roll", JLabel.CENTER));
//		info.add(new JLabel("Your Cards"), JLabel.CENTER);
//		add(info);

		cardImages = new JLabel[size];
		diceImages = new JLabel[2];

		cards = new JPanel(new GridLayout(1, size + 3, 20, 1));

		// Set up dice
		for (int i = 0; i < 2; i++) {
			diceImages[i] = new JLabel();
			diceImages[i].setHorizontalAlignment(JLabel.CENTER);
			cards.add(diceImages[i]);
		}

		// Add an empty space
		cards.add(new JLabel());

		// Set up cards
		for (int i = 0; i < size; i++) {
			cardImages[i] = new JLabel();
			cardImages[i].setHorizontalAlignment(JLabel.CENTER);
			cards.add(cardImages[i]);
		}

		add(cards);

		//Sets up size and background colour of JPanel
		this.setPreferredSize(new Dimension(600,100));
		this.setBackground(Color.black);
	}

	public void setCards(BufferedImage[] dice, BufferedImage[] cards) {

		// Set up dice
		for (int i = 0; i < dice.length; i++)
			diceImages[i].setIcon(new ImageIcon(CluedoMainFrame.resizeImage(dice[i], 200, 200)));

		// Set up cards
		for (int i = 0; i < cards.length; i++)
			cardImages[i].setIcon(new ImageIcon(CluedoMainFrame.resizeImage(cards[i], 500, 300)));

	}

	public static void main(String args[]) {

		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		BufferedImage[] testCards = new BufferedImage[2];

//		for (int i = 0; i < 6; i++)
//			testCards[i] = new CharacterCard(new CluedoCharacter(Suspect.values()[i])).getImage();

		Card mrsScar = new CharacterCard(new CluedoCharacter(Suspect.MISS_SCARLET));
		Card colMust = new CharacterCard(new CluedoCharacter(Suspect.COLONEL_MUSTARD));

		testCards[0] = mrsScar.getImage();
		testCards[1] = colMust.getImage();

		Dice die1, die2;
		die1 = die2 = new Dice();
		die1.roll();
		die2.roll();

		BufferedImage[] dice = new BufferedImage[] {die1.getRollImage(), die2.getRollImage()
				};

		CardPanel panel = new CardPanel(2);
		panel.setCards(dice, testCards);

		frame.add(panel);
		frame.pack();
		frame.repaint();
		// panel.setCards(dice, testCards.toArray());
	}
}
