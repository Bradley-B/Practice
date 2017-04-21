package rng;

import java.util.Random;

public class Coordinates {
	
	public static void main(String args[]) {
		double longit = 40.595287;
		double latit = -74.605831;		
		
		for (int i = 0; i < 10; i++) {
			printCoordsNear(longit, latit);
		}
		
	}
	
	
	public static void printCoordsNear(double longit, double latit) {
		Random rand = new Random();
		
		double rOne = rand.nextDouble()-0.5;
		double rTwo = rand.nextDouble()-0.5;
		
		double newlongit = (rOne/8)+longit;
		double newlatit = (rTwo/8)+latit;
		
		System.out.println(newlongit+", "+newlatit);

	}
}
