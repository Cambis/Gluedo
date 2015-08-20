package cluedo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cluedo.model.board.Square;

public class FixBoardPanel extends JPanel {

	private int height;
	private int width;
	private BufferedImage board;
	private BufferedImage titleScreen;
	private int squareWidth = 21;

	public FixBoardPanel(){
		height =  526;
		width = 526;

		// Loads in board image
		File img = new File("Cluedo.jpg");
		board = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			board = ImageIO.read(img);
		} catch (IOException e) {	e.printStackTrace();	}

		// Loads the title screen image
		File img2 = new File("title-screen.jpg");
		titleScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			titleScreen = ImageIO.read(img2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Sets preferred size of the JPanel 
		Dimension maxSize = new Dimension(height, width);
		setPreferredSize(maxSize);

	}
	
	/**
	 * Is responsible for drawing the image of the board
	 * @param g
	 */
	public void paintBoard(Graphics g){
		// Draw the board image
		g.drawImage(board, 0, 0, width, height, 0, 0, board.getWidth(),
				board.getHeight(), null);
	}
	
	/**
	 * Is responsible for drawing the title screen
	 * @param g
	 */
	public void paintTitleScreen(Graphics g){
		// Draw the title image
		g.drawImage(titleScreen, 0, 0, width, height, 0, 0, titleScreen.getWidth(),
				titleScreen.getHeight(), null);	

	}
	
	/**
	 * Draws a red grid over the entire board
	 * @param g
	 */
	public void paintGrid(Graphics g){
		// Draw the grid
		g.setColor(Color.red);
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				g.drawRect(i * squareWidth, j * squareWidth, squareWidth, squareWidth);
			}
		}
	}
	
	/**
	 * Draws the correct images on the JPanel depending
	 * on what state the game is in and to react to user 
	 * input
	 */
	@Override
	public void paint(Graphics g){
		paintBoard(g);
		//paintGrid(g);
	}
		
	public int getSquareWidth(){
		return squareWidth;
	}
	
	/**
	 * Paints the potential landing squares that a player can 
	 * move to. If a doorSquare is a potential landing area, it will highlight
	 * the entire room it belongs to
	 * 
	 * @param sq a set of landing squares to highlight
	 * @param g
	 */
	
	public void paintLandingSquares(Set<Square> sq, Graphics g){
		int x = 20;
		int y = 20;
		g.setColor(Color.red);
		for(Square s : sq){
			g.drawRect(s.getX()*x, s.getY()*y, x, y);
		}
	}
}
