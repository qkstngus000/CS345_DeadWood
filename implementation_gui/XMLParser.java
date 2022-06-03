
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XMLParser
 */
public class XMLParser {

	private static final boolean debug = false; // Set to true to print lots of stuff

	// private static String boardPath = (new
	// File("xml/board.xml")).getAbsolutePath();
	// private static String cardPath = (new
	// File("xml/card.xml")).getAbsolutePath();
	private static String boardPath = "../board.xml";
	private static String cardPath = "../cards.xml";

	public static Document getDocFromFile(String filename) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = null;

		try {
			doc = db.parse(filename);
		} catch (Exception ex) {
			System.out.println("XML parse failure");
			ex.printStackTrace();
		}
		return doc;
	}

	/*
	 * Function: XMLParseBoard
	 * Parameter:
	 * String boardData: filename of file to parse
	 * Description:
	 * Parses an XML file containing data for the rooms and creates the 'rooms'
	 * field accordingly.
	 * Return: parsedRoom[12]:
	 * index 0 trailer
	 * index 1 office
	 * index 2 Train Station
	 * index 3 Secret Hideout
	 * index 4 Church
	 * index 5 Hotel
	 * index 6 Main Street
	 * index 7 Jail
	 * index 8 General Store
	 * index 9 Ranch
	 * index 10 Bank
	 * index 11 Saloon
	 */
	public static Room[] XMLParseBoard() {

		Document doc = null;
		Room[] parsedRoom = new Room[12];
		int[][] upgradeInfo = new int[5][3];
		int[][] placeable;
		try {
			doc = getDocFromFile(boardPath);
			Element root = doc.getDocumentElement();
			NodeList sceneRoom = root.getElementsByTagName("set");
			Node office = root.getElementsByTagName("office").item(0);
			Node trailer = root.getElementsByTagName("trailer").item(0);

			// Read data from nodelist for scene
			for (int i = 0; i < sceneRoom.getLength(); i++) {
				if(debug) System.out.println("Printing information for room " + (i + 1)); // Log to see
				// the loop of each element

				// Reads data from individual node
				Node room = sceneRoom.item(i);
				String roomName = room.getAttributes().getNamedItem("name").getNodeValue();
				if(debug) System.out.println("name of room: " + roomName); // Log to see the name of
				// room printout statement
				int numShots = ((Element) ((Element) room).getElementsByTagName("takes").item(0))
						.getElementsByTagName("take").getLength();
				if(debug) System.out.println("\tshots: " + numShots); // Log to see shot value

				
				ObjCoord[] coordList = new ObjCoord[numShots];	// Initialize objCoord array to store shot coordinates
				
				// Get coordinates for each shots
				for (int j = 0; j < numShots; j++) {
					Node take = ((Element) ((Element) room).getElementsByTagName("takes").item(0)).getElementsByTagName("take").item(j);
					int number = Integer.parseInt(take.getAttributes().getNamedItem("number").getNodeValue());
					if(debug) System.out.println("\tnumber: " + number);

					int x_cord = Integer.parseInt(((Element) take).getElementsByTagName("area").item(0).getAttributes().getNamedItem("x").getNodeValue());
					int y_cord = Integer.parseInt(((Element) take).getElementsByTagName("area").item(0).getAttributes().getNamedItem("y").getNodeValue());
					int w = Integer.parseInt(((Element) take).getElementsByTagName("area").item(0).getAttributes().getNamedItem("w").getNodeValue());
					int h = Integer.parseInt(((Element) take).getElementsByTagName("area").item(0).getAttributes().getNamedItem("h").getNodeValue());
					if(debug) System.out.printf("\tx_cord: %d, y_cord: %d, w: %d, h: %d\n", x_cord, y_cord, w, h);
					// ObjCoord takeCord = new ObjCoord(x_cord, y_cord, w, h);
					coordList[j] = new ObjCoord(x_cord, y_cord, w, h);
				}
				
				// Get number of roles in the room
				int numRoles = ((Element) ((Element) room).getElementsByTagName("parts").item(0)).getElementsByTagName("part").getLength();
				if(debug) System.out.println("numRoles: " + ((Element) ((Element) room).getElementsByTagName("parts").item(0)).getElementsByTagName("part").getLength());
				Role[] roleList = new Role[numRoles];	// Initialize Role list array

				// Get coordinates and informations for each role in the room
				for (int j = 0; j < numRoles; j++) {
					// Get role
					Node role = ((Element) ((Element) room).getElementsByTagName("parts").item(0)).getElementsByTagName("part").item(j);
					String roleName = role.getAttributes().getNamedItem("name").getNodeValue();
					if(debug) System.out.println("Role Name: " + roleName);

					int rank = Integer.parseInt(role.getAttributes().getNamedItem("level").getNodeValue());
					if(debug) System.out.println("\tlevel: " + rank);
					String line = ((Element) role).getElementsByTagName("line").item(0).getTextContent();
					if(debug) System.out.println("\tLine: " + line);
					

					// Get role coordinate
					ObjCoord roleCoord = parseCoord((Element) role);

					roleList[j] = new Role(roleName, rank, line, false, roleCoord);	// Store parsed role variable into roleList
				}
				// Get role coordinate
				ObjCoord coord = parseCoord((Element) room);

				// Assign placeable location in the board
				if (roomName.equals("Train Station")) {
					if (debug) System.out.println("storing placeable location for train station");
					placeable = new int[][] {{200, 20},{10, 190}, {60, 220}, {10, 240}, {10, 290}, {10, 340}, {10, 390}, {120, 390}};
				}
				else if (roomName.equals("Secret Hideout")) {
					if (debug) System.out.println("storing placeable location for Secret Hideout");
					placeable = new int[][] {{240, 815}, {290, 815}, {340, 815}, {390, 815}, {240, 860}, {290, 860}, {340, 860}, {390, 860}}; 
				}
				else if (roomName.equals("Church")) {
					if (debug) System.out.println("storing placeable location for Church");
					placeable = new int[][] {{730, 645}, {780, 645}, {730, 690}, {780, 690}, {830, 690}, {880, 690}, {610, 855}, {800, 855}};
				}
				else if (roomName.equals("Hotel")) {
					if (debug) System.out.println("storing placeable location for Hotel");
					placeable = new int[][] {{1000, 460}, {1050, 460}, {1000, 510}, {1000, 560}, {1000, 610}, {1100, 630}, {1150, 630}, {950, 695}};
				}
				else if (roomName.equals("Main Street")) {
					if (debug) System.out.println("storing placeable location for Main Street");
					placeable = new int[][] {{770, 70}, {820, 70}, {870, 70}, {920, 70}, {770, 120}, {820, 120}, {870, 120}, {920, 120}};
				}
				else if (roomName.equals("Jail")) {
					if (debug) System.out.println("storing placeable location for Jail");
					placeable = new int[][] {{290, 160}, {340, 160}, {390, 160}, {390, 205}, {440, 205}, {490, 205}, {490, 160}, {540, 160}};
				}
				else if (roomName.equals("General Store")) {
					if (debug) System.out.println("storing placeable location for General Store");
					placeable = new int[][] {{280, 375}, {325, 375}, {280, 417}, {330, 417}, {380, 405}, {430, 405}, {480, 405}, {530, 405}};
				}
				else if (roomName.equals("Ranch")) {
					if (debug) System.out.println("storing placeable location for Ranch");
					placeable = new int[][] {{265, 605}, {315, 605}, {365, 605}, {265, 655}, {315, 655}, {365, 655}, {540, 525}, {540, 575}};
				}
				else if (roomName.equals("Bank")) {
					if (debug) System.out.println("storing placeable location for Bank");
					placeable = new int[][] {{840, 460}, {840, 510}, {610, 600}, {660, 600}, {710, 600}, {760, 600}, {810, 600}, {860, 600}};
				}
				else {
					if (debug) System.out.println("storing placeable location for Saloon");
					placeable = new int[][] {{735, 230}, {785, 230}, {835, 230}, {885, 230}, {935, 230}, {935, 280}, {935, 330}, {935, 380}};
				}

				Room curRoom = new SceneRoom(roomName, coord, placeable,  numShots, coordList);
				((SceneRoom) curRoom).setRoles(roleList);
				
				parsedRoom[i + 2] = curRoom;
			}

			// For index, get upgrade info and store int in upgrade
			Node upgrades = root.getElementsByTagName("upgrades").item(0);
			NodeList upgrade = ((Element) upgrades).getElementsByTagName("upgrade");
			int upgradeSz = upgrade.getLength();
			for (int i = 0; i < upgradeSz; i++) {
				Node info = upgrade.item(i);
				// Parse level from upgrade tag in XML
				// System.out.println("Level: " + info.getAttributes().getNamedItem("level").getNodeValue()); // Log to check
				// the level of rank
				int level = Integer.parseInt(info.getAttributes().getNamedItem("level").getNodeValue());

				// Parse currency from upgrade tag in XML
				String currency = info.getAttributes().getNamedItem("currency").getNodeValue();

				// Parse amount of currency needed
				int amt = Integer.parseInt(info.getAttributes().getNamedItem("amt").getNodeValue());

				// Store values in info array
				upgradeInfo[(i % 5)][0] = level;

				if (currency.equals("dollar")) {
					upgradeInfo[i % 5][1] = amt;
				} else {
					upgradeInfo[i % 5][2] = amt;
				}
			}

			// Print statement to see if info is stored correctly
			if (debug) {
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 3; j++) {
						System.out.print(upgradeInfo[i][j] + " ");
					}
					System.out.println();
				}
			}
			
			placeable = new int[][] {{10, 470}, {10, 520}, {10, 570}, {10, 620}, {175, 470}, {175, 520}, {175, 570}, {175, 620}};
			// Put office and trailer into room 10 & 11th index
			Room castingOffice = new CastingOffice("Casting Office",parseCoord((Element) office), placeable, upgradeInfo);
			parsedRoom[1] = castingOffice;

			placeable = new int[][] {{1001,253}, {1001, 303}, {1001, 353}, {1001, 403}, {1071, 253}, {1071, 303}, {1071, 353}, {1071, 403}};
			Room trailerRoom = new Room("Trailers", parseCoord((Element) trailer), placeable);
			parsedRoom[0] = trailerRoom;

			// Logs to check if name is correctly set for each obj.
			/*
			 * int counter = 0;
			 * for (Room i : parsedRoom) {
			 * System.out.println( "index " + counter + " " + i.getName());
			 * counter++;
			 * }
			 */

			// Since The parsing room operation is completed,
			// Set neighbors for casting office
			int numOfficeNeighbors = ((Element) ((Element) office).getElementsByTagName("neighbors").item(0))
					.getElementsByTagName("neighbor").getLength();
			Room[] officeNeighborRoom = new Room[numOfficeNeighbors];
			NodeList officeNeighborPath = ((Element) ((Element) office).getElementsByTagName("neighbors").item(0))
					.getElementsByTagName("neighbor");
			for (int i = 0; i < numOfficeNeighbors; i++) {
				// System.out.println("Printing neighbor of office: " +
				// officeNeighborPath.item(i).getAttributes().getNamedItem("name").getNodeValue());
				// // Log
				String neigh = officeNeighborPath.item(i).getAttributes().getNamedItem("name").getNodeValue();
				for (Room r : parsedRoom) {
					if (r.getName().equals(neigh)) {
						// System.out.println(r.getName()); // Log
						officeNeighborRoom[i] = r;
					}
				}
			}
			parsedRoom[1].setNeighbors(officeNeighborRoom);

			// Set neighbors for trailer
			int numTrailerNeighbors = ((Element) ((Element) trailer).getElementsByTagName("neighbors").item(0))
					.getElementsByTagName("neighbor").getLength();
			Room[] trailerNeighborRoom = new Room[numTrailerNeighbors];
			NodeList trailerNeighborPath = ((Element) ((Element) trailer).getElementsByTagName("neighbors").item(0))
					.getElementsByTagName("neighbor");
			for (int i = 0; i < numTrailerNeighbors; i++) {
				// System.out.println("Printing neighbor of trailer: " +
				// trailerNeighborPath.item(i).getAttributes().getNamedItem("name").getNodeValue());
				// // Log
				String neigh = trailerNeighborPath.item(i).getAttributes().getNamedItem("name").getNodeValue();
				for (Room r : parsedRoom) {
					if (r.getName().equals(neigh)) {
						trailerNeighborRoom[i] = r;
					}
				}
			}
			parsedRoom[0].setNeighbors(trailerNeighborRoom);

			// Loop one more time to set neighbors for scene rooms
			// System.out.println(sceneRoom.getLength()); // Log
			for (int i = 0; i < sceneRoom.getLength(); i++) {
				// System.out.println("Printing information for room " + i); // Log
				Node room = sceneRoom.item(i);
				// String workingRoom =
				// room.getAttributes().getNamedItem("name").getNodeValue(); // Log
				int numNeighbors = ((Element) ((Element) room).getElementsByTagName("neighbors").item(0))
						.getElementsByTagName("neighbor").getLength();
				// System.out.println("Number of neighbors: " + numNeighbors); // Log
				Room[] neighborRoom = new Room[numNeighbors];
				NodeList neighborPath = ((Element) ((Element) room).getElementsByTagName("neighbors").item(0))
						.getElementsByTagName("neighbor");

				// System.out.println("\tCurrent Working Room: " + workingRoom); // Log
				for (int j = 0; j < numNeighbors; j++) {
					// System.out.println("\t\tneighboringRoom: " +
					// neighborPath.item(j).getAttributes().getNamedItem("name").getNodeValue()); //
					// Log
					String neigh = neighborPath.item(j).getAttributes().getNamedItem("name").getNodeValue();
					for (Room r : parsedRoom) {
						if (r.getName().equals(neigh)) {
							neighborRoom[j] = r;
						}
					}
				}
				parsedRoom[i + 2].setNeighbors(neighborRoom);
				// System.out.println("end of iteration " + i); // Log
			}

		} catch (Exception e) {
			System.out.println("Error = " + e);
		}

		return parsedRoom;
//hi sage
// 	sincerely,
//		matthew~
	}

	/*
	 * Function: XMLParseCard
	 * Parameter:
	 * String cardData: filename of file to parse
	 * Description:
	 * Parses an XML file containing data for the scene cards and creates the 'deck'
	 * field accordingly.
	 */
	public static ArrayList<SceneCard> XMLParseCard() {
		ArrayList<SceneCard> parsedCard = new ArrayList<SceneCard>();

		Document doc = null;
		try {
			doc = getDocFromFile(cardPath);
			Element root = doc.getDocumentElement();
			NodeList cardList = root.getElementsByTagName("card");
			int numCards = cardList.getLength();
			for (int i = 0; i < numCards; i++) {
				Node card = cardList.item(i);
				String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
				String img = card.getAttributes().getNamedItem("img").getNodeValue();
				int budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
				int sceneNumbering = Integer.parseInt(((Element) card).getElementsByTagName("scene").item(0)
						.getAttributes().getNamedItem("number").getNodeValue());

				if(debug) System.out.println("Numbering: " + ((Element) card).getElementsByTagName("scene").item(0)
						.getAttributes().getNamedItem("number").getNodeValue()); // Log
				if(debug) System.out.println("SceneDescription: " + ((Element) card).getElementsByTagName("scene").item(0).getTextContent());
				String sceneDesc = ((Element) card).getElementsByTagName("scene").item(0).getTextContent();
				SceneCard s = new SceneCard(cardName, img, budget, sceneNumbering, sceneDesc);
				parsedCard.add(s);

				// Add roles to the card

				if(debug) System.out.println("Number of roles " + ((Element) card).getElementsByTagName("part").getLength());
				int numRoles = ((Element) card).getElementsByTagName("part").getLength();
				Role[] cardRoles = new Role[numRoles];
				for (int j = 0; j < numRoles; j++) {
					Node role = ((Element) card).getElementsByTagName("part").item(j); // Get indiv role node

					String name = role.getAttributes().getNamedItem("name").getNodeValue();
					int level = Integer.parseInt(role.getAttributes().getNamedItem("level").getNodeValue());

					if(debug) System.out.println("\trole name: " + role.getAttributes().getNamedItem("name").getNodeValue());
					if(debug) System.out.println("\trole level: " + role.getAttributes().getNamedItem("level").getNodeValue());

					Node coord = ((Element) role).getElementsByTagName("area").item(0);
					int x_cord = Integer.parseInt(coord.getAttributes().getNamedItem("x").getNodeValue());
					int y_cord = Integer.parseInt(coord.getAttributes().getNamedItem("y").getNodeValue());
					int h = Integer.parseInt(coord.getAttributes().getNamedItem("h").getNodeValue());
					int w = Integer.parseInt(coord.getAttributes().getNamedItem("w").getNodeValue());
					if(debug) System.out.printf("\tx_cord: %d, y_cord: %d, h: %d, w: %d\n", x_cord, y_cord, h, w);

					String line = ((Element) role).getElementsByTagName("line").item(0).getTextContent();
					if(debug) System.out.println("\tline: " + ((Element) role).getElementsByTagName("line").item(0).getTextContent());
					
					ObjCoord roleCoord = new ObjCoord(x_cord, y_cord, w, h);

					Role r = new Role(name, level, line, true, roleCoord); // Every roles are main role, so initialize it to be true
					cardRoles[j] = r;
				}
				// Add cardRoles into SceneRoom
				parsedCard.get(i).setRole(cardRoles);
			}

		} catch (Exception e) {
			System.out.println("Error = " + e);
		}

		return parsedCard;
	}

	/**
	 * Helper method to parse ObjCoords
	 * @param area the Element containing x,y,w,h data
	 * @return a new ObjCoord object encoding the same data as the supplied element
	 */
	private static ObjCoord parseCoord(Element element)
	{
		Element area = (Element) element.getElementsByTagName("area").item(0);
		int x_cord = Integer.parseInt(area.getAttributes().getNamedItem("x").getNodeValue());
		int y_cord = Integer.parseInt(area.getAttributes().getNamedItem("y").getNodeValue());
		int w = Integer.parseInt(area.getAttributes().getNamedItem("w").getNodeValue());
		int h = Integer.parseInt(area.getAttributes().getNamedItem("h").getNodeValue());
		if(debug) System.out.printf("\tarea: x_cord: %d, y_cord: %d, w: %d, h: %d\n", x_cord, y_cord, w, h);

		return new ObjCoord(x_cord, y_cord, w, h);
	}

	public static void main(String[] args) {
		XMLParseBoard();

	}
}
