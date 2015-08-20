package cluedo.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cluedo.control.CluedoGame.GameState;

public class FixFrame extends JFrame {

	public FixFrame(){
		super("Cluedo");
		setLayout(new BorderLayout()); // use border layour
		this.setSize(800, 800); 
		FixBoardPanel cbp = new FixBoardPanel();	
		add(cbp);
		
		this.setVisible(true);
	}
	
	public static void main(String args[]){
		new FixFrame();
	}
}
