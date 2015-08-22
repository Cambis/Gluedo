package cluedo.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Pops up at the end of a players turn to signify the next player's turn. If
 * you want to test this class separately it should extend JFrame otherwise it
 * should extend JInternalFrame.
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class PlayerStartTurnBox extends JDialog {

	private String playerName;
	private final String message = ", are you ready?";

	private JButton next;

	private JPanel panel;

	private JLabel prompt;

	public PlayerStartTurnBox() {

		panel = new JPanel(new GridLayout(2, 1));
		next = new JButton("Yes");
		prompt = new JLabel();
		prompt.setHorizontalAlignment(JLabel.CENTER);
		prompt.setText(message);

		panel.add(prompt);
		panel.add(next);

		// GUI or MainFrame should call when this is visible
		add(panel);
		setVisible(false);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		pack();
	}

	/**
	 * Changes the player that the frame is addressing
	 *
	 * @param player
	 */
	public void changePlayer(String playerName) {

		this.playerName = playerName;

		prompt.setText(playerName + message);
		pack();
	}

	public static void main(String args[]) {
		PlayerStartTurnBox frame = new PlayerStartTurnBox();
		frame.changePlayer("Dave");
		frame.setVisible(true);
	}

	public void addListener(GUI g) {
		next.addActionListener(g);		
	}
}
