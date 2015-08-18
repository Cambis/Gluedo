package cluedo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
			"Mrs. Peacock", "Reverend Green", "Colonel Mustard", "Mrs. White" };

	// User input
	private JTextField prompt;
	private JTextArea input;
	private JPanel userInputPanel;

	// New players
	private List<Player> players;
	private Set<String> playerNames;
	private Set<String> usedSuspects;

	// Information for new player
	private String name;
	private String character;
	private Suspect suspect;

	private ActionListener promptListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String text = prompt.getText();

			if (playerNames.contains(text))
				prompt.setText("That name is already chosen");
			else {
				input.append(text + "\n");
				playerNames.add(text);
				name = text;
			}

			prompt.selectAll();

			// Make sure the new text is visible, even if there
			// was a selection in the text area.
			input.setCaretPosition(input.getDocument().getLength());
		}

	};

	private ActionListener suspectListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			character = ((JRadioButton) e.getSource()).getText();

			if (usedSuspects.contains(character))
				prompt.setText("That name is already chosen");
			else {
				// usedSuspects.add(character);
				for (Suspect s : Suspect.values())
					if (s.toString().equals(character)) {
						suspect = s;
						break;
					}
			}
		}

	};

	private ActionListener playerListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			usedSuspects.add(character);
			players.add(new Player(name, suspect));
		}

	};

	public PlayerInitFrame() throws HeadlessException {
		super("Welcome");
		setSize(300, 500);
		setLayout(new GridLayout(3, 3, 1, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		setVisible(true);
	}

	private void init() {

		players = new ArrayList<Player>();
		playerNames = new HashSet<String>();
		usedSuspects = new HashSet<String>();

		// Set up players
		playerGroup = new ButtonGroup();
		addPlayer = new JButton("Add Player");
		addPlayer.setPreferredSize(new Dimension(200, 1));
		addPlayer.addActionListener(playerListener);
		removePlayer = new JButton("Remove Player");
		playerGroup.add(addPlayer);
		playerGroup.add(removePlayer);

		prompt = new JTextField(15);
		prompt.setEditable(true);
		prompt.setText("Please enter a name");
		prompt.addActionListener(promptListener);

		input = new JTextArea();
		input.setEditable(true);
		playerPanel = new JPanel(new BorderLayout());

		scroll = new JScrollPane(prompt);

		playerPanel.add(scroll, BorderLayout.NORTH);
		playerPanel.add(addPlayer, BorderLayout.WEST);
		playerPanel.add(input, BorderLayout.EAST);

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
		add(suspectPanel);
		pack();
	}

	public static void main(String args[]) {
		new PlayerInitFrame();
	}
}
