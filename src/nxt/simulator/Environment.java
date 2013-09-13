package nxt.simulator;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nxt.simulator.UI.EnvironmentUI;

import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.Sensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import tools.AdministratorConstants;
import tools.EnvironmentActions;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GGMouseListener;
import ch.aplu.jgamegrid.GGTileCollisionListener;
import ch.aplu.jgamegrid.GGTileMap;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

public abstract class Environment extends GameGrid implements
		AdministratorConstants, GGTileCollisionListener, GGMouseListener {

	//private static final long serialVersionUID = 1L;
	// Cuadro de 2m x 1.2m (1 pixel equivale a 2 mm)
	protected static int width = 1000;
	protected static int high = 600;
	protected static Location startLocation = new Location(450, 250);
	protected static double startDirection = START_DIRECTION;
	protected static boolean isNavigationBar = false;
	protected RobotCar nxt = new RobotCar(startLocation, "Brick.png");
	protected EnvironmentConfiguration environmentConfiguration;
	protected static String obstacleImage = "sprites/brickWhite.png";

	public EnvironmentActions getEnvironmentAction() {
		return environmentAction;
	}

	public void setEnvironmentAction(EnvironmentActions environmentAction) {
		this.environmentAction = environmentAction;
	}

	private final int nbHorzTiles = 50;
	private final int nbVertTiles = 30;
	private final int tileSize = 20;
	private GGTileMap tm = createTileMap(nbHorzTiles, nbVertTiles, tileSize,
			tileSize);
	private final int xMapStart = 0;
	private final int yMapStart = 0;	
 
	private EnvironmentActions environmentAction = EnvironmentActions.ADD;
	private boolean dragging = false;
	private boolean addingOrPainting = false;
	
	protected Color color = new Color(YELLOW);
	
	private long cicleTotalTime = System.currentTimeMillis();
	private long cicleStartTime = System.currentTimeMillis();
	
	protected EnvironmentUI environmentUI;		

	public Environment(Location startLocation, double startDirection) {
		super(width, high, 1, null, null, isNavigationBar, 60);
		setTitle("NXT Robot Simulation Environment");
		setSimulationPeriod(SIMULATION_PERIOD);
		setBgColor(new Color(56, 114, 114)); // (50, 100, 100) //(42, 85, 32));
												// //(142, 207, 126));
		// tm.setPosition(new Point(xMapStart, yMapStart));
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
		setNbVertCells(high); //nbVertTiles
		setNbHorzCells(width); //nbHorzTiles		
		setBgColor(new Color(56, 114, 114));
		environmentConfiguration = new EnvironmentConfiguration();
		Sensor.setEnvironment(this);
		clearTileMap(getTm());
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
		addActor(part, nxt.getPartLocation(part), startDirection);
	}

	public void addObstacleWithoutOffset(Obstacle obstacle, int x, int y) {
		super.addActor(obstacle, new Location(x, y));
	}

	public void addObstacle(Obstacle obstacle, int x, int y) {
		Location recalculatedLocation = setOffset(obstacle, new Location(x, y));
		addActor(obstacle, recalculatedLocation);
		obstacle.addCollisionTile(new Location(x, y));
	}

	public void removeObstacle(String obstacleName) {
		removeActor(getObstacle(obstacleName));
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

	public GGTileMap getTm() {
		return tm;
	}
	
	public void setTm(GGTileMap tm) {
		this.tm = tm;
	}
	
	public String getTMObstaclesAsString() {
		String returnedString = "";
		ArrayList<Location> locations = new ArrayList<Location>();
		GGTileMap tm = getTileMap();
		for (int i = 0 ; i < tm.getNbHorzTiles() ; i++) {
			for (int j = 0 ; j < tm.getNbVertTiles() ; j++) {
				Location locAux = new Location(i, j);
				if (tm.isTileCollisionEnabled(locAux)) {
					locations.add(locAux);
				}				
			}
		}
		returnedString = locations.toString();
		return returnedString;
	}
	
	public void setTMObstaclesFromString(String tiles) {
        List<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile("\\d+,\\s\\d+").matcher(tiles);
        while(m.find()) {
            matches.add(m.group());
        }
        GGTileMap tileMap = createTileMap(50, 30, 20, 20);
        clearTileMap(tileMap);
        for (String pos : matches) {
        	String[] strs = pos.split(", ");
        	int x = new Integer(strs[0]).intValue();
        	int y = new Integer(strs[1]).intValue();
    		tileMap.setImage(obstacleImage, x, y);
    		tileMap.setTileCollisionEnabled(new Location(x, y), true);        	
        }
        setTm(tileMap);
        refresh();
	}
	
	public void refreshEnvironmentConfiguration() {
		EnvironmentConfiguration environmentConfiguration = this.getEnvironmentConfiguration();
		List<String> obstaclesPositions = environmentConfiguration.getObstacles();
        GGTileMap tileMap = createTileMap(50, 30, 20, 20);
        clearTileMap(tileMap);
        for (String pos : obstaclesPositions) {
        	String[] strs = pos.subSequence(1, pos.length() -1).toString().split(", ");
        	int x = new Integer(strs[0]).intValue();
        	int y = new Integer(strs[1]).intValue();
    		tileMap.setImage(obstacleImage, x, y);
    		tileMap.setTileCollisionEnabled(new Location(x, y), true);
    		// Se agrega el tile a la lista de colision de los distintos sensores y el robot
    		Location location = new Location(x, y);
    		getNxt().addCollisionTile(location);
    		for (Actor sensor : getActors(Sensor.class)) {
    			sensor.addCollisionTile(location);
    		}
    		for (Actor motor : getActors(Motor.class)) {
    			motor.addCollisionTile(location);
    		}		
        }
		Map<String, Color> colorsPositions = environmentConfiguration.getColorsTM();
        for (Map.Entry<String, Color> entry : colorsPositions.entrySet()) {
        	String[] strs = entry.getKey().subSequence(1, entry.getKey().length() -1).toString().split(", ");
        	int x = new Integer(strs[0]).intValue();
        	int y = new Integer(strs[1]).intValue();
        	paintCell(x, y, entry.getValue());
        }
        getNxt().moveCar(environmentConfiguration.getPosRobotX(), environmentConfiguration.getPosRobotY(), 
        		environmentConfiguration.getDirectionRobot());
       
        setTm(tileMap);
        refresh();
		
//		Matcher m = Pattern.compile("\\d+,\\s\\d+").matcher(tiles);
//        while(m.find()) {
//            matches.add(m.group());
//        }
//        GGTileMap tileMap = createTileMap(50, 30, 20, 20);
//        clearTileMap(tileMap);
//        for (String pos : matches) {
//        	String[] strs = pos.split(", ");
//        	int x = new Integer(strs[0]).intValue();
//        	int y = new Integer(strs[1]).intValue();
//    		tileMap.setImage(obstacleImage, x, y);
//    		tileMap.setTileCollisionEnabled(new Location(x, y), true);        	
//        }
//        setTm(tileMap);
//        refresh();
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
						setToTrueTileCollisionEnabledCel(mouse.getX(), mouse.getY());
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
						setToTrueTileCollisionEnabledCel(mouse.getX(), mouse.getY());
					} else if (EnvironmentActions.PAINT.equals(getEnvironmentAction())) {
						paintCell(mouse.getX(), mouse.getY());
					}
				}			
				break;
			case GGMouse.lRelease:
				dragging = false;
				addingOrPainting = false;
				break;
			case GGMouse.rPress:
				if (clickOnRobot) {
					rotateLeftNXT(15);
				}
				break;
			}
		}
		return true;
	}

	protected void paintCell() {
		GGBackground bg = getBg();
	    Color c = getBgColor();
	    bg.setPaintColor(color);
	    bg.fillCircle(new Point(500, 250), 150);
	    bg.setPaintColor(c);
	    bg.fillCircle(new Point(500, 250), 130);
	    refresh();
	}
	
	protected void paintCell(int x, int y) {
		int posTileX = x / 20;
		int posTileY = y / 20;
		int posX = posTileX * AdministratorConstants.TILE_WIDTH;
		int posY = posTileY * AdministratorConstants.TILE_WIDTH;
		
		GGBackground bg = getBg();
	    //Color color = Color.black;
	    bg.setPaintColor(color);
	    bg.fillRectangle(new Point(posX, posY), 
	    		new Point(posX + AdministratorConstants.TILE_WIDTH, posY + AdministratorConstants.TILE_WIDTH));
//	    bg.fillCircle(new Point(500, 250), 150);
//	    bg.setPaintColor(c);
//	    bg.fillCircle(new Point(500, 250), 130);
//	    colorsTM.put(new Location(posTileX, posTileY), Color.black);
	    //Se agrega la localización en la configuración
	    Location location = new Location(posX, posY);
	    getEnvironmentConfiguration().addColor(location.toString(), color);
//	    show();
	    refresh();
	}
	
	protected void moveNXT(int x, int y) {
		getNxt().moveCar(x, y, getNxt().getDirection());
		getEnvironmentConfiguration().setPosRobotX(x);
		getEnvironmentConfiguration().setPosRobotY(y);
		refresh();
	}

	protected void rotateLeftNXT(double angle) {
		double newDirection = (getNxt().getDirection() + angle) % 360;
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
	

	protected void paintCell(int x, int y, Color color) {
		int posTileX = x / 20;
		int posTileY = y / 20;
		int posX = posTileX * AdministratorConstants.TILE_WIDTH;
		int posY = posTileY * AdministratorConstants.TILE_WIDTH;
		
		GGBackground bg = getBg();
	    //Color color = Color.black;
	    bg.setPaintColor(color);
	    bg.fillRectangle(new Point(posX, posY), 
	    		new Point(posX + AdministratorConstants.TILE_WIDTH, posY + AdministratorConstants.TILE_WIDTH));
	    //Se agrega la localización en la configuración
	    Location location = new Location(posX, posY);
	    getEnvironmentConfiguration().addColor(location.toString(), color);
	    refresh();
	}
	
	
	public EnvironmentConfiguration getEnvironmentConfiguration() {
		return environmentConfiguration;
	}

	public void setEnvironmentConfiguration(
			EnvironmentConfiguration environmentConfiguration) {
		this.environmentConfiguration = environmentConfiguration;
	}

	/**
	 * @param x Cordenada del eje x
	 * @param y Cordenada del eje y
	 **/
	public void setToTrueTileCollisionEnabledCel(int x, int y) {
		// Obtengo la esquina superior del tile para luego setear la propiedad
		// de colision a true

		int posTileX = x / 20;
		int posTileY = y / 20;
		Location location = new Location(posTileX, posTileY);
		if (!getTileMap().isTileCollisionEnabled(location)) {
			getTileMap().setImage(obstacleImage, posTileX, posTileY);
			getTileMap().setTileCollisionEnabled(location, true);
			// Se agrega el tile a la lista de colision de los distintos sensores y el robot
			getNxt().addCollisionTile(location);
			for (Actor sensor : getActors(Sensor.class)) {
				sensor.addCollisionTile(location);
			}
			for (Actor motor : getActors(Motor.class)) {
				motor.addCollisionTile(location);
			}		
			// Se agrega la localizacion a la configuracion
			getEnvironmentConfiguration().addObstacle(location.toString());
			refresh();
		}
	}
	
	public void cleanCell(int x, int y) {
		// Obtengo la esquina superior del tile para luego setear la propiedad
		// de colision a true

		int posTileX = x / 20;
		int posTileY = y / 20;
		Location location = new Location(posTileX, posTileY);
		if (getTileMap().isTileCollisionEnabled(location)) {
			getTileMap().setImage("", posTileX, posTileY);
			getTileMap().setTileCollisionEnabled(location, false);
			// Se quita el tile a la lista de colision de los distintos sensores y el robot
			ArrayList<Location> aux = getNxt().getCollisionTiles(); 
			aux.remove(location);			
			for (Actor sensor : getActors(UltrasonicSensor.class)) {
				aux = sensor.getCollisionTiles(); 
				aux.remove(location);			
			}
			for (Actor motor : getActors(Motor.class)) {
				aux = motor.getCollisionTiles(); 
				aux.remove(location);			
			}		
			// Se quita la localizacion a la configuracion
			getEnvironmentConfiguration().removeObstacle(location.toString());
			refresh();
		}
	}	
	
	
	public void clearTileMap(GGTileMap tileMap) {
//		setTm(createTileMap(50, 30, 20, 20));
		//Se setea la propiedad isTileCollisionEnabled a false para todas las celdas del tileMap
		GGTileMap tm = tileMap;
		for (int i = 0 ; i < tm.getNbHorzTiles() ; i++) {
			for (int j = 0 ; j < tm.getNbVertTiles() ; j++) {
				Location locAux = new Location(i, j);
				if (tm.isTileCollisionEnabled(locAux)) {
					tm.setTileCollisionEnabled(locAux, false);
				}				
			}
		}					
//		refresh();			
	}
	
	public void clear() {
		setTm(createTileMap(50, 30, 20, 20));
		//Se setea la propiedad isTileCollisionEnabled a false para todas las celdas del tileMap
//		GGTileMap tm = getTileMap();
//		for (int i = 0 ; i < tm.getNbHorzTiles() ; i++) {
//			for (int j = 0 ; j < tm.getNbVertTiles() ; j++) {
//				Location locAux = new Location(i, j);
//				if (tm.isTileCollisionEnabled(locAux)) {
//					tm.setTileCollisionEnabled(locAux, false);
//				}				
//			}
//		}
		clearTileMap(this.getTm());
		getBg().clear();
		//Se limpia la lista de tiles
		ArrayList<Location> aux = nxt.getCollisionTiles(); 
		aux.removeAll(nxt.getCollisionTiles());
		
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
	

}
