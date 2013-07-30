package nxt.simulator;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;


/**
 * Abstract class as ancestor of all parts.
 */
public class Part extends Actor
{
  private Location pos;

  public Part(String imageName, Location pos)
  {
    super(true, imageName);
    this.pos = pos;
  }

  public Part(String imageName, Location pos, int nbSprites)
  {
    super(true, imageName, nbSprites);
    this.pos = pos;
  }
  
  public Part(boolean isRotatable, String imageName, Location pos)
  {
    super(true, imageName);
    this.pos = pos;
  }  

  public void setPosition(Location pos)
  {
    this.pos = pos.clone();
  }

  public Location getPosition()
  {
    return pos;
  }

  // Called to cleanup
  protected void cleanup() {
	  
  }
  
	public void detect() {	
	}	  
}
