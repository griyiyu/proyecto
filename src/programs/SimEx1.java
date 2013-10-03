package programs;

import lejos.nxt.Motor;

/**
 * Ejemplo de los distintos moviemientos del robot, 
 * variando las velocidades de cada motor de movimiento.
 */
public class SimEx1 {
	
	public SimEx1() throws InterruptedException {
		Motor.A.stop();
		Motor.B.stop();
		Thread.sleep(1000);		
		
		//Adelante misma der mas rapido
		    Motor.A.setSpeed(500);
		    Motor.B.setSpeed(250);
		    Motor.A.forward();
		    Motor.B.forward();
		    
		    Thread.sleep(8000);
		    
		    Motor.A.stop();
			Motor.B.stop();
		
		  
		//Adelante misma izq mas rapido
		    Motor.A.setSpeed(250);
		    Motor.B.setSpeed(500);

		    Motor.A.forward();
		    Motor.B.forward();
		    
		    Thread.sleep(8000);
		    
		    Motor.A.stop();
			Motor.B.stop();
		    
			
		//Adelante der parado
		    Motor.A.setSpeed(250);
		    Motor.B.setSpeed(500);

		    Motor.A.stop();
		    Motor.B.forward();
		    Thread.sleep(8000);
		    
		    Motor.A.stop();
			Motor.B.stop();
		
		  
		//Adelante izq parado
		    Motor.A.setSpeed(250);
		    Motor.B.setSpeed(500);

		    Motor.A.forward();
		    Motor.B.stop();
		    
		    Thread.sleep(8000);
		    
		    Motor.A.stop();
			Motor.B.stop();
		
			  
		//Atras misma velocidad    
		    Motor.A.setSpeed(200);
		    Motor.B.setSpeed(200);

		    Motor.A.backward();
		    Motor.B.backward();
		    
		    Thread.sleep(4000);
		    
		    Motor.A.stop();
			Motor.B.stop();
		
		 
		//Atras misma der mas rapido
		    Motor.A.setSpeed(500);
		    Motor.B.setSpeed(250);

		    Motor.A.backward();
		    Motor.B.backward();
		    
		    Thread.sleep(8000);
		
		    Motor.A.stop();
			Motor.B.stop();
		
			
		  //Adelante misma velocidad    
		    Motor.A.setSpeed(500);
		    Motor.B.setSpeed(500);

		    Motor.A.forward();
		    Motor.B.forward();
		    
		    Thread.sleep(5000);  
		    
		    Motor.A.stop();
			Motor.B.stop();
		
	}
  
}
