package nxt.simulator;

import javax.swing.JOptionPane;
import javax.swing.plaf.InternalFrameUI;

public class Job implements Runnable {
	
	/**
	 * Nombre de la clase externa que contiene el código LeJOS con el comportamiento del robot para la simulación
	 */
	private final String lejosClassName;
	
	private final InternalFrameUI internalFrameUI;
	
	public Job(String lejosClassName, InternalFrameUI internalFrameUI) {
		this.lejosClassName = lejosClassName;
		this.internalFrameUI = internalFrameUI;
	}

    public void run() {
		System.out
				.println("Nombre de la clase LeJOS con la cual se ejecutará el simulador: "
						+ lejosClassName);
		try {
			// Se instancia la clase que contiene el código LeJOS.
			Class.forName(lejosClassName).newInstance();
			
		} catch (InstantiationException ie) {
			System.out.println("InstantiationException: " + ie.getMessage());
			JOptionPane.showInputDialog(internalFrameUI, "No se puede instanciar la clase " + lejosClassName);
			//ie.printStackTrace();
		} catch (IllegalAccessException iae) {
			System.out.println("IllegalAccessException: " + iae.getMessage());
			//iae.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException: " + cnfe.getMessage());
			//cnfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
