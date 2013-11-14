package nxt.simulator.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import nxt.simulator.Environment;
import nxt.simulator.EnvironmentConfiguration;
import nxt.simulator.EnvironmentTest;
import nxt.simulator.Job;
import nxt.simulator.persistance.EnvironmentConfigurationDao;
import tools.AdministratorConstants;
import tools.EnvironmentActions;
import tools.EnvironmentColors;
import ch.aplu.jgamegrid.GGMouse;

public class EnvironmentUI extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Entorno de simulación que contiene los obstáculos, el robot y los sensores
	 */
	protected Environment environment;
	
	/**
	 * Código lejos con el cual se moverá el robot
	 */
	private Thread job = null;

	/**
	 * Botones para rotar el robot
	 */
	JButton btnMoveLeft = new JButton();;
	JButton btnMoveRight = new JButton();;
	
	/**
	 * Create the frame.
	 */
	public EnvironmentUI() {
		
		setMinimumSize(new Dimension(400, 600));
		setClosable(true);
		setMaximizable(true);
		setResizable(true);
		setAutoscrolls(true);
		setBounds(100, 100, 1048, 674);

		// Se crea el panel central y el environment
		JPanel centerPanel = new JPanel();
		environment = new EnvironmentTest(this);
		environment.addMouseListener(environment, GGMouse.lClick
				| GGMouse.lDrag | GGMouse.lPress | GGMouse.lRelease);
//				| GGMouse.rPress);
		centerPanel.add(environment, BorderLayout.CENTER);
	
		// Se crea el panel de configuración del ambiente y sus componentes
		final ButtonGroup group = new ButtonGroup();
		JPanel envConfigPanel = createEnvConfigPanel(group);
		
		// Se define el layout para los paneles de botones
		FlowLayout experimentLayout = new FlowLayout();
		experimentLayout.setAlignment(FlowLayout.CENTER);
		
		// Se crea el panel de configuración del robot
		JPanel nxtConfigPanel = new JPanel();
		nxtConfigPanel.setBorder(new TitledBorder("Rotación del robot NXT"));
		nxtConfigPanel.setLayout(experimentLayout);
		btnMoveRight.setIcon(new ImageIcon(EnvironmentUI.class.getResource("/"
				+ AdministratorConstants.IMAGE_PATH + "aRnxt.png")));
		btnMoveRight.setToolTipText("Rota la dirección del robot 15° hacia la derecha.");
		btnMoveLeft.setIcon(new ImageIcon(EnvironmentUI.class.getResource("/"
				+ AdministratorConstants.IMAGE_PATH + "aLnxt.png")));
		btnMoveLeft.setToolTipText("Rota la dirección del robot 15° hacia la izquierda.");
		// Se agregan los botones para rotar el robot al panel
		nxtConfigPanel.add(btnMoveRight);
		nxtConfigPanel.add(btnMoveLeft);
		
		// Se crea el panel de simulación del robot
		JPanel simulationPanel = new JPanel();
		simulationPanel.setBorder(new TitledBorder("Simulación del robot NXT"));
		simulationPanel.setLayout(experimentLayout);
		JButton btnSimulate = new JButton("Simular");
		btnSimulate.setToolTipText("Comienza la simulaci\u00F3n.");
		JButton btnStop = new JButton("Detener");
		btnStop.setToolTipText("Detiene la simulaci\u00F3n.");
		// Se agregan los botones para iniciar y detener la simulación al panel
		simulationPanel.add(btnSimulate);		
		simulationPanel.add(btnStop);	
		
		// Se crea el panel inferior con los botones
		JPanel southPanel = new JPanel();
		southPanel.setBorder(new TitledBorder("Administración del ambiente"));
		southPanel.setLayout(experimentLayout);
		JButton btnSave = new JButton("Guardar");
		btnSave.setToolTipText("Guarda el ambiente creado.");
		JButton btnLoad = new JButton("Cargar");
		btnLoad.setToolTipText("Carga en pantalla un ambiente guardado previamente.");
		JButton btnClear = new JButton("Limpiar");
		btnClear.setToolTipText("Limpia el ambiente en pantalla.");
		southPanel.add(btnSave);
		southPanel.add(btnLoad);
		southPanel.add(btnClear);

		// Se crea el panel izquierdo con las opciones de configuración y adm del ambiente 
		// y config y simulacion del robot
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(envConfigPanel);
		leftPanel.add(southPanel);
		leftPanel.add(nxtConfigPanel);
		leftPanel.add(simulationPanel);

		// Se agregan los listeners para los botones
		btnMoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getEnvironment().rotateRightNXT(15);
			}
		});
		
		btnMoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getEnvironment().rotateLeftNXT(15);
			}
		});		
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setCurrentDirectory(new File("flat-files"));
				jFileChooser.setSelectedFile(new File("environment"));
				int retval = jFileChooser.showSaveDialog(EnvironmentUI.this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = jFileChooser.getSelectedFile();
					EnvironmentConfiguration environmentConfiguration = environment.getEnvironmentConfiguration();
					EnvironmentConfigurationDao environmentConfigurationDao = new EnvironmentConfigurationDao();
					environmentConfigurationDao.saveEnvironment(environmentConfiguration, file);
								
					
				}
			}
		});
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setCurrentDirectory(new File("flat-files"));
				int retval = jFileChooser.showOpenDialog(EnvironmentUI.this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = jFileChooser.getSelectedFile();
					environment.clear();
					EnvironmentConfigurationDao environmentConfigurationDao = new EnvironmentConfigurationDao();
					EnvironmentConfiguration aux = environmentConfigurationDao.getEnvironment(file);
					environment.setEnvironmentConfiguration(aux);
					environment.refreshEnvironmentConfiguration();
				}
			}
		});
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				environment.clear();
			}
		});
		btnSimulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				environment.setEnvironmentAction(EnvironmentActions.RUN);				
				environment.doRun();
				if (job == null) {
					job = new Thread(new Job((EnvironmentTest)environment));
					job.start();
				}
				
			}
		});
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				environment.doPause();
				String actionCommand = group.getSelection().getActionCommand();
				setEnvironmentAction(actionCommand);
				job = null;				
			}
		});		
		

		// Se agregan los paneles a la pantalla principal
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		pack();
	}

	private JPanel createEnvConfigPanel(final ButtonGroup group) {
		// Creación del panel de config del ambiente
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Configuración del ambiente"));		
		// Opción de agregar
		JRadioButton addButton = new JRadioButton("Agregar obstáculo");
		addButton.setToolTipText("Agrega un obst\u00E1culo haciendo click con el mouse en el ambiente.");
		addButton.setMnemonic(KeyEvent.VK_A);
		addButton.setActionCommand(EnvironmentActions.ADD.getCode().toString());
		addButton.setSelected(true);
		// Opción de pintar
		JRadioButton paintButton = new JRadioButton("Pintar fondo");
		paintButton.setToolTipText("Pinta el fondo del color seleccionado en el combo haciendo click con el mouse en el ambiente.");
		paintButton.setMnemonic(KeyEvent.VK_P);
		paintButton.setActionCommand(EnvironmentActions.PAINT.getCode()
				.toString());
		// Opción de limpiar
		JRadioButton cleanButton = new JRadioButton("Borrar");
		cleanButton.setToolTipText("Borra el obst\u00E1culo o el fondo haciendo click con el mouse en el obst\u00E1culo o fondo pintado.");
		cleanButton.setMnemonic(KeyEvent.VK_L);
		cleanButton.setActionCommand(EnvironmentActions.CLEAN.getCode()
				.toString());				
		// Se agrupan las opciones
		group.add(addButton);
		group.add(paintButton);
		group.add(cleanButton);
		// Se agregan los listeners para las opciones
		addButton.addActionListener(this);
		paintButton.addActionListener(this);
		cleanButton.addActionListener(this);		
		//Combobox de colores
		final JComboBox colorsList = new JComboBox(EnvironmentColors.values());
		colorsList.setToolTipText("Selecci\u00F3n del color con el cual se desea pintar el fondo del ambiente.");
		colorsList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				Color color = ((EnvironmentColors)colorsList.getSelectedItem()).getColor();
				environment.setColor(color);
			}
		});
		colorsList.setSelectedIndex(0);
		colorsList.setPreferredSize(new Dimension(90,20));
		colorsList.setMaximumSize(new Dimension(90,20));
		colorsList.addActionListener(this);	
		
		GroupLayout layout = new GroupLayout(panel); 
		ParallelGroup parallelGroupA= layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING); 
				
		JLabel lbl2 = new JLabel("");
		JLabel lbl6 = new JLabel("");
		
		SequentialGroup sequentialGroupFila1= layout.createSequentialGroup(); /* contiene los componentes de la primera fila */ 
		sequentialGroupFila1.addComponent(addButton, 20, 20, 130); 
		sequentialGroupFila1.addGap(20); /* espacio entre boton1 y boton2 */ 
		sequentialGroupFila1.addComponent(lbl2, 50, 50, 50); 

		SequentialGroup sequentialGroupFila2= layout.createSequentialGroup(); /* contiene los componentes de la segunda fila */ 
		sequentialGroupFila2.addComponent(paintButton, 20, 20, 130); 
		sequentialGroupFila2.addGap(20); /* espacio entre boton3 y boton4 */ 
		sequentialGroupFila2.addComponent(colorsList, 50, 50, 80); 
		
		SequentialGroup sequentialGroupFila3= layout.createSequentialGroup(); /* contiene los componentes de la segunda fila */ 
		sequentialGroupFila3.addComponent(cleanButton, 20, 20, 130); 
		sequentialGroupFila3.addGap(20); /* espacio entre boton5 y boton6 */ 
		sequentialGroupFila3.addComponent(lbl6, 50, 50, 50); 

		ParallelGroup parallelGroupAuxiliar1= layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING); 
		parallelGroupAuxiliar1.addGroup(sequentialGroupFila1); /* de esta forma indicamos que sequentialGroupFila1 y sequentialGroupFila2 van en filas diferentes */ 
		parallelGroupAuxiliar1.addGroup(sequentialGroupFila2); 
		parallelGroupAuxiliar1.addGroup(sequentialGroupFila3); 

		SequentialGroup sequentialGroupHorizontal= layout.createSequentialGroup(); 
		sequentialGroupHorizontal.addGap(20); /* espacio vacio a la izquierda de ambas filas*/ 
		sequentialGroupHorizontal.addGroup(parallelGroupAuxiliar1); 
		sequentialGroupHorizontal.addGap(20); /* espacio vacio a la derecha de ambas filas*/ 

		parallelGroupA.addGroup(sequentialGroupHorizontal); 
		layout.setHorizontalGroup(parallelGroupA); 
		
		ParallelGroup parallelGroupB= layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING); 

		SequentialGroup sequentialGroupColumna1= layout.createSequentialGroup(); /* contiene los componentes de la primera columna */ 
		sequentialGroupColumna1.addComponent(addButton,20,20,130); 
		sequentialGroupColumna1.addGap(10); /* espacio entre boton1 y boton3 */ 
		sequentialGroupColumna1.addComponent(paintButton,20,20,130); 
		sequentialGroupColumna1.addGap(10); /* espacio entre boton1 y boton3 */ 
		sequentialGroupColumna1.addComponent(cleanButton,20,20,130); 

		SequentialGroup sequentialGroupColumna2= layout.createSequentialGroup(); /* contiene los componentes de la segunda columna */ 
		sequentialGroupColumna2.addComponent(lbl2,50,50,50); 
		sequentialGroupColumna2.addGap(10); /* espacio entre boton2 y boton4 */ 
		sequentialGroupColumna2.addComponent(colorsList,50,50,80); 
		sequentialGroupColumna2.addGap(10); /* espacio entre boton2 y boton4 */ 
		sequentialGroupColumna2.addComponent(lbl6,50,50,50); 

		ParallelGroup parallelGroupAuxiliar2= layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING); 
		parallelGroupAuxiliar2.addGroup(sequentialGroupColumna1); /* de esta forma indicamos que sequentialGroupColumna1 y sequentialGroupColumna2 van en columnas diferentes */ 
		parallelGroupAuxiliar2.addGroup(sequentialGroupColumna2); 

		SequentialGroup sequentialGroupVertical= layout.createSequentialGroup(); 
		sequentialGroupVertical.addGap(10); /* espacio vacio arriba de ambas columnas*/ 
		sequentialGroupVertical.addGroup(parallelGroupAuxiliar2); 
		sequentialGroupVertical.addGap(10); /* espacio vacio abajo de ambas columnas */ 

		parallelGroupB.addGroup(sequentialGroupVertical); 
		layout.setVerticalGroup(parallelGroupB); 
		
		panel.setLayout(layout);
		
		return panel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		setEnvironmentAction(actionCommand);
	}

	public Environment getEnvironment() {
		return environment;
	}
	
	protected void setEnvironmentAction(String actionCommand) {
		try {
			Integer actionCommandNumber = new Integer(actionCommand);
			environment.setEnvironmentAction(EnvironmentActions
				.getState(actionCommandNumber.intValue()));
		} catch (NumberFormatException nfe) {
			// No es una acción del estado del environment.
		}
	}
	
}
