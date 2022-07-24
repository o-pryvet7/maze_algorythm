package maze;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FindPathInConsole extends  AbstractFindPathInputReader{
    //Wave method->
    WavePoint[] wavePoints;
    private boolean foundTarget=false;

    //<-Wave method
    private int prev_mov=-1, prev_prev_mov=-1, current_mov=-1;
    //private String temp_imp_maze;
    private char[][] _maze;
    private int[][] _maze_index;// -1-target, 0-free way, 1-a free path that has already been used, 2-start, 3-wall,
    private int temp_height=50,temp_width=50, height=0, width=0;
    private int start_poz_x=0, start_poz_y=0, target_poz_x=0, target_poz_y=0;
    private int current_x=0, current_y=0;
    public void Run(){
        Input();
    }

    @Override
    public void FindPath() {
        //do nothing
    }

    @Override
    public void WavePointsInitial() {
        //do nothing
    }

    @Override
    public char[] RememberingWay() {
        //do nothing
        return new char[0];
    }

    @Override
    public void WaveMethod() {
        //do nothing
    }

    @Override
    public int FindSpecificPoint(int x, int y) {
        //do nothing
        return 0;
    }

    @Override
    public int TargetPointCheck() {
        //do nothing
        return 0;
    }

    @Override
    public void SizeAnalysis() {
        //do nothing
    }

    @Override
    public void TransferMazeCharToInt() {
        //do nothing
    }

    private void Input() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/temp.txt", false);
            Scanner scanner = new Scanner(System.in);
            String _line = scanner.nextLine();
            while (_line != "ITSALL"  &&  (_line.charAt(1)=='.' || _line.charAt(1)=='#' || _line.charAt(1)=='S' || _line.charAt(1)=='X')) {
                fileWriter.write(_line);
                //System.out.println(" - "+_line);
                _line = scanner.nextLine();
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
    }

}
