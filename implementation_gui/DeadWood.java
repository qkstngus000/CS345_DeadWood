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
	private View view;
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
		view = new View();
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
		String[] playerColor = {"b", "c", "g", "o", "p", "r", "v", "w", "y"};
		// initialize days
		day = 0;
		maxDay = numPlayer < 4 ? 3 : 4;
		// create board
		board = new Board();
		System.out.println("Board created!");
		// initialize players
		players = new Player[numPlayer];
		for (int i = 0; i < numPlayer; i++) {
			players[i] = new Player("Player "+(i+1), playerColor[i]);
			if (numPlayer == 5) {
				players[i].addCredits(2);
				// view.drawPlayer(playerColor[i], 1);
			}
			else if (numPlayer == 6) {
				players[i].addCredits(4);
				// view.drawPlayer(playerColor[i], 1);
			}
			else if (numPlayer > 6) {
				players[i].setRank(2);
				// view.drawPlayer(playerColor[i], 2);
			}

			// view.drawElement(players[i]);

			System.out.println("Player " + (i+1) + " initialized!");
		}
		// view.drawPlayers(players);
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
		// players[curPlayer].playerTurn();
		Player p = players[curPlayer];
		Room proom = p.getRoom();


		// TODO adapt this to a gui display
		view.setPlayerName(p.getName());
		view.setPlayerRank(p.getRank());
		view.setPlayerFunds(p.getFunds());
		view.setPlayerCredits(p.getCredits());
		view.setPlayerRoom(proom.getName());
		view.setPlayerTokens(p.getRole() == null ? -1 : p.getToken());
		view.updateInfo();
		System.out.printf("\n~~~~~ %s's turn! ~~~~~%n",p.getName());
        System.out.printf("rank: %d, dollar: %d\tcredit: %d\n", p.getRank(), p.getFunds(), p.getCredits());
        if(p.getRole() == null)
        {
            // Player has no role so they can move and/or take a room action
            boolean moved = false;
            boolean actionPerformed = false;
            while(!moved||!actionPerformed)
            {
				// TODO adapt this to a gui display
                // Print options
                System.out.printf("~~~~ You are in: %s%n",proom.getName());
                System.out.println("You may move once and/or take one action during your turn.\nEnter what you would like to do:");
                if(!moved)
                {
                    System.out.println("\t'm': Move");
                }
                if(!actionPerformed)
                {
                    System.out.println("\t'a': Take Action");
                }
                System.out.println("\t'e': End Turn");
                // Take input
				// TODO change this to button input
                String usrEntry = DeadWood.feed.nextLine();
                if(!moved && usrEntry.trim().toLowerCase().equals("m"))
                {
                    // Player wants to move
                    if(p.move())
                    {
                        moved = true;
						proom = p.getRoom();
						// Redraw flipped SceneCard if it exists
						if(proom instanceof SceneRoom && ((SceneRoom)proom).getScene() != null)
						{
							view.drawElement(((SceneRoom) proom).getScene());
						}
						// draw player
						view.drawElement(p);
						// update info
						view.setPlayerRoom(proom.getName());
						view.updateInfo();
                    }
                    continue;
                }
                if(!actionPerformed && usrEntry.trim().toLowerCase().equals("a"))
                {
                    // Player wants to take action
                    if(proom.action(p))
                    {
                        actionPerformed = true;

						// draw player
						view.drawElement(p);
						
						// update rank and currencies
						view.setPlayerRank(p.getRank());
						view.setPlayerFunds(p.getFunds());
						view.setPlayerCredits(p.getCredits());
						view.updateInfo();

                        // End turn if player took a role in a sceneroom
                        if(p.getRole() != null)
                        {
                            break;
                        }
                    }
                    continue;
                }
                if(usrEntry.trim().toLowerCase().equals("e"))
                {
                    // Player ends their turn
                    break;
                }
                System.out.println("Invalid input. Please try again.");
            }
        }
        else
        {
            // Player has a role so they can act or rehearse
            while(true)
            {
                // Loop until player enters valid input
				
				// TODO adapt this to a gui display
                // Print options
                System.out.printf("~~~~ You are in: %s%n",proom.getName());
                ((SceneRoom) proom).printSceneInfo();
                System.out.printf("~~ Your role is: %s (Rank: %d)%n",p.getRole().getName(),p.getRole().getRank());
                // System.out.printf("You have +%d to any acting roll from your rehearsal bonus.%n",token);
                System.out.println("You may act or rehearse. Enter what you would like to do:");
                System.out.println("\t'a': Act");
                System.out.println("\t'r': Rehearse");
                // Take input
				// TODO change this to button input
                String usrEntry = DeadWood.feed.nextLine();
                if(usrEntry.trim().toLowerCase().equals("a"))
                {
                    // Player wants to act
                    p.act();

					// Draw player
					view.drawElement(p);
					// Remove SceneCard from view if scene is finished
					if(!((SceneRoom) proom).getActive())
					{
						view.removeElement(((SceneRoom) proom).getScene());
					}
					// Update shot counters
					view.drawShotCounters((SceneRoom) proom);
					// Update players currencies
					view.setPlayerFunds(p.getFunds());
					view.setPlayerCredits(p.getCredits());
					view.updateInfo();
                    break;
                }
                if(usrEntry.trim().toLowerCase().equals("r"))
                {
                    // Player wants to rehearse
                    if(p.rehearse())
                    {
						view.setPlayerTokens(p.getToken());
						view.updateInfo();
                        break;
                    }
                    continue;
                }
                System.out.println("Invalid input. Please try again.");
            }
        }

		// Progress curPlayer
        curPlayer = (curPlayer + 1) % numPlayer;
    }
	
	/**
	 * Calls drawElement() on the Player whose turn it is currently
	 */
	/*public static void updatePlayerVisual()
	{
		game.view.drawElement(game.players[game.curPlayer]);
	}*/
	
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
			board.reset(view);
			


			for(int i = 0; i < numPlayer; i++)
			{
				players[i].setRoom(board.getStartingRoom());
				view.drawElement(players[i]);
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

		int pCount = game.view.askNumPlayer();
		// board.setVisible(true);

		// System.out.print("Enter the number of players who would like to play:\n\t");
		// String usrEntry = feed.nextLine();
		// while (!isInteger(usrEntry)) {
		// 	System.out.print("Please enter a valid number of players.\n\t");
		// 	usrEntry = feed.nextLine();
		// }
		// int pcount = Integer.parseInt(usrEntry);
		game.setEnv(pCount);
		game.gameLoop();

		feed.close();
    }
}