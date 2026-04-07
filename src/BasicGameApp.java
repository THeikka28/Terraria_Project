//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;






//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener, MouseListener, MouseMotionListener {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   public boolean right;
   public boolean left;
   int mousex = 1, mousey =1;
   public Sword slash;

	public BufferStrategy bufferStrategy;
	public Image TerrarianPic;
	public Image Bosspic;
	public Image swordpic;
	public Image Bossbarpic;
	public EyeofCthulu boss;
	public boolean islanding;
	public Walls arena[];


   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	public Terrarian player;
	public Walls floor;


   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up
		player = new Terrarian(10,100);
		swordpic = Toolkit.getDefaultToolkit().getImage("Sword.png"); //load the picture
		slash = new Sword((int)(player.xpos+(player.width/2)), (int) (player.xpos+(player.width/2)));
		floor = new Walls(0,600,1000,200);
		boss = new EyeofCthulu(100,100);
		arena = new Walls[6];
		floor.ispassable = false;
		for(int j = 0; j<arena.length; j++)
		{
			arena[j] = new Walls(0, j*125, 1000, 30);
		}
		Bosspic = Toolkit.getDefaultToolkit().getImage("EyePhase1.png");
		Bossbarpic = Toolkit.getDefaultToolkit().getImage("Bossbar.png");

	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {
		crashing();
         moveThings();  //move all the game objects

         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		player.move();
		if (right == true && player.dx <10)
		{

			player.dx = player.dx + 1;
		}
		if (left == true && player.dx >-10)
		{
			player.dx = player.dx-1;
		}
			if(slash.isattacking == false)
			{
				slash.hitbox = new Rectangle((int) player.xpos+3, (int) player.ypos+3,  1, 1);
			}
			slash.xpos = (player.xpos+(player.width/2));
			slash.ypos = (player.ypos+(player.height/2));
			slash.attack();
			if(slash.isattacking == true)
			{
				slash.drawangle = slash.drawangle+0.1;
			}
		if(player.dx >0) {
			TerrarianPic = Toolkit.getDefaultToolkit().getImage("Terrarianpic.png"); //load the picture
		}
		if(player.dx <=0) {
			TerrarianPic = Toolkit.getDefaultToolkit().getImage("Terrarianpic2.png"); //load the picture
		}
		if(boss.isturning == true)
		{
			boss.scale = boss.speed/Math.sqrt(((player.xpos - boss.xpos) * (player.xpos - boss.xpos)) + ((player.ypos - boss.ypos) * (player.ypos - boss.ypos)));
			boss.dy = (player.ypos - boss.ypos)*boss.scale;
			boss.dx = (player.xpos - boss.xpos)*boss.scale;
		}
		if(boss.isphase1 == true)
		{
			if(boss.isfollowing == true) {
				boss.stalk(player.xpos, player.ypos);
			}
			if(boss.isfollowing == false && boss.isdashing == false) {
				boss.stalk(player.xpos, player.ypos+(Math.random()*-50)-350);
			}
			if(boss.isdashing == true)
			{
				boss.dash();
			}
		}
		boss.move();
	}


	public void crashing()
	{
		if(!slash.hitbox.intersects(boss.hitbox))
		{
			boss.isdamaged = false;
		}
		if(player.hitbox.intersects(floor.hitbox))
		{
			if(player.dx >= 1)
			{
				player.dx = player.dx-0.5;
			}
			if(player.dx <= -1)
			{
				player.dx = player.dx+0.5;
			}
			if(player.dx > -1 && player.dx<1 && right == false && left == false)
			{
				player.dx = 0;
			}
			if(islanding == false && player.ispassing == false )
			{
				player.isgrounded = true;
				player.ypos = 600-player.height;
			}
			if(islanding == false && player.ispassing == true )
			{
				if(floor.ispassable == false) {
					player.isgrounded = true;
					player.ypos = 600 - player.height;
				}
			}
		}
		if(!player.hitbox.intersects(floor.hitbox))
		{
			islanding = false;

		}
		if (!player.hitbox.intersects(floor.hitbox))
		{
			player.isgrounded = false;
		}
		for(int x=0; x<arena.length; x++){
		if (player.hitbox.intersects(arena[x].hitbox) && player.ypos < (arena[x].ypos-90))
			{
				if(player.dx >= 1)
				{
					player.dx = player.dx-0.5;
				}
				if(player.dx <= -1)
				{
					player.dx = player.dx+0.5;
				}
				if(player.dx > -1 && player.dx<1 && right == false && left == false)
				{
					player.dx = 0;
				}
			if(!player.ispassing)
				{
				player.isgrounded = true;
				player.ypos = arena[x].ypos - player.height;
				}
			}
		}
		if(slash.hitbox.intersects(boss.hitbox) && boss.isdamaged == false)
		{
			boss.isdamaged = true;
			boss.health = boss.health-player.strength;
			System.out.println(boss.health);
		}
		if(!player.hitbox.intersects(boss.hitbox))
		{
			player.isdamaged = false;
		}
		if(System.currentTimeMillis() - player.iframes > 670)
		{
			player.isdamaged = false;
		}
		if(player.hitbox.intersects(boss.hitbox) && player.isdamaged == false)
		{
			player.isdamaged = true;
			player.damagenumber = System.currentTimeMillis();
			player.health = player.health-boss.strenth;
			System.out.println(player.health);
			player.iframes = System.currentTimeMillis();
		}
	}
	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
	  canvas.addKeyListener(this);
	  canvas.addMouseListener(this);



	   panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();

   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(TerrarianPic, (int)player.xpos, (int)player.ypos, (int) player.width, (int)player.height, null );
		g.drawImage(Bosspic, (int)boss.xpos, (int)boss.ypos, (int) boss.width, (int)boss.height, null );
		g.drawRect(floor.hitbox.x, floor.hitbox.y, floor.hitbox.width, floor.hitbox.height);
		g.drawRect(player.hitbox.x, player.hitbox.y, player.hitbox.width, player.hitbox.height);
		g.drawRect(slash.hitbox.x,slash.hitbox.y,slash.hitbox.width,slash.hitbox.height);
		g.drawRect(boss.hitbox.x,boss.hitbox.y, boss.hitbox.width, boss.hitbox.height);
		for(int j =0; j<arena.length; j++)
		{
			g.fillRect(arena[j].hitbox.x, arena[j].hitbox.y, arena[j].hitbox.width, 5 );
		}
		if(boss.isAlive == true)
		{
			g.drawImage(Bossbarpic, 200, 550, 600, 65, null );
		}




		g.dispose();

		bufferStrategy.show();
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() ==69)
		{
			player.grapple(mousex,mousey);
		}

		if(e.getKeyCode() == 65 && player.dx>-10)
		{
			left = true;
			if(System.currentTimeMillis()- player.dash<350 && System.currentTimeMillis()- player.dashcooldown >1000)
			{
				player.dx = -15;
				player.iframes = System.currentTimeMillis();
				player.dashcooldown = System.currentTimeMillis();
			}
			player.dash = System.currentTimeMillis();

		}
		if(e.getKeyCode() == 32)
		{
			if(player.isgrounded == true)
			{
				player.dy = -10.5;
				player.ypos = player.ypos - 10;
			}
		}
		if(e.getKeyCode() == 68 && player.dx<10)
		{
			right = true;
			if(System.currentTimeMillis()- player.dash<350 && System.currentTimeMillis()- player.dashcooldown >1000)
			{
				player.dx = 15;
				player.iframes = System.currentTimeMillis();
				player.dashcooldown = System.currentTimeMillis();
			}
			player.dash = System.currentTimeMillis();

		}
		if(e.getKeyCode() == 83)
		{player.ispassing = true;}


	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == 68)
		{

			right = false;
			//		player.dx = 0;
		}
		if(e.getKeyCode() == 65)
		{
			left = false;
		//	player.dx = 0;
		}
		if(e.getKeyCode() == 83)
		{player.ispassing = false;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		double xdif = mousex - (player.xpos + player.width/2);
		double ydif = mousey - (player.ypos + player.height/2);
		player.angle = Math.atan2(ydif,xdif);
		slash.angle = Math.toDegrees(player.angle);
		if(slash.isattacking == false)
		{
			slash.millitime = System.currentTimeMillis();
		}


	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}