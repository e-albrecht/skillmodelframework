package de.unigoe.informatik.swe.krm;

import java.io.Serializable;
import java.util.UUID;

public class KRMEdge implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 845520049951504385L;
	private KRMSimpleNode v1;
	private KRMSimpleNode v2;
	private String id;
	
	public KRMEdge(KRMSimpleNode v1, KRMSimpleNode v2) {
		this.v1 = v1;
		this.v2 = v2;
		setId(UUID.randomUUID().toString());
	}

	public KRMSimpleNode getV1() {
		return v1;
	}

	public void setV1(KRMSimpleNode v1) {
		this.v1 = v1;
	}

	public KRMSimpleNode getV2() {
		return v2;
	}

	public void setV2(KRMSimpleNode v2) {
		this.v2 = v2;
	}

	public String getId() {
		if (id == null) setId(UUID.randomUUID().toString());
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean equals(Object o) {
		if (o instanceof KRMEdge) {
			KRMEdge edge = (KRMEdge) o;
			return v1.equals(edge.getV1()) && v2.equals(edge.getV2());
		}
		return false;
	}
	
	public int hashCode() {
		return v2.hashCode() + v2.hashCode();
	}
}
