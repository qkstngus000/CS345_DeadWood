
/*
 * Class: Player
 *  Description
 *      Player class takes care of what player can do for their turn during game as method
 *      The class would hold information of players such as player role, dollars/credit, rehearse token,
 *      rooms that player is located, rank, and player identifier(name) in its individual constructor
 * 
 */
public class Player implements Drawable {
    private String name; // Holds player name
    private Room room; // Holds current status of player location
    private int rank; // Holds rank of current player
    private int dollar; // Holds how many dollars player has
    private int credit; // Holds how many credit player has
    private int token; // Holds how many rehearse token player has
    private Role role; // Holds current role of player. If not, null
    private ObjCoord pos; // Holds screen position of the player. Used for drawing
    private static final String imgPath = "../images/dice/%s%d.png"; // The general path to the player icon files. Used for drawing
    private String imgColor; // Holds the color of the player's dice. Used for drawing
    private static int imgDim = 40; // The side dimension of the dice images
    private static int depth = 4; // The draw layer for player icons

    /*
     * Constructor Player
     * Parameter:
     * String name: Player input of name on their character representation
     * Description:
     * Initialize the player object to be created which would hold player
     * info, and update the player info as games progress.
     */
    public Player(String name, String imgColor) {
        this.name = name;
        this.imgColor = imgColor;
		rank = 1;
		dollar = 0;
		credit = 0;
		token = 0;
        pos = new ObjCoord(0,0,imgDim,imgDim);
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

    public ObjCoord getCoord()
    {
        return this.pos;
    }

    public String getImgPath()
    {
        return String.format(imgPath, imgColor,rank);
    }

    public String getName()
    {
        return name;
    }

    public int getDepth()
    {
        return depth;
    }

	public void setRoom(Room r)
	{
		room = r;

        // TODO caculate players position in the room so that it does not overlap anything else
        ObjCoord roomPos = room.getCoord();
        pos = new ObjCoord(roomPos.getX(),roomPos.getY(),imgDim,imgDim);
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

    public void resetRehearse() {
        this.token = 0;
    }
    public Room getRoom() {
        return this.room;
    }

    /**
     * Interact with DeadWood manager and keep track of the turn
     * as there are going to be many Player objects. <p>
     * 
     * Options:
     *      If player currently has a role:
     *          Act / Rehearse
     *      else
     *          Move / take room action
     *      
     * 
     * If action returns false, the player backed out without actually doing 
     * anything so they should be presented with the same options again.
     */
    public void playerTurn() {

        System.out.printf("\n~~~~~ %s's turn! ~~~~~%n",name);
        System.out.printf("rank: %d, dollar: %d\tcredit: %d\n", rank, dollar, credit);
        if(role == null)
        {
            // Player has no role so they can move and/or take a room action
            boolean moved = false;
            boolean actionPerformed = false;
            while(!moved||!actionPerformed)
            {
                // Print options
                System.out.printf("~~~~ You are in: %s%n",room.getName());
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
                String usrEntry = DeadWood.feed.nextLine();
                if(!moved && usrEntry.trim().toLowerCase().equals("m"))
                {
                    // Player wants to move
                    if(move())
                    {
                        moved = true;
                    }
                    continue;
                }
                if(!actionPerformed && usrEntry.trim().toLowerCase().equals("a"))
                {
                    // Player wants to take action
                    if(room.action(this))
                    {
                        actionPerformed = true;

                        // End turn if player took a role in a sceneroom
                        if(role != null)
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

                // Print options
                System.out.printf("~~~~ You are in: %s%n",room.getName());
                ((SceneRoom) room).printSceneInfo();
                System.out.printf("~~ Your role is: %s (Rank: %d)%n",role.getName(),role.getRank());
                System.out.printf("You have +%d to any acting roll from your rehearsal bonus.%n",token);
                System.out.println("You may act or rehearse. Enter what you would like to do:");
                System.out.println("\t'a': Act");
                System.out.println("\t'r': Rehearse");
                // Take input
                String usrEntry = DeadWood.feed.nextLine();
                if(usrEntry.trim().toLowerCase().equals("a"))
                {
                    // Player wants to act
                    act();
                    break;
                }
                if(usrEntry.trim().toLowerCase().equals("r"))
                {
                    // Player wants to rehearse
                    if(rehearse())
                    {
                        break;
                    }
                    continue;
                }
                System.out.println("Invalid input. Please try again.");
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

    /**
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
            // TODO set player position
            return true;
        }
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
        int prevDollar = this.dollar;
        int prevCredit = this.credit;
        int diceNum = DeadWood.rollDice();
        System.out.printf("%s is acting!%nRolled a %d + %d = %d%n",name,diceNum,token,diceNum+token);
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
                    System.out.println("1 dollar and 1 credit added to extra role player");
                }
                playerRoom.updateShot();
            }

            // If player has extra role and failed to roll dice >= budget
            else {
                if (role.getMainRole() == false) {
                    dollar++;
                    System.out.println("1 dollar added to extra role player");
                }
            }
        }
        System.out.printf("dollar: %d --> %d\tcredit: %d --> %d\n\n\n", prevDollar, dollar, prevCredit, credit);
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
            System.out.printf("Rehearse bonus increased by +1! (current bonus: +%d)%n", token);
            return true;
        }
        System.out.println("Rehearsing any more will have no benefit. (current bonus: 6)");
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
        Room[] neighborRoom = room.getNeighbors();
        while(true)
        {
            // Loop until user enters valid input

            // Show available rooms to move
            System.out.println("Nearby rooms: ");
            for (int i = 0; i < neighborRoom.length; i++) {
                System.out.printf("\t%d: %s%n", i+1, neighborRoom[i].getName());
            }
            System.out.println("Please enter the number of the room you'd like to move to or 'q' to go back:");
            String usrEntry = DeadWood.feed.nextLine();
            if(usrEntry.trim().toLowerCase().equals("q"))
            {
                // Player backed out
                return false;
            }
            if(DeadWood.isInteger(usrEntry.trim()))
            {
                // Player entered an integer
                int selection = Integer.parseInt(usrEntry.trim());
                if (selection > 0 && selection <= neighborRoom.length) {
                    // Player entered a valid room number
                    Room selectedRoom = neighborRoom[selection-1];
                    // Confirmation
                    while(true)
                    {
                        System.out.printf("Are you sure you would like to move to room: %s?%n",selectedRoom.getName());
                        System.out.println("\t'y': Yes\n\t'n': No");
                        if(selectedRoom instanceof SceneRoom) System.out.println("\t'i': Room Info");
                        usrEntry = DeadWood.feed.nextLine();
                        if(usrEntry.trim().toLowerCase().equals("y"))
                        {
                            // Player wants to move
                            setRoom(selectedRoom);
                            DeadWood.updatePlayerVisual();
                            if(room instanceof SceneRoom) ((SceneRoom) room).visit();
                            return true;
                        }
                        if(usrEntry.trim().toLowerCase().equals("n"))
                        {
                            // Player wants to go back
                            break;
                        }
                        if(selectedRoom instanceof SceneRoom && usrEntry.trim().toLowerCase().equals("i"))
                        {
                            // Player wants to see room info
                            ((SceneRoom) selectedRoom).printSceneInfo();
                            ((SceneRoom) selectedRoom).printRoleInfo();
                            continue;
                        }
                        System.out.println("Invalid entry. Please try again.");
                    }
                    continue;
                }
            }
            System.out.println("Invalid input. Please try again.");
        }
    }



    public boolean subtractFunds(int dollar) {
        if ((this.dollar - dollar) > 0) {
            this.dollar -= dollar;
            return true;
        }
        return false;
    }

    public boolean subtractCredits(int credit) {
        if ((this.credit - credit) > 0) {
            this.credit -= credit;
            return true;
        }
        return false;

        
    }
}
