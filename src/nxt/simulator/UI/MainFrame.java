package nxt.simulator.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final static JDesktopPane desktopPane = new JDesktopPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					initializeSimulationEnvironment();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Entorno de Simulaci\u00F3n MINDSTORMS");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				//MainFrame.class.getResource("/sprites/Brick.png")));
				MainFrame.class.getResource("/sprites/RobotCar.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 929, 510);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnSimulator = new JMenu("Simulador");
		menuBar.add(mnSimulator);

		JMenuItem mntmInitializeSimulator = new JMenuItem("Iniciar simulador");
		mntmInitializeSimulator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeSimulationEnvironment();
			}
		});
		mnSimulator.add(mntmInitializeSimulator);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		desktopPane.setBackground(UIManager
				.getColor("InternalFrame.activeTitleBackground"));

		contentPane.add(desktopPane, BorderLayout.CENTER);
	}

	/**
	 * Instancia e inicia la ventana que contiene el entorno de simulación y 
	 * el robot NXT.
	 */
	protected static void initializeSimulationEnvironment() {
		// Crea el entorno de simulación
		EnvironmentUI simulationEnvironment = new EnvironmentUI();
		// Se agrega el entorno de simulación al panel
		desktopPane.add(simulationEnvironment);
		simulationEnvironment.setLocation(MAXIMIZED_HORIZ, MAXIMIZED_VERT);
		simulationEnvironment.setVisible(true);
		// Agrega el robot NXT al entorno de simulación
		simulationEnvironment.getEnvironment().addNXT();
	}

}
