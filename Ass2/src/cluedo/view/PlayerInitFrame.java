package cluedo.view;

import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PlayerInitFrame extends JFrame {

	private static final long serialVersionUID = -2976740998106737976L;

	// Lists all of the players
	private JScrollPane scroll;

	// Player setup
	private ButtonGroup playerGroup;
	private JButton addPlayer, removePlayer;
	private int numOfPlayers;
	private JPanel playerPanel;

	// Suspects, rooms and weapons
	private String suspects[] = { "Miss Scarlet", "Professor Plum",
			"Mrs. Peacock", "Reverend Green", "Colonel Mustard", "Mrs. White" };

	private String weapons[] = { "Candlestick", "Dagger", "Lead Pipe",
			"Revolver", "Rope", "Spanner" };

	// User input
	private JTextField prompt;
	private JTextArea input;
	private JPanel userInputPanel;

	public PlayerInitFrame() throws HeadlessException {
		super("Welcome");
		setSize(300, 500);
		setLayout(new GridLayout (3, 3, 1, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		setVisible(true);
	}

	private void init() {

		// Set up suspects
		JRadioButton suspectButtons[] = new JRadioButton[6];
		JPanel suspectPanel = new JPanel(new GridLayout(6, 0, 0, 0));
		suspectPanel.setSize(100, 100);
		ButtonGroup suspectGroup = new ButtonGroup();

		for (int i = 0; i < suspectButtons.length; i++) {
			suspectButtons[i] = new JRadioButton(suspects[i], false);
			suspectGroup.add(suspectButtons[i]);
			suspectPanel.add(suspectButtons[i]);
		}

		// Set up weapons
		JRadioButton weaponButtons[] = new JRadioButton[6];
		JPanel weaponPanel = new JPanel(new GridLayout(6, 0, 0, 0));
		weaponPanel.setSize(100, 100);
		ButtonGroup weaponGroup = new ButtonGroup();

		for (int i = 0; i < weaponButtons.length; i++) {
			weaponButtons[i] = new JRadioButton(weapons[i], false);
			weaponGroup.add(weaponButtons[i]);
			weaponPanel.add(weaponButtons[i]);
		}

		add(suspectPanel);
		add(weaponPanel);
		pack();
	}

	public static void main(String args[]) {
		new PlayerInitFrame();
	}
}
