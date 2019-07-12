/*
Braulio Carrion Corveria
Mr. Rosen
6/8/2018
This program is the time class of Minesweeper
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;
import java.io.*;

public class timer extends Thread
{
    private Console c;

    public void displayTimer ()  //this method will display the time in the console window
    {
	int time = 0; //once the first move happens the program starts and time starts from zero
	while (true) //this loop clears the old time and displays the current time on the stop watch, only in seconds so it updates every second
	{
	    c.setColor (Color.lightGray); //clear time
	    c.fillRect (14, 70, 64, 30);
	    c.setColor (Color.red);
	    c.drawString ("" + time, 30, 95); //display time
	    //delay for time
	    try
	    {
		Thread.sleep (1000);
	    }
	    catch (Exception e)
	    {
	    }
	    time++; // after the 1 second time is increased by 1
	    if (time == 999) //the loop will break when time is equal to 999 since thats the max time amount
	    {
		break;
	    }
	    // the time class always checks the time.txt file to see if the mainClass has told it to stop or not
	    try
	    {
		BufferedReader info;
		info = new BufferedReader (new FileReader (("time.txt")));
		if (info.readLine ().equals ("1")) //if it read at 1 it should break the loop
		{
		    break;
		}
	    }
	    catch (IOException e)
	    {
	    }
	}
    }



    public timer (Console con) 
    {
	c = con;
    }


    public void run ()  //When the thread is started, this method will be called to run the time.
    {
	displayTimer ();
    }
} // time class


