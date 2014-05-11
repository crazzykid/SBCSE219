package pathx_file;

import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;
import java.util.AbstractList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import pathX_ui.PathXCar;
import pathX_ui.PathXCarState;
import pathx.PathX.PathXPropertyType;
import pathx_data.PathXRecord;
import pathx_data.PathXDataModel;
import pathX_ui.PathXMiniGame;
import pathx_data.PathXLevelRecord;
import pathx_data.PathXRecord;
import properties_manager.PropertiesManager;
import static pathx.PathXConstants.*;
import xml_utilities.XMLUtilities;
import pathx_data.PathXGameLevel;
import pathx_file.Connection;

/**
 * This class provides services for efficiently loading and saving
 * binary files for The Sorting Hat game application.
 *
 * @author Richard McKenna & __Lamar Myles_________________
 */
public class PathXFileManager
{
    // WE'LL LET THE GAME KNOW WHEN DATA LOADING IS COMPLETE
    private PathXMiniGame miniGame;
    // THIS WILL HELP US PARSE THE XML FILES
    private XMLUtilities xmlUtil;
    private PathXGameLevel level;
    // THIS IS THE SCHEMA WE'LL USE
    private File levelSchema;
    private TreeMap<String, Road> roadMap;
    private TreeMap<String, Intersection> intersectionMap;
    private ArrayList<Road> roadToLoad;
    private ArrayList<Intersection> intersectionToLoad;
    private PathXDataModel data;
    private ArrayList<PathXGameLevel> levelList;
    private  TreeMap<String, ArrayList<Connection>> gamePathsNodes;
    private int count;
    private ArrayList<PathXCar> gameCars;
    
    private ArrayList<Intersection> playerPath;
    /**
     * Constructor for initializing this file manager, it simply keeps
     * the game for later.
     *
     * @param initMiniGame The game for which this class loads data.
     */
    public PathXFileManager(PathXMiniGame initMiniGame)
    {
        // KEEP IT FOR LATER
        miniGame = initMiniGame;
        // THIS KNOWS HOW TO READ AND ACCESS XML FILES
        xmlUtil = new XMLUtilities();
        
        level = new PathXGameLevel();
        
        roadToLoad = new ArrayList<Road>();
        
        roadMap = new TreeMap();
        intersectionMap = new TreeMap();
        gameCars = new ArrayList();
        count =0;
        
        playerPath = new ArrayList<Intersection>();
        gamePathsNodes = new TreeMap<String, ArrayList<Connection>>();
        // WE'LL USE THE SCHEMA FILE TO VALIDATE THE XML FILES
        // levelSchema = initLevelSchema;

       
    }
    
    public void restPathXCar()          { gameCars = new ArrayList();}
    public ArrayList<Intersection> getPlayerPath() { return playerPath;}
    public PathXGameLevel       getLevel()                  {   return level;}
    public Intersection getInter(int i){     return intersectionToLoad.get(i); }
    
    public void addPlayer()
    {
        float x, y;
        SpriteType sT;
        Sprite s;
        BufferedImage img;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
        String playerButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_PLAYER);
        sT = new SpriteType(GAME_BUTTON_PLAYER_TYPE);
        
        
        img= ((PathXMiniGame)miniGame).loadImage(imgPath + playerButton);
        data.addSpriteType(sT);
        
        sT.addState(PathXCarState.INVISIBLE_STATE.toString(), img); // DOESN'T MATTER
        sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
        
        int startX = data.getLevel().getStartingLocation().getX();
        
        int startY = data.getLevel().getStartingLocation().getY();
        
        ArrayList<Connection> startLocation = this.getAllNeighbors(data.getLevel().getStartingLocation().getId());
       
        float totalX = ((startX + intersectionMap.get(startLocation.get(0).Intersection2ID).getX())/2) + LEVEL1X;
        
        float totalY = ((startY + intersectionMap.get(startLocation.get(0).Intersection2ID).getY()) /2) + LEVEL1Y;
       
        PathXCar player = new PathXCar(sT, totalX, totalY, 10, 10, PathXCarState.INVISIBLE_STATE.toString());
        
        player.setCarType(PLAYER);
        
        gameCars.add(player);
    }
    
    public boolean loadLevel(String levelFile, int pos)
    {
        // gameCars = new ArrayList<PathXCar>();
        data = (PathXDataModel)miniGame.getDataModel();
        
        // levelList = ((PathXDataModel)data).getLevelLocation();
        
        // level = levelList.get(pos);
        
        
       // if(!level.getLoad() && level.getStageUnlock())
            try
            {
                File fileToOpen = new File(LEVELS_PATH+ levelFile +".xml");
                levelSchema = new File(PATH_DATA + "PathXLevelSchema.xsd");
                
                // WE'LL FILL IN SOME OF THE LEVEL OURSELVES
                PathXGameLevel levelToLoad = ((PathXDataModel)data).getLevel();
                
                levelToLoad.reset();
                
                // FIRST LOAD ALL THE XML INTO A TREE
                Document doc = xmlUtil.loadXMLDocument( fileToOpen.getAbsolutePath(),
                        levelSchema.getAbsolutePath());
                
                // FIRST LOAD THE LEVEL INFO
                Node levelNode = doc.getElementsByTagName(LEVEL_NODE).item(0);
                NamedNodeMap attributes = levelNode.getAttributes();
                String levelName = attributes.getNamedItem(NAME_ATT).getNodeValue();
                levelToLoad.setLevelName(levelFile);
                
                String bgImageName = attributes.getNamedItem(IMAGE_ATT).getNodeValue();
                data.updateBackgroundImage(bgImageName);
                
                // THEN LET'S LOAD THE LIST OF ALL THE REGIONS
                loadIntersectionsList(doc, levelToLoad);
                ArrayList<Intersection> intersections = levelToLoad.getIntersections();
                
                // AND NOW CONNECT ALL THE REGIONS TO EACH OTHER
                loadRoadsList(doc, levelToLoad);
                
                // LOAD THE START INTERSECTION
                Node startIntNode = doc.getElementsByTagName(START_INTERSECTION_NODE).item(0);
                attributes = startIntNode.getAttributes();
                String startIdText = attributes.getNamedItem(ID_ATT).getNodeValue();
                int startId = Integer.parseInt(startIdText);
                String startImageName = attributes.getNamedItem(IMAGE_ATT).getNodeValue();
                Intersection startingIntersection = intersections.get(startId);
                levelToLoad.setStartingLocation(startingIntersection);
                data.updateStartingLocationImage(startImageName);
                
                // LOAD THE DESTINATION
                Node destIntNode = doc.getElementsByTagName(DESTINATION_INTERSECTION_NODE).item(0);
                attributes = destIntNode.getAttributes();
                String destIdText = attributes.getNamedItem(ID_ATT).getNodeValue();
                int destId = Integer.parseInt(destIdText);
                String destImageName = attributes.getNamedItem(IMAGE_ATT).getNodeValue();
                levelToLoad.setDestination(intersections.get(destId));
                data.updateDestinationImage(destImageName);
                
                // LOAD THE MONEY
                Node moneyNode = doc.getElementsByTagName(MONEY_NODE).item(0);
                attributes = moneyNode.getAttributes();
                String moneyText = attributes.getNamedItem(AMOUNT_ATT).getNodeValue();
                int money = Integer.parseInt(moneyText);
                levelToLoad.setMoney(money);
                
                // LOAD THE NUMBER OF POLICE
                Node policeNode = doc.getElementsByTagName(POLICE_NODE).item(0);
                attributes = policeNode.getAttributes();
                String policeText = attributes.getNamedItem(NUM_ATT).getNodeValue();
                int numPolice = Integer.parseInt(policeText);
                levelToLoad.setNumPolice(numPolice);
                for(int i =0; i < numPolice; i++)
                {
                    float x, y;
                    SpriteType sT;
                    Sprite s;
                    BufferedImage img;
                    PropertiesManager props = PropertiesManager.getPropertiesManager();
                    String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
                    String policeButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_POLICE);
                    sT = new SpriteType(GAME_BUTTON_POLICE_TYPE);
                
                    img= ((PathXMiniGame)miniGame).loadImage(imgPath + policeButton);
                    data.addSpriteType(sT);
                    
                    sT.addState(PathXCarState.INVISIBLE_STATE.toString(), img); // DOESN'T MATTER
                    sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
                    int Vx =0;
                    int Vy =0;
                    for(int j=0; j<intersections.size(); j++)
                    {
                       int randomNum = (int)(Math.random()*(intersections.size()));
                        System.out.println("random mun a a" + randomNum);
                        Intersection random = intersections.get(randomNum);
                        
                        if (random.getId() != levelToLoad.getStartingLocation().getId() && random.getId() != levelToLoad.getDestination().getId() && Vx==0)
                        {
                            Vx = random.getX() + LEVEL1X ;
                            Vy = random.getY() +LEVEL1Y ;  
                        }
                    }
                    PathXCar police = new PathXCar(sT, Vx, Vy, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
                    police.setCarType(POLICE);
                    gameCars.add(police);
                }
                // LOAD THE NUMBER OF BANDITS
                Node banditsNode = doc.getElementsByTagName(BANDITS_NODE).item(0);
                attributes = banditsNode.getAttributes();
                String banditsText = attributes.getNamedItem(NUM_ATT).getNodeValue();
                int numBandits = Integer.parseInt(banditsText);
                levelToLoad.setNumBandits(numBandits);
                for(int i =0; i < numBandits; i++)
                {
                    float x, y;
                    SpriteType sT;
                    Sprite s;
                    BufferedImage img;
                    PropertiesManager props = PropertiesManager.getPropertiesManager();
                    String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
                    String banditsButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_BANDIT);
                    sT = new SpriteType(GAME_BUTTON_BANDIT_TYPE);
        
                    img= ((PathXMiniGame)miniGame).loadImage(imgPath + banditsButton);
                    data.addSpriteType(sT);
                    
                    sT.addState(PathXCarState.INVISIBLE_STATE.toString(), img); // DOESN'T MATTER
                    sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
                    
                    int Vx =0;
                    int Vy =0;
                    for(int j=0; j<intersections.size(); j++)
                    {
                        int randomNum = (int)(Math.random()*(intersections.size()));
                        System.out.println("random mun a a" + randomNum);
                        Intersection random = intersections.get(randomNum);
                        
                        if (random.getId() != levelToLoad.getStartingLocation().getId() && random.getId() != levelToLoad.getDestination().getId() && Vx==0)
                        {
                            Vx = random.getX() + LEVEL1X;
                            Vy = random.getY() + LEVEL1Y;
                            
                        }
                    }
                    
                    PathXCar bandit = new PathXCar(sT, Vx, Vy, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
                    
                    bandit.setCarType(BANDIT);
                    gameCars.add(bandit);
                }
                
                // LOAD THE NUMBER OF ZOMBIES
                Node zombiesNode = doc.getElementsByTagName(ZOMBIES_NODE).item(0);
                attributes = zombiesNode.getAttributes();
                String zombiesText = attributes.getNamedItem(NUM_ATT).getNodeValue();
                int numZombies = Integer.parseInt(zombiesText);
                levelToLoad.setNumZombies(numZombies);
                for(int i =0; i < numZombies; i++)
                {
                    float x, y;
                    SpriteType sT;
                    Sprite s;
                    BufferedImage img;
                    PropertiesManager props = PropertiesManager.getPropertiesManager();
                    String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
                    String zombieButton = props.getProperty(PathXPropertyType.GAME_SCREEN_IMAGE_BUTTON_ZOMBIE);
                    sT = new SpriteType(GAME_BUTTON_ZOMBIE_TYPE);
                    
                    
                    img= ((PathXMiniGame)miniGame).loadImage(imgPath + zombieButton);
                    data.addSpriteType(sT);
                    
                    sT.addState(PathXCarState.INVISIBLE_STATE.toString(), img); // DOESN'T MATTER
                    sT.addState(PathXCarState.VISIBLE_STATE.toString(), img);
                    
                    int Vx =0;
                    int Vy =0;
                    for(int j=0; j<intersections.size(); j++)
                    {
                        int randomNum = (int)(Math.random()*(intersections.size()));
                        System.out.println("random mun a a" + randomNum);
                        Intersection random = intersections.get(randomNum);
                        
                        if (random.getId() != levelToLoad.getStartingLocation().getId() && random.getId() != levelToLoad.getDestination().getId() && Vx==0)
                        {
                            Vx = random.getX() + LEVEL1X;
                            Vy = random.getY() + LEVEL1Y;
                            
                        }
                    }
                    PathXCar zombie = new PathXCar(sT, Vx, Vy, 0, 0, PathXCarState.INVISIBLE_STATE.toString());
                    
                    zombie.setCarType(ZOMBIE);
                    gameCars.add(zombie);
                }
                
                // EVERYTHING WENT AS PLANNED SO LET'S MAKE IT PERMANENT
                // PathXDataModel dataModel = (PathXDataModel)miniGame.getDataModel();
                // Viewport viewport = data.getViewport();
                //    viewport.setGameWorldSize(numColumns * TILE_WIDTH, numRows * TILE_HEIGHT);
                //  viewport.setNorthPanelHeight(NORTH_PANEL_HEIGHT);
                //  viewport.initViewportMargins();
                data.setCurrentLevel(levelFile);
                data.initLevel(levelFile, intersections);
                
                // if(levelFile.equals(LEVEL1))
                // levelToLoad.setStageUnlock(true);
                // data.setLevel(levelToLoad, count);
                
                this.initIDs();
                this.addPlayer();
                data.initTiles();
                return true;
            }
            catch(Exception e)
            {
                
                // LEVEL DIDN'T LOAD PROPERLY
                return false;
            }
        // LEVEL LOADED PROPERLY
       
    } 
    // PRIVATE HELPER METHOD FOR LOADING INTERSECTIONS INTO OUR LEVEL
    private void loadIntersectionsList( Document doc, PathXGameLevel levelToLoad)
    {
        // FIRST GET THE REGIONS LIST
        Node intersectionsListNode = doc.getElementsByTagName(INTERSECTIONS_NODE).item(0);
        ArrayList<Intersection> intersections = levelToLoad.getIntersections();
        
        // AND THEN GO THROUGH AND ADD ALL THE LISTED REGIONS
        ArrayList<Node> intersectionsList = xmlUtil.getChildNodesWithName(intersectionsListNode, INTERSECTION_NODE);
        for (int i = 0; i < intersectionsList.size(); i++)
        {
            // GET THEIR DATA FROM THE DOC
            Node intersectionNode = intersectionsList.get(i);
            NamedNodeMap intersectionAttributes = intersectionNode.getAttributes();
            String idText = intersectionAttributes.getNamedItem(ID_ATT).getNodeValue();
            String openText = intersectionAttributes.getNamedItem(OPEN_ATT).getNodeValue();
            String xText = intersectionAttributes.getNamedItem(X_ATT).getNodeValue();
            int x = Integer.parseInt(xText);
            String yText = intersectionAttributes.getNamedItem(Y_ATT).getNodeValue();
            int y = Integer.parseInt(yText);
            
            // NOW MAKE AND ADD THE INTERSECTION
            Intersection newIntersection = new Intersection(x, y);
            newIntersection.open = Boolean.parseBoolean(openText);
            intersections.add(newIntersection);
            
        }
        intersectionToLoad = intersections;
    }
    
    // PRIVATE HELPER METHOD FOR LOADING ROADS INTO OUR LEVEL
    private void loadRoadsList( Document doc, PathXGameLevel levelToLoad)
    {
        // FIRST GET THE REGIONS LIST
        Node roadsListNode = doc.getElementsByTagName(ROADS_NODE).item(0);
        ArrayList<Road> roads = levelToLoad.getRoads();
        ArrayList<Intersection> intersections = levelToLoad.getIntersections();
        
        // AND THEN GO THROUGH AND ADD ALL THE LISTED REGIONS
        ArrayList<Node> roadsList = xmlUtil.getChildNodesWithName(roadsListNode, ROAD_NODE);
        for (int i = 0; i < roadsList.size(); i++)
        {
            // GET THEIR DATA FROM THE DOC
            Node roadNode = roadsList.get(i);
            NamedNodeMap roadAttributes = roadNode.getAttributes();
            String id1Text = roadAttributes.getNamedItem(INT_ID1_ATT).getNodeValue();
            int int_id1 = Integer.parseInt(id1Text);
            String id2Text = roadAttributes.getNamedItem(INT_ID2_ATT).getNodeValue();
            int int_id2 = Integer.parseInt(id2Text);
            String oneWayText = roadAttributes.getNamedItem(ONE_WAY_ATT).getNodeValue();
            boolean oneWay = Boolean.parseBoolean(oneWayText);
            String speedLimitText = roadAttributes.getNamedItem(SPEED_LIMIT_ATT).getNodeValue();
            int speedLimit = Integer.parseInt(speedLimitText);
            
            // NOW MAKE AND ADD THE ROAD
            Road newRoad = new Road();
            newRoad.setNode1(intersections.get(int_id1));
            newRoad.setNode2(intersections.get(int_id2));
            newRoad.setOneWay(oneWay);
            newRoad.setSpeedLimit(speedLimit);
            newRoad.calculateWeight();
            roads.add(newRoad);
            
            
        }
        
        roadToLoad =roads;
    }
    
    /**
     * This method saves the level currently being edited to the levelFile. Note
     * that it will be saved as an .xml file, which is an XML-format that will
     * conform to the schema.
     */
    public boolean saveLevel(File levelFile, PathXGameLevel levelToSave)
    {
        try
        {
            // THESE WILL US BUILD A DOC
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            // FIRST MAKE THE DOCUMENT
            Document doc = docBuilder.newDocument();
            
            // THEN THE LEVEL (i.e. THE ROOT) ELEMENT
            Element levelElement = doc.createElement(LEVEL_NODE);
            doc.createAttribute(NAME_ATT);
            levelElement.setAttribute(NAME_ATT, levelToSave.getLevelName());
            doc.appendChild(levelElement);
            doc.createAttribute(IMAGE_ATT);
            levelElement.setAttribute(IMAGE_ATT, levelToSave.getBackgroundImageFileName());
            
            // THEN THE INTERSECTIONS
            Element intersectionsElement = makeElement(doc, levelElement, INTERSECTIONS_NODE, "");
            
            // AND LET'S ADD EACH INTERSECTION
            int id = 0;
            doc.createAttribute(ID_ATT);
            doc.createAttribute(X_ATT);
            doc.createAttribute(Y_ATT);
            doc.createAttribute(OPEN_ATT);
            for (Intersection i : levelToSave.getIntersections())
            {
                // MAKE AN INTERSECTION NODE AND ADD IT
                Element intersectionNodeElement = makeElement(doc, intersectionsElement,
                        INTERSECTION_NODE, "");
                
                // NOW LET'S FILL IN THE INTERSECTION'S DATA. FIRST MAKE THE ATTRIBUTES
                intersectionNodeElement.setAttribute(ID_ATT,    "" + id);
                intersectionNodeElement.setAttribute(X_ATT,     "" + i.x);
                intersectionNodeElement.setAttribute(Y_ATT,     "" + i.y);
                intersectionNodeElement.setAttribute(OPEN_ATT,  "" + i.open);
            }
            
            // AND NOW ADD ALL THE ROADS
            Element roadsElement = makeElement(doc, levelElement, ROADS_NODE, "");
            doc.createAttribute(INT_ID1_ATT);
            doc.createAttribute(INT_ID2_ATT);
            doc.createAttribute(SPEED_LIMIT_ATT);
            doc.createAttribute(ONE_WAY_ATT);
            ArrayList<Intersection> intersections = levelToSave.getIntersections();
            for (Road r : levelToSave.getRoads())
            {
                // MAKE A ROAD NODE AND ADD IT TO THE LIST
                Element roadNodeElement = makeElement(doc, roadsElement, ROAD_NODE, "");
                int intId1 = intersections.indexOf(r.getNode1());
                roadNodeElement.setAttribute(INT_ID1_ATT, "" + intId1);
                int intId2 = intersections.indexOf(r.getNode2());
                roadNodeElement.setAttribute(INT_ID2_ATT, "" + intId2);
                roadNodeElement.setAttribute(SPEED_LIMIT_ATT, "" + r.getSpeedLimit());
                roadNodeElement.setAttribute(ONE_WAY_ATT, "" + r.isOneWay());
            }
            
            // NOW THE START INTERSECTION
            Element startElement = makeElement(doc, levelElement, START_INTERSECTION_NODE, "");
            int startId = intersections.indexOf(levelToSave.getStartingLocation());
            startElement.setAttribute(ID_ATT, "" + startId);
            startElement.setAttribute(IMAGE_ATT, levelToSave.getStartingLocationImageFileName());
            
            // AND THE DESTINATION
            Element destElement = makeElement(doc, levelElement, DESTINATION_INTERSECTION_NODE, "");
            int destId = intersections.indexOf(levelToSave.getDestination());
            destElement.setAttribute(ID_ATT, "" + destId);
            destElement.setAttribute(IMAGE_ATT, levelToSave.getDestinationImageFileName());
            
            // NOW THE MONEY
            Element moneyElement = makeElement(doc, levelElement, MONEY_NODE, "");
            doc.createAttribute(AMOUNT_ATT);
            moneyElement.setAttribute(AMOUNT_ATT, "" + levelToSave.getMoney());
            
            // AND THE POLICE COUNT
            Element policeElement = makeElement(doc, levelElement, POLICE_NODE, "");
            doc.createAttribute(NUM_ATT);
            policeElement.setAttribute(NUM_ATT, "" + levelToSave.getNumPolice());
            
            // AND THE BANDIT COUNT
            Element banditElement = makeElement(doc, levelElement, BANDITS_NODE, "");
            banditElement.setAttribute(NUM_ATT, "" + levelToSave.getNumBandits());
            
            // AND FINALLY THE ZOMBIES COUNT
            Element zombiesElement = makeElement(doc, levelElement, ZOMBIES_NODE, "");
            zombiesElement.setAttribute(NUM_ATT, "" + levelToSave.getNumZombies());
            
            // THE TRANSFORMER KNOWS HOW TO WRITE A DOC TO
            // An XML FORMATTED FILE, SO LET'S MAKE ONE
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, YES_VALUE);
            transformer.setOutputProperty(XML_INDENT_PROPERTY, XML_INDENT_VALUE);
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(levelFile);
            
            // SAVE THE POSE TO AN XML FILE
            transformer.transform(source, result);
            
            // SUCCESS
            return true;
        }
        catch(TransformerException | ParserConfigurationException | DOMException | HeadlessException ex)
        {
            // SOMETHING WENT WRONG
            return false;
        }
    }
    
    // THIS HELPER METHOD BUILDS ELEMENTS (NODES) FOR US TO HELP WITH
    // BUILDING A Doc WHICH WE WOULD THEN SAVE TO A FILE.
    private Element makeElement(Document doc, Element parent, String elementName, String textContent)
    {
        Element element = doc.createElement(elementName);
        element.setTextContent(textContent);
        parent.appendChild(element);
        return element;
    }
    
    /*
    public ArrayList<Intersection> findShortestPath(Intersection startLocation, Intersection endLocation)
    {
    // WE'LL MAINTAIN A SHORTEST PATH FROM THE
    // STARTING ACTOR TO EACH ACTOR WE ENCOUNTER
    TreeMap<String, ArrayList<Road>> shortestPaths;
    shortestPaths = new TreeMap();
    
    // THIS WILL STORE THE PATH WE ARE CURRENTLY
    // BUILDING UPON
    ArrayList<Road> currentPath;
    
    // WE ARE USING A BREADTH FIRST SEARCH, AND
    // WE'LL ONLY CHECK EACH Intersection AND Film ONCE
    // WE ARE USING 2 DATA STRUCTURES FOR EACH
    // BECAUSE WE WILL USE ONE AS A LIST OF
    // ITEMS TO CHECK IN ORDER, AND ANOTHER
    // FOR FAST SEARCHING
    ArrayList<Intersection> intersectionVisited = new ArrayList();
    TreeMap<String, Intersection> intersectionVisitedFast = new TreeMap();
    
    // INDEX OF INTERSECTION TO CHECK
    int intersectionIndex = 0;
    
    
    // THE SHORTEST PATH FROM THE START INTERSECTION
    // TO THE START ACTOR IS NOTHING, SO WE'll
    // START OUT WITH AN EMPTY ArrayList
    intersectionVisited.add(startLocation);
    intersectionVisitedFast.put(startLocation.getId(), startLocation);
    
    shortestPaths.put(startLocation.getId(), new ArrayList<Road>());
    // GO THROUGH ALL THE ACTORS WE HAVE REACHED
    // NEVER RE-VISITING AN ACTOR
    
    while (intersectionIndex < intersectionVisited.size())
    {
    // FIRST GET ALL THE MOVIES FOR THE
    // ACTOR AT THE actorIndex
    Intersection currentIntersection = intersectionVisited.get(intersectionIndex);
    
    // MAKE THE SHORTEST PATH FOR THE CURRENT
    // ACTOR THE CURRENT PATH, SINCE WE WILL
    // BUILD ON IT
    currentPath = shortestPaths.get(currentIntersection.getId());
    
    
    
    // NOW GO THROUGH THE FILMS AND GET
    // ALL THE ACTORS WHO WERE IN THOSE
    // FILMS, DO NOT GET ACTORS ALREADY
    // VISITED
    
    Iterator<Road> roadIDs = currentPath.iterator();
    while (roadIDs.hasNext())
    {
    String intersectionID = roadIDs.next().getNode1().getId();
    Intersection intersectionToTest = actors.get(actorID);
    if (!actorsVisitedFast.containsKey(actorID))
    {
    actorsVisited.add(actorToTest);
    actorsVisitedFast.put(actorID, actorToTest);
    ArrayList<Intersection> actorPath;
    actorPath = (ArrayList<Connection>) currentPath.clone();
    Connection c = new Connection(currentFilm.getId(),
    currentActor.getId(),
    actorToTest.getId());
    actorPath.add(c);
    shortestPaths.put(actorID, actorPath);
    
    // IF THIS IS KEVIN BACON WE'RE DONE
    if (actorID.equals(kevinBacon.getId()))
    {
    return actorPath;
    }
    }
    }
    filmIndex++;
    }
    actorIndex++;
    }
    return new ArrayList();
    } */
    
    public PathXCar playerToRender()
    {
        for(int i =0; i< gameCars.size(); i++)
        {
            if (gameCars.get(i).getCarType().equals(PLAYER) )
                return gameCars.get(i);
        }
        return null;
    }
    public ArrayList<PathXCar> tileToRender()
    {
        
        return gameCars;
        
    }
    
    public void initIDs()
    { 
        Iterator<Intersection> loadIntersection =intersectionToLoad.iterator();
        while(loadIntersection.hasNext())
        {
            Intersection theInt = loadIntersection.next();
            String id = theInt.getId();
            Iterator<Road> loadRoads =roadToLoad.iterator();
            while(loadRoads.hasNext())
            {
                Road start = loadRoads.next();
                
                Intersection int1 = start.getNode1();
                Intersection int2 = start.getNode2();
                
                if(id.equals(int1.getId()) || id.equals(int2.getId()))
                theInt.addRoadID(start.getRoadId());
            }
        }
        Iterator<Road> loadRoads =roadToLoad.iterator();
        while(loadRoads.hasNext())
        {
            Road theRd = loadRoads.next();
            String rdID1 =theRd.getNode1().getId();
            String rdID2 = theRd.getNode2().getId();
            
            Iterator<Intersection> loadInt = intersectionToLoad.iterator();
            while(loadInt.hasNext())
            {
                Intersection startInt = loadInt.next();
                String IntId = startInt.getId();
                
                if( rdID1.equals(IntId))
                    theRd.addIntersectionID(IntId);
                
                
                if( rdID2.equals(IntId))
                    theRd.addIntersectionID(IntId);
            }
        }   
        Iterator<Road> theRoads =roadToLoad.iterator();
        while(theRoads.hasNext())
        {
            Road load = theRoads.next();
            roadMap.put(load.getRoadId(), load);
        }
        
        Iterator<Intersection> theintersects =intersectionToLoad.iterator();
        while(theintersects.hasNext())
        {
            Intersection loadInt = theintersects.next();
            intersectionMap.put(loadInt.getId(), loadInt);
        }
        
    }
    
    public TreeMap<String, Road> getRoadMap() {
        return roadMap;
    }
    
    public TreeMap<String, Intersection> getIntersectionMap() {
        return intersectionMap;
    }
    
    public ArrayList<Connection> getAllNeighbors(String intersection)
    {
        
        ArrayList<Connection> connections = new ArrayList();
        Intersection theIntersection = intersectionMap.get(intersection);
        for (int i = 0; i < theIntersection.getRoadIDs().size(); i++)
        {
            String roadID = theIntersection.getRoadIDs().get(i);
            Road road = roadMap.get(roadID);
            for (int j = 0; j < road.getIntersectionIDs().size(); j++)
            {
                System.out.println("SIZE: " + (road.getIntersectionIDs().size()));
                String theIntersection2 = road.getIntersectionIDs().get(j);
                if (!theIntersection2.equals(intersection))
                {
                    Connection connection;
                    connection = new Connection(roadID, intersection, theIntersection2 );
                    connections.add(connection);
                }
            }
        }
        return connections;
    }
    
    public void getTreeRoad()
    {
        
        Iterator it = gamePathsNodes.values().iterator();
        
        while(it.hasNext())
        {
            ArrayList<Connection> theList = (ArrayList<Connection>)it.next();
            
            for(Connection c : theList)
            {
                
                System.out.println("Printing all the Values of the hashtable : " + intersectionMap.get(c.Intersection1ID) + "\t"+ intersectionMap.get(c.Intersection2ID));
            }    
        }   
    }
    private ArrayList<Connection> generatePath(ArrayList<String> intersectionIDs,
            ArrayList<String> roadIDs)
    {
        ArrayList<Connection> path = new ArrayList();
        for (int i = 0; i < roadIDs.size(); i++)
        {
            Connection c = new Connection(roadIDs.get(i),
                    intersectionIDs.get(i),
                    intersectionIDs.get(i + 1));
            path.add(c);
        }
        return path;
    }
    
    
    
    public ArrayList<Integer> findPath(Intersection intersection, Intersection end)
    {
        ArrayList<String> intersectionIDsInPath = new ArrayList();
        
        ArrayList<Intersection> pathList = new ArrayList<Intersection>();
        ArrayList<Integer> path = new ArrayList();
        ArrayList<String> roadIDsInPath = new ArrayList();
        TreeMap<String, String> closedIntersectionIDs;
        closedIntersectionIDs = new TreeMap();
        TreeMap<String, String> closedRoadIDs;
        closedRoadIDs = new TreeMap();
      
        intersectionIDsInPath.add(intersection.getId());
        closedIntersectionIDs.put(intersection.getId(), intersection.getId());
       
        // WHILE THE PATH IS NOT EMPTY
        // WHICH MEANS THERE MIGHT STILL
        // BE A PATH
        while (!intersectionIDsInPath.isEmpty())
        {
            String lastIntersectionID = intersectionIDsInPath.get(intersectionIDsInPath.size() - 1);
            Intersection lastIntersection = intersectionMap.get(lastIntersectionID);
            
            // GET ALL FILMS FOR lastIntersection
            ArrayList<Connection> neighbors = getAllNeighbors(lastIntersectionID);
            
            // IF KEVIN BACON HAS BEEN IN ANY
            // OF THOSE FILMS, ADD THE FILM TO
            // THE PATH AND WE'RE DONE
            Iterator<Connection> it = neighbors.iterator();
            while (it.hasNext())
            {
                Connection c = it.next();
                if (c.hasIntersection(end.getId()))
                {
                    Road roadForPath = roadMap.get(c.getRoadID());
                    roadIDsInPath.add(c.getRoadID());
                    intersectionIDsInPath.add(end.getId());
                    ArrayList<Connection> thePath = generatePath(intersectionIDsInPath, roadIDsInPath);
                   Intersection check = null;
                Iterator<Connection> i = thePath.iterator();
                    while(i.hasNext())
                                {
                                    Connection temp = i.next();
                                  
                                    if(intersectionMap.get(temp.Intersection1ID).equals(check) )
                                        pathList.add(intersectionMap.get(temp.Intersection2ID));
                                    else
                                    {
                                        pathList.add(intersectionMap.get(temp.Intersection1ID));
                                        pathList.add(intersectionMap.get(temp.Intersection2ID));
                                        
                                    }
                                    check = intersectionMap.get(temp.Intersection2ID);
                                }
                     Iterator<Intersection> in = pathList.iterator();
                 while(in.hasNext())
                 {
                     Intersection insec = in.next();
                     
                     path.add(insec.getX());
                     path.add(insec.getY());
                     
                 }
                  
                    
                    
                    return path;
                }
            }
            
            // REMOVE ALL CONNECTIONS FROM
            // THE CLOSED LIST OF ACTORS
            // AND THE CLOSED LIST OF FILMS
            for (int i = 0; i < neighbors.size(); i++)
            {
                Connection c = neighbors.get(i);
                String roadToTest = c.getRoadID();
                if (closedIntersectionIDs.containsKey(c.getIntersection2ID()))
                {
                    neighbors.remove(i);
                    i--;
                } else if (closedRoadIDs.containsKey(roadToTest))
                {
                    neighbors.remove(i);
                    i--;
                }
            }
            // IF NO MORE NEIGHBORS, THEN WE HAVE
            // A DEAD END, WHICH MEANS WE HAVE TO GO
            // ANOTHER WAY. THAT MEANS WE HAVE TO
            // REMOVE THE LAST NODE FROM THE PATH, ALSO
            // ADD IT TO THE CLOSED LIST
            if (neighbors.isEmpty())
            {
                intersectionIDsInPath.remove(intersectionIDsInPath.size() - 1);
                //   roadIDsInPath.remove(roadIDsInPath.size() - 1);
            } else
            {
                Connection c = neighbors.get(0);
                intersectionIDsInPath.add(c.getIntersection2ID());
                closedIntersectionIDs.put(c.getIntersection2ID(), c.getIntersection2ID());
                roadIDsInPath.add(c.getRoadID());
                closedRoadIDs.put(c.getRoadID(), c.getRoadID()); 
            }
        }
        return new ArrayList();
    }
    
//    public ArrayList<Connection> getPath (Intersection startIn)
//    {
//        
//        TreeMap<Intersection, Integer> tableA = new TreeMap<Intersection, Integer>();
//       TreeMap<Intersection, Integer> tableB = new  TreeMap<Intersection, Integer> ();
//        
//        Iterator it = intersectionMap.values().iterator();
//        
//       
//        while(it.hasNext())
//        {
//            
//            Intersection start = (Intersection)it.next();
//            if(start.equals(startIn))
//                tableA.put(startIn, 0);
//            else
//            tableA.put(start, Integer.MAX_VALUE);
//            
//        }
//        
//    while(!tableA.isEmpty())
//    {
//        
//        
//        
//    }
//        
//        
//    }
//    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void pathFinding()
    {
        ArrayList<String> list = new ArrayList<String> ();
        Iterator itList = intersectionMap.values().iterator();
        while(itList.hasNext())
        {
            Intersection startList = (Intersection)itList.next();
            list.add(startList.getId());
            ArrayList<Connection> neighbors = getAllNeighbors(startList.getId());  
        }
        
        ArrayList<String> roadlist = new ArrayList<String> ();
        Iterator roadList = roadMap.values().iterator();
        while(roadList.hasNext())
        { 
            Road startList = (Road)itList.next();
            roadlist.add(startList.getRoadId());
        }
    }
    
    public void clearPath()
    {
        gamePathsNodes = new TreeMap<String , ArrayList<Connection>>();
        
        playerPath = new ArrayList<Intersection>();
    }
    
    public ArrayList<Connection> findShortestPath(Intersection startLocation, Intersection endLocation)
    {
        // WE'LL MAINTAIN A SHORTEST PATH FROM THE
        // STARTING ACTOR TO EACH ACTOR WE ENCOUNTER
        
        
        // Iterator it = intersectionMap.values().iterator();
        // while(it.hasNext())
        {
        
        
        // Intersection startLocation = (Intersection)it.next();
        
        //ArrayList<Connection> test = this.getAllNeighbors(startLocation.getId());
        
        // for(int j = 0; j < test.size(); j++)
        {
            
            TreeMap<String, ArrayList<Connection>> shortestPaths;
            shortestPaths = new TreeMap();
            
            
            // THIS WILL STORE THE PATH WE ARE CURRENTLY
            // BUILDING UPON
            
            
            ArrayList<Connection> currentPath ;
            
            // WE ARE USING A BREADTH FIRST SEARCH, AND
            // WE'LL ONLY CHECK EACH Intersection AND Road ONCE
            // WE ARE USING 2 DATA STRUCTURES FOR EACH
            // BECAUSE WE WILL USE ONE AS A LIST OF
            // ITEMS TO CHECK IN ORDER, AND ANOTHER
            // FOR FAST SEARCHING
            
            ArrayList<Road> roadsVisited = new ArrayList();
            TreeMap<String, Road> roadsVisitedFast = new TreeMap();
            ArrayList<Intersection> intersectionsVisited = new ArrayList();
            TreeMap<String, Intersection> intersectionsVisitedFast = new TreeMap();
            
            
            Iterator it = intersectionMap.values().iterator();
            while(it.hasNext())
            {

                Intersection stopLocation = (Intersection)it.next();    
            }
            
            // INDEX OF Intersections AND Roads TO CHECK
            int roadIndex = 0;
            int intersectionIndex = 0;
    
            // THE SHORTEST PATH FROM THE START ACTOR
            // TO THE START ACTOR IS NOTHING, SO WE'll
            // START OUT WITH AN EMPTY ArrayList
            intersectionsVisited.add(startLocation);
            intersectionsVisitedFast.put(startLocation.getId(), startLocation);
            shortestPaths.put(startLocation.getId(), new ArrayList<Connection>());
            
            // GO THROUGH ALL THE ACTORS WE HAVE REACHED
            // NEVER RE-VISITING AN ACTOR
            while (intersectionIndex < intersectionsVisited.size() )
            {
                // FIRST GET ALL THE MOVIES FOR THE
                // ACTOR AT THE roadIndex
                Intersection currentIntersection = intersectionsVisited.get(intersectionIndex);
                
                // MAKE THE SHORTEST PATH FOR THE CURRENT
                // ACTOR THE CURRENT PATH, SINCE WE WILL
                // BUILD ON IT
                currentPath = shortestPaths.get(currentIntersection.getId());
                
                Iterator<String> loadRoads =currentIntersection.getRoadIDs().iterator();
                
                
                while(loadRoads.hasNext())
                {
                    
                    String Id = loadRoads.next();
                    Road Rd = roadMap.get(Id);
                    
                    
                    if (!roadsVisitedFast.containsKey(Id) )
                    {
                        roadsVisited.add(Rd);
                        roadsVisitedFast.put(Rd.getRoadId(), Rd);  
                    }
                }
                
                while (roadIndex < roadsVisited.size())
                {
                    // NOW GO THROUGH THE FILMS AND GET
                    // ALL THE ACTORS WHO WERE IN THOSE
                    // FILMS, DO NOT GET ACTORS ALREADY
                    // VISITED
                    
                    Road currentRoad = roadsVisited.get(roadIndex);
                    Iterator<String> loadIntersection =currentRoad.getIntersectionIDs().iterator();
                    
                    while (loadIntersection.hasNext())
                    {
                        String intersecID = loadIntersection.next();
                        Intersection intersectionToTest = intersectionMap.get(intersecID);
                        
                        //if(!roadToTest.isOpen())
                        {
                        
                        //roadsVisited.add(roadToTest);
                        //roadsVisitedFast.put(intersecID, roadToTest);
                        System.out.println("Testing to see if this get called");
                        
                    }
                        if (!intersectionsVisitedFast.containsKey(intersecID) && intersectionToTest.isOpen())
                        {
                            intersectionsVisited.add(intersectionToTest);
                            intersectionsVisitedFast.put(intersecID, intersectionToTest);
                            ArrayList<Connection> intersecionPath;
                            intersecionPath = (ArrayList<Connection>) currentPath.clone();
                            
                            // currentPath = shortestPaths.get(currentIntersection.getId());
                            
                            Connection c = new Connection(currentRoad.getRoadId(),
                                    currentIntersection.getId(), intersectionToTest.getId());
                            
                            // if(!shortestPaths.containsKey(intersecID))
                            intersecionPath.add(c);
                            
                            gamePathsNodes.put(currentRoad.getRoadId(), intersecionPath);
                            
                            shortestPaths.put(intersecID, intersecionPath);
                            
                            if (intersecID.equals(endLocation.getId()))
                            {
                                Iterator<Connection> path =intersecionPath.iterator();
                                Intersection check = null;
                                while (path.hasNext())
                                {
                                    Connection temp = path.next();
                                    
                                    shortestPaths.put(intersecID, intersecionPath);
                                    if(intersectionMap.get(temp.Intersection1ID).equals(check) )
                                        playerPath.add(intersectionMap.get(temp.Intersection2ID));
                                    else
                                    {
                                        playerPath.add(intersectionMap.get(temp.Intersection1ID));
                                        playerPath.add(intersectionMap.get(temp.Intersection2ID));
                                        
                                    }
                                    check = intersectionMap.get(temp.Intersection2ID);
                                }
                                
//                                gameCars.get(5).updatePlayerMove(playerPath);
                                return intersecionPath;
                            }
                            // IF THIS IS KEVIN BACON WE'RE DONE
                            
                        }
                    } roadIndex++;
                    
                }
                intersectionIndex++;
                
            }
            
            //startLocation = intersectionMap.get(test.get(j).Intersection2ID);
        }
        
    }
        return new ArrayList();
    }
}