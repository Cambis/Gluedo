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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.GameState;
import cluedo.model.Game;
import cluedo.model.Player;
import cluedo.model.board.Square;
import cluedo.model.cards.Card;

/**
 * Main user interface class, might not need all of the listeners.
 * Actually we probably don't need this.
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class GUI implements KeyListener, MouseListener, ActionListener {

	// Main frame that holds the boards, actions etc.
	private CluedoMainFrame frame;
	CluedoGame game;

	//Used for setting up players
	String name;
	Character c; 

	public GUI(CluedoGame game) {		
		this.game = game;
		frame = new CluedoMainFrame(this);		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {		
		if(game.getState() == GameState.GENERAL){
			int x = arg0.getX()/frame.getCanvasSquareWidth();
			int y =  arg0.getY()/frame.getCanvasSquareWidth();
			Square s = game.getBoard().squareAt(x, y);	// gets the square related to this click	
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
		// TODO Auto-generated method stub

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

		if(game.getState() == GameState.WELCOME){
			game.setState(GameState.SETUP_INDIVIDUAL); // changes state to select number of player
			frame.updateCanvas(GameState.SETUP_INDIVIDUAL); // lets the frame know of state change			
			frame.repaint(); // repaints the frame
			frame.createPlayerSelector(this);
		}		

		else if(game.getState() == GameState.SETUP_INDIVIDUAL){ //Finish button pressed
			PlayerInitFrame playerFrame = frame.getSetup();
			Map<String,Player> players = playerFrame.getPlayers();
			if(players.size() > 3){
				game.setNumOfPlayers(players.size());
				List<Player> p = new ArrayList<Player>();
				p.addAll(players.values());
				game.addPlayers(p);
				System.out.println("Done");
				game.setUp();
				
				game.setState(GameState.START_TURN); // changes state to first players roll
				frame.updateCanvas(GameState.START_TURN); // lets the frame know of state change
				frame.createCardPanel((int)Math.ceil(18/players.size()));	
				// Need to pop up a startTurn box here
				frame.repaint();
			}
		}
		
		else if(game.getState() == GameState.START_TURN){
			runTurn(); // rolls the dice
			game.setState(GameState.GENERAL); // changes state to first players roll
			frame.updateCanvas(GameState.GENERAL); // lets the frame know of state change
			frame.repaint();
		}
		
		

	}	
	
	public void runTurn(){
		Player p = game.getCurrentPlayer();
		Image[] dice  = game.getDiceRoll();
		Set<Card>  cards = p.getHand();
		
		frame.setCardPanel(cards, dice);		
		// then pass information to interaction panel
	}

}
