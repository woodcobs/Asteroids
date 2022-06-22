import java.util.ArrayList;

/**
 * The main asteroids game simulation class. This class controls the simulation by setting up the
 * game, telling the other classes to update themselves, adding more asteroids when necessary,
 * freezing the game when it is over, detecting collisions updating the game state, and telling the
 * other classes when to draw themselves.
 * 
 * @author: C. Fox
 * @version 3/19
 */
public class AsteroidsGame
{
  private ArrayList<Drawable> drawnObjects; // all the stuff drawn to the screen
  private ArrayList<Updateable> movingObjects; // all the things that move
  private final Ship ship; // for detecting ship collisions
  private final Scoreboard scoreboard; // for checking if the game is over
  private final int asteroidStock; // how many asteroids to start with
  private int asteroidsLeft; // current asteroid count during the game
  private int starCount = GameConstants.NUMBER_OF_STARS;

  /**
   * Allocate the stars, ship, and asteroids.
   * 
   * @param numAsteroids
   *          how many asteroids to start with
   */
  public AsteroidsGame(int numAsteroids)
  {
    drawnObjects = new ArrayList<Drawable>();
    movingObjects = new ArrayList<Updateable>();
    this.scoreboard = new Scoreboard();
    this.ship = new Ship();
    this.asteroidStock = GameConstants.DEFAULT_ASTEROIDS_PER_LEVEL;
    this.asteroidsLeft = GameConstants.DEFAULT_ASTEROIDS_PER_LEVEL;
    drawnObjects.add(ship);
    movingObjects.add(ship);
    drawnObjects.add(scoreboard);

    for (int i = 0; i < starCount; i++)
    {
      Star star = new Star();
      drawnObjects.add(star);
    }

    for (int i = 0; i < asteroidStock; i++)
    { 
        Asteroid ast = new Asteroid();
        drawnObjects.add(ast);
        movingObjects.add(ast);      
    }
  }

  /**
   * Check if the game is over, tell all the moving objects in the game to update themselves,
   * perhaps add a saucer and fire bullets, check for collisions, remove all the defunct objects
   * from the lists of drawn and moving objects, and replenish the asteroids if necessary.
   */
  public void update()
  {
    // checks for collisions
    detectCollision();

    // moves parts and checks defunct val
    if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_SPACE))
    {
      Bullet bullet = new Bullet(ship.getLocation(), ship.getHeading());
      drawnObjects.add(bullet);
      movingObjects.add(bullet);
    }

    if (scoreboard.isGameOver())
    {
      System.out.println("Game Over");
      drawnObjects.clear();
      movingObjects.clear();
      StdDraw.text(GameConstants.SCREEN_HEIGHT / 2, GameConstants.SCREEN_WIDTH / 2, "GAME OVER");
      StdDraw.text(GameConstants.SCREEN_HEIGHT / 2, GameConstants.SCREEN_WIDTH / 2 - 25,
          "Score: " + scoreboard.getScore());
    }
    else
    {
      if (asteroidsLeft == 0)
      {
        replenishAsteroids();
      }
      for (int i = movingObjects.size() - 1; i >= 0; i--)
      {
        movingObjects.get(i).update();
        if (GameConstants.GENERATOR.nextDouble() < GameConstants.SAUCER_APPEARANCE_PROB)
        {
          Saucer saucer = new Saucer();
          movingObjects.add(saucer);
          drawnObjects.add(saucer);
        }
        
        if (GameConstants.GENERATOR.nextDouble() < GameConstants.SAUCER_APPEARANCE_PROB / 2)
        {
          ToughAsteroid tough = new ToughAsteroid();
          movingObjects.add(tough);
          drawnObjects.add(tough);
        }

        if (movingObjects.get(i) instanceof Projectile)
        {
          if (((Projectile) movingObjects.get(i)).isDefunct())
          {
            Projectile item = (Projectile) movingObjects.get(i);
            if (movingObjects.get(i) instanceof Asteroid)
            {
              asteroidsLeft--;
            }
            movingObjects.remove(item);
            drawnObjects.remove(item);
          }
        }
      }
    }

  }

  /**
   * Tell all the drawn objects to draw themselves.
   */
  public void draw()
  {
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setPenRadius();
    for (Drawable item : drawnObjects)
    {
      item.draw();
    }
  }

  public void replenishAsteroids()
  {
    asteroidsLeft = asteroidStock;
    for (int i = 0; i < asteroidStock; i++)
    {
      Asteroid ast = new Asteroid();
      drawnObjects.add(ast);
      movingObjects.add(ast);
    }
  }

  public void detectCollision()
  {
    // first we want to go through all the movable items in the game
    double x1;
    double y1;
    double size1;
    Collidable item1;

    for (int i = movingObjects.size() - 1; i >= 1; i--)
    {
      // create a temp value to compare against all the following values
      if (movingObjects.get(i) instanceof Ship)
      {
        item1 = (Ship) movingObjects.get(i);
      }
      else if (movingObjects.get(i) instanceof Bullet)
      {
        item1 = (Bullet) movingObjects.get(i);
      }
      else if (movingObjects.get(i) instanceof Asteroid)
      {
        item1 = (Asteroid) movingObjects.get(i);

      } else if (movingObjects.get(i) instanceof ToughAsteroid)
      {
        item1 = (ToughAsteroid) movingObjects.get(i);

      } else
      {
        item1 = (Saucer) movingObjects.get(i);

      }
      x1 = item1.getLocation().getX();
      y1 = item1.getLocation().getY();
      size1 = item1.getSize();

      // now gets every other object in the list
      for (int x = movingObjects.size() - 2; x >= 0; x--)
      {
        Collidable item2 = (Collidable) movingObjects.get(x);
        double x2 = item2.getLocation().getX();
        double y2 = item2.getLocation().getY();
        double size2 = item2.getSize();

        // checks if the objects overlap
        if ((Math.abs(x1 - x2) < size1 && Math.abs(y1 - y2) < size1)
            || (Math.abs(x1 - x2) < size2 && Math.abs(y1 - y2) < size2))
        {
          if (item1 instanceof Ship)
          {
            if (item2 instanceof Asteroid || item2 instanceof Saucer || item2 instanceof ToughAsteroid)
            {
              ((Ship) item1).reset();
              scoreboard.decrementLives();
            }
          }
          else if (item1 instanceof Bullet)
          {
            if (item2 instanceof Asteroid)
            {
              ((Asteroid) item2).setDefunct();
              ((Bullet) item1).setDefunct();
              scoreboard.incrementScore(((Asteroid) item2).getPoints());

            } else if (item2 instanceof ToughAsteroid) 
            {
              if (((ToughAsteroid) item2).getLives() <= 0)
              {
                ((ToughAsteroid) item2).setDefunct();
                ((Bullet) item1).setDefunct();
                scoreboard.incrementScore(((ToughAsteroid) item2).getPoints());
              } else
              {
                ((Bullet) item1).setDefunct();
                ((ToughAsteroid) item2).decrementLives();
              }
              
            } else if (item2 instanceof Saucer)
            {
              ((Saucer) item2).setDefunct();
              ((Bullet) item1).setDefunct();
              scoreboard.incrementScore(((Saucer) item2).getPoints());

            }
          }
          else if (item1 instanceof Asteroid)
          {
            if (item2 instanceof Ship)
            {
              ((Ship) item2).reset();
              scoreboard.decrementLives();
            }
          }
          else if (item1 instanceof Saucer)
          {
            if (item2 instanceof Ship)
            {
              ((Ship) item2).reset();
              scoreboard.decrementLives();
            }
          }
          else if (item1 instanceof ToughAsteroid)
          {
            if (item2 instanceof Ship)
            {
              ((Ship) item2).reset();
              scoreboard.decrementLives();
            }
          }
        }

      }
    }
  }

}
