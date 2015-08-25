package cluedo.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cluedo.control.CluedoGame;
import cluedo.model.Game;
import cluedo.model.Player;
import cluedo.model.cards.Card;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;

public class GameTests {

	@Test
	public void testDeck() {
		List<Card> deck = Game.createDeck().get(0);
		List<Card> envelope = Game.createDeck().get(1);
		assertFalse(deck.containsAll(envelope));
	}

	@Test
	public void testDeck_2() {
		assertNotEquals(Game.createDeck().get(0), Game.createDeck().get(0));
	}

	@Test
	public void testCurrentPlayer() {
		Player player1 = new Player("David", Suspect.MISS_SCARLET, CluedoGame.MISS_SCARLET_START);
		Player player2 = new Player("Mark", Suspect.COLONEL_MUSTARD, CluedoGame.COLONEL_MUSTARD_START);

		CluedoGame game = new CluedoGame();
		List<Player> list = new ArrayList<Player>();
		list.add(player1);
		list.add(player2);

		game.addPlayers(list);
		assertEquals(game.getCurrentPlayer(), player1);
	}

	@Test
	public void testNextPlayer() {
		Player player1 = new Player("David", Suspect.MISS_SCARLET, CluedoGame.MISS_SCARLET_START);
		Player player2 = new Player("Mark", Suspect.COLONEL_MUSTARD, CluedoGame.COLONEL_MUSTARD_START);

		CluedoGame game = new CluedoGame();
		List<Player> list = new ArrayList<Player>();
		list.add(player1);
		list.add(player2);

		game.addPlayers(list);
		assertEquals(game.getNextPlayer(), player2);
	}
}
