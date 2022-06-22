
public class Ship implements Drawable, Updateable, Collidable
{
  private Pose pose;
  private Vector2D velocity;
  
  public Ship()
  {
    pose = new Pose(GameConstants.SCREEN_WIDTH / 2, GameConstants.SCREEN_HEIGHT / 2, Math.PI / 2);
    velocity = new Vector2D(Math.PI / 2, Math.PI / 2);
    
  }
  
  public void reset()
  {
    pose.setX(GameConstants.SCREEN_WIDTH / 2);
    pose.setY(GameConstants.SCREEN_HEIGHT / 2);
    pose.setHeading(Math.PI / 2);
    
  }
  
  public double getHeading() 
  {
    return velocity.getHeading();
  }

  @Override
  public Point getLocation()
  {
    // TODO Auto-generated method stub
    return new Point(pose.getX(), pose.getY());
  }

  @Override
  public double getSize()
  {
    // TODO Auto-generated method stub
    return (GameConstants.SHIP_WIDTH);
  }

  @Override
  public void update()
  {
    // TODO Auto-generated method stub
    if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_LEFT))
    {
      pose.setHeading(pose.getHeading() + GameConstants.SHIP_TURN_SPEED);
      velocity.setHeading(pose.getHeading() + GameConstants.SHIP_TURN_SPEED);
      
    } else if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_RIGHT))
    {
      pose.setHeading(pose.getHeading() - GameConstants.SHIP_TURN_SPEED);
      velocity.setHeading(pose.getHeading() + GameConstants.SHIP_TURN_SPEED);
    }
    
    if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_DOWN))
    {
      GameUtils.applyThrust(velocity, pose.getHeading(), GameConstants.SHIP_ACCELERATION);      
      pose.moveAndWrap(velocity, GameConstants.SCREEN_WIDTH-1, GameConstants.SCREEN_HEIGHT-1);
    } else 
    {
      velocity.setMagnitude(velocity.getMagnitude() * GameConstants.SHIP_FRICTION);    
      pose.moveAndWrap(velocity, GameConstants.SCREEN_WIDTH-1, GameConstants.SCREEN_HEIGHT-1);

    }
    
    
  }

  @Override
  public void draw()
  {
    // TODO Auto-generated method stub
    GameUtils.drawPoseAsTriangle(pose, GameConstants.SHIP_WIDTH, GameConstants.SHIP_HEIGHT);
  }

}
