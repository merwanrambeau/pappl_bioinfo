
public class Entity extends Node {
	
	protected ComplexNode complex;
	
	public Entity(String entId, int patId, ComplexNode n){
		entityId=entId;
		pathwaydbId=patId;
		complex=n;
	}
	
	public Entity(String pId, String entId, String nId, int patId){
		super(pId, entId, nId, patId);
	}
	
	public Entity(String pId, String entId, String nId, int patId, ComplexNode c){
		super(pId, entId, nId, patId);
		complex=c;
	}
	
	public Entity(String pId, String entId, String nId, int patId, String nam, String typ){
		super(pId, entId, nId, patId, nam, typ);
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
	
}
