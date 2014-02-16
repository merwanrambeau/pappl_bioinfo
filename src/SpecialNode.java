public class SpecialNode extends Node {

	public SpecialNode(String pId, String nId, int patId) {
		participantId = pId;
		nodeId= nId;
		pathwaydbId=patId;
	}

	public SpecialNode(String pId, String nId, int patId, String nam, String typ) {
		participantId = pId;
		nodeId= nId;
		pathwaydbId=patId;
		name = nam;
		type = typ;
	}

	public SpecialNode() {
	}

}
