package tools;

import lejos.nxt.ColorSensor;
import lejos.nxt.Sensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;


public enum SensorEnum {
	No_Sensor(0), TouchSensor(1), UltrasonicSensor(2), ColorSensor(3);
	
	private Sensor sensor;
	
	private SensorEnum(int value) {
		switch (value) {
		case 0: 
			sensor = null;
			break;
		case 1:
			sensor = null;//new TouchSensor(SensorPort.S1);
			break;
		case 2:
			sensor = null;//new UltrasonicSensor(SensorPort.S1);
			break;
		case 3:
			sensor = null;//new ColorSensor(SensorPort.S1);
			break;
		}
	}
	
	public Sensor getSensor() {
		return sensor;
	}

}
