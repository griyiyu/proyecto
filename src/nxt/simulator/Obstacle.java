/*
 Author: Sebastian Mangioni
 */

package nxt.simulator;

import java.awt.Point;

import tools.AdministratorConstants;
import ch.aplu.jgamegrid.*;

/**
 * Class to represent an obstacle detected by a touch sensor.
 */
public class Obstacle extends Actor implements GGMouseTouchListener {
	
	private Point hotSpot;

	protected String name = "";
	
	/**
	 * Creates an obstacle from given image file.
	 * 
	 * @param imageName
	 *            the image to be uses as obstacle
	 */
	public Obstacle(String imageName) {
		super(AdministratorConstants.OBSTACLE_PATH + imageName);
		setName(imageName);
		setCollisionImage();
	}

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void mouseTouched(Actor actor, GGMouse mouse, Point spot) {
		switch (mouse.getEvent()) {
		case GGMouse.lPress:
			hotSpot = spot;	
			break;
		case GGMouse.lDrag:
			if (hotSpot == null) // Pressed outside sprite
				hotSpot = spot;
			Location loc = new Location(mouse.getX() - hotSpot.x, mouse.getY() - hotSpot.y);
			actor.setLocation(loc);
			gameGrid.refresh();
			break;
		case GGMouse.lRelease:
			actor.setLocation(new Location(mouse.getX() - hotSpot.x, mouse.getY() - hotSpot.y));
			hotSpot = null;
			gameGrid.refresh();
			break;
		}
	}
	
}
