package cluedo.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
		playerIcon.setHorizontalAlignment(JLabel.CENTER);
		playerLabel = new JLabel();
		playerLabel.setHorizontalAlignment(JLabel.CENTER);
		commandButtons = new JButton[commands.length];
		buttonGroup = new ButtonGroup();

		this.setSize(new Dimension(100,526));

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


		setVisible(true);
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

		if(playerImage != null){
			// Resize image
			BufferedImage resized = CluedoMainFrame.resizeImage(playerImage, 100, 200);

			playerIcon.setIcon(new ImageIcon(resized));
		}
		playerLabel.setText(playerName);
	}

	/**
	 * Testing
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String args[]) throws IOException {
		JFrame frame = new JFrame();
		// BufferedImage image = new CharacterCard(new CluedoCharacter(Suspect.MRS_PEACOCK)).getImage();
		BufferedImage image = ImageIO.read(new File("blue_player_token.png"));

		// BufferedImage img = (BufferedImage) image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

		PlayerInfoPanel panel = new PlayerInfoPanel();
		panel.setPlayer(image, "David");
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
