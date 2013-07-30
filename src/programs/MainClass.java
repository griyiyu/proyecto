package programs;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class MainClass {

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                JColorChooser cc = new JColorChooser();
	                AbstractColorChooserPanel[] panels = cc.getChooserPanels();
	                for (AbstractColorChooserPanel accp : panels) {
	                    if (!(accp.getDisplayName().equals("HSB")) && !(accp.getDisplayName().equals("RGB"))) {
	                    	/*accp.remove(0);
	                    	JLabel lbl = new JLabel("Hola");
	                    	accp.add(lbl);
	                    	Color newColor = cc.getColor();
	                        lbl.setForeground(newColor);
	                        */
	                        JOptionPane.showMessageDialog(null, accp);
	                    }
	                }
	            }
	        });
	    }
	
}
