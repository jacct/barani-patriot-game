/**
 * class representing the game menus
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;

public class MainMenu
{
	private BufferedImage pats,boris,afshan,rahul,michael,spatter;
	int height, width;

	public boolean mainMenuOn;
	public boolean selectMenuOn;
	public boolean pauseMenuOn;
	public boolean deadMenuOn;
	public boolean aboutDescription;
    public boolean highScoreScreen;
	static final int BORIS = 0;
    static final int IVAN = 1;
    static final int RAHUL = 2;
    static final int MICHAEL = 3;
    boolean cheat;

    String[] menustrings,pausestrings,selectstrings,deathstrings;

    int menunum, pausenum, selectnum,deathnum;

    /** 
     * Creates a game menu object
     * @param w width of component to draw on
     * @param h height of component to draw on
     */
    public MainMenu(int w, int h)
    {
    	width = w;
    	height = h;
    	menunum = 0;
    	pausenum = 0;
    	selectnum = 0;
    	deathnum = 0;
    	try
    	{
    		menustrings = new String[]{"Start","About","Quit "};
    		pausestrings = new String[]{"Resume","GimmeGanja","Quit Game"};
    		selectstrings = new String[]{"Boris","Ivan Cats","Ragul","Hanky"};
    		deathstrings = new String[]{"play again", "main menu"};
    		
			pats = ImageIO.read(getClass().getResourceAsStream("Character Images/thePatGames.png"));
			boris = ImageIO.read(getClass().getResourceAsStream("Character Images/assad.png"));
			afshan = ImageIO.read(getClass().getResourceAsStream("Character Images/IVAN.png"));
			rahul = ImageIO.read(getClass().getResourceAsStream("Character Images/dhir.png"));
			michael = ImageIO.read(getClass().getResourceAsStream("Character Images/hankinGats.png"));
			spatter = ImageIO.read(getClass().getResourceAsStream("Character Images/spatter.png"));
    	}
    	catch (IOException e)
    	{
			
    	}
		
    	mainMenuOn = true;
        pauseMenuOn = false;
        selectMenuOn = false;
        highScoreScreen = false;
    }

    /** 
     * Draws the menu on a Graphics object
     * @param g the Graphics object to draw on
     */
    public void drawMenu(Graphics g)
    {
    	if(mainMenuOn)
    		drawMainMenu(g);
    	else if(selectMenuOn)
    		drawSelectMenu(g);
    }

    private void drawMainMenu(Graphics g)
    {
    	g.drawImage(pats,width*2/7,0,width*3/7,height,null,null);
        for(int i = 0; i < menustrings.length; i++)
        {
        	int centerX = width/2;
        	int centerMidPic = width*5/14;
        	FontMetrics f = g.getFontMetrics();
        	String hsString = selectstrings[i];
        	int hsWidth = f.stringWidth(hsString);
        	int strHeight = f.getHeight();
            if(i == menunum)
                g.setColor(Color.BLUE);
            g.drawString(menustrings[i],centerMidPic , 50 * i+50);
            g.setColor(Color.WHITE);
        }
    }
	
    /** 
     * Draws the about screen on a Graphics object
     * @param g the Graphics object to draw on
     */
	public void drawAbout(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(new Font("Bourgeois",Font.PLAIN,30));
		g.drawString("In a country threatened by the tyranny of Putin, the Barani Patriots find",10,100);
		g.drawString("themselves targeted by the Russian government. Faced with drone strikes,",10,200);
		g.drawString("the KGB, and even the Robotic Putin Patrol (RPP), the patriots are forced ",10,300);
		g.drawString("to flee Moscow and become gangstas in Kiev. Now equipped with kalashnikovs,",10,400);
		g.drawString(" the Barani Patriots decide to fight their way out of exile and back to Mother Russia.",10,500);
		g.drawString("PRESS ESCAPE TO RETURN TO THE MAIN MENU",10,600);
	}
	
    private void drawSelectMenu(Graphics g)
    {
    	g.setFont(new Font("Bourgeois",Font.PLAIN,40));
    	g.setColor(Color.DARK_GRAY);
    	g.fillRect(0,0,width,height);
    	for(int i =0; i<selectstrings.length;i++)
    	{
       		g.setColor(Color.WHITE);
    		if(i==selectnum)
    			g.setColor(Color.RED);
            int centerX = width/2;
        	int centerY = height/2;
        	int q1 = centerX/2;
        	int q3 = centerX +centerX/2;
        	FontMetrics f = g.getFontMetrics();
        	String hsString = selectstrings[i];
        	int hsWidth = f.stringWidth(hsString);
        	int strHeight = f.getHeight();
        	g.drawString(hsString,q1*i+q1/2-hsWidth/2,strHeight);
    		if(i==BORIS)
    			g.drawImage(boris,q1*i+q1/2-100,centerY-150,200,300,null,null);
    		else if(i==IVAN)
    			g.drawImage(afshan,q1*i+q1/2-100,centerY-150,200,300,null,null);
    		else if(i==RAHUL)
    			g.drawImage(rahul,q1*i+q1/2-100,centerY-150,200,300,null,null);
    		else
    			g.drawImage(michael,q1*i+q1/2-100,centerY-150,200,300,null,null);
    	}
    }

    /** 
     * Moves one menu option up (any menu)
     */
    public void moveUp()
    {
    	if(mainMenuOn)
    	{
    		menunum--;
    		if(menunum < 0)
    			menunum = menustrings.length-1;
    	}
    	else if (selectMenuOn)
    	{
    		if(--selectnum < 0)
    			selectnum = selectstrings.length-1;
    	}
    	else if (pauseMenuOn)
    	{
    		pausenum--;
            if(pausenum<0)
            	pausenum = pausestrings.length-1;
    	}
    	else if(deadMenuOn)
    	{
    		deathnum--;
    		if(deathnum<0)
    			deathnum = deathstrings.length-1;
    	}
		
    }

    /** 
     * Moves one menu option down (any menu)
     */
    public void moveDown()
    {
    	if(mainMenuOn)
    		menunum = (++menunum)%menustrings.length;
    	else if (selectMenuOn)
    		selectnum = (++selectnum)%selectstrings.length;
    	else if (pauseMenuOn)
    		pausenum = (++pausenum)%pausestrings.length;
    	else if(deadMenuOn)
    		deathnum = (++deathnum)%deathstrings.length;
    }

    /** 
     * gets current menu option (any menu)
     * @return current menu option
     */
    public int getCurrentOption()
    {
    	if(mainMenuOn)
    		return menunum;
    	else if(deadMenuOn)
    		return deathnum;
    	else if(selectMenuOn)
    		return selectnum;
    	else
    		return pausenum;
    }

    /** 
     * gets menu state 
     * @return if main or select menu is on
     */
    public boolean isMenuOn()
    {
    	return mainMenuOn || selectMenuOn;
    }

    /** 
     * gets pause state 
     * @return pause state (true = paused)
     */
    public boolean isPaused()
    {
    	return pauseMenuOn;
    }

    /** 
     * sets pause state 
     * @param pause state (true = paused)
     */
    public void setPaused(boolean p)
    {
    	pauseMenuOn = p;
    }

    /** 
     * Draws the death menu on a Graphics object
     * @param g the Graphics object to draw on
     */
	public void drawDeathMenu(Graphics g)
	{
    	int centerX = width/2;
   		int centerY = height/2;
   		g.drawString("YOU DIED",centerX-100,centerY-100);
   		for(int i = 0; i<deathstrings.length;i++)
    	{
    			
    		g.setColor(Color.WHITE);
    		if(deathnum==i)
    			g.setColor(Color.RED);
    		g.drawString(deathstrings[i],centerX+60,centerY + 30 + 50*i);
    		
    	}
	}
    /** 
     * Draws the pause menu on a Graphics object
     * @param g the Graphics object to draw on
     */
    public void drawPauseMenu(Graphics g)
    {
    	int centerX = width/2;
   		int centerY = height/2;
   		
   		g.setColor(new Color(111,0,0));
   		g.fillRect(centerX-300,centerY-162,600,325);
			g.setColor(Color.WHITE);
   		for(int dy = -162 + 25; dy<163; dy+=50)
   		{
   			g.fillRect(centerX-300,centerY+dy,600,25);
   		}
   		g.setColor(new Color(0,0,32));
   		g.fillRect(centerX+48,centerY-12,253,176);
   		
    	for(int i = 0; i<pausestrings.length; i++)
    	{
    			
    		g.setColor(new Color(0,0,32));
    		if(pausenum==i)
    			g.setColor(Color.WHITE);
    		if(cheat&& pausenum ==i)
    			g.drawString("GANJA MODE",centerX+50,centerY+30+50*i);
    		else
    			g.drawString(pausestrings[i],centerX+60,centerY + 30 + 50*i);
    		
    	}
    }
	/**
	 * draws the score screen on a graphics object
	 * @param Graphics g
	 */
    public void drawScoreScreen(Graphics g)
    {
        int centerX = width/2;
        int centerY = height/2;
        FontMetrics f = g.getFontMetrics();
        String hsString = "HIGH SCORES";
        int hsWidth = f.stringWidth(hsString);
        int strHeight = f.getHeight();
        g.drawString(hsString,centerX-hsWidth/2,strHeight);
        TreeMap<Score,String> scores = null;

        try
        {
            scores = ScoreList.loadScores("scores.txt");
        }
        catch(Exception e){System.out.println(e);System.exit(1);}

        int counter = 1;

        //System.out.println(scores);

        int thirdX = width/3;
        for (Score score: scores.keySet())
        {
            String points = score.getPoints() + "";
            String time = score.getTime() + "";
            String name = scores.get(score);
            g.drawString(points,thirdX*2-hsWidth/2,strHeight+counter*strHeight);

            g.drawString(name,thirdX-hsWidth/2,strHeight+counter*strHeight);
            counter++;



        }

    }


}