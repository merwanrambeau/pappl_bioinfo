/*
 * This class is used only because we sometimes need to create an edge
 * landing on another edge. Since we cannot do this with a graph, we create a
 * PseudoNode at the intersection of the 2 edges (and thus 3 edges : 2 arriving to 
 * the node and another leaving). These nodes do not represent
 * anything, they are just here for construction purposes. Their nodeId is the
 * interactionId of the edges.
 */


public class PseudoNode extends Node {
	
	private static int instanceCount = 0;
	protected Node controller;
	
	public PseudoNode(String id, Node contr){
		nodeId=id;
		controller = contr;
		instanceCount ++;
		name = "PseudoNode_"+instanceCount;
	}

	public PseudoNode() {
		instanceCount ++;
		name = "PseudoNode_"+instanceCount;
	}
	
	public String attributeForCytoscape(int i)
	{
		String result = "";
		switch (i)
		{
		case 0: result = null;
		break;

		case 1: result = null;
		break;
		
		case 2: result = Integer.toString(this.pathwaydbId);
		break;
		
		case 3: result = null;
		break;
		
		case 4: result = null;
		break;
		
		case 5: result = this.nodeId;
		break;
		
		case 6: result = this.name;
		break;
		
		case 7: result = this.type;
		break;
		}
		
		return result;
		
	}


}
