package implementation;

/*
* Class: Room
* Description:
*   This abstract class acts as a parent to SceneRoom, CastingOffice, and Trailer, which are all rooms on the board.
*   It will be used to refer to the rooms in the board class, and holds the common functionality between all types of rooms.
*/
public class Room
{
	/*
	* adjRoom: holds references to the rooms connected to this one in the four cardinal directions.
	* Used to determine what room to move to when travelling in those directions. NULL means there is no connection in that direction.
	* 
	* Direction: an enum used to make it easier to remember which index of adjRoom corresponds to which direction.
	* e.g. adjRoom[Direction.SOUTH] = the room to the south of this one
	*/
	Room[] adjRoom;
	public enum Direction
	{
		NORTH,
		EAST,
		SOUTH,
		WEST
	}
	
	int numActors;	// number of actors currently in the room
	
	/*
	* Constructor
	* Description:
	*   A simple constructor which just initializes adjRoom to an empty array of length 4 and sets numActors to 0.
	*/
	public Room()
	{
		// TODO
	}
	
	/*
	* Function: action
	* Parameter:
	*   Player p: the player who is executing the action
	* Returns:
	*   bool: true if the action was completed and false if the player backed out/can still take another action this turn.
	* Description:
	*   This abstract method, to be implemented by the child classes, will be called by a player when they decide to take an action in
	*   this room during their turn, and will determine what happens from there on. It returns a bool to communicate to the player whether
	*   an action was actually completed.
	*/
	public bool action(Player p);
}