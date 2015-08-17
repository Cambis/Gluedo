package cluedo.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import cluedo.model.board.Board;

public class CluedoCanvas extends Canvas {
	
	BufferedImage board;
	int width = 600;
	int height = 600;
	
	public CluedoCanvas() {
		this(new Board("cluedo.txt"));
	}
	
	public CluedoCanvas(Board b){
		File img = new File("clue3.png");		
		board = new BufferedImage(
			    width, height, BufferedImage.TYPE_INT_ARGB);	
		try {
			board = ImageIO.read(img);
		} catch (IOException e) {e.printStackTrace();}			
		
		Dimension maxSize = new Dimension(600,600);
		setPreferredSize(maxSize);
	}
	
	@Override
	public void paint(Graphics g){
		int x  = 25;
		int y = 23;
		g.drawImage(board, 0, 0, width, height, 0, 0, board.getWidth(),
			    board.getHeight(), null);
		g.setColor(Color.red);
		for(int i = 0; i < 24; i++){
			for(int j = 0; j < 25; j++){
				g.drawRect(i*x + 7 , j*y + 9 , x, y);
			}
		}
	}
}
