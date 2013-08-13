package nxt.simulator;

import java.awt.Color;

import lejos.nxt.Sensor;
import nxt.simulator.UI.EnvironmentUI;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGTileMap;
import ch.aplu.jgamegrid.Location;

public class EnvironmentTest extends Environment {
	
	public EnvironmentTest()
	{
		super();		
	}
	
	public EnvironmentTest(EnvironmentUI ui) {
		super();
		environmentUI = ui;
	}		
	
	public EnvironmentTest(Location startLocation, double startDirection)
	{
		super(startLocation, startDirection);	
	}

	@Override
	public int collide(Actor actor, Location location) {
		// TODO Auto-generated method stub
		return 0;
	}
	
//	public void act(){
//		for (Actor a : getActors(Sensor.class)) {
//			a.addCollisionActors(getActors(Obstacle.class));
//		}
//	}
}
