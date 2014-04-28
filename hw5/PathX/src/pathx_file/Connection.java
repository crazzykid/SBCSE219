/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx_file;
import java.util.*;

public class Connection implements Comparable, Comparator
{
	public String road;
	public String Intersection1ID;
	public String Intersection2ID;
	
	public Connection(	String initRoadID,
						String initIntersection1ID,
						String initIntersection2ID)
	{
		road = initRoadID;
		Intersection1ID = initIntersection1ID;
		Intersection2ID = initIntersection2ID;
	}
	
	public boolean hasActor(String intersectionID)
	{
		if (Intersection1ID.equals(intersectionID))
			return true;
		else if (Intersection2ID.equals(intersectionID))
			return true;
		else
			return false;
	}
	
	public int compareTo(Object obj)
	{
		if (equals(obj))
			return 0;
		else
		{
			Connection otherConnection = (Connection)obj;
			return (road + Intersection1ID + Intersection2ID)
			.compareTo(otherConnection.road 
				+ otherConnection.Intersection1ID
				+ otherConnection.Intersection2ID);
		}
	}
	
	public boolean equals(Object obj)
	{
		Connection otherConnection = (Connection)obj;
		boolean sameActors;
		sameActors = ((Intersection1ID.equals(otherConnection.Intersection1ID)
						&&
					   Intersection2ID.equals(otherConnection.Intersection2ID))
					  ||
					  (Intersection1ID.equals(otherConnection.Intersection2ID)
						&&
					   Intersection2ID.equals(otherConnection.Intersection1ID)));
		return sameActors && (road.equals(otherConnection.road));
	}

	public int compare(Object arg0, Object arg1) 
	{
		Connection conn0 = (Connection) arg0;
		Connection conn1 = (Connection) arg1;
		return conn0.compareTo(conn1);
	}
         public String toString()
    {
        return "Intersection 1 ID : "+Intersection1ID + "  Road ID : " +road + "  Intersection 2 ID : "+ Intersection2ID;
    }
}