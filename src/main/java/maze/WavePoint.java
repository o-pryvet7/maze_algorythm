package maze;

/**
 * @author o_pryvet7
 * <p>Class for storing wave data</p>
 */
public class WavePoint {

    /**
     * Basic initialization
     */
    public WavePoint() {
        Enable = true;
        value = 0;
        xPos = 0;
        yPos = 0;
    }

    private boolean IsWall;

    private boolean Enable;

    private int xPos, yPos;

    private int value;

    /**
     * Marks a point as inaccessible
     */
    public void Disabled() {
        Enable = false;
    }

    /**
     * @return point availability
     */
    public boolean IsEnabled() {
        return Enable;
    }

    /**
     * @param _wall - sets the presence of a wall at a point
     */
    public void SetIsWall(boolean _wall) {
        IsWall = _wall;
    }

    /**
     * @return checks for a wall at a point
     */
    public boolean GetIsWall() {
        return IsWall;
    }

    /**
     * @param n - current y
     */
    public void SetY(int n) {
        yPos = n;
    }

    /**
     * @param n - current x
     */
    public void SetX(int n) {
        xPos = n;
    }

    /**
     * @return this.x
     */
    public int GetX() {
        return xPos;
    }

    /**
     * @return this.y
     */
    public int GetY() {
        return yPos;
    }

    /**
     * @param _value - is value of current point
     */
    public void SetValue(int _value) {
        value = _value;
    }

    /**
     * @return - value of current point
     */
    public int GetValue() {
        return value;
    }
}
