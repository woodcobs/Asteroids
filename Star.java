
public class Star implements Drawable
{
  private Point location;
  
  public Star()
  {
    int xVal = (int) (Math.random() * GameConstants.SCREEN_WIDTH + 1);
    int yVal = (int) (Math.random() * GameConstants.SCREEN_HEIGHT + 1);
    location = new Point(xVal, yVal);
    
  }

  @Override
  public void draw()
  {
    // TODO Auto-generated method stub
    StdDraw.filledCircle(location.getX(), location.getY(), GameConstants.STAR_RADIUS);
    
  }

}
