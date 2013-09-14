package nxt.simulator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ConfigurationPanel extends JPanel{
	
	protected String obst0Name = "obstacles/obst0.gif";
	protected String obst1Name = "obstacles/obst1.gif";	
	
	public ConfigurationPanel(){
	    //Create the radio buttons.
	    JRadioButton optButton0 = new JRadioButton("hola");
	    optButton0.setMnemonic(KeyEvent.VK_B);
	    optButton0.setActionCommand(obst0Name);
	    //optButton0.setIcon(getImage("../../sprites/obstacles/obst0.gif"));
	    optButton0.setSelected(true);
	
	    JRadioButton optButton1 = new JRadioButton("dos");
	    optButton1.setMnemonic(KeyEvent.VK_C);
	    optButton1.setActionCommand(obst1Name);
	    
        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(optButton0);
        group.add(optButton1);
        
        //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 2));
        radioPanel.add(optButton0);
        ImageIcon img0 = getImage("../../sprites/obstacles/obst1.gif");
        JLabel lbl0 = new JLabel(img0);
        lbl0.setPreferredSize(new Dimension(10, 10));        
        radioPanel.add(lbl0);
        
        radioPanel.add(optButton1);
        ImageIcon img1 = getImage("../../sprites/obstacles/obst3.gif");
        JLabel lbl1 = new JLabel(img1);       
        lbl1.setPreferredSize(new Dimension(10, 10));
//        radioPanel.add(new JButton(getImage("../../sprites/obstacles/obst2.gif")));
        radioPanel.add(lbl1);
        
        
        //setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        
        add(radioPanel, BorderLayout.LINE_START);
        /*add(radioPanel);
        add(new JButton("1"));
        add(new JButton("1"));
        add(new JButton("1"));
        add(new JButton("1"));
        setSize(50,50);
        
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        for(int i = 1; i <= 10; i++)
        add(new JButton("Componente " + i));
        setSize(200,200);//pack();
        setVisible(true);
        */
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
}
