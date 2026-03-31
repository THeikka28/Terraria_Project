import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by chales on 11/6/2017.
 */
public class Terrarian {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public double xpos;                //the x position
    public double ypos;                //the y position
    public double dx;                    //the speed of the hero in the x direction
    public double dy;                    //the speed of the hero in the y direction
    public double width;
    public double height;
    public boolean isAlive;
    public Rectangle hitbox;
    public boolean isgrounded;
    public double angle;
    public int strength;
    double health;
    public boolean ispassing;
    public boolean isdamaged;
    public long iframes;
    public long damagenumber;
    public long dash;
    public long dashcooldown;



    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Terrarian(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =1;
        dy =1;
        width = 60;
        height = 100;
        isAlive = true;
        hitbox = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);
        angle = 45.467;
        strength = 100;
        health = 400;

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        hitbox = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);
        if(isgrounded == false && dy<15)
        {
            dy = dy + 0.5;
        }
    }
    public void grapple(double x, double y)
    {
        double scale;
        double speed = 15;
        scale = speed/Math.sqrt(((x - xpos) * (x - xpos)) + ((y - ypos) * (y - ypos)));
        dy = (y - ypos)*scale;
        dx = (x - xpos)*scale;

    }
}