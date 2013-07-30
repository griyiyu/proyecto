package nxt.simulator.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import nxt.simulator.Environment;
import nxt.simulator.EnvironmentTest;
import nxt.simulator.persistance.EnvironmentDao;
import tools.EnvironmentActions;
import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GGTileMap;
import ch.aplu.jgamegrid.Location;

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
				| GGMouse.lDrag);
		centerPanel.add(environment, BorderLayout.CENTER);

		// Se crea el panel izquierdo con las opciones de creación de ambientes
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		// Opción de agregar
		JRadioButton adddButton = new JRadioButton("Agregar");
		adddButton.setMnemonic(KeyEvent.VK_A);
		adddButton
				.setActionCommand(EnvironmentActions.ADD.getCode().toString());
		adddButton.setSelected(true);
		// Opción de pintar
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

		// Se crea el panel inferior con los botones
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		btnSave = new JButton("Guardar");
		btnLoad = new JButton("Cargar");
		btnClear = new JButton("Limpiar");

		// Se agregan los listeners para los botones
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnvironmentDao environmentDao = new EnvironmentDao();
				environmentDao.saveEnvironment(environment.getTmAsString());
			}
		});
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnvironmentDao environmentDao = new EnvironmentDao();
				String aux = environmentDao.getEnvironment();
				environment.setTmFromString(aux);
			}
		});
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearEnvironmnet();
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
	
	private void clearEnvironmnet() {
		environment.setTm(environment.createTileMap(50, 30, 20, 20));
		//Se setea la propiedad isTileCollisionEnabled a false para todas las celdas del tileMap
		GGTileMap tm = environment.getTileMap();
		for (int i = 0 ; i < tm.getNbHorzTiles() ; i++) {
			for (int j = 0 ; j < tm.getNbVertTiles() ; j++) {
				Location locAux = new Location(i, j);
				if (tm.isTileCollisionEnabled(locAux)) {
					tm.setTileCollisionEnabled(locAux, false);
				}				
			}
		}					
		environment.refresh();		
	}

}
