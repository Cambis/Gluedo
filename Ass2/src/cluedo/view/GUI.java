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
 * Main user interface class, might not need all of the listeners. Actually we
 * probably don't need this.
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class GUI implements KeyListener, MouseListener, ActionListener {

	// Main frame that holds the boards, actions etc.
	private CluedoMainFrame frame;
	CluedoGame game;

	// Used for setting up players
	//	String name;
	//	Character c;

	public GUI(CluedoGame game) {
		this.game = game;
		frame = new CluedoMainFrame(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(game.getState() == GameState.GENERAL){
			int y = arg0.getX()/frame.getCanvasSquareWidth();
			int x =  arg0.getY()/frame.getCanvasSquareWidth();
			Square s = game.getBoard().squareAt(x, y);	// gets the square related to this click

			System.out.println("X is " + x + " and y is " + y);

			if(frame.isHighlighted(s)){
				game.moveTo(s);

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
			// otherwise do nothing
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println(arg0.getKeyChar());
		if(arg0.isControlDown() && arg0.getKeyChar() == 'C'){
			if(game.getState() != GameState.WELCOME && game.getState() != GameState.SETUP_INDIVIDUAL
					&& game.getState() != GameState.START_TURN){
				frame.seeChecklist();
			}
		}
		
		if(arg0.isControlDown() && arg0.getKeyChar() == 'N'){
			if(game.getState() != GameState.WELCOME && game.getState() != GameState.SETUP_INDIVIDUAL){
				newGamePopUp("NEW GAME");
				frame.repaint();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(game.getState());

		// In the welcome state:
		// The start new game button has been pressed
		
		if(e.getActionCommand().equals("New Game")){
			newGamePopUp("NEW GAME");
		}

		if(e.getActionCommand().equals("Show Checklist")){			
			frame.seeChecklist();
		}

		if (game.getState() == GameState.WELCOME) {
			game.setState(GameState.SETUP_INDIVIDUAL); // changes state to
			// select number of
			// player
			frame.updateCanvas(GameState.SETUP_INDIVIDUAL); // lets the frame
			// know of state
			// change
			// repaints the frame
			frame.createPlayerSelector(this);
			frame.repaint();
		}

		else if (game.getState() == GameState.SETUP_INDIVIDUAL) { // Finish
			// button
			// pressed
			PlayerInitBox playerFrame = frame.getSetup();
			// Map<String,Player> players = playerFrame.getPlayers();

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

			if (players.size() >= 3) {				
				game.setNumOfPlayers(newPlayers.size());
				// List<Player> p = new ArrayList<Player>();
				// p.addAll(players.values());
				// game.addPlayers(p);

				// Add players to the game
				game.addPlayers(newPlayers);

				System.out.println("Done");
				game.setUp();

				game.setState(GameState.START_TURN); // changes state to first
				// players roll
				frame.updateCanvas(GameState.START_TURN); // lets the frame know
				// of state change
				
				// Create card panels
				frame.createCardPanel((int) Math.ceil(18 / newPlayers.size()));
//				Map<String, Set<Card>> cards = new HashMap<String, Set<Card>>();
				for (Player p : newPlayers)
					frame.addPlayerToCardPanel(p.getName(), p.getHand());
				
//				frame.setUpCardPanels((int) Math.ceil(18 / newPlayers.size()), cards);
				
				// Create checklists
				frame.createCheckLists(players.size(), this);
				//frame.repaint();

				System.out.println("Turnbox being created");
				frame.startTurnBox(this, game.getCurrentPlayer().getName());
				//frame.repaint();

				BufferedImage character = new CharacterCard(new CluedoCharacter(
						game.getCurrentPlayer().getCharacter())).getImage();
				frame.setNextPlayer(character,game.getCurrentPlayer().getName());

				frame.setNextPlayer(character,game.getCurrentPlayer().getName());
				//frame.repaint();
				frame.drawPlayers(newPlayers);
				frame.repaint();
			}
		}

		else if (game.getState() == GameState.START_TURN) {
			frame.repaint();
			// turn off dialog
			frame.getTurnBox().setVisible(false);

			runTurn(); // rolls the dice
			game.setState(GameState.GENERAL); // changes state to first players
			// roll
			frame.updateCanvas(GameState.GENERAL); // lets the frame know of
			// state change
			System.out.println("Finding moves");
			findMoves();
			System.out.println("Repaint");
			frame.repaint();
		}

		else if(game.getState() == GameState.SUGGESTION){
			System.out.println("Heard suggestion");
			frame.turnSuggOff(e);
			frame.repaint();
			String[] answers = frame.getSuggestion();
			String room = ((RoomSquare)game.getBoard().squareAt
					(game.getCurrentPlayer().getX(), game.getCurrentPlayer().getY()))
					.getRoom().toString();

			//frame.repaint();
			new RefutionPopUp(game.suggestion(answers, room));
			frame.repaint();
			//frame.repaint();

			nextTurn();

			frame.repaint();
		}

		else if(game.getState() == GameState.ACCUSATION){
			System.out.println("Heard accusation");
			frame.turnAccOff(e);
			String[] answers = frame.getAccusation();

			boolean correct = game.accusation(answers);

			new AccusationPopUp(correct);
			//frame.repaint();

			if(correct){
				frame.updateCanvas(GameState.GAME_WIN);
				game.setState(GameState.GAME_WIN);
				newGamePopUp("CONGRATULATIONS YOU HAVE WON");
			}
			else if (game.getNumOfPlayers() > 1){
				game.removePlayer();
				nextTurn();
			}
			else{
				frame.updateCanvas(GameState.GAME_OVER);
				game.setState(GameState.GAME_OVER);
				newGamePopUp("GAME OVER");
			}
			frame.repaint();
		}

		frame.repaint();

	}

	// Helper methods

	private void findMoves() {
		Board b = game.getBoard(); // gets board to find possible move
		Player current = game.getCurrentPlayer(); // gets current player
		Square start = b.squareAt(current.getX(), current.getY()); // gets
		// current
		// player's
		// location

		System.out.println("Starting square is " + current.getX() + " " + current.getY() + " and is a "
				+ start.getClass().toString() + " and is occupied " + ((InhabitableSquare) start).isOccupied());

		int roll = game.getRoll(); // gets their roll value
		System.out.println("Roll: " + roll);

		Set<Square> lands = game.getBoard().djikstra(start, roll);
		Set<String> rooms = findRooms(lands);

		frame.drawValidMoves(lands, rooms);
	}

	private Set<String> findRooms(Set<Square> lands) {
		Set<String> room = new HashSet<String>();
		Set<Square> toRemove = new HashSet<Square>();
		for (Square s : lands) {
			if (s instanceof DoorSquare) {
				room.add(((DoorSquare) s).getRoom().toString());
				toRemove.add(s);
			}
		}
		lands.removeAll(toRemove);
		System.out.println(room.size() + " rooms located");
		for(String r : room){
			System.out.println(r);
		}
		return room;
	}

	public void runTurn() {
		Player p = game.getCurrentPlayer();
		BufferedImage[] dice = game.getDiceRoll();
		Set<Card> cards = p.getHand();

		// frame.setCardPanel(cards, dice);
		frame.setCardPanel(p.getName(), dice);
		frame.showCards();
		// then pass information to interaction panel
	}

	public void nextTurn() {
		frame.hideCards();
		game.nextPlayer(); // moves to next players turn
		frame.updateCanvas(GameState.START_TURN);
		frame.getTurnBox().changePlayer(game.getCurrentPlayer().getName()); // updates turn box
		game.setState(GameState.START_TURN);
		frame.getTurnBox().setVisible(true);
		frame.turnOffChecklist();

		//frame.repaint();

		BufferedImage character = new CharacterCard(new CluedoCharacter(
				game.getCurrentPlayer().getCharacter())).getImage();
		frame.setNextPlayer(character,game.getCurrentPlayer().getName());
	}

	public void newGame(){
		game = new CluedoGame();
		frame.setVisible(false);
		frame = new CluedoMainFrame(this);
		frame.repaint();
	}
	
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
