package lejos.nxt;

import nxt.simulator.Environment;
import nxt.simulator.Part;
import tools.AdministratorConstants;
import ch.aplu.jgamegrid.Location;


public abstract class Sensor extends Part {

	protected static Environment environment;
	
	protected static final Location pos1 = new Location(40, 10);
	protected static final Location pos2 = new Location(40, 0);
	protected static final Location pos3 = new Location(40, -10);
	protected static final Location pos4 = new Location(-40, 10);

	protected SensorPort port;
	
	/**
	 * Crea una instancia del Sensor que se desea utilizar y lo asigna a la posición que
	 * corresponda al puerto en el que esta conectado el sensor.
	 *  
	 * @param port - Puerto en donde fue conectado el sensor
	 * @param imageName - Imagen que representa al sensor
	 */
	public Sensor(SensorPort port, String imageName) {
		super(AdministratorConstants.IMAGE_PATH + imageName + 
				new Integer(port.getPortId()).toString() + ".png",
				port == SensorPort.S1 ? pos1 : (port == SensorPort.S2 ? pos2
						: (port == SensorPort.S3 ? pos3 : pos4)));

		this.port = port;
	}
	
	/**
	 * Crea una instancia del Sensor que se desea utilizar y lo asigna a la posición que
	 * corresponda al puerto en el que esta conectado el sensor.
	 *  
	 * @param port - Puerto en donde fue conectado el sensor
	 * @param imageName - Imagen que representa al sensor
	 * @param moreCloser - Le resta o suma un valor para que cuando se cree la imagen esté pegada al ladrillo.
	 */
	public Sensor(SensorPort port, String imageName, int moreCloser) {
		super(AdministratorConstants.IMAGE_PATH + imageName + 
				new Integer(port.getPortId()).toString() + ".png",
				port == SensorPort.S1 ? pos1 : (port == SensorPort.S2 ? pos2
						: (port == SensorPort.S3 ? pos3 : pos4)));		
		this.port = port;
		// Si es el puerto 4 entonces debe voltearse la imagen ya que el sensor es agregado arriba.
		if (port == SensorPort.S4) {
			// Se suma o resta la cantidad necesaria para que el sensor pueda ser mostrado correctamente
			setPosition(new Location(getPosition().x + moreCloser, getPosition().y));
		} else {
			// Se suma o resta la cantidad necesaria para que el sensor pueda ser mostrado correctamente
			setPosition(new Location(getPosition().x - moreCloser, getPosition().y));
		}
	}
	
	
	public SensorPort getSensorPor() {
		return port;
	}

	public static Environment getEnvironment() {
		return environment;
	}

	public static void setEnvironment(Environment world) {
		Sensor.environment = world;
	}

}
