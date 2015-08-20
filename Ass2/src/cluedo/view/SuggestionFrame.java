package cluedo.view;

import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Prompts the user for a suggestion.
 *
 * If you want to test this class separately make it extend JFrame and not
 * JInternalFrame, the final version should extend JInternalFrame.
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class SuggestionFrame extends JInternalFrame {

	private JRadioButton[] suspectButtons, roomButtons;

	private ButtonGroup suspectGroup, roomGroup;

	// Displays rooms, suspects.
	private JPanel panel;

	private JLabel prompt;

	// User responses
	private String suspectResponse, roomResponse;

	// Used to test this class separately from the GUI
	private static boolean TEST;

	// Suspects, rooms and weapons
	private String suspects[] = { "Miss Scarlet", "Professor Plum",
			"Mrs. Peacock", "The Reverend Green", "Colonel Mustard",
			"Mrs. White" };

	private String rooms[] = { "Study", "Hall", "Lounge", "Library",
			"Billiard Room", "Dining Room", "Conservatory", "Ballroom",
			"Kitchen" };

	public SuggestionFrame() throws HeadlessException {
		this(false);
	}

	/**
	 * Used for testing this class
	 *
	 * @param test
	 * @throws HeadlessException
	 */
	public SuggestionFrame(boolean test) throws HeadlessException {
		TEST = test;
		init();
	}

	private void init() {

		setLayout(new FlowLayout());

		suspectButtons = new JRadioButton[6];
		roomButtons = new JRadioButton[9];

		panel = new JPanel();
		prompt = new JLabel();

		suspectGroup = new ButtonGroup();
		roomGroup = new ButtonGroup();

		add(panel);
		changeToSuspects();
		pack();

		// If it is a test set it to visible, otherwise the GUI should tell
		// were ever this class is visible
		setVisible(TEST);
	}

	/**
	 * Changes the panel to show suspects
	 */
	private void changeToSuspects() {

		panel.removeAll();
		panel.revalidate();
		panel.setLayout(new GridLayout(7, 1));

		// Set label
		prompt.setText("Please choose a character");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		panel.add(prompt);

		// Set up buttons
		for (int i = 0; i < 6; i++) {
			suspectButtons[i] = new JRadioButton(suspects[i], false);
			suspectButtons[i].addActionListener(suspectListener);
			suspectGroup.add(suspectButtons[i]);
			panel.add(suspectButtons[i]);
		}

		pack();
		panel.repaint();
	}

	/**
	 * Change the panel to show rooms
	 */
	private void changeToRooms() {

		panel.removeAll();
		panel.revalidate();
		panel.setLayout(new GridLayout(10, 1));

		// Set label
		prompt.setText("Please choose a room");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		panel.add(prompt);

		// Setup buttons
		for (int i = 0; i < 9; i++) {
			roomButtons[i] = new JRadioButton(rooms[i], false);
			roomButtons[i].addActionListener(roomListener);
			roomGroup.add(roomButtons[i]);
			panel.add(roomButtons[i]);
		}

		pack();
		panel.repaint();
	}

	/**
	 * Get user input
	 *
	 * @return
	 */
	public String[] getAnswers() {
		return new String[] { suspectResponse, roomResponse };
	}

	private ActionListener suspectListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			suspectResponse = ((JRadioButton) e.getSource()).getName();
			changeToRooms();
			repaint();
		}

	};

	private ActionListener roomListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			roomResponse = ((JRadioButton) e.getSource()).getName();
			changeToSuspects();
			repaint();
		}

	};

	public static void main(String args[]) {
		new SuggestionFrame(true);
	}
}
