package cluedo.model.gameObjects;

import java.util.Random;

/**
 * Represents the dice in the game
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class Dice {

	private int value;

	public void roll() {
		value = new Random().nextInt(13);
	}

	public final int getValue() {
		return value;
	}
}
