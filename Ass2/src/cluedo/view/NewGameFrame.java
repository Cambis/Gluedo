package cluedo.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class NewGameFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -721173876268663162L;

	// Buttons and group
	private ButtonGroup group;
	private JRadioButton one, two, three, four, five, six;

	// Go to player setup
	private JButton next;

	// Number of players that the user wants
	private int numOfPlayers;

	// Prompt the user for input
	private JTextField prompt;

	// Add buttons to this
	private JPanel radioPanel;

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

		next = new JButton("Next");

		init();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		repaint();
	}

	private void init() {

		// Add action listeners
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
		five.addActionListener(this);
		six.addActionListener(this);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		String num = ((JRadioButton) e.getSource()).getText();
		numOfPlayers = Integer.parseInt(num);
		System.out.println("NUM: " + numOfPlayers);

		// Close this frame
		this.dispose();
	}

	public static void main(String args[]) {
		NewGameFrame frame = new NewGameFrame();
	}
}
