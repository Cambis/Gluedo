package cluedo.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import cluedo.control.CluedoGame;

public class CluedoMainFrame extends JFrame {

	private static final long serialVersionUID = 6909931835454164833L;

	CluedoCanvas canvas;
	CluedoBoardPanel board;
	ControlPanel cp;
	JMenuBar menuBar;
	JMenu menu;

	// Asks for the number of players
	NewGameFrame start;

	private CluedoGame game;

	public CluedoMainFrame() {

		// Creates frame
		super("Cluedo");
		setLayout(new BorderLayout()); // use border layour
		this.setSize(800, 800); // sets size
		setJMenuBar(createMenu()); // creates menu bar

		// Add game
		game = new CluedoGame();

		// Add new game frame
		start = new NewGameFrame() {

			private static final long serialVersionUID = -7643791232912141407L;

			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				game.setNumOfPlayers(super.getNumOfPlayers());
			}
		};

		start.setVisible(true); // make it visible
		add(start, BorderLayout.CENTER);

		// Adds cluedo board (JPanel, use this or the one above)
		board = new CluedoBoardPanel();
		add(board, BorderLayout.CENTER);

		// Adds player control panel
		cp = new ControlPanel();
		add(cp, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// addButtons();

		// this.add(createMenu());

		// getContentPane().add(menuBar);

		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		repaint();
	}

	// private void addButtons() {
	// // section for player controls
	// JPanel playerControls = new JPanel(new BorderLayout());
	// add(playerControls, BorderLayout.SOUTH);
	// }

	private JMenuBar createMenu() {
		menuBar = new JMenuBar(); // creates menu bar

		menu = new JMenu("Game"); // creates first menu item
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");

		menuBar.add(menu); // adds to menu bar

		JMenuItem menuItem = new JMenuItem("New Game");
		menu.add(menuItem);

		return menuBar;
	}

}
