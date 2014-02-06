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
			Connection con=DriverManager.getConnection(dbURL,"merwan","Iwasbornthe7");
			
			Statement stmt = con.createStatement();
			//get all the "normal" nodes
			ResultSet rs = stmt.executeQuery("SELECT reformatted_entity_particpant.participantId, reformatted_entity_particpant.entityId, reformatted_abstract_node.superNodeId, reformatted_entity_particpant.pathwaydbId, reformatted_entity_information.entityType, reformatted_entity_information.entityName "
					+ "FROM reformatted_entity_particpant "
					+ "JOIN reformatted_abstract_node ON reformatted_abstract_node.participantId=reformatted_entity_particpant.participantId "
					+ "JOIN reformatted_entity_information ON reformatted_entity_information.entityId = reformatted_entity_particpant.entityId "
					+ "WHERE reformatted_abstract_node.pathwayDbId='2' AND reformatted_entity_particpant.pathwaydbId='2';");
			DirectedPseudograph<Node,Edge> graph = new DirectedPseudograph<Node,Edge>(Edge.class);
			while (rs.next()) {
				Node n;
				if(rs.getString("entityType").equals("complex")){
					n = new ComplexNode(rs.getString("participantId"), rs.getString("entityId"), rs.getString("supernodeId"), rs.getInt("pathwayDbId"),rs.getString("entityName"), rs.getString("entityType"));
					System.out.println("1. created complex "+n.getNodeID());
					ArrayList<Entity> sub_entities = ((ComplexNode)n).createSubEntities(con);
					for (Entity e : sub_entities) {
						graph.addVertex(e);
					}
				}
				else{
					n = new Entity(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4), rs.getString(5), rs.getString(6));
					System.out.println("1. created entity "+n.getNodeID());
				}
				graph.addVertex(n);
				
			}
			stmt.close();
			Statement stmt_spe = con.createStatement();
			ResultSet rs_spe;
			//get all special nodes (which do not appear in the "entity_particpant" table)
			rs_spe = stmt_spe.executeQuery("SELECT * FROM reformatted_abstract_node"
					+" WHERE reformatted_abstract_node.pathwayDbId='2' "
					+"AND reformatted_abstract_node.participantId NOT IN "
					+"(SELECT participantId FROM reformatted_entity_particpant WHERE pathwayDbId='2');");

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
					System.out.println("created complex "+n.getNodeID());
					ArrayList<Entity> sub_entities = ((ComplexNode)n).createSubEntities(con);
					for (Entity e : sub_entities) {
						graph.addVertex(e);
					}
					stmt_compl.close();
				}
				else{
					n = new SpecialNode(rs_spe.getString("participantId"), rs_spe.getString("supernodeId"), rs_spe.getInt("reformatted_abstract_node.pathwayDbId"));
					System.out.println("created special node "+n.getNodeID());
				}
				graph.addVertex(n);
			}
			stmt_spe.close();		
			Statement stmt2 = con.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM reformatted_pathway_relationPair WHERE pathwayDbId='2';");
			System.out.println("nb lignes resultats relations : "+rs2.getRow());
			int countRow = 0;
			while (rs2.next()){
				countRow = rs2.getRow();
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
			
			CytoscapeWritingTest writingTest = new CytoscapeWritingTest();
			writingTest.test(graph);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
