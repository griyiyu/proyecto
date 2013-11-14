package programs;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.Color;
import tools.AdministratorConstants;

/**
 * Ejemplo de funcionamiento de los tres sensores.
 */
public class SimEx5 {
  
	public SimEx5() throws InterruptedException {
		// Se instancian los sensores
		ColorSensor cs = new ColorSensor(SensorPort.S1);
		TouchSensor ts = new TouchSensor(SensorPort.S2);
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		// Se setea la velocidad inicial de los motores
		Motor.A.setSpeed(500);
		Motor.B.setSpeed(500);
		// Se define el comportamiento del robot
		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			if (Color.RED == cs.getColor()) {
				Motor.B.stop();
				Thread.sleep(7000);
			}
			if (ts.isPressed()) {
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep(3500);
			}
			if (us.getDistance() < 15) {
				Motor.B.stop();
				Thread.sleep(1500);
			}
			Thread.sleep(0);
			Motor.B.forward();
		}  		
	}

}
