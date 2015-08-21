package cluedo.view;

import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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

	private JRadioButton[] suspectButtons, weaponButtons;

	private ButtonGroup suspectGroup, weaponGroup;

	// Displays rooms, suspects.
	protected final JPanel panel;

	// Prompts user for input
	protected final JLabel prompt;

	// In case the user wants to cancel their action
	protected final JButton cancel;

	// User responses
	protected String suspectResponse, weaponResponse;

	// Suspects, weapons.
	private String suspects[] = { "Miss Scarlet", "Professor Plum",
			"Mrs. Peacock", "The Reverend Green", "Colonel Mustard",
			"Mrs. White" };

	private String weapons[] = { "Dagger", "Lead Pipe", "Revolver", "Rope",
			"Spanner", "Candlestick" };

	public SuggestionFrame() throws HeadlessException {

		panel = new JPanel();
		prompt = new JLabel();
		cancel = new JButton("Cancel");

		init();
	}

	private void init() {

		setLayout(new GridLayout());

		suspectButtons = new JRadioButton[6];
		weaponButtons = new JRadioButton[9];


		cancel.addActionListener(cancelListener);

		suspectGroup = new ButtonGroup();
		weaponGroup = new ButtonGroup();

		add(panel);
		changeToSuspects();
		pack();

		// GUI or MainFrame should tell this class when it can be visible
		setVisible(false);
		setResizable(false);
		setClosable(false);
	}

	/**
	 * Changes the panel to show suspects
	 */
	protected void changeToSuspects() {

		panel.removeAll();
		panel.revalidate();
		panel.setLayout(new GridLayout(8, 1));

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

		panel.add(cancel);

		pack();
		panel.repaint();
	}

	/**
	 * Change the panel to show weapons
	 */
	protected void changeToWeapons() {

		panel.removeAll();
		panel.revalidate();
		panel.setLayout(new GridLayout(8, 1));

		// Set label
		prompt.setText("Please choose a room");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		panel.add(prompt);

		// Setup buttons
		for (int i = 0; i < 6; i++) {
			weaponButtons[i] = new JRadioButton(weapons[i], false);
			weaponButtons[i].addActionListener(weaponListener);
			weaponGroup.add(weaponButtons[i]);
			panel.add(weaponButtons[i]);
		}

		panel.add(cancel);

		pack();
		panel.repaint();
	}

	/**
	 * Get user input
	 *
	 * @return
	 */
	public String[] getAnswers() {
		return new String[] { suspectResponse, weaponResponse };
	}

	private ActionListener suspectListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			suspectResponse = ((JRadioButton) e.getSource()).getName();
			changeToWeapons();
			repaint();
		}

	};

	protected ActionListener weaponListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			weaponResponse = ((JRadioButton) e.getSource()).getName();
			changeToSuspects();
			repaint();
		}

	};

	private ActionListener cancelListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Should we make it close the window or hide it?
			setVisible(false);
		}

	};

	public static void main(String args[]) {
		SuggestionFrame frame = new SuggestionFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
