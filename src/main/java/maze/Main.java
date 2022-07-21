package maze;

import java.util.Scanner;

public class Main {
    public void main(){
        System.out.println("You can type the maze into the console or save it as a file. \n If you want to write it as a file, then press 1. If you want to write it to the console, then press another key");
        Scanner scan = new Scanner(System.in);
        if(scan.nextInt()==1){
            System.out.println("Please put the file with the maze in the folder \"src\\main\\java\\maze\" and press enter when done");
            //FindPathInputReaderFile
        }

    }
}
