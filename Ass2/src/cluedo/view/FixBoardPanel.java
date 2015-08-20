package cluedo.view;

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
	Image board;

	public FixBoardPanel(){
		height =  500;
		width = 500;
		
		
		File img = new File("title-screen.jpg");
		board = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		try {
			board = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void paint(Graphics g){
		g.drawImage(board, 0, 0, width, height, 0, 0, board.getWidth(null),
				board.getHeight(null), null);
	}
}
