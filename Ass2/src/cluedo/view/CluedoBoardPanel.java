package cluedo.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cluedo.control.CluedoGame.GameState;
import cluedo.model.Player;
import cluedo.model.board.Square;

/**
 * Testing drawing the board as a JPanel, I think we should use this instead of
 * CluedoCanvas as we shouldn't be mixing awt and swing. Methods are taken from CluedoCanvas
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class CluedoBoardPanel extends JPanel {

	private BufferedImage board, titleScreen; // images for the board and welcome screen
	private RoomPolygonGenerator roomOutlines; // contains room outlines
	private int width, height; 
	private GameState state; // state the game is in

	private Set<Square> landSquares;
	private Set<Polygon> rooms;
	private List<Player> players;

	int x = 21; // square width
	int y = 21; // square height



	public CluedoBoardPanel() {
		init();
	}	

	/**
	 * Initialise the board panel
	 */
	private void init() {
		// Set up of the panel
		height =  700;
		width = 700; 
		Dimension maxSize = new Dimension(height, width);
		setPreferredSize(maxSize);
		this.setBackground(Color.BLACK);

		landSquares = new HashSet<Square>();
		rooms = new HashSet<Polygon>();

		// Read the title screen image
		File img = new File("Cluedo2 (1).jpg");
		board = new BufferedImage(526, 526, BufferedImage.TYPE_INT_ARGB);
		try {
			board = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read the board image
		File img2 = new File("title-screen.jpg");
		titleScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			titleScreen = ImageIO.read(img2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Generates room outlines
		roomOutlines = new RoomPolygonGenerator(x);		
	}

	/**
	 * Updates the canvas so it knows the state of the game
	 * @param g
	 */

	public void updateState(GameState g){
		state = g;
	}

	/**
	 * Updates the canvas so it knows which squares on the board to 
	 * highlight
	 * @param ls is the set of corridor squares a player can move to
	 * @param rooms is the set of room names a player can move to
	 */

	public void setLandingSquares(Set<Square> ls, Set<String> rooms){
		// Clear previous entries from earlier turn
		landSquares.clear();
		this.rooms.clear();

		// Stores room outlines and squares
		landSquares = ls;
		for(String s : rooms){
			this.rooms.add(roomOutlines.getRoom(s));
		}		
	}

	/**
	 * Draws the board image
	 * @param g
	 */
	public void paintBoard(Graphics g){
		// Draw the board image
		g.drawImage(board, 0, 0, 526, 526, 0, 0, board.getWidth(),
				board.getHeight(), null);
	}

	/**
	 * Draws the title/welcome screen
	 * @param g
	 */

	public void paintTitleScreen(Graphics g){
		// Draw the board image
		g.drawImage(titleScreen, 0, 0, width, height, 0, 0, titleScreen.getWidth(),
				titleScreen.getHeight(), null);	
		// changes dimension to suit the board
		this.setPreferredSize(new Dimension(526,526));
	}

	/**
	 * Draws all of the room and square outlines 
	 * for where a player can move to
	 * @param g
	 */
	public void paintLandingSquares(Graphics g){		
		g.setColor(Color.CYAN);
		((Graphics2D)g).setStroke(new BasicStroke(5f));
		for(Square s : landSquares){
			g.drawRect(s.getY()*y, s.getX()*x, y, x);
		}
		for(Polygon p : rooms){
			System.out.println("Drawing room");
			g.drawPolygon(p);
		}
	}
	
	/**
	 * Paints the players tokens on the board
	 * @param players
	 * @param g
	 */
	public void drawPlayers(List<Player> players, Graphics g) {
		for(Player p : players){
			p.draw(g);
			g.drawImage(p.getImage(), p.getY() * 21, p.getX() * 20, null);
		}
	}
	
	/**
	 * Chooses what to paint, depending on the game state
	 * @param g
	 */
	
	@Override
	public void paintComponent(Graphics g) {		
		if(state.equals(GameState.WELCOME)){
			paintTitleScreen(g);
		}

		else if(state.equals(GameState.GENERAL)){
			paintBoard(g);
			drawPlayers(players,g);
			paintLandingSquares(g);
		}
		
		else{	
			System.out.println("Painting board");	
			paintBoard(g);
			if(!state.equals(GameState.SETUP_INDIVIDUAL)){
				drawPlayers(players,g);
			}
		}
	}	
	
	/**
	 * Stores the players currently on the board
	 * @param players2 are the players on the board
	 */
	public void setPlayerPositions(List<Player> players2) {
		players = players2;		
	}
	
	/**
	 * Checks to see if a given square is highlighted.
	 * Essentially checks if a square is valid to move to
	 * 
	 * @param s is the square selected
	 * @return a boolean indicating if the move is valid
	 */

	public boolean isHighlighted(Square s) {
		// If it is a valid corridor square
		if(landSquares.contains(s)){
			return true;
		}
		// Checks if the square resides in a highlighted room
		else{
			Point click = new Point(s.getY()*y, s.getX()*x);
			for(Polygon room : rooms){	
				System.out.println("Checking room");
				if(room.contains(click)){
					System.out.println("Found room");
					return true;
				}
			}
		}		

		return false;
	}
	
	/**
	 * Returns square width
	 * @return square width
	 */
	public int getSquareWidth(){
		return x;
	}
	
	public static void main(String args[]){
		JFrame j = new JFrame();		
		j.setSize(550, 550);
		j.add(new CluedoBoardPanel());
		j.setVisible(true);
	}	

}

