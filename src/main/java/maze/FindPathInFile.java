package maze;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;


public class FindPathInFile extends AbstractFindPathInputReader {
    private String FileName;
    //private String temp_imp_maze;
    private char[][] _maze;
    private int height=0,width=0;
    private int start_poz_x=0, start_poz_y=0, target_poz_x=0, target_poz_y=0;

    public void Run(){
        SizeAnalysis();
        ReadFile();
    }

    public void InputMaze(){

    }

    public void ReadFile(){
        int x=0,y=0;
        try{
            FileReader fileReader = new FileReader(FileName);
            int chars = fileReader.read();
            System.out.println("Start Read");
            while(chars != -1){
                //System.out.print("Read is ok, curren chars is :");
                _maze[x][y]=(char)chars;
                if(_maze[x][y]=='\n'){
                    x=0; y++;
                }
                else if(_maze[x][y]=='S'){
                    start_poz_x=x;
                    start_poz_y=y;
                }
                else if(_maze[x][y]=='X'){
                    target_poz_x=x;
                    target_poz_y=y;
                }
                x++;
                System.out.print((char)chars);
                chars = fileReader.read();
            }
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
