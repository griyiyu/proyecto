package nxt.simulator;

import lejos.nxt.Sensor;
import nxt.simulator.UI.EnvironmentUI;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class EnvironmentTest extends EnvironmentConfiguration {

	private static final long serialVersionUID = -1719751487389340621L;

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
	
	public void act(){
		for (Actor a : getActors(Sensor.class)) {
			a.addCollisionActors(getActors(Obstacle.class));
		}
	}

}
