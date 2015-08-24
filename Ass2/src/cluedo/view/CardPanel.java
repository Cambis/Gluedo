package cluedo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cluedo.control.CluedoGame;
import cluedo.model.cards.Card;
import cluedo.model.cards.CharacterCard;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Dice;

public class CardPanel extends JPanel {

	private JLabel[] cardImages;
	private JLabel[] diceImages;

	public CardPanel(int size) {
		
		setLayout(new GridLayout(1, size + 3, 20, 20));
		cardImages = new JLabel[size];
		diceImages = new JLabel[2];

		// Set up dice
		for (int i = 0; i < 2; i++) {
			diceImages[i] = new JLabel();
			diceImages[i].setHorizontalAlignment(JLabel.CENTER);
			add(diceImages[i]);
		}

		// Add an empty space
		add(new JLabel());
		
		// Set up cards
		for (int i = 0; i < size; i++) {
			cardImages[i] = new JLabel();
			cardImages[i].setHorizontalAlignment(JLabel.CENTER);
			add(cardImages[i]);
		}

		//Sets up size and background colour of JPanel
		this.setPreferredSize(new Dimension(600,50));
		this.setBackground(Color.black);
	}

	public void setCards(BufferedImage[] dice, BufferedImage[] cards) {

		// Set up dice
		for (int i = 0; i < dice.length; i++)
			diceImages[i].setIcon(new ImageIcon(CluedoMainFrame.resizeImage(dice[i], 200, 200)));

		// Set up cards
		for (int i = 0; i < cards.length; i++)
			cardImages[i].setIcon(new ImageIcon(CluedoMainFrame.resizeImage(cards[i], 200, 300)));
		
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
		
		CardPanel panel = new CardPanel(6);
		panel.setCards(dice, testCards);
		
		frame.add(panel);
		frame.pack();
		frame.repaint();
		// panel.setCards(dice, testCards.toArray());
	}
}
