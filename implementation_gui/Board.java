
import java.util.ArrayList;
import java.util.Random;

/*
* Class: Board
* Description:
*   This class will be created upon game initialization by the DeadWood class, and has two main responsibilities.
*     1. Build the rooms and scene card deck by parsing the two XML documents, and connect the rooms by initializing adjRooms[] for each of them.
*     2. Distribute new scene cards to scene rooms when requested.
*/
public class Board {
	private int scenesLeft;					// Number of rooms with currently active scenes
	private ArrayList<SceneCard> deck;		// List containing the 'deck' of SceneCards
	private ArrayList<SceneCard> discard;	// List containing the SceneCards which are already used
	private Room[] rooms;					// Array holding all rooms associated with the board.
	
	/*
	* Constructor
	* Parameter:
	*   int numRoom: used to set self.numRoom
	*   String boardData: filename to be passed to XMLParseBoard()
	*   String cardData: filename to be passed to XMLParseCard()
	* Description:
	*   Initializes the board as well as running XMLParseBoard() and XMLParseCard() to create the deck and the room list.
	*/
	public Board()
	{
		rooms = XMLParser.XMLParseBoard();
		deck = XMLParser.XMLParseCard();
		discard = new ArrayList<SceneCard>();
	}
	
	
	public int getScenesLeft()
	{
		return scenesLeft;
	}
	
	public void decrementScenesLeft()
	{
		scenesLeft--;
	}
	
	public Room getStartingRoom()
	{
		return rooms[0];
	}
	
	
	/*
	* Function: reset
	* Description:
	*   calls distributeCard() on every SceneRoom in the board to repopulate the board with scenes.
	*   This method is also in charge of properly setting scenesLeft.
	*   Called at the start of each 'day' of gameplay by the DeadWood class.
	*/
	public void reset(View view)
	{
		int i;
		for (i = 0; i < rooms.length; i++)
		{
			if (rooms[i] instanceof SceneRoom)
			{
				SceneRoom scene = (SceneRoom) rooms[i];
				// view.drawShots(scene);
				if (!distributeCard(scene)) break;
				view.drawElement(scene.getScene());
				view.drawShotCounters(scene);
			}
		}
		scenesLeft = i;
	}
	
	/*
	* Function: distributeCard
	* Parameter:
	*   SceneRoom s: the room to recieve a new SceneCard
	* Returns:
	*   bool: whether or not the operation was performed successfully. Will only fail if no cards
	*   are left in the deck.
	* Description:
	*   Pops a random SceneCard from the deck and gives it to SceneRoom 's' by updating its scene field.
	*   The SceneCard is then added to the discard pile.
	*   The SceneCard given to s is a shallow copy of the one in the discard pile so as to avoid making
	*   permanent changes to the original.
	*/
	private boolean distributeCard(SceneRoom s)
	{
		if (deck.size() <= 0)
		{
			return false;
		}
		Random r = new Random();
		SceneCard c = deck.remove(r.nextInt(deck.size()));
		s.setScene(c);
		// TODO set SceneCard's draw position
		ObjCoord roomCoord = s.getCoord();
		c.setCoord(new ObjCoord(roomCoord.getX(),roomCoord.getY(),SceneCard.imgX,SceneCard.imgY));
		discard.add(c);
		
		return true;
	}
	
	/*
	* Function: newDeck
	* Description: replaces cards from the discard list into the deck to reset it for a new game.
	*/
	public void newDeck()
	{
		deck.addAll(discard);
		discard.clear();
	}
}
