package cluedo.model.cards;

import javax.swing.ImageIcon;

import cluedo.model.Player;
import cluedo.model.gameObjects.GameObject;
import cluedo.model.gameObjects.Room;

/**
 * Class that represents a room card in the game.
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class RoomCard implements Card {

	// Room that the card is associated with
	private Room m_room;

	public RoomCard(Room room) {
		this.m_room = room;
	}

	@Override
	public GameObject getObject() {
		return m_room;
	}

	@Override
	public ImageIcon getImageIcon() {

		java.net.URL imageURL = null;

		switch(m_room.getRoomType()) {
		case BALL_ROOM:
			imageURL = getClass().getResource("/cluedo/assets/ballroom_card.jpg");
			break;
		case BILLIARD_ROOM:
			imageURL = getClass().getResource("/cluedo/assets/billiard_room_card.jpg");
			break;
		case CONSERVATORY:
			imageURL = getClass().getResource("/cluedo/assets/conservatory_card.jpg");
			break;
		case DINING_ROOM:
			imageURL = getClass().getResource("/cluedo/assets/dining_room_card.jpg");
			break;
		case HALL:
			imageURL = getClass().getResource("/cluedo/assets/hall_card.jpg");
			break;
		case KITCHEN:
			imageURL = getClass().getResource("/cluedo/assets/kitchen_card.jpg");
			break;
		case LIBRARY:
			imageURL = getClass().getResource("/cluedo/assets/library_card.jpg");
			break;
		case LOUNGE:
			imageURL = getClass().getResource("/cluedo/assets/lounge_card.jpg");
			break;
		case STUDY:
			imageURL = getClass().getResource("/cluedo/assets/study_card.jpg");
			break;
		case SWIMMING_POOL:
			break;
		default:
			break;
		}

		ImageIcon image = null;

		if (imageURL != null) {
			image = new ImageIcon(imageURL);
		}

		return image;
	}
}
