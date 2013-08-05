package tools;

import java.awt.Color;
import java.util.ArrayList;

public enum EnvironmentColors {
	//NO_COLOR("Sin color"), BLACK("Negro"), BLUE("Azul"), GREEN("Verde"), 
	//YELLOW("Amarillo"), RED("Rojo"), WHITE("Blanco");
	Negro(1), Azul(2), Verde(3), Amarillo(4), Rojo(5), Blanco(6);


	//private String name;
	//private int value;
	private Color color;

//	public static EnvironmentColors getState(int value) {
//		EnvironmentColors returnedValue = NO_COLOR;
//		switch (value) {
//		case 1: 
//			returnedValue = EnvironmentColors.BLACK;
//			break;
//		case 2:
//			returnedValue = EnvironmentColors.BLUE;
//			break;
//		case 3:
//			returnedValue = EnvironmentColors.GREEN;
//			break;
//		case 4:
//			returnedValue = EnvironmentColors.YELLOW;
//			break;
//		case 5:
//			returnedValue = EnvironmentColors.RED;
//			break;
//		case 6:
//			returnedValue = EnvironmentColors.WHITE;
//			break;
//		default:
//			returnedValue = EnvironmentColors.NO_COLOR;
//			break;
//		};	
//		return returnedValue;
//	}
	
/*	public static String[] getAllColors() {
		ArrayList<String> colorsStrings = new ArrayList<String>();
		colorsStrings.add(EnvironmentColors.BLACK.getName());
		colorsStrings.add(EnvironmentColors.BLUE.getName());
		colorsStrings.add(EnvironmentColors.GREEN.getName());
		colorsStrings.add(EnvironmentColors.YELLOW.getName());
		colorsStrings.add(EnvironmentColors.RED.getName());
		colorsStrings.add(EnvironmentColors.WHITE.getName());
		
		return (String[])colorsStrings.toArray();
	}
*/	
/*
	private EnvironmentColors(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
*/
/*
	private EnvironmentColors(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
*/
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
