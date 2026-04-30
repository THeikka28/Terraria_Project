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
    public int strenth;
    public int startinghealth;
    boolean isdamaged;
    boolean isphase1;
    public boolean isfollowing;
    public long hidetime;
    public boolean isspawning;
    public boolean isdashing;
    public double px, py;



    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.
    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public EyeofCthulu(int pXpos, int pYpos) {//intializes the boss with it's variables
        xpos = pXpos;
        ypos = pYpos;
        dx =2;
        dy =2;
        width = 200;
        height = 150;
        isAlive = false;
        hitbox = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);
        angle = 45.467;
        speed = 5;
        health = 3000;
        startinghealth = health;
        strenth = (int)(Math.random()*10)+15;
    } // constructor

    /*
    Alternates between trying to stay above the player and summoning Servants of Cthulhu, and charging at the player occasionally. Spins when at low health, and begins exclusively charging at the player. Always looks at player.
     */
    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        strenth = (int)(Math.random()*10)+15;
        if(health>1600)//determines the behavior of the boss based on it's health
        {
            isphase1 =true;
        }
        else {isphase1 = false;}//determines the behavior of the boss based on it's health

        if(health<=1600)//determines the behavior of the boss based on it's health
        {
            dash();
        }
        hitbox = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);//draws the rectangle every frame, with updated values
        xpos = xpos + dx;
        ypos = ypos + dy;
        if(health<=0){isAlive = false;}//when the boss loses all of it's health, it dies
    }
    public void stalk(double x,double y) {// the boss follows the player
        speed = 7;
        px = x; py = y; // it's targets are x and y
        scale = speed / Math.sqrt(((x - xpos) * (x - xpos)) + ((y - ypos) * (y - ypos)));//does math to move towards the target position at a constant speed
        dy = (y - ypos) * scale;
        dx = (x - xpos) * scale;
        if (xpos < x - 10 && xpos > x + 10 && ypos < y - 10 && ypos > y + 10) {//when the boss gets to the target position, it spawns smaller enemies
            isspawning = false;
        }
            if(System.currentTimeMillis()-hidetime>(Math.random()*1000)+5000)//after about five seconds, it changes behavior
            {
            int rand = (int)(Math.random()*5)+1;
                hidetime = System.currentTimeMillis();
                System.out.println(rand);
                if(rand <3)//if it is less than 3 it will follow the player
                {
                    isfollowing = true;
                    isdashing = false;
                }
                if(rand >=3 && rand <=4)// if it is 3 or 4 it will dash at the player
                {
                    isdashing = true;
                    isfollowing = false;
                }
                if(rand ==5)// if it is 5 the boss will spawn smaller enemies
                {
                    isdashing = false;
                    isfollowing = false;
                }

            }


    }

    public void dash()//the boss dashes at the player, and keeps going until it hits the screen then turns again and dashes
    {
        if(System.currentTimeMillis()-hidetime>(Math.random()*1000)+5000)//makes the boss change behavior after 5 seconds, same as the other if statments with rand
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
        if (health >1000) //ensures that the speed while dashing but not in the second phase is sufficient
        {
            speed = 15;
        }
        if (health >= 400 && health<=1000) //when in the second phase, the boss speeds up as it's health goes down
        {
            speed = 10000 / (health);
        }
        else if(health<=300)// makes sure the bosses' speed doesnt get too fast
        {
            speed = 10000/300;
        }
        else
        {
            speed = 7;
        }

        if(xpos>1000)// when the boss exits the screen it turns to face the player again to dash again
        {
            isturning = true;
        }
        if(xpos<0)// when the boss exits the screen it turns to face the player again to dash again
        {
            isturning = true;
        }
        if(ypos>700)// when the boss exits the screen it turns to face the player again to dash again
        {
            isturning = true;
        }
        if(ypos<0)// when the boss exits the screen it turns to face the player again to dash again
        {
            isturning = true;
        }
        if(xpos<1000 && xpos>0&&ypos<700 && ypos>0) //when in the screen, the boss does not turn it's direction'
        {
            isturning = false;
        }
    }
}

