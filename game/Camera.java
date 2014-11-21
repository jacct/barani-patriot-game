/**
 * class that handles the camera that follows the player
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.Timer;


class Camera
{
    Rectangle state;

    public Camera(int w, int h)
    {
        state = new Rectangle(0,0,w,h);
    }
	/**
	 * applies the point given adjust and stay on the screen
	 * is used for the mouse and the trig involved in shooting
	 * @param Point p the point to be adjusted
	 * @return Point p that has been adjusted
	 */
	public Point apply(Point p)
	{
		return new Point((int)p.getX()-(int)state.getX(),(int)p.getY()-(int)state.getY());
	}
	/**
	 * returns a Rectangle that is shifted by the window of the screen
	 * @param rectangle r
	 * @return Rectangle that has been shifted
	 */
    public Rectangle apply(Rectangle r)
    {
        return new Rectangle((int)r.getX()+(int)state.getX(),(int)r.getY()+(int)state.getY(),(int)r.getWidth(),(int)r.getHeight());
    }
	/**
	 * moves the window based on the new location of the player
	 * @param Rectangle p of the player
	 */
    public void update(Rectangle p)
    {
        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int HALF_WIDTH = width/2;
        int HALF_HEIGHT = height/2;
        //set state
        int l, t;
        l = (int)p.getX();
        t = (int)p.getY();
        l = -l+HALF_WIDTH;
        t = -t + HALF_HEIGHT + HALF_HEIGHT/3;

        l = Math.min(0, l);
        //l = Math.max(-(width-HALF_WIDTH), l);
        //t = Math.max(-(height-HALF_HEIGHT), t);
        t = Math.min(0, t);

        state = new Rectangle(l, t, width, height);
    }
}