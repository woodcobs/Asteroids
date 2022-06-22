
public class Bullet extends Projectile
{
  
  private int stepsRemaining;
  
  public Bullet(Point p, double h)
  {
    super(p, new Vector2D(h, GameConstants.BULLET_SPEED));
    stepsRemaining = GameConstants.BULLET_LIFETIME;
    
  }
  
  public void update()
  {
    stepsRemaining--;
    location.moveAndWrap(super.velocity, GameConstants.SCREEN_WIDTH-1, 
        GameConstants.SCREEN_HEIGHT-1);
    
    if (stepsRemaining <= 0)
    {
      super.setDefunct();
    }
    
  }
  
  public void draw()
  {
    StdDraw.filledCircle(super.getLocation().getX(), 
        super.getLocation().getY(), GameConstants.BULLET_RADIUS);
  }
  
  public int getPoints()
  {
    return 0;
  }
  
  public double getSize()
  {
    return GameConstants.BULLET_RADIUS;
  }
  

}
