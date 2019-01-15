package de.unigoe.informatik.swe.krm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import de.unigoe.informatik.swe.utils.Pair;

public class KRM implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7929193545485578798L;
	private KRMSimpleNode root;
	private HashSet<KRMSimpleNode> nodes;
	private HashSet<KRMEdge> edges;
	private String id;
	private boolean aligned;
	private HashMap<Pair<KRMSimpleNode>, Boolean> succComp;
	private int level;
	
	public KRM(int level){
		nodes = new LinkedHashSet<KRMSimpleNode>();
		edges = new HashSet<KRMEdge>();
		setId(UUID.randomUUID().toString());
		root = new KRMSimpleNode();
		succComp = new HashMap<Pair<KRMSimpleNode>,Boolean>();
		this.level = level;
	}
	
	public void addKRMSingle(KRM krm, int solution) {
		aligned = true;
		KRMSimpleNode prev = root;
		for (KRMSimpleNode node : krm.getNodes()) {
			if (aligned && !prev.getSuccessors().isEmpty()) {
				prev = compare(prev,node, solution);
			} else {
				node = addNode(node, solution);
				addEdge(new KRMEdge(prev,node));
				aligned = false;
				prev = node;
			}
		}
	}
	
	public Set<KnowledgeComponent> getKCs() {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		for (KRMSimpleNode node : nodes) kcs.addAll(node.getKCs());
		return kcs;
	}

	private KRMSimpleNode compare(KRMSimpleNode prev, KRMSimpleNode node, int solution) {
		for (KRMSimpleNode succ : prev.getSuccessors()) {
			if (succ.isComplex() && node.isComplex() && ((KRMComplexNode)succ).getType().equals(((KRMComplexNode)node).getType()) && sameSuccessors(succ,node)) {
				succ.increaseCount();
				((KRMComplexNode) succ).getEmbeddedKRM().addKRMSingle(((KRMComplexNode)node).getEmbeddedKRM(), solution);
				return succ;
			} else if (succ.isSimple() && node.isSimple() && succ.getKCs().equals(node.getKCs())) {
				succ.increaseCount();
				return succ;
			} 
		}
		node = addNode(node,solution);
		addEdge(new KRMEdge(prev,node));

		aligned = false;
		return node;		
	}

	public HashSet<KRMSimpleNode> getNodes() {
		return nodes;
	}
	
	public LinkedList<KRMSimpleNode> getOrderedNodes() {
		return getNext(root);
	}
	
	private LinkedList<KRMSimpleNode> getNext(KRMSimpleNode node) {
		LinkedList<KRMSimpleNode> next = new LinkedList<KRMSimpleNode>(); 
		for (KRMSimpleNode n : node.getSuccessors()) {
			next.add(n);
			next.addAll(getNext(n));
		}
		return next;
	}
	
	private boolean sameSuccessors(KRMSimpleNode n1, KRMSimpleNode n2) {
		Pair<KRMSimpleNode> pair = new Pair<KRMSimpleNode>(n1,n2);
		Boolean available = succComp.get(pair);
		if (available == null) {
			for (KRMSimpleNode succ1 : n1.getSuccessors()) {
				boolean equiv = false;
				for (KRMSimpleNode succ2: n2.getSuccessors()) {
					if (succ1.isSimple() && succ2.isSimple() && succ1.getKCs().equals(succ2.getKCs()) && sameSuccessors(succ1, succ2)) equiv = true;
					else if (succ1.isComplex() && succ2.isComplex() && ((KRMComplexNode)succ1).getType().equals(((KRMComplexNode)succ2).getType()) && ((KRMComplexNode)succ1).getEmbeddedKRM().equals(((KRMComplexNode)succ2).getEmbeddedKRM()) && sameSuccessors(succ1, succ2)) equiv = true;
				}
				if (!equiv) {
					succComp.put(pair, false);
					return false;
				}
			}
			for (KRMSimpleNode succ1 : n2.getSuccessors()) {
				boolean equiv = false;
				for (KRMSimpleNode succ2: n1.getSuccessors()) {
					if (succ1.isSimple() && succ2.isSimple() && succ1.getKCs().equals(succ2.getKCs()) && sameSuccessors(succ1, succ2)) equiv = true;
					else if (succ1.isComplex() && succ2.isComplex() && ((KRMComplexNode)succ1).getType().equals(((KRMComplexNode)succ2).getType()) && sameSuccessors(succ1, succ2)) equiv = true;
				}
				if (!equiv) {
					succComp.put(pair, false);
					return false;
				}
			}
			succComp.put(pair, true);
			return true;

		}
		return available;
	}

	public void setNodes(HashSet<KRMSimpleNode> nodes) {
		this.nodes = nodes;
	}
	
	public KRMSimpleNode addNode(KRMSimpleNode node, int solution) {
		if (nodes.contains(node)) {
			for (KRMSimpleNode old : nodes) {
				if (old.equals(node)) node = old;
			}
		}
		node.addSolution(solution);
		nodes.add(node);
		return node;
	}

	public Set<KRMEdge> getEdges() {
		return edges;
	}

	public void setEdges(Set<KRMEdge> edges) {
		for (KRMEdge e : edges) {
			addEdge(e);
		}
	}
	
	public void addEdge(KRMEdge edge) {
		edges.add(edge);
		KRMSimpleNode v1 = edge.getV1();
		KRMSimpleNode v2 = edge.getV2();
		v1.addSuccessor(v2);
		//v2.addPredecessor(v1);
	}
	
	public String toString() {
		String krm ="";
		for (KRMEdge e : edges) {
			krm += e.getV1().toString() + " -> " + e.getV2() + "\n-----\n";
		}
		krm += "----------\n";
		return krm;		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean equals(Object o) {
		if (o instanceof KRM) {
			KRM krm = (KRM) o;
			return sameSuccessors(krm.getRoot(), root);
		}
		return false;
	}
	
	public int hashCode() {
		return edges.hashCode();
	}

	public KRMSimpleNode getRoot() {
		return root;
	}

	public void setRoot(KRMSimpleNode root) {
		this.root = root;
	}
	
	public String generateGraphML() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
					"<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" "+
					"xmlns:java=\"http://www.yworks.com/xml/yfiles-common/1.0/java\" " +
					"xmlns:sys=\"http://www.yworks.com/xml/yfiles-common/markup/primitives/2.0\" " +
					"xmlns:x=\"http://www.yworks.com/xml/yfiles-common/markup/2.0\" " +
					"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
					"xmlns:y=\"http://www.yworks.com/xml/graphml\" " +
					"xmlns:yed=\"http://www.yworks.com/xml/yed/3\" " +
					"xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd\">\n";
		xml += " <key for=\"node\" id=\"d0\" yfiles.type=\"nodegraphics\"/>\n";
		xml += generateEmbeddedXML(this,null);
		xml += "</graphml>";
		return xml;
	}
	
	private String generateEmbeddedXML(KRM krm, KRMSimpleNode parent) {
		String xml = "<graph edgedefault=\"directed\" id=\"" + krm.getId() + "\">\n";
		xml += generateNodeXML(krm.getRoot(),parent);
		for (KRMSimpleNode node : krm.getNodes()) {
			xml += generateNodeXML(node,parent);
		}
		for (KRMEdge edge : krm.getEdges()) {
			xml += generateEdgeXML(edge,parent);
		}
		xml += "</graph>\n";
		return xml;
	}

	private String generateEdgeXML(KRMEdge edge, KRMSimpleNode parent) {
		String v1Id ="";
		String v2Id ="";
		if (parent != null) {
			v1Id =edge.getV1().getId();
			v2Id = edge.getV2().getId();
		}
		else {
			v1Id = edge.getV1().getId();
			v2Id = edge.getV2().getId();
		}
		String xml = "<edge id=\"" + edge.getId() + "\" source=\"" + v1Id + "\" target=\"" + v2Id +"\"/>\n";
		return xml;
	}

	private String generateNodeXML(KRMSimpleNode node, KRMSimpleNode parent) {
		String xml = "<node id=\"";
		xml += node.getId();
		xml+= "\">\n";
		xml += "<data key=\"d0\">\n";
		xml += "<y:ShapeNode>\n<y:NodeLabel modelName=\"internal\" modelPosition=\"t\">\n";
		xml += node.getLabel() + "\n";
		xml += "counter: " + node.getCount();
		xml += "</y:NodeLabel>\n</y:ShapeNode>\n</data>\n";
		if (node.isComplex()) {
			KRMComplexNode cNode = (KRMComplexNode) node;
			xml += generateEmbeddedXML(cNode.getEmbeddedKRM(),node);
		}
		xml += "</node>\n";
		return xml;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


}
