package maze;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;


public class FindPathInFile extends AbstractFindPathInputReader {
    private String FileName;
    //private String temp_imp_maze;
    private char[][] _maze;
    private int[][] _maze_index;// -1-target, 0-free way, 1-a free path that has already been used, 2-start, 3-wall,
    private int height=0,width=0;
    private int start_poz_x=0, start_poz_y=0, target_poz_x=0, target_poz_y=0;

    public void Run(){
        SizeAnalysis();
        if(FileEquals("src/main/resources/previous_maze.txt" ,GetFileName())){
            System.out.println("MAZES IS EQUALS");
        } else System.out.println("MAZES ISN`T EQUALS");
        ReadFile();
    }

    public void InputMaze(){

    }

    public boolean FileEquals(String Writer, String Reader){
        boolean temp_return = true;
        try{
            FileReader fileReader = new FileReader(Reader);
            FileReader fileWriter = new FileReader(Writer);
            int chars1 = fileReader.read();
            int chars2 = fileWriter.read();
            System.out.println("Start Check File Equals");
            if(chars1!=-1 && chars2!=-1)
                while(chars1!=-1 || chars2!=-1){
                    if(chars1!=chars2){
                        temp_return = false;
                    }
                    chars1 = fileReader.read();
                    chars2 = fileWriter.read();
                }
            fileWriter.close();
            fileReader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return temp_return;
    }

    public void ReadFile(){
        int x=0,y=0;
        try{
            FileReader fileReader = new FileReader(FileName);
            FileWriter fileWriter_maze = new FileWriter(new File("src/main/resources/previous_maze.txt"));
            int chars = fileReader.read();
            System.out.println("Start Read");
            if(chars!=-1) while(chars != -1){
                fileWriter_maze.write((char)chars);
                //System.out.print("\nRead is ok, curret x is: "+x+", curret y is: "+y+",curret char symbol is :"+chars+", curren chars is :");
                _maze[x][y]=(char)chars;
                if((_maze[x][y]==13)||_maze[x][y]=='\n'){// CRLF
                    x=0; y++;
                    chars = fileReader.read();
                    fileWriter_maze.write((char)chars);
                    _maze[x][y]=(char)chars;
                }
                else if(_maze[x][y]=='S'){
                    start_poz_x=x;
                    start_poz_y=y;
                }
                else if(_maze[x][y]=='X') {
                    target_poz_x = x;
                    target_poz_y = y;
                }
                System.out.print(_maze[x][y]);
                x++;
                chars = fileReader.read();
            }
            else
                System.out.println("\nERROR");
            fileWriter_maze.close();
            fileReader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("\n\nEnd Read\nStart ["+start_poz_x+", "+start_poz_y+"],  target ["+target_poz_x+", "+target_poz_y+"]");

    }

    public void SizeAnalysis() {
        int x=0,y=0;
        try{
            FileReader fileReader = new FileReader(FileName);
            int chars = fileReader.read();
            System.out.println("Start Analysis");
            while(chars!=-1){
                x++;
                System.out.println("Analysis x++   , curret chars is : "+chars+" +in char : "+(char)chars);
                if((char)chars=='\n'){
                    System.out.println("Analysis y++");
                    width=x+1;
                    x=0;
                    y++;
                }
                chars = fileReader.read();
            }
            height=y+1;
            fileReader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        _maze = new char[width][height];
        System.out.println("End Analysis");

    }
    public void SetFileName(String Name){
        FileName=Name;
    };
    public String GetFileName(){
        return FileName;
    }
}
