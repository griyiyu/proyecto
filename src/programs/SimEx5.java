package programs;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import tools.AdministratorConstants;

/**
 * Ejemplo de funcionamiento del color sensor con fondo de color.
 */
public class SimEx5 {
  
	public SimEx5() throws InterruptedException {

		ColorSensor cs = new ColorSensor(SensorPort.S2);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			Motor.A.forward();
			Motor.B.forward();
			if (AdministratorConstants.WHITE == cs.getColor()) {
				Motor.A.setSpeed(400);
				Motor.B.setSpeed(350);
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep(2500);
			}
			Thread.sleep(0);
		}    		
	}

}
