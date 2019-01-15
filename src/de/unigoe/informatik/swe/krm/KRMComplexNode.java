package de.unigoe.informatik.swe.krm;

import java.io.Serializable;
import java.util.Set;

public class KRMComplexNode extends KRMSimpleNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5540866935542251309L;

	public enum KRMComplexType implements Serializable {
		FUNCTION, IF, WHILE, DO_WHILE, FOR, SWITCH, LOOP, SELECTION
	}
	
	private KRMComplexType type;
	private KRM embeddedKRM;
	
	public KRMComplexNode(KRMComplexType type, KRM embeddedKRM) {
		super();
		this.type = type;
		this.embeddedKRM = embeddedKRM;
	}
	
	public KRMComplexNode(KRMComplexType type) {
		super();
		this.type = type;
	}
	
	public Set<KnowledgeComponent> getKCs() {
		return embeddedKRM.getKCs();
	}
	
	@Override
	public boolean isSimple() {
		return false;
	}

	@Override
	public boolean isComplex() {
		return true;
	}

	public KRMComplexType getType() {
		return type;
	}

	public void setType(KRMComplexType type) {
		this.type = type;
	}

	public KRM getEmbeddedKRM() {
		return embeddedKRM;
	}

	public void setEmbeddedKRM(KRM embeddedKRM) {
		this.embeddedKRM = embeddedKRM;
	}
	
	public String toString() {
		return "Type: " + type +"\n Embedded KRM: " + embeddedKRM.toString();
	}
	
	public boolean equals(Object o) {
		if (o instanceof KRMComplexNode) {
			KRMComplexNode node = (KRMComplexNode) o;
			//return (node.getType().equals(type) && node.getEmbeddedKRM().equals(embeddedKRM));
			return node.getId().equals(super.getId());
		}
		return false;
	}
	
	public String getLabel() {
		return type.toString();
	}



}
