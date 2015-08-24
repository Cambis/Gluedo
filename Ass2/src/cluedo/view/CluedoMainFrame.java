package cluedo.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
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
import cluedo.model.Player;
import cluedo.model.board.Square;
import cluedo.model.cards.Card;

public class CluedoMainFrame extends JFrame {

	private static final long serialVersionUID = 6909931835454164833L;

	private CluedoCanvas canvas;
	private CluedoBoardPanel board;
	private CardPanel cp;
	private JMenuBar menuBar;
	private JMenu menu;
	private JButton startButton;
	
	private PlayerInfoPanel playerInfoPanel;
	
	private PlayerStartTurnBox turnBox;
	
	private SuggestionBox suggBox;

	// Asks for the number of players
	NewGameFrame start;
	PlayerInitBox nameAsker;

	private CluedoGame game;

	private GameState state;

	private AccusationBox accBox;

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

		// Sets opening mode to welcome
		updateCanvas(GameState.WELCOME);

		// Adds start button to panel
		createStartButton();
		startButton.addActionListener(g);
		add(startButton, BorderLayout.SOUTH);
		// startButton.setVisible(true);
		
		// Add info panel
		playerInfoPanel = new PlayerInfoPanel();
		playerInfoPanel.setVisible(true);
		add(playerInfoPanel, BorderLayout.EAST);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		repaint();

		// Sets up player frame and links to gui
		start = new NewGameFrame(g);
		start.setVisible(false);

	}

	public PlayerInitBox getSetup() {
		return nameAsker;
	}
	
	/**
	 * Passes a set of possible landing squares and Rooms onto the 
	 * CluedoBoardPanel for drawing
	 */
	
	public void drawValidMoves(Set<Square> lands, Set<String> rooms){
		board.setLandingSquares(lands, rooms);
	}

	/**
	 * Creates start button for title screen
	 */

	private void createStartButton() {
		startButton = new JButton("Start New Game");
	}

	/**
	 * Creates player set up tool
	 *
	 * @param g
	 */
	public void createPlayerSelector(GUI g) {
		nameAsker = new PlayerInitBox(); // note: Link to Finish button
		nameAsker.addListener(g);
	}

	public void createCardPanel(int cards) {
		cp = new CardPanel(cards);
		add(cp, BorderLayout.SOUTH);
		cp.setVisible(true);
		// pack();
	}

	/**
	 * This updates the panel to hold a given set of cards and dice rolls
	 *
	 * This should be called when a new player is taking their turn
	 *
	 * @param cards
	 *            is a set of the current players cards
	 * @param dice
	 *            is an array of the images of the dice values they have rolled
	 */

	public void setCardPanel(Set<Card> cards, BufferedImage[] dice) {
		BufferedImage[] d = new BufferedImage[dice.length];
		BufferedImage[] c = new BufferedImage[cards.size()];

		// Creates dice image icons
		 for(int i = 0; i < dice.length; i++){
			 d[i] = (dice[i]);
		 }

		// Creates card image icons
		int count = 0;
		for (Card card : cards) {
			c[count++] = card.getImage();
		}

		cp.setCards(d, c);
	}

	private JMenuBar createMenu() {
		menuBar = new JMenuBar(); // creates menu bar

		menu = new JMenu("Game"); // creates first menu item
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

		menuBar.add(menu); // adds to menu bar

		JMenuItem menuItem = new JMenuItem("New Game");
		menu.add(menuItem);

		return menuBar;
	}

	@Override
	public void repaint() {
		// System.out.println(state);
		if (state != GameState.WELCOME) { // turns off the startButton after
											// title screen
			startButton.setVisible(false);
		}

		if (state != GameState.WELCOME && state != GameState.SETUP_INDIVIDUAL) {
			cp.repaint();
		}
		// start.repaint();
		board.repaint();

	}

	public int getCanvasSquareWidth() {
		return board.getSquareWidth();
	}

	public void updateCanvas(GameState state) {
		this.state = state;
		board.updateState(state);
	}

	/**
	 * Resizes an image
	 * @param image
	 * @param scaleX
	 * @param scaleY
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage image, int scaleX, int scaleY) {
		Image img = image.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(scaleX, scaleY, Image.SCALE_SMOOTH);
		resized.getGraphics().drawImage(img, 0, 0, null);
		return resized;
	}


	public static void main(String args[]) {
		new CluedoMainFrame(new GUI(new CluedoGame()));
	}
	
	public PlayerStartTurnBox getTurnBox(){
		return turnBox;
	}

	public void startTurnBox(GUI g, String playerName) {
		turnBox = new PlayerStartTurnBox();
		turnBox.addListener(g);
		turnBox.changePlayer(playerName);
		turnBox.setVisible(true);
	}

	public void drawPlayers(List<Player> players) {
		board.setPlayerPositions(players);		
	}

	public boolean isHighlighted(Square s) {
		return board.isHighlighted(s);
	}

	public void suggestionBox(GUI g) {
		if(suggBox == null){
			suggBox = new SuggestionBox();
		}		
			suggBox.setListener(g);
			suggBox.setVisible(true);		
	}
	
	public void accusationBox(GUI g){
		if(accBox == null){
			accBox = new AccusationBox();
		}
		accBox.setVisible(true);
	}
	
}
