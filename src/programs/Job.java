package programs;

import tools.AdministratorConstants;
import lejos.nxt.ColorSensor;
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
//			pruebaTouch2();
//			pruebaTouch4();
//			pruebaUltrasonicSensor3();
//			colorSensor();
//			lineFollower();
//			new SimEx1();
//			new SimEx2();
			new SimEx3();
//			new SimEx4();
//			new SimEx5();
							
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
		TouchSensor ts = new TouchSensor(SensorPort.S4);
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
	
	
	private void pruebaTouch3() throws InterruptedException {
		TouchSensor ts = new TouchSensor(SensorPort.S4);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.backward();
			Motor.B.backward();
			
			if (ts.isPressed()) {
				Motor.A.setSpeed(300);
				Motor.B.setSpeed(300);
				Motor.A.forward();
				Motor.B.forward();
				Thread.sleep(1000);
			}
			Thread.sleep(1);
		}   
	}
	
	
	private void pruebaTouch4() throws InterruptedException {
		TouchSensor ts = new TouchSensor(SensorPort.S3);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			
			if (ts.isPressed()) {
				Motor.A.setSpeed(300);
				Motor.B.setSpeed(300);
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep(1000);
			}
			Thread.sleep(1);
		}   
	}
	
	
	private void pruebaUltrasonicSensor4() throws InterruptedException {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.backward();
			Motor.B.backward();
			
			if (us.getDistance() < 1) {
				Motor.A.setSpeed(300);
				Motor.B.setSpeed(300);
				Motor.A.forward();
				Motor.B.forward();
				Thread.sleep(500);
			}
			Thread.sleep(1);
		}   
	}
	
	
	private void pruebaUltrasonicSensor3() throws InterruptedException {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S3);
		Motor.A.stop();
		Motor.B.stop();

		while (true) {
			Motor.A.forward();
			Motor.B.forward();
			
			if (us.getDistance() < 1) {
				Motor.A.setSpeed(300);
				Motor.B.setSpeed(300);
				Motor.A.backward();
				Motor.B.backward();
				Thread.sleep(500);
			}
			Thread.sleep(1);
		}   
	}
	
	private void colorSensor() throws InterruptedException {
		ColorSensor cs = new ColorSensor(SensorPort.S2);
		//ColorSensor cs2 = new ColorSensor(SensorPort.S2);
		//ColorSensor cs3 = new ColorSensor(SensorPort.S3);
		//ColorSensor cs4 = new ColorSensor(SensorPort.S4);
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
	
	
	public void lineFollower() throws InterruptedException {

		
        float linea = 0;                // lectura del sensor de luz sobre la línea
        float suelo = 0;                // lectura del sensor de luz fuera de la línea
        float velMax=0; 				// velocidad (grados por segundo) máxima de los motores
        float kp = 0;           		// constante de proporcionalidad
        float ajuste;                   
        float error;            
        float offset;           
        
        ColorSensor lSensor = new ColorSensor(SensorPort.S2);
		Motor.A.stop();
		Motor.B.stop();

        
        //        lSensor.setFloodlight(true);
                        
        /* Parámetros a determinar antes de comenzar: */
        linea = 401;//400; //0; //600;            
        suelo = 655; //765; //240;            
        velMax=150;      
        offset  = (linea + suelo)/2;
        kp = (float)0.50; //velMax/(suelo-offset);

//        LCD.drawString("Presione ENTER", 0, 1);
//        LCD.drawString("para comenzar", 0, 2);
//
//        Button.ENTER.waitForPressAndRelease();
//
//        LCD.drawString("Presione ESCAPE", 0, 0);
//        LCD.drawString("para terminar ", 0, 1);

        while (true){
                error = (lSensor.getRawLightValue() - offset);
                ajuste = kp * error;                
        		System.out.println();
        		System.out.print("Color: " + lSensor.getRawLightValue() + " " + lSensor.getColor() + " ");
        		System.out.print(" Ajuste: " + ajuste);
        		System.out.print(" Speed A: " + Math.round(velMax + ajuste));
        		System.out.print(" Speed B: " + Math.round(velMax - ajuste));
        		System.out.print(" Offset: " + Math.round(offset));
        		System.out.print(" kp: " + kp);
        		System.out.print(" error: " + Math.round(error));
                Motor.A.setSpeed(Math.round(velMax - ajuste));
                Motor.B.setSpeed(Math.round(velMax + ajuste));
                Motor.A.forward();
                Motor.B.forward();      
        }
	}
	
	public void lineFollower2() throws Exception {
		
		ColorSensor cs = new ColorSensor(SensorPort.S2);
		Motor.A.stop();
		Motor.B.stop();
		
		while (true) {	
			System.out.println("Color: " + cs.getColor());
			if (AdministratorConstants.BLACK == cs.getColor()) {
				Motor.A.forward();
				Motor.B.forward();
			}
			else {
				Motor.A.backward();
				Thread.sleep(1000);
			}
			Thread.sleep(0);
		}
	}
	
}
