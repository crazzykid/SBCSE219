/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx_file;

import java.util.ArrayList;

/**
 *
 * @author Crazzykid
 */

/**
 * This class represents a road in level graph, which means it's 
 * basically a graph edge.
 * 
 * @author Richard McKenna
 */
public class Road
{
    // THESE ARE THE EDGE'S NODES
    Intersection node1;
    Intersection node2;
    
    private String id;
    
    
    // false IF IT'S TWO-WAY, true IF IT'S ONE WAY
    boolean oneWay;
    
    // ROAD SPEED LIMIT
    int speedLimit;
     private ArrayList<String> intersectionIDs;  
     
     public Road()
             {
     intersectionIDs = new ArrayList();
  
        

     }
    // ACCESSOR METHODS
    public Intersection getNode1()  {   return node1;       }
    public Intersection getNode2()  {   return node2;       }
    public boolean isOneWay()       {   return oneWay;      }
    public int getSpeedLimit()      {   return speedLimit;  }
    
    public String getRoadId()       { return (""+node1.getId()+node2.getId()); }
    
    public void IDprint(){ System.out.println(intersectionIDs);}
    // MUTATOR METHODS
    public void setNode1(Intersection node1)    {   this.node1 = node1;             }
    public void setNode2(Intersection node2)    {   this.node2 = node2;             }
    public void setOneWay(boolean oneWay)       {   this.oneWay = oneWay;           }
    public void setSpeedLimit(int speedLimit)   {   this.speedLimit = speedLimit;   }

     public ArrayList<String> getIntersectionIDs()   {   return intersectionIDs;         }
    public void addIntersectionID(String idToAdd)
    {  
        intersectionIDs.add(idToAdd);
    }
    
    public float calWeight()
    {
       int distanceX = node2.getX() - node1.getX();
       int distanceY = node2.getY() - node1.getY();
       
       int totalDis = Math.abs(distanceX)+Math.abs(distanceY);
       
       
      return (totalDis/speedLimit);
        
    }
    /**
     * Builds and returns a textual representation of this road.
     */
    @Override
    public String toString()
    {
        return node1 + " - " + node2 + "(" + speedLimit + ":" + oneWay + ")";
    }
}