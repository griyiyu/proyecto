package lejos.nxt;

import java.awt.Point;

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
		//TODO: Quitar esto una vez que se unan las clases Obstacle y ObstacleClick
		/*
		for (Actor a : gameGrid.getActors(ObstacleClick.class)) {
			if (gameGrid.isActorColliding(a, this)) {
				return true;
			}
		}
		*/
		return returnedValue;
	}
	
	public boolean isPressed(){
		return isTouchValue();
	}
	
	@Override
	public int collide(Actor actor, Location location) {
		if (actor instanceof TouchSensor) {
			setTouchValue(true);
		}
		return 1;
	}	
	
}
