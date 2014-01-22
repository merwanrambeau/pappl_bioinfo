
public class Edge {
	protected int pathwaydbId;
	protected String pathwayId;
	protected String interactionId;
	protected String interactionType;
	protected Node nodeA;
	protected Node nodeB;
	
	
	public Edge(int dbId, String patId, String intId, String interType, Node na, Node nb){
		pathwaydbId=dbId;
		pathwayId=patId;
		nodeA=na;
		nodeB=nb;
		interactionId=intId;
		interactionType=interType;
	}
	
	/**
	 * @return the interactionId
	 */
	public String getInteractionId() {
		return interactionId;
	}

	/**
	 * @param interactionId the interactionId to set
	 */
	public void setInteractionId(String interactionId) {
		this.interactionId = interactionId;
	}

	/**
	 * @return the interactionType
	 */
	public String getInteractionType() {
		return interactionType;
	}

	/**
	 * @param interactionType the interactionType to set
	 */
	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
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
	 * @return the pathwayId
	 */
	public String getPathwayId() {
		return pathwayId;
	}
	/**
	 * @param pathwayId the pathwayId to set
	 */
	public void setPathwayId(String pathwayId) {
		this.pathwayId = pathwayId;
	}
	/**
	 * @return the nodeA
	 */
	public Node getNodeA() {
		return nodeA;
	}
	/**
	 * @param nodeA the nodeA to set
	 */
	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}
	/**
	 * @return the nodeB
	 */
	public Node getNodeB() {
		return nodeB;
	}
	/**
	 * @param nodeB the nodeB to set
	 */
	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}
	
	
}
