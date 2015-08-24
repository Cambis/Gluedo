package cluedo.view;

import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Prompts the user for a suggestion.
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class SuggestionBox extends JDialog {

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

	public SuggestionBox() throws HeadlessException {

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
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
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
		prompt.setText("Please choose a weapon");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		panel.add(prompt);

		// Setup buttons
		for (int i = 0; i < 6; i++) {			
			weaponGroup.add(weaponButtons[i]);
			panel.add(weaponButtons[i]);
		}

		panel.add(cancel);

		pack();
		panel.repaint();
	}
	
	/**
	 * 
	 */
	
	public void setListener(GUI g){
		for(int i = 0; i < 6 ; i++){
		weaponButtons[i] = new JRadioButton(weapons[i], false);
		weaponButtons[i].addActionListener(weaponListener);
		}
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
			//changeToSuspects();
			//repaint();
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
		SuggestionBox frame = new SuggestionBox();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
