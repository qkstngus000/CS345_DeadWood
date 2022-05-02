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
    public String description; // Scene description from XML parse
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
    public SceneCard() {
        // TO DO
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
