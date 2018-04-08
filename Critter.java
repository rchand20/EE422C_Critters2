package assignment5;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static Critter occupied[][] = new Critter[Params.world_height][Params.world_width];
	private static Critter occupiedFight[][] = new Critter[Params.world_height][Params.world_width];

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	
	protected final String look(int direction, boolean steps) {
		int checkX = 0;
		int checkY = 0;
		
		if(steps) {
			if(direction==0) {
				checkX = moveX(2);
			}

			if(direction==1) {
				checkX = moveX(2);
				checkY = moveY(-2);
			}

			if(direction==2) {
				checkY = moveY(-2);
			}

			if(direction==3) {
				checkX = moveX(-2);
				checkY = moveY(-2);
			}

			if(direction==4) {
				checkX = moveX(-2);
			}

			if(direction==5) {
				checkX = moveX(-2);
				checkY = moveY(2);
			}

			if(direction==6) {
				checkY = moveY(2);
			}

			if(direction==7) {
				checkX = moveX(2);
				checkY = moveY(2);
			}
		}
		
		else {
			if(direction==0) {
				checkX = moveX(1);
			}

			if(direction==1) {
				checkX = moveX(1);
				checkY = moveY(-1);
			}

			if(direction==2) {
				checkY = moveY(-1);
			}

			if(direction==3) {
				checkX = moveX(-1);
				checkY = moveY(-1);
			}

			if(direction==4) {
				checkX = moveX(-1);
			}

			if(direction==5) {
				checkX = moveX(-1);
				checkY = moveY(1);
			}

			if(direction==6) {
				checkY = moveY(1);
			}

			if(direction==7) {
				checkX = moveX(1);
				checkY = moveY(1);
			}
		}
		
		if(inFight) {
			if(occupiedFight[checkY][checkX] != null) {
				return occupiedFight[checkY][checkX].toString();
			}
		}
		else {
			if(occupied[checkY][checkX] != null) {
				return occupied[checkY][checkX].toString();
			}
		}

		return null;
	}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	private int prevX;
	private int prevY;
	
	private boolean inFight;
	
	protected final void walk(int direction) {
		if(direction==0) {
			//prevX = x_coord;
			x_coord = moveX(1);
			
		}

		if(direction==1) {
		//	prevX = x_coord;
			//prevY = y_coord;
			
			x_coord = moveX(1);
			y_coord = moveY(-1);
		}

		if(direction==2) {
		//	prevY = y_coord;
			
			y_coord = moveY(-1);
		}

		if(direction==3) {
		//  prevX = x_coord;
		//	prevY = y_coord;
			
			x_coord = moveX(-1);
			y_coord = moveY(-1);
		}

		if(direction==4) {
			
			prevX = x_coord;
			x_coord = moveX(-1);
		}

		if(direction==5) {
		//	prevX = x_coord;
		//	prevY = y_coord;
			
			x_coord = moveX(-1);
			y_coord = moveY(1);
		}

		if(direction==6) {
		//	prevY = y_coord;
			
			y_coord = moveY(1);
		}

		if(direction==7) {
	//		prevX = x_coord;
		//	prevY = y_coord;
			
			x_coord = moveX(1);
			y_coord = moveY(1);
		}
		energy-= Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		if(direction==0) {
		//	prevX = x_coord;
			x_coord = moveX(2);
		}

		if(direction==1) {
			
		//	prevX = x_coord;
		//	prevY = y_coord;
			
			x_coord = moveX(2);
			y_coord = moveY(-2);
		}

		if(direction==2) {
		//	prevY = y_coord;
			
			y_coord = moveY(-2);
		}

		if(direction==3) {
		//	prevX = x_coord;
		//	prevY = y_coord;
			
			x_coord = moveX(-2);
			y_coord = moveY(-2);
		}

		if(direction==4) {
			prevX = x_coord;
			x_coord = moveX(-2);
		}

		if(direction==5) {
		//	prevX = x_coord;
		//	prevY = y_coord;
			x_coord = moveX(-2);
			y_coord = moveY(2);
		}

		if(direction==6) { 
			prevY = y_coord;
			y_coord = moveY(2);
		}

		if(direction==7) { 
			prevX = x_coord;
			prevY = y_coord;
			x_coord = moveX(2);
			y_coord = moveY(2);
		}

		energy-= Params.run_energy_cost;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if(getEnergy() < Params.min_reproduce_energy) {
			return;
		}

		offspring.energy = energy/2;
		energy = energy/2 + 1;


		if(direction==0) {
			offspring.x_coord = this.moveX(1);
			offspring.y_coord = this.y_coord;
		}

		if(direction==1) {
			offspring.x_coord = this.moveX(1);
			offspring.y_coord = this.moveY(-1);
		}

		if(direction==2) {
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.moveY(-1);
		}

		if(direction==3) {
			offspring.x_coord = this.moveX(-1);
			offspring.y_coord = this.moveY(-1);
		}

		if(direction==4) {
			offspring.x_coord = this.moveX(-1);
			offspring.y_coord = this.y_coord;
		}

		if(direction==5) {
			offspring.x_coord = this.moveX(-1);
			offspring.y_coord = this.moveY(1);
		}

		if(direction==6) {
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.moveY(1);
		}

		if(direction==7) {
			offspring.x_coord = this.moveX(1);
			offspring.y_coord = this.moveY(1);
		}

		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	public static void worldTimeStep() {
		
		//empty the occupied and occupied fight arrays
		for(int r = 0; r < Params.world_height; r++) {
			for(int c = 0; c < Params.world_width; c++) {
				occupied[r][c] = null;
				occupiedFight[r][c] = null;
			}
		}
		
		
		for(Critter c : population) {
			
			//fill the occupied array and sets all inFight booleans to false
			occupied[c.y_coord][c.x_coord] = c;
			c.inFight = false;
			
			c.doTimeStep();
			
			//after each do time step update the inFight occupied array
			occupiedFight[c.y_coord][c.x_coord] = c;
		}

		for(Critter a : population) {
			for(Critter b : population) {
				if(a == b) {
					continue;
				}
				//checks if two critters are in the same position and must have an encounter
				if((a.x_coord == b.x_coord) && (a.y_coord == b.y_coord)) {
					//checks if both critters are alive for the encounter
					if(a.energy > 0 && b.energy > 0) {
						
						a.inFight = true;
						b.inFight = true;
						
						boolean aFight = a.fight(b.toString());//true if a chooses to fight
						boolean bFight = b.fight(a.toString());//true if b chooses to fight

						//proceeds with the fight if critter a or be did not choose to run away instead of fight
						if((a.x_coord == b.x_coord) && (a.y_coord == b.y_coord)) {
							int aRoll=0;
							if(aFight) {
								aRoll= getRandomInt(a.energy);//A's roll in a fight
							}

							int bRoll=0;
							if(bFight) {
								bRoll= getRandomInt(b.energy);//B's roll in a fight
							}
							
							//if they are tied uses the random nnumber generator to determine who will actually win
							if(aRoll==bRoll) {
								int tie= getRandomInt(2);
								if(tie==0) {
									//a won
									a.energy += b.energy/2;
									b.energy = 0;
								}
								else {
									//b won
									b.energy += a.energy/2;
									a.energy = 0;
								}
							}
							
							//a wins
							else if(aRoll> bRoll){
								a.energy += b.energy/2;
								b.energy = 0;
							}

							//b wins
							else {
								b.energy += a.energy/2;
								a.energy = 0;
							}
						}
					}
				}
			}
		}

		//deducts rest energy cost for all critters
		for(Critter c:population) {
			c.energy -= Params.rest_energy_cost;
		}

		//produces more algae
		for (int i  = 0; i < Params.refresh_algae_count; i += 1) {
			try {
				Critter.makeCritter("Algae");
			} catch (InvalidCritterException e) {

			}
		}

		//adds the babies to the population 
		for(Critter c : babies) {
			population.add(c);
		}

		java.util.ArrayList<Critter> temp = new java.util.ArrayList<Critter>(babies);
		babies.removeAll(temp);//clears all babies from the babies list

		//removes all dead critters
		java.util.ArrayList<Critter> toRemove = new java.util.ArrayList<Critter>();
		for(Critter c: population) {
			if(c.energy <= 0) {
				toRemove.add(c);
			}
		}

		for(Critter c: toRemove) {
			population.remove(c);
		}
		
	}
	
	
	static int numRows = Params.world_height;
	static int numCols = Params.world_width;
	static double size = Main.grid.getWidth()/numCols;
	public static void displayWorld() {
		
		Main.grid.getChildren().clear();
	
		for (int r = 0; r < numRows; r++)
			for (int c = 0; c < numCols; c++) {
				Shape s = new Rectangle(size, size);
				s.setFill(null);
				s.setStroke(Color.BLACK);
				Main.grid.add(s, c, r);
			}
		/*
		Shape sTest = new Rectangle(size,size);
		sTest.setFill(Color.RED);
		Main.grid.add(sTest, 2, 2);
		*/

		
		for(Critter c : population) {
			Shape s = getIcon(c);
			Main.grid.add(s, c.x_coord, c.y_coord);
			//Main.grid.add(s, 100, 100);
		}
	}
	/* Alternate displayWorld, where you use Main.<pane> to reach into your
	   display component.
	   // public static void displayWorld() {}
	*/
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	
	
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		String critterName = "assignment5." + critter_class_name;
		Class<?> newCritter= null;
		try {
			newCritter = Class.forName(critterName);
			Constructor<?> constructor = null;
			constructor= newCritter.getConstructor();
			Critter c = (Critter) newCritter.newInstance();//creates a new object of a specified constructor

			c.x_coord = getRandomInt(Params.world_width);
			c.y_coord = getRandomInt(Params.world_height);
			//prevX = c.x_coord;
			//prevY = c.y_coord;
			c.energy = Params.start_energy;
			occupied[c.y_coord][c.x_coord] = c;

			population.add(c);

		} catch (ClassNotFoundException cnfe) {
			throw new InvalidCritterException(critter_class_name);
		} catch (InstantiationException iE) {
			throw new InvalidCritterException(critter_class_name);
		} catch(IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		Class<?> critter = null;
		String critterName = "assignment5." + critter_class_name;

		try {
			critter = Class.forName(critterName);
		} catch(Exception e) {

		}

		List<Critter> result = new java.util.ArrayList<Critter>();

		for(Critter c :population) {
			if(critter.isInstance(c)) {
				result.add(c);
			}
		}

		return result;
	}
	
	public static String runStats(List<Critter> critters) {
		String ret = "";
		
		ret += ("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			ret += (prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		return ret;	
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
	}
	
	/**
	 * This function determines the x position after moving either right or left
	 * @param steps where -1 is walking left, -2 is running left, 1 is walking right and 2 is running right
	 * @return the X position after moving
	 */
	private int moveX(int steps) {
		int width = Params.world_width;
		
		if(x_coord == 0) {
			if(steps<0) {
				return (Params.world_width + steps);
			}
			else {
				return (x_coord + steps);
			}	
		}
		
		if(x_coord==1) {
			if(steps == -2) {
				return Params.world_width - 1;
			}
			else {
				return x_coord + steps;
			}
		}
		
		if(x_coord == width - 1) {
			if(steps>0) {
				return steps - 1;
			}
			else {
				return x_coord + steps;
			}
		}
		
		if(x_coord == width - 2) {
			if(steps == 2) {
				return 1;
			}
			else {
				return x_coord + steps;
			}
		}
		
		return x_coord + steps;
	}
	
	/**
	 * This function determines the y position after moving either up or down
	 * @param steps where -1 is walking down, -2 is running down, 1 is walking up and 2 is running up
	 * @return the Y position after moving
	 */
	private int moveY(int steps) {
		
		int height = Params.world_height;
		
		
		if(y_coord == 0) {
			if(steps<0) {
				return (Params.world_height + steps);
			}
			else {
				return (y_coord + steps);
			}	
		}
		
		if(y_coord==1) {
			if(steps == -2) {
				return Params.world_height - 1;
			}
			else {
				return y_coord + steps;
			}
		}
		
		if(y_coord == height - 1) {
			if(steps>0) {
				return steps - 1;
			}
			else {
				return y_coord + steps;
			}
		}
		
		if(y_coord == height - 2) {
			if(steps == 2) {
				return 1;
			}
			else {
				return y_coord + steps;
			}
		}
		
		return y_coord + steps;
	}
	
	public static Shape getIcon(Critter c) {
		Shape s = null;
		
		if(c.viewShape() == CritterShape.SQUARE) {
			s = new Rectangle(size - 2, size - 2);
			s.setFill(c.viewFillColor());
			s.setStroke(c.viewOutlineColor());
			s.setStrokeWidth(2.0);
			//g.add(s, c.x_coord, c.y_coord);
			return s;

		}
		
		if(c.viewShape() == CritterShape.CIRCLE) {
			s = new Circle(size/2 - 2);
			s.setFill(c.viewFillColor());
			//g.add(s, c.x_coord, c.y_coord);
			s.setStroke(c.viewOutlineColor());
			s.setStrokeWidth(2.0);
			return s;
		}
		
		if(c.viewShape() == CritterShape.TRIANGLE) {
			
			Polygon p = new Polygon();
			p.getPoints().addAll(new Double[] {
					(double) (c.x_coord*size + size/2), (double) (c.y_coord*size + 4),
					(double) (c.x_coord*size + 4), (double) ((c.y_coord + 1)*size - 4), 
					(double) ((c.x_coord + 1)*size - 4), (double) ((c.y_coord + 1)*size - 4)
			});
			p.setFill(c.viewFillColor());
			p.setStroke(c.viewOutlineColor());
			p.setStrokeWidth(2.0);
			//g.add(p, c.x_coord, c.y_coord);
			return p;
		}
		
		if(c.viewShape() == CritterShape.STAR) {
			Polygon p = new Polygon();
			p.getPoints().addAll(new Double[] {
					(double)(c.x_coord*size + (size/2)), (double) (c.y_coord*size + 2),
					(double)(c.x_coord*size + (size/3)), (double) (c.y_coord*size + (size/3)),
					(double)(c.x_coord*size + 2), (double) (c.y_coord*size + (size/3)),
					(double)(c.x_coord*size + (size/3)), (double)(c.y_coord*size + (size/2)),
					(double)(c.x_coord*size + 2), (double)((c.y_coord + 1)*size - 2),
					(double)(c.x_coord*size + (size/2)), (double) (c.y_coord*size + (2*size/3)),
					(double)((c.x_coord + 1)*size - 2), (double)((c.y_coord + 1)*size - 2),
					(double)(c.x_coord*size + (2*size/3)), (double)(c.y_coord*size + (size/2)),
					(double)((c.x_coord + 1)*size - 2), (double) (c.y_coord*size + (size/3)),
					(double)(c.x_coord*size + (2*size/3)), (double) (c.y_coord*size + (size/3)),
					
			});
			p.setFill(c.viewFillColor());
			p.setStroke(c.viewOutlineColor());
			p.setStrokeWidth(2.0);
			//g.add(p, c.x_coord, c.y_coord);
			return p;
		}
		
		if(c.viewShape() == CritterShape.DIAMOND){
			Polygon p = new Polygon();
			p.getPoints().addAll(new Double[] {
					(double) (c.x_coord*size + 2), (double) (c.y_coord*size + (size/2)),
					(double) (c.x_coord*size + (size/2)), (double) (c.y_coord*size + 2),
					(double) ((c.x_coord + 1)*size - 2), (double) (c.y_coord*size + (size/2)), 
					(double) (c.x_coord*size + (size/2)), (double) ((c.y_coord + 1)*size - 2)
			});
			p.setFill(c.viewFillColor());
			p.setStroke(c.viewOutlineColor());
			p.setStrokeWidth(2.0);
			//g.add(p, c.x_coord, c.y_coord);
			return p;
		}
		
		
		return s;
		
	}
	
	
}
