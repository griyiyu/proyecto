package nxt.simulator.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

	private JPanel contentPane;
	private final JDesktopPane desktopPane = new JDesktopPane();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
	public MainFrame() {
		setTitle("Entorno de Simulaci\u00F3n MINDSTORMS");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/sprites/Brick.png")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 929, 510);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnEntorno = new JMenu("Entorno");
		menuBar.add(mnEntorno);
		
		JMenuItem mntmCrearEntornoCon = new JMenuItem("Crear entorno");
		mntmCrearEntornoCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnvironmentUI pep = new EnvironmentUI();
				desktopPane.add(pep);
				pep.setLocation(MAXIMIZED_HORIZ, MAXIMIZED_VERT);
				pep.setVisible(true);
				pep.getEnvironment().addNXT();
			}
		});
		mnEntorno.add(mntmCrearEntornoCon);
		
		JMenuItem mntmCargarEntorno = new JMenuItem("Cargar entorno");
		mntmCargarEntorno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnvUI pep = new EnvUI();
				desktopPane.add(pep);
				pep.setLocation(MAXIMIZED_HORIZ, MAXIMIZED_VERT);
				pep.setVisible(true);
				//pep.getEnvironment().addNXT();
			}
		});
		mnEntorno.add(mntmCargarEntorno);
		
		
		JMenu mnRobotNxt = new JMenu("Robot NXT");
		menuBar.add(mnRobotNxt);
		
		JMenuItem mntmDefinirPuertosPosicin = new JMenuItem("Definir puertos, posici\u00F3n y direcci\u00F3n");
		mnRobotNxt.add(mntmDefinirPuertosPosicin);
		
		JMenu mnSimulacin = new JMenu("Simulaci\u00F3n");
		menuBar.add(mnSimulacin);
		
		JMenuItem mntmCargarCdigoLejos = new JMenuItem("Cargar c\u00F3digo LEJOS");
		mnSimulacin.add(mntmCargarCdigoLejos);
		
		JMenuItem mntmIniciarSimulacin = new JMenuItem("Iniciar simulaci\u00F3n");
		mnSimulacin.add(mntmIniciarSimulacin);
		mntmIniciarSimulacin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimulationUI simulationUI = new SimulationUI();
				desktopPane.add(simulationUI);
				simulationUI.setLocation(MAXIMIZED_HORIZ, MAXIMIZED_VERT);
				simulationUI.setVisible(true);
			}
		});			
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		desktopPane.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		/*
		BufferedImage image = null;
		try {
			File file = new File("sprites/Todo.jpg");
			image = ImageIO.read(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		desktopPane.setBorder(new BorderImage(image));
		*/
		contentPane.add(desktopPane, BorderLayout.CENTER);	
	}

}
