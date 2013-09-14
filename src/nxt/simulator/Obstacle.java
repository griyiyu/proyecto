/*
 Author: Sebastian Mangioni
 */

package nxt.simulator;

import tools.AdministratorConstants;
import ch.aplu.jgamegrid.*;

/**
 * Class to represent an obstacle detected by a touch sensor.
 */
public class Obstacle extends Actor {
	
	public String name = "";
	
	/**
	 * Creates an obstacle from given image file.
	 * 
	 * @param imageName
	 *            the image to be uses as obstacle
	 */
	public Obstacle() {
		super(AdministratorConstants.IMAGE_PATH + "brickWhite.png");
		setName("brickWhite.png");
		setCollisionImage();
	}
	
	/**
	 * Creates an obstacle from given image file.
	 * 
	 * @param imageName
	 *            the image to be uses as obstacle
	 */
	public Obstacle(String imageName) {
		super(AdministratorConstants.IMAGE_PATH + imageName);
		setName(imageName);
		setCollisionImage();
	}

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
