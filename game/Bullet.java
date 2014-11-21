/** 
 * class representing bullets in game
 */

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.*;

public class Bullet extends Thing
{
	/**
	 * fields represent location(2D), direction(degree)
	 * static constants represent the distance the bulletMoves between each frame 
	 * and the size of the image
	 */
	private double angleDir;
	private BufferedImage bulletImg;
	public static final int SIZE = 20;
	public static final int DISTANCE = 1;
	public int dista;
	public boolean hitSomething;

	/**
	 * makes a new bullet
	 * @param int angle the direction that the bullet will move in
	 * @param int dist the distance between each bullet frame
	 */
    public Bullet(int x, int y, double angle, int dist,BufferedImage img)
    {
    	super(x,y,30,6, img); // wtf jacc);
    	angleDir = angle;
    	dista = dist;
    }
	
    /**
     * moves the bullet using the angle and DISTANCE
     */
    public void move(LinkedList<Block> bg)
    {
    	x += (int)(Math.cos(angleDir)*dista); //jacc's first commit!
    	y += (int)(Math.sin(angleDir)*dista);
        for(Block b: bg)
        {
            Rectangle r= b.getRect();
    		if(r.intersects(getRect()))
    			hitSomething = true;
    	}
    }

    /**
    * returns the enemy the bullet hit, if any
    * @return reference to the Enemy bullet has hit, or null if none
    */
    public Enemy hitEnemy(LinkedList<Enemy> enemies)
    {
        for(Enemy e : enemies)
        {
            Rectangle r= e.getRect();
            if(r.intersects(getRect()))
                return e;
        }
        return null;
    }
}