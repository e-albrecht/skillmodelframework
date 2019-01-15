package de.unigoe.informatik.swe.eval;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class Observation {

	private int studentId;
	private Map<KnowledgeComponent,Integer> kcRequired;
	private Map<KnowledgeComponent,Integer> kcSuccess;
	private Map<KnowledgeComponent,Integer> kcFailure;
	private int result;
	
	public Observation() {}
	
	public Observation(int studentId) {
		this.studentId = studentId;
		kcRequired = new HashMap<KnowledgeComponent,Integer>();
		kcSuccess = new HashMap<KnowledgeComponent,Integer>();
		kcFailure = new HashMap<KnowledgeComponent,Integer>();
		result = 2;
		initializeObservations();
	}
	
	private void initializeObservations() {
		for (KnowledgeComponent kc : KnowledgeComponent.values()) {
			kcRequired.put(kc, 0);
			kcSuccess.put(kc, 0);
			kcFailure.put(kc, 0);
		}
	}
	
	public void addKcRequired(KnowledgeComponent kc) {
		kcRequired.put(kc, 1);
	}
	
	public void updateKcSuccess(KnowledgeComponent kc, int success) {
		int old = kcSuccess.get(kc);
		kcSuccess.put(kc, old+success);
	}
	
	public void updateKcFailure(KnowledgeComponent kc, int failure) {
		int old = kcFailure.get(kc);
		kcFailure.put(kc, old+failure);
	}
	
	public void addKcsRequired(Collection<KnowledgeComponent> kcs) {
		for (KnowledgeComponent kc : kcs) {
			addKcRequired(kc);
		}
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Map<KnowledgeComponent,Integer> getKcFailure() {
		return kcFailure;
	}
	public void setKcFailure(Map<KnowledgeComponent,Integer> kcFailure) {
		this.kcFailure.putAll(kcFailure);
	}
	public Map<KnowledgeComponent,Integer> getKcSuccess() {
		return kcSuccess;
	}
	public void setKcSuccess(Map<KnowledgeComponent,Integer> kcSuccess) {
		this.kcSuccess.putAll(kcSuccess);
	}
	public Map<KnowledgeComponent,Integer> getKcRequired() {
		return kcRequired;
	}
	public void setKcRequired(Map<KnowledgeComponent,Integer> kcRequired) {
		this.kcRequired = kcRequired;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
}
