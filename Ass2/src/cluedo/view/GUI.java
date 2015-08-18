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

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.GameState;
import cluedo.model.Game;

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

	public GUI(CluedoGame game) {		
		this.game = game;
		//frame = new CluedoMainFrame(this);
		createFrame();
	}
	
	public void createFrame(){
		frame = new CluedoMainFrame(this);
	}




	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(game.getState());
		if(game.getState() == GameState.SETUP_PLAYERS){
			int players = Integer.parseInt(e.getActionCommand());
			game.setNumOfPlayers(players);
			System.out.println(game.getNumOfPlayers());
		}

	}	

}
