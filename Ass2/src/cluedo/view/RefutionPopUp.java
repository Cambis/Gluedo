package cluedo.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RefutionPopUp {

	public RefutionPopUp(List<String> names){
		String result = null;

		if(names == null || names.isEmpty()){
			result = "No one can refute this suggestion";
		}

		else{		
			for(String name: names){
				result = result + name + " can refute this suggestion\n";
			}
		}

		JOptionPane.showMessageDialog(new JFrame(),
				result,	"Suggestion", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args){
		new RefutionPopUp(null);
	}

}
