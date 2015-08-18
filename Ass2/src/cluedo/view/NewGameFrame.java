package cluedo.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Asks user(s) for how many players they want
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class NewGameFrame extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = -721173876268663162L;

	// Buttons and group
	private ButtonGroup group;
	private JRadioButton one, two, three, four, five, six;

	// Player setup
	private JButton addPlayer, removePlayer;

	// Number of players that the user wants
	private int numOfPlayers;

	// Prompt the user for input
	private JTextField prompt;

	// User input
	private JTextArea input;

	// Add buttons to this
	private JPanel radioPanel;

	public NewGameFrame(GUI g) throws HeadlessException {

		super("New Game");

		setLayout(new BorderLayout());
		setClosable(false);

		group = new ButtonGroup();
		one = new JRadioButton("1", false);
		two = new JRadioButton("2", false);
		three = new JRadioButton("3", false);
		four = new JRadioButton("4", false);
		five = new JRadioButton("5", false);
		six = new JRadioButton("6", false);

		prompt = new JTextField();

		init(g);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		repaint();
	}

	private void init(GUI g) {

		// Add action listeners
		one.addActionListener(g);
		two.addActionListener(g);
		three.addActionListener(g);
		four.addActionListener(g);
		five.addActionListener(g);
		six.addActionListener(g);

		// Add buttons to the button group
		group.add(one);
		group.add(two);
		group.add(three);
		group.add(four);
		group.add(five);
		group.add(six);

		// Add radio buttons to a panel
		radioPanel = new JPanel(new FlowLayout());
		radioPanel.add(one);
		radioPanel.add(two);
		radioPanel.add(three);
		radioPanel.add(four);
		radioPanel.add(five);
		radioPanel.add(six);

		// Add prompt
		prompt.setText("How many players?");
		prompt.setEditable(false);

		add(prompt, BorderLayout.NORTH);
		add(radioPanel, BorderLayout.SOUTH);
	}

	private void createPlayers() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String num = ((JRadioButton) e.getSource()).getText();
		numOfPlayers = Integer.parseInt(num);
		System.out.println("NUM: " + numOfPlayers);

		// Close this frame
		try {
			setClosed(true);
		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public final int getNumOfPlayers() {
		return this.numOfPlayers;
	}

//	public static void main(String args[]) {
//		NewGameFrame frame = new NewGameFrame();
//	}
}
