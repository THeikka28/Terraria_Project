import java.awt.*;

public class heart {
    public int xpos, ypos;
    public boolean isfull;
    public Image heart;
    public heart(int pxpos,  int pypos)
    {
        xpos = pxpos;
        ypos = pypos;
    }

    public void injured(double health, double total)
    {
        if(health < total)
        {isfull = false;
            heart = Toolkit.getDefaultToolkit().getImage("Empty.png");}
        else
        {isfull = true;
            heart = Toolkit.getDefaultToolkit().getImage("Full.png");}
    }

    }

