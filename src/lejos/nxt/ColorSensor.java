package lejos.nxt;

import java.awt.Color;

import ch.aplu.jgamegrid.Location;


public class ColorSensor extends Sensor {
	
	private static int radio = 2; //Radio en pxs del circulo de medici�n para obtener el rawValue.
	protected Color color;
	private int red;
	private int blue;
	private int green;
	private int rawValue;

	
	public ColorSensor(SensorPort port) {
		super(port, "colorSensor", 4);
		getEnvironment().addPart(this);	
		color = environment.getBackground();
		red = color.getRed();
		blue = color.getBlue();
		green = color.getGreen();
	}


	public void act() {
		color = getBackground().getColor(getLocation());
		setColor(color.getRed(), color.getBlue(), color.getGreen());
		//Se toman 5 puntos representativos de un circulo, y se devuelve el promedio en el caso del m�todo getRawLigthValue.
		Location location = getLocation();
		Color c1 = getBackground().getColor(location);
		Color c2 = getBackground().getColor(location.getAdjacentLocation(0, radio));
		Color c3 = getBackground().getColor(location.getAdjacentLocation(90, radio));
		Color c4 = getBackground().getColor(location.getAdjacentLocation(180, radio));
		Color c5 = getBackground().getColor(location.getAdjacentLocation(270, radio));
		rawValue = (c1.getRed() + c1.getBlue() + c1.getGreen() + c2.getRed() + c2.getBlue() 
				+ c2.getGreen() + c3.getRed() + c3.getBlue() + c3.getGreen() + c4.getRed() + c4.getBlue() + c4.getGreen() + c5.getRed() + c5.getBlue() + c5.getGreen())/15;
		
	}	
	
	/**
	 * Retorna un entero que representa el color que detecta el sensor, 
	 * los colores est�n dados por la clase lejos.robotics.Color
	 * Negro(7), Azul(2), Verde(1), Amarillo(3), Rojo(0), Blanco(6);
	 */
	public int getColorID() {
		int blank = 0;

		if (red > blue && red > green) {
			// red dominant color
			if (red < 65 || (blank < 40 && red < 110))
				return lejos.robotics.Color.BLACK;
			if (((blue >> 2) + (blue >> 3) + blue < green)
					&& ((green << 1) > red))
				return lejos.robotics.Color.YELLOW;
			if ((green << 1) - (green >> 2) < red)
				return lejos.robotics.Color.RED;
			if (blue < 70 || green < 70 || (blank < 140 && red < 140))
				return lejos.robotics.Color.BLACK;
			return lejos.robotics.Color.WHITE;
		} else if (green > blue) {
			// green dominant color
			if (green < 40 || (blank < 30 && green < 70))
				return lejos.robotics.Color.BLACK;
			if ((blue << 1) < red)
				return lejos.robotics.Color.YELLOW;
			if ((red + (red >> 2)) < green || (blue + (blue >> 2)) < green)
				return lejos.robotics.Color.GREEN;
			if (red < 70 || blue < 70 || (blank < 140 && green < 140))
				return lejos.robotics.Color.BLACK;
			return lejos.robotics.Color.WHITE;
		} else {
			// blue dominant color
			if (blue < 48 || (blank < 25 && blue < 85))
				return lejos.robotics.Color.BLACK;
			if ((((red * 48) >> 5) < blue && ((green * 48) >> 5) < blue)
					|| ((red * 58) >> 5) < blue || ((green * 58) >> 5) < blue)
				return lejos.robotics.Color.BLUE;
			if (red < 60 || green < 60 || (blank < 110 && blue < 120))
				return lejos.robotics.Color.BLACK;
			if ((red + (red >> 3)) < blue || (green + (green >> 3)) < blue)
				return lejos.robotics.Color.BLUE;
			return lejos.robotics.Color.WHITE;
		}

	}
	
    public int getRawLightValue()
    {
    	return rawValue + 400;
    }
	
    
    public void setColor(int red, int blue, int green)
    {
    	this.red = red;
    	this.blue = blue;
    	this.green = green;    	
    }

}
