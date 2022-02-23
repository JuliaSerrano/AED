package aed.individual6;

import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Vertex;

import java.util.ArrayList;
import java.util.Iterator;

import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.map.HashTableMap;


public class Suma {
  public static <E> Map<Vertex<Integer>,Integer> 
  sumVertices(DirectedGraph<Integer,E> g) {
    
	  	//mapa resultado
	    Map<Vertex<Integer>, Integer> map = new HashTableMap<Vertex<Integer>, Integer>();
	    
	   
	    //iterable de los vertices del grafo
	    Iterable<Vertex<Integer>> vertices = g.vertices();
	    Iterator<Vertex<Integer>> it = vertices.iterator();
	    
	    //iteramos sobre cada vertice
	    while(it.hasNext()){
	    	
	    	//visited vertex
	    	ArrayList<Vertex<Integer>> visited = new ArrayList<Vertex<Integer>>();
	    	
	    	
	    	//recursive sum to obtain 
	    	//the outgoing edges from each starting vertex
	    	Vertex<Integer> vertex = it.next();
	    	int sum;
	    	sum = sumRec(g,vertex,visited);
	    	
	    	
	    	map.put(vertex, sum);
	    }
	    
	    return map;

	    	
	    }
	   
  private static <E> int sumRec(DirectedGraph<Integer, E> g, Vertex<Integer> vertex, ArrayList<Vertex<Integer>> visited ) {

	  
	    if(!visited.contains(vertex)){
	    	
	    	//edges that have the vertex as a start vertex.
	    	Iterable<Edge<E>> edges = g.outgoingEdges(vertex);
	    	Iterator<Edge<E>> it = edges.iterator();
	    	//get the value
		    int sum = vertex.element().intValue();
		    //add to list to avoid accessing the vertex again
		    visited.add(vertex);
		    
		    //iterate over edges to get each vertex
		    while(it.hasNext()) {
		    	Edge<E> edge = it.next();
		    	sum += sumRec(g, g.endVertex(edge), visited);
		    }
		    return sum;
	    }
	    return 0;	
	     
	  }
	    
	    
	    
  
}