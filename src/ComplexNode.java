import java.util.ArrayList;


public class ComplexNode extends Node {
	
	protected ArrayList<Entity> sub_entities;

	public ComplexNode(String pId, String entId, String nId, int patId) {
		super(pId, entId, nId, patId);
	}
	
	public ComplexNode(String pId, String nId, int patId) {
		participantId = pId;
		nodeId= nId;
		pathwaydbId=patId;
	}

	public ComplexNode(String pId, String entId, String nId, int patId,	String nam, String typ) {
		super(pId, entId, nId, patId, nam, typ);
	}
	
	public ComplexNode(String pId, String nId, int patId,	String nam, String typ) {
		participantId = pId;
		nodeId= nId;
		pathwaydbId=patId;
		name = nam;
		type = typ;
	}

	public ComplexNode() {
	}

	/**
	 * @return the sub_entities
	 */
	public ArrayList<Entity> getSub_entities() {
		return sub_entities;
	}

	/**
	 * @param sub_entities the sub_entities to set
	 */
	public void setSub_entities(ArrayList<Entity> sub_entities) {
		this.sub_entities = sub_entities;
	}
	
	public void addSub_entity(Entity e){
		sub_entities.add(e);
	}

}
