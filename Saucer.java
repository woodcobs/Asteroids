
public class Saucer extends Projectile
{
  public Saucer()
  {
    super(GameConstants.SAUCER_SPEED);
  }

  @Override
  public void update()
  {
    location.move(velocity);
    if (GameConstants.GENERATOR.nextDouble() < GameConstants.SAUCER_DIRECTION_PROB)
    {
      double heading = GameConstants.GENERATOR.nextDouble() * (2 * Math.PI) + 1;
      velocity.setHeading(heading);
    }
    if ((location.getX() > GameConstants.SCREEN_WIDTH 
        || location.getX() < 0) ||(location.getY() > GameConstants.SCREEN_HEIGHT 
            || location.getY() < 0))
    {
      super.setDefunct();
    }
  }

  @Override
  public void draw()
  {
    StdDraw.rectangle(location.getX(), location.getY(),
        GameConstants.SAUCER_WIDTH / 2, GameConstants.SAUCER_HEIGHT / 2);
  }
  
  @Override
  public int getPoints()
  {
    return GameConstants.SAUCER_POINTS;
  }

  @Override
  public double getSize()
  {
    return GameConstants.SAUCER_HEIGHT;
  }
  

}
