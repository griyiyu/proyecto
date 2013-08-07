package nxt.simulator.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import ch.aplu.jgamegrid.GGMouse;

import nxt.simulator.Environment;
import nxt.simulator.EnvironmentTest;

public class SimulationUI extends JInternalFrame{
	
	protected Environment environment;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimulationUI frame = new SimulationUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SimulationUI() {
		setMinimumSize(new Dimension(400, 600));
		setClosable(true);
		setMaximizable(true);
		setResizable(true);
		setBounds(100, 100, 1048, 674);
		
		// Se crea el panel central y el environment
		JPanel centerPanel = new JPanel();
		environment = new EnvironmentTest();
//		environment.addMouseListener(environment, GGMouse.lClick
//				| GGMouse.lDrag | GGMouse.lPress | GGMouse.lRelease 
//				| GGMouse.rPress);
		centerPanel.add(environment, BorderLayout.CENTER);
		
	}
	
}
