
public abstract class Projectile implements Drawable, Updateable, Collidable
{
  protected Point location;
  protected Vector2D velocity;
  protected boolean isDefunct;
  
  public Projectile(Point p, Vector2D v)
  {
    location = p;
    velocity = v;
  }
  
  public Projectile(double s)
  {
    double xVal = (Math.random() * GameConstants.SCREEN_WIDTH + 1);
    double yVal = (Math.random() * GameConstants.SCREEN_HEIGHT + 1);
    double heading = GameConstants.GENERATOR.nextDouble() * (2 * Math.PI) + 1;
    Vector2D temp = new Vector2D(heading, s);
    this.location = new Point(xVal, yVal);
    this.velocity = temp;
    
  }
  
  public boolean isDefunct()
  {
    return isDefunct;
  }
  
  public void setDefunct()
  {
    isDefunct = true;
  }
  
  public Point getLocation()
  {
    return location;
  }
  
  public abstract int getPoints();

}
