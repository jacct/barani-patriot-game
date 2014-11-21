/**
 * class representing all the game items
 */
import java.util.*;
import java.awt.*;
import java.awt.image.*;
class Item extends Thing
{
	public static final int BITCOIN = 1;
	public static final int GANJA = 2;
	public static final int HEALTH = 3;
	public static final int ARMOR = 4;
	public static final int CRYSTAL_METH = 5;
	public static final int BLACK_TAR_HERION = 6;
	public static final int CANIAC = 7;
	public static final int CHIPOTLE_BURRITO = 8;
	public int itemType;
	private int width,counter;
	private boolean flip;
	public Item(int x, int y, int w, int h,BufferedImage img, int type)
	{
		super(x,y,w,h,img);
		width = w;
		flip = false;
		itemType = type;
	}

	/** 
     * spins the item around continuously
     */
	public void spin()
	{
		w = flip ? w+1:w-1;
		if(counter==0)
		{
			x = flip ? x-1:x+1;
			counter++;
		}
		else
			counter--;
		if(w>width)
		{
			w--;
			flip = false;
		}
		else if(w<(width*-1))
		{
			w++;
			flip=true;
		}	
	}
	/**
	 * overrides this method so the player won't have trouble picking up the coins
	 * @return rectangle with Item's size and coordinates
	 */
	public Rectangle getRect()
    {
    	return new Rectangle(x,y,width,h);
    }

    /** 
     * Draws the Item onto a Graphics object with an offset provided by the camera.
     * @param g the Graphics object to draw on
     * @param c Camera to offset with
     */
	public void draw(Graphics g, Camera c)
    {
    	Rectangle r = c.apply(getRect());
    	g.drawImage(currentImage,(int)r.getX(),(int)r.getY(), w,h,null,null);
    }
}