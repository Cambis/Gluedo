package cluedo.model.gameObjects;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Represents the dice in the game
 *
 * @author Cameron Bryers and Hannah Craighead
 *
 */
public class Dice {
	
	private BufferedImage[] rollImages;

	private int value;
	
	public Dice(){
		rollImages = new BufferedImage[6];
		
		for(int i = 1; i <= 6; i++){
			File img = new File("Dice" + i + ".png");
			BufferedImage die = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
			try {
				die = ImageIO.read(img);
			} catch (IOException e) {e.printStackTrace();}
			rollImages[i-1] = die;
		}
	}

	public void roll() {
		value = new Random().nextInt(7);
	}

	public final int getValue() {
		return value;
	}
	
	public BufferedImage getRollImage(){
		return rollImages[value];
	}
}
