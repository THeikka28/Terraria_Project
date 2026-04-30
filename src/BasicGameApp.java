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
   public Image zombiepic;

	public BufferStrategy bufferStrategy;
	public Image TerrarianPic;
	public Image Bosspic;
	public Image swordpic;
	public EyeofCthulu boss;
	public boolean islanding;
	public Walls arena[];
	public ServantofCthulu servants[];
	public heart hearts[];
	public Image Deathscreen;
	public Image cooldown;
	public Image background;
	public Image floorpic;
	public Zombie john;
	public Slime slimy;
	public Image Slime;



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
		player = new Terrarian(10,100);//set up player
		swordpic = Toolkit.getDefaultToolkit().getImage("Sword.png");
		slash = new Sword((int)(player.xpos+(player.width/2)), (int) (player.xpos+(player.width/2)));//set up the player's attack
		floor = new Walls(0,600,1000,200);//makes base floor
		boss = new EyeofCthulu(100,100); //makes boss
		arena = new Walls[6];//makes platforms that can be walked through
		john = new Zombie(0,520);//makes zombie
		slimy = new Slime(601, 101); //makes slime
		hearts = new heart[21]; //makes health bar of hearts at the top
		servants = new ServantofCthulu[5]; //makes boss's servants
		floor.ispassable = false;
		for(int j = 0; j<arena.length; j++)//for look used to initialize the arena
		{
			arena[j] = new Walls(0, j*125, 1000, 30);
		}


		for(int i = 0; i< hearts.length; i++)//for loop used to initilize the health bar
		{
			if(i<11) {
				hearts[i] = new heart(800+(i*20), 10);}
			if(i>10) {
			hearts[i] = new heart(580+(i*20), 40);}
		}
		for(int j = 0; j<servants.length; j++) // for loop used to initialize the boss's servants
		{
			servants[j] = new ServantofCthulu(-100 + (int)(Math.random()*250), -100 + (int)(Math.random()*300));
			servants[j].speed = (Math.random()*6+1);
		}
		//assigning the images actual values
		Bosspic = Toolkit.getDefaultToolkit().getImage("EyePhase1.png");
		Deathscreen = Toolkit.getDefaultToolkit().getImage("Death.png"); //load the picture
		cooldown = Toolkit.getDefaultToolkit().getImage("cooldown.png");
		background = Toolkit.getDefaultToolkit().getImage("Background.jpg");
		floorpic = Toolkit.getDefaultToolkit().getImage("Floorpic.png");
		zombiepic = Toolkit.getDefaultToolkit().getImage("zombiepic.png");
		Slime = Toolkit.getDefaultToolkit().getImage("Slime.png");


	}

   
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
         moveThings();  //move all the game objectss
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}

//method used to make characters move
	public void moveThings()
	{

		for(int l = 0; l< hearts.length; l++) // this loop makes the hearts in the array show up, and go gray if the player is injured
		{hearts[l].injured(player.health, l*20);}
		if(john.isAlive)
		{john.move();}
		//makes sure the zombie is only present when alive
		if(slimy.isAlive)
		{slimy.move();}
		//makes sure the zombie only moves if it alive

      //calls the move( ) code in the objects
		player.move();
		if (right == true && player.dx <10)
		{
			player.dx = player.dx + 1;
		}//when pressing d, the player accelerates up until the speed of 10;

		if (left == true && player.dx >-10)
		{
			player.dx = player.dx-1;
		}//when pressing a, the player accelerates up until the speed of -10;

			if(slash.isattacking == false)
			{
				slash.hitbox = new Rectangle((int) player.xpos+3, (int) player.ypos+3,  1, 1);
			}//when the player is not attacking anything else, the word hitbox is not present, or able to damage anything
			slash.xpos = (player.xpos+(player.width/2));
			slash.ypos = (player.ypos+(player.height/2));
			slash.attack();//does the method for attacking that is in the slash class

		if(player.dx >0) {
			TerrarianPic = Toolkit.getDefaultToolkit().getImage("Terrarianpic.png"); //load the picture
		}
		//makes the player look the direction oin which they are moving

		if(player.dx <=0) {
			TerrarianPic = Toolkit.getDefaultToolkit().getImage("Terrarianpic2.png"); //load the picture
		}		//makes the player look the direction oin which they are moving
		if(boss.isturning == true)//changes the boss's direction whenever it is turnign to look at the player
		{
			boss.scale = boss.speed/Math.sqrt(((player.xpos - boss.xpos) * (player.xpos - boss.xpos)) + ((player.ypos - boss.ypos) * (player.ypos - boss.ypos)));
			//does math to find the x and y scale so that the boss moves at a constant rate in the direction of the player
			boss.dy = (player.ypos - boss.ypos)*boss.scale;//applies the scale so that the boss moves in thedirection of the player
			boss.dx = (player.xpos - boss.xpos)*boss.scale;
		}
		if(boss.isphase1 == true)// does these actions only while the boss is in it's first phase
		{
			if(boss.isfollowing == true) {
				boss.stalk(player.xpos, player.ypos);//does this behavior only when the isfollowing case is true
			}
			if(boss.isfollowing == false && boss.isdashing == false) {//when the random chance makes the bossd have those two things, the boss performs the follwoing behaviors
				boss.stalk(player.xpos, player.ypos+(Math.random()*-50)-350);//makes the boss hover over the player when this behavior is true
				if(  boss.xpos > boss.px - 10 && boss.xpos < boss.px + 10 && boss.ypos > boss.py - 10 && boss.ypos < boss.py + 10 && boss.isspawning == false)//when the boss gets to it's target position above the player, it slpawns smaller enemies
				{
					boss.isspawning = true;
					for(int h = 0; h< servants.length; h++)//for loops used to makes the array of servants rbe revied in different locations around the map
					{
						servants[h].health = 8;
						servants[h].isAlive = true;
						servants[h].xpos = boss.xpos+ ((int)(Math.random()*250)-125);
						servants[h].ypos = boss.ypos+ ((int)(Math.random()*150)-75);

					}
				}
			}

			if(boss.isdashing == true)// if the random chance makes the boss have dashing = true, it will dash at the player
			{
				boss.dash();
			}
		}
		if(boss.isAlive){boss.move();}// makes sure the boss only is performing actions while alive
		else{boss.xpos = 10000;}// if the boss is not alive it will be off screen
		for(int x =1; x< servants.length; x++) {//loop used to make all servants in the array move
			if (servants[x].isAlive)//makes the servants move towards the player while alive
			{
				servants[x].move(player.xpos, player.ypos);
			}
			else
			{servants[x].xpos = 1000;}// if they die, they go off screen
		}

	}


	public void crashing()//method used to handle interactions
	{
		if(!slash.hitbox.intersects(boss.hitbox))//if the boss is not in contact with the weapon, then it is niot being damged, used for the method where the boss is damged.
		{
			boss.isdamaged = false;
		}
		if(player.hitbox.intersects(floor.hitbox))//intersection with floor, player does not fall while on floor
		{
			if(player.dx >= 1)
			{
				player.dx = player.dx-0.5;
			}//gives friction to the floor
			if(player.dx <= -1)
			{
				player.dx = player.dx+0.5;
			}//gives friction to the floor
			if(player.dx > -1 && player.dx<1 && right == false && left == false)
			{
				player.dx = 0;
			}//makes the player stop fully when not moving, and friction has slowed them down sufficiently
			if(islanding == false && player.ispassing == false ) //makesthe player stay on the ground when not jumping or doing othe things
			{
				player.isgrounded = true;
				player.ypos = 600-player.height;
			}
			if(islanding == false && player.ispassing == true )//makesthe player stay on the ground when not jumping or doing othe things
			{
				if(floor.ispassable == false) {
					player.isgrounded = true;
					player.ypos = 600 - player.height;
				}
			}
		}
		if(!player.hitbox.intersects(floor.hitbox)) //makes the variable used for jumping false when the player is not grounded so they cannot double jump
		{
			islanding = false;
			player.isgrounded = false;
		}
		for(int x=0; x<arena.length; x++){//for loop used to make this interaction true with all objects in the arena array
		if (player.hitbox.intersects(arena[x].hitbox) && player.ypos < (arena[x].ypos-90))//interaction happens when the player touches the arena, and they are on top of it
			{
				if(player.dx >= 1)
				{
					player.dx = player.dx-0.5;
				}//gives friction
				if(player.dx <= -1)
				{
					player.dx = player.dx+0.5;
				}//gives friction
				if(player.dx > -1 && player.dx<1 && right == false && left == false)
				{
					player.dx = 0;
				}//gives friction
			if(!player.ispassing)
				{
				player.isgrounded = true;
				player.ypos = arena[x].ypos - player.height;
				}//when the player does not try to drop through, they stay on the platform, but once they press s, they fall through the floor, like in the game
			}
		}
		if(slash.hitbox.intersects(boss.hitbox) && boss.isdamaged == false)//makes the boss be damaged by the sword
		{
			boss.isdamaged = true;
			boss.health = boss.health-player.strength;
		}

			if (!player.hitbox.intersects(boss.hitbox) && !player.hitbox.intersects(john.hitbox) && !player.hitbox.intersects(slimy.hitbox)&& !player.hitbox.intersects(servants[0].hitbox) && !player.hitbox.intersects(servants[1].hitbox) && !player.hitbox.intersects(servants[2].hitbox) && !player.hitbox.intersects(servants[3].hitbox) && !player.hitbox.intersects(servants[4].hitbox) ) {
				player.isdamaged = false;
			}//when the player is not intersecting any other object, it is not being damged, so it can now once again be hurt

		if(System.currentTimeMillis() - player.iframes > 670) // gives the player some invincibility frames, so when they take damage they have a second of invullnerability, even if they hit anoth object,
			//just to make the game a bit more balanced
		{
			player.isdamaged = false;
		}
		if(player.hitbox.intersects(boss.hitbox) && player.isdamaged == false && boss.isAlive)//makes the player take damage when hitting the boss, and resets invincibility frames
		{
			player.isdamaged = true;
			player.damagenumber = System.currentTimeMillis();
			player.health = player.health-boss.strenth;
			player.iframes = System.currentTimeMillis();
		}
		for (int o = 1; o< servants.length; o++)//for loop used to make interaction function for all servants.
		{
			if (servants[o].hitbox.intersects(player.hitbox) && player.isdamaged == false && servants[o].isAlive == true)
			{
				player.isdamaged = true;
				player.damagenumber = System.currentTimeMillis();
				player.health = player.health-servants[o].strenth;
				player.iframes = System.currentTimeMillis();
			}//when the servants hit the player, the player is damaged
		}

			if (john.hitbox.intersects(player.hitbox) && player.isdamaged == false && john.isAlive == true)//when the zombie hits the player, the player is damaged
			{
				player.isdamaged = true;
				player.damagenumber = System.currentTimeMillis();
				player.health = player.health-john.strenth;
				player.iframes = System.currentTimeMillis();
			}
		if (slimy.hitbox.intersects(player.hitbox) && player.isdamaged == false && slimy.isAlive == true)//when the slime hits the player, the player is damaged.
		{
			player.isdamaged = true;
			player.damagenumber = System.currentTimeMillis();
			player.health = player.health-slimy.strenth;
			player.iframes = System.currentTimeMillis();
		}

		for (int o = 1; o< servants.length; o++)//for loop used to make interaction work for all servants
		{
			if (servants[o].hitbox.intersects(slash.hitbox) && servants[o].isdamaged == false)//makes the servants take damage from the sword
			{
				servants[o].isdamaged = true;
				servants[o].health = servants[o].health-player.strength;
			}
		}

			if (john.hitbox.intersects(slash.hitbox) && john.isdamaged == false)//akes the zombie take damage from the sword
			{
				john.isdamaged = true;
				john.health = john.health-player.strength;
				System.out.println(john.health);
			}
		if (slimy.hitbox.intersects(slash.hitbox) && slimy.isdamaged == false)//makes the slime take damage from the sword
		{
			slimy.isdamaged = true;
			slimy.health = slimy.health-player.strength;
			System.out.println(slimy.health);
		}

		for (int o = 1; o< servants.length; o++)//loop used to make interactionw ork for all servants
		{
			if (!servants[o].hitbox.intersects(slash.hitbox))//whenever they are not being damaged, they are now able to be damaaged again
			{
				servants[o].isdamaged = false;
			}
		}

			if (!john.hitbox.intersects(slash.hitbox)) //whenever they are not being damaged, they are now able to be damaaged again
			{
				john.isdamaged = false;
			}
		if (!slimy.hitbox.intersects(slash.hitbox))//whenever they are not being damaged, they are now able to be damaaged again
		{
			slimy.isdamaged = false;
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
		//images used to show the background, floor, boss, servants, slime and zombie, also hearts.
		g.drawImage(background, 1,1,1000,700,null);
		g.drawImage(TerrarianPic, (int)player.xpos, (int)player.ypos, (int) player.width, (int)player.height, null );
		g.drawImage(Bosspic, (int)boss.xpos, (int)boss.ypos, (int) boss.width, (int)boss.height, null );
		g.setColor(Color.white);
		g.drawImage(floorpic, floor.hitbox.x, floor.hitbox.y, floor.hitbox.width, floor.hitbox.height, null);
		g.drawRect(player.hitbox.x, player.hitbox.y, player.hitbox.width, player.hitbox.height);
		g.drawRect(slash.hitbox.x,slash.hitbox.y,slash.hitbox.width,slash.hitbox.height);
		g.drawImage(slash.slice, slash.hitbox.x,slash.hitbox.y,slash.hitbox.width,slash.hitbox.height, null);
		g.drawRect(boss.hitbox.x,boss.hitbox.y, boss.hitbox.width, boss.hitbox.height);
		for(int j =0; j<arena.length; j++)// draws rectangle for all arena parts in the array
		{
			g.fillRect(arena[j].hitbox.x, arena[j].hitbox.y, arena[j].hitbox.width, 5 );
		}

		if(player.health < 0)//when the player dies, the deathscreen is shown
		{
			g.drawImage(Deathscreen, 350,350, 300, 90, null);
		}
		if(john.isAlive)//draws the zombie while alive.
		{
			g.drawImage(zombiepic, john.hitbox.x,john.hitbox.y, john.hitbox.width, john.hitbox.height,null);
			g.drawRect(john.hitbox.x, john.hitbox.y, john.hitbox.width, john.hitbox.height);}

		for(int k = 1; k< servants.length; k++)//draws all the servants
		{g.drawImage(Bosspic,(int)servants[k].xpos, (int)servants[k].ypos, (int)servants[k].width, (int)servants[k].height, null );}
		for(int k = 1; k< servants.length; k++)
		{g.drawRect(servants[k].hitbox.x,servants[k].hitbox.y, servants[k].hitbox.width, servants[k].hitbox.height);}
		if(boss.isAlive)//draws the boss and makes q health bar for it
		{
			g.drawString("Eye of Cthulhu " + boss.health + "/3000", boss.hitbox.x, boss.hitbox.y);}

		for(int u = 0; u<hearts.length; u++)//draws the player's hearts
		{g.drawImage(hearts[u].heart, hearts[u].xpos, hearts[u].ypos, 20,20, null);}

		g.setColor(Color.lightGray);
		g.fillRect(10, 35, 210,50 );
		g.setColor(Color.blue);
		double scale = (System.currentTimeMillis()- player.dashcooldown)/1500;
		double width = 200*scale;
		if(width<200){//makes a dash bar that gets filled once the player's dash cooldown has ended
		g.fillRect(10, 40, (int) (width),40 );
		}
		else{	g.fillRect(15, 40, 200,40 );}
		if(player.cooldown == true)//shows the health potion cooldown when it is still ongoing
		{g.drawImage(cooldown, 10, 90, 40,45, null);}
		if(slimy.isAlive == true ) {
			g.drawImage(Slime, slimy.hitbox.x, slimy.hitbox.y, slimy.hitbox.width, slimy.hitbox.height, null);}
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
		if(e.getKeyCode() == 49) {//whhen you press 1, the boss and it's minions are spawned it
			boss.isAlive = true;
			boss.xpos = 100;
			boss.ypos = 55;
			for (int k = 0; k < servants.length; k++) {
				servants[k].isAlive = true;
				servants[k].xpos = (int) (Math.random() * 1000);
				servants[k].ypos = (int) (Math.random() * 100);
			}
		}
		if(e.getKeyCode() == 72)//when you press h the player tries to use a health potion, and may heal if the coolown has ended
		{player.heal();}


		if(e.getKeyCode() == 65 && player.dx>-10)//when you double press a or d the player does a dash
		{
			left = true;
			if(System.currentTimeMillis()- player.dash<350 && System.currentTimeMillis()- player.dashcooldown >1500)
			{//if the dash cooldown has ended, then you can dash
				player.dx = -20;
				player.iframes = System.currentTimeMillis()+200;
				player.dashcooldown = System.currentTimeMillis();
			}
			player.dash = System.currentTimeMillis();

		}
		if(e.getKeyCode() == 32)
		{
			if(player.isgrounded == true) //lets the player jump while on the ground
			{
				player.dy = -10.5;
				player.ypos = player.ypos - 10;
			}
		}
		if(e.getKeyCode() == 68 && player.dx<10)//lets the player move or dash depending on if they double press and if the cooldown has eneded
		{
			right = true;
			if(System.currentTimeMillis()- player.dash<350 && System.currentTimeMillis()- player.dashcooldown >1500)
			{
				player.dx = 20;
				player.iframes = System.currentTimeMillis()+200;
				player.dashcooldown = System.currentTimeMillis();
			}
			player.dash = System.currentTimeMillis();

		}
		if(e.getKeyCode() == 83)//if the player presses down, they can fall through the arena platforms
		{player.ispassing = true;}


	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == 68)
		{

			right = false;
		}//when you let go of right, you stop moving right
		if(e.getKeyCode() == 65)
		{
			left = false;
		}//when you stop pressing left, you stop moving left.
		if(e.getKeyCode() == 83)
		{player.ispassing = false;
		}// if you let go of down, you no longer pass through the floor.

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {//when you press on the screen, the player attacks
		mousex = e.getX();
		mousey = e.getY();
		double xdif = mousex - (player.xpos + player.width/2);
		double ydif = mousey - (player.ypos + player.height/2);
		player.angle = Math.atan2(ydif,xdif);//determines the angle of the slash to make it above, below, to the left or to the right
		slash.angle = Math.toDegrees(player.angle);
		if(slash.isattacking == false)//gives the slash a small bit of time where it lasts
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