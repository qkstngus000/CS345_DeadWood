

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
* Class: SceneRoom
* Description:
*   Represents the majority of the rooms in the game, which get a new scene each day and where players can take acting roles.
*   Keeps track of the scene and roles contained within it and which players are currently acting in the room.
*   Handles players taking roles, tracking scene progress, and bonus payouts at the end of a scene.
*/
public class SceneRoom extends Room
{
	private int maxShots;					// the total shots needed to complete a scene in this room
	private int curShot;					// the current shot number this room is on. Starts at 1 and counts up to maxShots
	private Role[] roomRoles;				// array of 'extra' roles which are attached to this room rather than the scene
	private ArrayList<Player> actorInfo;	// array of players currently doing roles in this room
	private SceneCard scene;				// the scene currently active in this room
	private ObjCoord[] take;					// Coordinate for shot
	
	/*
	* Constructor
	* Description:
	*   Calls the parent constructor and initializes various fields. Also increments numScene.
	*/
	public SceneRoom(String name, int maxShots, ObjCoord[] take)
	{
		super(name);
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
 @Override
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
		// If scene is finished, print a message and return
		if(scene == null)
		{
			System.out.println("The scene in this room has finished. No action can be taken.");
			return false;
		}
		Role[] cardRoles = scene.getRoles();
		// Print out role informaion
		printSceneInfo();
		printRoleInfo();
		// Repeat until user enters a valid entry
		while(true)
		{
			System.out.println("Enter the # of the role you want, or 'q' to go back:");
			String usrEntry = DeadWood.feed.nextLine();
			if(usrEntry.trim().toLowerCase().equals("q"))
			{
				// User entered back command
				return false;
			}
			if(DeadWood.isInteger(usrEntry.trim()))
			{
				// User entered an integer
				int selection = Integer.parseInt(usrEntry.trim());
				// Find the Role object which corresponds to the user's entry, if it exists.
				Role selectedRole = null;
				if(selection >= 0)
				{
					// System.out.println("selection >= 0");
					if(selection < cardRoles.length)
					{
						// System.out.printf("selection <%d> is main role%n",selection);
						selectedRole = cardRoles[selection];
					}
					else if(selection < cardRoles.length + roomRoles.length)
					{
						// System.out.printf("selection <%d> is extra role%n",selection);
						// System.out.printf("role index: %d",selection - cardRoles.length);
						selectedRole = roomRoles[selection - cardRoles.length];
					}
				}
				if(selectedRole != null)
				{
					// User entered a valid role #
					if(selectedRole.getAvailable())
					{
						// Role is available
						if(p.takeRole(selectedRole))
						{
							// Role was taken successfully
							// set role to unavailable
							selectedRole.updateRoleStatus(false);
							// Add player to actorInfo
							actorInfo.add(p);
							return true;
						}
						System.out.printf("You need at least rank %d for this role. Current rank: %d%n",selectedRole.getRank(),p.getRank());
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
	* Function: printRoleInfo
	* Parameter:
	* 	none
	* Description:
	* 	Displays the name, description and budget of the scene, as well as the number, availability,
	* 	difficulty, name, and description for all main and extra roles.
	* 
	* 	Used for debug purposes as well as when a player attempts to take a role.
	*/
	public void printRoleInfo()
	{
		if(scene == null)
		{
			return;
		}
		Role[] cardRoles = scene.getRoles();
		System.out.println("Main Roles:");
		System.out.println("#|Available?|Min Rank|Name                |Description");
		System.out.println("-|----------|--------|--------------------|-------------------");
		for(int i = 0; i < cardRoles.length; i++)
		{
			System.out.printf(	"%1d|%-10b|%8d|%-20s|%s%n",i,cardRoles[i].getAvailable(),
								cardRoles[i].getRank(),cardRoles[i].getName(),cardRoles[i].getLine());
		}
		System.out.println("\nExtra Roles:");
		System.out.println("#|Available?|Min Rank|Name                |Description");
		System.out.println("-|----------|--------|--------------------|-------------------");
		for(int i = 0; i < roomRoles.length; i++)
		{
			System.out.printf(	"%1d|%-10b|%8d|%-20s|%s%n",i+cardRoles.length,roomRoles[i].getAvailable(),
								roomRoles[i].getRank(),roomRoles[i].getName(),roomRoles[i].getLine());
		}
	}

	/*
	* Function: printSceneInfo
	* Description:
	* 	Prints info about the current scene name, budget, and take progress.
	* 	Called by a player during their turn if they currently hold a role in this room.
	*/
	public void printSceneInfo()
	{
		if(scene == null)
		{
			System.out.println("The scene in this room has finished.");
			return;
		}
		System.out.printf("~~~ Scene: %s ~~~%nSynopsis: %s%nBudget: $%d million | ",scene.getName(),scene.getDesc(),scene.getBudget());
		System.out.printf("Takes completed: %d | Total takes needed: %d%n%n",curShot,maxShots);
	}
	
	/*
	* Function: updateShot
	* Description:
	*	Called by a player 'p' after they finish acting in this room.
	*   Increments curShot, and if curShot reaches maxShots, handles bonus payouts and calls closeRoom()
	*   Check if there are main role or not
	*/
	public void updateShot()
	{
		if(++curShot == maxShots)
		{
			// Handle bonus payouts

			// List main actors
			ArrayList<Player> mainActors = new ArrayList<Player>();
			ArrayList<Player> extras = new ArrayList<Player>();
			for(Player actor : actorInfo)
			{
				if(actor.getRole().getMainRole())
				{
					mainActors.add(actor);
				}
				else
				{
					extras.add(actor);
				}
			}

			// Only do bonus payouts if there are main actors
			if(mainActors.size() != 0)
			{
				mainActors.sort(new SortByRoleSize());
				int[] bonusRoll = new int[scene.getBudget()];
				int numRoles = scene.getRoles().length;
				Role[] mainRoles = scene.getRoles();
				int[] bonus = new int[numRoles];
				for(int i = 0; i < bonusRoll.length; i++)
				{
					bonusRoll[i] = DeadWood.rollDice();
					System.out.printf("Rolled: %d%n", bonusRoll[i]);
				}

				// Sort dice number from lowest value to largest
				Arrays.sort(bonusRoll);
				
				// Store lump sum bonus amount for distribution
				int c = numRoles;	// Keep track of bonus arrays
				for(int i = bonusRoll.length - 1; i >= 0; i--) {
					c--;
					bonus[c] += bonusRoll[i];
					if (c == 0) {
						c = numRoles;
					}
				}
				System.out.println("Bonus for main actor from lowest rank to highest rank is: ");
				for (int i = 0; i < bonus.length; i++) {
					System.out.printf("\trank %d: %d\n", scene.getRoles()[i].getRank(), bonus[i]);
				}

				int distOrder = 0;
				// Distribute bonus to main actors in room
				for (int r = 0; r < mainRoles.length; r++) {
					for (int j = 0; j < mainActors.size(); j++) {
						if (mainRoles[r].equals(mainActors.get(j).getRole())) {
							System.out.printf("%s in rank main role rank %d received %d dollars\n", mainActors.get(j).getName(), mainRoles[r].getRank(), bonus[distOrder]);
							mainActors.get(j).addFunds(bonus[distOrder]);
						}
					}
					distOrder++;
				}
					/*if (roomRoles[r].getAvailable() == false) {
						for (int j = 0; j < mainActors.size(); j++) {
							if (roomRoles[r].getName().equals(mainActors.get(j).getRole().getName())) {
								mainActors.get(j).addFunds(bonus[distOrder]);
								System.out.printf("%s in rank main role rank %d received %d", mainActors.get(j).getName(), roomRoles[r].getRank(), bonus[distOrder]);
							}
						}
					} 
					distOrder++;
				}*/

				// pay extras according to their role rank
				for(Player actor : extras)
				{
					actor.addFunds(actor.getRole().getRank());
				}
			}

			closeRoom();
		}
	}

	private class SortByRoleSize implements Comparator<Player>
	{
  @Override
		public int compare(Player p1, Player p2)
		{
			return p2.getRole().getRank() - p1.getRole().getRank();
		}
	}
	
	/*
	* Function: closeRoom
	* Description:
	*   Called when a scene finishes in this room or the day ends. resets curShot, sets scene to null, and removes actors' roles.
	*/
	public void closeRoom()
	{
		curShot = 0;
		scene = null;
		for(Player actor : actorInfo)
		{
			actor.removeRole();
			actor.resetRehearse();
		}
		actorInfo.clear();
	}
	
	/*
	* Function: visit()
	* Description:
	*   Called whenever a Player enters the room. Reveals the scene card for this room if it is face-down.
	*/
	public void visit() {
		if((scene != null) && (!scene.getFlipped())) {
			scene.flipCard();
		}
	}
}