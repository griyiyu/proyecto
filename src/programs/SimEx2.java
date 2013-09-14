package programs;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import nxt.simulator.EnvironmentTest;
import nxt.simulator.Obstacle;

/**
 * Ejemplo de funcionamiento del touch sensor.
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
		Obstacle o = new Obstacle("bar2.gif");
		e.addObstacle(o, 250, 400);
		new SimEx2();
	}
	
}
