package implementation;

import java.util.Scanner;

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
        // Check if the player has a role or not
        if (role.equals(null)) {
            Scanner feed = new Scanner(System.in);
            boolean flag = false;
            while (!flag) {
                System.out.println("Please choose whether to move or take role\ntype 'm' for move, and 'r' for taking role");
                String userInput = feed.nextLine();
                if (userInput.toLowerCase().equals("m") || userInput.toLowerCase().equals("move")) {
                    // If move return false, loop through player choice until player makes a valid move
                    while (!move()) {
                        System.out.println("This is not valid move. Please choose neighboring room to move");
                    }
                    System.out.println("Move process completed");
                }
                // else if(userInput.toLowerCase().equals("r") || userInput.toLowerCase().equals("role")) {
                    // If takeRole func return false, loop thru player choice until player makes a valid choice
                    // while(!((SceneRoom) room).action(Player)) {  // Currently, i am not sure on how to send oneself as parameter

                    // }
                // }
            }

        }
    }

    /*
     * Function: calcScore
     * Parameter:
     * None
     * Description:
     * At the end of the game, this function will grab player's dollar,
     * credit, and rank to calculate the player score.
     */
    public int calcScore() {
        return credit + dollar + (rank * 5);
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
        if (this.rank >= role.getRank()) {
            System.out.println("Role successfully taken");
            this.role = role;
            // Change role status
            role.updateRoleStatus(false);
            return true;
        }
        System.out.println("Role requires higher rank. Please try selecting roles that are less than or equal to your rank");
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
        int diceNum = DeadWood.rollDice();
        if (room instanceof SceneRoom) {
            SceneRoom playerRoom = ((SceneRoom) room);
            if (diceNum+token >= playerRoom.getScene().getBudget()) {
                // If dice+token >= budget && main role
                if(role.getMainRole() == true) {
                    credit+=2;
                    System.out.println("2 Credit added to main role player");
                }
                
                // If dice+token >= budget && extra role
                else {
                    dollar++;
                    credit++;
                    System.out.println("1 dollar and 1 credit payment added to extra role");
                }
                ((SceneRoom) room).updateShot();
            }

            // If player has extra role and failed to roll dice >= budget
            else {
                if (role.getMainRole() == false) {
                    dollar++;
                }
            }
        }
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
        SceneRoom playerRoom = ((SceneRoom) room);
        int budget = playerRoom.getScene().getBudget();
        if ((role != null) && token < budget - 1) {
            
            this.token+=1;
            System.out.printf("Token updated from %d to %d\n", token-1, token);
            return true;
        }
        System.out.println("Token could not be updated.");
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
        this.role = null;
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
        return false;
    }
    
    public Role getRole() {
        return this.role;
    }

    public int getRank() {
        return this.rank;
    }
}
