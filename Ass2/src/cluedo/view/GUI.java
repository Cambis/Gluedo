package cluedo.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.GameState;
import cluedo.model.Game;
import cluedo.model.board.Square;

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
		int x = arg0.getX()/frame.getCanvasSquareWidth();
		int y =  arg0.getY()/frame.getCanvasSquareWidth();
		Square s = game.getBoard().squareAt(x, y);
		
		if(game.getState() == GameState.GENERAL){
			
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
		
		//Mode is setting up an individual player
		if(game.getState() == GameState.SETUP_INDIVIDUAL){
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
				JTextField t = (JTextField)arg0.getSource();
				name = t.getText();
				System.out.println(name);
			}
		}

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
			game.setState(GameState.SETUP_PLAYERS); // changes state to select number of playes
			frame.updateCanvas(GameState.SETUP_PLAYERS); // lets the frame know of state change			
			frame.repaint(); // repaints the frame
		}

		else if(game.getState() == GameState.SETUP_PLAYERS){
			int players = Integer.parseInt(e.getActionCommand());
			game.setNumOfPlayers(players);
			game.setState(GameState.SETUP_INDIVIDUAL);
			frame.updateCanvas(GameState.SETUP_INDIVIDUAL);
			frame.repaint();
			//System.out.println(game.getNumOfPlayers());
			//game.setState(GameState.);
		}

		else if(game.getState() == GameState.SETUP_INDIVIDUAL){

		}

	}	

}
