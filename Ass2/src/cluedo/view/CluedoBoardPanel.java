package cluedo.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cluedo.control.CluedoGame.GameState;
import cluedo.model.Player;
import cluedo.model.board.Square;

/**
 * Testing drawing the board as a JPanel, I think we should use this instead of
 * CluedoCanvas as we shouldn't be mixing awt and swing. Methods are taken from
 * CluedoCanvas
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class CluedoBoardPanel extends JPanel {

	private JLabel[] playerIcons;

	private BufferedImage board, titleScreen; // images for the board and
												// welcome screen
	private RoomPolygonGenerator roomOutlines; // contains room outlines
	private int width, height;
	private GameState state; // state the game is in

	private Set<Square> landSquares;
	private Set<Polygon> rooms;
	private List<Player> players;

	int x = 21; // square width
	int y = 21; // square height

	public int getSquareWidth() {
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
	 * 
	 * @param g
	 */

	public void updateState(GameState g) {
		state = g;
	}

	/**
	 * Set up the board
	 */
	private void init() {

		height = 700;
		width = 700;
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

		// Generates rooms
		roomOutlines = new RoomPolygonGenerator(x);

		Dimension maxSize = new Dimension(height, width);
		setPreferredSize(maxSize);
		this.addMouseListener(new MouseHandler());
	}

	public void paintBoard(Graphics g) {
		// Draw the board image
		g.drawImage(board, 0, 0, 526, 526, 0, 0, board.getWidth(), board.getHeight(), null);
	}

	/**
	 * Updates the canvas so it knows which squares on the board to highlight
	 * 
	 * @param ls
	 *            is the set of corridor squares a player can move to
	 * @param rooms
	 *            is the set of room names a player can move to
	 */
	public void setLandingSquares(Set<Square> ls, Set<String> rooms) {
		// Clear previous entries from earlier turn
		landSquares.clear();
		this.rooms.clear();

		landSquares = ls;
		for (String s : rooms) {
			this.rooms.add(roomOutlines.getRoom(s));
		}
		System.out.println("Panel has " + this.rooms.size() + " rooms polygons to draw");
	}

	public void paintTitleScreen(Graphics g) {
		// Draw the board image
		g.drawImage(titleScreen, 0, 0, width, height, 0, 0, titleScreen.getWidth(), titleScreen.getHeight(), null);
		this.setPreferredSize(new Dimension(526, 526));

	}

	public void paintGrid(Graphics g) {
		// Draw the grid
		g.setColor(Color.red);
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				g.drawRect(i * x, j * y, x, y);
			}
		}
	}

	public void testRoomOutlines(Graphics g) {
		g.setColor(Color.CYAN);
		((Graphics2D) g).setStroke(new BasicStroke(5f));
		g.drawPolygon(roomOutlines.getKitchen());
		g.drawPolygon(roomOutlines.getBallRoom());
		g.drawPolygon(roomOutlines.getConservatory());
		g.drawPolygon(roomOutlines.getStudy());
		g.drawPolygon(roomOutlines.getLibrary());
		g.drawPolygon(roomOutlines.getBilliardsRoom());
		g.drawPolygon(roomOutlines.getLounge());
		g.drawPolygon(roomOutlines.getHall());
		g.drawPolygon(roomOutlines.getDiningRoom());
		g.drawPolygon(roomOutlines.getSwimmingPool());
	}

	public void paintLandingSquares(Graphics g) {
		g.setColor(Color.CYAN);
		((Graphics2D) g).setStroke(new BasicStroke(5f));
		for (Square s : landSquares) {
			g.drawRect(s.getY() * y, s.getX() * x, y, x);
		}
		for (Polygon p : rooms) {
			System.out.println("Drawing room");
			g.drawPolygon(p);
		}
	}

	/**
	 * Chooses what to paint, depending on the game state
	 * 
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (state.equals(GameState.WELCOME)) {
			paintTitleScreen(g);
		}

		else if (state.equals(GameState.GENERAL)) {
			paintBoard(g);
			drawPlayers(players, g);
			paintLandingSquares(g);
		} else {
			System.out.println("Painting board");
			paintBoard(g);
			if (!state.equals(GameState.SETUP_INDIVIDUAL)) {
				drawPlayers(players, g);
			}
			// testRoomOutlines(g);
			// paintGrid(g);
		}
	}

	public void drawPlayers(List<Player> players, Graphics g) {
		for (Player p : players) {
			p.draw(g);
			// g.drawImage(p.getImage(), p.getY() * 21, p.getX() * 20, null);
		}

		for (int i = 0; i < players.size(); i++) {
			playerIcons[i].repaint(players.get(i).getY() * 21, players.get(i).getX() * 20,
					playerIcons[i].getIcon().getIconWidth(), playerIcons[i].getIcon().getIconHeight());

			playerIcons[i].getIcon().paintIcon(this, g, players.get(i).getY() * 21, players.get(i).getX() * 20);
			// playerIcons[i].rep
		}
	}

	/**
	 * Stores the players currently on the board
	 * 
	 * @param players2
	 *            are the players on the board
	 */
	public void setPlayerPositions(List<Player> players2) {
		players = players2;
		playerIcons = new JLabel[players2.size()];

		for (int i = 0; i < playerIcons.length; i++) {
			playerIcons[i] = new JLabel();
			playerIcons[i].setHorizontalAlignment(JLabel.CENTER);
			playerIcons[i].setIcon(new ImageIcon(players.get(i).getImage()));
			playerIcons[i].addMouseListener(new MouseHandler());
		}

	}

	/**
	 * Checks to see if a given square is highlighted. Essentially checks if a
	 * square is valid to move to
	 * 
	 * @param s
	 *            is the square selected
	 * @return a boolean indicating if the move is valid
	 */

	public boolean isHighlighted(Square s) {
		if (landSquares.contains(s)) {
			return true;
		} else {
			Point click = new Point(s.getY() * y, s.getX() * x);
			for (Polygon room : rooms) {
				System.out.println("Checking room");
				if (room.contains(click)) {
					System.out.println("Found room");
					return true;
				}
			}
		}

		return false;
	}

	private class MouseHandler extends MouseAdapter {

		private boolean inside = false;
		private MouseThread mouse = new MouseThread();
		
		@Override
		public void mouseEntered(MouseEvent e) {
			inside = true;
			mouse.run();
			System.out.println("In here");
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("In here");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			inside = false;
			if (mouse.isAlive())
				mouse.stop();
		}
	}

	private class MouseThread extends Thread {

		private Point mousePos;
		
		public Point getMouseLocation() {
			return mousePos;
		}

		public void run() {
			while (true) {
			mousePos = MouseInfo.getPointerInfo().getLocation();
			System.out.println(mousePos.toString());
			}
		}
	}
}
