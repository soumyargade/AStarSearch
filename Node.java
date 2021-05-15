import java.io.*;
import java.util.*;

public class Node {
	public final String name;
	public final String latitude;
	public final String longitude;
	public double f_score = 0;
	public double g_score;
	public ArrayList<Edge> adjacencies;
	public Node parent;

	public Node(String personName, String lat, String lon) {
		name = personName;
		latitude = lat;
		longitude = lon;
		adjacencies = new ArrayList<Edge>();
	}

	@Override
    	public String toString() {
        	//return(name + " " + latitude + " " + longitude);
        	return name;
	}
}
