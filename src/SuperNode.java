import java.util.ArrayList;


public class SuperNode extends Node{
	protected ArrayList<Node> sub_nodes=new ArrayList<Node>();
	
	public SuperNode(){
		type="SuperNode";
	}
	
	public SuperNode(String supernodeId){
		nodeId=supernodeId;
		type="SuperNode";
	}
	
	public void addSubNode(Node n){
		sub_nodes.add(n);
	}
	
	public ArrayList<Node> getSubNodes(){
		return sub_nodes;
	}
}
