package cluedo.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A pop up notification to appear after a player has 
 * made an accusation. It has both a failure and success option
 * 
 * @author Cameron Bryers and Hannah Craighead
 *
 */

public class AccusationPopUp {
	
	public AccusationPopUp(boolean correct){
		
		// Successful Accusation		
		if(correct){
			JOptionPane.showMessageDialog(new JFrame(),
					"Congratulations, you have solved the murder!"
					,	"YOU WIN", JOptionPane.INFORMATION_MESSAGE);
		}
		// Unsuccessful Accusation
		else{
			JOptionPane.showMessageDialog(new JFrame(),
					"Oh no, that's not quite right"
					,	"YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
