
/*
 * Class: ObjCoord
 * Description:
 *      Store values of x_coordinate, y_coordinate, width, and height of little objects used in Scenecard and Board
 *      This will be used when graphic visualization happens
 */
public final class ObjCoord {
    private final int x_cord; // Store x coordinate of the obj
    private final int y_cord; // Store y coordinate of the obj
    private final int w;  // Store width of the obj
    private final int h;  // Store height of the obj
    
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

    public int getX() {
        return x_cord;
    }

    public int getY() {
        return y_cord;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
