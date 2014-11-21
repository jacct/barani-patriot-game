/** 
 * class representing game enemies
 */

import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;


public class Enemy extends Thing
{
	public int moves;
	public double dir;
	public boolean dead, attack,facingRight,fireRounds;
	public int health;
	public int gunDist;
	public float points;
	private int counter,counter1;
    /** 
     * Creates an Enemy
     * @param x the x coordinate of the left side
     * @param y the y coordinate of the top
     * @param xp the amount of health the enemy will spawn with
     * @param img BufferedImage with enemy spritesheet
     * @param String s containing the width of each sprite and height of each sprite and the location of the gun
     */
    public Enemy(int x, int y, int xp,BufferedImage img,String s) 
    {
    	super(x,y,Integer.parseInt(s.substring(0,3)),Integer.parseInt(s.substring(3,6)),img,8,1);
    	if(Integer.parseInt(s.substring(0,3))>100)
    	{
    		health = 1000;
    		points = 100;
    	}
    	else
    	{
    		points = 0.00001f * (int)(Math.random()*xp+4);
    		health = 200;
    		
    	}
    	gunDist = Integer.parseInt(s.substring(6));
    	moves = 0;
    	dead = false;
    }

    /** 
     * Draws the Thing onto a Graphics object with an offset provided by the camera.
     * @param bg LinkedList with all the blocks in the map
     * @param px Player x coordinate
     * @param py Player y coordinate
     */
    public void move(LinkedList<Block> bg,int px, int py)
    {
    	if(dx!=0)
    		facingRight= dx>0;
    	if(health<=0)
    	{
    		dead = true;
    		return;
    	}
    	if(moves>0)
    		moves--;
    	else
    		AI(x-px,y-py);
    	counter1++;
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
    		
    	if(dy>18)
    		dy = 19;
    	else if(inAir)
    		dy++;
       	x+=dx;
        for(Block b: bg)
        {
            Rectangle r= b.getRect();
    		if(r.intersects(getRect()))
    		{
    			if(dx>0)
    				x = (int)r.getX()-w;
    			else if(dx<0)
    				x = (int)r.getX() + (int)r.getWidth();
    		}
    		
    	}
    	y+=dy;
    	inAir = true;
        for(Block b: bg)
        {
            Rectangle r= b.getRect();
    		if(r.intersects(getRect()))
    		{
    			if(dy>0)
    			{
    				y = (int)r.getY() - h;
    				inAir = false;
    				dy = 0;
    			}
    			else if(dy<0)
    				y = (int)r.getY() + (int)r.getHeight();
    		}
    	}
    	
    }
	/**
     * Controls enemy behavior relative to the player
     * Notes: 
	 * positive xDif means that player is to the left of this enemy
	 * positive yDif means that the player is above this enemy
     * @param xDif x difference in location between this Enemy and Player (EnemyX - PlayerX)
     * @param yDif y difference in location between this Enemy and Player (EnemyY - PlayerY)
	 */
    public void AI(int xDif, int yDif)
    {
    	fireRounds = false;
    	attack = false;
    	///// stay still
    	if(Math.random()<.4)
    	{
    		dx=0;
    		dy=0;
    		moves = 20;
    		facingRight = xDif<0;
    		return;
    	}
    	/////  jump
    	if(yDif>10 && !inAir && Math.random()<.34)
    	{
    		dy = -20;
    		y+=dy;
    	}
    	///// player is to the left
    	if(xDif>0)
    	{
    		dir = Math.PI;
    		if(Math.random()<.7)
    			dx = -4;//// move left
    		else
    			dx = 4;///// move right
    	}
    	///// player is to the right
    	else if(xDif<0)
    	{
    		dir = 0;
    		if(Math.random()<.7)
    			dx = 4;//// move right
    		else
    			dx = -4;//// move left
    	}
    	attack = false;
    	////// can attack player (within range)
    	if((yDif>-30 && yDif<30 || gunDist==108 )&&Math.abs(xDif)<500)
    	{
    		if(Math.random()<.5)
    		{
    			attack = true;
    			if(Math.random()<.7)
    				fireRounds = true;
    		}
    	}
    	moves = 20;
    }
}