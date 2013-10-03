package programs;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import tools.AdministratorConstants;

/**
 * Ejemplo de funcionamiento del color sensor.
 */
public class SimEx4 {
  
	public SimEx4() throws InterruptedException {

		ColorSensor cs = new ColorSensor(SensorPort.S2);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			System.out.println("Color: " + cs.getColor());
//			System.out.println("Red: " + cs.getColor().getRed());
//			System.out.println("Green: " + cs.getColor().getGreen());
//			System.out.println("Blue: " + cs.getColor().getBlue());
			if (AdministratorConstants.BLACK == cs.getColor()) {
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
