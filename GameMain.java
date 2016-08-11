/* Dungeons & Dragons: The One Without Dungeons or Dragons
 GameMain
 Drew Jacobson
 djacobso
 Section 4 */

//The map is best viewed if the font is set to Consolas.

import java.util.Scanner;
import java.util.Random;
import java.io.*;
public class GameMain
{
  public static void main(String [] args) throws IOException//main. runs through the game at a macro level.
  {
    //creating values to be initialized during the game
    Building[] building;//creating building array
    int length;
    String[] scoreNames;
    int[] scoreScores;
    String[] scoreNamesOutput;
    int[] scoreScoresOutput;
    boolean playAgain;
    boolean playGame;
    Player player;//player class
    Draw draw;
    
    do{
      //initializing variables for the game
      building = new Building[9];//sets the array of buiildings to 9. (There are always 9 buildings.)            (AR)
      length = findScoreListLength();//sets length to the return value of the method findScoreListLength()
      scoreNames = new String[length];//initializing variables for the game
      scoreScores = new int[length];
      scoreNamesOutput = new String[length+1];
      scoreScoresOutput = new int[length+1];
      playAgain = false;
      playGame = true;
      player = new Player();
      
      initializeWorld(building);//create the building class which then creates the floor and room classes.
      draw = new Draw();//creates the draw class (needs to be done after initializeWorld()).
      draw.drawMap(building);
      
      importScores(scoreNames,scoreScores,length);//imports the past player list
      intro(scoreNames, player);//introduction to the game
      rollSkills(player);//rolls skills for the player
      do{//the game loop
        playGame = game(player,building,draw);//beginning of the game method                                (MYMETH)
      }while (playGame==true);
      
      if (player.getHealth() < 1)//if the game ended because the player was dead.
      {
        parallel_sort(scoreNames, scoreScores);  
      }
      else//if the player ended the game while alive
      {
        addPlayerToLeaderboard(scoreNames,scoreScores,scoreNamesOutput,scoreScoresOutput,length,player);
        parallel_sort(scoreNamesOutput, scoreScoresOutput); 
      }
      playAgain = endGame(scoreNames,scoreScores,scoreNamesOutput,scoreScoresOutput,length,player);//runs the code for 
      //the end of the game
    }while (playAgain == true);//while the player wants to play again
  }//end method main
  
  //Initializes the array of class Building, which then creates the arrays for floors and rooms
  //The created array is needed to initialize (building)
  public static void initializeWorld(Building[] building)//world contructor
  {
    for (int i = 0; i<9; i++){
      building[i] = new Building(i);
    }//creating a building
  }//end method initializeWorld
  
  //Runs the code which guides the user through the intro of the game
  //needs the names array for search, and needs the player array to set data to.
  public static void intro(String[] names, Player player)
  {
    
    Scanner scan = new Scanner(System.in);//creating scanner
    System.out.println("Hello. Welcome to Dungeons & Dragons: The One Without Dungeons or Dragons.\nPlease enter your name: ");
    String name = scan.nextLine();//reading the user's name
    player.setName(name);
    
    int searchReturn = search(names, name);//uses the search method to look for a user's name
    if (searchReturn == -1)
      System.out.println("Welcome "+name+"!");//if the player has not played before
    else
      System.out.println("Welcome back "+name+"!");//if the player has played before
    
    int userAns;//for the user's anser to the next question (initializing it).
    do{
      System.out.println("What would you like to do?\n1. Play the game\n2. Read the rules");
      userAns = scan.nextInt();//player decides whether they want to read the rules or play the game
    }while (userAns < 1 || userAns >2);
    if (userAns == 1)
      return;//quit if the user wants to play the game
    else
      rules();
  }//end method intro
  
  //Rolls the skills for the player
  //the player class is needed to know who to roll players for
  public static void rollSkills(Player player)
  {
    Scanner scan = new Scanner(System.in);//scanner
    Boolean reroll = true; //keeps track of it the user wants to reroll 
    int rerollAmt = 0;//int keeps track of how many rerolls
    String userAns;
    System.out.println("\nYou are now rolling for skill values.");
    do{
      
      player.rollSkillValues();//rolls the skill values
      System.out.printf("%-6s      %-2d\n","Attack",player.getAttack()); //returns the value of the player's roll
      System.out.printf("%-6s      %-2d\n","Sneak",player.getSneak());
      System.out.printf("%-6s      %-2d\n","Speed",player.getSpeed());
      System.out.println("You have "+(2-rerollAmt)+" roll(s) left.");//tells the player how many rolls they have left
      if (rerollAmt < 2){
        do{
          System.out.println("Would you like to reroll? (y/n)");//asks whether they want to reroll
          userAns = scan.nextLine();
        }while (!(userAns.equalsIgnoreCase("y") || userAns.equalsIgnoreCase("yes") || userAns.equalsIgnoreCase("n") || 
                  userAns.equalsIgnoreCase("no"))); 
        if (userAns.equalsIgnoreCase("y") || userAns.equalsIgnoreCase("yes")){//if yes, then reroll is true
          reroll = true;     
          rerollAmt++; 
        }
        else 
          reroll = false;//if it's not yes, then it's no (false) (Boolean)
      }
      else {
        rerollAmt++;
        System.out.println("Do you accept your skill values?");//asks whether they want to reroll
        userAns = scan.nextLine(); 
      }
    }while((reroll == true) && rerollAmt < 3);//end do-while loop
  }//end method rollSkills
  
  //allows the user to choose to view the rules list
  //no parameters are needed
  public static void rules(){
    Scanner scan = new Scanner(System.in);//creating scanner 
    int userChoice;
    Boolean quitRules = false;//creates the boolean to decide whether to stay in the do-while loop
    do{
      do{
        System.out.println("\nRule Menu:\n1. General\n2. Movement\n3. Combat\n4. Skills\n5. Map key\n6. Quit (Play the game)");
        userChoice = scan.nextInt();//the menu
      }while (userChoice<0 || userChoice >=7);
      if (userChoice == 1){//if the user chooses option 1
        System.out.println("\nThe basic objective of the game is to explore the haunted houses and\nget the gold that is within"
                             +" each chest. As you explore\nyou will encounter ghouls and other undead enemies who will do" 
                             +" their \nbest to kill you. However, using your skills effectively you\nwill be able to combat" 
                             +" or avoid each encounter. With each chest\nyou open you gain one gold.");
      }
      else if (userChoice == 2){//option 2
        System.out.println("\nThe possible moves you can make will be displayed when needed.\nMost menus can be answered" 
                             +" using a number guide, although\nsome the player will need to enter either “yes”, “y”, “no”," 
                             +"or “n”.");
      }
      
      else if (userChoice == 3){//option 3
        System.out.println("\nCombat consists of two main moves which correspond with your\nattack skill value. The quick"
                             +" attack will have a higher chance\nof hitting the enemy first, however is weaker than the"
                             +" big\nattack, which comparatively is slower but stronger. The strength\nof both attacks are"
                             +"calculated using your skill value.\n\nThe combatant that reaches an HP of 0 first, loses.\n"
                             +"As the user you have an HP of 50 to begin with."
                             +"\nYou gain 10 HP after each successful fight.");
      }
      
      else if (userChoice == 4){//option 4
        System.out.println("\nBefore the game begins the player will roll random numbers for\nthree classes: attack, sneak,"+
                           "and speed.\n\nAttack: Has an effect on the player’s possible attack points.\n"
                             +"\nSneak: Has an effect on the player’s sneaking ability during\n"
                             +"close quarter situations with enemies and allows the player a\nbetter chance of sneaking around "
                             +"ghouls to get the loot they\nguard successfully.\n\nSpeed: Has an effect on how fast"
                             +" the player can run. If the player’s\nability"
                             +" is high, they will be able to run away from combat.\n");
      }
      else if (userChoice == 5){//option 5
        System.out.println("\nWhen viewing the map the rooms with '*' in them symbolize the existence of loot."+
                           " The absence of the '*' symbolizes the lack of loot.");
      }
      else
        quitRules = true;//if it's not 1-5, then it must be 6(and quit)
    }while (quitRules == false);
  }//end method rules
  
  //is the first method in the game string of ccalled methods. Provides some narrative and calls the chooseHouse method
  //The parameters player, building, and draw are all needed later in the game, so they have to pass through this method
  public static Boolean game(Player player,Building[] building,Draw draw)
  {
    boolean dead = false;//creating and initializing the variable dead
    System.out.println("You get out of your car. In the distance you see a row of houses.\nIf you explore them"
                         +" successfully you will fill your pockets with\nmany points. However, if you die, unlike"
                         +" the undead you will find\nin the houses, there is no coming back.\n\nYou walk towards the"
                         +" houses...\n");//narrative stuff
    chooseHouse(player, dead,building,draw);//beginning of game methods
    return false;//if the user comes back to this array it means that they are done. So this method will always return false
  }//end method game
  
  //Allows the user to choose what house they go into.
  //player, building, and draw are used at later times. dead is used to determine if the player has died.
  public static void chooseHouse(Player player,boolean dead,Building[] building,Draw draw)//choose house
  {
    Scanner scan = new Scanner(System.in);
    int userChoice;
    Boolean quit = false;
    do{
      do{
        System.out.println("\nYou're standing in front of a row of 9 spooky buildings. What would you like to do?");
        for (int x = 1; x<10; x++)
          System.out.println(x+".  Go to house "+x);//reading the user their options (loop b/c it's repeditive
        System.out.println("10. Check your health and skill values\n11. Print map of loot\n12. Quit the game");//finishig reading the user their options
        userChoice = scan.nextInt();//scanning the user's answer
      }while(!(userChoice > 0 && userChoice <13));//while the answer is not garbage
      switch(userChoice){
        case 1: chooseFloor(player,dead,building,0);dead = player.checkPlayerDeath();break;//go to the first house
        case 2: chooseFloor(player,dead,building,1);dead = player.checkPlayerDeath();break;//calls the chooseFloor method
        case 3: chooseFloor(player,dead,building,2);dead = player.checkPlayerDeath();break;
        case 4: chooseFloor(player,dead,building,3);dead = player.checkPlayerDeath();break;
        case 5: chooseFloor(player,dead,building,4);dead = player.checkPlayerDeath();break;
        case 6: chooseFloor(player,dead,building,5);dead = player.checkPlayerDeath();break;
        case 7: chooseFloor(player,dead,building,6);dead = player.checkPlayerDeath();break;
        case 8: chooseFloor(player,dead,building,7);dead = player.checkPlayerDeath();break;
        case 9: chooseFloor(player,dead,building,8);dead = player.checkPlayerDeath();break;             // (MYMETH(O))
        case 10: printPlayerInfo(player);break;
        case 11: printMap(draw,building); break;
        case 12: quit = true; break;//returns to the game method which ends the game
      }//end switch statement
    }while(quit == false && dead == false); //end do-while statement
    
  }//end method chooseHouse
  
  //allows the user to choose their floor
  //the player commander is used at a later time. building and buildingNum are used to determine where the player is.
  //dead allows the program to tell if the player is dead
  public static void chooseFloor(Player player,boolean dead,Building[] building,int buildingNum)
  {
    Scanner scan = new Scanner(System.in);
    int userChoice;
    Boolean quit = false;
    do{
      do{
        System.out.println("\nWhere would you like to explore?");
        System.out.println("1. Leave the building\n2. Basement\n3. First Floor");//giving the user their options (they always have these.)
        
        if (building[buildingNum].getNumOfFloors()==3)//only reads this option when it is possible
          System.out.println("4. Second Floor");
        if (building[buildingNum].getNumOfFloors()==4){//only reads this option when there are 4 floors
          System.out.println("4. Second Floor");
          System.out.println("5. Third Floor");}
        userChoice = scan.nextInt();
        
      }while (!(userChoice > 0 && userChoice <= building[buildingNum].getNumOfFloors()+1));//verifying the validity of
      //the user's answer
      switch (userChoice){
        case 1: quit = true;break;//quit the menu/go back
        case 2: chooseRoom(player, dead, building, buildingNum, 0);dead = player.checkPlayerDeath();break;//if chosen, go to room 1 (0 index)
        case 3: chooseRoom(player, dead, building, buildingNum, 1);dead = player.checkPlayerDeath();break;
        case 4: chooseRoom(player, dead, building, buildingNum, 2);dead = player.checkPlayerDeath();break;
        case 5: chooseRoom(player, dead, building, buildingNum, 3);dead = player.checkPlayerDeath();break;
      }//end switch
    }while(quit == false && dead == false);//end do-while loop
  }//end choose floor
  
  //allows the user to choose their room
  //the parameter player is used at a later time. building, buildingNum, floorNum are used to determine where the player is.
  //dead allows the program to tell if the player is dead
  public static void chooseRoom(Player player, boolean dead, Building[] building,int buildingNum, int floorNum)
  {
    Scanner scan = new Scanner(System.in);//initializing necessary variables
    int userChoice;
    boolean quit = false;
    dead = false;
    
    do{
      do{
        
        System.out.println("\nThere are "+building[buildingNum].floors[floorNum].getNumOfRooms()+" roomson this floor."
                             +" Which would you like to go into?");
        System.out.println("1. Go back\n2. Room 1\n3. Room 2");//printing instructions to the player
        
        if (building[buildingNum].floors[floorNum].getNumOfRooms()==3)//different instructions are printed depending
          System.out.println("4. Room 3");                            //on the size of the floor
        if (building[buildingNum].floors[floorNum].getNumOfRooms()==4){
          System.out.println("4. Room 3");
          System.out.println("5. Room 4");}//end printing choices
        userChoice = scan.nextInt();//takes the user's input
      }while (!(userChoice > 0 && userChoice <= building[buildingNum].floors[floorNum].getNumOfRooms()+1));//verifys the validity of the user's answer
      switch (userChoice){//switch to determine what the user meant
        case 1: quit = true; break;//quits
        case 2: openDoor(player, dead, building, buildingNum, floorNum,0);dead = player.checkPlayerDeath();break;//records the user's answer
        case 3: openDoor(player, dead, building, buildingNum, floorNum,1);dead = player.checkPlayerDeath();break;//goes to room 2
        case 4: openDoor(player, dead, building, buildingNum, floorNum,2);dead = player.checkPlayerDeath();break;
        case 5: openDoor(player, dead, building, buildingNum, floorNum,3);dead = player.checkPlayerDeath();break;
      }//end switch
    }while(quit == false && dead == false);//while the user isn't dead and hasn't chosen to quit
    return;
  }//end method choose room
  
  //allows the user to choose how they open the door
  //the parameter player is used to check if the player is dead, (and for further methods.)
  //Building, buildingNum, floorNum, roomNum are used to determine where the player is.
  //dead allows the program to tell if the player is dead.
  public static void openDoor(Player player, boolean dead,Building[] building,int buildingNum, int floorNum, int roomNum)
  {
    Scanner scan = new Scanner(System.in);//creating and initializing necessary variables
    int userChoice;
    Boolean quit = false;
    
    if (player.getHealth() <1)//if the player is dead, set the variable "dead" to true;
      dead = true;
    do{
      do{
        System.out.println("\nHow would you like to enter the room?\n1. Loud and violent\n2. Quietly\n3. Back away from"
                             +" the door");//asking for the user's choice
        userChoice = scan.nextInt();
      }while (!(userChoice <1 || userChoice <=3));//while the anseer is within a reasonable value
      switch (userChoice) {
        case 1: //loud and violent
          if (building[buildingNum].floors[floorNum].rooms[roomNum].getLootTaken()){//if the loot has already been taken
          System.out.println("You take a running start at the door, almost tumbling as you stop yourself before you enter. "+
                             " You ask yourself, why would you do that? I've already taken the loot.");}
          else{
            System.out.println("You break down the door with your foot and storm in.");
            battle(building,buildingNum,floorNum,roomNum,building[buildingNum].floors[floorNum].rooms[roomNum].getMonster(), player);
            dead = player.checkPlayerDeath();}//if it hasn't, do battle.
          break;
        case 2://quietly
          if (building[buildingNum].floors[floorNum].rooms[roomNum].getLootTaken()){//if the loot has already been taken
          System.out.println("You slowly begin to open the door but before you can you ask yourself, why would I do that?");}
          else{
            System.out.println("You carefully open the door and slip in.");
            enterRoomQuietly(building,buildingNum,floorNum,roomNum,//if it hasn't, go in.
                             building[buildingNum].floors[floorNum].rooms[roomNum].getMonster(), player);//runs the method to go in slowly
            dead = player.checkPlayerDeath();}
          break;
        case 3: quit = true;//quit                                                                               (BOOL)
        break;
        
      }//end switch 
    }while(quit == false && dead == false);//end do-while loop
    return;
  }//end method open door
  
  //the parameters player and monster is used for the fight class, (and for further methods.)
  //Building, buildingNum, floorNum, roomNum are used to determine where the player is.
  public static void enterRoomQuietly(Building[] building,int buildingNum, int floorNum, int roomNum, Monster monster, Player player)
  {
    Fight fight = new Fight(monster, player);//creating the fight class
    if (building[buildingNum].floors[floorNum].rooms[roomNum].getIsMon()){//while there is a monster
      if (fight.sneakBy()){//if the user was able to sneak by
        openChest(building, buildingNum,floorNum,roomNum,player);//open the chest
        building[buildingNum].floors[floorNum].rooms[roomNum].setLootTakenFalse();}//set the loot taken to false
      else{
        battle(building, buildingNum,floorNum,roomNum,monster,player);//if the player was detected: battle. 
      }//end else
    }//end if
    else//if there isn't a monster, (very unlikely.)
    {
      openChest(building, buildingNum,floorNum,roomNum,player);
      building[buildingNum].floors[floorNum].rooms[roomNum].setLootTakenFalse();}
  }//end method enterRoomQuietly
  
  //finds the length of the file "scoreList.txt"
  //no parameters are needed
  
  
  //allows the user to battle a monster
  //the parameters player and monster is used for the fight method
  //building, buildingNum, floorNum, roomNum are used to determine where the player is.
  public static void battle(Building[] building, int buildingNum, int floorNum, int roomNum,Monster monster, Player player)
  {
    System.out.println("\n---Combat Initialized---");//for style
    Scanner scan = new Scanner(System.in);//creating necessary variables
    int userChoice;
    boolean runAway = false;
    Fight fight = new Fight(monster, player);//creating the fight class.
    int playerAttackVal;
    boolean dead = false;
    boolean quickAttackPlayer = false;
    
    do{//only executes if the monster or player isn't dead, and if the player isn't run away.
      do{
        quickAttackPlayer = false;
        playerAttackVal = 0;
        System.out.println("\nWhat would you like to do?\n1. Quick attack\n2. Big attack\n3. Run away");
        userChoice = scan.nextInt();
      }while(!(userChoice > 0 && userChoice <4));//asking and interpreting what the user wants to do.
      switch (userChoice){
        case 1: playerAttackVal = fight.quickAttackPlayer();break;//gets the value of the user's attack
        case 2: playerAttackVal = fight.bigAttackPlayer(); quickAttackPlayer = true;break;//gets the value of the user's attack
        case 3: runAway = fight.runAway();break;//tests whether the player was able to run away
      }//end switch statement
      if (runAway == false){//if the user doesn't want to run away, attack code executes
        int enemyAttack = fight.attackEnemy();//the attack value of the enemy is equal to the method called in "fight".
        
        if (fight.checkSpeed(quickAttackPlayer)){//if the player ia faster than the enemy
          monster.decHealth(playerAttackVal);//reduces the health of the monster
          System.out.println("You attack first.");
          if (monster.getHealth()>0)//only if the monster isn't dead
            player.decHealth(enemyAttack);}//reduces the player's health by the attack value of the enemy
        else{//if the player is slowe than the enemy
          player.decHealth(enemyAttack);
          System.out.println("The Ghoul attacks first.");
          if (player.getHealth()>0)//only if the player isn't dead
            monster.decHealth(playerAttackVal);
        }
        
        
        System.out.println("You have hit the ghoul for "+playerAttackVal+" health points. While it has hit you for "
                             + enemyAttack + " health points.\n");//telling the player information
        System.out.println("Your current HP is: "+((1 > player.getHealth()) ?0 : player.getHealth()) +
                           "\nGhoul's current Hp is: "+((1 > monster.getHealth()) ?0 : monster.getHealth()));
        
      }//end while loop
      if (player.getHealth() < 1){//if the player is dead, set dead to true, and notify the player
        dead = true;
        System.out.println("\nYou have died.");
      }
      else if(monster.getHealth() < 1){//if the monster is dead
        System.out.println("You have defeated the ghoul.");
        player.incHealth(10);
        building[buildingNum].floors[floorNum].rooms[roomNum].setIsMonFalse();//tell the room class that the monster is gone
        building[buildingNum].floors[floorNum].rooms[roomNum].setLootTakenFalse();//set it so that the loot is gone
        openChest(building, buildingNum,floorNum,roomNum,player);//open the chest 
      }//end else if-statement
    }while(dead == false && runAway == false && building[buildingNum].floors[floorNum].rooms[roomNum].getIsMon() == true);//room isMon == false 
    System.out.println("\n---Combat Completed---\n"); 
  }//end method battle
  
  //runs the code that decides what is in each chest and allows the player to open it
  //building, buildingNum, floorNum, roomNum are used to determine where the player is.
  //player is used to give the randomized values to the player
  public static void openChest(Building[] building, int buildingNum, int floorNum, int roomNum, Player player)
  {
    Random gen = new Random();
    System.out.println("You move over to the chest and open it.");
    player.incScore();//increases the player's score
    System.out.print("You open a chest and find a potion of increased ");
    int decideTypePotion = gen.nextInt(4);//random value decides what the drop is
    int amountIncreased = gen.nextInt(8)+3;//random value which decides how much the player will get
    switch (decideTypePotion){
      case 0: //if decideTypePotion was 0
        System.out.print("strength. Your attack");//the middle of the sentence  
        break;
      case 1: 
        System.out.print("quietness. Your sneak");  
        break;
      case 2: 
        System.out.print("swiftness. Your speed");  
        break;
      case 3: 
        System.out.print("life. Your health"); 
        break;
      default: break;
    }
    if (decideTypePotion < 3){//if the drop wasn't a skill increase (rather it is a health increase.)                     
      System.out.println(" has been increased by "+amountIncreased+" points.");                    
      player.incSkill(amountIncreased,decideTypePotion);}
    else{
      System.out.println(" has been increased by "+(amountIncreased+18)+" points.");
      player.incHealth(amountIncreased+13);}
  }//end method openChest
  
  public static int findScoreListLength() throws IOException
  {
    Scanner scanScoresRaw = new Scanner(new File ("scoreList.txt"));//opens the files and creates the scanner     (I/O)
    int countLines = 0;
    while (scanScoresRaw.hasNext())//checking to see how many lines exist
    {
      countLines++;//adds to the count lines int
      scanScoresRaw.nextLine();//goes to a new line
    }
    scanScoresRaw.close();//closes the file
    return countLines;//returns the correct number
  }//end method findScoreListLength
  
  //imports the scores/names for the file "scoreList.txt"
  //uses scoreNames and scoreScores as arrays to import into. countLines is used for keeping the for loop in bounds.
  public static void importScores(String[] scoreNames,int[] scoreScores,int countLines) throws IOException
  { 
    Scanner scanScores = new Scanner(new File ("scoreList.txt"));//opens the files and creates a scanner
    for (int i = 0; i<countLines; i++)//loops while there are lines to read
    {
      scoreNames[i]=scanScores.next();//read in the name
      scoreScores[i]=scanScores.nextInt();//read in their scores
      if (scanScores.hasNext()){//if there is another line, move to it
        scanScores.nextLine();
      }
      
    }
    
  }//end method importScores
  
  //sorts names and scores for the leader boards.
  //needs names and scores so that it may sort them.
  public static void parallel_sort (String[] names, int[] scores)// parallel sort                              (SORT)
  {
    int min;   //index of smallest element of sublist
    int diff = 0;
    int tempInt;
    String tempName;
    
    for (int startIndex=0; startIndex < scores.length; startIndex++)//check for the length of the array
    {
      min=startIndex;//the smallest should be in the beginning and left there.
      
      for (int i=startIndex+1; i<scores.length; i++){
        if (scores[i]>scores[min])
          min=i;}//if the check value is larger than the min value, set the min to the check value
      
      //swap the values
      tempName=names[min];
      names[min]=names[startIndex];
      names[startIndex]=tempName;
      
      tempInt=scores[min];
      scores[min]=scores[startIndex];
      scores[startIndex]=tempInt;     
    }//end for startIndex  
  }//end method parallel+sort
  
  //searches the array of names for the user's entered name.
  //uses names as the array to search in, and target as the target for the search, (the user's name.)
  public static int search(String[] names, String target){//search method                                   (SEARCH)
    for (int start = 0; start < names.length; start++){
      if (names[start].equalsIgnoreCase(target))//if the array value is equal to the target value
        return start;//return the value
    }
    return -1;//if the loop never finds a match it returns -1
  }//end method search
  
  //Adds the player to the leaderboard
  //Uses the original arrays for past players' names and scores (scoreNames/ScoreScores), then transfers into new arrays
  //(scoreNamesOutput/scoreScoresOutput). It uses player to get the player's name and length for the for loop.
  public static void addPlayerToLeaderboard(String[] scoreNames,int[] scoreScores,String[] scoreNamesOutput,
                                            int[] scoreScoresOutput,int length, Player player) //throws IOException
  {
    for(int i = 0; i<length; i++)//for the length of the old array
    {
      scoreNamesOutput[i] = scoreNames[i];//set the value from the index in the old array to the same index in the new array
      scoreScoresOutput[i] = scoreScores[i];
    }
    scoreNamesOutput[length] = player.getName();//sets the last index slot in the new array to the player's info
    scoreScoresOutput[length] = player.getScore();
    
  }//end method addPlayerToLeaderboard
  
  //prints the player's current statictics
  //uses player to get the player's states, (the methods and instance data is in that class.)
  public static void printPlayerInfo(Player player)
  {
    Scanner scan = new Scanner(System.in); 
    System.out.println("\nPlayer information:");//for style
    System.out.printf("%-7s   %-3d\n","Health:",player.getHealth());
    System.out.printf("%-7s   %-3d\n","Attack:",player.getAttack());
    System.out.printf("%-7s   %-3d\n","Sneak:",player.getSneak());
    System.out.printf("%-7s   %-3d\n","Speed:",player.getSpeed());
    System.out.println("\nEnter any symbol to continue:");
    scan.nextLine();
  }//end method printPlayerInfo
  
  //runs the end game display code, (calls leaderboard and play again.)
  //scoreNames,scoreScores,scoreNamesOutput,scoreScoresOutput,length,player are used to call leaderboard and sort methods.
  public static boolean endGame(String[] scoreNames,int[] scoreScores,String[] scoreNamesOutput, int[] scoreScoresOutput,
                                int length, Player player)throws IOException
  {
    System.out.println("\nThe game is over.\n");
    Scanner scan = new Scanner(System.in);
    boolean playAgain = false;
    String userAns;
    
    if (player.getHealth() < 1)//if the player is dead
    {
      System.out.println("Because you died your score hasn't been recorded.\nWould you like to view the current high"
                           +"score list? (y/n)");
      userAns = scan.nextLine();
      if (userAns.equalsIgnoreCase("y") || userAns.equalsIgnoreCase("yes")){//if they want to see the high scores
        parallel_sort(scoreNames, scoreScores);//sort the high scores
        printLeaderboard(scoreNames, scoreScores,length);}//print the high scores
      System.out.println("Would you like to replay? (y/n)");//if the user would like to replay set the return value to true
      userAns = scan.nextLine();
      if (userAns.equalsIgnoreCase("y") || userAns.equalsIgnoreCase("yes"))
        return true;
      //end if-statement
    }
    else
    {
      System.out.println("Because you willingfully ended the game, you score has been recorded.\nWould you like to"
                           +" view the current high score list? (y/n)");//if the player ended the game on their own accord
      userAns = scan.nextLine();
      if (userAns.equalsIgnoreCase("y") || userAns.equalsIgnoreCase("yes")){
        addPlayerToLeaderboard(scoreNames,scoreScores,scoreNamesOutput,scoreScoresOutput,length,player);
        parallel_sort(scoreNamesOutput, scoreScoresOutput);
        printLeaderboard(scoreNamesOutput, scoreScoresOutput,length+1);}
      System.out.println("Would you like to replay? (y/n)");
      userAns = scan.nextLine();
      if (userAns.equalsIgnoreCase("y") || userAns.equalsIgnoreCase("yes"))
        return true;
      
    }
    return false;
  }//end method endGame
  
  //prints the leaderboard to the pretty output file and the former input file, (for reuse.)
  //uses scoreNames, scoreScores, and length to print to the output files.
  public static void printLeaderboard(String[] scoreNames, int[] scoreScores, int length) throws IOException
  {
    PrintWriter outputFile = new PrintWriter("HighScores.txt");//creating the output files
    PrintWriter outputFileForReuse = new PrintWriter("ScoreList.txt");
    String output;
    System.out.println("High Score list:\n-----------------------");//for style
    if (length >10){//if the length is greater than 10
      for (int i = 0; i<10; i++){
        output = ""+(i+1)+".";//for style
        System.out.printf("%-3s   %-12s%-4d\n",output,scoreNames[i],scoreScores[i]);//printing to the screen
        outputFile.printf("%-3s   %-12s%-4d\n",output,scoreNames[i],scoreScores[i]);//printing to the output file.
        outputFile.println();
      }//end for loop
    }
    else{//if the length is less than 10
      for (int i = 0; i<length; i++){//only run through the array for the length of the array (to avoid outOfBounds errors).
        output = ""+(i+1)+".";
        System.out.printf("%-3s   %-12s%-4d\n",output,scoreNames[i],scoreScores[i]);
        outputFile.printf("%-3s   %-12s%-4d\n",output,scoreNames[i],scoreScores[i]);}
      
    }//end if statement
    System.out.println("-----------------------");//for formatting
    for (int i = 0; i<length; i++){//prints the new array for the computer to use the next time someone plays the game.
      outputFileForReuse.println(scoreNames[i] + " "+scoreScores[i]);}
    
    outputFileForReuse.close();//close the files.
    outputFile.close();
  }//end method printLeaderboard
  //prints the map of the loot for the player
  //the draw class is used to access the print methods. Building is necessary for the draw methods to work.
  public static void printMap(Draw draw,Building[] building)
  {
    Scanner scan = new Scanner(System.in);
    draw.updateIndicators(building);//updates the indicators in the draw class
    draw.print();//prints for the draw class
    System.out.println("Enter any letter/number to exit the map:");//allows the user to exit the map on their own
    scan.nextLine();
  }//end method printMap
  
}//end class game3