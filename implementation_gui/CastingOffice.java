import java.util.ArrayList;

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
	public CastingOffice(String name, ObjCoord coord, int[][] placeable, int[][] castingInfo)
	{
		super(name, coord, placeable);
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
			// System.out.println("Enter the level of rank you'd like to buy, or 'q' to go back:");
			// String usrEntry = DeadWood.feed.nextLine();

			ArrayList<String> options = new ArrayList<String>();
			for(int i = p.getRank()-1; i < castingInfo.length; i++)
			{
				options.add("Purchase Rank "+(i+2));
			}
			options.add("Go Back");

			String usrEntry = DeadWood.getButtonInput(options);

			if(usrEntry.equals("Go Back"))
			{
				// User entered back command
				return false;
			}
			else for(int i = p.getRank()-1; i < castingInfo.length; i++)
			{
				if(usrEntry.equals("Purchase Rank "+(i+2)))
				{
					// User entered a valid rank
					if(i+2 > p.getRank())
					{
						// User entered a purchaseable rank
						if(purchaseRank(p,i+2))
						{
							return true;
						}
						continue;
					}
				}
			}
		}
	}

	/**
	 * Attempt to purchase a rank for a player and display error messages if there is a problem with the transaction.
	 * Also prompts whether to buy with cash or credits
	 * @param p Player purchasing the rank
	 * @param r Rank to purchase
	 * @return If the transaction was successful
	 */
	private boolean purchaseRank(Player p, int r)
	{
		// Loop until player successfully buys a rank or backs out
		while(true)
		{
			// System.out.println("Enter 'm' to pay with money, 'c' to pay with credits, or 'q' to go back:");
			ArrayList<String> options = new ArrayList<String>();
			options.add("Buy With Cash");
			options.add("Buy With Credits");
			options.add("Go Back");
	
			String usrEntry = DeadWood.getButtonInput(options);
			if(usrEntry.equals("Go Back"))
			{
				// User entered back command
				return false;
			}
			else if(usrEntry.equals("Buy With Cash"))
			{
				// User purchasing rank with money
				if(p.subtractFunds(castingInfo[r-2][1]))
				{
					DeadWood.showMessage(String.format("Rank upgraded from %d to %d!\nPaid: $%d",p.getRank(),r,castingInfo[r-2][1]),"Purchasing Rank!");
					p.setRank(r);
					return true;
				}
				DeadWood.showMessage(String.format("Insufficient funds.\nOwned: $%d\nRequired $%d",p.getFunds(),castingInfo[r-2][1]));
				// System.out.println("Insufficient funds.");
			}
			else
			{
				// User purchasing rank with credits
				if(p.subtractCredits(castingInfo[r-2][2]))
				{
					DeadWood.showMessage(String.format("Rank upgraded from %d to %d!\nPaid: %d credits",p.getRank(),r,castingInfo[r-2][2]),"Purchasing Rank!");
					p.setRank(r);
					return true;
				}
				DeadWood.showMessage(String.format("Insufficient credits.\nOwned: %d\nRequired %d",p.getCredits(),castingInfo[r-2][2]));
				// System.out.println("Insufficient credits.");
			}
		}
	}
}