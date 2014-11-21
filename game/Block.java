/** 
 * class representing blocks/tiles in game
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Block
{
	public static BufferedImage spritesheet;
	public static int nSpriteCol, nSpriteRow;
	public static  int w, h;
	
	public BufferedImage currentImage;
	Rectangle rect;
	
	
	/** 
     * Creates a Block.
     * @param x the x coordinate of the left side
     * @param y the y coordinate of the top
     * @param s sprite to use from global Block spritesheet
     */
	public Block(int x, int y, int s)
	{
		rect = new Rectangle(x,y,w,h);
		setSprite(s);
	}
    
    /** 
     * Returns a Rectangle with the coordinates and size of the Block
     * @return Rectangle with the coordinates and size of the Block
     */
    public Rectangle getRect()
    {
    	return rect;
    }

    /** 
     * Returns image for block
     * @return image for block
     */
    public BufferedImage getImage()
    {
    	return currentImage;
    }

    /**
     * Sets the current sprite based on the spritesheet, or doesn't do anything if there is no spritesheet.
     * Spritesheet format will be as follows:
     * 0 1 2 3 (-> nCol-1)
     * nCol + 1 nCol+2 etc.
     * @param num the number sprite to set the Block to
     */
    public void setSprite(int num)
    {
    	currentImage = spritesheet.getSubimage(num/nSpriteCol*w, (num%nSpriteRow)*h, w, h);
    }
}