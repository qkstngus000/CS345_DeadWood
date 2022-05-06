package implementation;

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
    public static Random dice; // Dice Roll

    /*
     * Constructor: DeadWood
     * Parameter:
     *   numPlayer: number of players
     * Description:
     *   Creates the board and player objects and initializes various values for this DeadWood game.
     */
    public DeadWood(int numPlayer) {
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
			players[i] = new Player("Player" + i);
			if (numPlayer > 4) players[i].addCredits(2);
			System.out.println("Player " + i + " initialized!");
		}
    }

    /*
     * Function: setEnv()
     * Parameter:
     * None
     * Description:
     * With number of players given, create player obejects with corresponding
     * staring information.
     * Set day according to number of players
     */
    public void setEnv() {
        // TO DO
    }

    /*
     * Function: trackDate
     * Parameter:
     * None
     * Description:
     * When board reset, decrement day until it hit 0
     */
    public void trackDate() {
        // TO DO
    }

    /*
     * Function: winner
     * Parameter:
     * None
     * Description:
     * Bring total Score from each player and compare the highest score
     * Call out the winner
     */
    public void winner() {
        // TO DO
    }

    /*
     * Function: nextTurnPlayer
     * Parameter: None
     * Description:
     * Keep track of which player's turn it is and call playerTurn for that Player.
     */
    public void nextTurnPlayer() {
        curPlayer = (curPlayer + 1) % numPlayer;
		players[curPlayer].playerTurn();
    }
	
	/*
	* Function: rollDice
	* Parameter:
	*   Optional int count: number of dice to roll
	* Returns:
	*   sum of dice rolled
	* Description: rolls 6-sided dice to generate random numbers and returns the result.
	*/
	public int rollDice()
	{
		return dice.nextInt(6);
	}
	
	public int rollDice(int count)
	{
		int sum = 0;
		while(count-- > 0)
		{
			sum += rollDice();
		}
		return sum;
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
			
		}
		// Print scores and who won
		// TODO
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
		Scanner feed = new Scanner(System.in);
		dice = new Random();
		System.out.print("Enter the number of players who would like to play:\n\t");
		String usrEntry = feed.nextLine();
		while (!isInteger(usrEntry))
		{
			System.out.print("Please enter a valid number of players.\n\t");
			usrEntry = feed.nextLine();
		}
		int pcount = Integer.parseInt(pcount);
		DeadWood game = new DeadWood(pcount);
		game.gameLoop();
    }
    
}