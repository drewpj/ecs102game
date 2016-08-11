/* Dungeons & Dragons: The One Without Dungeons or Dragons
 Floor
 Drew Jacobson
 djacobso
 Section 4 */
import java.util.Scanner;
import java.util.Random;
public class Floor                               // (CL)
{
  Random rand = new Random();
  public  Room[] rooms = new Room[numOfRooms = rand.nextInt(3)+2];
  private int numOfRooms;
  
  //constructor. creates rooms
  //mHP is used in the room class, so it needs to be "pushed" through all constructors until it gets there.
  public Floor(int mHP)
  {
    for (int i = 0; i < numOfRooms; i++)
    {
      rooms[i] = new Room(mHP);
    }//creates the rooms in the array
  }//end constructor
  public int getNumOfRooms()
  {
    return numOfRooms; //returns the number of rooms
  }//end method getNumOfRooms
  
}//end class Floor