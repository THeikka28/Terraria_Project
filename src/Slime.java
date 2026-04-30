import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Slime {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public double xpos;                //the x position
    public double ypos;                //the y position
    public double dx;                    //the speed of the hero in the x direction
    public double dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public int strenth;
    public boolean isAlive;//a boolean to denote if the hero is alive or dead.
    public int health;
    public Rectangle hitbox;
    boolean isdamaged;


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Slime(int pXpos, int pYpos) {//initalizes the slime
        xpos = pXpos;
        ypos = pYpos;
        dx =-4;
        dy =0;
        width = 60;
        height = 60;
        isAlive = true;
        health = 300;
        strenth = 20;
        hitbox = new Rectangle(width, height, (int)xpos, (int)ypos);

 
    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {//makes the slime move around
        if(xpos<0)
        {dx = -dx;}//bounces off of left and right walls
        if(xpos>1000)
        {dx = -dx;}//bounces off of left/right walls
        if(health<0)
        {isAlive = false;}//dies when it runs out of health
        xpos = xpos + dx;
        ypos = ypos + dy;
        hitbox = new Rectangle((int)xpos, (int)ypos, width, height);
        if(ypos < 600)//akes the slime have gravity pulling it to the ground
        {
            dy = dy+0.5;
        }
        else{//when it hits the ground, it bounces up
            ypos = 601;
            dy = -15;
        }

 
    }
}






