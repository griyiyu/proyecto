package lejos.nxt;

import java.awt.Point;
import java.util.ArrayList;

import nxt.simulator.Obstacle;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGTileMap;
import ch.aplu.jgamegrid.Location;


public class TouchSensor extends Sensor {

//	protected static Point collisionCenter = new Point(0, -10);
	protected static int collisionRadius = 15;//9;
	
	protected boolean touchValue = false;

	public boolean isTouchValue() {
		return touchValue;
	}
	public void setTouchValue(boolean touchValue) {
		this.touchValue = touchValue;
	}
	public TouchSensor(SensorPort port) {
		super (port, "touchSensor");
		getEnvironment().addPart(this);		
		switch (port.getPortId()) {
		case 1: setCollisionCircle(new Point(0, -10), collisionRadius);
			break;
		case 2: setCollisionCircle(new Point(0, 0), collisionRadius);
			break;
		case 3: setCollisionCircle(new Point(0, 10), collisionRadius);
			break;
		case 4: setCollisionCircle(new Point(-15, 0), collisionRadius);
			break;			
		}
		addTileCollisionListener(this);
		
		this.addCollisionTiles(environment.getNxt().getCollisionTiles());
	}

	public void act() {	
		setTouchValue(false);	
	}	

	public boolean isColliding() {
		boolean returnedValue = false;
		for (Actor a : gameGrid.getActors(Obstacle.class)) {
			if (gameGrid.isActorColliding(a, this)) {
				returnedValue =  true;
			}
		}
		return returnedValue;
	}
	
	public boolean isPressed(){		
		return isTouchValue();
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
	
	private int sumXOffset(int nextCoordinate) {
		int offset = 14;
		int returnedValue = offset;
		if ((getDirection() >= 0 && getDirection() < 90) || (getDirection() > 270 && getDirection() <= 360)) {
			if (getEnvironment().getNxt().getX() - nextCoordinate < 0) {
				returnedValue = offset;
			} else {
				returnedValue = -offset;
			}
		} else if (getEnvironment().getNxt().getX() - nextCoordinate < 0) {
				returnedValue = -offset;
			} else {
				returnedValue = offset;
			}
		return returnedValue;
	}
	
	private int sumYOffset(int nextCoordinate) {
		int offset = 14;
		int returnedValue = offset;
		if (getDirection() >= 0 && getDirection() <180) {
			if (getEnvironment().getNxt().getY() - nextCoordinate < 0) {
				returnedValue = offset;
			} else {
				returnedValue = -offset;
			}
		} else if (getEnvironment().getNxt().getY() - nextCoordinate < 0) {
				returnedValue = -offset;
			} else {
				returnedValue = offset;
			}
		return returnedValue;
	}	
	
	@Override
	public int collide(Actor actor, Location location) {
		if (actor instanceof TouchSensor) {
			System.out.println("se activa el sensor");
			setTouchValue(true);
		}
		return 1;
	}	
	
}
