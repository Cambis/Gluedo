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

public class GUI implements KeyListener, MouseListener, ActionListener {

	private CluedoMainFrame frame;
	private CluedoGame game;

	// Used to get number of players
	private ButtonGroup numOfPlayersGroup = new ButtonGroup();
	private JRadioButton players_1, players_2, players_3, players_4, players_5, players_6;

	private JApplet app = new JApplet();

	public GUI() {
		frame = new CluedoMainFrame();
		game = new CluedoGame();

		intialize();
		setupPlayers();
	}

	private void intialize() {

		// Set up buttons
		players_1 = new JRadioButton("1", false);
		players_2 = new JRadioButton("2", false);
		players_3 = new JRadioButton("3", false);
		players_4 = new JRadioButton("4", false);
		players_5 = new JRadioButton("5", false);
		players_6 = new JRadioButton("6", false);
	}

	/**
	 * Setup players for the game
	 */
	private void setupPlayers() {
		players_1.addActionListener(this);
		numOfPlayersGroup.add(players_1);
		Container cp = app.getContentPane();
		cp.setLayout(new FlowLayout());
		cp.add(players_1);
		// app.add(cp);
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
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		GUI main = new GUI();
//		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		main.frame.getContentPane().add(main.app);
//		main.app.init();
//		main.app.start();
//		main.frame.setVisible(true);
	}

}
