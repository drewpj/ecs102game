/* Dungeons & Dragons: The One Without Dungeons or Dragons
 Room
 Drew Jacobson
 djacobso
 Section 4 */
import java.util.Scanner;
import java.util.Random;
public class Room
{
  private int floor;
  private int roomNum;
  private int awardType;
  private Monster monster;
  private boolean isMon;
  private boolean lootTaken;
  
  //constructor. creates monsters
  //mHP is used in the monster class, so it needs to be "pushed" through all constructors until it gets there.
  public Room(int mHP)
  {
    Random rand = new Random();//creating a random object
    int i = 2; //rand.nextInt(10);
    if (i > 1)
    {
      monster = new Monster(mHP);
      isMon = true; 
    }
    else
      isMon = false;
    int awardType = rand.nextInt(3);
  }//end constructor
  
  public Monster getMonster()//returns the monster class.
  {
    return monster; 
  }//get method getMonster
  
  public void setIsMonFalse()//sets the value of isMon to false.
  {
    
    isMon = false; 
  }//get method setIsMonFalse
  public boolean getIsMon()//returns the vlaue of isMon.
  {
    return isMon; 
  }//get method getIsMon
  
  public void setLootTakenFalse()//sets the value of lootTaken to true.
  {
    lootTaken = true; 
  }//get method setLootTakenFalse
  
  public boolean getLootTaken()//returns the value of loot taken.
  {
    return lootTaken; 
  }//get method getLootTaken
  
}//end class Room