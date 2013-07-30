package lejos.nxt;

import java.awt.Point;

import nxt.simulator.Obstacle;
import ch.aplu.jgamegrid.Actor;


public class TouchSensor extends Sensor {

	protected static Point collisionCenter = new Point(0, 0);
	protected static int collisionRadius = 15;//9;
	
	protected boolean touchValue = false;

	public boolean isTouchValue() {
		return touchValue;
	}
	public void setTouchValue(boolean touchValue) {
		this.touchValue = touchValue;
	}
	public TouchSensor(SensorPort port) {
		super (port, "touchSensor.png");
		getEnvironment().addPart(this);		
		addActorCollisionListener(this);
		setCollisionCircle(collisionCenter, collisionRadius);
	}

	public void act() {	
		if (isColliding()) {
			setTouchValue(true); 
		}
		else {
			touchValue = false;
		}
	}	

	public boolean isColliding() {
		for (Actor a : gameGrid.getActors(Obstacle.class)) {
			if (gameGrid.isActorColliding(a, this)) {
				return true;
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
		return false;
	}
	
	public boolean isPressed(){
		return isTouchValue();
	}
	
}
