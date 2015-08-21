package cluedo.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 * Prompts the user for an accusation, same as the suggestion frame but it asks
 * for a room as well.
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class AccusationFrame extends SuggestionFrame {

	private JRadioButton[] roomButtons;

	private ButtonGroup roomGroup;

	// Rooms
	private String rooms[] = { "Study", "Hall", "Lounge", "Library",
			"Billiard Room", "Dining Room", "Conservatory", "Ballroom",
			"Kitchen" };

	// User response
	private String roomResponse;

	public AccusationFrame() {
		super();
		init();
	}

	private void init() {

		roomButtons = new JRadioButton[9];
		roomGroup = new ButtonGroup();

		// Override the original weaponListener
		weaponListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				weaponResponse = ((JRadioButton) e.getSource()).getName();
				changeToRooms();
				repaint();
			}

		};
	}

	/**
	 * Change the panel to show rooms
	 */
	protected void changeToRooms() {

		panel.removeAll();
		panel.revalidate();
		panel.setLayout(new GridLayout(11, 1));

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

		panel.add(cancel);

		pack();
		panel.repaint();
	}

	@Override
	public String[] getAnswers() {
		return new String[] { suspectResponse, weaponResponse, roomResponse };
	}

	private ActionListener roomListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			roomResponse = ((JRadioButton) e.getSource()).getName();
			changeToSuspects();
			repaint();
		}

	};

	public static void main(String args[]) {
		new AccusationFrame().setVisible(true);
	}
}
