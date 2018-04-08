/**
 * Critter 1 will always either randomly walk or run. If it's energy is greater than 77 then it will always reproduce
 * two children. This critter get's tired from running so it will only fight if it didn't run on the turn. For the
 * statistics on this critter we gather how many total critters of Critter1 type exist in the world, 
 * the number of times Critter1 has run versus walked and the total number of fights. 
 */
package assignment5;

import java.util.*;

import assignment5.Critter.CritterShape;

public class Critter1 extends Critter.TestCritter {
	
	private boolean ran;
	private static int numRuns;
	private static int numWalks;
	private static int numFights;
	
	public Critter1() {
		ran = false;
	}
	
	static {
		numRuns = 0;
		numWalks = 0;
		numFights = 0;
	}
	
	/**
	 * This function performs one time step for Critter 1
	 * The Critter will walk or run
	 * The critter will reproduce with enough energy
	 */
	@Override
	public void doTimeStep() {
		int dir= getRandomInt(8);
		int walkOrRun = getRandomInt(2);
	
		if(walkOrRun == 0) {		
			String c = look(dir, true);
			if(c == null) {
				walk(dir);//make critter walk decided by random number generator
				numWalks++;
			}

		}
		
		if(walkOrRun == 1) {
			String c = look(dir, false);
			if(c == null) {
				run(dir);//make critter run decided by random number generator
				numRuns++;
				ran = true;
			}
		}
		
		//if there is enough energy the critter will reproduce
		if (getEnergy() > 77) {
			Critter1 child1 = new Critter1();
			Critter1 child2 = new Critter1(); 
			//position of child 1
			int pos1 = Critter.getRandomInt(8);
			//position of child 2
			int pos2 = pos1 + 1;
			if(pos2 == 8) {
				pos2 = 0;
			}
			
			reproduce(child1, pos1);
			reproduce(child2, pos2);
		}
	}

	/**
	 * This function determines if a critter will fight in an encounter or not
	 * @param string of the opponent
	 * @return boolean true if the critter decides to fight and false if the critter decides not to fight
	 */
	@Override
	public boolean fight(String opponent) {
		numFights++;
		if(opponent.equals("@")) return true;
		if(ran) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Returns the string representation of the critter
	 * @return the string representation of the critter
	 */
	public String toString() {
		return "1";
	}
	
	/**
	 * This function prints the stats for Critter1
	 * @param r1 a list of the Critter1's in the world
	 */
	public static String runStats(java.util.List<Critter> r1) {
		String ret = "";
		ret = ("Total Number of Rishab1 Critters: " + r1.size());
		ret += (" ,Total Number of Runs: " + numRuns);
		ret += (" ,Total Number of Walks: " + numWalks);
		ret += (" ,Total Number of Fights: " + numFights);
		
		return ret;
	}
	
	public void test (List<Critter> l) {
		
	}
	@Override
	public CritterShape viewShape() { return CritterShape.STAR; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLACK; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.RED; }
}