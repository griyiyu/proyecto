package programs;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;

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
		try {
			
			
//			TouchSensor us = new TouchSensor(SensorPort.S2);
//			TouchSensor ts = new TouchSensor(SensorPort.S2);
			while (true) {
				Motor.A.forward();
				Motor.B.forward();
//				Thread.sleep(3500);
//				if (us.getDistance() < 1) {
//						Motor.A.backward();
//						Motor.B.backward();						
//						Thread.sleep(1500);
//				}
			}


/*			
			//Test for ultrasonic Sensor	
			UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
			while (true) {
				Motor.A.forward();
				Motor.B.forward();
//				Thread.sleep(3500);
				if (us.getDistance() < 20) {
						Motor.A.backward();
						Thread.sleep(1500);
						Motor.A.forward();
				}
			}
*/			
		//Test for touch Sensor	
/*
		TouchSensor ts2 = new TouchSensor(SensorPort.S1);
		TouchSensor ts = new TouchSensor(SensorPort.S2);		
		TouchSensor ts3 = new TouchSensor(SensorPort.S3);
		TouchSensor ts1 = new TouchSensor(SensorPort.S4);
		while (true) {
			Motor.A.forward();
			Motor.B.forward();
//			Thread.sleep(3500);
			if (ts.isPressed()) {
				while (!ts1.isPressed()){
					Motor.A.backward();
					Motor.B.backward();
				}
			}
		}
*/		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
    }
}
