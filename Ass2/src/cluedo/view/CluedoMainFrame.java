package cluedo.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.GameState;

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
		this.setSize(800, 800); // sets size
		setJMenuBar(createMenu()); // creates menu bar



		// Add game
		//game = new CluedoGame();

		// Add new game frame
//		start = new NewGameFrame(g) {
//
//			private static final long serialVersionUID = -7643791232912141407L;
//
////			@Override
////			public void actionPerformed(ActionEvent e) {
////				System.out.println(e.getActionCommand());
////				super.actionPerformed(e);
////				//game.setNumOfPlayers(super.getNumOfPlayers());
////
////			}
//		};

		//start.setVisible(true); // make it visible
		//add(start, BorderLayout.CENTER);

		// Adds cluedo board (JPanel, use this or the one above)
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

		// addButtons();

		// this.add(createMenu());

		// getContentPane().add(menuBar);

		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		repaint();

		//Sets up player frame and links to gui
		start = new NewGameFrame(g);
		start.setVisible(false);

		// Sets up TextField for getting a player's name
		// nameAsker = new PlayerInitFrame(g); // note: Link to Finish button
		// nameAsker.addKeyListener(g);
		// nameAsker.addActionListener(g);

	}
	
	public PlayerInitFrame getSetup(){
		return nameAsker;
	}

	// private void addButtons() {
	// // section for player controls
	// JPanel playerControls = new JPanel(new BorderLayout());
	// add(playerControls, BorderLayout.SOUTH);
	// }

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
	}
	
//	public void setCardPanel(){
//		cp.setCards(dice, cards);
//	}


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


