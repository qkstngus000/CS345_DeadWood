package implementation;

/*
 * Class: Role
 * Description:
 *  This class will be responsible for having all roles in its role objects
 *  and provide role information to scenecard and sceneroom.
 */
public class Role {
    private String roleName; // Name of role
    private String line;    // Role description
    private int rank; // the rank of role
    private boolean mainRole; // Type of role whether role is main or extra
    private boolean roleDeprived; // Track of role if role is already used or currently in use
    private ObjCoord coord;

    /*
     * Constructor: Role
     * Parameter:
     * String roleName: Assign roleName to each object
     * int rank: Give rank to each role
     * boolean main: Verify if role is main or extra
     * 
     * Description:
     * Initialize the role objects that contains informations of each role.
     */
    public Role(String roleName, int rank, String line, boolean main, ObjCoord coord) {
        this.roleName = roleName;
		this.rank = rank;
		this.mainRole = main;
		this.roleDeprived = false;
        this.line = line;
    }
}
