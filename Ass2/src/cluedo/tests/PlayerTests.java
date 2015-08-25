package cluedo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cluedo.model.Game;
import cluedo.model.Player;
import cluedo.model.board.Board;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Room;
import cluedo.model.gameObjects.Room.RoomType;

public class PlayerTests {

	@Test
	public void testPlayerName() {
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);
		assertEquals(player.getName(), "Jim");
	}

	

	@Test
	public void testNewPosition() {
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);

		int x = 13, y = 10;

		player.move(x, y);
		assertTrue(player.getX() == x && player.getY() == y);
	}
	

	@Test
	public void testPassageWayInvalid(){
		Board board = new Board("cluedo.txt");
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);
		RoomType.LOUNGE.addPlayer(player);
		assertFalse(board.isValid(player, 4, 1, 1));
	}
}
