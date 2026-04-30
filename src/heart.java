import java.awt.*;

public class heart {
    public int xpos, ypos;
    public boolean isfull;
    public Image heart;
    public heart(int pxpos,  int pypos)//initializes the hearts
    {
        xpos = pxpos;
        ypos = pypos;
    }

    public void injured(double health, double total)//method used to tell if the heart is full or not depending on the player's health
    {
        if(health < total)//when the player's health is greater than the hearts assigned value, which is 20 x its number, then it is full
        {isfull = false;
            heart = Toolkit.getDefaultToolkit().getImage("Empty.png");}
        else
        {isfull = true;
            heart = Toolkit.getDefaultToolkit().getImage("Full.png");}//when the health is less than te valueof the heart, it is drawn as empty
    }

    }

