
public class Node {
	protected String participantId;
	protected String entityId;
	protected int pathwaydbId;
	protected String feature;
	protected String location;
	protected String nodeId;
	
	

	public Node(String pId, String entId, String nId, int patId){
		participantId = pId;
		entityId=entId;
		nodeId= nId;
		pathwaydbId=patId;
	}

	public Node(){}
	/**
	 * @return the nodeID
	 */
	public String getNodeID() {
		return nodeId;
	}

	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNodeID(String nodeID) {
		this.nodeId = nodeID;
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
	 * @return the pathwaydbId
	 */
	public int getPathwaydbId() {
		return pathwaydbId;
	}

	/**
	 * @param pathwaydbId the pathwaydbId to set
	 */
	public void setPathwaydbId(int pathwaydbId) {
		this.pathwaydbId = pathwaydbId;
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
	
	
	
}