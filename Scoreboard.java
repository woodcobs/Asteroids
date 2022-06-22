
public class Scoreboard implements Drawable
{
  private int score;
  private int lives;
  
  public Scoreboard()
  {
    score = 0;
    lives = GameConstants.NUMBER_OF_LIVES;
    
  }
  
  public void incrementScore(int k)
  {
    score += k;
  }
  
  public void decrementLives()
  {
    lives -= 1;
  }
  
  public boolean isGameOver()
  {
    return lives <= 0;   
  }

  @Override
  public void draw()
  {
    // TODO Auto-generated method stub
    StdDraw.text(GameConstants.SCORE_X, GameConstants.SCORE_Y, "Score :" + score);
    StdDraw.text(GameConstants.LIVES_X, GameConstants.LIVES_Y, "Lives :" + lives);
    
  }
  
  public int getScore()
  {
    return score;
  }

}
