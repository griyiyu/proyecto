package lejos.nxt;

import java.awt.Point;

import nxt.simulator.Obstacle;
import nxt.simulator.Part;

import lejos.nxt.SensorPort;

import tools.AdministratorConstants;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;


public class UltrasonicSensor extends Sensor {

	private static Point collisionCenter = new Point(0, 0);
	private static int collisionRadius = 12;	

	private Part aux;
	private int ultrasonicValue = 255;

	
	public UltrasonicSensor(SensorPort port) {
		super(port, "UltrasonicSensor", 1);

		environment.addPart(this);		
		addActorCollisionListener(this);
		setCollisionCircle(collisionCenter, collisionRadius);
	}
	
	public Part ultrasonicAux() {
		return new Part(AdministratorConstants.IMAGE_PATH + "UltrasonicSensor"
				+ new Integer(port.getPortId()).toString() + ".png",
				port == SensorPort.S1 ? pos1 : (port == SensorPort.S2 ? pos2
						: (port == SensorPort.S3 ? pos3 : pos4)));
	}

	public void act() {
		ultrasonicValue = collideDistance();
	}	

	/**
	 * 
	 * Retorna la distancia en cm desde el ultrasonicSensor hasta un obstaculo o el limite de la grilla, 
	 * el calculo lo realiza en la direcci�n que est� apuntando. 
	 */
	public int collideDistance() {

		double tita = getDirection();
		double xInit = getX();
		double yInit = getY();
		double xNew = getX();
		double yNew = getY();
		boolean colliding = false;
		int distance = 255;
		int traveledDistance = 0;
		
		try {
			getAux().setDirection(this.getDirection());
			getAux().setLocation(this.getLocation());
			getAux().show();
		} catch (NullPointerException npe) {
			
		}
		//Si no hay obstaculos entonces devuelve el valor por defecto.
		if (!(gameGrid.getActors(Obstacle.class).size() > 0)) {
			return distance;
		}
		while (!colliding){			
			//Revisa si est� colisionando y devuelve la distancia en caso de estar colisionando.
			for (Actor a : gameGrid.getActors(Obstacle.class)) {
				if (gameGrid.isActorColliding(a, aux) || !(aux.isMoveValid())) {
					distance = (int)Math.round((Math.sqrt(Math.pow((xNew-xInit), 2)+ Math.pow((yNew-yInit), 2))));
					colliding = true;
				}
			}
			if (!colliding) {
				traveledDistance = traveledDistance + 5;
				
				// Calcula la posici�n x del robot, se incrementa 1 pixel en x en la direccion del sensor.
				xNew = xNew + 5 * Math.cos((float)tita * (Math.PI / (float)180));
				// Calcula la posici�n y del robot, se incrementa 1 pixel en y en la direccion del sensor.
				yNew = yNew + 5 * Math.sin((float)tita * (Math.PI / (float)180));
				int xPosition = (int)Math.round(xNew);
				int yPosition = (int)Math.round(yNew);
				// Se asigna la nueva posici�n a la parte auxiliar para el calculo de distancia.
				getAux().setLocation(new Location(xPosition,yPosition));
			}
		}	
		getAux().hide();
		//5 pixels = 1 cm.
		distance /= 5;
		return distance >= 170 ? 255 : distance;
	}
	
	/**
	 * Retorna la distancia en cm desde el ultrasonicSensor hasta un obstaculo o el limite de la grilla.
	 * @return
	 */
	public int getDistance(){
		return ultrasonicValue;
	}

	public Part getAux() {
		if (aux == null) {
			//Se instancia un actor del mismo tama�o del sensor para realizar los calculos auxiliares.
			aux = ultrasonicAux();
			addActorCollisionListener(aux);
			aux.hide();
			getEnvironment().addPart(aux);
		}
		return aux;
	}

	public void setAux(Part aux) {
		this.aux = aux;
	}	
}
