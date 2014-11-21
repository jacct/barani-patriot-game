import javax.swing.*;
import java.awt.*;
import java.io.*;
/**
 * a class that makes a frame and adds the system component to the frame
 */
public class Runner
{
	public static void main(String args[]) throws IOException, InterruptedException
	{
		JFrame frame = new JFrame();
		
		final int FRAME_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		final int FRAME_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("BARANI PATRIOTS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SystemComponent coffee = new SystemComponent();
		frame.add(coffee);
		frame.setVisible(true);
    } 
}