/*
Braulio Carrion Corveria
Mr. Rosen
6/8/2018
This program is the main class of Minesweeper, other classes only include the time and the splashscreen, this class includes the game itself.
*/

import java.awt.*;
import hsa.Console;
import hsa.Message;
import java.io.*;
import java.util.*;

//left to do = pic of settings,method dictionary

public class Minesweeper //Minesweeper class
{
    Console c = new Console ("Minesweeper"); //Creates a new console window

    /*
		Variable Dictionary
	Name--------------------Type--------------------Description
	row                     int                     This is the total amount of rows in the arrays used, since the maximum grid is 25*25 its 25
	col                     int                     This is the total amount of columns in the arrays used, since the maximum grid is 25*25 its 25
	levelRow                double                  The amount of rows for the specific level
	levelCol                double                  The amount of columns for the specific level
	total                   double                  The total amount of squares in the grid
	level                   int                     Which level was chosen, 1-3
	counter                 int                     Counter counts how many times a loop happened. It's used multiple times throught out this class
	color                   int                     Value for Red color
	color1                  int                     Value for green color
	color2                  int                     Value for blue color
	menuSelection           int                     Which screen comes next, chosen in menu
	highScore               int                     Stores the current high score
	highScores              int                     An array that stores the high scores
	input                   char                    Stores the input of the user
	anything                boolean                 used when a loop has multiple loops in it and break isnt called more than once, anything would then be false
	winOrLose               String                  Checks if the user won or lost and its a string because it displays accordingly
	cheatMode               String                  Checks if the user turned cheat mode on or off and its a string because it displays accordingly
	oldDate                 long                    Finds the date from when the game starts
	mineColor               Color                   Color for mine explosion
	numbersColor            Color                   Color for numbers
	flagColor               Color                   Color for flags
	questionMarkColor       Color                   Color for question mark
	tileBackgroundColor     Color                   Color for blank tiles
	cursorColor             Color                   Color for cursor
	fileName                String                  Name of the file being checked/written
	line                    String                  Line from a file


		Method Dictionary
	Name--------------------Type--------------------Description
	mainMenu                public                  This is where the user chooses which screen to continue to
	pauseProgram            public                  This pauses the program until the user hits a key
	endScreen               public                  This displays whether the user won or not and their score
	instructions            public                  This displays the instructions for the program
	settings                public                  This displays the settings where the user can change the color of things and turn Game Dev mode on or off
	highScores              public                  This displays the games highScores that are stored in a file. The user is also allowed to clear
	goodBye                 public                  This displays a good bye message and then the window closes
	title                   public                  This displays the transition and then the title (clears screen)
	levelSelection          public                  This lets the user select the level they want to play
	spawner                 private                 This generates whether a bomb spawns or not
	gameScreen              public                  This displays minesweeper intself. Displays the grid, generates the numbers, lets the user move around and play the game
	splashScreen            public                  This runs the splashScreen class that displays an animation
	timer                   public                  This runs the timer class that displays the time passed in the game
	main                    public                  This runs all the methods

    */
    static final int row = 25;
    static final int col = 25;
    double levelRow, levelCol, total;
    int level = 1, counter, color, color1, color2, menuSelection = 1, highScore = 0;
    int highScores[] = new int [15];
    char input;
    boolean anything = true;
    String winOrLose = "Lose";
    String cheatMode = "OFF";
    long oldDate;
    Color mineColor;
    Color numbersColor;
    Color flagColor;
    Color questionMarkColor;
    Color tileBackgroundColor;
    Color cursorColor = (Color.white);
    String fileName;
    String line;

    public void mainMenu ()  //mainMenu method displays all the other possible screens the user can go to
    {
	int error = 0; //Error variable to see if a highscore file exists already or not
	title (); //Runs title method
	c.setFont (new Font ("Garamond", 1, 20)); //creates a new font
	c.drawString ("What do you want to do?", 210, 100); //prints string
	c.setFont (new Font ("Garamond", 1, 40)); //new font
	//Menu choices
	c.drawString ("1. Play", 60, 150);
	c.drawString ("2. Instructions", 60, 225);
	c.drawString ("3. Settings", 60, 300);
	c.drawString ("4. High Scores", 60, 375);
	c.drawString ("5. Exit", 60, 450);

	//instructions for the menu
	c.setColor (Color.black);
	c.drawRect (420, 200, 200, 175);
	c.setFont (new Font ("Garamond", 1, 40));
	c.drawString ("W = Up", 430, 250);
	c.drawString ("S = Down", 430, 300);
	c.drawString ("E = Enter", 430, 350);

	fileName = "highScores.txt"; //filename for the current file being checked
	while (true) //loop to check if file exists
	{
	    try
	    {
		if (error == 0) //By default error is 0 and will go through this if statment, here the file is checked and if its blank or doesnt exists then an error message appears
		{
		    error = 1;
		    BufferedReader info;
		    info = new BufferedReader (new FileReader ((fileName)));
		    for (int i = 0 ; i < 15 ; i++)
		    {
			line = info.readLine ();
			highScores [i] = (Integer.parseInt (line));
		    }
		}
		else // it then loops back and creates the file and fills it in with blank scores and continues on
		{
		    error = 2;
		    PrintWriter output1 = new PrintWriter (new FileWriter (fileName));
		    for (int i = 0 ; i < 15 ; i++)
		    {
			output1.println ((0)); //print 0 in file
		    }
		    output1.close ();

		    //Stores all the blank scores into an array
		    BufferedReader info;
		    info = new BufferedReader (new FileReader ((fileName)));
		    for (int i = 0 ; i < 15 ; i++)
		    {
			line = info.readLine ();
			highScores [i] = (Integer.parseInt (line));
		    }
		}
		break;
	    }
	    catch (Exception e)
	    {
		new Message ("No High Score files named 'highScores.txt' were found so a new one was created.", "Error Message"); //error message
	    }
	}

	while (true) //Cursor for the menu
	{
	    c.setColor (cursorColor);
	    c.fillOval (40, 135 + (menuSelection - 1) * 75, 10, 10); //display cursor
	    input = c.getChar (); //get input

	    c.setColor (Color.lightGray);
	    c.fillOval (40, 135 + (menuSelection - 1) * 75, 10, 10); //clear cursor

	    if (input == 'w' && menuSelection != 1) //if w is pressed menu selection cant be 1 because 0 istn an option
	    {
		menuSelection--;
	    }
	    else if (input == 's' && menuSelection != 5) //if s is pressed menu selection cant be 5 because 6 istn an option
	    {
		menuSelection++;
	    }
	    else if (input == 'e') //by pressing e you make your choice and the loop breaks
	    {
		break;
	    }
	}
    }


    public void pauseProgram ()  //pauseProgram method pauses the program until and input is inputted
    {
	c.setColor (Color.white);
	c.setFont (new Font ("Lucida Calligraphy", 1, 35));
	c.drawString ("Press Any Key to Continue...", 20, 475);
	c.getChar (); //get input
    }


    public void endScreen ()  //endScreen method displays the users score and if they won or not.
    {
	title (); //run title method
	c.setFont (new Font ("Garamond", 1, 60));
	c.drawString ("You " + winOrLose + "!", 195, 150); //displays whether you won or lost
	if (highScore == 0)
	{
	    c.drawString ("Your Score is: N/A!", 100, 300); //displays your high score
	}
	else
	    c.drawString ("Your Score is: " + highScore + "!", 100, 300); //displays your high score

	if (highScore != 0) //if high score does not equal (default and not counted) then it will check with the rest of the scores to see if it is lower then any, if so it gets squeezed into that slot and the other numbers get shifted down
	{
	    fileName = "highScores.txt"; //file name for current file
	    try
	    {
		PrintWriter output = new PrintWriter (new FileWriter (fileName));
		for (int i = 5 * level - 5 ; i < 5 * level ; i++) //only checks high score for that level
		{
		    if (highScores [i] > highScore || highScores [i] == 0) //if the current highscore is greater than any high score in the array or a value in the arry is equal to zero then store it and shift the rest over
		    {
			for (int x = (5 * level) - 1 ; x >= i ; x--)
			{
			    if (x == i)
			    {
				highScores [i] = highScore; //once it reaches the original high score that was greater then replace it with the current highscore
			    }
			    else
			    {
				highScores [x] = highScores [x - 1]; //numbers get shifted over until it reaches the first number that is greater than the current highscore
			    }
			}
			break;
		    }
		}
		for (int i = 0 ; i < 15 ; i++) //update the high score file
		{
		    output.println ((highScores [i]));
		}
		output.close ();
	    }
	    catch (IOException e)
	    {
	    }
	}
	pauseProgram (); //run pauseProgram method
    }


    public void instructions ()  //instrucitons method displays how to use the program and play the game
    {
	title (); //run title method
	c.setFont (new Font ("Times New Roman", 1, 20));

	//instructions
	c.drawString ("Welcome to Minesweeper. Playing this game is very addictive!", 30, 100);
	c.drawString ("Use WASD to move around the gird (or menu's) and press F to flag a", 30, 150);
	c.drawString ("block if you know there is a bomb in that location, or press Q to ", 30, 200);
	c.drawString ("add a question mark. Press E to flip over the block your cursor is on. ", 30, 250);
	c.drawString ("The rules for the game are simple. The numbers represent how many", 30, 300);
	c.drawString ("bombs are touching that number. Your goal is to flip over all", 30, 350);
	c.drawString ("the numbers without hitting a bomb. Be careful with those 50/50's.", 30, 400);
	pauseProgram (); //runs pauseProgram
    }


    public void settings ()  //settings method is used to turn cheat mode on or off and to change the color of specific things
    {
	int counter1 = 0; //new variable counter used as a second counter since it would count amount of loops which is already in a loop with the original counter
	title (); //run title method

	//displays the labels of the things being changed in settings
	c.setFont (new Font ("Garamond", 1, 30));
	c.drawString ("Colours", 100, 150);
	c.setFont (new Font ("Garamond", 1, 20));
	c.drawString ("Mine Explosion:", 143, 185);
	c.drawString ("Numbers:", 143, 215);
	c.drawString ("Flag:", 143, 240);
	c.drawString ("Question Mark:", 143, 265);
	c.drawString ("Tile Background:", 143, 290);
	c.drawString ("Cursor:", 143, 315);
	c.drawString ("Transition:", 143, 340);

	//instructions
	c.setColor (Color.black);
	c.drawRect (400, 350, 210, 125);
	c.drawString ("- A and D to move", 405, 375);
	c.drawString ("across colors", 405, 390);
	c.drawString ("- W and S to move", 405, 410);
	c.drawString ("through settings", 405, 425);
	c.drawString ("- E to enter", 405, 445);
	c.drawString ("- Space to exit", 405, 465);

	//this for loop creates the different blocks with the different colors, each if statment is to check if loop is on the next block to also change the color
	for (int y = 0 ; y < 150 ; y = y + 25)
	{
	    counter = 0;
	    for (int x = 0 ; x < 300 ; x = x + 25)
	    {
		if (counter == 0)
		{
		    c.setColor (new Color (255, 0, 0));
		}
		else if (counter == 1)
		{
		    c.setColor (new Color (0, 255, 0));
		}
		else if (counter == 2)
		{
		    c.setColor (new Color (0, 0, 255));
		}
		else if (counter == 3)
		{
		    c.setColor (new Color (150, 22, 255));
		}
		else if (counter == 4)
		{
		    c.setColor (Color.cyan);
		}
		else if (counter == 5)
		{
		    c.setColor (new Color (191, 255, 0));
		}
		else if (counter == 6)
		{
		    c.setColor (Color.gray);
		}
		else if (counter == 7)
		{
		    c.setColor (Color.magenta);
		}
		else if (counter == 8)
		{
		    c.setColor (Color.orange);
		}
		else if (counter == 9)
		{
		    c.setColor (Color.pink);
		}
		else if (counter == 10)
		{
		    c.setColor (Color.white);
		}
		else if (counter == 11)
		{
		    c.setColor (Color.yellow);
		}

		c.fillRect (300 + x, 175 + y, 20, 20);
		counter++;
	    }
	}
	counter = 0; //reset counter
	//this for loop creates the blocks for the last thing since only 3 are needed, each if statment is to check if loop is on the next block to also change the color
	for (int x = 0 ; x < 75 ; x = x + 25)
	{
	    if (counter == 0)
	    {
		c.setColor (new Color (255, 0, 0));
	    }
	    else if (counter == 1)
	    {
		c.setColor (new Color (0, 255, 0));
	    }
	    else if (counter == 2)
	    {
		c.setColor (new Color (0, 0, 255));
	    }
	    c.fillRect (300 + x, 325, 20, 20);
	    counter++;
	}

	counter = 0; //reset counter

	while (true) //setting selection loop
	{
	    c.setFont (new Font ("Garamond", 1, 30));
	    c.setColor (Color.lightGray);
	    c.fillRect (200, 400, 200, 50);
	    c.setColor (Color.white);
	    c.drawString ("Game Dev Mode: " + cheatMode, 100, 425); //displays the cheat mode label, either on or off

	    c.setColor (cursorColor); //changes cursor color
	    c.fillOval (75, 135 + (counter) * 275, 10, 10); //draws cursor
	    input = c.getChar (); //gets input

	    c.setColor (Color.lightGray);
	    c.fillOval (75, 135 + (counter) * 275, 10, 10); // cleats cursor

	    if (input == 'w' && counter != 0) //if w is pressed  counter cant be 1 because 0 istn an option
	    {
		counter--;
	    }
	    else if (input == 's' && counter != 1) //if s is pressed counter cant be 1 because 2 istn an option
	    {
		counter++;
	    }
	    else if (input == 'e') //if e is pressed then the continues on depending on what you had selected
	    {
		if (counter == 0) //if colors was selected then the choosing color process begins
		{
		    for (int x = 0 ; x < 7 ; x++) //this loop goes down vertically after the first color is chosen for the first thing
		    {
			counter1 = 0; //counter1 reset
			int run = 11; //int run checks how many times the loop has ran for since there must be a limit
			if (x == 6) //if x is equal to 6 then the limit is 2 since this is the thing that only has 3 options of colors
			    run = 2;
			while (true) //here the cursor for the colors is displayed
			{
			    c.setColor (Color.black);
			    c.drawRect (297 + counter1 * 25, 172 + x * 25, 25, 25); //displays cursor
			    input = c.getChar (); //gets input
			    c.setColor (Color.lightGray);
			    c.drawRect (297 + counter1 * 25, 172 + x * 25, 25, 25); //clears cursor

			    if (input == 'a' && counter1 != 0) //if a is pressed  counter1 cant be 0 because -1 istn an option
			    {
				counter1--;
			    }
			    else if (input == 'd' && counter1 != run) //if d is pressed  counter1 cant be 11 or 2 because 12 or 3 arent options
			    {
				counter1++;
			    }
			    else if (input == 'e') //if e is pressed the current color that the cursor is on is selected
			    {
				//these sets of if statments first check what youre choosing the color for and then what the color is all depending on the value of counter1 and x
				if (counter1 == 0)
				{
				    if (x == 0)
					mineColor = (new Color (255, 0, 0));
				    if (x == 1)
					numbersColor = (new Color (255, 0, 0));
				    if (x == 2)
					flagColor = (new Color (255, 0, 0));
				    if (x == 3)
					questionMarkColor = (new Color (255, 0, 0));
				    if (x == 4)
					tileBackgroundColor = (new Color (255, 0, 0));
				    if (x == 5)
					cursorColor = (new Color (255, 0, 0));
				    if (x == 6)
				    {
					//since we want the numbers not the actual colors thats whats being set here
					color = 255;
					color1 = 0;
					color2 = 0;
				    }

				}
				else if (counter1 == 1)
				{
				    if (x == 0)
					mineColor = (new Color (0, 255, 0));
				    if (x == 1)
					numbersColor = (new Color (0, 255, 0));
				    if (x == 2)
					flagColor = (new Color (0, 255, 0));
				    if (x == 3)
					questionMarkColor = (new Color (0, 255, 0));
				    if (x == 4)
					tileBackgroundColor = (new Color (0, 255, 0));
				    if (x == 5)
					cursorColor = (new Color (0, 255, 0));
				    if (x == 6)
				    {
					//since we want the numbers not the actual colors thats whats being set here
					color = 0;
					color1 = 255;
					color2 = 0;
				    }
				}
				else if (counter1 == 2)
				{
				    if (x == 0)
					mineColor = (new Color (0, 0, 255));
				    if (x == 1)
					numbersColor = (new Color (0, 0, 255));
				    if (x == 2)
					flagColor = (new Color (0, 0, 255));
				    if (x == 3)
					questionMarkColor = (new Color (0, 0, 255));
				    if (x == 4)
					tileBackgroundColor = (new Color (0, 0, 255));
				    if (x == 5)
					cursorColor = (new Color (0, 0, 255));
				    if (x == 6)
				    {
					//since we want the numbers not the actual colors thats whats being set here
					color = 0;
					color1 = 0;
					color2 = 255;
				    }
				}
				else if (counter1 == 3)
				{
				    if (x == 0)
					mineColor = (new Color (150, 22, 255));
				    if (x == 1)
					numbersColor = (new Color (150, 22, 255));
				    if (x == 2)
					flagColor = (new Color (150, 22, 255));
				    if (x == 3)
					questionMarkColor = (new Color (150, 22, 255));
				    if (x == 4)
					tileBackgroundColor = (new Color (150, 22, 255));
				    if (x == 5)
					cursorColor = (new Color (150, 22, 255));
				}
				else if (counter1 == 4)
				{
				    if (x == 0)
					mineColor = (Color.cyan);
				    if (x == 1)
					numbersColor = (Color.cyan);
				    if (x == 2)
					flagColor = (Color.cyan);
				    if (x == 3)
					questionMarkColor = (Color.cyan);
				    if (x == 4)
					tileBackgroundColor = (Color.cyan);
				    if (x == 5)
					cursorColor = (Color.cyan);
				}
				else if (counter1 == 5)
				{
				    if (x == 0)
					mineColor = (new Color (191, 255, 0));
				    if (x == 1)
					numbersColor = (new Color (191, 255, 0));
				    if (x == 2)
					flagColor = (new Color (191, 255, 0));
				    if (x == 3)
					questionMarkColor = (new Color (191, 255, 0));
				    if (x == 4)
					tileBackgroundColor = (new Color (191, 255, 0));
				    if (x == 5)
					cursorColor = (new Color (191, 255, 0));
				}
				else if (counter1 == 6)
				{
				    if (x == 0)
					mineColor = (Color.gray);
				    if (x == 1)
					numbersColor = (Color.gray);
				    if (x == 2)
					flagColor = (Color.gray);
				    if (x == 3)
					questionMarkColor = (Color.gray);
				    if (x == 4)
					tileBackgroundColor = (Color.gray);
				    if (x == 5)
					cursorColor = (Color.gray);
				}
				else if (counter1 == 7)
				{
				    if (x == 0)
					mineColor = (Color.magenta);
				    if (x == 1)
					numbersColor = (Color.magenta);
				    if (x == 2)
					flagColor = (Color.magenta);
				    if (x == 3)
					questionMarkColor = (Color.magenta);
				    if (x == 4)
					tileBackgroundColor = (Color.magenta);
				    if (x == 5)
					cursorColor = (Color.magenta);
				}
				else if (counter1 == 8)
				{
				    if (x == 0)
					mineColor = (Color.orange);
				    if (x == 1)
					numbersColor = (Color.orange);
				    if (x == 2)
					flagColor = (Color.orange);
				    if (x == 3)
					questionMarkColor = (Color.orange);
				    if (x == 4)
					tileBackgroundColor = (Color.orange);
				    if (x == 5)
					cursorColor = (Color.orange);
				}
				else if (counter1 == 9)
				{
				    if (x == 0)
					mineColor = (Color.pink);
				    if (x == 1)
					numbersColor = (Color.pink);
				    if (x == 2)
					flagColor = (Color.pink);
				    if (x == 3)
					questionMarkColor = (Color.pink);
				    if (x == 4)
					tileBackgroundColor = (Color.pink);
				    if (x == 5)
					cursorColor = (Color.pink);
				}
				else if (counter1 == 10)
				{
				    if (x == 0)
					mineColor = (Color.white);
				    if (x == 1)
					numbersColor = (Color.white);
				    if (x == 2)
					flagColor = (Color.white);
				    if (x == 3)
					questionMarkColor = (Color.white);
				    if (x == 4)
					tileBackgroundColor = (Color.white);
				    if (x == 5)
					cursorColor = (Color.white);
				}
				else if (counter1 == 11)
				{
				    if (x == 0)
					mineColor = (Color.yellow);
				    if (x == 1)
					numbersColor = (Color.yellow);
				    if (x == 2)
					flagColor = (Color.yellow);
				    if (x == 3)
					questionMarkColor = (Color.yellow);
				    if (x == 4)
					tileBackgroundColor = (Color.yellow);
				    if (x == 5)
					cursorColor = (Color.yellow);
				}
				break;
			    }
			}
		    }
		}
		else //if counter = 1 then game dev mode switch gets flicked
		{
		    if (cheatMode.equals ("OFF")) //if its already off it gets turned on
		    {
			cheatMode = "ON";
		    }
		    else // if its already on it gets turned off
		    {
			cheatMode = "OFF";
		    }
		}
	    }
	    else if ((int) input == 32) // if the user hits the space bar then they exit settings
	    {
		break;
	    }
	}
    }


    public void highScores ()  //this method displays all the high scores for all the levels
    {
	title (); //run title method

	// displays the levels at the top
	c.setFont (new Font ("Garamond", 1, 20));
	c.drawString ("High Scores!", 270, 90);
	c.setFont (new Font ("Garamond", 1, 30));
	c.drawString ("Level 1", 100, 150);
	c.drawString ("Level 2", 280, 150);
	c.drawString ("Level 3", 460, 150);
	c.setFont (new Font ("Garamond", 1, 25));

	counter = 0; //reset counter
	for (int x = 100 ; x <= 460 ; x = x + 180) //these for loops displays all the high scores, once done going down the outermost loop shifts the printing of the string over to align with the levels, if the score is 0 it displays N/A
	{
	    for (int y = 225 ; y <= (225 + 50 * 4) ; y = y + 50)
	    {
		if (highScores [counter] == 0)
		{
		    c.drawString ("N/A Seconds", x, y);
		}
		else
		{
		    c.drawString ("" + highScores [counter] + " seconds", x, y);
		}
		counter++;
	    }
	}
	// displays the lines/borders around the scores
	c.drawLine (80, 100, 80, 435);
	c.drawLine (260, 100, 260, 435);
	c.drawLine (440, 100, 440, 435);
	c.drawLine (80, 435, 600, 435);

	c.setColor (Color.white);
	c.setFont (new Font ("Lucida Calligraphy", 1, 21));
	c.drawString ("Press Any Key to go back or C to clear high scores.", 20, 475);

	while (true)
	{
	    counter = 0;
	    input = c.getChar (); //gets input
	    fileName = "highScores.txt"; //filename for the current file being checked
	    if (input == 'c') //if w is pressed  counter cant be 1 because 0 istn an option
	    {
		try
		{
		    //clears high scores
		    PrintWriter output1 = new PrintWriter (new FileWriter (fileName));
		    for (int i = 0 ; i < 15 ; i++)
		    {
			output1.println ((0)); //print 0 in file
		    }
		    output1.close ();

		    //Stores all the blank scores into an array
		    BufferedReader info;
		    info = new BufferedReader (new FileReader ((fileName)));
		    for (int i = 0 ; i < 15 ; i++)
		    {
			line = info.readLine ();
			highScores [i] = (Integer.parseInt (line));
		    }
		}
		catch (IOException e)
		{
		}
		break;
	    }
	    else //if s is pressed counter cant be 1 because 2 istn an option
	    {
		counter = 1;
		break;
	    }
	}
	if (counter != 1)
	{
	    highScores ();
	}
    }


    public void goodbye ()  //goodBye method displays a good bye message and displays the creators name
    {
	title (); //run title method
	//displays good bye message and creators name
	c.setFont (new Font ("Times New Roman", 1, 40));
	c.drawString ("Thank you for playing this game!", 30, 200);
	c.drawString ("By: Braulio Carrion Corveira", 65, 300);
	pauseProgram (); // runs pauseProgram method
	c.close (); // closes console window
    }


    public void title ()  // this is the title method, this clears the screen and displays the title but before that it displays a transformation
    {
	c.setColor (Color.black);
	int[] [] animation = new int [25] [32]; //new array for the animation since it will display block randomly
	counter = 0; //counter resest
	while (true) //this loop will get a random square in screen and adds it to the array that way that blocked wont be called again and the transformation can finish
	{
	    int x = ((int) (Math.random () * (32)) + (0)); //random x value of block
	    int y = ((int) (Math.random () * (25)) + (0)); //random y value of bloack
	    if (animation [y] [x] != 3) // if that block isnt displayed already it continues on to display a new block
	    {
		c.setColor (new Color (((int) (Math.random () * (color)) + (1)), ((int) (Math.random () * (color1)) + (1)), ((int) (Math.random () * (color2)) + (1))));
		c.fillRect (x * 20, y * 20, 20, 20); //displays block
		animation [y] [x] = 3; //that block can no longer be revealed again throughout this loop
		counter++;
		try
		{
		    Thread.sleep (1); //slight delay between block reveal
		}
		catch (Exception e)
		{
		}
		if (counter == 24 * 31) //loop breaks once all the blocks have been revealed
		{
		    c.clear ();
		    break;
		}
	    }
	}
	//displays title and colors background
	c.setColor (Color.lightGray);
	c.fillRect (0, 0, 640, 500);
	c.setFont (new Font ("Lucida Calligraphy", 1, 40));
	c.setColor (Color.white);
	c.drawString ("Minesweeper", 170, 50);
	c.drawLine (160, 65, 480, 65);
    }


    public void levelSelection ()  //level selection
    {
	title (); //runs title method
	//displays the level choices
	c.setFont (new Font ("Garamond", 1, 30));
	c.drawString ("Level 1", 100, 150);
	c.drawString ("Level 2", 280, 150);
	c.drawString ("Level 3", 460, 150);

	//displays the sizes for the grids
	c.setFont (new Font ("Garamond", 1, 25));
	c.drawString ("9x9 Grid", 100, 275);
	c.drawString ("16x16 Grid", 280, 275);
	c.drawString ("25x25 Grid", 460, 275);

	//displays the amount of bombs in those grids
	c.drawString ("10 Bombs", 100, 325);
	c.drawString ("40 Bombs", 280, 325);
	c.drawString ("100 Bombs", 460, 325);

	//displays a border around everything
	c.drawLine (80, 100, 80, 350);
	c.drawLine (260, 100, 260, 350);
	c.drawLine (440, 100, 440, 350);
	c.drawLine (80, 350, 600, 350);

	//instructions for menu
	c.setColor (Color.black);
	c.drawRect (260, 375, 180, 100);
	c.setFont (new Font ("Garamond", 1, 40));
	c.drawString ("A = Left", 263, 405);
	c.drawString ("D = Right", 263, 435);
	c.drawString ("E = Enter", 263, 465);

	while (true) //loop to get the level choice
	{
	    c.setColor (cursorColor);
	    c.drawRect (90 + (level - 1) * 180, 125, 110, 30); //cursor
	    input = c.getChar ();

	    c.setColor (Color.lightGray);
	    c.drawRect (90 + (level - 1) * 180, 125, 110, 30); //clear cursor

	    if (input == 'a' && level != 1) //if a is pressed it moves to the left until the very end
	    {
		level--;
	    }
	    else if (input == 'd' && level != 3) //if d is pressed it moves to the right until the very end
	    {
		level++;
	    }
	    else if (input == 'e') // if e is pressed it selects that level deppending where the cursor is
	    {
		break;
	    }
	}
    }


    private int spawner ()  //return method for creations of bombs
    {
	return ((int) (Math.random () * (total)) + (1));
    }


    public void gameScreen ()  //game screen method, this is where almost everything is done, grid is created, values are generated, and game process happens
    {
	/*
		    Variable Dictionary
	    Name--------------------Type--------------------Description
	    bombCounter             int                     Counts how many bombs has been spawned
	    amountOfBombs           int                     Amount of bombs that MUST be spawned
	    dimension               int                     Since the grid is the same regardless of level the 2nd grid has be a bit bigger so it would be a perfect square so dimension is added
	    row1                    int                     Location of cursor
	    col1                    int                     Location of cursor
	    dimensionOfSquares      int                     This is the dimension of each individual block
	    clicks                  int                     Amount of inputs the user made (enters/clicks)
	    firstMove               int                     If the user made the first move or note, if so specific actions must occur
	    bombCount               int                     This is a counter for the amount of bombs around a block
	    x1                      int                     Amount of times the dimensionOfSquares has to be multiplied
	    y1                      int                     Amount of times the dimensionOfSquares has to be multiplied
	    flippedNumber           int                     An array to store whether a tile has been flipped or not
	    number                  int                     An array that stores the whole grid
	*/
	int bombCounter, amountOfBombs = 0, dimension = 0, row1 = 0, col1 = 0, dimensionOfSquares, clicks = 0, firstMove = 1, bombCount = 0, x1 = 0, y1 = 0;
	highScore = 0;
	int[] [] flippedNumber = new int [row] [col];
	int[] [] number = new int [row] [col];

	title (); //runs title method
	c.setColor (Color.lightGray);
	c.fillRect (0, 0, 640, 500);
	fileName = "Final Answer.txt"; //current file

	//setting values for the level chosen
	if (level == 1)
	{
	    levelRow = 9;
	    levelCol = 9;
	    amountOfBombs = 10;
	}
	else if (level == 2)
	{
	    levelRow = 16;
	    levelCol = 16;
	    amountOfBombs = 40;
	}
	else if (level == 3)
	{
	    levelRow = 25;
	    levelCol = 25;
	    amountOfBombs = 100;
	}


	total = (levelRow * levelCol); //calculates total amount of blocks
	// setting up the bombs and non bombs
	while (true) // this is the generation process of the game. This is to only generate bombs and no bombs, numbers happen later on
	{
	    bombCounter = 0; //bombCounter reset
	    counter = 0; //counter reset
	    for (int rowCount = 0 ; rowCount < levelRow ; rowCount++) //for loop for the rows
	    {
		for (int colCount = 0 ; colCount < levelCol ; colCount++) //for loop for the columns
		{
		    if (bombCounter == amountOfBombs) //if all the bombs have already spawned then set everything else to not a bomb
		    {
			number [rowCount] [colCount] = 1;
			counter = counter + 1;
		    }
		    else //get a random value, if it matches the values that a bomb would be then change the numbers array value to 9. If it isnt a bomb it fchnages the value to 1
		    {
			number [rowCount] [colCount] = spawner ();

			if (number [rowCount] [colCount] >= 1 && number [rowCount] [colCount] <= amountOfBombs)
			{
			    number [rowCount] [colCount] = 9;
			    bombCounter = bombCounter + 1;
			}
			else
			{
			    number [rowCount] [colCount] = 1;
			}
			counter = counter + 1;
		    }
		}
	    }

	    if (bombCounter == amountOfBombs && counter == (levelRow * levelCol)) //this loop will only break once all the bombs have been spawned and it has finished setting a value for all the numbers in the gird. If it finihses the generation but not enough bombs spawned then it does it again
	    {
		break;
	    }
	}


	while (true) //this loop checks the non bomb and counts how many bombs are next to and sets the value accordingly
	{
	    try
	    {
		PrintWriter output = new PrintWriter (new FileWriter (fileName));
		for (int rowCount = 0 ; rowCount < levelRow ; rowCount++) //for loop for the amount of rows
		{
		    for (int colCount = 0 ; colCount < levelCol ; colCount++) //for loop for the amount of columns
		    {
			//these if statments make it so that you dont check a value outside of the array/grid limit and crash it
			int up = 1, down = 1, left = 1, right = 1, diag1 = 1, diag2 = 1, diag3 = 1, diag4 = 1;
			if (rowCount == 0)
			{
			    up = 0;
			    diag1 = 0;
			    diag2 = 0;
			}
			if (colCount == 0)
			{
			    left = 0;
			    diag1 = 0;
			    diag4 = 0;
			}
			if (rowCount == (levelRow - 1))
			{
			    down = 0;
			    diag4 = 0;
			    diag3 = 0;
			}
			if (colCount == (levelCol - 1))
			{
			    right = 0;
			    diag2 = 0;
			    diag3 = 0;
			}
			//these if statmenst first check if the number is checking is not a bomb, if it isnt then it checks the blocks next to it and if it is a bomb it adds 1 to its value
			if (number [rowCount] [colCount] == 1)
			{
			    if (number [rowCount - diag1] [colCount - diag1] == 9)
			    {
				bombCount++;
			    }
			    if (number [rowCount - diag2] [colCount + diag2] == 9)
			    {
				bombCount++;
			    }
			    if (number [rowCount + diag3] [colCount + diag3] == 9)
			    {
				bombCount++;
			    }
			    if (number [rowCount + diag4] [colCount - diag4] == 9)
			    {
				bombCount++;
			    }
			    if (number [rowCount - up] [colCount] == 9)
			    {
				bombCount++;
			    }
			    if (number [rowCount + down] [colCount] == 9)
			    {
				bombCount++;
			    }
			    if (number [rowCount] [colCount - left] == 9)
			    {
				bombCount++;
			    }
			    if (number [rowCount] [colCount + right] == 9)
			    {
				bombCount++;
			    }
			    number [rowCount] [colCount] = bombCount; //sets the finaly number on how many bombs are near by
			    output.println ((number [rowCount] [colCount])); //prints the value into a file
			    bombCount = 0; //resets bomb count
			}
			else if (number [rowCount] [colCount] == 9)
			{
			    output.println ((number [rowCount] [colCount])); // if its a bomb print out 9, since you can never have 9 bombs near by
			}
		    }
		}
		output.close ();
	    }
	    catch (IOException e)
	    {
	    }
	    break;
	}

	c.setColor (Color.black);
	c.setFont (new Font ("Garamond", 1, 20));
	if (level == 2)
	{
	    dimension = 14; //if level 2 was chosen then dimesnion will equal 14 instead of 0 that way it would be a perfect square
	}

	// making the grid changes size if it is level 2 since size is not PERFECT,
	c.drawRect (95, 25, 450 + dimension, 450 + dimension);
	dimensionOfSquares = (int) Math.ceil ((450 + dimension) / levelRow);
	for (int i = 95 ; i < 545 + dimension ; i = i + dimensionOfSquares) //this for loop draws all the vertical lines
	{
	    c.drawLine (i, 25, i, 475 + dimension);
	}
	for (int i = 25 ; i < 475 + dimension ; i = i + dimensionOfSquares) // this for loop draws all the horizontal lines
	{
	    c.drawLine (95, i, 545 + dimension, i);
	}
	if (cheatMode.equals ("ON")) // this displays the answers of the grid if cheatMode is on
	{
	    try
	    {
		BufferedReader info;
		info = new BufferedReader (new FileReader ((fileName)));
		for (int y = 25 ; y < 475 ; y = y + (int) Math.ceil (450 / (levelRow)))
		{
		    for (int x = 95 ; x < 545 ; x = x + (int) Math.ceil (450 / (levelCol)))
		    {
			line = info.readLine ();
			c.drawString (line, 5 + x, y + 15);
		    }
		}
	    }
	    catch (IOException e)
	    {
	    }
	}
	//this for loop draws all the rectangles for the instructions
	for (int y = 0 ; y < 65 * 5 ; y = y + 65)
	{
	    c.drawRect (570, 50 + y, 60, 45);
	}
	//instructions for the games inputs
	c.setFont (new Font ("Garamond", 1, 15));
	c.drawString ("F = Flag", 572, 73);
	c.drawString ("Q = ", 572, 128);
	c.drawString ("Question", 571, 142);
	c.drawString ("Mark", 572, 156);
	c.drawString ("WASD =", 572, 202);
	c.drawString ("move", 572, 218);
	c.drawString ("E = Flip", 572, 270);
	c.drawString ("Space =", 572, 325);
	c.drawString ("Quit", 572, 345);
	c.drawRect (10, 50, 70, 60);
	c.drawString ("Time:", 15, 65);


	// this creates a file with a 0 in it, another class will constantly check the file until the 0 changes that way the class knows its own loop should break
	try
	{
	    PrintWriter output2 = new PrintWriter (new FileWriter ("time.txt"));
	    output2.println ((0));
	    output2.close ();
	}
	catch (IOException e)
	{
	}

	for (int y = 0 ; y < row ; y++) // this for loops sets all the blocks to not be flipped over
	{
	    for (int x = 0 ; x < col ; x++)
		flippedNumber [y] [x] = 0;
	}

	firstMove = 1; // first move set to 1
	anything = true; //anything set to true that way the game can be played again
	while (anything) // this is the main loop for the game tha controls movement and what gets flipped over
	{
	    c.setFont (new Font ("Garamond", 1, 20));
	    c.setColor (cursorColor);
	    c.drawRect (95 + x1 * dimensionOfSquares, 25 + y1 * dimensionOfSquares, dimensionOfSquares, dimensionOfSquares); //cursor
	    input = c.getChar (); //get user input

	    c.setColor (Color.black);
	    c.drawRect (95 + x1 * dimensionOfSquares, 25 + y1 * dimensionOfSquares, dimensionOfSquares, dimensionOfSquares); //clear cursor

	    if (input == 'c' && cheatMode.equals ("ON")) //if the user presses c and cheatMode is on then the game ends in a win instantly
	    {
		winOrLose = "Win";
		try
		{
		    PrintWriter output2 = new PrintWriter (new FileWriter ("time.txt"));
		    output2.println ((1));
		    output2.close ();
		}
		catch (IOException e)
		{
		}
		anything = false;

	    }
	    else if (input == 'w' && row1 != 0) //if the user presses w the amount of times the y coordinates has to happen is reduced and the cursor then changes to a location above only if this it not on the edge
	    {
		y1--;
		row1 = row1 - 1;
	    }
	    else if (input == 'a' && col1 != 0) //if the user presses w the amount of times the x coordinates has to happen is reduced and the cursor then changes to a location to the left only if this it not on the edge
	    {
		x1--;
		col1 = col1 - 1;
	    }
	    else if (input == 's' && row1 != levelRow - 1) //if the user presses s the amount of times the y coordinates has to happen is increased and the cursor then changes to a location below only if this it not on the edge
	    {
		y1++;
		row1 = row1 + 1;
	    }
	    else if (input == 'd' && col1 != levelCol - 1) //if the user presses w the amount of times the x coordinates has to happen is increased and the cursor then changes to a location to the left only if this it not on the edge
	    {
		x1++;
		col1 = col1 + 1;
	    }
	    else if (input == 'f') //if the user press f and that block is blank it adds the flag, if that block already has a flag it removes it
	    {
		if (flippedNumber [row1] [col1] == 2)
		{
		    c.setColor (Color.lightGray);
		    c.fillRect (95 + x1 * dimensionOfSquares + 1, 25 + y1 * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
		    flippedNumber [row1] [col1] = 4;
		}
		else if (flippedNumber [row1] [col1] != 1 && flippedNumber [row1] [col1] != 3)
		{
		    c.setColor (flagColor);
		    c.drawRect (95 + x1 * dimensionOfSquares + 5, 25 + y1 * dimensionOfSquares + 5, dimensionOfSquares - 10, dimensionOfSquares - 10);
		    flippedNumber [row1] [col1] = 2;
		}
	    }
	    else if (input == 'q') //if the user press q and that block is blank it adds the question mark, if that block already has a question mark it removes it
	    {
		if (flippedNumber [row1] [col1] == 1)
		{
		    c.setColor (Color.lightGray);
		    c.fillRect (95 + x1 * dimensionOfSquares + 1, 25 + y1 * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
		    flippedNumber [row1] [col1] = 4;
		}
		else if (flippedNumber [row1] [col1] != 3 && flippedNumber [row1] [col1] != 2)
		{
		    c.setColor (questionMarkColor);
		    c.drawString ("?", 95 + x1 * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + y1 * dimensionOfSquares + dimensionOfSquares / 2 + 7);
		    flippedNumber [row1] [col1] = 1;
		}
	    }
	    else if ((int) input == 32) //if the user presses the space bar then the game ends with a lose
	    {
		try
		{
		    PrintWriter output2 = new PrintWriter (new FileWriter ("time.txt"));
		    output2.println ((1)); //prints 1 to the time file so the timer class stops
		    output2.close ();
		}
		catch (IOException e)
		{
		}
		break;
	    }
	    else if (input == 'e') //if the person press e then  many actions will occur depending on many factors.
	    {
		if (firstMove == 1) //if its the first move when e is clicked start the time
		{
		    Date date = new Date ();
		    oldDate = date.getTime ();
		    firstMove = 0;
		}
		if (flippedNumber [row1] [col1] != 2 && flippedNumber [row1] [col1] != 1 && flippedNumber [row1] [col1] != 3) //if the press e and its not on a tile that is occupied
		{
		    clicks++; //adds 1 to clicks
		    c.setColor (numbersColor); //sets color of number
		    c.drawString ("" + (number [row1] [col1]), 95 + x1 * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + y1 * dimensionOfSquares + dimensionOfSquares / 2 + 7); //prints the value of the block selected
		    if (number [row1] [col1] == 9) //if they block they selected was a bomb
		    {
			try
			{
			    PrintWriter output2 = new PrintWriter (new FileWriter ("time.txt"));
			    output2.println ((1)); //sends the value of 1 to time file so that the timer stops
			    output2.close ();
			}
			catch (IOException e)
			{
			}

			//This displays the explosion after the game is over
			for (int i = 0 ; i <= 400 ; i++)
			{
			    c.setColor (mineColor);
			    c.drawOval (320 - i, 250 - i, i * 2, i * 2);
			    try
			    {
				Thread.sleep (1);
			    }
			    catch (Exception e)
			    {
			    }
			}

			for (int i = 0 ; i <= 400 ; i++)
			{
			    c.setColor (Color.black);
			    c.drawOval (320 - i, 250 - i, i * 2, i * 2);
			    try
			    {
				Thread.sleep (3);
			    }
			    catch (Exception e)
			    {
			    }
			}
			anything = false; //ends the loop
		    }
		    if (number [row1] [col1] == 0) //if they hit a 0, or a blank
		    {
			flippedNumber [row1] [col1] = 3; //sets the number in that postition to 3 so i cant be clicked again
			c.setColor (Color.black);
			c.setFont (new Font ("Garamond", 1, 20));
			c.setColor (tileBackgroundColor); //sets color for the blank tiles
			c.fillRect (95 + (x1) * dimensionOfSquares + 1, 25 + (y1) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1); //displays the tile

			//these if statments make sure that the values dont go past the array/grid limit
			int up = 1, down = 1, left = 1, right = 1, diag1 = 1, diag2 = 1, diag3 = 1, diag4 = 1;

			if (row1 == 0)
			{
			    up = 0;
			    diag1 = 0;
			    diag2 = 0;
			}


			if (col1 == 0)
			{
			    left = 0;
			    diag1 = 0;
			    diag4 = 0;
			}


			if (row1 == (levelRow - 1))
			{
			    down = 0;
			    diag4 = 0;
			    diag3 = 0;
			}


			if (col1 == (levelCol - 1))
			{
			    right = 0;
			    diag2 = 0;
			    diag3 = 0;
			}

			//each of these if statments flips over the values next to the one clicked, if its a number it flips it over and displays it, if its blank then it displays the tile
			if (flippedNumber [row1 - diag1] [col1 - diag1] != 3 && flippedNumber [row1 - diag1] [col1 - diag1] != 2 && flippedNumber [row1 - diag1] [col1 - diag1] != 1)
			{
			    if (number [row1 - diag1] [col1 - diag1] != 0)
			    {
				c.setColor (numbersColor);
				c.drawString ("" + (number [row1 - diag1] [col1 - diag1]), 95 + (x1 - diag1) * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + (y1 - diag1) * dimensionOfSquares + dimensionOfSquares / 2 + 7);
			    }
			    else
			    {
				c.setColor (tileBackgroundColor);
				c.fillRect (95 + (x1 - diag1) * dimensionOfSquares + 1, 25 + (y1 - diag1) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
			    }
			    clicks = clicks + diag1;
			    flippedNumber [row1 - diag1] [col1 - diag1] = 3;
			}


			if (flippedNumber [row1 - diag2] [col1 + diag2] != 3 && flippedNumber [row1 - diag2] [col1 + diag2] != 2 && flippedNumber [row1 - diag2] [col1 + diag2] != 1)
			{
			    if (number [row1 - diag2] [col1 + diag2] != 0)
			    {
				c.setColor (numbersColor);
				c.drawString ("" + (number [row1 - diag2] [col1 + diag2]), 95 + (x1 + diag2) * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + (y1 - diag2) * dimensionOfSquares + dimensionOfSquares / 2 + 7);
			    }
			    else
			    {
				c.setColor (tileBackgroundColor);
				c.fillRect (95 + (x1 + diag2) * dimensionOfSquares + 1, 25 + (y1 - diag2) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
			    }
			    clicks = clicks + diag2;
			    flippedNumber [row1 - diag2] [col1 + diag2] = 3;
			}


			if (flippedNumber [row1 + diag3] [col1 + diag3] != 3 && flippedNumber [row1 + diag3] [col1 + diag3] != 2 && flippedNumber [row1 + diag3] [col1 + diag3] != 1)
			{
			    if (number [row1 + diag3] [col1 + diag3] != 0)
			    {
				c.setColor (numbersColor);
				c.drawString ("" + (number [row1 + diag3] [col1 + diag3]), 95 + (x1 + diag3) * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + (y1 + diag3) * dimensionOfSquares + dimensionOfSquares / 2 + 7);
			    }
			    else
			    {
				c.setColor (tileBackgroundColor);
				c.fillRect (95 + (x1 + diag3) * dimensionOfSquares + 1, 25 + (y1 + diag3) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
			    }
			    clicks = clicks + diag3;
			    flippedNumber [row1 + diag3] [col1 + diag3] = 3;
			}


			if (flippedNumber [row1 + diag4] [col1 - diag4] != 3 && flippedNumber [row1 + diag4] [col1 - diag4] != 2 && flippedNumber [row1 + diag4] [col1 - diag4] != 1)
			{
			    if (number [row1 + diag4] [col1 - diag4] != 0)
			    {
				c.setColor (numbersColor);
				c.drawString ("" + (number [row1 + diag4] [col1 - diag4]), 95 + (x1 - diag4) * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + (y1 + diag4) * dimensionOfSquares + dimensionOfSquares / 2 + 7);
			    }
			    else
			    {
				c.setColor (tileBackgroundColor);
				c.fillRect (95 + (x1 - diag4) * dimensionOfSquares + 1, 25 + (y1 + diag4) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
			    }
			    clicks = clicks + diag4;
			    flippedNumber [row1 + diag4] [col1 - diag4] = 3;
			}


			if (flippedNumber [row1 - up] [col1] != 3 && flippedNumber [row1 - up] [col1] != 2 && flippedNumber [row1 - up] [col1] != 1)
			{
			    if (number [row1 - up] [col1] != 0)
			    {
				c.setColor (numbersColor);
				c.drawString ("" + (number [row1 - up] [col1]), 95 + x1 * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + (y1 - up) * dimensionOfSquares + dimensionOfSquares / 2 + 7);
			    }
			    else
			    {
				c.setColor (tileBackgroundColor);
				c.fillRect (95 + (x1) * dimensionOfSquares + 1, 25 + (y1 - up) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
			    }
			    clicks = clicks + up;
			    flippedNumber [row1 - up] [col1] = 3;
			}


			if (flippedNumber [row1 + down] [col1] != 3 && flippedNumber [row1 + down] [col1] != 2 && flippedNumber [row1 + down] [col1] != 1)
			{
			    if (number [row1 + down] [col1] != 0)
			    {
				c.setColor (numbersColor);
				c.drawString ("" + (number [row1 + down] [col1]), 95 + x1 * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + (y1 + down) * dimensionOfSquares + dimensionOfSquares / 2 + 7);
			    }
			    else
			    {
				c.setColor (tileBackgroundColor);
				c.fillRect (95 + (x1) * dimensionOfSquares + 1, 25 + (y1 + down) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
			    }
			    clicks = clicks + down;
			    flippedNumber [row1 + down] [col1] = 3;
			}


			if (flippedNumber [row1] [col1 - left] != 3 && flippedNumber [row1] [col1 - left] != 2 && flippedNumber [row1] [col1 - left] != 1)
			{
			    if (number [row1] [col1 - left] != 0)
			    {
				c.setColor (numbersColor);
				c.drawString ("" + (number [row1] [col1 - left]), 95 + (x1 - left) * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + y1 * dimensionOfSquares + dimensionOfSquares / 2 + 7);
			    }
			    else
			    {
				c.setColor (tileBackgroundColor);
				c.fillRect (95 + (x1 - left) * dimensionOfSquares + 1, 25 + (y1) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
			    }
			    clicks = clicks + left;
			    flippedNumber [row1] [col1 - left] = 3;
			}


			if (flippedNumber [row1] [col1 + right] != 3 && flippedNumber [row1] [col1 + right] != 2 && flippedNumber [row1] [col1 + right] != 1)
			{
			    if (number [row1] [col1 + right] != 0)
			    {
				c.setColor (numbersColor);
				c.drawString ("" + (number [row1] [col1 + right]), 95 + (x1 + right) * dimensionOfSquares + dimensionOfSquares / 2 - 4, 25 + y1 * dimensionOfSquares + dimensionOfSquares / 2 + 7);
			    }
			    else
			    {
				c.setColor (tileBackgroundColor);
				c.fillRect (95 + (x1 + right) * dimensionOfSquares + 1, 25 + (y1) * dimensionOfSquares + 1, dimensionOfSquares - 1, dimensionOfSquares - 1);
			    }
			    clicks = clicks + right;
			    flippedNumber [row1] [col1 + right] = 3;
			}
		    }
		    if (clicks == (levelRow * levelCol) - amountOfBombs) //if the amount of clicks is equal to the amount of tiles minus the amount of bombs
		    {
			Date date2 = new Date ();
			long newDate = date2.getTime (); //gets the new time after the game is over
			highScore = (int) Math.ceil ((newDate - oldDate) / 1000); //subtracts the new time by the old time to get the total time passed, its then divided by 1000 to change milliseconds to seconds
			if (highScore > 999) //if the time is greater than 999 then it sets it back to 999
			{
			    highScore = 999;
			}
			winOrLose = "Win"; //sets winOrLose to Win
			try
			{
			    PrintWriter output2 = new PrintWriter (new FileWriter ("time.txt"));
			    output2.println ((1)); //prints 1 in the time file to stop the timer class
			    output2.close ();
			}
			catch (IOException e)
			{
			}
			anything = false;
		    }

		    flippedNumber [row1] [col1] = 3; //after a tile is clicked and it wasnt already flipped then it sets the value to 3 so that way it can not be flipped again
		}
	    }
	    if (firstMove == 0) //after the first click happens firstMove is set to 0 so that timer class starts
	    {
		timer (); //runs the timer method
		firstMove = 2; //first move is set to 2 so that it doesnt call timer again
	    }
	}
    }


    public void splashScreen ()  //splashScreen method runs the splashScreen class
    {
	SplashScreen ss = new SplashScreen (c);
	ss.run ();
	pauseProgram ();
    }


    public void timer ()  //timer methods runs the timer class
    {
	timer ss = new timer (c);
	ss.start ();
    }


    public static void main (String[] args)  //main method
    {
	Minesweeper ms = new Minesweeper ();
	ms.splashScreen ();

	while (true)
	{
	    ms.mainMenu ();
	    if (ms.menuSelection == 1) //if menuSelection is 1 it goes through play and all the methods in it
	    {
		ms.levelSelection ();
		ms.gameScreen ();
		ms.endScreen ();
	    }
	    else if (ms.menuSelection == 2) //if menuSelection is 2 it goes to instructions
	    {
		ms.instructions ();
	    }

	    else if (ms.menuSelection == 3) //if menuSelection is 3 it goes to settings
	    {
		ms.settings ();
	    }
	    else if (ms.menuSelection == 4) //if menuSelection is 4 it goes to highScores
	    {
		ms.highScores ();
	    }
	    else //if menuSelection is anything else it goes to goodBye and exits the loop and programs
	    {
		ms.goodbye ();
		break;
	    }
	}
    }
}



