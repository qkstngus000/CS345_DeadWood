package implementation_gui;

/*
* Class: Room
* Description:
*   This abstract class acts as a parent to SceneRoom, CastingOffice, and Trailer, which are all rooms on the board.
*   It will be used to refer to the rooms in the board class, and holds the common functionality between all types of rooms.
*/
public class Room
{
	private Room[] neighbors; // neighbors: holds references to the rooms that are reachable from this room in one move.
	/* public enum Direction
	{
		NORTH,
		EAST,
		SOUTH,
		WEST
	} */
	
	private int numActors;	// number of actors currently in the room
	private String name;
	
	/*
	* Constructor
	* Description:
	*   A simple constructor which just sets name and sets numActors to 0.
	*/
	public Room(String name)
	{
		this.name = name;
		numActors = 0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setNeighbors(Room[] neighbors)
	{
		this.neighbors = neighbors;
	}
	
	public Room[] getNeighbors() {
		return neighbors;
	}

	/*
	* Function: action
	* Parameter:
	*   Player p: the player who is executing the action
	* Returns:
	*   bool: true if the action was completed and false if the player backed out/can still take another action this turn.
	* Description:
	*   This method, to be implemented by the child classes, will be called by a player when they decide to take an action in
	*   this room during their turn, and will determine what happens from there on. It returns a bool to communicate to the player whether
	*   an action was actually completed. Here it has a default implementation which will be used by the Trailer room and simply prints
	*   that no action can be performed.
	*/
	public boolean action(Player p)
	{
		System.out.println("No action can be performed in this room.");
		return false;
	}
}