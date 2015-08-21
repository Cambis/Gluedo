package cluedo.model.cards;

import javax.swing.ImageIcon;

import cluedo.model.Player;
import cluedo.model.gameObjects.GameObject;
import cluedo.model.gameObjects.Weapon;

/**
 * Class that represents a weapon card in the game.
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class WeaponCard implements Card {

	// Weapon that this card is associated with
	private Weapon m_weapon;

	public WeaponCard(Weapon weapon) {
		this.m_weapon = weapon;
	}

	@Override
	public GameObject getObject() {
		return m_weapon;
	}

	@Override
	public ImageIcon getImageIcon() {

		java.net.URL imageURL = null;

		switch (m_weapon.getWeaponType()) {
		case CANDLESTICK:
			imageURL = getClass().getResource("/cluedo/assets/candlestick_card.jpg");
			break;
		case DAGGER:
			imageURL = getClass().getResource("/cluedo/assets/dagger_card.jpg");
			break;
		case LEAD_PIPE:
			imageURL = getClass().getResource("/cluedo/assets/lead_pipe_card.jpg");
			break;
		case REVOLVER:
			imageURL = getClass().getResource("/cluedo/assets/revolver_card.jpg");
			break;
		case ROPE:
			imageURL = getClass().getResource("/cluedo/assets/rope_card.jpg");
			break;
		case SPANNER:
			imageURL = getClass().getResource("/cluedo/assets/spanner_card.jpg");
			break;
		default:
			break;
		}

		ImageIcon image = null;

		if (imageURL != null) {
			// System.out.println("In here");
			image = new ImageIcon(imageURL);
		}

		return image;
	}
}
