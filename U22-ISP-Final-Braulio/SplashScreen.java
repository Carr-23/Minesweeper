/*
Braulio Carrion Corveria
Mr. Rosen
6/8/2018
This is the splash screen class which will display the animation intro
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class SplashScreen implements Runnable
{
    private Console c;

    public void displaySplashScreen ()  //this method will display a foot hitting a mine and it exploding
    {
	c.setColor (new Color (122, 250, 255)); //sky
	c.fillRect (0, 0, 640, 500);

	c.setColor (Color.green); //grass
	c.fillRect (0, 385, 640, 200);

	c.setColor (new Color (237, 119, 23)); //flags
	c.fillRect (45, 325, 60, 30);
	c.fillRect (175, 355, 25, 15);
	c.fillRect (405, 205, 145, 80);

	c.setColor (Color.black); //flag pole
	c.drawLine (45, 325, 45, 440);
	c.drawLine (175, 355, 175, 395);
	c.drawLine (550, 205, 550, 440);

	c.setColor (Color.darkGray); //mine
	c.fillRect (245, 370, 150, 50);
	c.setColor (Color.red); //mine button
	c.fillRect (290, 360, 60, 10);

	for (int i = -1 ; i <= 305 ; i++) //this for loop animates the foot coming down towards the mine
	{
	    c.setColor (new Color (122, 250, 255)); //foot eraser
	    c.fillRect (220, -30 + i, 160, 85);

	    c.setColor (new Color (255, 213, 155)); //leg
	    c.fillRect (290, 0, 60, -15 + i);

	    c.setColor (new Color (132, 89, 29)); //boot
	    c.fillRect (270, -20 + i, 100, 85);
	    c.fillOval (220, i, 92, 65);
	    try
	    {
		Thread.sleep (5);
	    }
	    catch (Exception e)
	    {
	    }
	}

	for (int i = 430 ; i >= 0 ; i--) //this for loop draws the line of the smoke/fire mushroom (the shaft)
	{
	    c.setColor (Color.red);
	    c.drawLine (220, i, 400, i);
	}

	for (int i = 0 ; i <= 200 ; i++) //this for loop draws the first big circle of smoke/fire
	{
	    c.drawOval (310 - i, 175 - i, i * 2, i);
	    try
	    {
		Thread.sleep (1);
	    }
	    catch (Exception e)
	    {
	    }
	}
	c.fillOval (110, -25, 400, 200); //fills up the oval so there is no pixel gaps


	for (int i = 0 ; i <= 150 ; i++) //this for loop draws the second big circle of smoke/fire
	{
	    c.drawOval (310 - i, 200, i * 2, 100);
	    try
	    {
		Thread.sleep (1);
	    }
	    catch (Exception e)
	    {
	    }
	}
	c.fillOval (160, 200, 300, 100); //fills up the oval so there is no pixel gaps

	c.setFont (new Font ("Berlin Sans FB Demi", 1, 39));
	for (int i = 0 ; i <= 100 ; i++) //this draws the title minesweeper come into the screen
	{
	    c.setColor (Color.red);
	    c.fillRect (180, i - 50, 262, 50);
	    c.setColor (Color.yellow);
	    c.drawString ("MINESWEEPER", 180, i);
	    try
	    {
		Thread.sleep (5);
	    }
	    catch (Exception e)
	    {
	    }
	}
	for (int i = 39 ; i <= 55 ; i++) //this for loop increases the size of the title
	{
	    c.setColor (Color.red);
	    c.fillOval (110, -25, 400, 200);
	    c.setColor (Color.yellow);
	    c.setFont (new Font ("Berlin Sans FB Demi", 1, i));
	    c.drawString ("MINESWEEPER", 180 - i, 100);
	    try
	    {
		Thread.sleep (6);
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    public SplashScreen (Console con)
    {
	c = con;
    }


    public void run ()  //When the thread is started, this method will be called to run the animation.
    {
	displaySplashScreen ();
    }
} // splashScreen class


