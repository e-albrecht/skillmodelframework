package de.unigoe.informatik.swe.krm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class KRMSimpleNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6842547963828533368L;
	private TreeSet<KnowledgeComponent> kcs;
	private int count;
	//private HashSet<KRMSimpleNode> predecessors;
	private HashSet<KRMSimpleNode> successors;
	private String id;
	private HashSet<Integer> solutions;
	
	public KRMSimpleNode() {
		kcs = new TreeSet<KnowledgeComponent>();
		// predecessors = new HashSet<KRMSimpleNode>();
		successors = new HashSet<KRMSimpleNode>();
		count = 1;
		setId(UUID.randomUUID().toString());
		solutions = new HashSet<Integer>();
	}
	
	public KRMSimpleNode(TreeSet<KnowledgeComponent> kcs) {
		if (kcs!= null) this.kcs = kcs;
		else kcs = new TreeSet<KnowledgeComponent>();
		//predecessors = new HashSet<KRMSimpleNode>();
		successors = new HashSet<KRMSimpleNode>();		
		count = 1;
		setId(UUID.randomUUID().toString());
		solutions = new HashSet<Integer>();
	}
	
	public boolean isSimple() {
		return true;
	}

	public boolean isComplex() {
		return false;
	}

	public Set<KnowledgeComponent> getKCs() {
		return kcs;
	}

	/*public void setKcs(Set<KnowledgeComponent> kcs) {
		this.kcs = kcs;
	}*/
	
	public String toString() {
		return kcs.toString();
	}
	
	public boolean equals(Object o) {
		if (o instanceof KRMSimpleNode) {
			KRMSimpleNode node = (KRMSimpleNode) o;
			//return (node.getKCs().equals(kcs) && node.getSuccessors().equals(successors));
			return node.getId().equals(id);
		}
		return false;	
	}
	
	public void addKCs(Set<KnowledgeComponent> kcs) {
		this.kcs.addAll(kcs);
	}
	
	public void addKC(KnowledgeComponent kc) {
		kcs.add(kc);
	}

	public int getCount() {
		return count;
	}

	public void increaseCount() {
		count++;
	}

/*	public HashSet<KRMSimpleNode> getPredecessors() {
		return predecessors;
	}*/

	public HashSet<KRMSimpleNode> getSuccessors() {
		return successors;
	}

/*	public void addPredecessor(KRMSimpleNode node) {
		predecessors.add(node);
		
	} */

	public void addSuccessor(KRMSimpleNode node) {
		
		successors.add(node);
	}

/*	public KRMSimpleNode isPredecessor(KRMSimpleNode node) {
		for (KRMSimpleNode n : predecessors) {
			if (n.sameKCs(node)) return n;
		}
		return null;	} */

	public KRMSimpleNode isSuccessor(KRMSimpleNode node) {
		for (KRMSimpleNode n : successors) {
			if (n.isComplex() && node.isComplex()) {
				KRMComplexNode cN = (KRMComplexNode) n;
				KRMComplexNode cNode = (KRMComplexNode) node;
				if (cN.getType().equals(cNode.getType())) return n;
			}
			else if (n.isSimple() && node.isSimple() && n.sameKCs(node)) return n;
		}
		return null;
	}
	
	public boolean sameKCs(KRMSimpleNode node) {
		return kcs.equals(node.getKCs());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		String label = "";
		for (KnowledgeComponent kc : kcs) label += kc + "\n";
		return label;
	}
	
	public int hashCode() {
		return kcs.hashCode();
	}

	public HashSet<Integer> getSolutions() {
		return solutions;
	}

	public void setSolutions(HashSet<Integer> solutions) {
		this.solutions = solutions;
	}
	
	public void addSolution(int solution) {
		solutions.add(solution);
	}

}
