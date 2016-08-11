/* Dungeons & Dragons: The One Without Dungeons or Dragons
 Player
 Drew Jacobson
 djacobso
 Section 4 */

import java.util.Random;
public class Player 
{
  private int score;
  private int health = 30;
  private int[] skills = new int[3];
  private int reroll;
  private String name;
  
  public Player()
  {
    rollSkillValues();
  }//end constructor
  public void incHealth(int amt)
  {
    health+=amt;//adds the specified amount to health
  }//end method incHealth
  
  public void incSkill(int amt, int skill)
  {
    skills[skill] += amt; //adds the specified amount to the specified skill
  }//end method incSkill
  
  public void setName(String n)
  {
    name = n;
  }//end method setName
  
  public String getName()
  {
    return name; 
  }//end method getName
  
  //decreases the player's health
  //amt is the integer amount the plsyer's health should be decreased
  public void decHealth(int amt)
  {
    health -= amt; 
  }//end method decHealth
  
  public int getHealth()
  {
    return health; //returns the value of the sprite's health
  }//end method getHealth
  
  public void rollSkillValues()
  {
    Random rand = new Random();//generating the skill values
    //using arbitrary values as the seed for the skill values
    skills[0] = rand.nextInt(20)+1;//attack                                                                  (RANDOM)
    
    int skillOneMax = (20-skills[0])+1;//takes the remaining from the skill[0] calculation
    skills[1] = rand.nextInt(skillOneMax)+5;//sneak
    
    int skillTwoMax = (skillOneMax - skills[1]) +6;
    skills[2]= skillTwoMax;//speed
  }//end method rollSkillValues
  
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
  
  public int rollAttack()
  {
    Random rand = new Random();
    return (rand.nextInt(skills[0])); //retuns the value of a roll of the Sprite's attack skill
  }//end method rollAttack
  
  public int rollSneak()
  {
    Random rand = new Random();
    return (rand.nextInt(skills[1]));//retuns the value of a roll of the Sprite's sneak skill
  }//end method rollSneak
  
  public int rollSpeed()
  {
    Random rand = new Random();
    return (rand.nextInt(skills[2]));//retuns the value of a roll of the Sprite's speed skill
  }//end method rollSpeed
  public boolean checkPlayerDeath()//checks whether the player is dead
  {
    boolean dead;
    if (health < 1)//if the player's health is 0 or below (less than 1).
      dead = true;
    else
      dead = false;
    return dead;
  }//end method checkPlayerDeath
  public void incScore()//increases the score for the player
  {
    score++; 
  }//end method incScore
  public int getScore()//returns the player's score
  {
    return score; 
  }//end method getScore
  
}//end class Player