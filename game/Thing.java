/**
 * class that represents most game objects including Enemies and Items
 */

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;


public class Thing
{
	protected int w, h;
	protected int dx,dy,x, y;
	protected boolean inAir;
	public int nSpriteCol, nSpriteRow;
	protected BufferedImage spritesheet;
	protected BufferedImage currentImage;

	 /** 
     * Creates a Thing with just 1 image (no spritesheet).
     * @param xLeft the x coordinate of the left side
     * @param yTop the y coordinate of the top
     * @param width the width of the Thing
     * @param height the height of the Thing
     * @param img BufferedImage with the image
     */
	public Thing(int xLeft, int yTop, int width, int height, BufferedImage img)
	{
		this(xLeft, yTop, width, height, img, 1, 1);
	}

	/** 
     * Creates a Thing with a spritesheet.
     * @param xLeft the x coordinate of the left side
     * @param yTop the y coordinate of the top
     * @param width the width of the Thing
     * @param height the height of the Thing
     * @param img BufferedImage with the spritesheet for the Thing
     * @param nCol the number of columns in the spritesheet
     * @param nRow the number of rows in the spritesheet
     */
	public Thing(int xLeft, int yTop, int width, int height, BufferedImage img, int nCol, int nRow)
	{
		x = xLeft;
		y = yTop;
		w = width;
		h = height;
		spritesheet = img;
		nSpriteCol = nCol;
		nSpriteRow = nRow;
		setSprite(0);
	}

	/** 
     * Draws the Thing onto a Graphics object.
     * @param g the Graphics object to draw on
     */
    public void draw(Graphics g)
    {
    	if(currentImage != null)
    		g.drawImage(currentImage,x,y,w,h,null,null);
    }

     /** 
     * Draws the Bullet onto a Graphics object with an offset provided by the camera.
     * @param g the Graphics object to draw on
     * @param c Camera to offset with
     */
    public void draw(Graphics g, Camera c)
    {
        Rectangle r = c.apply(getRect());
        g.drawImage(currentImage,(int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight(),null,null);
    }
    
    /** 
     * Returns a Rectangle with the coordinates and size of the Thing
     * @return Rectangle with the coordinates and size of the Thing
     */
    public Rectangle getRect()
    {
    	return new Rectangle(x,y,w,h);
    }

    /**
     * Sets the current sprite based on the spritesheet, or doesn't do anything if there is no spritesheet.
     * Spritesheet format will be as follows:
     * 0 1 2 3 (-> nCol-1)
     * nCol + 1 nCol+2 etc.
     * @param num the number sprite to set the Thing to
     */
    public void setSprite(int num)
    {
    	if(nSpriteCol*nSpriteRow==1)
    		currentImage = spritesheet;
    	else
    		currentImage = spritesheet.getSubimage((num%nSpriteCol)*w, (num%nSpriteRow)*h, w, h);
    }

}