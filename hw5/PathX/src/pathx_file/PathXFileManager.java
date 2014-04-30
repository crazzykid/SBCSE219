package pathx_file;

import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mini_game.Viewport;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
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
        
        // WE'LL USE THE SCHEMA FILE TO VALIDATE THE XML FILES
       // levelSchema = initLevelSchema;
        
        
    }
    public PathXGameLevel       getLevel()                  {   return level;}
public Intersection getInter(int i){     return intersectionToLoad.get(i); }
  
public boolean loadLevel(String levelFile)
    {
        try
        {
             data = (PathXDataModel)miniGame.getDataModel();
             File fileToOpen = new File(levelFile);
             
             levelSchema = new File(PATH_DATA + "PathXLevelSchema.xsd");
             
            // WE'LL FILL IN SOME OF THE LEVEL OURSELVES
            PathXGameLevel levelToLoad = data.getLevel();
            
            levelToLoad.reset();
            
            // FIRST LOAD ALL THE XML INTO A TREE
            Document doc = xmlUtil.loadXMLDocument( fileToOpen.getAbsolutePath(), 
                                                    levelSchema.getAbsolutePath());
            
            // FIRST LOAD THE LEVEL INFO
            Node levelNode = doc.getElementsByTagName(LEVEL_NODE).item(0);
            NamedNodeMap attributes = levelNode.getAttributes();
            String levelName = attributes.getNamedItem(NAME_ATT).getNodeValue();
            levelToLoad.setLevelName(LEVEL1);
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
            
            // LOAD THE NUMBER OF BANDITS
            Node banditsNode = doc.getElementsByTagName(BANDITS_NODE).item(0);
            attributes = banditsNode.getAttributes();
            String banditsText = attributes.getNamedItem(NUM_ATT).getNodeValue();
            int numBandits = Integer.parseInt(banditsText);
            levelToLoad.setNumBandits(numBandits);
            
            // LOAD THE NUMBER OF ZOMBIES
            Node zombiesNode = doc.getElementsByTagName(ZOMBIES_NODE).item(0);
            attributes = zombiesNode.getAttributes();
            String zombiesText = attributes.getNamedItem(NUM_ATT).getNodeValue();
            int numZombies = Integer.parseInt(zombiesText);
            levelToLoad.setNumZombies(numZombies);   
            
            
            
                // EVERYTHING WENT AS PLANNED SO LET'S MAKE IT PERMANENT
           // PathXDataModel dataModel = (PathXDataModel)miniGame.getDataModel();
           // Viewport viewport = data.getViewport();
        //    viewport.setGameWorldSize(numColumns * TILE_WIDTH, numRows * TILE_HEIGHT);
          //  viewport.setNorthPanelHeight(NORTH_PANEL_HEIGHT);
          //  viewport.initViewportMargins();
            data.setCurrentLevel(LEVEL1);
            data.initLevel(LEVEL1, intersections);
            
        }
        catch(Exception e)
        {
           
            // LEVEL DIDN'T LOAD PROPERLY
            return false;
        }
        // LEVEL LOADED PROPERLY
           this.initIDs();
           
        return true;
     
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
        // WE'LL ONLY CHECK EACH Actor AND Film ONCE
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
                           
                           if(id.equals(int1.getId()) || id.equals(int2))
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
    
    public ArrayList<Connection> findShortestPath(Intersection startLocation, Intersection endLocation)
    {
	// WE'LL MAINTAIN A SHORTEST PATH FROM THE
        // STARTING ACTOR TO EACH ACTOR WE ENCOUNTER
        TreeMap<String, ArrayList<Connection>> shortestPaths;
        shortestPaths = new TreeMap();

        boolean sameLocation = false;
        
        if(startLocation.x == endLocation.x && startLocation.y == endLocation.y)
            sameLocation = true;
        
        // THIS WILL STORE THE PATH WE ARE CURRENTLY
        // BUILDING UPON
        
        
        ArrayList<Connection> currentPath ;

	// WE ARE USING A BREADTH FIRST SEARCH, AND
        // WE'LL ONLY CHECK EACH Intersection AND Road ONCE
        // WE ARE USING 2 DATA STRUCTURES FOR EACH
        // BECAUSE WE WILL USE ONE AS A LIST OF 
        // ITEMS TO CHECK IN ORDER, AND ANOTHER
        // FOR FAST SEARCHING
        ArrayList<Intersection> roadsVisited = new ArrayList();
        TreeMap<String, Intersection> roadsVisitedFast = new TreeMap();
        ArrayList<Road> intersectionsVisited = new ArrayList();
        TreeMap<String, Road> intersectionsVisitedFast = new TreeMap();

        // INDEX OF Intersections AND Roads TO CHECK
        int roadIndex = 0;
        int intersectionIndex = 0;

	// THE SHORTEST PATH FROM THE START ACTOR
        // TO THE START ACTOR IS NOTHING, SO WE'll
        // START OUT WITH AN EMPTY ArrayList
        roadsVisited.add(startLocation);
        roadsVisitedFast.put(startLocation.getId(), startLocation);
        shortestPaths.put(startLocation.getId(), new ArrayList<Connection>());

	// GO THROUGH ALL THE ACTORS WE HAVE REACHED
        // NEVER RE-VISITING AN ACTOR
        while (roadIndex < roadsVisited.size())
        {
            // FIRST GET ALL THE MOVIES FOR THE
            // ACTOR AT THE roadIndex
            Intersection currentIntersection = roadsVisited.get(roadIndex);

            // MAKE THE SHORTEST PATH FOR THE CURRENT
            // ACTOR THE CURRENT PATH, SINCE WE WILL
            // BUILD ON IT
            currentPath = shortestPaths.get(currentIntersection.getId());
        
               Iterator<String> loadRoads =currentIntersection.getRoadIDs().iterator();
         
           
             while(loadRoads.hasNext())
            {
             
                  String Id = loadRoads.next();
                  Road Rd = roadMap.get(Id);
                 
                
                if (!intersectionsVisitedFast.containsKey(Id) )
                {
                    intersectionsVisited.add(Rd);
                    intersectionsVisitedFast.put(Rd.getRoadId(), Rd);
                }
            }

            while (intersectionIndex < intersectionsVisited.size())
            {
		// NOW GO THROUGH THE FILMS AND GET
                // ALL THE ACTORS WHO WERE IN THOSE
                // FILMS, DO NOT GET ACTORS ALREADY
                // VISITED
             
                
                Road currentRoad = intersectionsVisited.get(intersectionIndex);
                Iterator<String> loadIntersection =currentRoad.getIntersectionIDs().iterator();
         
                while (loadIntersection.hasNext())
                {
                    String intersecID = loadIntersection.next();
                    Intersection roadToTest = intersectionMap.get(intersecID);
                    
                    if(!roadToTest.isOpen())
                    {
                        
                      //roadsVisited.add(roadToTest);
                        //roadsVisitedFast.put(intersecID, roadToTest);
                        System.out.println("Testing to see if this get called");
                    
                    }
                    if (!roadsVisitedFast.containsKey(intersecID))
                    {
                        roadsVisited.add(roadToTest);
                        roadsVisitedFast.put(intersecID, roadToTest);
                        ArrayList<Connection> roadPath;
                        roadPath = (ArrayList<Connection>) currentPath.clone();
                        
                        // currentPath = shortestPaths.get(currentIntersection.getId());
                         
                         
                        Connection c = new Connection(currentRoad.getRoadId(), 
                                currentIntersection.getId(), roadToTest.getId());
                   
                        roadPath.add(c);
                        
                        
                        shortestPaths.put(intersecID, roadPath);

                        // IF THIS IS KEVIN BACON WE'RE DONE
                        if (intersecID.equals(endLocation.getId())||sameLocation==true)
                        {
                            System.out.println(startLocation.toString() +"  " + endLocation);
                            int i=0;
                            while(roadPath.size()>i)
                                    {
                                      roadPath.get(i).toString();
                                       i++;
                                    }
                                   System.out.println( "Size Of array"+roadPath.size());
                                 System.out.println(roadPath.toString());
                                 
                            return roadPath;
                            
                        }
                    }
                }
                intersectionIndex++;
            }
            roadIndex++;
        }
        return new ArrayList();
    }
}