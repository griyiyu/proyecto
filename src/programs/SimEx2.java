package programs;

import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import nxt.simulator.EnvironmentTest;
import nxt.simulator.Obstacle;

/**
 * 
 * @author smangioni
 * Este es un ejemplo de funcionamiento del touch sensor.
 *
 */
public class SimEx2 {
  
	public SimEx2() throws InterruptedException {
	
		TouchSensor ts = new TouchSensor(SensorPort.S2);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			
			if (ts.isPressed()) {
				Motor.A.setSpeed(200);
				Motor.B.setSpeed(200);
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep(3500);
			}
			Thread.sleep(1);
		}   
  }

	public static void main(String[] args) throws InterruptedException {

		EnvironmentTest e = new EnvironmentTest();
//		Obstacle o = new Obstacle("brick.gif");
//	    o.addMouseTouchListener(o,
//	    	      GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease);		
//		e.addObstacle(o, 1, 1);
//		e.getTileMap().setImage("sprites/brick.gif", 29, 5);
//		e.getTileMap().setTileCollisionEnabled(new Location(29,5), false);
//		e.getTileMap().setImage("sprites/brick.gif", 29, 10);
//		e.getTileMap().setTileCollisionEnabled(new Location(29,10), false);
//		e.getTileMap().setImage("sprites/brick.gif", 29, 11);
//		e.getTileMap().setTileCollisionEnabled(new Location(29,11), false);
//		e.getTileMap().setImage("sprites/brick.gif", 29, 12);
//		e.getTileMap().setTileCollisionEnabled(new Location(29,12), false);
//		e.getTileMap().setImage("sprites/brick.gif", 29, 13);
//		e.getTileMap().setTileCollisionEnabled(new Location(29,13), false);
//		e.getTileMap().setImage("sprites/brick.gif", 29, 14);
//		e.getTileMap().setTileCollisionEnabled(new Location(29,14), false);
//		e.addMouseTouchListener(e,
//	      GGMouse.lClick);
		e.addMouseListener(e, GGMouse.lClick | GGMouse.lDrag);
		
		new SimEx2();
	}
	
}
