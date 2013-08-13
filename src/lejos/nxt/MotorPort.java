package lejos.nxt;

import tools.AdministratorConstants;

public class MotorPort implements AdministratorConstants{

	protected int portId = 0;
	
	private MotorPort(int portId)
	{
		this.portId = portId;
	}	
	
	/**
	 * A motor port for a motor connected to port A.
	 */
	public static final MotorPort A = new MotorPort(0);
	/**
	 * A motor port for a motor connected to port B.
	 */
	public static final MotorPort B = new MotorPort(1);
	/**
	 * A motor port for a motor connected to port C.
	 */
	public static final MotorPort C = new MotorPort(2);

	public static MotorPort getInstance(int id)
	{
		switch (id)
		{
		case 0:
			return MotorPort.A;
		case 1:
			return MotorPort.B;
		case 2:
			return MotorPort.C;
		default:
			throw new IllegalArgumentException("no such motor port");
		}
	}
	
	public int getPortId() {
		return portId;
	}

}
