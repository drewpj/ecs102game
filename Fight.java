/* Dungeons & Dragons: The One Without Dungeons or Dragons
 Fight
 Drew Jacobson
 djacobso
 Section 4 */
import java.util.Random;
import java.util.Scanner;
public class Fight
{
  private int health;
  private Player player;
  private Monster enemy;
  
  public Fight(Monster ene, Player p)//constructor, sets the parameters (monster and player) to the instance data (enemy and player).
  {
    player = p;
    enemy = ene;
    //System.out.print 
  }//end constructor 
  
  //returns the value of a quick attack performed by the player
  public int quickAttackPlayer()
  {
    Random rand = new Random();
    int attackVal = (int)((rand.nextInt(player.getAttack()+1))*(2.0/3));
    return attackVal;
  }
  
  //returns the value of a big attack performed by the player.
  public int bigAttackPlayer()
  {
    Random rand = new Random();
    int attackVal = (rand.nextInt(player.getAttack()+1));
    return attackVal;
  }//end method bigAttackPlayer
  
  //returns the value of a regular/big attack performed by the enemy.
  public int attackEnemy()
  {
    int attackVal;
    Random rand = new Random();
    if (rand.nextInt(2) == 0)
      attackVal = (rand.nextInt(enemy.getAttack()+1));
    else
      attackVal = (int)((rand.nextInt(enemy.getAttack()+1))*(3.0/4));
    return attackVal; 
  }//end method attackEnemy
  //determines whether the player is able to run away from the enemy.
  public boolean runAway()
  {
    Random rand = new Random();
    Boolean runAway = false;
    int attackVal;
    if (rand.nextInt(player.getSpeed()+1) >  rand.nextInt(enemy.getSpeed()+1))//if the player speed is greater than the
    {                                                                                                         //enemy's
      System.out.println("\nYou have successfully run away");
      runAway = true;
    }
    else
    {
      System.out.println("You were not able to run away.");
      attackVal = rand.nextInt(Math.abs((player.getAttack()+1)-(player.getSpeed()+1))+1);
      System.out.println("While you had your back turned, the ghoul has hit you for " +
                         attackVal + " health points.");
      player.decHealth(attackVal);
      
    }
    return runAway;
  }//end method runAway
  
  //determines whether the player is able to sneak by, (using it's sneak skill) the enemy.
  public boolean sneakBy()
  {
    Random rand = new Random();
    Boolean sneakBy = false;
    int attackVal;
    if (rand.nextInt(player.getSneak()+1) >  rand.nextInt(enemy.getSneak()+1))//if the player sneak is larger than enemy's
    {
      System.out.println("\nYou have successfully snuck around the unsuspecting ghoul.");
      sneakBy = true;
    }
    else//if the enemy's is larger
    {
      System.out.println("You were not able to sneak around the ghoul.");
      attackVal = rand.nextInt(Math.abs((player.getAttack()+1)-(player.getSpeed()+1))) + 10;
      System.out.println("While you were sneaking by, the ghoul has hit you for " +
                         attackVal + " health points.");
      player.decHealth(attackVal);//decrease the health of player
      
    }
    return sneakBy;
  }//end method sneakBy
  
  //determines whether the user will attack before the enemy
  //quickAttackPlayer is used determine if the player is using a quick attack, (and increases the player's speed accordingly.)
  public boolean checkSpeed(boolean quickAttackPlayer)
  {
    int playerSpeed =  player.getSpeed();//gets the player's speed
    if (quickAttackPlayer)//if the player chose to do a quick attack
      playerSpeed = player.getSpeed() + (player.getSpeed()/3);//add 1/3 of player speed to player's speed
    //System.out.println("Player speed: " + playerSpeed);
    Random rand = new Random();
    if (rand.nextInt(playerSpeed+1) >  rand.nextInt(enemy.getSpeed()+1))//if the player's speed is greater than the enemy's
      return true;//return true
    return false;//return false;
  }//end method checkSpeed
 
}//end class Fight