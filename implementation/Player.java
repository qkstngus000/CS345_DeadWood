package implementation;

/*
 * Class: Player
 *  Description
 *      Player class takes care of what player can do for their turn during game as method
 *      The class would hold information of players such as player role, dollars/credit, rehearse token,
 *      rooms that player is located, rank, and player identifier(name) in its individual constructor
 * 
 */
public class Player {
    private String name; // Holds player name
    private Room room; // Holds current status of player location
    private int rank; // Holds rank of current player
    private int dollar; // Holds how many dollars player has
    private int credit; // Holds how many credit player has
    private int token; // Holds how many rehearse token player has
    private Role role; // Holds current role of player. If not, null

    /*
     * Constructor Player
     * Parameter:
     * String name: Player input of name on their character representation
     * Description:
     * Initialize the player object to be created which would hold player
     * info, and update the player info as games progress.
     */
    public Player(String name) {
        this.name = name;
		rank = 1;
		dollar = 0;
		credit = 0;
		token = 0;
    }
	
	public void setRoom(Room r)
	{
		room = r;
	}
	
	public void addCredits(int n)
	{
		credit += n;
	}

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    /*
     * Function: playerTurn
     * Parameter:
     * None
     * Description:
     * Interact with DeadWood manager and keep track of the turn
     * as there are going to be many Player objects.
     * 
     * Options:
     *      If player currently has a role:
     *          Act / Rehearse
     *      else
     *          Move / take room action
     * 
     * If action returns false, the player backed out without actually doing 
     * anything so they should be presented with the same options again.
     */
    public void playerTurn() {
        // TO DO
    }

    /*
     * Function: calcScore
     * Parameter:
     * None
     * Description:
     * At the end of the game, this function will grab player's dollar,
     * credit, and rank to calculate the player score.
     */
    public void calcScore() {
        // TO DO
    }

    /*
     * Function: takeRole
     * Parameter:
     * Role role: the role that player is wanting to take
     * 
     * Description:
     * If player choose to take role, this function will change the player
     * status and make sure the role is available for player by comparing it
     * to player's current rank status.
     */
    public boolean takeRole(Role role) {
        // TO DO
        return false;
    }

    /*
     * Function: act
     * Parameter:
     * none
     * Description:
     * If player wants to act on player's role, function calls the dice obejct
     * to receive the rolled number and compare with budget.
     */
    public void act() {
        // TO DO
    }

    /*
     * Function: rehearse
     * Parameter:
     * None
     * 
     * Description:
     * If player wish to rehearse rather than act, rehearse function will increment
     * player token until the number reaches budget - 1 for the scene.
     * 
     * Return: boolean
     * If player wish to rehearse while token is already budget - 1, return false
     * to notify player it is wrong move.
     */
    public boolean rehearse() {
        // TO DO
        return false;
    }

    /*
     * Function: removeRole
     * Parameter:
     * None
     * 
     * Description:
     * After clearing out the scene, remove the role from player
     * Token gets updated to be 0 as well.
     */
    public void removeRole() {
        // TO DO
    }

    /*
     * Function: move
     * Parameter:
     * None
     * 
     * Description:
     * Allows player to move to different rooms.
     * 
     * Return: boolean
     * If player wants to move to adjecent room, return true.
     * Otherwise, return false.
     */
    public boolean move() {
        // TO DO
        return false;
    }
}
