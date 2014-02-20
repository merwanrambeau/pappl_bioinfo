/*
 * This class is used only because we sometimes need to create an edge
 * landing on another edge. Since we cannot do this with a graph, we create a
 * PseudoNode at the intersection of the 2 edges (and thus 3 edges : 2 arriving to 
 * the node and another leaving). These nodes do not represent
 * anything, they are just here for construction purposes. Their nodeId is the
 * interactionId of the edges.
 */


public class PseudoNode extends Node {
	
	public PseudoNode(String id){
		nodeId=id;
	}

	public PseudoNode() {
	}

}
