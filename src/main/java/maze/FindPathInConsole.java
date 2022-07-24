package maze;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FindPathInConsole extends  AbstractFindPathInputReader{

    public void Run(){//...yes
        FindPathInFile findPathInFile = new FindPathInFile();
        findPathInFile.SetFileName( Input());
        findPathInFile.Run();
    }

    @Override
    public void FindPath() {/*do nothing*/}
    @Override
    public void WavePointsInitial() {/*do nothing*/}
    @Override
    public char[] RememberingWay() {/*do nothing*/return new char[0];}
    @Override
    public void WaveMethod() {/*do nothing*/}
    @Override
    public int FindSpecificPoint(int x, int y) {/*do nothing*/return 0;}
    @Override
    public int TargetPointCheck() {/*do nothing*/return 0;}
    @Override
    public void SizeAnalysis() {/*do nothing*/}
    @Override
    public void TransferMazeCharToInt() {/*do nothing*/}
    private String Input() {//data input
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/temp.txt", false);
            Scanner scanner = new Scanner(System.in);
            String _line = scanner.nextLine();
            while (_line != "ITSALL"  &&  (_line.charAt(1)=='.' || _line.charAt(1)=='#' || _line.charAt(1)=='S' || _line.charAt(1)=='X')) {
                fileWriter.write(_line);
                //System.out.println(" - "+_line);
                _line = scanner.nextLine();//A rather bad method is used here, because I could not find a normal way to write by character
                fileWriter.write('\n');
                //fileWriter.write((char)13);
            }
            System.out.println("Thanks");
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ("src/main/resources/temp.txt");
    }

}
