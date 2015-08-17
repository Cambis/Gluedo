package cluedo.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class CluedoMainFrame extends JFrame {

	CluedoCanvas canvas;
	CluedoBoardPanel board;
	ControlPanel cp;
	JMenuBar menuBar;
	JMenu menu;

	public CluedoMainFrame() {
		// Creates frame
		super("Cluedo");
		setLayout(new BorderLayout()); // use border layour
		this.setSize(800, 800); // sets size
		setJMenuBar(createMenu()); // creates menu bar

		// Adds cluedo board
		// canvas = new CluedoCanvas();
		// add(canvas, BorderLayout.CENTER); // add canvas to frame

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
		// setVisible(true); // make sure we are visible!

		// Wait for the GUI to tell the frame it can be visible
		setVisible(false);
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
