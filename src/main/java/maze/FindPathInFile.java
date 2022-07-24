package maze;

//import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
//import java.net.MalformedURLException;


public class FindPathInFile extends AbstractFindPathInputReader {
    private String FileName = "maze.txt";
    //Wave method->
    WavePoint[] wavePoints;
    private boolean foundTarget=false;

    //<-Wave method
    private int prev_mov=-1, prev_prev_mov=-1, current_mov=-1;
    //private String temp_imp_maze;
    private char[][] _maze;
    private int[][] _maze_index;// -1-target, 0-free way, 1-a free path that has already been used, 2-start, 3-wall,
    private int height=0,width=0;
    private int start_poz_x=0, start_poz_y=0, target_poz_x=0, target_poz_y=0;
    private int current_x=0, current_y=0;

    public void Run(){  //run
        SizeAnalysis();
        ReadFile();
        TransferMazeCharToInt();
        FindPath();
    }

    public void FindPath(){ //Pathfinder function.
        SetCurrentPosition(start_poz_x,start_poz_y);//starting position
        WaveMethod();//running a method that looks for the target point
        char[] final_way;
        final_way = RememberingWay();//The function returns along the path that the previous function built and remembers it
        for(int i = 0; i<final_way.length ; i++){
            System.out.print((char)(final_way[i]) + ","); //output way
        }
        //FindPathBody();
        //System.out.println("\n\nALL IS DONE\n\n");
    }

    public void WavePointsInitial(){
        wavePoints = new WavePoint[width*height];  //Initial Wave Values
        for(int i = 0;i < height; i++)
            for(int k = 0;k < width; k++)
                wavePoints[ (i*width+k) ] = new WavePoint();

        for(int i = 0;i < height; i++){
            for(int k = 0;k < width; k++){
                //System.out.println(" -Counter temp : "+ (i*width+k));
                    wavePoints[ (i*width+k) ].SetX(k);
                    wavePoints[ (i*width+k) ].SetY(i);
                    wavePoints[ (start_poz_y*width+start_poz_x) ].SetValue(1);
                    //System.out.println(" start_poz_y*width+start_poz_x : "+(start_poz_y*width+start_poz_x)+"   start x "+ start_poz_x+"      start y"+ start_poz_y);
                    if(_maze_index[k][i]==3){ //check if it's a wall
                        wavePoints[ (i*width+k) ].SetIsWall(true);
                    }
            }
        }
    }

    public char[] RememberingWay(){  //A feature that could have been made easier
        //ArrayList<Character> way = new ArrayList<>();
        int targetWayIndex = FindSpecificPoint(target_poz_x,target_poz_y);
        int min=wavePoints[targetWayIndex].GetValue(), temp_x = wavePoints[targetWayIndex].GetX(), temp_y = wavePoints[targetWayIndex].GetY();
        char[] way = new char[min+2];
        do{
            if((wavePoints[ (temp_y*width)+temp_x+1 ].GetValue()>0) && (wavePoints[ (temp_y*width)+temp_x+1 ].GetValue()<min) && //right
                    (wavePoints[ (temp_y*width)+temp_x+1 ].GetX()==wavePoints[ (temp_y*width)+temp_x ].GetX()+1) &&
                    (wavePoints[ (temp_y*width)+temp_x+1 ].GetY()==wavePoints[ (temp_y*width)+temp_x ].GetY())  )
                min=wavePoints[ (temp_y*width)+temp_x+1 ].GetValue();
            if((wavePoints[ ((temp_y+1)*width)+temp_x ].GetValue()>0) && (wavePoints[ ((temp_y+1)*width)+temp_x ].GetValue()<min) && //down
                    (wavePoints[ ((temp_y+1)*width)+temp_x ].GetX()==wavePoints[ (temp_y*width)+temp_x ].GetX()) &&
                    (wavePoints[ ((temp_y+1)*width)+temp_x ].GetY()==wavePoints[ (temp_y*width)+temp_x ].GetY()+1)  )
                min=wavePoints[ ((temp_y+1)*width)+temp_x ].GetValue();
            if((wavePoints[ (temp_y*width)+temp_x-1 ].GetValue()>0) && (wavePoints[ (temp_y*width)+temp_x-1 ].GetValue()<min) && //left
                    (wavePoints[ (temp_y*width)+temp_x-1 ].GetX()==wavePoints[ (temp_y*width)+temp_x ].GetX()-1) &&
                    (wavePoints[ (temp_y*width)+temp_x-1 ].GetY()==wavePoints[ (temp_y*width)+temp_x ].GetY())  )
                min=wavePoints[ (temp_y*width)+temp_x-1 ].GetValue();
            if((wavePoints[ ((temp_y-1)*width)+temp_x ].GetValue()>0) && (wavePoints[ ((temp_y-1)*width)+temp_x ].GetValue()<min) && //up
                    (wavePoints[ ((temp_y-1)*width)+temp_x ].GetX()==wavePoints[ (temp_y*width)+temp_x ].GetX()) &&
                    (wavePoints[ ((temp_y-1)*width)+temp_x ].GetY()==wavePoints[ (temp_y*width)+temp_x ].GetY()-1)  )
                min=wavePoints[ ((temp_y-1)*width)+temp_x ].GetValue();

            if((wavePoints[ (temp_y*width)+temp_x+1 ].GetValue()>0) && (wavePoints[ (temp_y*width)+temp_x+1 ].GetValue()==min) && //right
                    (wavePoints[ (temp_y*width)+temp_x+1 ].GetX()==wavePoints[ (temp_y*width)+temp_x ].GetX()+1) &&
                    (wavePoints[ (temp_y*width)+temp_x+1 ].GetY()==wavePoints[ (temp_y*width)+temp_x ].GetY())  ){
                temp_x++;
                way[min-1] = 'l';
            }

            else if((wavePoints[ ((temp_y+1)*width)+temp_x ].GetValue()>0) && (wavePoints[ ((temp_y+1)*width)+temp_x ].GetValue()==min) && //down
                    (wavePoints[ ((temp_y+1)*width)+temp_x ].GetX()==wavePoints[ (temp_y*width)+temp_x ].GetX()) &&
                    (wavePoints[ ((temp_y+1)*width)+temp_x ].GetY()==wavePoints[ (temp_y*width)+temp_x ].GetY()+1)  ){
                temp_y++;
                way[min-1] = 'u';
            }

            else if((wavePoints[ (temp_y*width)+temp_x-1 ].GetValue()>0) && (wavePoints[ (temp_y*width)+temp_x-1 ].GetValue()==min) && //left
                    (wavePoints[ (temp_y*width)+temp_x-1 ].GetX()==wavePoints[ (temp_y*width)+temp_x ].GetX()-1) &&
                    (wavePoints[ (temp_y*width)+temp_x-1 ].GetY()==wavePoints[ (temp_y*width)+temp_x ].GetY())  ) {
                temp_x--;
                way[min-1] = 'r';
            }


            else if((wavePoints[ ((temp_y-1)*width)+temp_x ].GetValue()>0) && (wavePoints[ ((temp_y-1)*width)+temp_x ].GetValue()==min) && //up
                    (wavePoints[ ((temp_y-1)*width)+temp_x ].GetX()==wavePoints[ (temp_y*width)+temp_x ].GetX()) &&
                    (wavePoints[ ((temp_y-1)*width)+temp_x ].GetY()==wavePoints[ (temp_y*width)+temp_x ].GetY()-1)  ) {
                temp_y--;
                way[min-1] = 'd';
            }

        }while(wavePoints[ (temp_y*width)+temp_x ].GetValue() != wavePoints[ (start_poz_y*width)+start_poz_x ].GetValue());

        return way;
    }

    public void WaveMethodDisplay(){  //I used this function because I wanted to see the propagation of the wave step by step
        for(int i = 0;i < height; i++){
            for(int k = 0;k < width; k++){
                System.out.print(" "+wavePoints[ (i*width+k) ].GetValue());
            }
            System.out.println();
        }
        System.out.println();
    }


    public void WaveMethod(){
        WavePointsInitial();
        int temp_mov_counter=0;
        int Value = 0;
        int temp_counter=0;
        int target_index = TargetPointCheck();
        while(wavePoints[target_index].GetValue()==0){//until the wave touches the point of interest
            temp_mov_counter=0;
            Value++;
            for(int i = 0; i < wavePoints.length; i++){//every points
                if(wavePoints[i].GetValue() == Value){
                    if(wavePoints[i].GetX()<width-1){
                        temp_counter = FindSpecificPoint(wavePoints[i].GetX()+1, wavePoints[i].GetY());
                        if(wavePoints[temp_counter].GetValue() == 0 && wavePoints[temp_counter].IsEnabled() && !wavePoints[temp_counter].GetIsWall() ){
                            temp_mov_counter++;
                            wavePoints[temp_counter].SetValue(Value+1);//right
                            wavePoints[temp_counter].Disabled();
                        }
                    }
                    if(wavePoints[i].GetX()>0){
                        temp_counter = FindSpecificPoint(wavePoints[i].GetX()-1, wavePoints[i].GetY());
                        if(wavePoints[temp_counter].GetValue() == 0 && wavePoints[temp_counter].IsEnabled() && !wavePoints[temp_counter].GetIsWall() ){
                            temp_mov_counter++;
                            wavePoints[temp_counter].SetValue(Value+1);//left
                            wavePoints[temp_counter].Disabled();
                        }
                    }
                    if(wavePoints[i].GetY()<height-1){
                        temp_counter = FindSpecificPoint(wavePoints[i].GetX(), wavePoints[i].GetY()+1);
                        if(wavePoints[temp_counter].GetValue() == 0 && wavePoints[temp_counter].IsEnabled() && !wavePoints[temp_counter].GetIsWall() ){
                            temp_mov_counter++;
                            wavePoints[temp_counter].SetValue(Value+1);//down
                            wavePoints[temp_counter].Disabled();
                        }
                    }
                    if(wavePoints[i].GetY()>0){
                        temp_counter = FindSpecificPoint(wavePoints[i].GetX(), wavePoints[i].GetY()-1);
                        if((wavePoints[temp_counter].GetValue() == 0) && (wavePoints[temp_counter].IsEnabled()) && (!wavePoints[temp_counter].GetIsWall()) ){
                            temp_mov_counter++;
                            wavePoints[temp_counter].SetValue(Value+1);//up
                            wavePoints[temp_counter].Disabled();
                        }
                    }
                }
            }
            if( temp_mov_counter==0){ //It happens that the wave rests on the walls and does not move.
                System.out.println(" ERROR.  THERE IS NO SUITABLE PATH, OR MEMORY FAILURE ");
                System.exit(0);
            }
            //WaveMethodDisplay();
        }
        System.out.println(" --- FINAL --- ");
    }

    public int FindSpecificPoint(int x, int y){ //Finding a kidney in an array given only its coordinates.
        for(int i = 0;i<wavePoints.length;i++){// You can use math for this ( y * width + x ) , but I decided to make it more reliable
            if(wavePoints[i].GetX()==x && wavePoints[i].GetY()==y)
                return i;
        }
        return -1;
    }

    public int TargetPointCheck(){ //finding index of point of interest
        for(int i = 0;i<wavePoints.length;i++){
            if(wavePoints[i].GetX()==target_poz_x && wavePoints[i].GetY()==target_poz_y)
                return i;
        }
        return -1;
    }

    public boolean FindPathBody(){//...yes
        Random rand = new Random();
        int temp_way[] = new int[4];//index  : 0-right, 1-up, 2-left, 3-down   // value like _maze_index
        System.out.println(" - FINDPATHRECURSION Start - ");
        while(_maze_index[current_x][current_y]!=-1){
            current_mov =(rand.nextInt(3));
            System.out.println(" \nCURRENT POS : "+current_x+" X "+current_y);
            //System.out.println(" - FINDPATHRECURSION GO - ");
            if((current_x<width-1) && (_maze_index[current_x+1][current_y]==BestWay(current_x,current_y)) && (_maze_index[current_x+1][current_y]<3) && ((prev_mov!=2) || (current_mov==0))){
                temp_way[0] =_maze_index[current_x+1][current_y];
                if (temp_way[0]<3){
                    _maze_index[current_x][current_y]=1;
                    current_x++;
                    prev_prev_mov=prev_mov;
                    prev_mov=0;
                }
                //System.out.println(" CURRENT WIDTH IS : "+ width +", CURRENT HEIGHT IS : "+ height +", CURRENT WAY IS RIGHT, CURRENT RIGHT BLOCK IS : "+ _maze_index[current_x+1][current_y]);
            }
            else if((current_y>0) && (_maze_index[current_x][current_y-1]==BestWay(current_x,current_y)) && (_maze_index[current_x][current_y-1]<3) && ((prev_mov!=3) || (current_mov==1))){
                temp_way[1] =_maze_index[current_x][current_y-1];
                if (temp_way[1]<3){
                    _maze_index[current_x][current_y]=1;
                    current_y--;
                    prev_prev_mov=prev_mov;
                    prev_mov=1;
                }
                //System.out.println(" CURRENT WIDTH IS : "+ width +", CURRENT HEIGHT IS : "+ height +", CURRENT WAY IS UP, CURRENT UP BLOCK IS : "+ _maze_index[current_x][current_y-0]);
            }
            else if((current_x>1) && (_maze_index[current_x-1][current_y]==BestWay(current_x,current_y)) && (_maze_index[current_x-1][current_y]<3) && ((prev_mov!=0) || (current_mov==2))){
                temp_way[2] =_maze_index[current_x-1][current_y];
                if (temp_way[2]<3){
                    _maze_index[current_x][current_y]=1;
                    current_x--;
                    prev_prev_mov=prev_mov;
                    prev_mov=3;
                }
                //System.out.println(" CURRENT WIDTH IS : "+ width +", CURRENT HEIGHT IS : "+ height +", CURRENT WAY IS LEFT, CURRENT LEFT BLOCK IS : "+ _maze_index[current_x-1][current_y]);
            }
            else if((current_y<height-1) && (_maze_index[current_x][current_y+1]==BestWay(current_x,current_y)) && (_maze_index[current_x][current_y+1]<3) && ((prev_mov!=1) || (current_mov==3))){
                temp_way[3] =_maze_index[current_x][current_y+1];
                if (temp_way[3]<3){
                    _maze_index[current_x][current_y]=1;
                    current_y++;
                    prev_prev_mov=prev_mov;
                    prev_mov=3;
                }
                //System.out.println(" CURRENT WIDTH IS : "+ width +", CURRENT HEIGHT IS : "+ height +", CURRENT WAY IS DOWN, CURRENT DOWN BLOCK IS : "+ _maze_index[current_x][current_y+1]);
            }
            if(_maze_index[current_x][current_y]==-1)
                break;

        }
            return true;
            //return false;//the path continues
    }

    public int BestWay(int x, int y){//...yes
        int[] temp_list = new int[4]; int min=3;
        for(int i=0;i<4;i++)temp_list[i]=3;
        if (x+1<width){
            temp_list[0]=_maze_index[x+1][y];
            //System.out.print("Right way exist and its "+_maze_index[x+1][y]+" , ");
        }
        if (y>0){
            temp_list[1]=_maze_index[x][y-1];
            //System.out.print("Up way exist and its "+_maze_index[x][y-1]+" , ");
        }
        if (x>0){
            temp_list[2]=_maze_index[x-1][y];
            //System.out.print("left way exist and its "+_maze_index[x-1][y]+" , ");
        }
        if (y+1<height){
            temp_list[3]=_maze_index[x][y+1];
            //System.out.print("Down way exist and its "+_maze_index[x][y+1]+" , ");
        }
        System.out.println("");
        for(int i=0;i<4;i++){
            if(min>temp_list[i])
                min = temp_list[i];
        }
        System.out.println(" --- BEST WAY IS :"+ min +" --- ");
        return min;
    }//

    public void TransferMazeCharToInt(){//moving the maze into an array of numbers.
        // This happened because a different method was used earlier.
        int temp=2;
        width-=2;
        System.out.println("Width is : "+ width +", Height is : "+ height);
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(_maze[j][i]==83){
                    temp=2;
                    start_poz_x=j;
                    start_poz_x=i;
                }
                else if(_maze[j][i]==88){
                    temp=-1;
                    target_poz_x=j;
                    target_poz_y=i;
                }
                else if(_maze[j][i]==46)
                    temp=0;
                else if(_maze[j][i]==35)
                    temp=3;

                _maze_index[j][i]=temp;
                //System.out.print(" "+_maze_index[j][i]);
            }
            //FtargetSystem.out.println();
        }
    }

    public void ReadFile(){//normal file read
        int x=0,y=0;
        try{
            FileReader fileReader = new FileReader(FileName);
            int chars=0;
            System.out.println("Start Read");
            if(chars!=-1){
                while(chars != -1){
                    chars = fileReader.read();
                    if(chars != 13 && chars !=10){
                        //System.out.print((char)chars);
                        _maze[x][y] = (char)chars;//writing values to an array
                        x++;
                    }
                    else{
                        x=1;
                        if(chars == 10){
                            y++;
                            //System.out.println();
                        }
                    }
                    if(_maze[x][y] == 'S'){
                        start_poz_x= (int) (x-1);
                        start_poz_y=y;
                    }
                    if(_maze[x][y] == 'X'){
                        target_poz_x= (int) (x-1);
                        target_poz_y=y;
                    }
                    if(chars==13 || chars==10)x--;
                    //System.out.print("\nRead is ok, current x is: "+x+", current y is: "+y+",current char symbol is :"+chars+", current chars is :");
                }
            }
            else
                System.out.println("\nERROR");
            fileReader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("\nEnd Read");

    }

    public void SizeAnalysis() {//maze size check
        int x=0,y=0;
        try{
            FileReader fileReader = new FileReader(FileName);
            int chars = fileReader.read();
            System.out.println("Start Analysis");
            while(chars!=-1){
                x++;
                //System.out.println("Analysis x++   , current chars is : "+chars+" +in char : "+(char)chars);
                if(((char)chars=='\n') || (chars==10)){
                    //System.out.println("Analysis y++");
                    width=x;
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
        _maze_index = new int[width][height];
        System.out.println("End Analysis");
    }
    public void SetFileName(String Name){
        FileName=Name;
    }
    public String GetFileName(){
        return FileName;
    }

    public void SetCurrentPosition(int _x, int _y){
        current_x=_x;
        current_y=_y;
    }
    public void SetCurrent_x(int _x){
        current_x=_x;
    }
    public void SetCurrent_y(int _y){
        current_y=_y;
    }
    public int GetCurrent_x(){
        return current_x;
    }
    public int GetCurrent_y(){
        return current_y;
    }
}
