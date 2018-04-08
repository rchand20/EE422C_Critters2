/**
 * Critter 4 will either walk run or not move. In the stats we determine the percent of time the critter walks, runs or 
 * moves out of the total. Critter 4 is generally a peaceful critter that only fights critter 1 or if it has enough energy.
 * In the statistics we also keep track of the number of Critter 4's as well as the number of encounters.
 */
package assignment5;

import java.util.*;

import assignment5.Critter.CritterShape;

public class Critter4 extends Critter {
	
	private boolean hasMoved;
	private static int encounters;
	private static int walking;
	private static int running;
	private static int staying;

	public Critter4() {
		hasMoved = false;
	}
	
	static {
		staying = 0;
		running = 0;
		walking = 0;
		encounters = 0;
	}
	
	/**
	 * This function performs one time step for Critter4
	 * The Critter will walk or run or stay
	 * The critter will reproduce with enough energy
	 */
	@Override
	public void doTimeStep() {
		int dir = getRandomInt(8);
		int move = getRandomInt(3);
		
		if(move == 0) {
			walking++;
			hasMoved = true;
			walk(dir);
		}
		else if(move == 1) {
			running++;
			hasMoved = true;
			run(dir);
		}
		else {
			staying++;
		}
		
		if (getEnergy() > 10) {
			Critter4 child = new Critter4();
			reproduce(child, Critter.getRandomInt(8));
		}
	}

	/**
	 * This function determines if a critter will fight in an encounter or not
	 * @param string of the opponent
	 * @return boolean true if the critter decides to fight and false if the critter decides not to fight
	 */
	@Override
	public boolean fight(String opponent) {
		encounters++;
		if(hasMoved) return true;
		if(opponent.equals("@")) return true;
		if(opponent.equals("1")) return true;
		if (getEnergy() > 50) return true;
		return false;
	}
	
	/**
	 * Returns the string representation of the critter
	 * @return the string representation of the critter
	 */
	public String toString() {
		return "4";
	}
	
	public void test (List<Critter> l) {
		
	}
	
	/**
	 * This function prints the stats for Critter4
	 * @param r1 a list of the Critter4's in the world
	 */
	public static String runStats(java.util.List<Critter> taras) {
		int total_moves = walking + running + staying;
		
		String ret = "";
		
		//Prints number of Tara's
		ret += ("" + taras.size() + " total Taras    ");
		//Prints total number of encounters for all Tara's
		ret += ("" + encounters + " total encounters    ");
		//Prints percent of moves walking, running or staying
		ret += (""+ (walking/total_moves)*100 + "% of moves walking    ");
		ret += (""+ (running/total_moves)*100 + "% of moves running    ");
		ret += (""+ (staying/total_moves)*100 + "% of moves staying");
		
		return ret;
	}

	@Override
	public CritterShape viewShape() { return CritterShape.CIRCLE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.RED; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.BROWN; }
}