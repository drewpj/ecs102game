/* Dungeons & Dragons: The One Without Dungeons or Dragons
 Draw
 Drew Jacobson
 djacobso
 Section 4 */
public class Draw
{
//  private int xMax = 118;
//  private int ymax = 11;
  private char[][] board = new char[13][118];//the array containing the map (as chars)
  
  public Draw()//constructor. Allows the user to access the methods in the class.
  {

  }
  //First array in a string of array used to draw the map to the board array
  //building allows the draw methods to know what building they are printing (and access commands in the building array)
  public void drawMap(Building[]building)
  {
    for (int numBuilding = 0; numBuilding<9; numBuilding++)//drawing 9 buildings
      drawBuilding(building,numBuilding,board);
    drawIndicators(building,board);//draws loot indicators
    drawGround();//draws the ground
  }
  //Calls the method to update the loot indicators
  //building allows the draw methods to know what building they are printing (and access commands in the building array)
  public void updateIndicators(Building[] building)
  {
    drawIndicators(building, board); 
  }
  //draws the ground
  //no parameters are needed
//  public void drawRoof()
//  {
//    
//  }
  public void drawGround()
  {
    //System.out.println("\nAbout to draw the ground");
    for (int i = 0; i<118; i++)
      board[12][i] = '-';
  }
  
  //Actually draws the loot indicators on the map
  //building allows the draw methods to know what building they are printing (and access commands in the building array)
  //board is where the method draws to
  public void drawIndicators(Building[] building, char[][]board)
  {
    int numOfFloors;
    int numOfRooms;
    for (int numBuilding = 0; numBuilding <9; numBuilding++){//for all the buildings
      numOfFloors = building[numBuilding].getNumOfFloors();
      for(int numFloor = 0; numFloor < numOfFloors; numFloor++){//for all the floors
        numOfRooms =  building[numBuilding].floors[numFloor].getNumOfRooms();
        for(int numRoom = 0; numRoom < numOfRooms; numRoom++){//for all the rooms
          if (!(building[numBuilding].floors[numFloor].rooms[numRoom].getLootTaken())){//if the loot isn't taken
            switch (numFloor) {//set the draw '*' onto the map
              case 3: board[5][(numBuilding*13)+2+(numRoom*3)] = '*'; break;//if the room is on floor 4
              case 2: board[7][(numBuilding*13)+2+(numRoom*3)] = '*'; break;//floor3
              case 1: board[9][(numBuilding*13)+2+(numRoom*3)] = '*'; break;//floor2
              case 0: board[11][(numBuilding*13)+2+(numRoom*3)] = '*';break;//floor1
            }//end switch statement
          }//end if-statement
          else//if the loot is taken
          {
            switch (numFloor) {//set the draw ' ' onto the map
              case 3: board[5][(numBuilding*13)+2+(numRoom*3)] = ' '; break;//floor 4
              case 2: board[7][(numBuilding*13)+2+(numRoom*3)] = ' '; break;
              case 1: board[9][(numBuilding*13)+2+(numRoom*3)] = ' '; break;
              case 0: board[11][(numBuilding*13)+2+(numRoom*3)] = ' '; break;
            }//end switch statement
          }//end else statement
        }//end for-loop (room)
      }//end for-loop(floor)
    }//end for-loop (building)
  }//end method drawIndicators
  
  //Part of the string of methods which draws the buildings on the map
  //building and numBuilding allows the draw methods to know what building they are printing (and access commands in the building array)/
  //board is where the method draws to.
  public void drawBuilding(Building[]building,int numBuilding, char[][] board){//drawing a building
    //System.out.println("About to draw a building");
    int colStart = (numBuilding*13);//formula to find the start of the building, (col symbolizes column.(
    int numOfFloors = building[numBuilding].getNumOfFloors();
    int startLevel = 0;
    switch (numOfFloors){//determines the start level of the floors (rows)
      case 4: startLevel = 2; break; //if the building has 4 floors
      case 3: startLevel = 4; break; //3 floors
      case 2: startLevel = 6; break; //2 floors
    }
    for (int rows = startLevel; rows<10; rows+=2){//decrease the floor number
      //for(int cols = colStart; cols < cols+=13; cols++;
      //System.out.println("StartLEvel =" + startLevel);
      numOfFloors--;
      if (startLevel <10)
        startLevel+=2;
      drawFloor(building,colStart, startLevel,board,numBuilding, numOfFloors);//draw the floor
      
      
    }
  }//end method drawBuilding
  
  //part of a tring of methods which draws the buildings on the map (this one draws a specific floor.
  //building and numBuilding, numFloor allows the draw methods to know what building they are printing (and access commands in the building array)/
  //board is where the method draws to.
  //startLevel and colStart are pushed to the next method to tell it where to startr it's drawing.
  public void drawFloor(Building[] building,int colStart, int startLevel, char[][] board, int numBuilding, int numFloor)//drawing a floor
  {
    int numOfRoom = building[numBuilding].floors[numFloor].getNumOfRooms();
    if (numOfRoom == 2)//if statement, determines what room type the program should draw.
    {
      drawRoomFloor(colStart, startLevel, board, 2);  
    }
    else if(numOfRoom == 3)
    {
      drawRoomFloor(colStart, startLevel, board ,3);  
    }
    else
    {
      drawRoomFloor(colStart, startLevel, board, 4);
    }
    //for (startLevel = startLevel; startLevel < 10; startLevel+=2)
    
//board[startLevel][colstart] 
  }//end method drawFloor
  
  //Prints the roms of a floor to the map
  //colStart and startLevel tells the method where to start it's drawing
  //numOfRooms determines what kind of room the method should print
  //board is the array the method prints to
  public void drawRoomFloor(int colStart, int startLevel, char[][] board, int numOfRooms)
  {
    for (int i = colStart+1; i <= colStart+12;i++)
      board[startLevel][i] = '-';
    board[startLevel][colStart+12] = '#';
    board[startLevel][colStart] = '#';
    if (numOfRooms == 2){//if it's a floor with 2 rooms
      board[startLevel+1][colStart] = '#';
      board[startLevel+1][colStart+1] = ' ';
      board[startLevel+1][colStart+2] = ' ';
      board[startLevel+1][colStart+3] = '|';
      board[startLevel+1][colStart+4] = ' ';
      board[startLevel+1][colStart+5] = ' ';
      board[startLevel+1][colStart+6] = '|';
      board[startLevel+1][colStart+7] = '/';
      board[startLevel+1][colStart+8] = '/';
      board[startLevel+1][colStart+9] = '/';
      board[startLevel+1][colStart+10] = '/';
      board[startLevel+1][colStart+11] = '/';
      board[startLevel+1][colStart+12] = '#';
    }//end if-statement
    else if (numOfRooms == 3){//3 rooms
      board[startLevel+1][colStart] = '#';
      board[startLevel+1][colStart+1] = ' ';
      board[startLevel+1][colStart+2] = ' ';
      board[startLevel+1][colStart+3] = '|';
      board[startLevel+1][colStart+4] = ' ';
      board[startLevel+1][colStart+5] = ' ';
      board[startLevel+1][colStart+6] = '|';
      board[startLevel+1][colStart+7] = ' ';
      board[startLevel+1][colStart+8] = ' ';
      board[startLevel+1][colStart+9] = '|';
      board[startLevel+1][colStart+10] = '/';
      board[startLevel+1][colStart+11] = '/';
      board[startLevel+1][colStart+12] = '#'; 
    }//end else if-statement 
    else{//2 roomed floor 
      board[startLevel+1][colStart] = '#';
      board[startLevel+1][colStart+1] = ' ';
      board[startLevel+1][colStart+2] = ' ';
      board[startLevel+1][colStart+3] = '|';
      board[startLevel+1][colStart+4] = ' ';
      board[startLevel+1][colStart+5] = ' ';
      board[startLevel+1][colStart+6] = '|';
      board[startLevel+1][colStart+7] = ' ';
      board[startLevel+1][colStart+8] = ' ';
      board[startLevel+1][colStart+9] = '|';
      board[startLevel+1][colStart+10] = ' ';
      board[startLevel+1][colStart+11] = ' ';
      board[startLevel+1][colStart+12] = '#';
    }//end else statement 
  }//end method drawRoomFloor
  
  //puts blank spaces in all parts of the array to avoid nullPoint errors
  public void initializeMap()
  {
    for (int rows = 0; rows<11; rows++){//for the amount of rows and columns in the array
      for(int cols = 0; cols<117; cols++)
        board[rows][cols] = ' ';} 
  }
  
  //prints the map to the screen
  //no parameters are necessary
  public void print()
  {
    System.out.println();
    for (int rows = 3; rows<13; rows++){
      for(int cols = 0; cols<117; cols++)
        System.out.print(board[rows][cols]);
      System.out.println("");
    }
    System.out.println();
  }//end method print
  
}//end class Draw