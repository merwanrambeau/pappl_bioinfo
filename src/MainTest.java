import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.SimpleDirectedGraph;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		}
		catch
		(java.lang.ClassNotFoundException e)
		{
		System.err.print("ClassNotFoundException:");
		System.err.println(e.getMessage());
		}
		
		String dbURL="jdbc:mysql://localhost:3306/hipathdb_reformatted_20100309";
		try {
			Connection con=DriverManager.getConnection(dbURL,"root","papplsql");
			int databaseId = 4;
			Statement stmt = con.createStatement();
			
			
			//get all the "normal" nodes
			ResultSet rs = stmt.executeQuery("SELECT reformatted_entity_particpant.participantId, reformatted_entity_particpant.entityId, reformatted_abstract_node.superNodeId, reformatted_entity_particpant.pathwaydbId, reformatted_entity_information.entityType, reformatted_entity_information.entityName "
					+ "FROM reformatted_entity_particpant "
					+ "JOIN reformatted_abstract_node ON reformatted_abstract_node.participantId=reformatted_entity_particpant.participantId "
					+ "JOIN reformatted_entity_information ON reformatted_entity_information.entityId = reformatted_entity_particpant.entityId "
					+ "WHERE reformatted_abstract_node.pathwayDbId='"+databaseId+"' AND reformatted_entity_particpant.pathwaydbId='"+databaseId+"' ORDER BY supernodeId;");
			DirectedPseudograph<Node,Edge> graph = new DirectedPseudograph<Node,Edge>(Edge.class);
			while (rs.next()) {
				Node n;
				SuperNode s=new SuperNode(rs.getString("superNodeId")+"false");
				
				if(rs.getString("entityType").equals("complex")){
					n = new ComplexNode(rs.getString("participantId"), rs.getString("entityId"), rs.getString("supernodeId"), rs.getInt("pathwayDbId"),rs.getString("entityName"), rs.getString("entityType"));
					System.out.println("1. created complex (name) "+n.getName());
					ArrayList<Entity> sub_entities = ((ComplexNode)n).createSubEntities(con);
					
				}
				else{
					n = new Entity(rs.getString("participantId"), rs.getString("entityId"),rs.getString("superNodeId"),rs.getInt("pathwaydbId"), rs.getString("entityName"), rs.getString("entityType"));
					System.out.println("1. created entity (name) "+n.getName());
				}
				//special for BDD 4 : we have to manage entity linked to several superNode which appear several times in teh SQL answer
				if(rs.getInt("pathwaydbId")==4 && rs.getString("superNodeId").startsWith("C")){
					//if it is a different superNode, create it and store it in s (superNodes do not appear directly as lines in the answer)
					if(!s.getNodeID().equals(rs.getString("superNodeId"))){
						s = new SuperNode(rs.getString("superNodeId"));
						graph.addVertex(s);
					}

				}
				//We browse the graph to check if the node is not already in the graph 
				//(We cannot  use the containsVertex method of the graph because it does not behave as explained in the doc)
				Set<Node> nodes = new HashSet<Node>();
				nodes = graph.vertexSet();
				boolean trouve = false;
				Iterator<Node> it = nodes.iterator();
				Node node=new Node();
				while(it.hasNext() && !trouve){
					node = (Node)it.next();
					if(node.equals(n)){
						trouve=true;
					}
				}
				//if the node is already in the graph, we don't add it, but for base 4 we add the already existant one to the supernode
				if(trouve){
					System.out.println("Node already in the graph : (id) "+n.getEntityId()+" (name) "+n.getName());
					if(rs.getInt("pathwaydbId")==4 && rs.getString("superNodeId").startsWith("C")){
						s.addSubNode(node);
					}
				}
				//if the node is not in the graph, we add it and its sub entities if it is a complex
				else{
					graph.addVertex(n);
					if(n.getType().equals("complex")){
						ArrayList<Entity> sub_entities = ((ComplexNode)n).getSub_entities();
						for (Entity e : sub_entities) {
							graph.addVertex(e);
						}
					}
				}
				
			}
			stmt.close();
			Statement stmt_spe = con.createStatement();
			ResultSet rs_spe;
			//get all special nodes (which do not appear in the "entity_particpant" table)
			rs_spe = stmt_spe.executeQuery("SELECT * FROM reformatted_abstract_node"
					+" WHERE reformatted_abstract_node.pathwayDbId='"+databaseId+"' "
					+"AND reformatted_abstract_node.participantId NOT IN "
					+"(SELECT participantId FROM reformatted_entity_particpant WHERE pathwayDbId='"+databaseId+"');");

			while (rs_spe.next()){
				Node n;
				if(rs_spe.getInt("reformatted_abstract_node.pathwayDbId")==1){
					//special nodes in DB1 are complex which have information associated to them in the entity_information table
					Statement stmt_compl=con.createStatement();
					String requete = "SELECT * FROM reformatted_entity_information WHERE entityId='"+rs_spe.getString("participantId")+"' AND pathwayDbId='1'";
					ResultSet rs_compl=stmt_compl.executeQuery(requete);
					System.out.println(requete);
					if(rs_compl.next()){
						n = new ComplexNode(rs_spe.getString("participantId"), rs_spe.getString("supernodeId"), rs_spe.getInt("reformatted_abstract_node.pathwayDbId"),	rs_compl.getString("entityName"),rs_compl.getString("entityType"));
					}
					else{
						n = new ComplexNode(rs_spe.getString("participantId"), rs_spe.getString("supernodeId"), rs_spe.getInt("reformatted_abstract_node.pathwayDbId"));
					}
					System.out.println("created complex(name) "+n.getName());
					ArrayList<Entity> sub_entities = ((ComplexNode)n).createSubEntities(con);
					for (Entity e : sub_entities) {
						graph.addVertex(e);
					}
					stmt_compl.close();
				}
				else{
					n = new SpecialNode(rs_spe.getString("participantId"), rs_spe.getString("supernodeId"), rs_spe.getInt("reformatted_abstract_node.pathwayDbId"));
					n.setName(rs_spe.getString("participantId"));
					System.out.println("created special node (name) "+n.getName());
				}
				graph.addVertex(n);
			}
			stmt_spe.close();		
			
			Statement stmt2 = con.createStatement();
			String query = "SELECT * FROM reformatted_pathway_relationPair WHERE pathwayDbId='"+databaseId+"';";
			ResultSet rs2 = stmt2.executeQuery(query);
			while (rs2.next()){
				Set<Node> nodes = new HashSet<Node>();
				nodes = graph.vertexSet();
				String nodeAId = rs2.getString("nodeA");
				String nodeBId = rs2.getString("nodeB");
				String controllerId = rs2.getString("controller");
				System.out.println(nodeAId+ " " + nodeBId);
				Node nodeA=new Node();
				Node nodeB=new Node();
				Node controller=new Node();
				Iterator<Node> it = nodes.iterator();
				while(it.hasNext()){
					Node n = (Node)it.next();
					if(n.getNodeID()!=null){ //subentities have no nodeid, so we have to check first if there is one nodeId to prevent crash
						if(n.getNodeID().equals(nodeAId)){
							nodeA=n;
						}
						if(n.getNodeID().equals(nodeBId)){
							nodeB=n;
						}
						if(n.getNodeID().equals(controllerId)){
							controller=n;
						}
					}
				}
				if(nodeA.getNodeID()==null && nodeB.getNodeID()!=null && controller.getNodeID()!=null){ //if there is no nodeA, the edge comes from controller to nodeB
					Edge e = new Edge(rs2.getInt("pathwayDbId"), rs2.getString("pathwayId"), rs2.getString("interactionId"),rs2.getString("interactionType"),controller,nodeB);
					graph.addEdge(controller, nodeB, e);
					System.out.println("created edge without nodeA");
				}
				else if(nodeB.getNodeID()==null && nodeA.getNodeID()!=null && controller.getNodeID()!=null){ //if there is no nodeB but a nodeA and a controller, edge from controller to nodeA
					Edge e = new Edge(rs2.getInt("pathwayDbId"), rs2.getString("pathwayId"), rs2.getString("interactionId"),rs2.getString("interactionType"),controller,nodeA);
					graph.addEdge(controller, nodeA, e);
					System.out.println("created edge without nodeB");
				}
				else if (nodeA.getNodeID()!=null && nodeB.getNodeID()!=null){//normal edge from nodeA to nodeB
					Edge e = new Edge(rs2.getInt("pathwayDbId"), rs2.getString("pathwayId"), rs2.getString("interactionId"),rs2.getString("interactionType"),nodeA,nodeB);
					graph.addEdge(nodeA, nodeB, e);
					System.out.println("created normal edge");
				}
				else{
					System.out.println("created NO EDGE");
					System.out.println("controller : "+controllerId);
				}
			}
			stmt2.close();
			
			
			con.close();
			Driver theDriver=DriverManager.getDriver(dbURL);
			DriverManager.deregisterDriver(theDriver);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
