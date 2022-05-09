package implementation;

/*
 * Class: ObjCoord
 * Description:
 *      Store values of x_coordinate, y_coordinate, width, and height of little objects used in Scenecard and Board
 *      This will be used when graphic visualization happens
 */
public class ObjCoord {
    private int x_cord; // Store x coordinate of the obj
    private int y_cord; // Store y coordinate of the obj
    private int w;  // Store width of the obj
    private int h;  // Store height of the obj
    
    /*
     * Constructor ObjCoord
     * Parameter:
     *      x_cord: Retrieve data from XML parse and store obj coordinate of x
     *      y_cord: Retrieve data from XML parse and store obj coordinate of y 
     *      w: Retrieve data from XML parse and store obj width
     *      h: Retrieve data from XML parse and store obj height
     * Description:
     *      Simple constructor to store coordinate and size of object in SceneCard and Board
     */
    public ObjCoord(int x_cord, int y_cord, int w, int h) {
        this.x_cord = x_cord;
        this.y_cord = y_cord;
        this.h = h;
        this.w = w;
    }
}
