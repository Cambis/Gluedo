package cluedo.view;

import java.awt.Checkbox;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cluedo.model.Player;

/**
 * Represents a checklist that the player has, each player should have one. The
 * ticking off of the cards is manual, as it is in the real version of the game.
 * There are no listeners in this class.
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class PlayerCheckList extends JDialog {

	private JCheckBox[] checkBoxes;

	// Suspects, weapons, rooms.
	private final String suspects[] = { "Miss Scarlet", "Professor Plum",
			"Mrs. Peacock", "The Reverend Green", "Colonel Mustard",
			"Mrs. White" };

	private final String weapons[] = { "Dagger", "Lead Pipe", "Revolver",
			"Rope", "Spanner", "Candlestick" };

	private final String rooms[] = { "Study", "Hall", "Lounge", "Library",
			"Billiard Room", "Dining Room", "Conservatory", "Ballroom",
			"Kitchen" };

	public PlayerCheckList() {		
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setResizable(false);
		init();
		pack();		
	}	
	
	/**
	 * Initialises checklist
	 */
	private void init() {
		
		// Special images icons for the checklist
		ImageIcon question = new ImageIcon("question.jpg");
		ImageIcon disabledIcon = new ImageIcon("Cross.png");
		
		checkBoxes = new JCheckBox[suspects.length + weapons.length
				+ rooms.length];
		JPanel checkPanel = new JPanel(new GridLayout(26, 2));
		checkPanel.add(new JLabel("CHECKLIST", JLabel.CENTER));

		// Set up buttons

		// Suspects
		checkPanel.add(new JLabel("Possible Suspects", JLabel.CENTER));
		for (int i = 0; i < suspects.length; i++) {
			checkBoxes[i] = new JCheckBox(suspects[i], false);
			checkBoxes[i].setIcon(question);
			checkBoxes[i].setSelectedIcon(disabledIcon);			
			checkPanel.add(checkBoxes[i]);
		}

		// Weapons
		checkPanel.add(new JLabel("Possible Murder Weapons", JLabel.CENTER));
		for (int i = 0; i < weapons.length; i++) {
			checkBoxes[suspects.length + i] = new JCheckBox(weapons[i], false);
			checkBoxes[suspects.length + i].setIcon(question);
			checkBoxes[suspects.length + i].setSelectedIcon(disabledIcon);	
			checkPanel.add(checkBoxes[suspects.length + i]);
		}

		// Rooms
		checkPanel.add(new JLabel("Possible Crime Scenes", JLabel.CENTER));
		for (int i = 0; i < rooms.length; i++) {
			checkBoxes[suspects.length + weapons.length + i] = new JCheckBox(
					rooms[i], false);
			checkPanel.add(checkBoxes[suspects.length + weapons.length + i]);
			checkBoxes[suspects.length + weapons.length + i].setIcon(question);
			checkBoxes[suspects.length + weapons.length + i].setSelectedIcon(disabledIcon);	
		}
		
		add(checkPanel);		
	}

	public static void main(String args[]) {
		PlayerCheckList list = new PlayerCheckList();
		list.setVisible(true);
		list.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}
}
