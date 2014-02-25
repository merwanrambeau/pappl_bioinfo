import java.util.ArrayList;


public class Entity extends Node 
{
	
	protected String participantId;
	protected String entityId;
	protected String feature;
	protected String location;
	protected ComplexNode complex;
	protected ArrayList<SuperNode> supernodes = new ArrayList<SuperNode>();
	
	public Entity(){}
	
	public Entity(String entId, int patId, ComplexNode n){
		entityId=entId;
		pathwaydbId=patId;
		complex=n;
	}
	
	public Entity(String pId, String entId, String nId, int patId){
		participantId=pId;
		entityId=entId;
		nodeId=nId;
		pathwaydbId=patId;
	}
	
	public Entity(String pId, String entId, String nId, int patId, ComplexNode c){
		participantId=pId;
		entityId=entId;
		nodeId=nId;
		pathwaydbId=patId;
		complex=c;
	}
	
	public Entity(String pId, String entId, String nId, int patId, String nam, String typ){
		participantId=pId;
		entityId=entId;
		nodeId=nId;
		pathwaydbId=patId;
		name=nam;
		type=typ;
	}
	
	public String attributeForCytoscape(int i)
	{
		String result = "";
		switch (i)
		{
		case 0: result = this.participantId;
		break;

		case 1: result = this.entityId;
		break;
		
		case 2: result = Integer.toString(this.pathwaydbId);
		break;
		
		case 3: result = this.feature;
		break;
		
		case 4: result = this.location;
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


	/**
	 * @return the complex
	 */
	public ComplexNode getComplex() {
		return complex;
	}

	/**
	 * @param complex the complex to set
	 */
	public void setComplex(ComplexNode complex) {
		this.complex = complex;
	}
	
	

	/**
	 * @return the participantId
	 */
	public String getParticipantId() {
		return participantId;
	}

	/**
	 * @param participantId the participantId to set
	 */
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	/**
	 * @return the entityId
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the feature
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * @param feature the feature to set
	 */
	public void setFeature(String feature) {
		this.feature = feature;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the supernodes
	 */
	public ArrayList<SuperNode> getSupernodes() {
		return supernodes;
	}

	/**
	 * @param supernodes the supernodes to set
	 */
	public void setSupernodes(ArrayList<SuperNode> supernodes) {
		this.supernodes = supernodes;
	}
	
	public void addSuperNode(SuperNode s){
		this.supernodes.add(s);
	}

	/*
	 * We need to redefine equals to prevent from creating several times
	 * the same node when entity belong to several SuperNodes
	 */
	public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
 
        
        if (obj instanceof Entity) {
            Entity other = (Entity) obj;
            
            // 2 nodes are considered the same if they have the same entityId, pathwayDbId, location and feature
            // (if both have an attribute "null", they are considered identical on this attribute)
            
            //comparing entity_id
            boolean this_null = (this.entityId==null);
            boolean other_null = (other.entityId==null);
            if((this_null && !other_null) || (!this_null && other_null)){
            	return false;
            }
            else if(!this_null && !other_null){
            	if(!this.entityId.equals(other.entityId)){
                	return false;
                }
            }
            
            //comparing location
            this_null=(this.location ==null);
            other_null = (other.location==null);
            if((this_null && !other_null) || (!this_null && other_null)){
            	return false;
            }
            else if(!this_null && !other_null){
            	if(!this.location.equals(other.location)){
                	return false;
                }
            }
            
            //comparing feature
            this_null=(this.feature ==null);
            other_null = (other.feature==null);
            if((this_null && !other_null) || (!this_null && other_null)){
            	return false;
            }
            else if(!this_null && !other_null){
            	if (!this.feature.equals(other.feature)){
                	return false;
                }
            }
            
            //comparing pathwayDbId
            else if(this.pathwaydbId != other.pathwaydbId)
            {
            	return false;
            }
            
            return true;
        }
        return false;
	}
	
}
