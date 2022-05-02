package implementation;

/*
* Class: DeadWood
* Description: 
*   This class will be the first class to be called to initiate and call other classes to
*   start program. Its main role is to interact with players and set up the game environment
*   as well as keep traking of the game system until game is over.
*/
public class DeadWood {
    private int day; // Take cares of days that players play depending on their number
    private int numPlayer; // Input of number of players
    private Player[] listPlayer; // List of players to keep track of turn
    private Board board;
    private Player curPlayer; // Current players' turn

    /*
     * Constructor: DeadWood
     * Parameter:
     * None
     * Description:
     * Simple DeadWood constructor
     */
    public DeadWood() {
        // TO DO
    }

    /*
     * main function
     * Description:
     * Ask for how many users are there to play game
     * Also loop the function for each day
     */
    public static void main(String[] args) {
        // TO DO
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
     * Keep track of next player with listPlayer.
     * Either decrement or increment index of listPlayer to give
     * each player a turn.
     */
    public void nextTurnPlayer() {
        // TO DO
    }
}