package aed.delivery;

import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.graph.DirectedAdjacencyListGraph;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.map.HashTableMap;
import es.upm.aedlib.set.HashTableMapSet;
import es.upm.aedlib.set.Set;
import java.util.Iterator;

public class Delivery<V> {
	DirectedGraph<V, Integer> graph;

	// Construct a graph out of a series of vertices and an adjacency matrix.
	// There are 'len' vertices. A negative number means no connection. A non-negative
	// number represents distance between nodes.
	public Delivery(V[] places, Integer[][] gmat) {
		graph = new DirectedAdjacencyListGraph<V, Integer>();
		for (int i = 0; i < places.length; i++) {
			graph.insertVertex(places[i]);
		}
		Iterator<Vertex<V>> iterator1 = graph.vertices().iterator();
		boolean[][] edgesChecked = new boolean[gmat.length][gmat.length];
		int i = 0;
		while (iterator1.hasNext()) {
			Vertex<V> vertexI = iterator1.next();
			Iterator<Vertex<V>> iterator2 = graph.vertices().iterator();
			int j = 0;
			while (iterator2.hasNext()) {
				Vertex<V> vertexJ = iterator2.next();
				if (gmat[i][j] != null && !edgesChecked[i][j] && vertexI != vertexJ) {
					edgesChecked[i][j] = true;
					graph.insertDirectedEdge(vertexI, vertexJ, gmat[i][j]);
				}
				j++;
			}
			i++;
		}
	}

	// Just return the graph that was constructed
	public DirectedGraph<V, Integer> getGraph() {
		return graph;
	}

	// Return a Hamiltonian path for the stored graph, or null if there is noe.
	// The list containts a series of vertices, with no repetitions (even if the path
	// can be expanded to a cycle).
	public PositionList <Vertex<V>> tour() {
		PositionList<Vertex<V>> list = new NodePositionList<Vertex<V>>();
		Iterator<Vertex<V>> vertices = graph.vertices().iterator();

		while (vertices.hasNext()) {
			list = new NodePositionList<Vertex<V>>();
			list = obtainHamiltonianPath(vertices.next(), list);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	private PositionList<Vertex<V>> obtainHamiltonianPath(Vertex<V> initialVertex,
			PositionList<Vertex<V>> pastVerticesList) {
		pastVerticesList.addLast(initialVertex);
		// If vertex does not have more edges and the list has the right size.
		if (pastVerticesList.size() == graph.numVertices()) {
			return pastVerticesList;
		}
		Iterator<Edge<Integer>> edgeIterator = graph.outgoingEdges(initialVertex).iterator();
		while (edgeIterator.hasNext()) {
			Edge<Integer> edgeAdyacente = edgeIterator.next();
			Vertex<V> verticeAdyacente = graph.endVertex(edgeAdyacente);
			if (!DoesPositionListContain(pastVerticesList, verticeAdyacente)) {
				PositionList<Vertex<V>> lista = obtainHamiltonianPath(verticeAdyacente, pastVerticesList);
				if (lista != null) {
					return lista;
				}
			}
		}
		pastVerticesList.remove(pastVerticesList.last());
		return null;
	}

	private boolean DoesPositionListContain(PositionList<Vertex<V>> list, Vertex<V> itemToSearch) {
		Iterator<Vertex<V>> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(itemToSearch)) {
				return true;
			}
		}
		return false;
	}

	public int length(PositionList<Vertex<V>> path) {
		if(path.size()==0) return 0;

		int res= 0;
		//recorrer vertices grafo
		Iterable<Vertex<V>> vertices = graph.vertices();

		//cursor path
		Position<Vertex<V>> cursor = path.first();

		while(cursor!= null) {
			Vertex<V> el = cursor.element();

			//ultimo vertice
			if(path.next(cursor)==null) return res;

			//comparamos vertices grafo con el del path
			for(Vertex<V> vertexGraph : vertices) {

				if(el.equals(vertexGraph)) {
					//edges que salen de el
					Iterable<Edge<Integer>> edges = graph.outgoingEdges(el);
					for(Edge<Integer> edge : edges) {
						if(graph.endVertex(edge).equals(path.next(cursor).element())) {							
							res += edge.element();
							break;
						}

					}

				}

			}
			cursor = path.next(cursor);
		}

		return res;	  	  
	}

	public String toString() {
		return "Delivery";
	}
}
