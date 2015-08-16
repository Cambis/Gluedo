package cluedo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{
	
	Image dice;	

	public ControlPanel(){
		Dimension maxSize = new Dimension(600,100);
		setPreferredSize(maxSize);		
		dice = new ImageIcon("Dice.png").getImage();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
	}

	public void paint(Graphics g) {
		setOpaque(true);
		setBackground(Color.BLACK);
		g.drawImage(dice,0 ,0 , null);
	}
}
