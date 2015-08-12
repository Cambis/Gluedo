package cluedo.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CluedoCanvas extends JPanel {
	
	Image board;
	
	public CluedoCanvas(){
		board = new ImageIcon("Cluedo-board.jpg").getImage();
		Dimension maxSize = new Dimension(600,600);
		setPreferredSize(maxSize);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
	}
	
	public void paint(Graphics g) {
		 g.drawImage(board, 0, 0, null);
	}

}
