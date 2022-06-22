
public class ToughAsteroid extends Projectile
{
  int astLives;
  public ToughAsteroid()
  {
    super(GameConstants.ASTEROID_SPEED);
    astLives = 5;
  }

  @Override
  public void update()
  {
    location.moveAndWrap(velocity, GameConstants.SCREEN_WIDTH-1, GameConstants.SCREEN_HEIGHT-1);
  }

  @Override
  public void draw()
  {
    if (astLives == 5)
    {
      StdDraw.circle(location.getX(), location.getY(), 60);
    } else if (astLives == 4)
    {
      StdDraw.circle(location.getX(), location.getY(), 50);
    } else if (astLives == 3)
    {
      StdDraw.circle(location.getX(), location.getY(), 40);
    } else if (astLives == 2)
    {
      StdDraw.circle(location.getX(), location.getY(), 30);
    } else
    {
      StdDraw.circle(location.getX(), location.getY(), 20);
    }
  }
  
  @Override
  public int getPoints()
  {
    return GameConstants.ASTEROID_POINTS * 2;
  }

  @Override
  public double getSize()
  {
    return 60;
  }
  
  public int getLives()
  {
    return astLives;
  }
  
  public void decrementLives()
  {
    astLives--;
  }
}
