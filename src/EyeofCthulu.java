import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class EyeofCthulu {

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
    public boolean isturning;
    public double angle;
    public double speed;
    public double scale;
    public int health;
    public double strenth;
    public int startinghealth;
    boolean isdamaged;
    boolean isphase1;
    public boolean isfollowing;
    public long hidetime;
    public boolean isspawning;
    public boolean isdashing;



    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.
    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public EyeofCthulu(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =2;
        dy =2;
        width = 200;
        height = 150;
        isAlive = true;
        hitbox = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);
        angle = 45.467;
        speed = 5;
        health = 3000;
        startinghealth = health;
        strenth = (Math.random()*10)+10;

    } // constructor


    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        strenth = (Math.random()*10)+10;
        if(health>1600)
        {
            isphase1 =true;
        }
        else {isphase1 = false;}

        if(health<=1600)
        {
            dash();
        }
        hitbox = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);
        xpos = xpos + dx;
        ypos = ypos + dy;

    }
    public void stalk(double x,double y)
    {
/*
Alternates between trying to stay above the player and summoning Servants of Cthulhu, and charging at the player occasionally. Spins when at low health, and begins exclusively charging at the player. Always looks at player.
 */
            scale = speed / Math.sqrt(((x - xpos) * (x - xpos)) + ((y - ypos) * (y - ypos)));
            dy = (y - ypos) * scale;
            dx = (x - xpos) * scale;
            if(xpos>x-10 && xpos<x+10 && ypos>y-10 && ypos<y+10)
            {
                System.out.println("spawn");

            }
            if(System.currentTimeMillis()-hidetime>(Math.random()*1000)+3000)
            {
            int rand = (int)(Math.random()*3)+1;
                hidetime = System.currentTimeMillis();
                if(rand ==1)
                {
                    isfollowing = true;
                    isdashing = false;
                }
                if(rand ==2)
                {
                    isdashing = true;
                    isfollowing = false;
                }
                if(rand ==3)
                {
                    isdashing = false;
                    isfollowing = false;
                }
            }


    }

    public void dash()
    {
        if(System.currentTimeMillis()-hidetime>(Math.random()*1000)+3000)
        {
            int rand = (int)(Math.random()*3)+1;
            hidetime = System.currentTimeMillis();
            if(rand ==1)
            {
                isfollowing = true;
                isdashing = false;
            }
            if(rand ==2)
            {
                isdashing = true;
                isfollowing = false;
            }
            if(rand ==3)
            {
                isdashing = false;
                isfollowing = false;
            }
        }
        if (health > 300 && health<1000)
        {
            speed = 10000 / (health);
        }
        else
        {
            speed = 10;
        }

        if(xpos>1000)
        {
            isturning = true;
        }
        if(xpos<0)
        {
            isturning = true;
        }
        if(ypos>700)
        {
            isturning = true;
        }
        if(ypos<0)
        {
            isturning = true;
        }
        if(xpos<1000 && xpos>0&&ypos<700 && ypos>0)
        {
            isturning = false;
        }
    }
}

