package implementation;

import java.util.ArrayList;
import java.util.Scanner;

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
		Scanner feed = new Scanner(System.in);
		//Print out role informaion
		System.out.println("Available Roles:\n#|Available?|Min Rank|Name                |Description");
		for(int i = 0; i < roomRoles.length; i++)
		{
			System.out.printf(	"%1d|%10b|%-8d|%20s|%s%n",i,roomRoles[i].getAvailable(),
								roomRoles[i].getRank(),roomRoles[i].getName(),roomRoles[i].getLine());
		}
		//Repeat until user enters a valid entry
		while(true)
		{
			System.out.println("Enter the # of the role you want, or 'q' to go back:");
			String usrEntry = feed.nextLine();
			if(usrEntry.trim().toLowerCase().equals("q"))
			{
				//User entered back command
				feed.close();
				return false;
			}
			if(DeadWood.isInteger(usrEntry.trim()))
			{
				//User entered an integer
				int selection = Integer.parseInt(usrEntry.trim());
				if(selection >= 0 && selection < roomRoles.length)
				{
					//User entered a valid role #
					if(roomRoles[selection].getAvailable())
					{
						//Role is available
						if(p.takeRole(roomRoles[selection]))
						{
							//Role was taken successfully
							feed.close();
							return true;
						}
						System.out.printf("You need at least rank %d for this role. Current rank: %d%n",roomRoles[selection].getRank(),p.getRank());
						continue;
					}
					System.out.println("This role is currently unavailable.");
					continue;
				}
			}
			System.out.println("Invalid entry. Please try again.");
		}
	}
	
	/*
	* Function: updateShot
	* Description:
	*   Increments curShot, and if curShot reaches maxShots, handles bonus payouts and calls closeRoom()
	*   Check if there are main role or not
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
		if(!scene.getFlipped())
		{
			scene.flipCard();
		}
	}
}