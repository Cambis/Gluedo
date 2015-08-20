package cluedo.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FixBoardPanel extends JPanel {

	int height;
	int width;
	BufferedImage board;
	BufferedImage titleScreen;

	public FixBoardPanel(){
		height =  500;
		width = 500;


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

		Dimension maxSize = new Dimension(height, width);
		setPreferredSize(maxSize);

	}

	public void paintBoard(Graphics g){
		// Draw the board image
		g.drawImage(board, 0, 0, width, height, 0, 0, board.getWidth(),
				board.getHeight(), null);
	}
	
	public void paintTitleScreen(Graphics g){
		// Draw the board image
		g.drawImage(titleScreen, 0, 0, width, height, 0, 0, titleScreen.getWidth(),
				titleScreen.getHeight(), null);	

	}

	@Override
	public void paint(Graphics g){
		paintTitleScreen(g);
	}
}
