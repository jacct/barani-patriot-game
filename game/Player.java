/** 
 * class representing the player in game
 */

import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;

public class Player extends Thing
{
	public int health,armor,points,fireTimer,lives,fireRate,grenadeTimer;
	public int ammo,grenades,gunDist;
	public float bitcoins;
	public double dir;
	private int counter,counter1;
	public boolean facingRight,shoot;

    //examples of how sprites will be done, this corresponds to sprite number in Thing
    // e.g. setSprite(Player.JUMPING);
    public static final int JUMPING = 9;
    public static final int SIDE = 0;

    /** 
     * Creates a Player.
     * @param x the x coordinate of the left side
     * @param y the y coordinate of the top
     * @param img BufferedImage with player spritesheet
     * @param s information about sprite to use
     */
    public Player(int x, int y,BufferedImage img,String s)
    {
    	///// info on spriteSheets
    	/**
    	 * BORIS   SHEET --- w=64,h=83 
    	 * IVAN    SHEET --- w=72,h=90
    	 * RAHUL   SHEET --- w=55,h=83
    	 * MICHAEL SHEET --- w ...in progress
    	 */
    	
    	super(x,y,Integer.parseInt(s.substring(0,2)),Integer.parseInt(s.substring(2,4)), img,8,1);
		health = 100;

		armor = 100;
		gunDist = Integer.parseInt(s.substring(4));
        dx = 0;
        dy = 0;
        inAir = false;
        bitcoins = 0.0f;
        fireTimer = 0;
        grenades = 100;
        ammo = 20000;
        lives = 3;
        fireRate = -1;
        grenadeTimer = 0;
    }

    /** 
     * damages the player
     * @param dam the amount of damage
     */
	public void damage(int dam)
	{
		if((armor-=dam)<0)// if it is greater than 0, the value still changes but nothing else happens
		{
			health+=armor;
			armor = 0;
		}
		if(health<0)
			health = 0;
	}

    /** 
     * fires bullets from the player
     * @return true if player could successfully fire
     */
	public boolean fire()
	{
		if(shoot && ammo>0)
		{
			ammo--;
			shoot = false;
			fireTimer = 0;
			return true;
		}
		return false;
	}

    /** 
     * throws grenade from the player
     * @return true if player could successfully throw grenade
     */
	public boolean tossGrenade()
	{
		if(grenades<=0 || grenadeTimer>0)
			return false;
		grenades--;
		grenadeTimer=20;
		return true;
	}

    /** 
     * Draws the Player onto a Graphics object with an offset provided by the camera.
     * @param g the Graphics object to draw on
     * @param c Camera to offset with
     */
    public void draw(Graphics g, Camera c)
    {
        // test comment to show matt this stuff
        Rectangle r = c.apply(getRect());
        g.drawImage(currentImage,(int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight(),null,null);
        Point p = c.apply(new Point((int)MouseInfo.getPointerInfo().getLocation().getX(),(int)MouseInfo.getPointerInfo().getLocation().getY()));
		double my = p.getY()-27;/// these should be adjusted so that the top of the panel is accounted for aka
        double mx = p.getX();
        dir = Math.atan(Math.abs((my-(y+getRect().getHeight()/2))/(mx-x)));
		
        if(my<=(y+h/2) && (dx<0||(dx==0&&!facingRight)))///2nd QUAD
        {
        	dir = Math.PI-dir;
        	facingRight = false;
        }
        else if(my>=(y+h/2) &&(dx<0||(dx==0&&!facingRight)))////3rd QUAD
        {
        	dir +=Math.PI;
        	facingRight = false;
        }
        else if(my<=(y+h/2)  && (dx>0||(dx==0&&facingRight)))/// 1st QUAD
        	facingRight = true;
       	else if(my>=(y+h/2) &&(dx>0||(dx==0&&facingRight)))//// 4TH QUAD
       	{
       		dir = Math.PI*2-dir;
       		facingRight = true;
       	}

    }

    /** 
     * moves the player
     * @param bg LinkedList of blocks in the level
     * @param jumping if the player is trying to jump
     */
    public void move(LinkedList<Block> bg, boolean jumping)
    {
    	counter1++;
    	
    	if(health<0)
    		health = 0;
    	if(armor<0)
    		armor = 0;
    	if(dy!=0)
    	{
    		if(facingRight)
    			setSprite(2);
    		else 
    			setSprite(5);
    	}
       	else if(dx!=0)
       	{
	      	if((facingRight)&&counter1%3==0)
	    	{
	    		counter++;
	    		setSprite(counter%3+2);
	    	}
	    	else if(counter1%3==0)
	    	{
	    		counter++;
	    		setSprite(counter%3+5);
	    	}       		
       	}
       	else
       	{
       		if(facingRight)
       			setSprite(0);
       		else
       			setSprite(1);
       	}
		if(grenadeTimer>0)
			grenadeTimer--;
    	if(fireRate>fireTimer)
    		fireTimer++;
    	else if(fireRate==fireTimer)
    	{
    		shoot = true;
    	}
        if(jumping && !inAir)
            dy = -20;
        if(inAir)
            dy++;
        if(dy > 10)
            dy = 10;
        x += dx;
        

       		
        for(Block b: bg)
        {
            Rectangle r= b.getRect();
            if(r.intersects(getRect()))
            {
                if(dx > 0)
                    x = (int)r.getX()-w;
                if (dx < 0)
                    x = (int)r.getX()+(int)r.getWidth();
            }
        }
        y += dy;

        inAir = true;
                
        for(Block b: bg)
        {
            Rectangle r= b.getRect();
            if(r.intersects(getRect()))
            {
                if(dy > 0)
                {
                    y = (int)r.getY()-h;
                    inAir = false;
                    dy = 0;
                }
                if (dy < 0)
                    y = (int)r.getY()+(int)r.getHeight();
            }
        }
    }

}