package cluedo.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class CluedoFrame extends JFrame{

	CluedoCanvas canvas;
	JMenuBar menuBar;
	JMenu menu;

	public CluedoFrame(){
		super("Cluedo");
		canvas = new CluedoCanvas();
		setLayout(new BorderLayout()); // use border layour
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!

		addButtons();

		createMenu();
		setJMenuBar(menuBar);
		
		getContentPane().add(menuBar);

		repaint();
	}

	private void addButtons() {
		// section for player controls
		JPanel playerControls = new JPanel(new BorderLayout());
		add(playerControls, BorderLayout.SOUTH);
	}

	private void createMenu() {		
		menuBar = new JMenuBar(); // creates menu bar

		menu = new JMenu("Game"); // creates first menu item
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");

		menuBar.add(menu); // adds to menu bar

		JMenuItem menuItem = new JMenuItem("New Game");
		menu.add(menuItem);
		
	}

}
