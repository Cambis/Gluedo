package cluedo.view;

import java.awt.Polygon;

public class RoomPolygonGenerator {
	
	Polygon diningRoom, hall, kitchen, conservatory,
	lounge, study, swimmingPool, library, billiardsRoom, ballRoom;
	
	int[] xPoints, yPoints;
	
	private int size;
	
	public RoomPolygonGenerator(int size){
		this.size = size;
		
		makeKitchen();
	}
	
	public void makeKitchen(){
		xPoints =  new int[]{0, 6*size, 6*size, 1*size, 1*size, 0*size};
		yPoints = new int[]{1*size, 1*size, 6*size, 6*size, 5*size, 5*size};
		kitchen = new Polygon(xPoints, yPoints, xPoints.length);
	}
	
	public Polygon getKitchen(){
		return kitchen;
	}

}
