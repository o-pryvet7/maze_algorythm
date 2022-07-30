package maze;

import java.io.FileReader;
import java.io.IOException;


/**
 * @author o_pryvet7
 * <p>a full-fledged algorithm for finding a path in a maze.</p>
 * <p>This class is designed for the maze, which is recorded in the file</p>
 */
public class FindPathInFile {
    private String FileName = "maze.txt";
    WavePoint[] wavePoints;
    private char[][] _maze;
    private int[][] _maze_index;// -1-target, 0-free way, 1-a free path that has already been used, 2-start, 3-wall,
    private int height = 0, width = 0;
    private int start_poz_x = 0, start_poz_y = 0, target_poz_x = 0, target_poz_y = 0;

    public void SetFileName(String Name) {
        FileName = Name;
    }


    /**
     * Solution progress.
     * <p>Analysis of the dimensions of this labyrinth. {@link FindPathInFile#SizeAnalysis()}  }</p>
     * <p>Reading a file and then writing the maze to an array.</p>
     * <p>Transferring the maze to a more convenient array (another one). {@link FindPathInFile#WaveMethod()}  }</p>
     * <p>Search for a solution</p>
     */
    public void Run() {
        SizeAnalysis();
        ReadFile();
        TransferMazeCharToInt();
        FindPath();
    }

    private void FindPath() {
        WaveMethod();
        char[] final_way;
        final_way = RememberingWay();
        for (char c : final_way) {
            System.out.print(c + ",");
        }
    }

    private void WavePointsInitial() {
        wavePoints = new WavePoint[width * height];
        for (int i = 0; i < height; i++)
            for (int k = 0; k < width; k++)
                wavePoints[(i * width + k)] = new WavePoint();

        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                wavePoints[(i * width + k)].SetX(k);
                wavePoints[(i * width + k)].SetY(i);
                wavePoints[(start_poz_y * width + start_poz_x)].SetValue(1);
                if (_maze_index[k][i] == 3) { // 3 - wall
                    wavePoints[(i * width + k)].SetIsWall(true);
                }
            }
        }
    }

    private char[] RememberingWay() {  //A feature that could have been made easier
        //ArrayList<Character> way = new ArrayList<>();
        int targetWayIndex = FindSpecificPoint(target_poz_x, target_poz_y);
        int min = wavePoints[targetWayIndex].GetValue(), temp_x = wavePoints[targetWayIndex].GetX(), temp_y = wavePoints[targetWayIndex].GetY();
        char[] way = new char[min + 2];
        do {
            if ((wavePoints[(temp_y * width) + temp_x + 1].GetValue() > 0) && (wavePoints[(temp_y * width) + temp_x + 1].GetValue() < min) && //right
                    (wavePoints[(temp_y * width) + temp_x + 1].GetX() == wavePoints[(temp_y * width) + temp_x].GetX() + 1) && (wavePoints[(temp_y * width) + temp_x + 1].GetY() == wavePoints[(temp_y * width) + temp_x].GetY()))
                min = wavePoints[(temp_y * width) + temp_x + 1].GetValue();
            if ((wavePoints[((temp_y + 1) * width) + temp_x].GetValue() > 0) && (wavePoints[((temp_y + 1) * width) + temp_x].GetValue() < min) && //down
                    (wavePoints[((temp_y + 1) * width) + temp_x].GetX() == wavePoints[(temp_y * width) + temp_x].GetX()) && (wavePoints[((temp_y + 1) * width) + temp_x].GetY() == wavePoints[(temp_y * width) + temp_x].GetY() + 1))
                min = wavePoints[((temp_y + 1) * width) + temp_x].GetValue();
            if ((wavePoints[(temp_y * width) + temp_x - 1].GetValue() > 0) && (wavePoints[(temp_y * width) + temp_x - 1].GetValue() < min) && //left
                    (wavePoints[(temp_y * width) + temp_x - 1].GetX() == wavePoints[(temp_y * width) + temp_x].GetX() - 1) && (wavePoints[(temp_y * width) + temp_x - 1].GetY() == wavePoints[(temp_y * width) + temp_x].GetY()))
                min = wavePoints[(temp_y * width) + temp_x - 1].GetValue();
            if ((wavePoints[((temp_y - 1) * width) + temp_x].GetValue() > 0) && (wavePoints[((temp_y - 1) * width) + temp_x].GetValue() < min) && //up
                    (wavePoints[((temp_y - 1) * width) + temp_x].GetX() == wavePoints[(temp_y * width) + temp_x].GetX()) && (wavePoints[((temp_y - 1) * width) + temp_x].GetY() == wavePoints[(temp_y * width) + temp_x].GetY() - 1))
                min = wavePoints[((temp_y - 1) * width) + temp_x].GetValue();

            if ((wavePoints[(temp_y * width) + temp_x + 1].GetValue() > 0) && (wavePoints[(temp_y * width) + temp_x + 1].GetValue() == min) && //right
                    (wavePoints[(temp_y * width) + temp_x + 1].GetX() == wavePoints[(temp_y * width) + temp_x].GetX() + 1) && (wavePoints[(temp_y * width) + temp_x + 1].GetY() == wavePoints[(temp_y * width) + temp_x].GetY())) {
                temp_x++;
                way[min - 1] = 'l';
            } else if ((wavePoints[((temp_y + 1) * width) + temp_x].GetValue() > 0) && (wavePoints[((temp_y + 1) * width) + temp_x].GetValue() == min) && //down
                    (wavePoints[((temp_y + 1) * width) + temp_x].GetX() == wavePoints[(temp_y * width) + temp_x].GetX()) && (wavePoints[((temp_y + 1) * width) + temp_x].GetY() == wavePoints[(temp_y * width) + temp_x].GetY() + 1)) {
                temp_y++;
                way[min - 1] = 'u';
            } else if ((wavePoints[(temp_y * width) + temp_x - 1].GetValue() > 0) && (wavePoints[(temp_y * width) + temp_x - 1].GetValue() == min) && //left
                    (wavePoints[(temp_y * width) + temp_x - 1].GetX() == wavePoints[(temp_y * width) + temp_x].GetX() - 1) && (wavePoints[(temp_y * width) + temp_x - 1].GetY() == wavePoints[(temp_y * width) + temp_x].GetY())) {
                temp_x--;
                way[min - 1] = 'r';
            } else if ((wavePoints[((temp_y - 1) * width) + temp_x].GetValue() > 0) && (wavePoints[((temp_y - 1) * width) + temp_x].GetValue() == min) && //up
                    (wavePoints[((temp_y - 1) * width) + temp_x].GetX() == wavePoints[(temp_y * width) + temp_x].GetX()) && (wavePoints[((temp_y - 1) * width) + temp_x].GetY() == wavePoints[(temp_y * width) + temp_x].GetY() - 1)) {
                temp_y--;
                way[min - 1] = 'd';
            }

        } while (wavePoints[(temp_y * width) + temp_x].GetValue() != wavePoints[(start_poz_y * width) + start_poz_x].GetValue());

        return way;
    }

    private void WaveMethodDisplay() {  //I used this function because I wanted to see the propagation of the wave step by step
        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                System.out.print(" " + wavePoints[(i * width + k)].GetValue());
            }
            System.out.println();
        }
        System.out.println();
    }


    /**
     * <p>This is part of the function that is responsible for finding the path in the maze.</p>
     * <p>This is a function that searches for a point of interest using the wave method.</p>
     * @see WavePoint
     */
    public void WaveMethod() {
        WavePointsInitial();
        int temp_mov_counter, Value = 0, temp_counter, target_index = TargetPointCheck();
        while (wavePoints[target_index].GetValue() == 0) {//until the wave touches the point of interest
            temp_mov_counter = 0;
            Value++;
            for (WavePoint wavePoint : wavePoints) {//every points
                if (wavePoint.GetValue() == Value) {
                    if (wavePoint.GetX() < width - 1) {
                        temp_counter = FindSpecificPoint(wavePoint.GetX() + 1, wavePoint.GetY());
                        if (wavePoints[temp_counter].GetValue() == 0 && wavePoints[temp_counter].IsEnabled() && !wavePoints[temp_counter].GetIsWall()) {
                            temp_mov_counter++;
                            wavePoints[temp_counter].SetValue(Value + 1);//right
                            wavePoints[temp_counter].Disabled();
                        }
                    }
                    if (wavePoint.GetX() > 0) {
                        temp_counter = FindSpecificPoint(wavePoint.GetX() - 1, wavePoint.GetY());
                        if (wavePoints[temp_counter].GetValue() == 0 && wavePoints[temp_counter].IsEnabled() && !wavePoints[temp_counter].GetIsWall()) {
                            temp_mov_counter++;
                            wavePoints[temp_counter].SetValue(Value + 1);//left
                            wavePoints[temp_counter].Disabled();
                        }
                    }
                    if (wavePoint.GetY() < height - 1) {
                        temp_counter = FindSpecificPoint(wavePoint.GetX(), wavePoint.GetY() + 1);
                        if (wavePoints[temp_counter].GetValue() == 0 && wavePoints[temp_counter].IsEnabled() && !wavePoints[temp_counter].GetIsWall()) {
                            temp_mov_counter++;
                            wavePoints[temp_counter].SetValue(Value + 1);//down
                            wavePoints[temp_counter].Disabled();
                        }
                    }
                    if (wavePoint.GetY() > 0) {
                        temp_counter = FindSpecificPoint(wavePoint.GetX(), wavePoint.GetY() - 1);
                        if ((wavePoints[temp_counter].GetValue() == 0) && (wavePoints[temp_counter].IsEnabled()) && (!wavePoints[temp_counter].GetIsWall())) {
                            temp_mov_counter++;
                            wavePoints[temp_counter].SetValue(Value + 1);//up
                            wavePoints[temp_counter].Disabled();
                        }
                    }
                }
            }
            if (temp_mov_counter == 0) { //It happens that the wave rests on the walls and does not move.
                System.out.println(" ERROR.  THERE IS NO SUITABLE PATH, OR MEMORY FAILURE ");
                System.exit(0);
            }
        }
        System.out.println(" --- FINAL --- ");
    }

    private int FindSpecificPoint(int x, int y) {
        for (int i = 0; i < wavePoints.length; i++) {// You can use math for this ( y * width + x ) , but I decided to make it more reliable
            if (wavePoints[i].GetX() == x && wavePoints[i].GetY() == y) return i;
        }
        return -1;
    }

    private int TargetPointCheck() {
        for (int i = 0; i < wavePoints.length; i++) {
            if (wavePoints[i].GetX() == target_poz_x && wavePoints[i].GetY() == target_poz_y) return i;
        }
        return -1;
    }

    private void TransferMazeCharToInt() {
        // This happened because a different method was used earlier.
        int temp = 2;
        width -= 2;
        System.out.println("Width is : " + width + ", Height is : " + height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (_maze[j][i] == 83) {
                    temp = 2;
                    start_poz_x = j;
                    start_poz_y = i;
                } else if (_maze[j][i] == 88) {
                    temp = -1;
                    target_poz_x = j;
                    target_poz_y = i;
                } else if (_maze[j][i] == 46) temp = 0;
                else if (_maze[j][i] == 35) temp = 3;

                _maze_index[j][i] = temp;
            }
        }
    }

    private void ReadFile() {
        int x = 0, y = 0;
        try {
            FileReader fileReader = new FileReader(FileName);
            int chars = 0;
            System.out.println("Start Read");
            while (chars != -1) {
                chars = fileReader.read();
                if (chars != 13 && chars != 10) {
                    _maze[x][y] = (char) chars;
                    x++;
                } else {
                    x = 1;
                    if (chars == 10) {
                        y++;
                    }
                }
                if (_maze[x][y] == 'S') {
                    start_poz_x = (x - 1);
                    start_poz_y = y;
                }
                if (_maze[x][y] == 'X') {
                    target_poz_x = (x - 1);
                    target_poz_y = y;
                }
                if (chars == 13 || chars == 10) x--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nEnd Read");

    }

    /**
     * <p>Determining the size of the given array.</p>
     * <p>DSize is saved in variables.</p>
     */
    public void SizeAnalysis() {
        int x = 0, y = 0;
        try {
            FileReader fileReader = new FileReader(FileName);
            int chars = fileReader.read();
            System.out.println("Start Analysis");
            while (chars != -1) {
                x++;
                if (((char) chars == '\n') || (chars == 13)) {
                    width = x;
                    x = 0;
                    y++;
                }
                chars = fileReader.read();
            }
            height = y + 1;
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        _maze = new char[width][height];
        _maze_index = new int[width][height];
        System.out.println("End Analysis");
    }

}
