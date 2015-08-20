package cluedo.model.cards;

import java.awt.image.BufferedImage;

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
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
