package com.algorithm;
import java.util.Iterator;

import com.graph.*;

public class tcss543 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleGraph g = new SimpleGraph();
		GraphInput gi = new GraphInput();
		gi.LoadSimpleGraph(g);
		
		//from here you can use g(a simple graph) as the input of your algorithms
		
		
		/*below is a simple example of using g*/
		Iterator i;
		Vertex v;
		Edge e;
		 
		         System.out.println("Iterating through vertices...");
		         for (i= g.vertices(); i.hasNext(); ) {
		             v = (Vertex) i.next();
		             System.out.println("found vertex " + v.getName());
		         }
		 
		         System.out.println("Iterating through adjacency lists...");
		         for (i= g.vertices(); i.hasNext(); ) {
		             v = (Vertex) i.next();
		             System.out.println("Vertex "+v.getName());
		             Iterator j;
		             
		             for (j = g.incidentEdges(v); j.hasNext();) {
		                 e = (Edge) j.next();
		                 System.out.println("  found edge with capacity  " + e.getData() + " to vertex "+g.opposite(v, e).getName());
		                 
		             }
		         }
	}
}

