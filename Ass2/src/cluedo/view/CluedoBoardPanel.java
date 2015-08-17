package cluedo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
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
public class CluedoBoardPanel extends JPanel {

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
		height = width = 600;

		// Read the image
		File img = new File("clue3.png");
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

		int x = 25;
		int y = 23;

		// Draw the board image
		g.drawImage(board, 0, 0, width, height, 0, 0, board.getWidth(),
				board.getHeight(), null);


		// Draw the grid
		g.setColor(Color.red);
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 25; j++) {
				g.drawRect(i * x + 7, j * y + 9, x, y);
			}
		}
	}

}
