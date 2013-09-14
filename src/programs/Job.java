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

    public void run() {
		try {
//			pruebaTouch();
//			pruebaUltrasonicSensor();
//			adelante();
//			atras();
			pruebaTouch2();
							
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
    }

	private void adelante() {
		while (true) {
			Motor.A.forward();
			Motor.B.forward();
		}
		
	}
	
	private void atras() {
		while (true) {
//			env.refresh();
			Motor.A.setSpeed(500);
			Motor.B.setSpeed(500);
			Motor.A.backward();
			Motor.B.backward();
			
		}
		
	}

	private void pruebaUltrasonicSensor() throws InterruptedException {
/*		
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			
			if (us.getDistance() < 15) {
				Motor.A.setSpeed(200);
				Motor.B.setSpeed(200);
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep(3500);
			}
			Thread.sleep(0);
		}    	
*/		
		//Test for ultrasonic Sensor	

		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		while (true) {
			Motor.A.forward();
			Motor.B.forward();
//			Thread.sleep(3500);
			if (us.getDistance() < 15) {
				Motor.A.backward();
				Thread.sleep(1000);
			/*} else {
				Motor.A.forward();
*/
			}
			Thread.sleep(0);
		}

		
	}

	/**
	 * Este método se utiliza para probar el TouchSensor
	 * @throws InterruptedException 
	 */
	private void pruebaTouch() throws InterruptedException {
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
		
	}
	
	
	/**
	 * Este método se utiliza para probar el TouchSensor
	 * @throws InterruptedException 
	 */
	private void pruebaTouch2() throws InterruptedException {
		TouchSensor ts = new TouchSensor(SensorPort.S2);
		
		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			if (ts.isPressed()) {
				Motor.A.backward();
				Thread.sleep(1000);
			}
			Thread.sleep(0);
		}

	}
}
