package maze;

import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String args[]){
        //I wanted to do something better. But it just happened
        System.out.println("You can enter the maze into the console or save it as a file. \nIf you want to write it as a file, then enter 1 to console." +
                " If you want to write it to the console, then enter another symbol.\n\nif you want to run an automatic generator of fields with walls, then press 0");
        Scanner scan = new Scanner(System.in);
        char sc = scan.nextLine().charAt(0);
        if(sc=='1'){//Finding a file in the root folder and running the algorithm
            System.out.println("Please put the file with the maze in the folder \"maze_algorithm\" and restart the program. \nIf you have already placed the file in the desired folder, enter 1");
            if(scan.nextInt()==1){
                FindPathInFile File_path = new FindPathInFile();
                File_path.SetFileName(FindFile());
                File_path.Run();
                //FindFile();
            }
        }
        else if(sc=='0'){//Starting the generator
            for(int i =0;i<50;i++){
                MazeGenerator.Run("t.txt");
                FindPathInFile findPathInFile = new FindPathInFile();
                findPathInFile.SetFileName("t.txt");
                findPathInFile.Run();
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex) {          //I wrote this here, because when I launched it for the first time, I didn’t even have time to notice if there was a way
                    Thread.currentThread().interrupt();
                }
                System.out.println(" \n--------------------------\n ");
            }
            File file = new File("t.txt");
            file.delete(); //deleting a file
        }
        else{//Input from the console (very inconvenient)
            System.out.println("Please, enter the maze in the form of symbols \n(\"#\" - wall, \".\" - free element, \"S\" - start, \"X\" - end). \nPress \"Enter\" after each line.\nWhen finished, type \"ITSALL\" on a new line and press \"Enter\" again.");
            System.out.println("The program uses dynamic size. Maximum : 500 X 500");
            FindPathInConsole Console_path = new FindPathInConsole();
            Console_path.Run();
        }
    }

    public static String FindFile()  {//A function to search for a file that is not required in the root folder. Most likely it is in it that there is a maze
        File dir = new File(".");
        for(String path : dir.list()) {
            //System.out.println(path + path.length());
            char[] temp_char = path.toCharArray();
            if((temp_char[0] != '.') && (!CAE(temp_char,"pom.xml")) && (!CAE(temp_char,"src")) && (!CAE(temp_char,"target")) && (DotCheck(temp_char))){
                System.out.println(path + path.length());
                return path;
            }
        }
        return null;
    }


    public static boolean DotCheck(char[] ch){//check for a dot in a file or folder name
        for(int i = 0 ; i < ch.length ; i++){
            if(ch[i]==46)
                return true;
        }
        return false;
    };
    public static boolean CAE(char[] ch1, char[] ch2){// Char Array Equals
        int len1=ch1.length,len2=ch2.length;
        int minLen =(len1<len2)?len1:len2;
            for(int i = 0 ; i < minLen ; i++){
                if(ch1[i]!=ch2[i])
                    return false;
            }
        return true;
    }

    public static boolean CAE(char[] ch1, String ch){// Char Array Equals
        char[] ch2=ch.toCharArray();
        int len1=ch1.length,len2=ch2.length;
        int minLen =(len1<len2)?len1:len2;
        for(int i = 0 ; i < minLen ; i++){
            if(ch1[i]!=ch2[i])
                return false;
        }
        return true;
    }
}
