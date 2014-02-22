public class Node {
	
	protected int pathwaydbId;
	
	protected String nodeId;
	
	protected String type;
	

	public Node(String nId, int patId){
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
