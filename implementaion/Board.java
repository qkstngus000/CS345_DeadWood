package implementaion;

import java.util.ArrayList;

/*
* Class: Board
* Description:
*   This class will be created upon game initialization by the DeadWood class, and has two main responsibilities.
*     1. Build the rooms and scene card deck by parsing the two XML documents, and connect the rooms by initializing adjRooms[] for each of them.
*     2. Distribute new scene cards to scene rooms when requested.
*/
public class Board {
    private int numRoom;				// number of rooms for this board
	private ArrayList<SceneCard> deck;	// List containing the 'deck' of SceneCards
	private Room[] rooms;				// Array holding all rooms associated with the board. Should be length [numRoom]
	
	/*
	* Constructor
	* Parameter:
	*   int numRoom: used to set self.numRoom
	*   String boardData: filename to be passed to XMLParseBoard()
	*   String cardData: filename to be passed to XMLParseCard()
	* Description:
	*   Initializes the board as well as running XMLParseBoard() and XMLParseCard() to create the deck and the room list.
	*   Then, connects rooms to each other by setting adjRooms[] for each of them
	*/
	public Board(int numRoom, String boardData, String cardData)
	{
		// TODO
	}
	
	/*
	* Function: XMLParseBoard
	* Parameter:
	*   String boardData: filename of file to parse
	* Description:
	*   Parses an XML file containing data for the rooms and creates the 'rooms' field accordingly.
	*/
	public void XMLParseBoard(String boardData)
	{
		// TODO
	}
	
	/*
	* Function: XMLParseCard
	* Parameter:
	*   String cardData: filename of file to parse
	* Description:
	*   Parses an XML file containing data for the scene cards and creates the 'deck' field accordingly.
	*/
	public void XMLParseCard(String cardData)
	{
		// TODO
	}
	
	/*
	* Function: reset
	* Description:
	*   calls distributeCard() on every SceneRoom in the board to repopulate the board with scenes.
	*   Called at the start of each 'day' of gameplay by the DeadWood class.
	*/
	public void reset()
	{
		// TODO
	}
	
	/*
	* Function: distributeCard
	* Parameter:
	*   SceneRoom r: the room to recieve a new SceneCard
	* Description:
	*   Pops a random SceneCard from the deck and gives it to SceneRoom 'r' by updating its scene field.
	*/
	public void distributeCard(SceneRoom r)
	{
		// TODO
	}
}
