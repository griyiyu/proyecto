package lejos.nxt;

import java.awt.Point;

import nxt.simulator.Obstacle;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;


public class TouchSensor extends Sensor {

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
		case 4: setCollisionCircle(new Point(0, 0), collisionRadius);
			break;			
		}
		addTileCollisionListener(this);
		
		this.addCollisionTiles(environment.getNxt().getCollisionTiles());
	}

	public void act() {	
		if (isColliding()) {
			setTouchValue(true); 
		}
		else {
			setTouchValue(false);
		}
	}	
/*
	public boolean isColliding() {
		for (Actor obstacle : gameGrid.getActors(Obstacle.class)) {
			if (gameGrid.isActorColliding(obstacle, this)) {
				return true;
			}
		}
		return false;
	}
*/
	
	public boolean isColliding() {
		for (Actor obstacle : getEnvironment().getActors(Obstacle.class)) {
			if (getEnvironment().isActorColliding(obstacle, this)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPressed(){
		return isTouchValue();
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
