package maze;

public class WavePoint {

    public WavePoint(){
        Enable = true;
        value = 0;
        xPos = 0;
        yPos = 0;
    }

    private boolean IsWall;

    private boolean Enable;

    private int xPos, yPos;

    private int value;

    public void Enabled(){ Enable=true; }
    public void Disabled(){ Enable=false; }
    public boolean IsEnabled(){ return Enable; }

    public void SetIsWall(boolean _wall){ IsWall = _wall; }
    public boolean GetIsWall(){ return IsWall; }
    public void SetPosition(int x, int y){ xPos=x; yPos=y; }
    public void SetY(int n){yPos=n;}
    public void SetX(int n){xPos=n;} //...yes
    public int GetX(){ return xPos; }
    public int GetY(){ return yPos; }
    public void SetValue(int _value){ value=_value; }
    public int GetValue(){ return value; }
}
