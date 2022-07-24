package maze;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MazeGenerator {

    private static int width, height;
    private static char[][] maze;


    public static void Run(String File){
        MazeGenerate(File);
    }
    private static void MazeInit(){
        width = 20 + (int)(Math.random()*500);
        height = 20 + (int)(Math.random()*500);
        maze = new char[width][height];
    }

    public static void MazeGenerate(String File){
        MazeInit();
        int temp,temp2;
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/temp.txt", false);
            for(int i = 0;i < height; i++){
                for(int k=0;k<width;k++){
                    temp = 0 + (int)(Math.random()*1);
                    switch (temp){
                        case 0:
                            maze[k][i] = '.';
                            break;
                        case 1:
                            maze[k][i] = '#';
                            break;
                    }
                }
            }
            temp = 0 +(int) (Math.random()*width);
            temp2 = 0 +(int) (Math.random()*height);
            maze[temp][temp2] = 'S';
            temp = 0 +(int) (Math.random()*width);
            temp2 = 0 +(int) (Math.random()*height);
            maze[temp][temp2] = 'X';
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        WriteToFile(File);
    }

    public static void WriteToFile(String File){
        try{
            FileWriter fileWriter = new FileWriter(File);
            for(int i = 0;i < height;i++){
                for(int k = 0;k < width;k++){
                    fileWriter.write(maze[k][i]);
                }
                fileWriter.write((char)10);
                fileWriter.write((char)13);
            }
            fileWriter.flush();
            fileWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
