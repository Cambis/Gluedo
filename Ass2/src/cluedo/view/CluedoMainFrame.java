package cluedo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class CluedoMainFrame extends JFrame{

	private static final long serialVersionUID = 6909931835454164833L;

	// Consistent components on the frame
	private CluedoBoardPanel board;
	private CardPanel cp;
	private JMenuBar menuBar;
	private JMenu menu;
	private JButton startButton;
	private PlayerInfoPanel playerInfoPanel;

	// Toggled components on the frame
	private PlayerStartTurnBox turnBox;
	private SuggestionBox suggBox;	
	private AccusationBox accBox;
	PlayerInitBox nameAsker;

	// Current game state
	private GameState state;	

	// Players checklists
	private List<PlayerCheckList> checklists;

	// Index for currently used checklist
	private int currentCheck = 0;

	public CluedoMainFrame(GUI g) {
		// Creates frame
		super("Cluedo");
		setLayout(new BorderLayout()); // use border layour
		this.setSize(700, 700); // sets size
		setJMenuBar(createMenu(g)); // creates menu bar
		this.setBackground(Color.BLACK); // sets backgrounnd to black

		// Adds board panel
		board = new CluedoBoardPanel();
		board.addMouseListener(g);		
		add(board, BorderLayout.CENTER);

		// Sets opening mode to welcome
		updateCanvas(GameState.WELCOME);		

		// Adds start button to panel
		createStartButton();
		startButton.addActionListener(g);
		add(startButton, BorderLayout.SOUTH);		

		// Add info panel
		playerInfoPanel = new PlayerInfoPanel(g);
		playerInfoPanel.setVisible(false);
		add(playerInfoPanel, BorderLayout.EAST);

		//Sets frame to close on exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets frame to visible
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		repaint();		
	}

	/**
	 * Gets the player initialiser
	 * @return player initiliaser box
	 */
	public PlayerInitBox getSetup() {
		return nameAsker;
	}

	/**
	 * Passes a set of possible landing squares and Rooms onto the
	 * CluedoBoardPanel for drawing
	 */

	public void drawValidMoves(Set<Square> lands, Set<String> rooms) {
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

	/**
	 * Creates the card panel and adds to frame
	 * @param cards is the number of cards a player can have
	 */
	public void createCardPanel(int cards) {
		cp = new CardPanel(cards);
		add(cp, BorderLayout.SOUTH);
		cp.setVisible(true);		
	}

	/**
	 * Adds a player and their cards to the card panel
	 * @param name of the player
	 * @param cards in the players hand
	 */
	public void addPlayerToCardPanel(String name, Set<Card> cards) {
		cp.addCards(name, cards);
	}

	/**
	 * Sets the dice roll for a player
	 * @param name
	 * @param dice
	 */

	public void setCardPanel(String name, BufferedImage[] dice) {
		cp.setPlayer(name, dice);
	}

	/**
	 * Toggles card display on and off
	 */

	public void showCards() {
		cp.toggleCards();
	}

	/**
	 * Hides card display
	 */
	public void hideCards() {
		cp.hideCards();
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
		for (int i = 0; i < dice.length; i++) {
			d[i] = (dice[i]);
		}

		// Creates card image icons
		int count = 0;
		for (Card card : cards) {
			c[count++] = card.getImage();
		}
	}

	/**
	 * Creates menu 
	 * 
	 * @param g is the GUI class which listens to see
	 * if the menu is selected
	 * 
	 * @return menu
	 */
	private JMenuBar createMenu(GUI g) {
		menuBar = new JMenuBar(); // creates menu bar

		menu = new JMenu("Game"); // creates first menu item
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

		menuBar.add(menu); // adds to menu bar

		JMenuItem menuItem = new JMenuItem("New Game");
		menuItem.addActionListener(g);
		menu.add(menuItem);

		return menuBar;
	}
	
	/**
	 * Repaints the various components on the frame
	 */

	@Override
	public void repaint() {
		
		if (state != GameState.WELCOME) { // turns off the startButton after title screen			
			startButton.setVisible(false);
		}

		if (state != GameState.WELCOME && state != GameState.SETUP_INDIVIDUAL) {
			System.out.println("Is past set up");
			playerInfoPanel.setVisible(true);

			playerInfoPanel.repaint();
			cp.repaint();
		}
		
		board.repaint();
	}
	
	/**
	 * Returns the width of a canvas square
	 * @return canvas square width
	 */
	
	public int getCanvasSquareWidth() {
		return board.getSquareWidth();
	}
	
	/**
	 * Updates the state in the frame and board panel
	 * @param state to update to
	 */
	
	public void updateCanvas(GameState state) {
		this.state = state;
		board.updateState(state);
	}

	/**
	 * Resizes an image
	 * 
	 * @param image
	 * @param scaleX
	 * @param scaleY
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage image, double scaleX, double scaleY) {

		BufferedImage resized = new BufferedImage((int) (image.getWidth(null) * scaleX),
				(int) (image.getHeight(null) * scaleY), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = (Graphics2D) resized.getGraphics();

		g.scale(scaleX, scaleY);

		g.drawImage(image, 0, 0, null);
		g.dispose();
		return resized;
	}

	/**
	 * Returns the start turn box
	 * @return start turn box
	 */

	public PlayerStartTurnBox getTurnBox() {
		return turnBox;
	}
	
	/**
	 * Creates a new start turn box 
	 * @param g is the GUI class which listens to the turn box
	 * @param playerName
	 */
	
	public void startTurnBox(GUI g, String playerName) {
		turnBox = new PlayerStartTurnBox();
		turnBox.addListener(g);
		turnBox.changePlayer(playerName);
		turnBox.setVisible(true);
	}
	
	/**
	 * Calls draw players on the board panel
	 * @param players
	 */
	
	public void drawPlayers(List<Player> players) {
		board.setPlayerPositions(players);
	}
	
	/**
	 * Checks if a square is highlighted on the board panel
	 * @param s is the given square
	 * @return boolean indicating if the square is highlighted
	 */
	
	public boolean isHighlighted(Square s) {
		return board.isHighlighted(s);
	}

	/**
	 * Makes a new suggestion box, or
	 * sets the old one visible
	 * @param g is the GUI class which listens to it
	 */
	
	public void suggestionBox(GUI g) {
		if (suggBox == null) {
			suggBox = new SuggestionBox();
		}
		suggBox.setListener(g);
		suggBox.setVisible(true);
	}
 
	/**
	 * Makes a new accusation box, or
	 * sets the old one visible
	 * @param g is the GUI class which listens to it
	 */
	
	public void accusationBox(GUI g) {
		if (accBox == null) {
			accBox = new AccusationBox();
		}
		accBox.setListener(g);
		accBox.setVisible(true);
	}
	
	/**
	 * Returns a player suggestion
	 * @return player suggestion
	 */

	public String[] getSuggestion() {
		return suggBox.getAnswers();
	}
	
	/**
	 * Turns the suggestion box off
	 * Also resets it for next time
	 * @param e is the action event that ended its use
	 */

	public void turnSuggOff(ActionEvent e) {
		// reset for next time
		suggBox.setWeapon(e);
		suggBox.changeToSuspects();
		suggBox.setVisible(false);

	}
	
	/**
	 * Turns the accusation box off
	 * Also resets it for next time
	 * @param e is the action event that ended its use
	 */

	public void turnAccOff(ActionEvent e) {
		// reset for next time
		accBox.setRoom(e);
		accBox.changeToSuspects();
		accBox.setVisible(false);

	}
	
	/**
	 * Gets accusation made by a player
	 * @return accusation
	 */

	public String[] getAccusation() {
		return accBox.getAnswers();
	}
	
	/**
	 * Sets next players related graphics.
	 * This includes images for their card panel
	 * and their own checklist
	 * 
	 * @param playerImage is their image
	 * @param playerName is their name
	 */

	public void setNextPlayer(BufferedImage playerImage, String playerName) {
		checklists.get(currentCheck).setVisible(false);
		playerInfoPanel.setPlayer(playerImage, playerName);
		if (currentCheck == checklists.size() - 1) {
			currentCheck = 0;
		} else {
			currentCheck++;
		}
	}
	
	/**
	 * Creates a checklist for each player
	 * @param n is the number of players
	 * @param gui 
	 */

	public void createCheckLists(int n, GUI gui) {
		checklists = new ArrayList<PlayerCheckList>();
		for (int i = 0; i < n; i++) {
			checklists.add(new PlayerCheckList());
		}
	}

	/**
	 * Turns off the current checklist
	 */
	
	public void turnOffChecklist() {
		checklists.get(currentCheck).setVisible(false);	
		repaint();
	}	

	/**
	 * Toggles on/off the current players checklist
	 */
	
	public void seeChecklist() {
		boolean on = checklists.get(currentCheck).isVisible();
		checklists.get(currentCheck).setVisible(!on);
		repaint();

	}
	
	public static void main(String args[]) {
		new CluedoMainFrame(new GUI(new CluedoGame()));
	}
}
