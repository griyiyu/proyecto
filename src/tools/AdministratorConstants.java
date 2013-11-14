/**
 * 
 */
package tools;

import java.awt.Color;

import ch.aplu.jgamegrid.Location;

/**
 * @author Gri
 *
 */
public interface AdministratorConstants {
	
	// Number of pixels advances per simulation period	
	public final static int NB_STEPS = 1;  
	
	// Simulation period
	public final static int SIMULATION_PERIOD = 100;
	
	// Factor that determines motor rotation speed
	double MOTOR_ROT_INC_FACTOR = 2.0;

	// Factor that determines gear rotation speed
	double GEAR_ROT_INC_FACTOR = 3.0;   

	// Angle per simulation period (in degrees) when motors have same speed
	double MOTOR_TURN_ANGLE = 15;  
	
	// Robot start direction 
	public static final double START_DIRECTION = Direction.SOUTH.getDegrees();
	
	// Robot start location 
	public static final Location START_LOCATION = new Location(250, 250);
	
	// Robot start speed
	public final static int START_SPEED = 300;
	
	// Default image path
	public static String IMAGE_PATH = "images/";
	
	// Motor forward value
	public final static int FORWARD = 1;
	
	// Motor backward value
	public final static int BACKWARD = 2;
	
	// Motor stop value
	public final static int STOP = 3;
	
	
	public final static int FLOAT = 4;
    
	// Environment background color
    public static final Color backgroundColor = new Color(56, 114, 114);
    
    // Obstacle / background width
    public static final int TILE_WIDTH = 20;
    
    // Directions
    public static enum Direction{
   	  	
    	EAST(0), WEST(180), NORTH(270), SOUTH(90);
    
    	private double degrees;
    	
    	Direction(int val){
    		this.degrees = val;
    	}
    	
	   public double getDegrees(){
		      return this.degrees;
	   }
    };
}
