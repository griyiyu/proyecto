package nxt.simulator;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.Sensor;
import nxt.simulator.UI.EnvironmentUI;
import tools.AdministratorConstants;
import tools.EnvironmentActions;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GGMouseListener;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

public abstract class Environment extends GameGrid implements
		AdministratorConstants, GGMouseListener {

	//private static final long serialVersionUID = 1L;
	// Cuadro de 2m x 1.2m (1 pixel equivale a 2 mm)
	protected static int width = 1000;
	protected static int high = 600;
	protected static Location startLocation = new Location(450, 250);
	protected static double startDirection = START_DIRECTION;
	protected static boolean isNavigationBar = false;
	protected RobotCar nxt = new RobotCar(startLocation, "Brick.png");
	protected EnvironmentConfiguration environmentConfiguration;
	protected static String obstacleImage = AdministratorConstants.IMAGE_PATH + "brickWhite.png";

	public EnvironmentActions getEnvironmentAction() {
		return environmentAction;
	}

	public void setEnvironmentAction(EnvironmentActions environmentAction) {
		this.environmentAction = environmentAction;
	}

	private EnvironmentActions environmentAction = EnvironmentActions.ADD;
	private boolean dragging = false;
	private boolean addingOrPainting = false;
	
	protected Color color = new Color(0);
	
	private long cicleTotalTime = System.currentTimeMillis();
	private long cicleStartTime = System.currentTimeMillis();
	
	protected EnvironmentUI environmentUI;		

	public Environment(Location startLocation, double startDirection) {
		super(width, high, 1, null, null, isNavigationBar, 60);
		setTitle("NXT Robot Simulation Environment");
		setSimulationPeriod(SIMULATION_PERIOD);
		setBgColor(AdministratorConstants.backgroundColor); 
		environmentConfiguration = new EnvironmentConfiguration();
		Sensor.setEnvironment(this);

		addActor(nxt, startLocation, startDirection);
		addPart(new Motor(MotorPort.A));
		addPart(new Motor(MotorPort.B));

		show();
	}

	public Environment() {
		super();
//		setCellSize(20);
//		setGridColor(Color.red);
		setCellSize(1);
		setNbVertCells(high); 
		setNbHorzCells(width); 		
		setBgColor(AdministratorConstants.backgroundColor);
		environmentConfiguration = new EnvironmentConfiguration();
		Sensor.setEnvironment(this);
	}

	public void addNXT() {
		addActor(nxt, startLocation, startDirection);
		addPart(new Motor(MotorPort.A));
		addPart(new Motor(MotorPort.B));
		getEnvironmentConfiguration().setPosRobotX(startLocation.getX());
		getEnvironmentConfiguration().setPosRobotY(startLocation.getY());
		getEnvironmentConfiguration().setDirectionRobot(startDirection);
	}
	
	public void addPart(Part part) {
		addActor(part, nxt.getPartLocation(part), nxt.getDirection());
	}

	public void addObstacleWithoutOffset(Obstacle obstacle, int x, int y) {
		addActor(obstacle, new Location(x, y));
	}

	public void addObstacle(Obstacle obstacle, int x, int y) {
		Location recalculatedLocation = setOffset(obstacle, new Location(x, y));
		ArrayList<Actor> obstacles = getActorsAt(recalculatedLocation, Obstacle.class);
		if (obstacles == null || obstacles.isEmpty()) {
			addActor(obstacle, recalculatedLocation);
		}
	}

	public void removeObstacle(String obstacleName) {
		removeActor(getObstacle(obstacleName));
	}

	public void removeObstaclesAtLocation(int x, int y) {
		//Se busca la posici�n el obstaculo mediante el punto central de la imagen.
		int posTileX = x / 20;
		int posTileY = y / 20;
		int posX = posTileX * AdministratorConstants.TILE_WIDTH + AdministratorConstants.TILE_WIDTH / 2;
		int posY = posTileY * AdministratorConstants.TILE_WIDTH + AdministratorConstants.TILE_WIDTH / 2;		
		ArrayList<Actor> obstacles = getActorsAt(new Location(posX,posY), Obstacle.class);
		for (Actor obstacle : obstacles) {
			removeActor(obstacle);
		}
		refresh();
	}
	
	public Obstacle getObstacle(String imageName) {
		for (Actor actor : getActors(Obstacle.class)) {
			Obstacle obstacle = (Obstacle) actor;
			if (obstacle.getName().equalsIgnoreCase(imageName)) {
				return obstacle;
			}
		}
		return null;
	}

	private Location setOffset(Obstacle obstacle, Location loc) {
		int x = (obstacle.getImage().getWidth()) / 2;
		int y = (obstacle.getImage().getHeight()) / 2;
		return new Location(loc.getX() + x, loc.getY() + y);
	}

	public void showActor() { // (Actor a) {
		// tsw.show(); //a.show();
	}
	

	public boolean mouseEvent(GGMouse mouse) {
		boolean clickOnRobot = false;
		RobotCar robot = (RobotCar)getOneActor(RobotCar.class);
		if (robot!= null) {
			//56pxX37px
			if (mouse.getX() >= robot.getX() - robot.getHeight(0)  &&  mouse.getX() <= robot.getX() + robot.getHeight(0) && 
					mouse.getY() >= robot.getY() - robot.getHeight(0) && mouse.getY() <= robot.getY() + robot.getHeight(0))
			clickOnRobot = true;						
		}
		if (!EnvironmentActions.RUN.equals(getEnvironmentAction())) {
			switch (mouse.getEvent()) {
			case GGMouse.lPress:
				if (clickOnRobot) {
					dragging = true;
					moveNXT(mouse.getX(), mouse.getY());									
				} else {
					addingOrPainting = true;
				}
				break;
			case GGMouse.lClick:
				if (!clickOnRobot) {
					if (EnvironmentActions.ADD.equals(getEnvironmentAction())) {
						addObstacleInCell(mouse.getX(), mouse.getY());
					} else if (EnvironmentActions.PAINT.equals(getEnvironmentAction())) {
						paintCell(mouse.getX(), mouse.getY());
					} else if (EnvironmentActions.CLEAN.equals(getEnvironmentAction())) {
						cleanCell(mouse.getX(), mouse.getY());
					}
				}
				break;
			case GGMouse.lDrag:
				if ((clickOnRobot && !addingOrPainting)|| dragging) {
					moveNXT(mouse.getX(), mouse.getY());
				} else if (!clickOnRobot){
					if (EnvironmentActions.ADD.equals(getEnvironmentAction())) {
						addObstacleInCell(mouse.getX(), mouse.getY());
					} else if (EnvironmentActions.PAINT.equals(getEnvironmentAction())) {
						paintCell(mouse.getX(), mouse.getY());
					} else if (EnvironmentActions.CLEAN.equals(getEnvironmentAction())) {
						cleanCell(mouse.getX(), mouse.getY());
					}
				}			
				break;
			case GGMouse.lRelease:
				dragging = false;
				addingOrPainting = false;
				break;
			/*
			case GGMouse.rPress:
				if (clickOnRobot) {
					rotateLeftNXT(15);
				}
				break;
				*/
			}
		}	
		return true;		
	}

	
	protected void paintCell(int x, int y) {
		paintCell(x, y, color);
	}
	

	protected void paintCell(int x, int y, Color color) {
		int posTileX = x / 20;
		int posTileY = y / 20;
		int posX = posTileX * AdministratorConstants.TILE_WIDTH;
		int posY = posTileY * AdministratorConstants.TILE_WIDTH;
		//Se pinta la celda
		GGBackground bg = getBg();
	    bg.setPaintColor(color);
	    bg.fillRectangle(new Point(posX, posY), 
	    		new Point(posX + AdministratorConstants.TILE_WIDTH, posY + AdministratorConstants.TILE_WIDTH));
	    //Se agrega la localizaci�n de la esquina superior izquierda del cuadro pintado en la configuraci�n
	    Location location = new Location(posTileX, posTileY);
	    getEnvironmentConfiguration().addColor(location.toString(), color);
	    refresh();
	}

	
	public void addObstacleInCell(int x, int y) {
		int posTileX = x / 20;
		int posTileY = y / 20;
		int posX = posTileX * AdministratorConstants.TILE_WIDTH;
		int posY = posTileY * AdministratorConstants.TILE_WIDTH;
		//Se agrega el obst�culo
		addObstacle(new Obstacle(), posX, posY);
		//Se agrega la localizaci�n de la esquina superior izquierda del obst�culo en la configuraci�n
		Location location = new Location(posTileX, posTileY);
		getEnvironmentConfiguration().addObstacle(location.toString());		
		
	}
	
	protected void moveNXT(int x, int y) {
		getNxt().moveCar(x, y, getNxt().getDirection());
		getEnvironmentConfiguration().setPosRobotX(x);
		getEnvironmentConfiguration().setPosRobotY(y);
		refresh();
	}

	public void rotateLeftNXT(double angle) {
		double newDirection = getNxt().getDirection() - angle;
		getNxt().moveCar(getNxt().getX(), getNxt().getY(), newDirection);
		getEnvironmentConfiguration().setDirectionRobot(newDirection);		
		refresh();		
	}
	
	public void rotateRightNXT(double angle) {		
		double newDirection = getNxt().getDirection() + angle;
		getNxt().moveCar(getNxt().getX(), getNxt().getY(), newDirection);
		getEnvironmentConfiguration().setDirectionRobot(newDirection);		
		refresh();		
	}		
	
	public RobotCar getNxt() {
		return nxt;
	}

	public void setNxt(RobotCar nxt) {
		this.nxt = nxt;
	}
		
	
	public EnvironmentConfiguration getEnvironmentConfiguration() {
		if (environmentConfiguration == null) {
			environmentConfiguration = new EnvironmentConfiguration();
		}
		return environmentConfiguration;
	}

	public void setEnvironmentConfiguration(
			EnvironmentConfiguration environmentConfiguration) {
		this.environmentConfiguration = environmentConfiguration;
	}
	
	public void cleanCell(int x, int y) {
		removeObstaclesAtLocation(x,y);
		paintCell(x, y, AdministratorConstants.backgroundColor);
		int posTileX = x / 20;
		int posTileY = y / 20;
		Location location = new Location(posTileX, posTileY);
		getEnvironmentConfiguration().removeObstacle(location.toString());
		getEnvironmentConfiguration().removeColor(location.toString());
		refresh();	
	}	
	
	public void clear() {
		getBg().clear();
		for (Actor obstacle : getActors(Obstacle.class)) {
			removeActor(obstacle);	
		}		
		getEnvironmentConfiguration().removeAllObstacles();
		getEnvironmentConfiguration().removeAllColors();
		refresh();	
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public long getCicleTotalTime() {
		return cicleTotalTime;
	}

	public void setCicleTotalTime(long cicleTotalTime) {
		this.cicleTotalTime = cicleTotalTime;
	}
	
	
	public long getCicleStartTime() {
		return cicleStartTime;
	}

	public void setCicleStartTime(long cicleStartTime) {
		this.cicleStartTime = cicleStartTime;
	}

	public void act() {
		setCicleTotalTime(System.currentTimeMillis() - getCicleStartTime());
	}
	
	public EnvironmentUI getEnvironmentUI() {
		return environmentUI;
	}
	
	public void refreshEnvironmentConfiguration() {
		Set<String> obstaclesPositions = getEnvironmentConfiguration().getObstacles();
        for (String pos : obstaclesPositions) {
        	String[] strs = pos.subSequence(1, pos.length() -1).toString().split(", ");
        	int x = new Integer(strs[0]).intValue() * AdministratorConstants.TILE_WIDTH;
        	int y = new Integer(strs[1]).intValue() * AdministratorConstants.TILE_WIDTH;
    		addObstacle(new Obstacle(), x, y);
        }
		Map<String, Color> colorsPositions = getEnvironmentConfiguration().getColorsTM();
        for (Map.Entry<String, Color> entry : colorsPositions.entrySet()) {
        	String[] strs = entry.getKey().subSequence(1, entry.getKey().length() -1).toString().split(", ");
        	int x = new Integer(strs[0]).intValue() * AdministratorConstants.TILE_WIDTH;
        	int y = new Integer(strs[1]).intValue() * AdministratorConstants.TILE_WIDTH;
        	paintCell(x, y, entry.getValue());
        }
        getNxt().moveCar(getEnvironmentConfiguration().getPosRobotX(), getEnvironmentConfiguration().getPosRobotY(), 
        		getEnvironmentConfiguration().getDirectionRobot());
        refresh();
	}	

	

}
