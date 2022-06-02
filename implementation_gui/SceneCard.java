

/*
 * Class: SceneCard
 * Description:
 *  Initialized as Board is calling XMLParseCard to retrieve information from
 *  XML file. 
 *  Then, the cards are going to be randomly put onto the created board for scene.
 */
public class SceneCard implements Drawable{
    public String name; // The name of the scene
    private String sceneDes;    // The scene description
    private Role[] roles; // Roles that scenecard would have
    private String img; // The filename of the image for this card
    private static final String backImg = "../images/card_back.jpg"; // The path to the back image for all cards
    private ObjCoord coord; // The drawing coordinates for the SceneCard. Set when assigned to a room
    private boolean flip; // Boolean storing whether there was visitor in the room. If not, faced down as
                         // false
    private int budget; // Store budget value for scene
    private int numbering; // Indicates card number
    public static final int imgX = 115;
    public static final int imgY = 205;
    public static final int depth = 3;


    /*
     * Constructor: SceneCard
     * Parameter:
     * XMLData: XML card data brought from Board
     * 
     * Description:
     * Create objects with using information passed down from XML Card Parse
     * Store information given
     */
    public SceneCard(String name, String img, int budget, int numbering, String sceneDes) {
        this.name = name;
        this.img = img;
        this.budget = budget;
        this.numbering = numbering;
        this.sceneDes = sceneDes;
        flip = false;
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

    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return sceneDes;
    }

    public ObjCoord getCoord()
    {
        return coord;
    }
    
    public String getImgPath()
    {
        return flip ? img : backImg;
    }

    public int getDepth()
    {
        return depth;
    }
    
    public Role[] getRoles() {
        return roles;
    }

    public void setRole(Role[] r) 
    {
        this.roles = r;
    }

    public void setCoord(ObjCoord coord)
    {
        this.coord = coord;
    }

    public int getBudget() {
        return this.budget;
    }

    public void flipCard() {
        flip = !flip;
    }

    public boolean getFlipped()
    {
        return flip;
    }
}
