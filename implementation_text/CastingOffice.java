package implementation_text;
/**
* Class: CastingOffice
* Description:
*   Represents the Casting Office where players can purchase rank upgrades.
*/
public class CastingOffice extends Room
{
	/*
	* castingInfo: holds the prices in dollars and credits for each level of rank.
	* castingInfo[0][]: rank
	* castingInfo[1][]: dollars
	* castingInfo[2][]: credits
	*/
	private final int[][] castingInfo;
	
	/*
	* Constructor
	* Description:
	*   Calls the parent constructor as well as initializing castingInfo.
	*/
	public CastingOffice(String name, int[][] castingInfo)
	{
		super(name);
		this.castingInfo = castingInfo;
		
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
	public boolean action(Player p)
	{
		return upgradeManager(p);
	}
	
	/**
	 * Function: printCastingInfo
	 * Parameter:
	 *   None
	 * Returns:
	 *   None
	 * Description:
	 *    Function prints out rank upgrade information to user if user wants to do some action in casting office.
	 */
	private void printCastingInfo() {
		// Print welcome message
		System.out.println("Welcome to the Casting Office!");
		System.out.println("Here you can purchase a rank upgrade with either cash or credits.");

		// Print shop info
		System.out.println("\nRank|Cost in dollars|Cost in credits");
		System.out.println("----|---------------|---------------");

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 0) System.out.printf("%-4d", castingInfo[i][j]);
				else System.out.printf("|%-15d", castingInfo[i][j]);
			}
			System.out.println("\n");
		}
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
	private boolean upgradeManager(Player p)
	{
		printCastingInfo();
		// Repeat until the user enters a valid input
		while(true)
		{
			System.out.println("Enter the level of rank you'd like to buy, or 'q' to go back:");
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

				if(selection > 1 && selection <= castingInfo.length-1)
				{
					// User entered a valid rank
					if(selection > p.getRank())
					{
						// User entered a purchaseable rank
						if(purchaseRank(p,selection))
						{
							return true;
						}
						continue;
					}
					System.out.println("You already have a rank of equal or higher level.");
					continue;
				}
			}
			System.out.println("Invalid entry. Please try again.");
		}
	}


	private boolean purchaseRank(Player p, int r)
	{
		

		// Repeat until the user enters a valid input
		while(true)
		{
			System.out.println("Enter 'm' to pay with money, 'c' to pay with credits, or 'q' to go back:");
			String usrEntry = DeadWood.feed.nextLine();
			if(usrEntry.trim().toLowerCase().equals("q"))
			{
				// User entered back command
				return false;
			}
			if(usrEntry.trim().toLowerCase().equals("m"))
			{
				// User purchasing rank with money
				if(p.subtractFunds(castingInfo[r-2][1]))
				{
					return true;
				}
				System.out.println("Insufficient funds.");
				continue;
			}
			if(usrEntry.trim().toLowerCase().equals("c"))
			{
				// User purchasing rank with credits
				if(p.subtractCredits(castingInfo[r-2][2]))
				{
					return true;
				}
				System.out.println("Insufficient funds.");
				continue;
			}
			System.out.println("Invalid entry. Please try again.");
		}
	}
}