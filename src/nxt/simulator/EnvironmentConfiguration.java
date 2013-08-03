package nxt.simulator;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.aplu.jgamegrid.Location;

public class EnvironmentConfiguration implements Serializable{

	protected List<String> obstacles;
	protected HashMap<String, Color> colorsTM;

	EnvironmentConfiguration() {
		obstacles = new ArrayList<String>();
		colorsTM = new HashMap<String, Color>();
	}
	
	public List<String> getObstacles() {
		return obstacles;
	}

	public void setObstacles(List<String> obstacles) {
		this.obstacles = obstacles;
	}

	public HashMap<String, Color> getColorsTM() {
		return colorsTM;
	}

	public void setColorsTM(HashMap<String, Color> colorsTM) {
		this.colorsTM = colorsTM;
	}
	
	public void addObstacle(String location) {
		obstacles.add(location);
	}
	
	public void addColor(String location, Color color) {
		colorsTM.put(location, color);
	}
}
