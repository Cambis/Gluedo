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
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
			"Mrs. Peacock", "The Reverend Green", "Colonel Mustard", "Mrs. White" };

	// User input
	private JTextField input;
	private JLabel prompt;
	private JPanel playerDisplayPanel;
	private String selectedPlayer;
	private JList<String> playerDisplay;
	private DefaultListModel<String> model;

	// New players
	private Map<String, Player> players;
	private Set<String> usedSuspects;

	// Information for new player
	private String name;
	private String character;
	private Suspect suspect;

	public PlayerInitFrame() throws HeadlessException {
		super("Welcome");
		setSize(500, 500);
		setLayout(new GridLayout(2, 2, 1, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		setVisible(true);
		setResizable(false);
	}

	private void init() {

		players = new HashMap<String, Player>();
		usedSuspects = new HashSet<String>();

		// Set up players
		playerGroup = new ButtonGroup();
		addPlayer = new JButton("Add Player");
		addPlayer.addActionListener(playerAddListener);
		removePlayer = new JButton("Remove Player");
		removePlayer.addActionListener(playerRemoveListener);
		playerGroup.add(addPlayer);
		playerGroup.add(removePlayer);

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
		playerPanel = new JPanel(new GridLayout(4, 1));

		scroll = new JScrollPane(playerDisplay);

		playerPanel.add(prompt);
		playerPanel.add(input);
		playerPanel.add(addPlayer);
		playerPanel.add(removePlayer);

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
		add(new JPanel());		// Add an empty one, will put image of character here instead
		add(suspectPanel);
		add(playerDisplayPanel);
		pack();
	}
	
	public Map<String, Player> getPlayers(){
		return players;
	}

	private ActionListener suspectListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			character = ((JRadioButton) e.getSource()).getText();

			if (usedSuspects.contains(character))
				prompt.setText("That name is already chosen");
			else {
				prompt.setText("Please enter a name:");
				for (Suspect s : Suspect.values())
					if (s.toString().equals(character)) {
						suspect = s;
						break;
					}
			}
		}

	};

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

			// Null checker
			if (character == null || name == null) {
				prompt.setText("Invalid input, try again");
			}
			// check if player can be added
			else if (usedSuspects.contains(character)
					|| players.containsKey(name)) {
				prompt.setText("Character has already been chosen");
			} else {
				prompt.setText("Please enter a name:");
				usedSuspects.add(character);
				players.put(name, new Player(name, suspect));
				model.addElement(name);
				System.out.println("PLAYER: " + name + " " + character);
			}

		}

	};

	private ActionListener playerRemoveListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {

			// Null checker
			if (selectedPlayer == null) {
				prompt.setText("No player selected");
				return;
			}

			if (players.containsKey(selectedPlayer)) {
				prompt.setText("Please enter a name:");
				String susName = players.get(selectedPlayer).getCharacter().toString();
				usedSuspects.remove(susName);
				players.remove(selectedPlayer);
				model.removeElement(selectedPlayer);
			}
		}

	};

	public static void main(String args[]) {
		new PlayerInitFrame();
	}
}
