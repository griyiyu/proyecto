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
	

	public final static int NB_STEPS = 1;  // Number of pixels advances per simulation period	
	public final static int SIMULATION_PERIOD = 100;
	double MOTOR_ROT_INC_FACTOR = 2.0;  // Factor that determines motor rotation speed
	double GEAR_ROT_INC_FACTOR = 3.0;   // Factor that determines gear rotation speed

	double MOTOR_TURN_ANGLE = 15;  // Angle per simulation period (in degrees) when motors have same speed
	
	
	//public static String imagePath = "C:\\Proyecto/workspace/NxtSim/src/examples/sprites/";
	//public static String imagePath = "D:\\Eclipse/workspace/NxtSim/src/examples/sprites/";

	public static final double  START_DIRECTION = Direction.SOUTH.getDegrees();
	public static final Location  START_LOCATION = new Location(250, 250);
	
	public static String IMAGE_PATH = "sprites/";
	public static String OBSTACLE_PATH = "sprites/obstacles/";
	
	public final static int START_SPEED = 300;
	
	public final static int FORWARD = 1;
	public final static int BACKWARD = 2;
	public final static int STOP = 3;
	public final static int FLOAT = 4;

	//Colours
    public static final int BLACK = 1;
    public static final int BLUE = 2;
    public static final int GREEN = 3;
    public static final int YELLOW = 4;
    public static final int RED = 5;
    public static final int WHITE = 6;
    /** Color sensor data RED value index. */
    public static final int RED_INDEX = 0;
    /** Color sensor data GREEN value index. */
    public static final int GREEN_INDEX = 1;
    /** Color sensor data BLUE value index. */
    public static final int BLUE_INDEX = 2;
    /** Color sensor data BLANK/Background value index. */
    public static final int BLANK_INDEX = 3;
    
    public static final Color backgroundColor = new Color(56, 114, 114);
    
    //Tiles
    public static final int TILE_WIDTH = 20;
    
    //Directions
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
