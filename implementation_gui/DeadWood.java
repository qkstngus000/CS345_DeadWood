package implementation_gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
* Class: DeadWood
* Description: 
*   This class will be the first class to be called to initiate and call other classes to
*   start program. Its main role is to interact with players and set up the game environment
*   as well as keep traking of the game system until game is over.
*/
public class DeadWood {
    private int day; // Take cares of days that players play depending on their number
	private int maxDay;
    private int numPlayer; // Input of number of players
    private Player[] players; // List of players to keep track of turn
    private Board board;
    private int curPlayer; // Index in players
    private static Random dice; // Dice Roll
	private static DeadWood game = new DeadWood();
	public static final Scanner feed = new Scanner(System.in);

    /*
     * Constructor: DeadWood
     * Parameter:
     *   numPlayer: number of players
     * Description:
     *   Does nothing but initialize the game. Must be included to hide the constructor.
     */
    private DeadWood() {
    }

    /*
     * Function: setEnv()
     * Parameter:
	 * 		int numPlayer: number of players
     * Description:
     * 		Sets up the environment for the game by creating player objects and the board object
	 * 		and initializing day values.
     */
    private void setEnv(int numPlayer) {
        // initialize numPlayer
        this.numPlayer = numPlayer;
		// initialize days
		day = 0;
		maxDay = numPlayer < 4 ? 3 : 4;
		// create board
		board = new Board();
		System.out.println("Board created!");
		// initialize players
		players = new Player[numPlayer];
		for (int i = 0; i < numPlayer; i++)
		{
			players[i] = new Player("Player" + (i+1));
			if (numPlayer == 5) players[i].addCredits(2);
			else if (numPlayer == 6) players[i].addCredits(4);
			else if (numPlayer > 6) players[i].setRank(2);
			System.out.println("Player " + i + " initialized!");
		}
    }

    /*
     * Function: trackDate
     * Parameter:
     * None
     * Description:
     * When board reset, decrement day until it hit 0
    
    public void trackDate() {
        // TO DO
    } */

    /*
     * Function: winner
     * Parameter:
     * None
     * Description:
     * Bring total Score from each player and compare the highest score
     * Call out the winner
     
    public void winner() {
        // TO DO
    }*/

    /*
     * Function: nextTurnPlayer
     * Parameter: None
     * Description:
     * Keep track of which player's turn it is and call playerTurn for that Player.
     */
    public void nextTurnPlayer() {
		players[curPlayer].playerTurn();
        curPlayer = (curPlayer + 1) % numPlayer;
    }
	
	/*
	* Function: rollDice
	* Returns:
	*   number of dice rolled
	* Description: rolls 6-sided dice to generate random numbers and returns the result.
	*/
	public static int rollDice()
	{
		return (dice.nextInt(6) + 1);
	}
	
	/*
	* Function: gameLoop
	* Parameter: none
	* Description: called by the main method and performs the main sequence of events for the game,
	* including setup, player turns, passing of days, and the game end.
	*/
	public void gameLoop()
	{
		
		// Loop for daily events
		while(day <= maxDay)
		{
			System.out.printf("~~~~~~ Day %d Start ~~~~~~%n",day);
			// reset board and move players to trailer room.
			board.reset();
			for(int i = 0; i < numPlayer; i++)
			{
				players[i].setRoom(board.getStartingRoom());
				players[i].removeRole();
			}
			
			// Loop for turns
			while(board.getScenesLeft() > 0)
			{
				// do player turn
				nextTurnPlayer();
			}
			
			day++;
		}
		// Print scores and find who won
		System.out.println("~~~~~~ The last day has passed! ~~~~~~");

		System.out.println("Player Scores:");
		ArrayList<Integer> winners = new ArrayList<Integer>();
		int hiscore = 0;
		for(int i = 0; i < numPlayer; i++)
		{
			int newscore = players[i].calcScore();
			System.out.printf("\t%-20s: %d%%n",players[0].getName(),newscore);
			// track highest scores
			if(newscore >= hiscore)
			{
				// if there is a new high score, clear winner list
				if(newscore > hiscore)
				{
					winners.clear();
				}
				// add to winner list and update high score
				winners.add(i);
				hiscore = newscore;
			}
		}
		// Print out winners
		if(winners.size() == 1)
		{
			System.out.printf("%s wins with a score of %d!%n",players[winners.get(0)].getName(),hiscore);
		}
		else
		{
			System.out.printf("There was a %d-way tie! The winners, each with a score of %d, are:%n",winners.size(),hiscore);
			for(Integer n : winners)
			{
				System.out.printf("\t%s%n",players[n].getName());
			}
		}
	}
	
	public static boolean isInteger(String s)
	{
		try
		{
			Integer.parseInt(s);
		} catch (Exception E)
		{
			return false;
		}
		return true;
	}
	
    /*
     * main function
     * Description:
     * Ask for how many users are there to play game, then creates a new instance of DeadWood and runs the game.
     */
    public static void main(String[] args) {
		dice = new Random();
		System.out.print("Enter the number of players who would like to play:\n\t");
		String usrEntry = feed.nextLine();
		while (!isInteger(usrEntry)) {
			System.out.print("Please enter a valid number of players.\n\t");
			usrEntry = feed.nextLine();
		}
		int pcount = Integer.parseInt(usrEntry);
		game.setEnv(pcount);
		game.gameLoop();

		feed.close();
    }
}