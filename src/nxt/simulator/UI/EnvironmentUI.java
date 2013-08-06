package nxt.simulator.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import nxt.simulator.persistance.EnvironmentConfigurationDao;
import tools.EnvironmentActions;
import tools.EnvironmentColors;
import ch.aplu.jgamegrid.GGMouse;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class EnvironmentUI extends JInternalFrame implements ActionListener {

	protected Environment environment;
	protected JButton btnSave;
	protected JButton btnLoad;
	protected JButton btnClear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnvironmentUI frame = new EnvironmentUI();
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
	public EnvironmentUI() {
		setMinimumSize(new Dimension(400, 600));
		setClosable(true);
		setMaximizable(true);
		setResizable(true);
		setBounds(100, 100, 1048, 674);

		// Se crea el panel central y el environment
		JPanel centerPanel = new JPanel();
		environment = new EnvironmentTest();
		environment.addMouseListener(environment, GGMouse.lClick
				| GGMouse.lDrag | GGMouse.lPress | GGMouse.lRelease);
		centerPanel.add(environment, BorderLayout.CENTER);

		// Se crea el panel izquierdo con las opciones de creaci�n de ambientes
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
		// Opci�n de agregar
		JRadioButton adddButton = new JRadioButton("Agregar");
		adddButton.setMnemonic(KeyEvent.VK_A);
		adddButton
				.setActionCommand(EnvironmentActions.ADD.getCode().toString());
		adddButton.setSelected(true);
		// Opci�n de pintar
		JRadioButton paintButton = new JRadioButton("Pintar");
		paintButton.setMnemonic(KeyEvent.VK_P);
		paintButton.setActionCommand(EnvironmentActions.PAINT.getCode()
				.toString());
		// Se agrupan las opciones
		ButtonGroup group = new ButtonGroup();
		group.add(adddButton);
		group.add(paintButton);
		// Se agregan los listeners para las opciones
		adddButton.addActionListener(this);
		paintButton.addActionListener(this);
		// Se agregan las opciones al panel izquierdo
		leftPanel.add(adddButton);
		leftPanel.add(paintButton);
		
		//Combobox de colores
		final JComboBox colorsList = new JComboBox(EnvironmentColors.values());
		colorsList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				Color color = ((EnvironmentColors)colorsList.getSelectedItem()).getColor();
				environment.setColor(color);
			}
		});
		colorsList.setSelectedIndex(0);
		colorsList.addActionListener(this);
		leftPanel.add(colorsList);
		
		// Se crea el panel inferior con los botones
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		btnSave = new JButton("Guardar");
		btnLoad = new JButton("Cargar");
		btnClear = new JButton("Limpiar");

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
					EnvironmentConfigurationDao environmentConfigurationDao = new EnvironmentConfigurationDao();
					EnvironmentConfiguration aux = environmentConfigurationDao.getEnvironment(file);
					environment.clear();
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
		southPanel.add(btnSave);
		southPanel.add(btnLoad);
		southPanel.add(btnClear);

		// Se agregan los paneles a la pantalla principal
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		environment.setEnvironmentAction(EnvironmentActions
				.getState((new Integer((String) e.getActionCommand()))
						.intValue()));
	}

	public Environment getEnvironment() {
		return environment;
	}	
}
