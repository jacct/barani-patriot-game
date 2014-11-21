/**
 * class that stores a saving and loading scores
 */

import java.util.*;
import java.io.*;
import java.text.*;

public abstract class ScoreList 
{
	/**
	 * reads the highScores from a file
	 * @param String fileName
	 */
    public static TreeMap<Score,String> loadScores(String fileName) throws IOException,ParseException
    {
    	Scanner sc = new Scanner(new File(fileName));
    	TreeMap<Score,String> map = new TreeMap<Score,String>();
    	while(sc.hasNext())
    	{
    		int points = Integer.parseInt(sc.nextLine());
    		String name = sc.nextLine();
    		int time = Integer.parseInt(sc.nextLine());
            System.out.println(points+" " + name + " " +time);
    		map.put(new Score(points,time),name);
    	}
    	return map;
    }
    /**
     * saves the scores to a file
     * @param String fileName and the treeMap of scores and string names
     */
    public static void saveScores(String fileName,TreeMap<Score,String> map) throws IOException
    {
    	FileWriter f = new FileWriter(new File(fileName));
    	for(Score s: map.keySet())
    	{
    		f.write("" + s.getPoints() + " " + s.getTime() + " " + map.get(s) + "/n");
    	}
    }
    
}