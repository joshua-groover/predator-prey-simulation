package predator_prey_sim;

import util.Helper;
import java.awt.*;

public class Predator extends Animal{

    private double predDeathProb = .01;
    private double predReproduceProb = .01;
    private double predDirChangeProb = .05;

    public Predator(int startX, int startY, char startHeading, Color inputColor){
        super(startX, startY, startHeading, inputColor);
    }

    /** 
    *runs the pred's turn
    *checks and updates if the pred dies and reproduces
    *moves the pred
    * - if pred sees prey ahead of it, move one space in that diretion
    * - else 5% chance of changing heading, move one space ahead (within screen bounds)
    *@param maxX the maximum x value on the screen
    *@param maxY the maximum Y value on the screen
    *@param seePred a boolean - if prey sees pred within 10 spaces of prey's direction
	**/
    public void runTurn(int maxX, int maxY, boolean seePrey){    
        if (!dead){
            double deathProb = Helper.nextDouble();
            double reproduceProb = Helper.nextDouble();
            this.reproduce = false;

            if (deathProb < predDeathProb){
                this.dead = true;
            }
            else{
                if (reproduceProb < predReproduceProb)
                    this.reproduce = true;
                
                if (!seePrey)
                    changeDirection(predDirChangeProb);
        
                setNewCords();
                checkImplementMove(maxX, maxY);
                correctHeading(maxX, maxY);
            }
        }		
    }
}