package implementation;

/*
 * Class: SceneCard
 * Description:
 *  Initialized as Board is calling XMLParseCard to retrieve information from
 *  XML file. 
 *  Then, the cards are going to be randomly put onto the created board for scene.
 */
public class SceneCard {
    public String name; // The name of the scene
    private int[] roles; // Roles that scenecard would have
    public String img; // The filename of the image for this card
    public boolean flip; // Boolean storing whether there was visitor in the room. If not, faced down as
                         // false
    private int budget; // Store budget value for scene

    /*
     * Constructor: SceneCard
     * Parameter:
     * XMLData: XML card data brought from Board
     * 
     * Description:
     * Create objects with using information passed down from XML Card Parse
     * Store information given
     */
    public SceneCard(String name, String img, int budget) {
        this.name = name;
		this.img = img;
		this.budget = budget;
    }

    /*
     * Function: cardUsed
     * Parameter:
     * None
     * Description:
     * Check if card has been already used so used cards would not be placed into
     * the board
     * again.
     * 
     * return: boolean
     * Return false if the card has been used before, and true for not used.
     * Change the cardUsed status if return true;
     */
    public boolean cardUsed() {
        // TO DO
        return false;
    }
}
