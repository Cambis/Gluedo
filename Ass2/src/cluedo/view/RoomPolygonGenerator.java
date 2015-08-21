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
		makeBallRoom();
		makeConservatory();
		makeBilliardRoom();
		makeLibrary();
		makeStudy();
	}
	
	private void makeStudy() {
		xPoints =  new int[]{17*size, 24*size, 24*size, 18*size, 18*size,
				17*size};
		yPoints = new int[]{21*size, 21*size, 25*size, 25*size, 24*size, 24*size};
		study = new Polygon(xPoints, yPoints, xPoints.length);
		
	}

	private void makeLibrary() {
		xPoints =  new int[]{18*size,23*size,23*size, 24*size, 24*size,
				23*size, 23*size, 18*size, 18*size, 17*size, 17*size, 18*size };
		yPoints = new int[]{14*size, 14*size, 15*size, 15*size, 18*size,
				18*size, 19*size, 19*size, 18*size, 18*size, 15*size, 15*size };
		library = new Polygon(xPoints, yPoints, xPoints.length);
	}

	private void makeBilliardRoom() {
		xPoints =  new int[]{18*size,24*size, 24*size, 18*size};
		yPoints = new int[]{8*size, 8*size, 13*size, 13*size};
		billiardsRoom = new Polygon(xPoints, yPoints, xPoints.length);
		
	}

	public void makeKitchen(){
		xPoints =  new int[]{0, 6*size, 6*size, 1*size, 1*size, 0*size};
		yPoints = new int[]{1*size, 1*size, 7*size, 7*size, 6*size, 6*size};
		kitchen = new Polygon(xPoints, yPoints, xPoints.length);
	}
	
	public void makeBallRoom(){
		xPoints =  new int[]{10*size, 14*size, 14*size, 16*size, 16*size, 8*size, 
				8*size, 10*size};
		yPoints = new int[]{0,0,2*size,2*size, 8*size, 8*size, 2*size, 2*size};
		ballRoom = new Polygon(xPoints, yPoints, xPoints.length);
	}
	
	public void makeConservatory(){
		xPoints =  new int[]{18*size,24*size, 24*size, 23*size,
				23*size, 19*size, 19*size, 18*size};
		yPoints = new int[]{size, size, 5*size, 5*size, 6*size, 6*size,
				5*size, 5*size};
		conservatory = new Polygon(xPoints, yPoints, xPoints.length);
	}
	
	// Accessors
	
	public Polygon getKitchen(){
		return kitchen;
	}
	
	public Polygon getBallRoom(){
		return ballRoom;
	}

	public Polygon getDiningRoom() {
		return diningRoom;
	}

	public Polygon getHall() {
		return hall;
	}

	public Polygon getConservatory() {
		return conservatory;
	}

	public Polygon getLounge() {
		return lounge;
	}

	public Polygon getStudy() {
		return study;
	}

	public Polygon getSwimmingPool() {
		return swimmingPool;
	}

	public Polygon getLibrary() {
		return library;
	}

	public Polygon getBilliardsRoom() {
		return billiardsRoom;
	}
	
}
