package cluedo.model.cards;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import cluedo.model.Player;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.GameObject;

/**
 * Class that represents the character card in the game.
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class CharacterCard implements Card {

	// Character that the card is associated with.
	private CluedoCharacter m_character;

	public CharacterCard(CluedoCharacter character) {
		this.m_character = character;
	}

	@Override
	public GameObject getObject() {
		return m_character;
	}

	@Override
	public BufferedImage getImage() {

		// java.net.URL imageURL = getClass().getResource("/cluedo/assets/mrs_peacok_card.jpg");
		java.net.URL imageURL = null;

		switch(m_character.getSuspect()) {
		case COLONEL_MUSTARD:
			imageURL = getClass().getResource("/cluedo/assets/col_mustard_card.jpg");
			break;
		case MISS_SCARLET:
			imageURL = getClass().getResource("/cluedo/assets/miss_scarlet_card.jpg");
			break;
		case MRS_PEACOCK:
			imageURL = getClass().getResource("/cluedo/assets/mrs_peacok_card.jpg");
			break;
		case MRS_WHITE:
			imageURL = getClass().getResource("/cluedo/assets/mrs_white_card.jpg");
			break;
		case PROFESSOR_PLUM:
			imageURL = getClass().getResource("/cluedo/assets/pro_pum_card.jpg");
			break;
		case THE_REVEREND_GREEN:
			imageURL = getClass().getResource("/cluedo/assets/mr_green_card.jpg");
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

	public static void main(String args[]) throws IOException {
		BufferedImage image = new CharacterCard(new CluedoCharacter(Suspect.MRS_PEACOCK)).getImage();
		System.out.println(image.toString());
	}
}
