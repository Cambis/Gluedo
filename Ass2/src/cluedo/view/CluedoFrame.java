package cluedo.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class CluedoFrame extends JFrame{
	
	CluedoCanvas canvas;
	
	public CluedoFrame(){
		super("Cluedo");
		canvas = new CluedoCanvas();
		setLayout(new BorderLayout()); // use border layour
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
	}

}
