package cluedo.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AccusationPopUp {
	
	public AccusationPopUp(boolean correct){
		
		if(correct){
			JOptionPane.showMessageDialog(new JFrame(),
					"Congratulations, you have solved the murder!"
					,	"YOU WIN", JOptionPane.INFORMATION_MESSAGE);
		}
		
		else{
			JOptionPane.showMessageDialog(new JFrame(),
					"Oh no, that's not quite right"
					,	"YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
