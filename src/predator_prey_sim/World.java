package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.util.ArrayList;


public class World {

	private int width, height;
	private Color canavasColor;
	ArrayList<Predator> predators;
	ArrayList<Prey> preys;

	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numPrey number of prey
	 * @param numPredator number of predators
	 */
	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		System.out.println("width: " + w);
		System.out.println("hiegh: " + h);
		resetWorld();
	}

	/** 
	*Resets world to starting state (ie 5 pred, 10 prey, random background)
	**/
	public void resetWorld(){
		int numPrey = 10;
		int numPred = 5;
		changeBackgroundColor();
		predators = new ArrayList<Predator>();
		preys = new ArrayList<Prey>();
		populate(numPrey, numPred);
	}
	
	/**
	*Sets the background color to a random color
	**/
	public void changeBackgroundColor(){
		canavasColor = Helper.newRandColor();
	}
	
	/** 
	*Adds a predator to the x and y position with a random heading given as inputs
	*@param xpos the x position of the new predator
	*@param ypos the y position of the new predator
	**/
	public void clickMakePred(int xpos, int ypos){
		char startHeading = Helper.randDirection();
		predators.add(new Predator(xpos, ypos, startHeading, Color.RED));
	}

	/** 
	*Creates a predator, gives it a random location on the screen and random heading
	**/
	private void makePred(){
		int xpos = Helper.nextInt(width);
		int ypos = Helper.nextInt(height);
		char startHeading = Helper.randDirection();
		predators.add(new Predator(xpos, ypos, startHeading, Color.RED));
	}
	
	/** 
	*Creates a prey, gives it a random location on the screen and random heading
	**/
	private void makePrey(){
		int xpos = Helper.nextInt(width);
		int ypos = Helper.nextInt(height);
		char startHeading = Helper.randDirection();
		Color color = Helper.newRandColor();
		preys.add(new Prey(xpos, ypos, startHeading, color));

	}

	/** 
	*Checks the x,y positions given as inputs for a predator
	*@param x the x position to check
	*@param y the y position to check
	**/
	private boolean checkPosForPred(int x, int y){
		for (Predator pred : predators){
			if (pred.getX() == x){
				if (pred.getY() == y){
					return true;
				}
			}
		}
		return false;
	}

	/** 
	*Checks the x,y positions given as inputs for a prey
	*@param x the x position to check
	*@param y the y position to check
	**/
	private boolean checkPosForPrey(int x, int y){
		for (Prey prey : preys){
			if (prey.getX() == x){
				if (prey.getY() == y){
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	*Checks if a prey adjacent (above, below, left, or right) to a predator
	*@param prey the prey that needs to have it surrondings checked
	**/
	private boolean predatorStrike(Prey prey){
		int xprey = prey.getX();
		int yprey = prey.getY();

		if (!prey.getColor().equals(canavasColor)){
			if (checkPosForPred(xprey+1, yprey))
				return true;
			else if (checkPosForPred(xprey-1, yprey))
				return true;
			else if (checkPosForPred(xprey, yprey+1))
				return true;
			else if (checkPosForPred(xprey, yprey-1))
				return true;
		}
		return false;
	}
	
	/** 
	*Checks if a prey can see a prey ahead of it in the direction its facing
	*The maximum distance a pred can see ahead of it is 15
	*@param pred the pred that is looking forward
	**/
	private boolean predSeePrey(Predator pred){
		int i;
		char heading = pred.getHeading();
		int xpred = pred.getX();
		int ypred = pred.getY();
		int predRange = 16;

		if (heading == 'N'){
			for (i=1; i<predRange; i++){
				if (checkPosForPrey(xpred, ypred-i)){
					return true;
				}
			}
		}
		else if (heading == 'W'){
			for (i=1; i<predRange; i++){
				if (checkPosForPrey(xpred-1, ypred)){
					return true;
				}
			}
		}
		else if (heading == 'E'){
			for (i=1; i<predRange; i++){
				if (checkPosForPrey(xpred+1, ypred)){
					return true;
				}
			}
		}
		else{
			for (i=1; i<predRange; i++){
				if (checkPosForPrey(xpred, ypred+1)){
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	*Checks if a prey can see a predator ahead of it in the direction its facing
	*The maximum distance a prey can see ahead of it is 10
	*@param prey the prey that is looking forward
	**/
	private boolean preySeePred(Prey prey){
		int i;
		char heading = prey.getHeading();
		int xprey = prey.getX();
		int yprey = prey.getY();
		int preyRange = 11;

		if (heading == 'N'){
			for (i=1; i<preyRange; i++){
				if (checkPosForPred(xprey, yprey-i) == true){
					return true;
				} 
			}
		}
		else if (heading == 'W'){
			for (i=1; i<preyRange; i++){
				if (checkPosForPred(xprey-1, yprey) == true){
					return true;
				}
			}
		}
		else if (heading == 'E'){
			for (i=1; i<preyRange; i++){
				if (checkPosForPred(xprey+1, yprey) == true){
					return true;
				}
			}
		}
		else{
			for (i=1; i<preyRange; i++){
				if (checkPosForPred(xprey, yprey+1) == true){
					return true;
				}
			}
		}
		return false;
	}

	/** 
	*Teleports all the prey in the simulation to a random new location
	**/
	public void teleportPrey(){
		int newX, newY;
		for (Prey prey : preys){
			newX = Helper.nextInt(width);
			newY = Helper.nextInt(height);
			prey.setX(newX);
			prey.setY(newY);
		}
	}
	
	/**
	 * Generates numPrey random prey and numPredator random predators 
	 * distributed throughout the world.
	 * Prey must not be placed outside canavas!
	 *
	 * @param numPrey the number of prey to generate
	 * @param numPredator the number of predators to generate
	 */
	private void populate(int numPrey, int numPredators)
	{
		// Generates numPrey prey and numPredator predators 
		// randomly placed around the world.
		for (int predInc=0; predInc<numPredators; predInc++){
			makePred();
		}
		for (int preyInc=0; preyInc<numPrey; preyInc++){
			makePrey();
		}
	}
	
	/**
	 * Updates the state of the world for a time step.
	 */
	public void update() {
		// Move predators, prey, etc
		int numBabies = 0;
		for (Predator pred : predators){
			boolean seePrey = predSeePrey(pred);
			pred.runTurn(width, height, seePrey);
			numBabies+=pred.getReproduce();
		}
		for (int i=0; i<numBabies; i++){
			makePred();
		}

		numBabies = 0;
		for (Prey prey : preys){
			if (predatorStrike(prey)){
				prey.predatorAttack();
			}
			else{
				boolean seePred =  preySeePred(prey);
				prey.runTurn(width, height, seePred);
				numBabies+=prey.getReproduce();
			}
		}
		for (int i=0; i<numBabies; i++){
			makePrey();
		}
	}

	/**
	 * Draw all the predators and prey.
	 */
	public void draw(){
		/* Clear the screen */
		PPSim.dp.clear(canavasColor);
		// Draw predators and pray
		for (Predator pred : predators){
			if (pred.isDead()){
				continue;
			}
			PPSim.dp.drawSquare(pred.getX(), pred.getY(), pred.getColor());
		}

		for (Prey prey : preys){
			if (prey.isDead()){
				continue;
			}
			PPSim.dp.drawCircle(prey.getX(), prey.getY(), prey.getColor());
		}
	}



}
