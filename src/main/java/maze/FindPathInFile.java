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
        ReadFile();
    }

    public void InputMaze(){

    }

    public void ReadFile(){
        int x=0,y=0;
        try{
            FileReader fileReader = new FileReader(FileName);
            int chars = fileReader.read();
            while(chars != -1){
                _maze[x][y]=(char)chars;
                x++;
                if((char)chars=='\n'){
                    x=0; y++;
                }
                System.out.print((char)chars);
                chars = fileReader.read();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void SizeAnalysis() {

    }
    public void SetFileName(String Name){
        FileName=Name;
    };
    public String GetFileName(){
        return FileName;
    }
}
