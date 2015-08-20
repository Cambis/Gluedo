package cluedo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardPanel extends JPanel{

	private JLabel[] cardImages;
	private JLabel[] diceImages;

	public CardPanel(int size) {
		setLayout(new FlowLayout());
		cardImages = new JLabel[size];
		diceImages = new JLabel[2];

		// Set up dice
		for (int i = 0; i < 2; i++) {
			diceImages[i] = new JLabel();
			add(diceImages[i]);
		}

		// Set up cards
		for (int i = 0; i < size; i++) {
			cardImages[i] = new JLabel();
			add(cardImages[i]);
		}
		
		//Sets up size and background colour of JPanel
		this.setPreferredSize(new Dimension(600,200));
		this.setBackground(Color.black);
	}

	public void setCards(ImageIcon[] dice, ImageIcon[] cards) {

		// Set up dice
		for (int i = 0; i < dice.length; i++)
			diceImages[i].setIcon(dice[i]);

		// Set up cards
		for (int i = 0; i < cards.length; i++)
			cardImages[i].setIcon(cards[i]);
	}
}
