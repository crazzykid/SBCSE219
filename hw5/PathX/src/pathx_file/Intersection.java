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
 * This class represents an intersection in a level. Note that an intersection
 * connects roads and can be thought of as a node on a graph.
 * 
 * @author Richard McKenna
 */
public class Intersection
{
    // INTERSECTION LOCATION
    public int x;
    public int y;
    public String id;
    // IS IT OPEN OR NOT
    public boolean open;
    
    private int randNum;
 private ArrayList<String> roadIDs; 
    /**
     * Constructor allows for a custom location, note that all
     * intersections start as open.
     */
    public Intersection(int initX, int initY)
    {
        x = initX;
        y = initY;
        open = true;
        randNum = (int)(Math.random()*100);
        id =""+x+y+randNum;
        
        roadIDs = new ArrayList();
    }

    // ACCESSOR METHODS
    public int getX()       {   return x;       }
    public int getY()       {   return y;       }
    public boolean isOpen() {   return open;    }
    public ArrayList<String> getRoadIDs()   {   return roadIDs;         }
    
     public void IDprint(){ System.out.println(roadIDs);}
     
    public void addRoadID(String idToAdd)
    {
        roadIDs.add(idToAdd);
    }
    public String getId() { return id; }
    // MUTATOR METHODS
    public void setX(int x)
    {   this.x = x;         }
    public void setY(int y)
    {   this.y = y;         }
    public void setOpen(boolean open)
    {   this.open = open;   }
    
    /**
     * This toggles the intersection open/closed.
     */
    public void toggleOpen()
    {
        open = !open;
    }
    
    /**
     * Returns a textual representation of this intersection.
     */
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
    

