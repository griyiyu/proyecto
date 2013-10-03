package tools;

import java.awt.Color;

public enum EnvironmentColors {
	
	Negro(1), Azul(2), Verde(3), Amarillo(4), Rojo(5), Blanco(6);

	private Color color;

	private EnvironmentColors(int value) {
		switch (value) {
		case 1: 
			color = Color.BLACK;
			break;
		case 2:
			color = Color.BLUE;
			break;
		case 3:
			color = Color.GREEN;
			break;
		case 4:
			color = Color.YELLOW;
			break;
		case 5:
			color = Color.RED;
			break;
		case 6:
			color = Color.WHITE;
			break;
		default:
			color = Color.BLACK;
			break;
		};
	}

	public Color getColor() {
		return color;
	}
		
}
