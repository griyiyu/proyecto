package nxt.simulator;

import java.awt.Point;
import java.util.ArrayList;

import lejos.nxt.Motor;
import lejos.nxt.Sensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;

import tools.AdministratorConstants;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class RobotCar extends Actor implements AdministratorConstants {
	
	/**
	 * Centro de colisi�n del robot.
	 */
	protected static Point collisionCenter = new Point(0,0);
	
	/**
	 * Radio de colisi�n del robot.
	 */
	protected static int collisionRadius = 30;

	/**
	 * Factor de correcci�n por el rosamiento en el suelo etc.
	 */
	protected static final double FACTOR_WHEEL = -0.3;
	
	
	/**
	 * Radio de las ruedas del robot.
	 */
	protected static final double RADIO_WHEEL = 2 + FACTOR_WHEEL;
	
	
	private Location lastTilePosition;
	
	/**
	 * Constructor del robot automovil
	 * 
	 * @param startLocation - Ubicaci�n del robot al iniciar la simulaci�n
	 * @param imageName - Nombre de la imagen la cual representar� al robot
	 */
	public RobotCar(Location startLocation, String imageName) {
		super(true, AdministratorConstants.IMAGE_PATH + imageName);
		addActorCollisionListener(this);
	    setCollisionCircle(collisionCenter, collisionRadius + 10);
	    addTileCollisionListener(this);
	}
		
	
	@Override
	public synchronized void act() {
		((Environment)gameGrid).setCicleStartTime(System.currentTimeMillis());
		int modeA = Motor.A.getMode();
		int modeB = Motor.B.getMode();
		// Si los dos motores estan detenidos no se mueve el robot
		if (STOP == modeA && STOP == modeB) {
			return;
		}
		int speedA = Motor.A.getSpeed();	//grados/seg
		int speedB = Motor.B.getSpeed();
		// Si los dos motores tienen velocidad cero no se mueve el robot		
		if (speedA == 0 && speedB == 0) {
			return;
		}		
		// Si el robot no se va del cuadro, intenta mover el robot
		if (isMoveValid()) {

			int speedA2 = getSpeedPxP(modeA, speedA);
			int speedB2 = getSpeedPxP(modeB, speedB);
			speedA = getSpeed(modeA, speedA);
			speedB = getSpeed(modeB, speedB);
			// Obtiene la direcci�n actual
			double tita = getDirection();
			// Obtiene la posici�n x e y actual
			double x = getX();
			double y = getY();
			double wheelDistance = 16; //cm
			/** Calculo del angulo de direcccion.
			 * Las velocidades vienen dadas en grados/seg, se divide por 1000 para pasar a ms y luego se multiplica por el 
			 * tiempo de simulacion dado en ms.
			 */
			tita = tita + ((RADIO_WHEEL/wheelDistance) *  (((double)(speedB - speedA)/1000)*SIMULATION_PERIOD));
			// Calcula la posici�n x del robot
			x = x + ((speedB2 + speedA2)/2) * Math.cos((float)tita * (Math.PI / (float)180));
			// Calcula la posici�n y del robot
			y = y + ((speedB2 + speedA2)/2) * Math.sin((float)tita * (Math.PI / (float)180));
			int xPosition = (int)Math.round(x);
			int yPosition = (int)Math.round(y);
			
			int posTileX = this.getLocation().getX() / 20;
			int posTileY = this.getLocation().getY() / 20;
			lastTilePosition = new Location(posTileX, posTileY);
			// Verifica que el robot no se choque con nada en la nueva posici�n
			boolean possibleMove = canMove(xPosition, yPosition, tita);
			if (possibleMove) {
				// Mueve el robot en la direcci�n y posici�n calculada
				moveCar(xPosition, yPosition, tita);
			}
		}
	}
	/**
	 * Retorna true si alguno de los sensores o las ruedas toca un obstaculo
	 * @return boolean
	 */
	private boolean isPartCollide(int x, int y, double direction) {
		boolean returnedValue = false;
		for (Actor sensor : gameGrid.getActors(Sensor.class)) {
			if (sensor.getClass() == UltrasonicSensor.class) {
				if (((UltrasonicSensor)sensor).isCollide(x, y, direction)) { 
					returnedValue = true;
					System.out.println("Esta tocando un sensor");
					break;
				}
			} else if (((TouchSensor)sensor).isCollide(x, y, direction)) {
				returnedValue = true;
				break;
			};
		}
		for (Actor wheel : gameGrid.getActors(Motor.class)) {
			if (wheel.getClass() == Motor.class) {
				if (((Motor)wheel).isCollide(x, y, direction)) {
					returnedValue = true;
					System.out.println("Esta tocando una rueda");
					break;
				}
			};
		}		
		return returnedValue;
	}

	/**
	 * Retorna la velocidad en pixels por periodo, con el signo correspondiente
	 * para representar los diferentes modos del robot. 
	 * FORWARD --> (+), BACKWARD --> (-) o STOP --> (0).
	 * 
	 * @param mode - Modo del robot (FORWARD, BACKWARD o STOP)
	 * @param speed - Velocidad en grados por segundo
	 * @return Velocidad en metros por segudo
	 */
	protected int getSpeedPxP(int mode, int speed) {
		double newSpeed = (speed * (Math.PI/180))* ((RADIO_WHEEL)* 10); //Devuelve mm/seg
		newSpeed /=2; // 1 pixel equivale a 2 mm
		newSpeed = (newSpeed/1000) * SIMULATION_PERIOD; //Se pasa a ms y se multiplica por el tiempo de simulacion
		switch (mode) {
		case STOP:
			return 0;
		case BACKWARD:
			return -Math.round((float)newSpeed);
		default:
			return Math.round((float)newSpeed); //FORWARD
		}
	}
	
	
	/**
	 * Retorna la velocidad en grados por segundo, con el signo correspondiente
	 * para representar los diferentes modos del robot. 
	 * FORWARD --> (+), BACKWARD --> (-) o STOP --> (0).
	 * 
	 * @param mode - Modo del robot (FORWARD, BACKWARD o STOP)
	 * @param speed - Velocidad en grados por segundo
	 * @return Velocidad en grados por segundo
	 */
	protected int getSpeed(int mode, int speed) { 
		switch (mode) {
		case STOP:
			return 0;
		case BACKWARD:
			
			return -speed;
			
		default:
			return speed;
		}
	}
	
	
	/**
	 * Asigna la nueva posici�n y direcci�n al robot.
	 * 
	 * @param x - Posici�n x del robot
	 * @param y - Posici�n y del robot
	 * @param tita - �ngulo de direcci�n del robot
	 */
	protected void moveCar(int x, int y, double tita) {
		// Se asigna la nueva direcci�n al ladrillo
		setDirection(tita);
		// Se asigna la nueva posici�n al ladrillo 
		setLocation(new Location(x,y));
		// Se recorren las partes del ladrillo y se le asigna la posicion con respecto al ladrillo
		for (Actor actor : gameGrid.getActors(Part.class)) {
			Part p = (Part) actor; 
			p.setDirection(tita);
			p.setLocation(getPartLocation(p));
		}
	}
	
	/**
	 * Obtiene la posici�n que debe tener la parte pasada por par�metro con respecto a la ubicaci�n del ladrillo.
	 * 
	 * @param part - {@link Part} de la cual se desea obtener la posici�n correcta.
	 * @return {@link Location} Posici�n que debe tener la parte especificada para corresponderse con el ladrillo.
	 */
	protected Location getPartLocation(Part part) {
		int x = getX();
		int y = getY();
		Location partPosition = part.getPosition();
		double radio = Math.sqrt(partPosition.x * partPosition.x + partPosition.y * partPosition.y);
		double phi = Math.atan2(partPosition.y, partPosition.x);
		double direction = getDirection() * Math.PI / 180;
		Location location = new Location((int) (Math.round(x + radio
				* Math.cos(direction + phi))), (int) (Math.round(y + radio
				* Math.sin(direction + phi))));
		return location;
	}

	/**
	 * Obtiene la posici�n que debe tener la parte pasada por par�metro con respecto a la ubicaci�n del ladrillo.
	 * 
	 * @param part - {@link Part} de la cual se desea obtener la posici�n correcta.
	 * @return {@link Location} Posici�n que debe tener la parte especificada para corresponderse con el ladrillo.
	 */
	protected Location getPartLocation(Part part, int xAdd, int yAdd) {
		int x = getX() + xAdd;
		int y = getY() + yAdd;
		Location partPosition = part.getPosition();
		double radio = Math.sqrt(partPosition.x * partPosition.x + partPosition.y * partPosition.y);
		double phi = Math.atan2(partPosition.y, partPosition.x);
		double direction = getDirection() * Math.PI / 180;
		Location location = new Location((int) (Math.round(x + radio
				* Math.cos(direction + phi))), (int) (Math.round(y + radio
				* Math.sin(direction + phi))));
		return location;
	}
	
	public boolean isTileColliding(Location posNextMoviment, double direction) {		
		boolean returnedValue = false;
		double tita = direction;
		double xNew = getLocation().getX();
		double yNew = getLocation().getY();		
		ArrayList<Location> occupiedTiles = new ArrayList<Location>();
		xNew = (xNew + sumXOffset(posNextMoviment.getX())) * Math.cos((float) tita * (Math.PI / (float) 180));
		yNew = (yNew + sumXOffset(posNextMoviment.getY())) * Math.sin((float) tita * (Math.PI / (float) 180));
		occupiedTiles.add(new Location((int) Math.round(xNew)/20, (int) Math.round(yNew)/20));
		
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
	
	private int sumXOffset(int nextCoordinate) {
		int offset = 30;
		int returnedValue = offset;
		if ((getDirection() >= 0 && getDirection() < 90) || (getDirection() > 270 && getDirection() <= 360)) {
			if (getX() - nextCoordinate < 0) {
				returnedValue = offset;
			} else {
				returnedValue = -offset;
			}
		} else if (getX() - nextCoordinate < 0) {
				returnedValue = -offset;
			} else {
				returnedValue = offset;
			}
		return returnedValue;
	}
	
	private int sumYOffset(int nextCoordinate) {
		int offset = 30;
		int returnedValue = offset;
		if (getDirection() >= 0 && getDirection() <180) {
			if (getY() - nextCoordinate < 0) {
				returnedValue = offset;
			} else {
				returnedValue = -offset;
			}
		} else if (getY() - nextCoordinate < 0) {
				returnedValue = -offset;
			} else {
				returnedValue = offset;
			}
		return returnedValue;
	}	
	
	
	/**
	 * Verifica si en la nueva posici�n pasada por par�metro el robot puede
	 * moverse sin colisionar con ning�n obst�culo.
	 * 
	 * @param x - Posici�n x del robot
	 * @param y - Posici�n y del robot
	 * @return true si es que el robot no colisionar� con ning�n obst�culo en la
	 *         nueva posici�n y false de lo contrario.
	 */
	protected boolean canMove(int x, int y, double direction) {
		Location actualPosition = getLocation();
		boolean result = false;
		if (isCollide(x, y, direction) || isPartCollide(x,y,direction)) {
			result = false;
		}
		else {
			result = true;
		}
		setLocation(actualPosition);
		return result;
	}
	
	public boolean isCollide(int x, int y, double direction) {
		boolean returnedValue = false;
		double tita = direction;
		double xNew = getX();
		double yNew = getY();	
		ArrayList<Location> occupiedTiles = new ArrayList<Location>();
		xNew = xNew + sumXOffset(x) * Math.cos((float) tita * (Math.PI / (float) 180));
		yNew = yNew + sumYOffset(y) * Math.sin((float) tita * (Math.PI / (float) 180));
		occupiedTiles.add(new Location((int) Math.round(xNew)/20, (int) Math.round(yNew)/20));

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
