package maze;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author o_pryvet7
 * <p>simple maze generator based on pseudo-randomness</p>
 */
public class MazeGenerator {

    private static int width, height;
    private static char[][] maze;


    public static void Run(String File) {
        MazeGenerate(File);
    }

    private static void MazeInit() {
        width = 20 + (int) (Math.random() * 500);
        height = 20 + (int) (Math.random() * 500);
        maze = new char[width][height];
    }

    /**
     * Creates labyrinths that are more like fields with scattered pieces of walls.
     * @param File - the file that will contain the maze
     */
    public static void MazeGenerate(String File) {//Pretty simple generator of fields and mazes
        MazeInit();
        int temp, temp2;
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/temp.txt", false);
            for (int i = 0; i < height; i++) {
                for (int k = 0; k < width; k++) {
                    temp = (int) (Math.random() * 2);
                    switch (temp) {
                        case 0, 2 -> maze[k][i] = '.';
                        case 1 -> maze[k][i] = '#';
                    }
                }
            }
            temp = (int) (Math.random() * width);
            temp2 = (int) (Math.random() * height);
            maze[temp][temp2] = 'S';
            temp = (int) (Math.random() * width);
            temp2 = (int) (Math.random() * height);
            maze[temp][temp2] = 'X';
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        WriteToFile(File);
    }

    /**
     * character-by-character saving of the maze to a file. For example "t.txt"
     * @param File - the file that will contain the maze
     */
    public static void WriteToFile(String File) {
        try {
            FileWriter fileWriter = new FileWriter(File);
            for (int i = 0; i < height; i++) {
                for (int k = 0; k < width; k++) {
                    fileWriter.write(maze[k][i]);
                }
                fileWriter.write((char) 10);
                fileWriter.write((char) 13);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
