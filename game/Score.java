/***
 * a class that holds a score and date achieved
 */

import java.util.*;

public class Score implements Comparable<Score>
{
	private int points;
	private int time;
    /**
     * Creates a score object.
     * @param points points for player
     * @param date date score was achieved
     */
    public Score(int points, int time) 
    {
    	this.points = points;
    	this.time = time;
    }

    /** 
     * Returns the date the score was achieved
     * @return date the score was achieved
     */
    public int getTime()
    {
    	return time;
    }

    /** 
     * Returns the points
     * @return points
     */
    public int getPoints()
    {
    	return points;
    }
    /** 
     * Compares one score object with another based on points, and if the points are the same compare by date
     * @param s the score to compare to
     * @return which score is larger
     */
    public int compareTo(Score s)
    {
    	if(points == s.getPoints())
    		return s.getTime() - time;
    	return s.getPoints() - points;
    }
    
}