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
	protected int posRobotX;
	protected int posRobotY;
	double directionRobot;

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

	public int getPosRobotX() {
		return posRobotX;
	}

	public void setPosRobotX(int posRobotX) {
		this.posRobotX = posRobotX;
	}

	public int getPosRobotY() {
		return posRobotY;
	}

	public void setPosRobotY(int posRobotY) {
		this.posRobotY = posRobotY;
	}

	public double getDirectionRobot() {
		return directionRobot;
	}

	public void setDirectionRobot(double directionRobot) {
		this.directionRobot = directionRobot;
	}
	
	public void removeObstacle(String value) {
		obstacles.remove(value);
	}
	
	
}
