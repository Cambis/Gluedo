package cluedo.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.GameState;
import cluedo.model.cards.Card;

public class CluedoMainFrame extends JFrame {

	private static final long serialVersionUID = 6909931835454164833L;

	CluedoCanvas canvas;
	CluedoBoardPanel board;
	CardPanel cp;
	JMenuBar menuBar;
	JMenu menu;
	JButton startButton;

	// Asks for the number of players
	NewGameFrame start;
	PlayerInitFrame nameAsker;

	private CluedoGame game;

	private GameState state;

	public CluedoMainFrame(GUI g) {

		// Creates frame
		super("Cluedo");
		setLayout(new BorderLayout()); // use border layour
		this.setSize(700, 700); // sets size
		setJMenuBar(createMenu()); // creates menu bar

		board = new CluedoBoardPanel();
		board.addMouseListener(g);
		board.updateState(GameState.WELCOME);
		add(board, BorderLayout.CENTER);


		//Sets opening mode to welcome
		updateCanvas(GameState.WELCOME);

		//Adds start button to panel
		createStartButton();
		startButton.addActionListener(g);
		add(startButton, BorderLayout.SOUTH);
		//startButton.setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		//pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		repaint();

		//Sets up player frame and links to gui
		start = new NewGameFrame(g);
		start.setVisible(false);

	}

	public PlayerInitFrame getSetup(){
		return nameAsker;
	}


	/**
	 * Creates start button for title screen
	 */

	private void createStartButton(){
		startButton = new JButton("Start New Game");
	}

	/**
	 * Creates player set up tool
	 * @param g
	 */
	public void createPlayerSelector(GUI g){
		nameAsker = new PlayerInitFrame(); // note: Link to Finish button
		nameAsker.addListener(g);
	}

	public void createCardPanel(int cards){
		cp = new CardPanel(cards);
		add(cp,BorderLayout.SOUTH);
		cp.setVisible(true);
		//pack();
	}

	/**
	 * This updates the panel to hold a given
	 * set of cards and dice rolls
	 *
	 * This should be called when a new player is taking their turn
	 *
	 * @param cards is a set of the current players cards
	 * @param dice	is an array of the images of
	 * the dice values they have rolled
	 */

	public void setCardPanel(Set<Card> cards, Image[] dice){
		ImageIcon[] d = new ImageIcon[dice.length];
		ImageIcon[] c = new ImageIcon[cards.size()];

		// Creates dice image icons
		for(int i = 0; i < dice.length; i++){
			d[i] = new ImageIcon(dice[i]);
		}

		// Creates card image icons
		int count = 0;
		for(Card card: cards){
			c[count++] = new ImageIcon(card.getImage());
		}

		cp.setCards(d, c);
	}


	private JMenuBar createMenu() {
		menuBar = new JMenuBar(); // creates menu bar

		menu = new JMenu("Game"); // creates first menu item
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");

		menuBar.add(menu); // adds to menu bar

		JMenuItem menuItem = new JMenuItem("New Game");
		menu.add(menuItem);

		return menuBar;
	}

	@Override
	public void repaint(){
		//System.out.println(state);
		if(state != GameState.WELCOME){ // turns off the startButton after title screen
			startButton.setVisible(false);
		}

		if(state != GameState.WELCOME && state != GameState.SETUP_INDIVIDUAL){
			cp.repaint();
		}
		//start.repaint();
		board.repaint();

	}

	public int getCanvasSquareWidth(){
		return board.getWidth();
	}

	public void updateCanvas(GameState state) {
		this.state = state;
		board.updateState(state);
	}

	public static void main(String args[]) {
		new CluedoMainFrame(new GUI(new CluedoGame()));
	}
}


