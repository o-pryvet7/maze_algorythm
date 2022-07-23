package maze;

public class WavePoint {

    public WavePoint(){
        Enable = true;
        value = 0;
    }

    private boolean IsWall;

    private boolean Enable;

    private int value;

    public void Enabled(){ Enable=true; }
    public void Disabled(){ Enable=false; }
    public boolean IsEnabled(){ return Enable; }

    public void SetIsWall(boolean _wall){ IsWall = _wall; }
    public boolean GetIsWall(){ return IsWall; }

    public void SetValue(int _value){ value=_value; }
    public int GetValue(){ return value; }
}
