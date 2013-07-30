
package lejos.nxt;


/**
 * Useful declarations for sensor port connections.
 */
public class SensorPort
{

    /**
     * Port labeled 1 on NXT.
     */
    public static final SensorPort S1 = new SensorPort(0);
    /**
     * Port labeled 2 on NXT.
     */
    public static final SensorPort S2 = new SensorPort(1);
    /**
     * Port labeled 3 on NXT.
     */
    public static final SensorPort S3 = new SensorPort(2);
    /**
     * Port labeled 4 on NXT.
     */
    public static final SensorPort S4 = new SensorPort(3);
	

    public static SensorPort getInstance(int id)
    {
    	switch (id)
    	{
    		case 0:
    			return SensorPort.S1;
    		case 1:
    			return SensorPort.S2;
    		case 2:
    			return SensorPort.S3;
    		case 3:
    			return SensorPort.S4;
    		default:
    			throw new IllegalArgumentException("no such sensor port");
    	}
    }
    
  private int portId;

  private SensorPort(int id)
  {
    portId = id;
  }

}
