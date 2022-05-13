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
	
    public String getName()
    {
        return name;
    }

	public void setRoom(Room r)
	{
		room = r;
	}
	
	public void addCredits(int n)
	{
		credit += n;
	}

    public void addFunds(int n)
    {
        dollar += n;
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
        Scanner feed = new Scanner(System.in);
        boolean flag = false;

        if (room.getName().equals("trailer")) {
            room.action(this);
            while(!move()) {
                System.out.println("That is not valid room option. Please try again.");
            }
        }
        // Check if the user is in Casting Office
        else if(room instanceof CastingOffice) {
            System.out.println("Welcome to the casting Office.\nHere are some options for upgrading rank. Would you like to upgrade rank?");
            System.uot.println("If you want to upgrade rank, please type 'y', and if you do not wish to upgrade, please type 'n'");
            String userInput = feed.nextLine();    
            if (userInput.toLowerCase().equals("y")) {
                int prevrank = this.rank;
                while(!((CastingOffice) room).action(this)) {
                    System.out.println("You have insufficient dollar/credit for upgrade. Please select another option");
                }
                if (prevrank != this.rank) {
                    System.out.println("Upgrading rank successful");
                }
            }
        }

        // Check if the user is in SceneRoom
        else if(room instanceof SceneRoom) {
            // Check if the player has a role or not
            if (role.equals(null)) {
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
                    else if(userInput.toLowerCase().equals("r") || userInput.toLowerCase().equals("role")) {
                        // If takeRole func return false, loop thru player choice until player makes a valid choice
                        while(!((SceneRoom) room).action(this)) {  
                            System.out.println("The role selected is not possible. Please select another role");
                        }
                        if(role != null) {
                            System.out.println("Role selection successful");
                            flag = true;
                        }                
                        
                    }
                }
            }
            else if (!role.equals(null)) {
                // Loop statement if user types wrong answer
                while(!flag) {
                    System.out.println("Please choose whether to act or to rehearse.")
                    System.out.println("If you want to act, type 'a'. For rehearse, type 'r'.");
                    String userInput = feed.nextLine();

                    // Check for user input
                    if(userInput.toLowerCase().equals("a") || userInput.toLowerCase().equals("act")) {
                        act();                        
                        flag = true;
                    }
                    else if(userInput.toLowerCase().equals("r") || userInput.toLowerCase().equals("rehearse")) {
                        if (!rehearse()) {
                            System.out.println("You have reached to max token. You cannot rehearse anymore");
                        }
                        else {
                            flag = true;
                        }
                    }
                    else {
                        Sysem.out.println("Invalid command. Try again.");
                    }
                }
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
        Scanner feed = new Scanner(System.in);
        
        // Show available rooms to move
        System.out.println("Available Room option to move: ");
        Room[] neighborRoom = room.getNeighbors();
        for (int i = 0; i < neighborRoom.length(); i++) {
            System.out.printf("\tOption %d: %s", i+1, neighborRoom[i].getName());
        }
        System.out.println("Please type room number to move");
        int userInput = feed.nextInt();
        if (userInput > 0 && userInput < neighborRoom.length()) {
            room = neighborRoom[userInput-1];
            return true;
        } 
        return false;
    }

    /*
     * Function: getter methods
     * Parameter:
     * None
     * 
     * Description:
     * Allows other classes to access variables in this class
     */
    public Role getRole() {
        return this.role;
    }

    public int getRank() {
        return this.rank;
    }

    public int subtractFunds(int dollar) {
        if ((this.dollar - dollar) > 0) {
            this.dollar -= dollar;
            return true;
        }
        return false;
    }

    public int subtractCredit(int crdit) {
        if ((this.credit - credit) > 0) {
            this.credit -= credit;
            return true;
        }
        return false;

        
    }
}
