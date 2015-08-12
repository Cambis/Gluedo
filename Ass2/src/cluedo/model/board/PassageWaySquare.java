package cluedo.model.board;

import cluedo.model.gameObjects.Room.RoomType;

/**
 * A PassageWaySqaure is a special type of door square that exists only in the corner rooms of the board.
 * They take one move to explore, taking the player to the room in the opposite corner.
 *
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class PassageWaySquare extends DoorSquare{

	private int m_x;
	private int m_y;
	
	// Rooms at start and end of passage
	private RoomType from;
	private RoomType to;

	public PassageWaySquare(int x, int y, RoomType from, RoomType to){
		super(x,y,from);
		m_x = x;
		m_y = y;
		this.from = from;
		this.to = to;
	}

	public RoomType getFrom() {
		return from;
	}

	public RoomType getTo() {
		return to;
	}

	@Override
	public int getX() {
		return m_x;
	}

	@Override
	public int getY() {
		return m_y;
	}

	public String toString(){
		return "t";
	}

}
