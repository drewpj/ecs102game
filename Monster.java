/* Dungeons & Dragons: The One Without Dungeons or Dragons
 Monster
 Drew Jacobson
 djacobso
 Section 4 */
import java.util.Random;
public class Monster //extends Sprite
{
  private int health = 25;
  private int[] skills = new int[3];
  private int reroll;
  
  public Monster(int mHP)//constuctor. uses mHP to determine the skill and health values of the monster
  {
    health += (int)((mHP*mHP));
    rollSkillValues(mHP);
  }//end constructor
  
  //rolls the skill values of the monster
  //uses mHP to change the values depending on the building the monster is located in
  public void rollSkillValues(int mHP)
  {
    Random rand = new Random();//generating the skill values
    //using arbitrary values as the seed for the skill values
    skills[0] = (rand.nextInt(20)+1+((mHP)*4));//attack value
    
    int skillOneMax = (20-skills[0])+1+((mHP)*4); 
    skills[1] = rand.nextInt(skillOneMax);//sneak
    
    int skillTwoMax = (skillOneMax - skills[1]) +1; //doesn't use extra mHP code because it is created from leftover
    skills[2]= skillTwoMax;//speed                                                                             //values
  }
  
  //decreases the amount of health of the monster
  //ant is the integer amount
  public void decHealth(int amt)
  {
    health -= amt; //decreases the amount of health on in sprite
  }//end method decHealth
  public int getHealth()
  {
    return health; //returns the value of the sprite's health
  }//end method getHealth
  
  public  int getAttack()
  {
    return skills[0];//returns the value of the sprite's attack
  }//end method getAttack
  
  public int getSneak()
  {
    return skills[1];//returns the value of the sprite's sneak
  }//end method getSneak
  
  public int getSpeed()
  {
    return skills[2];//returns the value of the sprite's speed
  }//end method getSpeed
  
  //rolls the attack value of the monster and returns that value
  public int rollAttack()
  {
    Random rand = new Random();
    return (rand.nextInt(skills[0])+1); //retuns the value of a roll of the Sprite's attack skill
  }//end method rollAttack
  
  //rolls the sneak value of the monster and returns that value
  public int rollSneak()
  {
    Random rand = new Random();
    return (rand.nextInt(skills[1])+1);//retuns the value of a roll of the Sprite's sneak skill
  }//end method rollSneak
  
  //rolls the speed value of the monster and returns that value
  public int rollSpeed()
  {
    Random rand = new Random();
    return (rand.nextInt(skills[2])+1);//retuns the value of a roll of the Sprite's speed skill
  }//end method rollSpeed
  
}//end class Monster