package cluedo.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cluedo.control.CluedoGame.GameState;
import cluedo.model.board.Square;

/**
 * Testing drawing the board as a JPanel, I think we should use this instead of
 * CluedoCanvas as we shouldn't be mixing awt and swing. Methods are taken from CluedoCanvas
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class CluedoBoardPanel extends JPanel {

	private BufferedImage board, titleScreen;
	private RoomPolygonGenerator roomOutlines;
	private int width, height;
	private GameState state;
	private Set<Square> landSquares;
	
	int x = 21;
	int y = 21;
	
	public int getSquareWidth(){
		return x;
	}

	public CluedoBoardPanel() {
		init();
	}

	public CluedoBoardPanel(LayoutManager layout) {
		super(layout);
		init();
	}

	public CluedoBoardPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		init();
	}

	public CluedoBoardPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		init();
	}
	/**
	 * Updates the canvas so it knows the state of the game
	 * @param g
	 */

	public void updateState(GameState g){
		state = g;
	}

	/**
	 * Set up the board
	 */
	private void init() {
		height =  526;
		width = 526;
		landSquares = new HashSet<Square>();

		// Read the title screen image
		File img = new File("Cluedo.jpg");
		board = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
		
		// Generates rooms
		roomOutlines = new RoomPolygonGenerator(x);

		Dimension maxSize = new Dimension(height, width);
		setPreferredSize(maxSize);
	}

	public void paintBoard(Graphics g){
		// Draw the board image
		g.drawImage(board, 0, 0, width, height, 0, 0, board.getWidth(),
				board.getHeight(), null);
	}
	
	public void setLandingSquares(Set<Square> ls){
		landSquares = ls;
	}

	public void paintTitleScreen(Graphics g){
		// Draw the board image
		g.drawImage(titleScreen, 0, 0, width, height, 0, 0, titleScreen.getWidth(),
				titleScreen.getHeight(), null);	

	}

	public void paintGrid(Graphics g){
		// Draw the grid
		g.setColor(Color.red);
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				g.drawRect(i * x, j * y, x, y);
			}
		}
	}
	
	public void testRoomOutlines(Graphics g){
		g.setColor(Color.CYAN);
		((Graphics2D)g).setStroke(new BasicStroke(5f));
		g.drawPolygon(roomOutlines.getKitchen());
		g.drawPolygon(roomOutlines.getBallRoom());
		g.drawPolygon(roomOutlines.getConservatory());
	}

	public void paintLandingSquares(Set<Square> sq, Graphics g){
		int x = 20;
		int y = 20;
		g.setColor(Color.red);
		for(Square s : sq){
			g.drawRect(s.getX()*x, s.getY()*y, x, y);
		}
	}

	@Override
	public void paint(Graphics g) {
		if(true){
			paintBoard(g);
			testRoomOutlines(g);
		}
		
		else if(state.equals(GameState.WELCOME)){
			paintTitleScreen(g);
		}
		
		else if(state.equals(GameState.GENERAL)){
			paintBoard(g);
			paintLandingSquares(landSquares, g);
		}
		else{		
		paintBoard(g);
		testRoomOutlines(g);
		//paintGrid(g);
		}
	}	
	
	public static void main(String args[]){
		JFrame j = new JFrame();		
		j.setSize(550, 550);
		j.add(new CluedoBoardPanel());
		j.setVisible(true);
	}
}

