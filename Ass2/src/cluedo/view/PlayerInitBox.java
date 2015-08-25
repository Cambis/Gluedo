package cluedo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cluedo.model.Player;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;

public class PlayerInitBox extends JDialog {

	private static final long serialVersionUID = -2976740998106737976L;

	// Lists all of the players
	private JScrollPane scroll;

	// Player setup
	private ButtonGroup playerGroup;
	private JButton addPlayer, removePlayer, finish;
	private int numOfPlayers;
	private JPanel playerPanel;

	// Suspects, rooms and weapons
	private String suspects[] = { "Miss Scarlet", "Professor Plum",
			"Mrs. Peacock", "The Reverend Green", "Colonel Mustard",
			"Mrs. White" };

	// User input
	private JTextField input;
	private JLabel prompt;
	private JPanel playerDisplayPanel;
	private String selectedPlayer;
	private JList<String> playerDisplay;
	private DefaultListModel<String> model;

	// Error message
	private JFrame errorPrompt = new JFrame();

	// New players
	private Map<String, String> players; // Maps player names to characters
	private Set<String> usedSuspects;

	// Information for new player
	private String name;
	private String character;
	private Suspect suspect;

	public PlayerInitBox() throws HeadlessException {		
		setSize(500, 500);
		setLayout(new GridLayout(2, 2, 1, 1));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		init();
		setVisible(true);
		setResizable(false);
	}

	private void init() {

		players = new HashMap<String, String>();
		usedSuspects = new HashSet<String>();

		// Set up players
		playerGroup = new ButtonGroup();
		addPlayer = new JButton("Add Player");
		addPlayer.addActionListener(playerAddListener);
		removePlayer = new JButton("Remove Player");
		removePlayer.addActionListener(playerRemoveListener);
		finish = new JButton("Finish");
		finish.addActionListener(finishListener);

		playerGroup.add(addPlayer);
		playerGroup.add(removePlayer);
		playerGroup.add(finish);

		input = new JTextField(15);
		input.setEditable(true);
		// input.setText("Please enter a name");
		input.selectAll();

		prompt = new JLabel();
		prompt.setHorizontalAlignment(JLabel.CENTER);
		prompt.setText("Please enter a name:");

		// Set up JList
		model = new DefaultListModel<String>();
		playerDisplay = new JList<String>(model);
		playerDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerDisplay.addListSelectionListener(listListener);
		playerPanel = new JPanel(new GridLayout(5, 1));

		scroll = new JScrollPane(playerDisplay);

		playerPanel.add(prompt);
		playerPanel.add(input);
		playerPanel.add(addPlayer);
		playerPanel.add(removePlayer);
		playerPanel.add(finish);

		playerDisplayPanel = new JPanel(new GridLayout(2, 1));
		JLabel playerLabel = new JLabel();
		playerLabel.setHorizontalAlignment(JLabel.CENTER);
		playerLabel.setText("Players");
		playerDisplayPanel.add(playerLabel);
		playerDisplayPanel.add(scroll);

		// Set up suspects
		JRadioButton suspectButtons[] = new JRadioButton[6];
		JPanel suspectPanel = new JPanel(new GridLayout(6, 0, 0, 0));
		suspectPanel.setSize(100, 100);
		ButtonGroup suspectGroup = new ButtonGroup();

		for (int i = 0; i < suspectButtons.length; i++) {
			suspectButtons[i] = new JRadioButton(suspects[i], false);
			suspectButtons[i].addActionListener(suspectListener);
			suspectGroup.add(suspectButtons[i]);
			suspectPanel.add(suspectButtons[i]);
		}

		add(playerPanel);
		add(new JPanel()); 
		add(suspectPanel);
		add(playerDisplayPanel);		
		pack();
	}

	public void setPrompt(String msg) {
		prompt.setText(msg);
	}

	public Map<String, String> getPlayers() {
		return players;
	}

	/**
	 * Adds a listener to the finish button from the GUI class so it can extract
	 * information out of the PlayerInitFrame once it has been completed
	 *
	 * @param g
	 */
	public void addListener(GUI g) {
		finish.addActionListener(g);
	}

	private ActionListener suspectListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			character = ((JRadioButton) e.getSource()).getText();

			if (usedSuspects.contains(character)) {				
				JOptionPane.showMessageDialog(new JFrame(),
						"That suspect has already been chosen",
						"Invalid Input", JOptionPane.ERROR_MESSAGE);
			} else {
				prompt.setText("Please enter a name:");
				for (Suspect s : Suspect.values())
					if (s.toString().equals(character)) {
						suspect = s;
						break;
					}
			}
		}

	};
	
	/**
	 * A series of private inner listeners that carry out it own 
	 * error checking
	 */

	private ListSelectionListener listListener = new ListSelectionListener() {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			selectedPlayer = (String) playerDisplay.getSelectedValue();
		}

	};

	private ActionListener playerAddListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			String text = input.getText();
			name = text;

			input.selectAll();

			// Check if there is a valid number of players
			if (numOfPlayers >= 6) {				
				JOptionPane.showMessageDialog(new JFrame(),
						"Maximum amount of players reached", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Null checker
			if (character == null || name == null || name.length() == 0) {				
				JOptionPane.showMessageDialog(new JFrame(),
						"No name or no character", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);
			}			
			// check if player can be added
			else if (usedSuspects.contains(character)) {				
				JOptionPane.showMessageDialog(new JFrame(),
						"Suspect has already been chosen", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);				
			} else if (players.containsKey(name)) {				
				JOptionPane.showMessageDialog(new JFrame(),
						"That name is already being used", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);
				input.setText(null);
			} else { // the input for a single player is valid
				prompt.setText("Please enter a name:");
				usedSuspects.add(character);				
				players.put(name, character);
				model.addElement(character + ": " + name);
				numOfPlayers++;
				input.setText(null);				
			}
		}
	};

	/**
	 * Removes players from the list
	 */
	private ActionListener playerRemoveListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Null checker
			if (selectedPlayer == null) {				
				JOptionPane.showMessageDialog(new JFrame(),
						"No player selected", "Invalid Action",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (players.containsKey(selectedPlayer)) {
				prompt.setText("Please enter a name:");				

				// Find player and remove them
				String susName = players.get(selectedPlayer);
				usedSuspects.remove(susName);				
				players.remove(selectedPlayer);
				model.removeElement(susName + ": " + selectedPlayer);
				numOfPlayers--;
			}
		}
	};

	private ActionListener finishListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Check if there is a valid number of players
			if (numOfPlayers < 3) {				
				JOptionPane.showMessageDialog(new JFrame(),
						"Minimum amount of players is three", "Too few players",
						JOptionPane.ERROR_MESSAGE);
			}
			else
				setVisible(false);
		}

	};

	public static void main(String args[]) {
		PlayerInitBox box = new PlayerInitBox();
		box.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
