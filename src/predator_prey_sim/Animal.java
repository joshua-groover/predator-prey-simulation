package predator_prey_sim;

import util.Helper;
import java.awt.*;

public class Animal {
    protected int xpos;
    protected int ypos;
    protected char heading;
    protected Color color;
    protected boolean dead = false;
    protected boolean reproduce = false;
    protected int tempX;
    protected int tempY;

    /**
	 * Create a new animal and give it a position, heading, and color
	 * @param startX starting x pos
	 * @param startY starting y pos
	 * @param startHeading starting heading
	 * @param inputColor animal color
	 */
    public Animal(int startX, int startY, char startHeading, Color inputColor){
        xpos = startX;
        ypos = startY;
        heading = startHeading;
        color = inputColor;
    }

    /** 
    *gets the x location of the animal
    *@return the xpos of the animal
	**/
    public int getX(){
        int x = xpos;
        return x;
    }

    /** 
    *gets the y location of the animal
    *@return the ypos of the animal
	**/
    public int getY(){
        int y = ypos;
        return y;
    }

    /** 
    *sets the x position of the animal
    *@param newX the new x position of the animal
	**/
    public void setX(int newX){
        this.xpos = newX;
    }

    /** 
    *sets the y position of the animal
    *@param newY the new y position of the animal
	**/
    public void setY(int newY){
        this.ypos = newY;
    }

    /** 
    *gets the heading of the animal
    *@return the heading of the animal
	**/
    public char getHeading(){
        char h = heading;
        return h;
    }

    /** 
    *gets the color of the animal
    *@return the color of the animal
	**/
    public Color getColor(){
        Color c = color;
        return c;
    }

    /** 
    *checks if the animal is dead
    *@return true if animal dead, false if not
	**/
    public boolean isDead() {
        if (this.dead){
            return true;
        }
        return false;
	}

    /** 
    *gets the number of offspring the animal makes if reproduce is true (between 1-3)
    *if reproduce is not true, returns 0 (reproduce is set when runing the turn in the Pred/Prey subclass)
    *@return the number of new offspring to create
	**/
    public int getReproduce(){
        double howMany = Helper.nextDouble();
        if (this.reproduce){
            if(howMany < .33){
                return 1;
            }
            else if(howMany < .66){
                return 2;
            }
            return 3;
        }
        return 0;
    }

    /** 
    *Determines if the animal should change direction based on a random number between 0-1
    *Random number compared to the animal prob of turning
    *If less than animal prob of turning this changes heading
    *@param prob the animal's probability of turning in a new direction
	**/
    public void changeDirection(double prob){
        double dirRand = Helper.nextDouble();
        if (dirRand < prob){
            char curDir = this.heading;
            while (curDir == this.heading){
                curDir = Helper.randDirection();
            }
            this.heading = curDir;
        }
    }

    /** 
    *returns the y position in front of the direction the animal is facing
    *@return y pos in from of the direction the animal is facing (ie if facing north return ycurrent-1)
	**/
    protected int moveY(){
        if (this.heading == 'N')
            return this.ypos-1;
        return this.ypos+1;
    }

    /** 
    *returns the x position in front of the direction the animal is facing
    *@return x pos in from of the direction the animal is facing (ie if facing west return xcurrent-1)
	**/
    protected int moveX(){
        if (this.heading == 'W')
            return this.xpos-1;
        return this.xpos+1;
    }

    /** 
    *Sets new test coordinates for the animal during a turn. 
    *All this does is set the temporary coordinates one position ahead of the animal
    *temp cords used by pred/prey subclasses - they determine if temp cords are in bounds b4 implementing them
	**/
    protected void setNewCords(){
        int newX, newY;
        if (this.heading == 'N' || this.heading == 'S'){
            newY = moveY();
            newX = this.xpos;
        }
        else{
            newX = moveX();
            newY = this.ypos;
        }
        tempX = newX;
        tempY = newY;
    }

    /** 
    *Determines if the temp x/y positions set by running a turn are out of bounds
    *if they are NOT, updates the current position to match the temp position
    *if they are, does not move the animal 
    *@param maxX the maximum x position on the screen
    *@param maxY the maximum y position on the screen
	**/
    protected void checkImplementMove(int maxX, int maxY){
        int newX = tempX;
        int newY = tempY;

        if (newX <= maxX && newX >= 0){
            this.xpos = newX;
        }
        if (newY <= maxY && newY >= 0){
            this.ypos = newY;
        }
    }

    /** 
    *Determines if the animal is on the edge of the screen
    *if on the edge of the screen, change the heading of the animal to face away from the edge
    *@param maxX the maximum x position on the screen
    *@param maxY the maximum y position on the screen
	**/
    protected void correctHeading(int maxX, int maxY){
        if (maxX == this.xpos){
            this.heading = 'W';
        }
        if (0 == this.xpos){
            this.heading = 'E';
        }
        if (maxY == this.ypos){
            this.heading = 'N';
        }
        if (0 == this.ypos){
            this.heading = 'S';
        }
    }

}