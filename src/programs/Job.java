package programs;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import nxt.simulator.EnvironmentTest;

public class Job implements Runnable {
	
	private EnvironmentTest env;
	
    public EnvironmentTest getEnv() {
		return env;
	}

	public void setEnv(EnvironmentTest env) {
		this.env = env;
	}
	
	public Job(EnvironmentTest env) {
		setEnv(env);
	}

	@Override
    public void run() {
         // Do some stuff
		try {

//		TouchSensor ts = new TouchSensor(SensorPort.S2);
		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			Thread.sleep(3500);
//			if (ts.isPressed()) {			
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep(3500);
//			}
		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
    }
}
