package cluedo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Testing drawing the board as a JPanel, I think we should use this instead of
 * CluedoCanvas as we shouldn't be mixing awt and swing. Methods are taken from CluedoCanvas
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class CluedoBoardPanel extends JPanel implements MouseListener{

	private BufferedImage board;
	private int width, height;

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
	 * Set up the board
	 */
	private void init() {
		height =  500;
		width = 500;

		// Read the image
		File img = new File("Cluedo.jpg");
		board = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			board = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Dimension maxSize = new Dimension(height, width);
		setPreferredSize(maxSize);
	}

	@Override
	public void paint(Graphics g) {

		int x = 20;
		int y = 20;

		// Draw the board image
		g.drawImage(board, 0, 0, width, height, 0, 0, board.getWidth(),
				board.getHeight(), null);


		// Draw the grid
		g.setColor(Color.red);
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				g.drawRect(i * x, j * y, x, y);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Niggers");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Niggers");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
