import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;


public class PHWriting {
	
	public PHWriting(){}
	
	public void findTransformations(DirectedPseudograph<Node, Edge> graph){
		
		Set<Node> allNodes = new HashSet<Node>();
		allNodes = graph.vertexSet();
		for(Node node : allNodes){
			//transformations uses an edge landing on an edge, so there is a PseudoNode involved
			if(node instanceof PseudoNode){
				Set<Edge> edges = new HashSet<Edge>();
				edges = graph.edgesOf(node);
				for(Edge edge : edges){
					if(edge.getInteractionType().equals("biochemicalReaction") && edge.getControlType().equals("ACTIVATION")){
						Node nodeA=((PseudoNode) node).getNodeA();
						Node nodeB=((PseudoNode) node).getNodeB();
						if(nodeA instanceof Entity && nodeB instanceof Entity){
						if(nodeA.getName().equals(nodeB.getName())){
							System.out.println("Relation d'activation");
							System.out.println(nodeA.getName() + " passe de " + ((Entity)nodeA).getFeature() + " à " + ((Entity)nodeB).getFeature());
						}
						}
						else{
							System.out.println("activation. noeud chiant trouvé");
							System.out.println("relation id :"+ edge.getInteractionId());
						}
					}
					else if(edge.getInteractionType().equals("biochemicalReaction") && edge.getControlType().equals("INHIBITION")){
						Node nodeA=((PseudoNode) node).getNodeA();
						Node nodeB=((PseudoNode) node).getNodeB();
						if(nodeA instanceof Entity && nodeB instanceof Entity){
						if(nodeA.getName().equals(nodeB.getName())){
							System.out.println("Relation d'inhibition");
							System.out.println(nodeA.getName() + " passe de " + ((Entity)nodeA).getFeature() + " à " + ((Entity)nodeB).getFeature());
						}
						}
						else{
							System.out.println("inhibition. noeud chiant trouvé");
							System.out.println("relation id :"+ edge.getInteractionId());
						}
					}
				}
			}
		}
	}
}
