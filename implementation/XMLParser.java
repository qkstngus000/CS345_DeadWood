package implementation;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * XMLParser
 */
public class XMLParser {
	// private static String boardPath = (new
	// File("xml/board.xml")).getAbsolutePath();
	// private static String cardPath = (new
	// File("xml/card.xml")).getAbsolutePath();
	private static String boardPath = "board.xml";
	private static String cardPath = "card.xml";

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
	 */
	public static Room[] XMLParseBoard() {

		Document doc = null;
		Room[] parsedRoom = new Room[12];
		int[][] upgradeInfo = new int[5][3];
		try {
			doc = getDocFromFile(boardPath);
			Element root = doc.getDocumentElement();
			NodeList sceneRoom = root.getElementsByTagName("set");
			NodeList office = root.getElementsByTagName("office");
			NodeList trailer = root.getElementsByTagName("trailer");

			// Read data from nodelist for scene
			for (int i = 0; i < sceneRoom.getLength() - 2; i++) {
				System.out.println("Printing information for room " + (i + 1));

				// Reads data from individual node
				Node room = sceneRoom.item(i);
				String roomName = room.getAttributes().getNamedItem("name").getNodeValue();
				System.out.println("name of room: " + roomName);
				int shots = ((Element) ((Element) room).getElementsByTagName("takes").item(0))
						.getElementsByTagName("take").getLength();
				System.out.println("shots: " + shots);
				Room curRoom = new SceneRoom(roomName, shots);
				parsedRoom[i] = curRoom;
			}

			// For index, get upgrade info and store int in upgrade
			Node upgrades = root.getElementsByTagName("upgrades").item(0);
			NodeList upgrade = ((Element) upgrades).getElementsByTagName("upgrade");
			int upgradeSz = upgrade.getLength();
			for (int i = 0; i < upgradeSz; i++) {
				Node info = upgrade.item(i);
				// Parse level from upgrade tag in XML
				System.out.println("Level: " + info.getAttributes().getNamedItem("level").getNodeValue());
				int level = Integer.parseInt(info.getAttributes().getNamedItem("level").getNodeValue());

				// Parse currency from upgrade tag in XML
				String currency = info.getAttributes().getNamedItem("currency").getNodeValue();

				// Parse amount of currency needed
				int amt = Integer.parseInt(info.getAttributes().getNamedItem("level").getNodeValue());

				// Store values in info array
				upgradeInfo[(i % 5)][0] = level;

				if (currency.equals("dollar")) {
					upgradeInfo[i % 5][1] = amt;
				} else {
					upgradeInfo[i % 5][2] = amt;
				}
			}

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print(upgradeInfo[i][j] + " ");
				}
				System.out.println();
			}

			// Put office and trailer into room 11 & 12th index
			// Room castingOffice = new CastingOffice("Office"/* , Something */ );

			// Loop one more time to set neighbors

		} catch (Exception e) {
			System.out.println("Error = " + e);
		}

		return parsedRoom;

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
		// TODO
		ArrayList<SceneCard> card = new ArrayList<SceneCard>();
		return card;
	}

	public static void main(String[] args) {
		XMLParseBoard();

	}
}