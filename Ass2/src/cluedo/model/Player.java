package cluedo.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import cluedo.control.CluedoGame;
import cluedo.model.cards.Card;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;

/**
 * Class that represents the player
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class Player {

	// Cards in the players hand
	private Set<Card> hand;

	// Player's checklist
	// private CheckList checkList;

	// Player's character token
	private final Suspect m_character;

	// Player's gamertag
	private final String m_name;

	// Player's position on the board
	private int m_x, m_y;

	public Player(Set<Card> hand, String name, Suspect character, int start_x, int start_y) {
		this.hand = hand;
		this.m_name = name;
		this.m_character = character;
		this.m_x = start_x;
		this.m_y = start_y;
	}

	public Player(String name, Suspect character, Point start) {
		this.m_name = name;
		this.m_character = character;
		this.m_x = start.x;
		this.m_y = start.y;
	}

	public Player(String name, Suspect character) {
		this.m_name = name;
		this.m_character = character;

		switch (character) {
		case COLONEL_MUSTARD:
			this.m_x = CluedoGame.COLONEL_MUSTARD_START.x;
			this.m_y = CluedoGame.COLONEL_MUSTARD_START.y;
			break;
		case MISS_SCARLET:
			this.m_x = CluedoGame.MISS_SCARLET_START.x;
			this.m_y = CluedoGame.MISS_SCARLET_START.y;
			break;
		case MRS_PEACOCK:
			this.m_x = CluedoGame.MRS_PEACOCK_START.x;
			this.m_y = CluedoGame.MRS_PEACOCK_START.y;
			break;
		case MRS_WHITE:
			this.m_x = CluedoGame.MRS_WHITE_START.x;
			this.m_y = CluedoGame.MRS_WHITE_START.y;
			break;
		case PROFESSOR_PLUM:
			this.m_x = CluedoGame.PROFESSOR_PLUM_START.x;
			this.m_y = CluedoGame.PROFESSOR_PLUM_START.y;
			break;
		case THE_REVEREND_GREEN:
			this.m_x = CluedoGame.MR_GREEN_START.x;
			this.m_y = CluedoGame.MR_GREEN_START.y;
			break;
		default:
			break;

		}
	}

	/**
	 * Moves a player's coordinates to the x and y coordinates given
	 * 
	 * @param x
	 *            is the x coordinate
	 * @param y
	 *            is the y coordinate
	 */
	public void move(int x, int y) {
		m_x = x;
		m_y = y;
	}

	/**
	 * setHand givens a collection of cards that belong to the player
	 * 
	 * @param hand
	 *            is a set of the player's cards
	 */

	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

	public int getX() {
		return m_x;
	}

	public int getY() {
		return m_y;
	}

	public String getName() {
		return m_name;
	}

	public final Suspect getCharacter() {
		return m_character;
	}

	/**
	 * Gets the players hand of cards
	 * 
	 * @return the player's hand
	 */

	public final Set<Card> getHand() {
		return hand;
	}

	public BufferedImage getImage() {

		java.net.URL imageURL = null;

		switch (m_character) {
		case COLONEL_MUSTARD:
			imageURL = getClass().getResource("/cluedo/assets/col_mustard_token.png");
			break;
		case MISS_SCARLET:
			imageURL = getClass().getResource("/cluedo/assets/miss_scarlet_token.png");
			break;
		case MRS_PEACOCK:
			imageURL = getClass().getResource("/cluedo/assets/mrs_peacock_token.png");
			break;
		case MRS_WHITE:
			imageURL = getClass().getResource("/cluedo/assets/mrs_white_token.png");
			break;
		case PROFESSOR_PLUM:
			imageURL = getClass().getResource("/cluedo/assets/pro_plum_token.png");
			break;
		case THE_REVEREND_GREEN:
			imageURL = getClass().getResource("/cluedo/assets/mr_green_token.png");
			break;
		default:
			throw new RuntimeException();
		}

		BufferedImage image = null;

		try {
			image = ImageIO.read(imageURL);
		} catch (IOException e) {

		}

		return image;
	}

	public void draw(Graphics g) {
		switch (m_character) {
		case COLONEL_MUSTARD:
			g.setColor(Color.yellow);
			break;
		case MISS_SCARLET:
			g.setColor(Color.red);
			break;
		case MRS_PEACOCK:
			g.setColor(Color.blue);
			break;
		case MRS_WHITE:
			g.setColor(Color.WHITE);
			break;
		case PROFESSOR_PLUM:
			g.setColor(Color.MAGENTA);
			break;
		case THE_REVEREND_GREEN:
			g.setColor(Color.green);
			break;
		default:
			break;

		}
		g.fillOval(m_y * 21, m_x * 21, 21, 21);
	}

}
