package predator_prey_sim;

import util.Helper;
import java.awt.*;

public class Prey extends Animal{

    private double preyReproduceProb = .005;
    private double preyDirChangeProb = .1;

    public Prey(int startX, int startY, char startHeading, Color inputColor){
        super(startX, startY, startHeading, inputColor);
    }

    /** 
    *used when a predator attacks the prey
    *sets prey's dead boolean value (in animal class) equal to true
	**/
    public void predatorAttack(){
        //System.out.println("oof");
        this.dead = true;
    }
    
    /** 
    *turns around (reverses heading)
    *sets the temp position two spots ahead of where it is (in the new direction)
	**/
    private void runAway(){
        if (this.heading == 'N'){
            tempY = this.ypos + 2;
            tempX = this.xpos;
            this.heading = 'S';
        }
        else if (this.heading == 'W'){
            System.out.println("Seeing a predator");
            tempY = this.ypos;
            tempX = this.xpos + 2;
            this.heading = 'E';
        }
        else if (this.heading == 'S'){
            tempY = this.ypos - 2;
            tempX = this.xpos;
            this.heading = 'N';
        }
        else{
            tempY = this.ypos;
            tempX = this.xpos - 2;
            this.heading = 'W';
        }
    }

    /** 
    *runs the prey's turn
    *checks and updates if the prey reproduces
    *moves the prey
    * - if prey sees predator, turn around and go two spaces (in the new direction)
    * - else 5% chance of changing heading, move one space ahead (within screen bounds)
    *@param maxX the maximum x value on the screen
    *@param maxY the maximum Y value on the screen
    *@param seePred a boolean - if prey sees pred within 10 spaces of prey's direction
	**/
    public void runTurn(int maxX, int maxY, boolean seePred){    
        
        if (!dead){
            double reproduceProb = Helper.nextDouble();
            this.reproduce = false;
            if (reproduceProb < preyReproduceProb){
                this.reproduce = true;
            }
            //System.out.println("Im running now");
            if (seePred){
                runAway();
            }
            else{
                changeDirection(preyDirChangeProb);
                setNewCords();
            }
            checkImplementMove(maxX, maxY);
            correctHeading(maxX, maxY);	
        }
    }
}