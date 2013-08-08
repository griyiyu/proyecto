package lejos.nxt;

import java.awt.Point;
import java.util.ArrayList;

import nxt.simulator.Obstacle;
import nxt.simulator.Part;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class UltrasonicSensor extends Sensor {

	private static Point collisionCenter = new Point(0, 0);
	private static int collisionRadius = 0;

	private Part aux;
	private int ultrasonicValue = 255;
	
	private boolean collide = false;

	public UltrasonicSensor(SensorPort port) {
		super(port, "UltrasonicSensor", 1);

		getEnvironment().addPart(this);
		setCollisionCircle(collisionCenter, collisionRadius);
		addCollisionActors(environment.getActors(Obstacle.class));
		addTileCollisionListener(this);
		this.addCollisionTiles(environment.getNxt().getCollisionTiles());
	}

	public void act() {		
		ultrasonicValue = findObstacle();
	}
	
	public int findObstacle(){
		
		double tita = getDirection();
		double xInit = getX();
		double yInit = getY();
		double xNew = getX();
		double yNew = getY();
		boolean colliding = false;
		int pixelsAdvanced = 0;
		int distance = 255;	
		Location lastLocation = new Location(getX(), getY());


		ArrayList<Actor> obstacles = gameGrid.getActors(Obstacle.class);	
		while (!colliding) {
			// Revisa si está colisionando y devuelve la distancia en caso de
			// estar colisionando.			
//			if (gameGrid.isTileColliding((Actor)this, new Location(Math.round(getLocation().getX()/20),Math.round(getLocation().getY()/20))) || !(isMoveValid())) {
//				distance = (int) Math.round((Math.sqrt(Math.pow(
//				(xNew - xInit), 2) + Math.pow((yNew - yInit), 2))));				
//				colliding = true;
//			}	
			if (isTileColliding(new Location(Math.round(getLocation().getX()),Math.round(getLocation().getY()))) || !(isMoveValid())) {
				distance = (int) Math.round((Math.sqrt(Math.pow(
				(xNew - xInit), 2) + Math.pow((yNew - yInit), 2))));				
				colliding = true;
			}				
			// Calcula la posición x del robot, se incrementa 1 pixel en x en la
			// direccion del sensor.
			xNew = xNew + 1 * Math.cos((float) tita * (Math.PI / (float) 180));
			// Calcula la posición y del robot, se incrementa 1 pixel en y en la
			// direccion del sensor.
			yNew = yNew + 1 * Math.sin((float) tita * (Math.PI / (float) 180));
			int xPosition = (int) Math.round(xNew);
			int yPosition = (int) Math.round(yNew);
			// Se asigna la nueva posición a la parte auxiliar para el calculo
			// de distancia.
			setLocation(new Location(xPosition, yPosition));				
			pixelsAdvanced = pixelsAdvanced + 1;
		}	
		
		//Si se avanzo solo un px entonces el sensor está chocando
		if (pixelsAdvanced > 1) {
			setLocation(lastLocation);				
		}		
		// 5 pixels = 1 cm.
		distance /= 5;
		System.out.println(distance);
		return distance >= 170 ? 255 : distance;
	}

	public boolean isTileColliding(Location posNextMoviment) {		
		boolean returnedValue = false;
		ArrayList<Location> occupiedTiles = new ArrayList<Location>();
		//Center cells
//		occupiedTiles.add(new Location(getLocation().getX()/20, (getLocation().getY() - 27)/20));
		occupiedTiles.add(new Location(getLocation().getX()/20, (getLocation().getY() + 10)/20));
		//Left cells
//		occupiedTiles.add(new Location((getLocation().getX() - 20)/20, (getLocation().getY() - 27)/20));
//		occupiedTiles.add(new Location((getLocation().getX() - 20)/20, (getLocation().getY() + 27)/20));
		//Right cells
//		occupiedTiles.add(new Location((getLocation().getX() + 20)/20, (getLocation().getY() - 27)/20));
//		occupiedTiles.add(new Location((getLocation().getX() + 20)/20, (getLocation().getY() + 27)/20));		
		
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
	
	/**
	 * 
	 * Retorna la distancia en cm desde el ultrasonicSensor hasta un obstaculo o
	 * el limite de la grilla, el calculo lo realiza en la dirección que está
	 * apuntando.
	 */
	public int collideDistance() {

		double tita = getDirection();
		double xInit = getX();
		double yInit = getY();
		double xNew = getX();
		double yNew = getY();
		boolean colliding = false;
		int distance = 255;

		ArrayList<Actor> obstacles = gameGrid.getActors(Obstacle.class);
		
		aux.setDirection(this.getDirection());
		aux.setLocation(this.getLocation());

		aux.show();
		while (!colliding) {
			// Revisa si está colisionando y devuelve la distancia en caso de
			// estar colisionando.
			for (Actor a : obstacles) {
				if (gameGrid.isActorColliding(a, aux) || !(aux.isMoveValid())) {
					distance = (int) Math.round((Math.sqrt(Math.pow(
							(xNew - xInit), 2) + Math.pow((yNew - yInit), 2))));
					colliding = true;
					break;
				}
			}
			// Calcula la posición x del robot, se incrementa 1 pixel en x en la
			// direccion del sensor.
			xNew = xNew + 1 * Math.cos((float) tita * (Math.PI / (float) 180));
			// Calcula la posición y del robot, se incrementa 1 pixel en y en la
			// direccion del sensor.
			yNew = yNew + 1 * Math.sin((float) tita * (Math.PI / (float) 180));
			int xPosition = (int) Math.round(xNew);
			int yPosition = (int) Math.round(yNew);
			// Se asigna la nueva posición a la parte auxiliar para el calculo
			// de distancia.
			aux.setLocation(new Location(xPosition, yPosition));
		}
		aux.hide();
		// 20 pixels = 1 cm.
		distance /= 20;
		return distance >= 170 ? 255 : distance;
	}

	/**
	 * Retorna la distancia en cm desde el ultrasonicSensor hasta un obstaculo o
	 * el limite de la grilla.
	 * 
	 * @return
	 */
	public int getDistance() {
		return ultrasonicValue;
	}

	@Override
	public int collide(Actor a1, Actor a2)
	{
	  if (a1 instanceof UltrasonicSensor){
		  collide = true;
	  }
	  return 0;
	}
}
