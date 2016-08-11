/* Dungeons & Dragons: The One Without Dungeons or Dragons
 Building
 Drew Jacobson
 djacobso
 Section 4 */
import java.util.Scanner;
import java.util.Random;
public class Building
{
  Random rand = new Random();//generating the floors
  public  Floor[] floors = new Floor[numOfFloors = rand.nextInt(3)+2];
  private int numOfFloors;
  private int monsterHP;
  
  //constructor. creates floors
  //mHP is used in the room class, so it needs to be "pushed" through all constructors until it gets there.
  public Building(int mHP)
  {
    monsterHP = mHP;
    for (int i = 0; i < numOfFloors; i++)
    {
      //System.out.println("floor " +i+" created");
      floors[i] = new Floor(mHP);
      //System.out.println("\t\t\tTHIS IS TESTING: " + floors[i].getNumOfRooms());
    }//creates floors in the array for the full amount of the array
  }
  public Floor[] getFloorArray()//returns the floor array
  {
    return floors; 
  }//end method getFloorArray
//  public void setFloor(int num)//
//  {
//    
//  }
  public int getNumOfFloors()//returns the number of floors in the building
  {
    return numOfFloors; 
    
  }//end method getNumOfFloors
  public int getNumOfRooms(int num)//ets the number of rooms on a certain floor in the building. num is the number of the floor.
  {
    floors[num] = new Floor(monsterHP); 
    return floors[num].getNumOfRooms(); //returns the number of rooms on a specific floor
    
  }//end method getNumOfRooms
  
}//end class Building