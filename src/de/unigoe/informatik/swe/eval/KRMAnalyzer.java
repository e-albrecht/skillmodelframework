package de.unigoe.informatik.swe.eval;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KRM;
import de.unigoe.informatik.swe.krm.KRMComplexNode;
import de.unigoe.informatik.swe.krm.KRMSimpleNode;
import de.unigoe.informatik.swe.krm.KAM;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class KRMAnalyzer {

	private KRM krm;
	private Map<String, Set<Multiset<KnowledgeComponent>>> pathMap;
	
	public KRMAnalyzer(KRM krm) {
		this.krm = krm;
		pathMap = new HashMap<String, Set<Multiset<KnowledgeComponent>>>();
	}

	public KRM getKrm() {
		return krm;
	}

	public void setKrm(KRM krm) {
		this.krm = krm;
	}
	
	public Set<Multiset<KnowledgeComponent>> getPaths() {
		return getPathsForNode(krm.getRoot());
	}
	
/*	public void getPath(Set<Set<KnowledgeComponent>> paths, Set<KnowledgeComponent> path, KRMNode node) {
		
		if (node.isSimple()) {
			path.addAll(node.getKCs());
		    if (node.getSuccessors().isEmpty()) paths.add(path);
		    else {
			  for (KRMNode succ : node.getSuccessors()) {
				Set<KnowledgeComponent> newPath = new HashSet<KnowledgeComponent>();
				newPath.addAll(path);
				getPath(paths,newPath,succ);
			  }
		  }
		} else {
			KRMAnalyzer krmA = new KRMAnalyzer(((KRMComplexNode) node).getEmbeddedKRM());
			for (Set<KnowledgeComponent> eP : krmA.getPaths()) {
			    if (node.getSuccessors().isEmpty()) {
			    	Set<KnowledgeComponent> newPath = new HashSet<KnowledgeComponent>();
			    	newPath.addAll(path);
			    	newPath.addAll(eP);
			    	paths.add(newPath);
			    } else {
			    	for (KRMNode succ : node.getSuccessors()) {
						Set<KnowledgeComponent> newPath = new HashSet<KnowledgeComponent>();
						newPath.addAll(path);
						newPath.addAll(eP);
						getPath(paths,newPath,succ);
				  }
			    }
			}
		}
	}*/
	
	public static Multiset<KnowledgeComponent> getPathsForKAM(KAM kam) {
		KRM krm = kam.createKRM();
		return getPathsForSingleKRM(krm);
	}
	
	public static Multiset<KnowledgeComponent> getPathsForSingleKRM(KRM krm) {
		Multiset<KnowledgeComponent> path = HashMultiset.create();
		for (KRMSimpleNode node : krm.getNodes()) {
			if (node.isSimple()) path.addAll(node.getKCs());
			else {
				KRM embedded = ((KRMComplexNode) node).getEmbeddedKRM();
				path.addAll(getPathsForSingleKRM(embedded));
			}
		}
		return path;
			
	}
	
	public static List<KRMSimpleNode> getPathForSingleKRM(KRM krm) {
		List<KRMSimpleNode> path = new LinkedList<KRMSimpleNode>();
		for (KRMSimpleNode node : krm.getNodes()) {
			if (node.isSimple()) path.add(node);
			else {
				KRM embedded = ((KRMComplexNode) node).getEmbeddedKRM();
				path.addAll(getPathForSingleKRM(embedded));
			}
		}
		return path;		
	}
	
	public Set<Multiset<KnowledgeComponent>> getPathsForNode(KRMSimpleNode node) {
		if (pathMap.get(node) != null) return pathMap.get(node.getId());  // path already calculated
		else {
			Set<Multiset<KnowledgeComponent>> paths = new HashSet<Multiset<KnowledgeComponent>>();
			if (!node.getSuccessors().isEmpty()) { // not last node in tree
				for (KRMSimpleNode succ : node.getSuccessors()) {
					Set<Multiset<KnowledgeComponent>> succPaths = getPathsForNode(succ);
					for (Multiset<KnowledgeComponent> succPath : succPaths) {
						if (node.isSimple()) {
							Multiset<KnowledgeComponent> path = HashMultiset.create();
							path.addAll(node.getKCs());
							path.addAll(succPath);
							paths.add(path);
						}
						else {
							for (Multiset<KnowledgeComponent> innerPath : getPathsForNode(((KRMComplexNode)node).getEmbeddedKRM().getRoot())) {
								Multiset<KnowledgeComponent> path = HashMultiset.create();
								path.addAll(node.getKCs());
								path.addAll(innerPath);
								path.addAll(succPath);
								paths.add(path);							
							}
						}
					}
				}			
			} else { // node is leaf
				if (node.isSimple()) {
					Multiset<KnowledgeComponent> path = HashMultiset.create();
					path.addAll(node.getKCs());
					paths.add(path);
				}
				else {
					for (Multiset<KnowledgeComponent> innerPath : getPathsForNode(((KRMComplexNode)node).getEmbeddedKRM().getRoot())) {
						Multiset<KnowledgeComponent> path = HashMultiset.create();
						path.addAll(node.getKCs());
						path.addAll(innerPath);
						paths.add(path);							
					}
				}
			}
			pathMap.put(node.getId(), paths);
			return paths;
		}
	}
	

	public static Set<KnowledgeComponent> necessaryKCs(Set<Multiset<KnowledgeComponent>> paths) {
		Set<KnowledgeComponent> necessary = new HashSet<KnowledgeComponent>();
		Multiset<KnowledgeComponent> first = paths.iterator().next();
		for (KnowledgeComponent kc : first.elementSet()) {
			boolean contains = true;
			for (Multiset<KnowledgeComponent> path : paths) {
				if (!path.elementSet().contains(kc)) contains = false;
			}
			if (contains) necessary.add(kc);
		}
		
		return necessary;
	}
	
	public static Set<KnowledgeComponent> getMostCommonPath(KRM krm) {
		KRMSimpleNode root = krm.getRoot();
		return getMostCommonPath(root);
		
	}
	
	public static Set<KnowledgeComponent> getMostCommonPath(KRMSimpleNode node) {
		Set<KnowledgeComponent> path = new HashSet<KnowledgeComponent>();
		KRMSimpleNode next = getMaxSuccessor(node);
		if (next != null) {
			path.addAll(next.getKCs());
			path.addAll(getMostCommonPath(next));
		}
		return path;
	}
	
	private static KRMSimpleNode getMaxSuccessor(KRMSimpleNode node) {
		int maxCount = 0;
		KRMSimpleNode maxNode = null;
		for (KRMSimpleNode succ : node.getSuccessors()) {
			int count = succ.getCount();
			if (count > maxCount) {
				maxCount = count;
				maxNode = succ;
			} else if (count == maxCount) {
				KRMSimpleNode nextSucc = getMaxSuccessor(maxNode);
				KRMSimpleNode nextNewSucc = getMaxSuccessor(succ);
				if (nextSucc != null) {
					if (nextNewSucc != null) {	
						int oldMax = getMaxSuccessor(maxNode).getCount();
						int newMax = getMaxSuccessor(succ).getCount();
						if (newMax > oldMax) maxNode = succ;
					} else maxNode = succ;
				}
			}
		}
		return maxNode;
	}
	
	public int numberOfNodes() {
		return numberOfEmbeddedNodes(krm);
	}
	
	private int numberOfEmbeddedNodes(KRM krm) {
		int num = 0;
		num += 1 + krm.getNodes().size();
		for (KRMSimpleNode node : krm.getNodes()){
			if (node.isComplex()) { // add number for ebedded KRMs of complex nodes
				num += numberOfEmbeddedNodes(((KRMComplexNode) node).getEmbeddedKRM());
			}
		}
		return num;
	}
	
	public int numberOfEdges() {
		return numberOfEmbeddedEdges(krm);
	}
	
	private int numberOfEmbeddedEdges(KRM krm) {
		int num = 0;
		num += krm.getEdges().size();
		for (KRMSimpleNode node : krm.getNodes()){
			if (node.isComplex()) { // add number for embedded KRMs of complex nodes
				num += numberOfEmbeddedEdges(((KRMComplexNode) node).getEmbeddedKRM());
			}
		}
		return num;
	}
	
	public int getMinimumPathSize(Set<Set<KnowledgeComponent>> paths) {
		int min = Integer.MAX_VALUE;
		for (Set<KnowledgeComponent> path : paths) {
			if (path.size() < min) min = path.size();
		}
		
		return min;
	}
	
	public int getMaximumPathSize(Set<Set<KnowledgeComponent>> paths) {
		int max = Integer.MIN_VALUE;
		for (Set<KnowledgeComponent> path : paths) {
			if (path.size() > max) max = path.size();
		}
		
		return max;
	}
	
	public double getAvergaePathSize(Set<Set<KnowledgeComponent>> paths) {
		double size = 0;
		int counter = 0;
		for (Set<KnowledgeComponent> path : paths) {
			counter++;
			size += path.size();
		}
		
		return size/counter;
	}
	
	public double claculateSD(double avg, Set<Set<KnowledgeComponent>> paths) {
		double sd = 0;
		for (Set<KnowledgeComponent> path : paths) {
			double curSD = (avg-path.size()) * (avg-path.size());
			sd += curSD;
		}
		return Math.sqrt(sd/paths.size());
	}
	
	
}
