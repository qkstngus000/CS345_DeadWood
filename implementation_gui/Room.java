import java.util.HashMap;

/*
* Class: Room
* Description:
*   This abstract class acts as a parent to SceneRoom, CastingOffice, and Trailer, which are all rooms on the board.
*   It will be used to refer to the rooms in the board class, and holds the common functionality between all types of rooms.
*/
public class Room {
	private Room[] neighbors; // neighbors: holds references to the rooms that are reachable from this room in
								// one move.
	private int numActors; // number of actors currently in the room
	private String name;
	private ObjCoord coord;
	private int[][] placeable;
	private HashMap<Integer, Integer> validPosition;

	/**
	 * Constructor
	 * Description:
	 * A simple constructor which just sets name and coord and sets numActors to 0.
	 */
	public Room(String name, ObjCoord coord, int[][] placeable) {
		this.name = name;
		this.coord = coord;
		numActors = 0;
		this.placeable = placeable;
		validPosition = new HashMap<Integer, Integer>();
		/*
		 * for (int i = 0; i < 8; i++) {
		 * validPosition.put(i, false);
		 * }
		 */
	}

	public int getValidPosition(int id) {
		int i;
		for (i = 0; i < 8; i++) {
			if (!validPosition.containsKey(i)) {
				validPosition.put(i, id);
				return i;
			} else if (validPosition.containsKey(i) && validPosition.get(i) == id) {
				return i;
			}
		}
		return i;
	}

	public int[] getPlaceable(int i) {
		return placeable[i];
	}

	public void removeValidPosition(int id) {
		for (int i = 0; i < 8; i++) {
			if (validPosition.containsKey(i)) {
				if (validPosition.get(i) == id) {
					validPosition.remove(i, id);
				}
			}

		}

	}

	public String getName() {
		return name;
	}

	public ObjCoord getCoord() {
		return coord;
	}

	public void setNeighbors(Room[] neighbors) {
		this.neighbors = neighbors;
	}

	public Room[] getNeighbors() {
		return neighbors;
	}

	/*
	 * Function: action
	 * Parameter:
	 * Player p: the player who is executing the action
	 * Returns:
	 * bool: true if the action was completed and false if the player backed out/can
	 * still take another action this turn.
	 * Description:
	 * This method, to be implemented by the child classes, will be called by a
	 * player when they decide to take an action in
	 * this room during their turn, and will determine what happens from there on.
	 * It returns a bool to communicate to the player whether
	 * an action was actually completed. Here it has a default implementation which
	 * will be used by the Trailer room and simply prints
	 * that no action can be performed.
	 */
	public boolean action(Player p) {
		System.out.println("No action can be performed in this room.");
		return false;
	}
}