/**
 * class that handles the game loop, game logic, and player input
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.Timer;


public class SystemComponent extends JPanel implements ActionListener
{
    static final int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    static final int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private LinkedList<Grenade> grenades;
	private LinkedList<Bullet> pBullets;
	private LinkedList<Bullet> bullets;
	private LinkedList<Enemy> enemies;
    private LinkedList<Block> bg;
    private LinkedList<Item> items;
    private LinkedList<Thing> other;
    private BufferedImage grenade,explosion,bitcoin,bullet,burrito;
    private BufferedImage BorisSprites,MichaelSprites,IvanSprites,RahulSprites,enemyImage,FedSprites,PutinSprites;
    private BufferedImage backgroundImage;
    	
	private Cursor crosshair;
    Player player;
    MainMenu menu;
    private Timer timer;
    private Font titleFont, font;
    Camera camera;
    boolean up;

    int bgw, maxlen;

    /**
    * Creates a new SystemComponent
    */
    public SystemComponent()
    {
        menu = new MainMenu(width, height);
        readSprites();
        setFocusable(true); // DONT FORGET THIS OR ELSE KEYADAPTER WILL BE A PAIN
        addKeyListener(new GameKeyListener());
        addMouseListener(new MouseControl());
        timer = new Timer(10, this);
        timer.start();
    }

    private void readSprites()
    {
        try
        {
            explosion = ImageIO.read(getClass().getResourceAsStream("Character Images/explode.png"));
            grenade = ImageIO.read(getClass().getResourceAsStream("Character Images/grenade.png"));
            bitcoin = ImageIO.read(getClass().getResourceAsStream("Character Images/Bitcoin.png"));
            bullet = ImageIO.read(getClass().getResourceAsStream("Character Images/bullet.png"));

            BorisSprites = ImageIO.read(getClass().getResourceAsStream("Character Images/BORIS-SPRITES.png"));
            MichaelSprites = ImageIO.read(getClass().getResourceAsStream("Character Images/MICHAEL-SPRITES.png"));
            IvanSprites = ImageIO.read(getClass().getResourceAsStream("Character Images/IVAN-SPRITES.png"));
            RahulSprites = ImageIO.read(getClass().getResourceAsStream("Character Images/RAHUL-SPRITES.png"));
            enemyImage = ImageIO.read(getClass().getResourceAsStream("Character Images/ENEMYscaled.png"));
            FedSprites = ImageIO.read(getClass().getResourceAsStream("Character Images/FED-SPRITES.png"));
            PutinSprites = ImageIO.read(getClass().getResourceAsStream("Character Images/PUTIN-SPRITES.png"));
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("Character Images/bg4.jpg"));
            burrito = ImageIO.read(getClass().getResourceAsStream("Character Images/burrito.png"));
            
            
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage("Character Images/crosshair.png");
            Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(),this.getY()), "img");
            this.setCursor(c);
        }
        catch(IOException e)
        {
            System.out.println("Could not read sprites!");
        }
    }

    private void generateLevel()
    {
        String[] blocks = new String[25];
        Scanner sc = null;
        try
        {
            sc = new Scanner(new File("map.txt"));
        }
        catch (FileNotFoundException f){}

        for(int i = 0; i < 25; i++)
            blocks[i] = sc.nextLine();
        grenades = new LinkedList<Grenade>();
        pBullets = new LinkedList<Bullet>();
        bullets = new LinkedList<Bullet>();
        enemies = new LinkedList<Enemy>();
        items = new LinkedList<Item>();
        
        bg = new LinkedList<Block>();
        
        switch(menu.selectnum)
        {
        	case 0: player = new Player(213,150,BorisSprites,"648353"); break;
            case 1: player = new Player(213,150,IvanSprites,"729056"); break;
            case 2: player = new Player(213,150,RahulSprites,"568352"); break;
            case 3: player = new Player(213,150,MichaelSprites,"508345"); break;
        }
        int dx = 34;
        int dy = 34;
        
        try
        {   
            Block.spritesheet = ImageIO.read(getClass().getResourceAsStream("Character Images/blocks1.png"));    
        }
        catch(IOException e){}
        
        Block.nSpriteCol = 15;
        Block.nSpriteRow = 11;
        Block.w = dx;
        Block.h = dy;
        
        maxlen = 0;
        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[i].length(); j++)
            {
            	if(blocks[i].length() > maxlen)
            		maxlen = blocks[i].length();
                if(!blocks[i].substring(j,j+1).equals(" "))
                {
                    if(blocks[i].substring(j,j+1).equals("F"))
                        enemies.add(new Enemy(j*dy, i*dx-60,100,FedSprites,"044085055"));
                    else if(blocks[i].substring(j,j+1).equals("A"))
                    	enemies.add(new Enemy(j*dy,i*dx-60,900,enemyImage,"053110054"));
                    else if(blocks[i].substring(j,j+1).equals("O"))
                    	enemies.add(new Enemy(j*dy,i*dx-60,900,PutinSprites,"101214108"));
                    else
                    {
                        Block r = new Block(j*dy+2, i*dx+2, Integer.parseInt(blocks[i].substring(j,j+1)));
                        bg.add(r);
                    }
                }
                
            }
        }
        bgw = dx * maxlen;
        System.out.printf("%d %d%n", height, width);
		
        camera = new Camera(blocks[0].length()*dx,blocks.length*dy);
        up = false;
    }

    /**
    * Resets the game (after loss, etc)
    */
	public void resetGame()
	{
		menu.mainMenuOn = false;
		menu.selectMenuOn = false;
		menu.deadMenuOn = false;
		menu.setPaused(false);
		player.health = 100;
		player.ammo = 500;
		player.grenades = 10;
		player.armor = 100;
		
	}

    /**
    * Resets the level after a death
    */
	public void resetlevel()
	{
		menu.mainMenuOn = false;
		menu.selectMenuOn = false;
		menu.deadMenuOn = false;
		menu.setPaused(false);
		
	}

    /**
    * primary game loop
    * @param e ActionEvent triggered by game timer
    */
    public void actionPerformed(ActionEvent e)
    {
    	if(menu.isMenuOn() ||  menu.isPaused() || menu.deadMenuOn || menu.aboutDescription)
    	{
    		repaint();
    		return;
    	}

    	if(player.health<=0 && !menu.pauseMenuOn)
    		menu.deadMenuOn=true;
    		
        camera.update(player.getRect());
        
        player.move(bg, up);
        for(Item b: items)
        {
        	b.spin();
        }
        
		for(int i = enemies.size()-1; i>=0; i--)
		{
			enemies.get(i).move(bg,player.x,player.y);
			if(enemies.get(i).attack)
			{
				if(!enemies.get(i).fireRounds && enemies.get(i).moves==20)
					grenades.add(new Grenade(enemies.get(i).x,enemies.get(i).y,enemies.get(i).dir==0,grenade));
				else if(enemies.get(i).fireRounds && enemies.get(i).moves%5==0)
				{
					double variance =  Math.random()*Math.PI/24 - Math.PI/48;//// pi/24 to -pi/24 just enough for visual effect
					Enemy nm = enemies.get(i);
					bullets.add(new Bullet(nm.x + nm.w/2,
									nm.y+nm.gunDist,
									nm.dir*-1 +variance,10,bullet));
				}
			}
			if(enemies.get(i).dead)
			{
				player.bitcoins+=enemies.get(i).points;
				
				Enemy ei = enemies.get(i);
				items.add(new Item(ei.x,ei.y+40,30,30,bitcoin,1));
				enemies.remove(i);
			}
		}
		for(Grenade nade: grenades)
		{
			nade.move(bg);
			if(nade.explosion)
				nade.explosionDamage(enemies,player);
		}
		for(int i = bullets.size()-1; i>=0; i--)
		{
			bullets.get(i).move(bg);
			if(bullets.get(i).hitSomething)
				bullets.remove(i);
			else if(bullets.get(i).getRect().intersects(player.getRect()))
			{
				player.damage(3);
				bullets.remove(i);
			}
		}
		for(int i = items.size()-1;i>=0;i--)
		{
			if(items.get(i).itemType==1 && items.get(i).getRect().intersects(player.getRect()))
			{
				items.remove(i);
				player.bitcoins+=Math.random()*.002+.009;
				player.health+=3;
				player.armor+=3;
			}
		}
		for(int i = pBullets.size()-1; i>=0; i--)
		{
			pBullets.get(i).move(bg);
			if(pBullets.get(i).hitSomething)
				pBullets.remove(i);
		}

        for(int i = pBullets.size()-1; i>=0; i--)
        {
            Enemy enemy = pBullets.get(i).hitEnemy(enemies);
            if(enemy != null)
            {
                enemy.health -= 34;
                if(enemy.health <= 0)
                    enemy.dead = true;
                pBullets.remove(i);
            }

        }
        repaint();
    }

    /**
    * draws everything in the game 
    * @param g Graphics object to draw on
    */
    public void paintComponent(Graphics g)
    {

    	g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
		g.setFont(new Font("Bourgeois",Font.PLAIN,40));
        g.setColor(Color.WHITE);
        if(menu.highScoreScreen)
        {
            menu.drawScoreScreen(g);
            return;
        }
        if(menu.isMenuOn())
        {
        	menu.drawMenu(g);
            return;
        }
        else if(menu.aboutDescription)
        {
        	menu.drawAbout(g);
        	return;
        }
        //bgx += player.dx;
        for(int i = 0; i < bgw/width+1; i++)
            g.drawImage(backgroundImage,(int)camera.state.getX()+width*i, 0, width, height,null,null);
        //g.drawImage(backgroundImage,width, height, width, height,null,null);
		if(player.fire())
		{
			double variance =  Math.random()*Math.PI/24 - Math.PI/48;//// pi/24 to -pi/24 just enough for visual effect
			pBullets.add(new Bullet(player.x + (int)(player.getRect().getWidth()/2),
									player.y+player.gunDist,
									player.dir*-1 +variance,30,bullet));
		
		}
		
        g.setColor(Color.CYAN);
        for(int i = 0; i < bg.size(); i++)
        {
            Rectangle r = camera.apply(bg.get(i).getRect());
            g.drawImage(bg.get(i).getImage(),(int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight(),null,null);
        }

		for(Enemy enemy: enemies)
			enemy.draw(g,camera);
			
        for(Bullet b: bullets)
        {
            b.draw(g, camera);
        }
        for(Item b: items)
        {
        	b.draw(g,camera);
        }
        for(Bullet b: pBullets)
        	b.draw(g,camera);
        for(int i = grenades.size()-1; i>=0; i--)
        {
        	if(grenades.get(i).explosion)
        	{
       			Rectangle re = grenades.get(i).getRect();
        		re.setSize((int)re.getWidth()*8,(int)re.getHeight()*8);
        		re.setLocation((int)(re.getX()-re.getWidth()*.72),(int)(re.getY()-re.getHeight()*.72));
        		re = camera.apply(re);
        		g.drawImage(explosion,(int)re.getX(), (int)re.getY(), (int)re.getWidth(), (int)re.getHeight(),null,null);
        		if(grenades.get(i).bounces>10)
        			grenades.remove(i);
        	}
        	else 
        		grenades.get(i).draw(g,camera);
        }
        g.setColor(Color.BLACK);
		g.setFont(new Font("Lucida Sans Regular",Font.PLAIN,40));
		g.setColor(Color.WHITE);
		g.drawString(""+player.armor ,20,30);
		g.drawString("AMMO: " + player.ammo,220,30);
		g.drawString("GRENADES: " + player.grenades,500,30);
		g.setColor(new Color(152,102,137));
		g.drawString(""+player.health+ "\u2764",100,30);
		g.setColor(new Color(218,165,32));
		g.drawString("" + player.bitcoins,900,30);
		
		player.draw(g,camera);
		if(player.health<=0)
		{
			menu.deadMenuOn=true;
			
		}

        /// Pause Window
        if(menu.isPaused())
        {
            menu.drawPauseMenu(g);
            return; // had to add this, or else you could still fall/move if you had a dx/dy while pausing
        }
        else if(menu.deadMenuOn)
        {
        	menu.drawDeathMenu(g);
        }
		
    }
	/**
	 * allows the player to fire
	 */
	class MouseControl extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			player.fireRate = 6;
		}
		public void mouseReleased(MouseEvent e)
		{
			player.fireRate = -1;
		}
	}
	/**
	 * allows the player to move,jump, and throw grenades
	 * allows the menu to change and for the user to select options in the game
	 */
    class GameKeyListener extends KeyAdapter
    {
        // fixed small things, but it still might have to be cleaned up
        /**
         * changes the state of the game when a key is pressed
         */
        public void keyPressed(KeyEvent e) 
        {
            int keyCode = e.getKeyCode();

            if(keyCode == KeyEvent.VK_Q)
            	enemies.add(new Enemy(200,200,100,enemyImage,"5354110"));   
            if(keyCode == KeyEvent.VK_A)
            {
                if(player!=null && !menu.isPaused() && !menu.isMenuOn())
                    player.dx = -6;
                if(menu.isMenuOn())
                    menu.moveUp();
            }
            else if(keyCode == KeyEvent.VK_D)
            {
                if(!menu.isPaused() && !menu.isMenuOn())
                    player.dx = 6;
                if(menu.isMenuOn())
                    menu.moveDown();      
            }
            else if(keyCode == KeyEvent.VK_W)
            {
                if(menu.isMenuOn() || menu.isPaused() || menu.deadMenuOn)
                    menu.moveUp();
                else
                    up = true;
            }
            else if(keyCode == KeyEvent.VK_S)
            {
                if(menu.isMenuOn() || menu.isPaused() || menu.deadMenuOn)
                    menu.moveDown();
            }
            else if(keyCode == KeyEvent.VK_SPACE)
            {
            	if(player!=null && player.tossGrenade())
        		{
        			boolean right = (player.dir<Math.PI/2 || player.dir>Math.PI*3/2);
        			grenades.add(new Grenade(player.x,player.y+(int)(player.getRect().getHeight()/2),right,grenade));
        		}
            }
            else if(keyCode == KeyEvent.VK_ENTER)
            {
                int co = menu.getCurrentOption();
                if(menu.mainMenuOn)
                {
                    if(co == 0)
                        menu.selectMenuOn = true; //menu.selectMenuOn = !(menu.mainMenuOn = false); jacc pls
                    else if(co == 1)
                        menu.aboutDescription = true;
                    else if(co == 2)
                        System.exit(0);
                    menu.mainMenuOn = false;
                    
                }
                else if(menu.selectMenuOn)
                {
                	menu.selectMenuOn = false;
                    generateLevel();
                	if(menu.selectnum==0)
                		player = new Player(213,150,BorisSprites,"648353");
                	else if(menu.selectnum==1)
                		player = new Player(213,150,IvanSprites,"729056");
                	else if(menu.selectnum==2)
                		player = new Player(213,150,RahulSprites,"568352");
                	else if(menu.selectnum==3)
                		player = new Player(213,150,MichaelSprites,"508345");

                }
                else if(menu.isPaused())
                {
                	if(co == 0)
                		menu.setPaused(false);
                	else if(co == 1)
                	{
                        menu.highScoreScreen = true;
                    }
                	else if(co == 2)
                	{
                		menu.mainMenuOn = menu.isPaused();
                		menu.setPaused(false);
                	}
                }
                else if(menu.deadMenuOn)
                {
                    if(co == 0)
                    	menu.deadMenuOn = false;
                    if(co == 1)
                		menu.mainMenuOn = !(menu.deadMenuOn=false);/// reset the game in the main menu
                    generateLevel();
                }
            }
            else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
                menu.setPaused(false);
                menu.mainMenuOn = true;
                menu.aboutDescription = false;
                menu.selectMenuOn = false;
            }
            else if(e.getKeyCode() == KeyEvent.VK_P)
            {
            	menu.setPaused(!menu.isPaused() && !menu.deadMenuOn);
            }
        } 
		/**
		 * changes the player states when the user releases a key
		 */
        public void keyReleased(KeyEvent e)
        {
        	if(player!=null)
        	{
	        	if(e.getKeyCode()==KeyEvent.VK_A)
	        		player.dx = 0;
	        	else if(e.getKeyCode()==KeyEvent.VK_D)
	        		player.dx = 0;
	            else if (e.getKeyCode() == KeyEvent.VK_W)
	                up = false;
        	}
        	
        }      
    }
}

