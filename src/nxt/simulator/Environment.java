package nxt.simulator;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.Sensor;
import tools.AdministratorConstants;
import tools.EnvironmentActions;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGActorCollisionListener;
import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GGMouseListener;
import ch.aplu.jgamegrid.GGTileCollisionListener;
import ch.aplu.jgamegrid.GGTileMap;
import ch.aplu.jgamegrid.GGToggleButtonListener;
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

	public Environment(Location startLocation, double startDirection) {
		super(width, high, 1, null, null, isNavigationBar, 60);
		setTitle("NXT Robot Simulation Environment");
		setSimulationPeriod(SIMULATION_PERIOD);
		setBgColor(new Color(56, 114, 114)); // (50, 100, 100) //(42, 85, 32));
												// //(142, 207, 126));
		// tm.setPosition(new Point(xMapStart, yMapStart));

		Sensor.setEnvironment(this);

		addActor(nxt, startLocation, startDirection);
		addPart(new Motor(MotorPort.A));
		addPart(new Motor(MotorPort.B));

		show();
	}

	public Environment() {
		super();
		setCellSize(20);
		setGridColor(Color.red);
		setNbVertCells(nbVertTiles);
		setNbHorzCells(nbHorzTiles);
		setBgColor(new Color(56, 114, 114));
		clearTileMap(getTm());
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
	
	public String getTmAsString() {
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
	
	public void setTmFromString(String tiles) {
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
    		tileMap.setImage("sprites/brick.gif", x, y);
    		tileMap.setTileCollisionEnabled(new Location(x, y), true);        	
        }
        setTm(tileMap);
        refresh();
	}

	public boolean mouseEvent(GGMouse mouse) {
		if (EnvironmentActions.ADD.equals(getEnvironmentAction())) {
			switch (mouse.getEvent()) {
			case GGMouse.lClick:
				setToTrueTileCollisionEnabledCel(mouse.getX(), mouse.getY());
				break;
			 case GGMouse.lDrag:
				 setToTrueTileCollisionEnabledCel(mouse.getX(), mouse.getY());
			 break;
			}
		}
			return true;
	}

	public void setToTrueTileCollisionEnabledCel(int x, int y) {
		// Obtengo la esquina superior del tile para luego setear la propiedad
		// de colision a true
		// int offsetX = x % 20;
		// int offsetY = y % 20;

		int posTileX = x / 20;
		int posTileY = y / 20;
		getTileMap().setImage("sprites/brick.gif", posTileX, posTileY);
		getTileMap().setTileCollisionEnabled(new Location(posTileX, posTileY),
				true);
		refresh();
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

}
