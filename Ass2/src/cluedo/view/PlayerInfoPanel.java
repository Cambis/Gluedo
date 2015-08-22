package cluedo.view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cluedo.model.cards.CharacterCard;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;

/**
 * Shows the player info on the right side of the main frame
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class PlayerInfoPanel extends JPanel {

	// Image of the player's character
	private JLabel playerIcon, playerLabel;

	// Buttons that the player can choose
	private JButton[] commandButtons;
	private ButtonGroup buttonGroup;

	private final String commands[] = new String[] { "Move", "Make A Suggestion",
			"Make An Accusation", "Show Cards", "Show Checklist" };

	// Current player info
	private BufferedImage playerImage;
	private String playerName;

	public PlayerInfoPanel() {

		playerIcon = new JLabel();
		playerLabel = new JLabel();
		playerLabel.setHorizontalAlignment(JLabel.CENTER);
		commandButtons = new JButton[commands.length];
		buttonGroup = new ButtonGroup();

		init();
	}

	private void init() {

		setLayout(new GridLayout(10, 1));

		add(playerIcon);
		add(playerLabel);

		// Set up buttons
		for (int i = 0; i < commandButtons.length; i++) {
			commandButtons[i] = new JButton(commands[i]);
			buttonGroup.add(commandButtons[i]);
			add(commandButtons[i]);
		}

		// TODO setup button listeners

	}

	/**
	 * Change the current player
	 *
	 * @param playerImage
	 * @param playerName
	 */
	public void setPlayer(BufferedImage playerImage, String playerName) {

		this.playerImage = playerImage;
		this.playerName = playerName;

		playerIcon.setIcon(new ImageIcon(playerImage));
		playerIcon.setSize(50, 50);
		playerLabel.setText(playerName);
	}

	/**
	 * Testing
	 * @param args
	 */
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		Image image = new CharacterCard(new CluedoCharacter(Suspect.MRS_PEACOCK)).getImage();
		BufferedImage img = (BufferedImage) image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		
		PlayerInfoPanel panel = new PlayerInfoPanel();
		panel.setPlayer(image, "David");
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
