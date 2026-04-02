import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by chales on 11/6/2017.
 */
public class Sword {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public double xpos;                //the x position
    public double ypos;                //the y position
    public double dx;                    //the speed of the hero in the x direction
    public double dy;                    //the speed of the hero in the y direction
    public double width;
    public double height;
    public Rectangle hitbox;
    public double angle;
    double drawangle;
    long millitime;
    boolean isattacking;
    public boolean isanimating;



    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Sword(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =1;
        dy =1;
        width = 60;
        height = 60;
         hitbox = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);
        angle = 45.467;
        millitime = System.currentTimeMillis();

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void attack() {
            if(System.currentTimeMillis()-millitime < 450)
            {
                isattacking = true;
            }
            else
            {
                isattacking = false;
                hitbox = new Rectangle(-200, -200, 2 , 2);

            }
            if(isattacking == true && angle >-45 && angle <45)
            {
                hitbox = new Rectangle((int) xpos+30, (int) ypos-60, (int) width+20, (int) height+60);
                drawangle = -45;
            }
            if(isattacking == true && angle <-45 && angle >-135)
            {
            hitbox = new Rectangle((int) xpos-60, (int) ypos-130, (int) width+60, (int) height+20);
            drawangle = -135;

            }
            if(isattacking == true && angle <-135)
            {
            hitbox = new Rectangle((int) xpos-110, (int) ypos-60, (int) width+20, (int) height+60);
            drawangle = 135;

            }
            if(isattacking == true && angle >135)
            {
            hitbox = new Rectangle((int) xpos-110, (int) ypos-60, (int) width+20, (int) height+60);
            drawangle = 135;

            }
            if(isattacking == true && angle <135 && angle >45)
            {
            hitbox = new Rectangle((int) xpos-60, (int) ypos+50, (int) width+60, (int) height+20);
            drawangle = 45;
            }


    }

}


