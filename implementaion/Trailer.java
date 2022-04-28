package implementation;

/*
* Class: Trailer
* Description:
*   Represents the trailer room where all players start each day. This class will be very small since not much
*   can be done in the trailer room.
*/
public class Trailer extends Room
{
	/*
	* Constructor
	* Description:
	*   Just calls the parent constructor.
	*/
	public Trailer()
	{
		// TODO
	}
	
	/*
	* Function: action
	* Parameter:
	*   Player p: the player who is executing the action
	* Returns:
	*   bool: always should be false, since no action can be performed in the trailer room.
	* Description:
	*   Players cannot do anything in the trailer room, but an action method still must be present to display
	*   a message to the player and return them to their turn code.
	*/
	public bool action(Player p)
	{
		// TODO
	}
}