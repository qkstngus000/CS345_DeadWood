import java.util.ArrayList;

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
    private static final String imgPath = "../images/dice/%s%d.png"; // The general path to the player icon files. Used
                                                                     // for drawing
    private String imgColor; // Holds the color of the player's dice. Used for drawing
    private static int imgDim = 40; // The side dimension of the dice images
    private static int depth = 4; // The draw layer for player icons

    private static int playerNum = 0;
    private int id;

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
        pos = new ObjCoord(0, 0, imgDim, imgDim);
        id = playerNum++;
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

    public int getFunds() {
        return this.dollar;
    }

    public int getCredits() {
        return this.credit;
    }

    public ObjCoord getCoord() {
        return this.pos;
    }

    public String getImgPath() {
        return String.format(imgPath, imgColor, rank);
    }

    public String getName() {
        return name;
    }

    public int getDepth() {
        return depth;
    }

    public int getID() {
        return id;
    }

    public void setRoom(Room r) {
        if (room != null) {
            room.removeValidPosition(id);
        }
        room = r;
        int validPos = r.getValidPosition(id);
        int[] placeCord = r.getPlaceable(validPos);
        // Caculate players position in the room so that it does not overlap anything
        // else
        System.out.println(placeCord[0] + " " + placeCord[1]);
        pos = new ObjCoord(placeCord[0], placeCord[1], imgDim, imgDim);
    }

    public void addCredits(int n) {
        credit += n;
    }

    public void addFunds(int n) {
        dollar += n;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void resetRehearse() {
        this.token = 0;
    }

    public Room getRoom() {
        return this.room;
    }

    public int getToken() {
        return token;
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
            // Set player position
            ObjCoord roleCoord = this.role.getCoord();
            if (role.getMainRole()) {
                // If the role is a main role, its position is relative to the room
                ObjCoord roomCoord = room.getCoord();
                pos = new ObjCoord(roleCoord.getX() + roomCoord.getX(), roleCoord.getY() + roomCoord.getY(), imgDim,
                        imgDim);
                room.removeValidPosition(id);
            } else {
                pos = new ObjCoord(roleCoord.getX() + 4, roleCoord.getY() + 4, imgDim, imgDim);
                room.removeValidPosition(id);
            }
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
        System.out.printf("%s is acting!%nRolled a %d + %d = %d%n", name, diceNum, token, diceNum + token);
        if (room instanceof SceneRoom) {
            SceneRoom playerRoom = ((SceneRoom) room);
            if (diceNum + token >= playerRoom.getScene().getBudget()) {
                // If dice+token >= budget && main role
                if (role.getMainRole() == true) {
                    credit += 2;
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

            this.token += 1;
            System.out.printf("Rehearse bonus increased by +1! (current bonus: +%d)%n", token);
            return true;
        }
        DeadWood.showError("Rehearsing any more will have no benefit.");
        // System.out.println("Rehearsing any more will have no benefit. (current bonus:
        // 6)");
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
        // Reset position to default in room.
        setRoom(room);
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

        ArrayList<String> options = new ArrayList<String>();
        // Show available rooms to move
        // System.out.println("Nearby rooms: ");
        for (int i = 0; i < neighborRoom.length; i++) {
            // System.out.printf("\t%d: %s%n", i+1, neighborRoom[i].getName());
            options.add(neighborRoom[i].getName());
        }
        options.add("Go Back");
        // System.out.println("Please enter the number of the room you'd like to move to
        // or 'q' to go back:");

        // String usrEntry = DeadWood.feed.nextLine();
        String usrEntry = DeadWood.getButtonInput(options);
        if (usrEntry.equals("Go Back")) {
            // Player backed out
            return false;
        }
        for (Room r : neighborRoom) {
            if (usrEntry.equals(r.getName())) {
                // Player chose a room to move to
                setRoom(r);
                if (room instanceof SceneRoom)
                    ((SceneRoom) room).visit();
                return true;
            }
        }
        // should never reach here
        return false;
    }

    public boolean subtractFunds(int dollar) {
        if ((this.dollar - dollar) >= 0) {
            this.dollar -= dollar;
            return true;
        }
        return false;
    }

    public boolean subtractCredits(int credit) {
        if ((this.credit - credit) >= 0) {
            this.credit -= credit;
            return true;
        }
        return false;

    }
}
