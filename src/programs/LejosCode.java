package programs;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.Color;

public class LejosCode {
	
	public LejosCode() throws InterruptedException {
		lineaRoja();
//		chocador();
//		esquivar();	
//		seguirUnCuadrado();
//		new SimEx1();
//		new SimEx2();
//		new SimEx3();
//		new SimEx4();
//		new SimEx5();
	}

	private void lineaRoja() throws InterruptedException {
		ColorSensor cs = new ColorSensor(SensorPort.S2);
		Motor.A.setSpeed(500);
		Motor.B.setSpeed(500);

		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			if (Color.RED == cs.getColor()) {
			//if (Color.RED == cs.getColor()) {
				Motor.B.stop();
				Thread.sleep(7000);
			}
			Thread.sleep(0);
			Motor.B.setSpeed(500);
			Motor.B.forward();
		}
	}

	private void esquivar() throws InterruptedException {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		Motor.A.setSpeed(500);
		Motor.B.setSpeed(500);

		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			while (us.getDistance() < 15) {
				Motor.B.stop();
				Thread.sleep(1500);
			}
			Thread.sleep(0);
			Motor.B.setSpeed(500);
			Motor.B.forward();
		}
	}

	private void chocador() throws InterruptedException {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		TouchSensor ts = new TouchSensor(SensorPort.S4);
		Motor.A.setSpeed(500);
		Motor.B.setSpeed(500);

		while (true) {
			while (us.getDistance() > 15) {
				Motor.A.forward();
				Motor.B.forward();
			}
			while (!ts.isPressed()) {
				Motor.A.backward();
				Motor.B.backward();
			}
		}
	}

	private void seguirUnCuadrado() throws InterruptedException {
		ColorSensor cs = new ColorSensor(SensorPort.S2);
		Motor.A.setSpeed(500);
		Motor.B.setSpeed(500);

		while (true) {
			while (Color.BLACK == cs.getColor()) {
				Motor.A.forward();
				Motor.B.forward();
				// if (Color.RED == cs.getColor()) {
				// Motor.B.stop();
				// Thread.sleep(7000);
			}
			Motor.A.backward();
			Motor.B.backward();
			Thread.sleep(500);
			Motor.A.forward();
			Motor.B.forward();
			Motor.A.stop();
			Thread.sleep(1000);
			// Thread.sleep(0);
			Motor.A.setSpeed(500);
			Motor.A.forward();
		}
	}
	
	
	}
