package cluedo.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.GameState;
import cluedo.model.Game;
import cluedo.model.Player;
import cluedo.model.board.Board;
import cluedo.model.board.CorridorSquare;
import cluedo.model.board.DoorSquare;
import cluedo.model.board.InhabitableSquare;
import cluedo.model.board.RoomSquare;
import cluedo.model.board.Square;
import cluedo.model.cards.Card;
import cluedo.model.cards.CharacterCard;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Room;

/**
 * Main user interface class
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */

public class GUI implements KeyListener, MouseListener, ActionListener {

	// Main frame that holds the boards, actions etc.
	private CluedoMainFrame frame;

	// Game that it helps to display
	private CluedoGame game;	

	public GUI(CluedoGame game) {
		this.game = game;
		frame = new CluedoMainFrame(this);
		frame.addKeyListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		// Player is clicking to move
		if(game.getState() == GameState.GENERAL){
			int y = arg0.getX()/frame.getCanvasSquareWidth();
			int x =  arg0.getY()/frame.getCanvasSquareWidth();

			// Stops potential out of bounds clicking
			if(x >= 25 || y >= 25){
				return;
			}			
			
			Square s = game.getBoard().squareAt(x, y);	// gets the square related to this click

			if(frame.isHighlighted(s)){ // if it is a valid move
				game.moveTo(s); // moves the player

				//update state
				if(s instanceof CorridorSquare){
					nextTurn();
				}

				else if(s instanceof RoomSquare || s instanceof DoorSquare){
					//Re-gets the square in case a player clicked on a door square and was randomly allocated a place in the room
					s = game.getBoard().squareAt(game.getCurrentPlayer().getX(), game.getCurrentPlayer().getY());
					// In centre room so making an accusation
					if(((RoomSquare)s).getRoom().equals(Room.RoomType.SWIMMING_POOL)){
						frame.updateCanvas(GameState.ACCUSATION);
						game.setState(GameState.ACCUSATION);
						frame.accusationBox(this);
					}
					// In an outer room so making a suggestion
					else{
						frame.updateCanvas(GameState.SUGGESTION);
						game.setState(GameState.SUGGESTION);
						frame.suggestionBox(this);
					}
				}

				frame.repaint();
			}
			// otherwise do nothing if the click is not on a valid square
		}
	}

	/**
	 * This code was intended for keyboard short cuts.
	 * It currently does not do anything
	 */
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// Checklist shortcut
		System.out.println(arg0.getKeyChar());
		if(arg0.isControlDown() && arg0.getKeyChar() == 'C'){
			if(game.getState() != GameState.WELCOME && game.getState() != GameState.SETUP_INDIVIDUAL
					&& game.getState() != GameState.START_TURN){
				frame.seeChecklist();
			}
		}
		
		// New game shortcut
		if(arg0.isControlDown() && arg0.getKeyChar() == 'N'){
			if(game.getState() != GameState.WELCOME && game.getState() != GameState.SETUP_INDIVIDUAL){
				newGamePopUp("NEW GAME");
				frame.repaint();
			}
		}

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// A new game has been selected on the menu

		if(e.getActionCommand().equals("New Game")){
			newGamePopUp("NEW GAME");
		}

		// In the welcome state:
		// The start new game button has been pressed

		if (game.getState() == GameState.WELCOME) {
			game.setState(GameState.SETUP_INDIVIDUAL); // changes to set up state
			frame.updateCanvas(GameState.SETUP_INDIVIDUAL); // lets the frame know of state change			
			frame.createPlayerSelector(this); // pops up set up box
			frame.repaint();
		}

		else if (game.getState() == GameState.SETUP_INDIVIDUAL) { // Finish setup button pressed
			PlayerInitBox playerFrame = frame.getSetup();		

			// Get players that the users have created
			Map<String, String> players = playerFrame.getPlayers();

			// Create the players
			List<Player> newPlayers = new ArrayList<Player>();

			for (String playerName : players.keySet()) {
				Suspect suspect = null;
				for (Suspect s : Suspect.values())
					if (s.toString().equals(players.get(playerName))) {
						suspect = s;
						break;
					}

				newPlayers.add(new Player(playerName, suspect));
			}

			// Sort out the players according to the suspects they have chosen,
			// this is done by comparing the suspects' ordinal position in the
			// Suspect enum.
			newPlayers.sort(new Comparator<Player>() {

				@Override
				public int compare(Player o1, Player o2) {
					if (o1.getCharacter().getValue() < o2.getCharacter().getValue())
						return 1;
					else if (o1.getCharacter().getValue() > o2.getCharacter().getValue())
						return -1;
					return 0;
				}

			});

			if (players.size() >= 3) { // if there are enough players for a game				
				game.setNumOfPlayers(newPlayers.size());				

				// Add players to the game
				game.addPlayers(newPlayers);
				game.setUp();

				game.setState(GameState.START_TURN); // changes state to first players roll
				frame.updateCanvas(GameState.START_TURN); // lets the frame know  of state change

				// Create card panels
				frame.createCardPanel((int) Math.ceil(18 / newPlayers.size()));				
				for (Player p : newPlayers)
					frame.addPlayerToCardPanel(p.getName(), p.getHand());				

				// Create checklists
				frame.createCheckLists(players.size(), this);				

				// Creates turn box
				frame.startTurnBox(this, game.getCurrentPlayer().getName());
				
				// Updates the game to start with the first player
				BufferedImage character = new CharacterCard(new CluedoCharacter(
						game.getCurrentPlayer().getCharacter())).getImage();
				frame.setNextPlayer(character,game.getCurrentPlayer().getName());
				frame.setNextPlayer(character,game.getCurrentPlayer().getName());
				
				// Draws the board
				frame.drawPlayers(newPlayers);
				frame.repaint();
			}
		}
		
		// Some has clicked to start their turn
		else if (game.getState() == GameState.START_TURN) {
			frame.repaint();
			
			// turn off dialog
			frame.getTurnBox().setVisible(false);

			runTurn(); // rolls the dice
			game.setState(GameState.GENERAL); // changes state to first players roll
			frame.updateCanvas(GameState.GENERAL); // lets the frame know of state change			
			findMoves(); // finds moves the player can take			
			frame.repaint();
		}
		
		// Player has clicked to view/hide checklist
		else if(e.getActionCommand().equals("Show Checklist")){
			if(game.getState() != GameState.START_TURN){
				frame.seeChecklist();
				frame.repaint();
			}
		}

		// Player has clicked to view/hide cards
		else if(e.getActionCommand().equals("Show Cards")){		
			if(game.getState() != GameState.START_TURN){
				frame.showCards();
				frame.repaint();
			}
		}

		// Player has finished making a suggestion
		else if(game.getState() == GameState.SUGGESTION){			
			frame.turnSuggOff(e); // gets rid of suggestion box
			frame.repaint();
			
			// Extracts suggestion
			String[] answers = frame.getSuggestion();
			String room = ((RoomSquare)game.getBoard().squareAt
					(game.getCurrentPlayer().getX(), game.getCurrentPlayer().getY()))
					.getRoom().toString();
		
			// Information box pop up with players who can refute the suggestion
			new RefutionPopUp(game.suggestion(answers, room));
			frame.repaint();

			nextTurn(); // prepares for next turn
			frame.repaint();
		}

		// Player has finished making an accusation
		else if(game.getState() == GameState.ACCUSATION){
			// turns accusation box off
			frame.turnAccOff(e);
			
			//Extracts answers and determines if correct
			String[] answers = frame.getAccusation();
			boolean correct = game.accusation(answers);
			new AccusationPopUp(correct);
			
			// correct accusation
			if(correct){
				frame.updateCanvas(GameState.GAME_WIN);
				game.setState(GameState.GAME_WIN);
				newGamePopUp("CONGRATULATIONS YOU HAVE WON");
			}
			// incorrect accusation
			else if (game.getNumOfPlayers() > 1){
				game.removePlayer();
				nextTurn(); // game continues
			}
			else{ // incorrect accusation and no remaining players
				frame.updateCanvas(GameState.GAME_OVER);
				game.setState(GameState.GAME_OVER);
				newGamePopUp("GAME OVER");
			}
			frame.repaint();
		}
		frame.repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {	}

	@Override
	public void mouseExited(MouseEvent arg0) {	}

	@Override
	public void mousePressed(MouseEvent arg0) {	}

	@Override
	public void mouseReleased(MouseEvent arg0) { }

	@Override
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) {	}

	/*
	 * HELPER METHODS
	 */
	
	/**
	 * Finds the moves a player can make
	 */

	private void findMoves() {
		Board b = game.getBoard(); // gets board to find possible move
		Player current = game.getCurrentPlayer(); // gets current player
		Square start = b.squareAt(current.getX(), current.getY()); // gets current player's location
		int roll = game.getRoll(); // gets their roll value
	
		Set<Square> lands = game.getBoard().djikstra(start, roll);
		Set<String> rooms = findRooms(lands);

		// Draws valid moves on board panel
		frame.drawValidMoves(lands, rooms);
	}
	
	/**
	 * Finds the rooms a player can reach
	 * @param lands are all the squares a player can reach
	 * @return a set of strings correlating to rooms a player can reach
	 */

	private Set<String> findRooms(Set<Square> lands) {
		Set<String> room = new HashSet<String>();
		Set<Square> toRemove = new HashSet<Square>();
		
		for (Square s : lands) {
			if (s instanceof DoorSquare) { // adds a room if the square is a door
				room.add(((DoorSquare) s).getRoom().toString());
				toRemove.add(s);
			}
		}
		
		lands.removeAll(toRemove); // removes all door squares from landing squares
		return room;
	}

	/**
	 * Runs preparations for a players turn
	 */
	public void runTurn() {
		Player p = game.getCurrentPlayer();
		BufferedImage[] dice = game.getDiceRoll();		
		frame.setCardPanel(p.getName(), dice);
		frame.showCards();		
	}
	
	/**
	 * Prepares for the next players turn
	 */

	public void nextTurn() {
		frame.hideCards(); // hides previous players cards
		frame.turnOffChecklist(); // turns off previous players checklist
		game.nextPlayer(); // moves to next players turn
		frame.updateCanvas(GameState.START_TURN); // updates game state
		game.setState(GameState.START_TURN);
		frame.getTurnBox().changePlayer(game.getCurrentPlayer().getName()); // updates turn box		
		frame.getTurnBox().setVisible(true);
		
		BufferedImage character = new CharacterCard(new CluedoCharacter(
				game.getCurrentPlayer().getCharacter())).getImage();
		frame.setNextPlayer(character,game.getCurrentPlayer().getName());
	}
	
	/**
	 * Creates a new game
	 */

	public void newGame(){
		game = new CluedoGame();
		frame.setVisible(false);
		frame = new CluedoMainFrame(this);
		frame.repaint();
	}

	/**
	 * Creates a pop up asking if a player wants to create a new game
	 * @param title
	 */
	public void newGamePopUp(String title){
		int reply = JOptionPane.showConfirmDialog(null, "Would you like to start a new game?", title, JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			newGame();
		}
		else {        	
			if(game.getState() == GameState.GAME_OVER || game.getState() == GameState.GAME_WIN){
				JOptionPane.showMessageDialog(null, "GOODBYE");
				System.exit(0);
			}
			else{
				frame.repaint();// Otherwise do nothing
			}
		}

	}

}
