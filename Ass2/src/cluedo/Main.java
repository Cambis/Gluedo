package cluedo;

import cluedo.control.CluedoGame;
import cluedo.model.Game;
import cluedo.view.GUI;

/**
 * Main class, runs the entire game
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class Main {

	public static void main(String args[]) {
		CluedoGame cluedo = new CluedoGame();
		GUI main = new GUI(cluedo);
	}
}
