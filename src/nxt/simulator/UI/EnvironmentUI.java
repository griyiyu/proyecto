package nxt.simulator.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import nxt.simulator.Environment;
import nxt.simulator.EnvironmentConfiguration;
import nxt.simulator.EnvironmentTest;
import nxt.simulator.Job;
import nxt.simulator.persistance.EnvironmentConfigurationDao;
import tools.EnvironmentActions;
import tools.EnvironmentColors;
import ch.aplu.jgamegrid.GGMouse;

public class EnvironmentUI extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Entorno de simulaci�n que contiene los obst�culos, el robot y los sensores
	 */
	protected Environment environment;
	
	/**
	 * C�digo lejos con el cual se mover� el robot
	 */
	private Thread job = null;
	//private Job job = null;

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
				| GGMouse.lDrag | GGMouse.lPress | GGMouse.lRelease 
				| GGMouse.rPress);
		centerPanel.add(environment, BorderLayout.CENTER);

		// Se crea el panel izquierdo con las opciones de creaci�n de ambientes
		JPanel leftPanel = new JPanel();
//		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
		leftPanel.setLayout(new GridBagLayout());
			
		// Opci�n de agregar
		JRadioButton addButton = new JRadioButton("Agregar obst�culo");
		addButton.setToolTipText("Agrega un obst\u00E1culo haciendo click con el mouse en el ambiente.");
		addButton.setMnemonic(KeyEvent.VK_A);
		addButton.setActionCommand(EnvironmentActions.ADD.getCode().toString());
		addButton.setSelected(true);
		// Opci�n de pintar
		JRadioButton paintButton = new JRadioButton("Pintar fondo");
		paintButton.setToolTipText("Pinta el fondo del color seleccionado en el combo haciendo click con el mouse en el ambiente.");
		paintButton.setMnemonic(KeyEvent.VK_P);
		paintButton.setActionCommand(EnvironmentActions.PAINT.getCode()
				.toString());
		// Opci�n de limpiar
		JRadioButton cleanButton = new JRadioButton("Borrar");
		cleanButton.setToolTipText("Borra el obst\u00E1culo o el fondo haciendo click con el mouse en el obst\u00E1culo o fondo pintado.");
		cleanButton.setMnemonic(KeyEvent.VK_L);
		cleanButton.setActionCommand(EnvironmentActions.CLEAN.getCode()
				.toString());				
		// Se agrupan las opciones
		final ButtonGroup group = new ButtonGroup();
		group.add(addButton);
		group.add(paintButton);
		group.add(cleanButton);
		// Se agregan los listeners para las opciones
		addButton.addActionListener(this);
		paintButton.addActionListener(this);
		cleanButton.addActionListener(this);
		// Se agregan las opciones al panel izquierdo
		addGridBagConstraintsToPanel(0, 0, addButton, leftPanel);
		addGridBagConstraintsToPanel(0, 1, paintButton, leftPanel);
		addGridBagConstraintsToPanel(0, 2, cleanButton, leftPanel);
			
		
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
//		leftPanel.add(colorsList);
		
		addGridBagConstraintsToPanel(1, 1, colorsList, leftPanel);
				
		// Se crea el panel inferior con los botones
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		JButton btnSave = new JButton("Guardar");
		btnSave.setToolTipText("Guarda el ambiente creado.");
		JButton btnLoad = new JButton("Cargar");
		btnLoad.setToolTipText("Carga en pantalla un ambiente guardado previamente.");
		JButton btnClear = new JButton("Limpiar");
		btnClear.setToolTipText("Limpia el ambiente en pantalla.");
		JButton btnSimulate = new JButton("Simular");
		btnSimulate.setToolTipText("Comienza la simulaci\u00F3n.");
		JButton btnStop = new JButton("Detener");
		btnStop.setToolTipText("Detiene la simulaci\u00F3n.");

		// Se agregan los listeners para los botones
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
					//job.run();
					job.start();
				}
				
			}
		});
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				environment.doPause(); //stopGameThread(); //
				String actionCommand = group.getSelection().getActionCommand();
				setEnvironmentAction(actionCommand);
				//if (job != null)
				//	job.interrupt(); // job.stop();
				job = null;
				
			}
		});		
		southPanel.add(btnSave);
		southPanel.add(btnLoad);
		southPanel.add(btnClear);
		southPanel.add(btnSimulate);
		southPanel.add(btnStop);

		// Se agregan los paneles a la pantalla principal
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		pack();
	}

	protected void addGridBagConstraintsToPanel(int x, int y,
			JRadioButton button, JPanel panel) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = x;
		c.gridy = y;
		panel.add(button, c);
	}
	
	protected void addGridBagConstraintsToPanel(int x, int y,
			JComboBox comboBox, JPanel panel) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = x;
		c.gridy = y;
		panel.add(comboBox, c);
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
			// No es una acci�n del estado del environment.
		}
	}
	
}
