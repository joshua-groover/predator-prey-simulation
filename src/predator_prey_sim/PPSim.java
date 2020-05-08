package predator_prey_sim;

import util.DotPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;

import javax.swing.JFrame;


/*
 * You must add a way to represent predators.  When there is no prey, predators
 * should follow these simple rules:
 * 		if (1 in 5 chance):
 * 			turn to face a random direction (up/down/left/right)
 * 		Move in the current direction one space unless stepping out of the world.
 *
 * We will add additional rules for dealing with sighting or running into prey later.
 */

public class PPSim extends JFrame implements KeyListener, MouseListener {

	private static final long serialVersionUID = -5176170979783243427L;

	/** The Dot Panel object you will draw to */
	protected static DotPanel dp;
	World ppworld;

	/* Define constants using static final variables */
	public static final int MAX_X = 100;
	public static final int MAX_Y = 100;
	public static final int DOT_SIZE = 6;
	private static final int NUM_PREY = 10;
	private static final int NUM_PREDATORS = 5;



	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares for predators and circles for prey
	 * to the screen.
	 */
	public PPSim() {
		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Predator Prey World");

		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);

		/* Add the panel to the frame */
		Container cPane = this.getContentPane();
		cPane.add(dp);

		//add a key and mouse listener
		this.addKeyListener(this);
		dp.addMouseListener(this);

		/* Initialize the DotPanel canvas:
		 * You CANNOT draw to the panel BEFORE this code is called.
		 * You CANNOT add new widgets to the frame AFTER this is called.
		 */
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		this.setVisible(true);

		/* Create our city */
		ppworld = new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);

		/* This is the Run Loop (aka "simulation loop" or "game loop")
		 * It will loop forever, first updating the state of the world
		 * (e.g., having predators take a single step) and then it will
		 * draw the newly updated simulation. Since we don't want
		 * the simulation to run too fast for us to see, it will sleep
		 * after repainting the screen. Currently it sleeps for
		 * 33 milliseconds, so the program will update at about 30 frames
		 * per second.
		 */
		while(true)
		{
			// Run update rules for world and everything in it
			ppworld.update();
			// Draw to screen and then refresh
			ppworld.draw();
			dp.repaintAndSleep(30);

		}
	}


	public static void main(String[] args) {
		/* Create a new GUI window  */
		new PPSim();
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}

	/*
	This function handles all of the key pressed actions
	Space Bar: change the background of world
	Enter Key: Reset the simulation
	Shift Key: Teleport all of the prey to a new location
	*/
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		System.out.print(keyEvent.getKeyChar());
		switch(keyEvent.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				ppworld.changeBackgroundColor();
				break;
			case KeyEvent.VK_ENTER:
				ppworld.resetWorld();
				break;
			case KeyEvent.VK_SHIFT:
				ppworld.teleportPrey();
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
	}

	/* 
	This function handles mouse clicking on the screen
	Clicking the mouse adds a new predator to the location of the clicking
	*/
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		ppworld.clickMakePred(mouseEvent.getX()/DOT_SIZE, mouseEvent.getY()/DOT_SIZE);
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}
}

