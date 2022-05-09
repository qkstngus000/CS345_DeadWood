package implementation;

import java.util.ArrayList;

/*
* Class: SceneRoom
* Description:
*   Represents the majority of the rooms in the game, which get a new scene each day and where players can take acting roles.
*   Keeps track of the scene and roles contained within it and which players are currently acting in the room.
*   Handles players taking roles, tracking scene progress, and bonus payouts at the end of a scene.
*/
public class SceneRoom extends Room
{
	public static int scenesLeft;			// the number of active scenes still on the board
	public static int numScene;				// the total number of scene rooms
	private int maxShots;					// the total shots needed to complete a scene in this room
	private int curShot;					// the current shot number this room is on. Starts at 1 and counts up to maxShots
	private Role[] roomRoles;				// array of 'extra' roles which are attached to this room rather than the scene
	private ArrayList<Player> actorInfo;	// array of players currently doing roles in this room
	private SceneCard scene;				// the scene currently active in this room
	private Take[] take;
	
	/*
	* Constructor
	* Description:
	*   Calls the parent constructor and initializes various fields. Also increments numScene.
	*/
	public SceneRoom(String name, int maxShots, Take[] take)
	{
		super(name);
		numScene++;
		this.maxShots = maxShots;
		curShot = 0;
		actorInfo = new ArrayList<Player>();
		this.take = take;
		
	}
	
	public Role[] getRoles()
	{
		return roomRoles;
	}
	
	public void setRoles(Role[] r)
	{
		roomRoles = r;
	}
	
	public SceneCard getScene()
	{
		return scene;
	}
	
	public void setScene(SceneCard s)
	{
		scene = s;
	}
	
	/*
	* Function: action
	* Parameter:
	*   Player p: the player who is executing the action
	* Returns:
	*   bool: whatever roleManager() returns
	* Description:
	*   This just acts as a wrapper method for the private method, roleManager(), which actually handles players taking roles.
	*/
	public boolean action(Player p)
	{
		return roleManager(p);
	}
	
	/*
	* Function: roleManager
	* Parameter:
	*   Player p: the player who is taking a role
	* Returns:
	*   bool: true if the player acquired a role and false if the player backed out.
	* Description:
	*   Handles what happens when a player tries to take a role in this room. Should take input from the player to decide
	*   which role to take, call takeRole() on the player, and update the role's roleDeprived field to complete the interaction.
	*/
	private boolean roleManager(Player p)
	{
		// TODO
		return false;
	}
	
	/*
	* Function: updateShot
	* Description:
	*   Increments curShot, and if curShot reaches maxShots, handles bonus payouts and calls closeRoom()
	*/
	public void updateShot()
	{
		// TODO
	}
	
	/*
	* Function: closeRoom
	* Description:
	*   Called when a scene finishes in this room or the day ends. resets curShot, sets scene to null, and removes actors' roles.
	*/
	public void closeRoom()
	{
		// TODO
	}
	
	/*
	* Function: firstVisit()
	* Description:
	*   Called whenever a Player enters the room. Reveals the scene card for this room if it is face-down.
	*/
	public void firstVisit()
	{
		// TODO
	}
}