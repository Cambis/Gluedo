package cluedo.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class NewGameFrame extends JFrame {

	// Buttons and group
	private ButtonGroup group;
	private JRadioButton one, two, three, four, five, six;

	// Number of players that the user wants
	private int numOfPlayers;

	// Prompt the user for input
	private JTextField prompt;

	// Add buttons to this
	private JPanel radioPanel;

	// Listens for user input
	private ActionListener action = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String num = ((JRadioButton) e.getSource()).getText();
			numOfPlayers = Integer.parseInt(num);
			System.out.println("NUM: " + numOfPlayers);
		}

	};

	public NewGameFrame() throws HeadlessException {

		super("New Game");

		setLayout(new BorderLayout());

		group = new ButtonGroup();
		one = new JRadioButton("1", false);
		two = new JRadioButton("2", false);
		three = new JRadioButton("3", false);
		four = new JRadioButton("4", false);
		five = new JRadioButton("5", false);
		six = new JRadioButton("6", false);

		prompt = new JTextField();

		init();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		repaint();
	}

	private void init() {

		// Add action listeners
		one.addActionListener(action);
		two.addActionListener(action);
		three.addActionListener(action);
		four.addActionListener(action);
		five.addActionListener(action);
		six.addActionListener(action);

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

		add(prompt, BorderLayout.LINE_START);
		add(radioPanel, BorderLayout.LINE_END);
	}

	public static void main(String args[]) {
		NewGameFrame frame = new NewGameFrame();
	}
}
