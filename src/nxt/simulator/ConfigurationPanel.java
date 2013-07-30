package nxt.simulator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.aplu.jgamegrid.Location;

public class ConfigurationPanel extends JPanel implements ActionListener {
	
	protected String obst1Name = "obstacles/obst1.gif";
	
	//Group the radio buttons.
    protected ButtonGroup group = new ButtonGroup();
    protected JComboBox imagesCombo = new JComboBox();
    protected JLabel lblImage = new JLabel();
    
    protected JTextField xvalue = new JTextField();
    protected JTextField yvalue = new JTextField();
    
	
	public ConfigurationPanel(){
	    // Panel de coordenadas
        JPanel coordPanel = new JPanel(new GridBagLayout()); 
        
//        coordPanel.add(new JLabel("x: "));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        coordPanel.add (new JLabel("x: "), constraints);        
                
        xvalue.setText("250");
        //xvalue.setSize(100, 1);
//        coordPanel.add(xvalue);
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        coordPanel.add (xvalue, constraints);        
        
//        coordPanel.add(new JLabel("y: "));
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 1; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        coordPanel.add (new JLabel("y: "), constraints);
        
        yvalue.setText("400");
        //yvalue.setSize(2, 2);
//        coordPanel.add(yvalue);
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 1; // El área de texto empieza en la fila cero
        constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        coordPanel.add (yvalue, constraints);        

        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 2; // El área de texto empieza en la fila cero
        constraints.gridwidth = 2; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        String path = "src/sprites/obstacles/";
        File dir = new File(path);
        String[] images = dir.list();
        if (images == null) {
        	System.out.println("No hay imagenes en el directorio especificado");
        }
    	else { 
    	  for (int x=0; x<images.length; x++) {
    		  imagesCombo.addItem(images[x]);
    	  }
    	}
        imagesCombo.addActionListener(this);
        coordPanel.add (imagesCombo, constraints);        

        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 3; // El área de texto empieza en la fila cero
        constraints.gridwidth = 2; // El área de texto ocupa dos columnas.
        constraints.gridheight = 5; // El área de texto ocupa 2 filas.        
        lblImage.setIcon(getImage("../../sprites/obstacles/" + (String) imagesCombo.getSelectedItem()));
        lblImage.setPreferredSize(new Dimension(150, 300));
        coordPanel.add (lblImage, constraints);        
        
        
        //Panel de seleccion de imagenes
//        JPanel imagePanel = new JPanel(new BorderLayout());
        
//        String path = "src/sprites/obstacles/";
//        File dir = new File(path);
//        String[] images = dir.list();
//        if (images == null) {
//        	System.out.println("No hay imagenes en el directorio especificado");
//        }
//    	else { 
//    	  for (int x=0; x<images.length; x++) {
//    		  imagesCombo.addItem(images[x]);
//    	  }
//    	}
//        imagesCombo.addActionListener(this);
//        imagePanel.add(imagesCombo, BorderLayout.PAGE_START);
        
        
//        lblImage.setIcon(getImage("../../sprites/obstacles/obst5.gif"));
//        lblImage.setPreferredSize(new Dimension(90, 90));
//        imagePanel.add(lblImage, BorderLayout.PAGE_END);
        
        //setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        
        //add(radioPanel, BorderLayout.LINE_START);
//        setLayout(new GridLayout(2, 1));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//        add(coordPanel);
//        add(imagePanel);
        add(coordPanel, BorderLayout.NORTH);
//        add(imagePanel, BorderLayout.SOUTH);
        //add(lblImage, BorderLayout.CENTER);
        //add(imagesCombo, BorderLayout.PAGE_END);
	}

	/**
     * retorna un ImageIcon a partir de la url pasada por parámetro.
     * @param url
     * @return {@link ImageIcon}
     */
    public ImageIcon getImage(String url) {
        ImageIcon image = null;
        URL imgURL = getClass().getResource(url);
        if (imgURL != null) {
            image = new ImageIcon(imgURL);
        }
        if (image == null) {
        	System.out.println("No fue encontrada la imagen con path: " + url + " dentro de " + getClass().getResource(""));
        }
        return image;
    }

	public String getSelectedObstacle() {
		//return group.getSelection().getActionCommand();
		return (String) imagesCombo.getSelectedItem(); 
	}
	
	public Location getObstacleLocation() {
		try {
			Integer xIntValue = new Integer(xvalue.getText());
			Integer yIntValue = new Integer(yvalue.getText());
			return new Location(xIntValue,yIntValue);
		}
		catch (NumberFormatException nfe) {
			return new Location(0,0);
		}
	}
	
    /** Listens to the combo box. */
    public void actionPerformed(ActionEvent e) {
        lblImage.setIcon(getImage("../../sprites/obstacles/" + imagesCombo.getSelectedItem()));
    }
	
}
