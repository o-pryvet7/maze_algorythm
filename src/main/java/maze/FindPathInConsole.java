package maze;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author o_pryvet7
 * <p>Reading and solving a maze that the user can enter manually through the console</p>
 */
public class FindPathInConsole {

    /**
     * character-by-character writing of the maze to a file and further transfer of this file for processing
     * @see FindPathInFile
     * @see FindPathInFile#Run()
     */
    public void Run() {
        FindPathInFile findPathInFile = new FindPathInFile();
        findPathInFile.SetFileName(Input());
        findPathInFile.Run();
    }

    private String Input() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/temp.txt", false);
            Scanner scanner = new Scanner(System.in);
            String _line = scanner.nextLine();
            while (_line != "ITSALL" && (_line.charAt(1) == '.' || _line.charAt(1) == '#' || _line.charAt(1) == 'S' || _line.charAt(1) == 'X')) {
                fileWriter.write(_line);
                _line = scanner.nextLine();//A rather bad method is used here, because I could not find a normal way to write by character
                fileWriter.write('\n');
            }
            System.out.println("Thanks");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ("src/main/resources/temp.txt");
    }

}
