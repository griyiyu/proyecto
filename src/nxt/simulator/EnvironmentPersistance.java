package nxt.simulator;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EnvironmentPersistance implements Serializable{

	private static final long serialVersionUID = 1L;
	protected Set<String> obstacles;
	protected HashMap<String, Color> colorsTM;
	protected int posRobotX;
	protected int posRobotY;
	double directionRobot;

	EnvironmentPersistance() {
		obstacles = new HashSet<String>();
		colorsTM = new HashMap<String, Color>();
	}
	
	public Set<String> getObstacles() {
		return obstacles;
	}

	public void setObstacles(Set<String> obstacles) {
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
	
	public void removeObstacle(String location) {
		obstacles.remove(location);
	}
	
	public void removeAllObstacles() {
		obstacles = new HashSet<String>();
	}
	
	public void removeColor(String location) {
		colorsTM.remove(location);
	}
	
	public void removeAllColors() {
		colorsTM = new HashMap<String, Color>();
	}
	
}
