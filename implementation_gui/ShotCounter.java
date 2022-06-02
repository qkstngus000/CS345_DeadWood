public class ShotCounter implements Drawable{
    private static final String imgPath = "../images/shot.png";
    private static final int depth = 3;
    ObjCoord pos;

    public ShotCounter(ObjCoord pos)
    {
        this.pos = pos;
    }

    public ObjCoord getCoord()
    {
        return pos;
    }

    public int getDepth()
    {
        return depth;
    }

    public String getImgPath()
    {
        return imgPath;
    }
}
