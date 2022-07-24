package maze;

public abstract class AbstractFindPathInputReader {

    public abstract void Run();
    public abstract void FindPath();
    public abstract void WavePointsInitial();
    public abstract char[] RememberingWay();
    public abstract void WaveMethod();
    public abstract int FindSpecificPoint(int x, int y);
    public abstract int TargetPointCheck();
    public abstract void SizeAnalysis();
    public abstract void TransferMazeCharToInt();
}
