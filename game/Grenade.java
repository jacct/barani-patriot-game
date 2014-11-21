import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.*;

/**
 * provides the parabolic path of the grenade in a parametric equation
 */
public class Grenade extends Thing
{
	/**
	 * constants represent the size of the image and the distance between each frame/draw
	 * fields represent the time(# of frames/draws), angle of the initial throw, 
	 */
	public static final int SIZE = 20;
	public int bounces;
	public boolean explosion;

	/**
	 * makes a new grenade
	 * @param int angle the direction that the grenade will move in
	 * @param int dist the distance between each bullet frame
	 */
    public Grenade(int x, int y, boolean right,BufferedImage img)
    {
    	super(x,y,20,20, img);
    	if(right)
    		dx = (int)(Math.random()*7)+10;
    	else
    		dx = -(int)(Math.random()*7)-10;
    	dy = -(int)Math.sqrt(400-dx*dx);
    	bounces = 0;
    }
	/**
	 * damages any person within the explosion gif
	 * @param LinkedList of all of the enemies and a player
	 */
    public void explosionDamage(LinkedList<Enemy> enemies,Player player)
    {
    	Rectangle re = getRect();
		re.setSize((int)re.getWidth()*8,(int)re.getHeight()*8);
		re.setLocation((int)(re.getX()-re.getWidth()*.72),(int)(re.getY()-re.getHeight()*.72));
		for(Enemy en: enemies)
		{
			if(en.getRect().intersects(re))
				en.health-=100;
		}
		if(player.getRect().intersects(re))
			player.damage(2);
    }
    /**
     * moves the grenade 
     * @param a linkedList of all of the blocks
     */
    public void move(LinkedList<Block> bg)
    {
    	if(explosion)
    		bounces++;
    	else
    	{
	    	dy++;
	    	x+=dx;
	        for(Block b: bg)
	        {
            	Rectangle r= b.getRect();
	            if(r.intersects(getRect()))
	            {
	                if(dx > 0)
	                {
	                	x = (int)r.getX()-w;
	                }
	                else if (dx < 0)
	                    x = (int)r.getX()+(int)r.getWidth();
	                dx = -dx;
	                
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
	                    dy = -(dy-6);
	                    if(dy>0)
	                    	dy = 0;
	                    bounces++;
	                    if(dy>0)
	                    	dy = 0;
	                    if(dx>1)
	                    	dx-=2;
	                    else if(dx<-1)
	                    	dx+=2;
	                    else
	                    	dx=0;
	                    inAir = false;
	                }
	                else if (dy < 0)
	                {
	                	y = (int)r.getY()+(int)r.getHeight();
	                	dy = -(dy+6);
	                	if(dy<0)
	                		dy = 0;
	                	bounces++;
	                    if(dx>0)
	                    	dx-=2;
	                    else if(dx<0)
	                    	dx+=2;
	                }
	            }
	        }
	        if(dx == 0 && dy == 0 && !inAir)
	        {
	        	explosion = true;
	        	bounces = 0;
	        }
    	}	
    } 
    
}