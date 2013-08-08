package lejos.nxt;

import java.awt.Point;
import java.util.ArrayList;

import nxt.simulator.Part;
import tools.AdministratorConstants;
import ch.aplu.jgamegrid.Location;


/**
 * Class that represents one of the NXT motors.
 */
public class Motor extends Part implements AdministratorConstants {
	private static final Location pos1 = new Location(0, 23);
	private static final Location pos2 = new Location(0, -25);
	
	private static Point collisionCenter = new Point(-6, 0);
	private static int collisionRadius = 0;	
	
    /**
     * Motor A.
     */
    public static final Motor A = new Motor(MotorPort.A);
    /**
     * Motor B.
     */
    public static final Motor B = new Motor(MotorPort.B);
    /**
     * Motor C.
     */
    public static final Motor C = new Motor(MotorPort.C);		

	protected MotorPort port;
	protected int speed = START_SPEED;
	protected int mode = STOP;
	
    
	/**
	 * Creates a motor instance that is plugged into given port.
	 * 
	 * @param port
	 *            the port where the motor is plugged-in (MotorPort.A,
	 *            MotorPort.B, MotorPort.C)
	 */
	public Motor(MotorPort port) {
		super(port == MotorPort.A ? AdministratorConstants.IMAGE_PATH + "RuedaA.png"
				: (port == MotorPort.B ? AdministratorConstants.IMAGE_PATH + "RuedaB.png" : AdministratorConstants.IMAGE_PATH + "RuedaA.png"),
				port == MotorPort.A ? pos1
				: (port == MotorPort.B ? pos2 : pos1));
		setCollisionCircle(collisionCenter, collisionRadius);
		addTileCollisionListener(this);
	}
	
	/**
	 * Creates a motor instance that is plugged into given port and location.
	 * 
	 * @param port
	 * @param location
	 */
	public Motor(MotorPort port, Location location) {
		super(port == MotorPort.A ? AdministratorConstants.IMAGE_PATH + "RuedaA.png"
				: (port == MotorPort.B ? AdministratorConstants.IMAGE_PATH + "RuedaB.png" 
						: AdministratorConstants.IMAGE_PATH + "RuedaA.png"), 
				location);
	}

	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	
	public void forward()
	{
		setMode(FORWARD);
	}
	

	public void backward()
	{
		setMode(BACKWARD);
	}

	
	public void stop()
	{
		setMode(STOP);
	}

	
	public int getMode(){
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}	
	
	public boolean isCollide() {
		boolean returnedValue = false;
		ArrayList<Location> occupiedTiles = new ArrayList<Location>();		
		occupiedTiles.add(new Location(getLocation().getX()/20, (getLocation().getY() - 32)/20));
		occupiedTiles.add(new Location(getLocation().getX()/20, (getLocation().getY() + 34)/20));

		for (Location loc : getCollisionTiles()) {
			for (Location occLoc : occupiedTiles){
				if (loc.getX() == occLoc.getX() && loc.getY() == occLoc.getY()) {					
					returnedValue = true;
					break;				
				}
			}
		}
		return returnedValue;		
	}	
}
