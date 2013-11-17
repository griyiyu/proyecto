/**
 * Clase de la ventana principal de la aplicaci�n
 * 
 * @author Griselda Arias
 */
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

import tools.AdministratorConstants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private final static JDesktopPane desktopPane = new JDesktopPane();

	/**
	 * Abre la aplicaci�n
	 * 
	 * @param args
	 *            Arreglo de argunmentos que contendr� un String (lejosClassName)
	 *            detallando el nombre de la clase externa que contiene el
	 *            c�digo LeJOS con el comportamiento del robot para la
	 *            simulaci�n
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(args[0]);
					frame.setVisible(true);
					initializeSimulationEnvironment(args[0]);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea la ventana principal de la aplicaci�n y la ventana del ambiente de simulaci�n
	 * 
	 * @param lejosClassName
	 *            Nombre de la clase externa que contiene el c�digo LeJOS con el
	 *            comportamiento del robot para la simulaci�n
	 */
	public MainFrame(final String lejosClassName) {
		// Titulo de la aplicaci�n
		setTitle("Entorno de Simulaci\u00F3n MINDSTORMS");
		// Icono de la aplicaci�n
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainFrame.class.getResource("/"
						+ AdministratorConstants.IMAGE_PATH + "RobotCar.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 929, 510);
		// Barra para el men� de la aplicaci�n
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// Men� principal de la palicaci�n
		JMenu mnSimulator = new JMenu("Simulador");
		menuBar.add(mnSimulator);
		// Item del men� principal que inicia el entorno de simulaci�n
		JMenuItem mntmInitializeSimulator = new JMenuItem("Iniciar simulador");
		mntmInitializeSimulator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeSimulationEnvironment(lejosClassName);
			}
		});
		mnSimulator.add(mntmInitializeSimulator);
		// Panel que contiene a la aplicaci�n
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		desktopPane.setBackground(UIManager
				.getColor("InternalFrame.activeTitleBackground"));
		contentPane.add(desktopPane, BorderLayout.CENTER);
	}

	/**
	 * Instancia e inicia la ventana que contiene el entorno de simulaci�n y el
	 * robot NXT.
	 * 
	 * @param lejosClassName
	 *            Nombre de la clase externa que contiene el c�digo LeJOS con
	 *            el comportamiento del robot para la simulaci�n
	 */
	protected static void initializeSimulationEnvironment(String lejosClassName) {
		// Crea el entorno de simulaci�n
		EnvironmentUI simulationEnvironment = new EnvironmentUI(lejosClassName);
		// Se agrega el entorno de simulaci�n al panel
		desktopPane.add(simulationEnvironment);
		simulationEnvironment.setLocation(MAXIMIZED_HORIZ, MAXIMIZED_VERT);
		simulationEnvironment.setVisible(true);
		// Agrega el robot NXT al entorno de simulaci�n
		simulationEnvironment.getEnvironment().addNXT();
	}

}
