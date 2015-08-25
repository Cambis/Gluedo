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
public class AccusationBox extends SuggestionBox {

	private JRadioButton[] roomButtons;

	private ButtonGroup roomGroup;

	// Rooms
	private String rooms[] = { "Study", "Hall", "Lounge", "Library",
			"Billiard Room", "Dining Room", "Conservatory", "Ballroom",
	"Kitchen" };

	// User response
	private String roomResponse;

	public AccusationBox() {
		super();
		init();
	}
	
	/**
	 * Initialises accusation box
	 */

	private void init() {
		roomButtons = new JRadioButton[9];
		roomGroup = new ButtonGroup();

		// Override the original weaponListener
		// this is done because the last option for the user is now the
		// room selection, not the weapon. It now transitions to the room option
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
	 * Change the panel to show weapons
	 */
	@Override
	protected void changeToWeapons() {

		panel.removeAll();
		panel.revalidate();
		panel.setLayout(new GridLayout(11, 1));

		// Set label
		prompt.setText("Please choose a weapon");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		panel.add(prompt);

		// Setup buttons
		for (int i = 0; i < 6; i++) {			
			weaponButtons[i] = new JRadioButton(weapons[i], false);
			weaponGroup.add(weaponButtons[i]);
			weaponButtons[i].addActionListener(weaponListener);
			panel.add(weaponButtons[i]);
		}

		panel.add(cancel);

		pack();
		panel.repaint();
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

			roomGroup.add(roomButtons[i]);
			panel.add(roomButtons[i]);
		}

		panel.add(cancel);

		pack();
		panel.repaint();
	}

	/**
	 * Adds the GUI class as a listener to the room options
	 * so it knows when the accusation has been fully made
	 */
	@Override
	public void setListener(GUI g){
		for(int i = 0; i < 9; i++){
			roomButtons[i] = new JRadioButton(rooms[i], false);			
			roomButtons[i].addActionListener(g);

		}
	}
	
	/**
	 * Sets the room response 
	 * @param e is the ActionEvent that occurs when a room is selected
	 */

	public void setRoom(ActionEvent e){
		roomResponse =  ((JRadioButton) e.getSource()).getText();
	}
	
	/**
	 * Returns an array of strings which carries the accusation
	 * inputted by the player
	 */

	@Override
	public String[] getAnswers() {
		return new String[] { suspectResponse, weaponResponse, roomResponse };
	}

	private ActionListener weaponListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			weaponResponse =  ((JRadioButton) e.getSource()).getText();
			changeToRooms();
			repaint();
		}
	};

	public static void main(String args[]) {
		new AccusationBox().setVisible(true);
	}
}
