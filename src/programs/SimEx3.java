package programs;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/**
 * Ejemplo de funcionamiento del ultrasonic sensor.
 */
public class SimEx3 {
  
	public SimEx3() throws InterruptedException {

		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			
			if (us.getDistance() < 2) {
				Motor.A.setSpeed(200);
				Motor.B.setSpeed(200);
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep(3500);
			}
			Thread.sleep(0);
		}    		
	}

}
