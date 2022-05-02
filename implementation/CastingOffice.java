package implementation;

/*
* Class: CastingOffice
* Description:
*   Represents the Casting Office where players can purchase rank upgrades.
*/
public class CastingOffice extends Room
{
	private final int[][] castingInfo;	// holds the prices in dollars and credits for each level of rank.
	
	/*
	* Constructor
	* Description:
	*   Calls the parent constructor as well as initializing castingInfo.
	*/
	public CastingOffice(castingInfo)
	{
		// TODO
	}
	
	/*
	* Function: action
	* Parameter:
	*   Player p: the player who is executing the action
	* Returns:
	*   bool: returns whatever upgradeManager() returns
	* Description:
	*   This just acts as a wrapper method for the private method, upgradeManager(), which actually handles players purchasing rank upgrades.
	*/
	public bool action(Player p)
	{
		// TODO
	}
	
	/*
	* Function: upgradeManager
	* Parameter:
	*   Player p: the player who is purchasing a rank
	* Returns:
	*   bool: true if player purchased a rank, false if player backed out or had insufficient funds.
	* Description:
	*   Handles what happens when a player attempts to purchase a rank. Takes input from player for which rank to buy
	*   and what currency to use, and makes the purchase by setting the appropriate values in the player if the player
	*   has enough resources to make the purchase, and if the player does not already possess an equal or higher rank.
	*/
	private bool upgradeManager(Player p)
	{
		// TODO
	}
}