package de.unigoe.informatik.swe.krm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import de.unigoe.informatik.swe.data.Solution;
import de.unigoe.informatik.swe.eval.KRMAnalyzer;
import de.unigoe.informatik.swe.kcast.MyInclude;
import de.unigoe.informatik.swe.kcast.MyKCAST;
import de.unigoe.informatik.swe.kcast.MyMacroDefinition;
import de.unigoe.informatik.swe.kcast.declaration.MyDeclaration;
import de.unigoe.informatik.swe.kcast.statement.MyBlock;
import de.unigoe.informatik.swe.kcast.statement.MyFor;
import de.unigoe.informatik.swe.kcast.statement.MyIf;
import de.unigoe.informatik.swe.kcast.statement.MyStatement;
import de.unigoe.informatik.swe.kcast.statement.MySwitch;
import de.unigoe.informatik.swe.kcast.statement.MyWhile;
import de.unigoe.informatik.swe.kcast.statement.MyWhile.WhileType;
import de.unigoe.informatik.swe.krm.KRMComplexNode.KRMComplexType;

public class KAM implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8794640517354530639L;
	
	/**
	 * Nodes of the KAM in the sequential order
	 */
	private LinkedList<KRMSimpleNode> nodes;
	
	/**
	 * KC level
	 */
	private int level;
	
	/**
	 * Identifier of the KAM
	 */
	private String id;
	
	/**
	 * Id of solution the KAM is based on
	 */
	private int solutionId;
	
	/**
	 * Id of the student on whose solution the KAM is based on
	 */
	private int studentId;
	
	/**
	 * Source code the KAM is representing
	 */
	private String sourceCode;
	
	/**
	 * The length of the longest path in the KAM
	 */
	private int pathSize;
		
	/**
	 * Creates a new empty KAM
	 * @param level KC level
	 * @param solutionId Id of the solution the KAM is based on
	 * @param sourceCode Source code the KAM is representing
	 */
	public KAM(int level, int solutionId, int studentId, String sourceCode) {
		nodes = new LinkedList<KRMSimpleNode>();
		this.level = level;
		setId(UUID.randomUUID().toString());
		this.setSolutionId(solutionId);
		this.setSourceCode(sourceCode);
		this.studentId = studentId;
	}
	
	/**
	 * Creates a new empty KAM
	 * @param level KC level
	 * @param solution Solution the KAM is representing
	 */
	public KAM(int level, Solution solution) {
		this(level, solution.getId(), solution.getStudentId(), solution.getSourceCode());
	}

	/**
	 * 
	 * @return Nodes of the KAM
	 */
	public LinkedList<KRMSimpleNode> getNodes() {
		return nodes;
	}

	/**
	 * 
	 * @param nodes Nodes of the KAM
	 */
	public void setNodes(LinkedList<KRMSimpleNode> nodes) {
		this.nodes = nodes;
	}
	
	/**
	 * 
	 * @return KC level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 
	 * @param level KC level
	 */
	public void setLevel(int level) {
		this.level = level;
	}	
	
	/**
	 * 
	 * @return Set of KCs contained in the KAM
	 */
	public Set<KnowledgeComponent> getKCs() {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		for (KRMSimpleNode node : nodes) {
			kcs.addAll(node.getKCs());
		}
		return kcs;
	}


		public void construct(MyKCAST tu) {
			// root node of KRM contains includes and defines
			KRMSimpleNode root = new KRMSimpleNode();
			
			// add includes
			for (MyInclude inc : tu.getIncludeMacros()) {
				root.addKCs(inc.getKCs(level));
			}
			
			//add macros
			for (MyMacroDefinition def : tu.getDefineMacros()) {
				root.addKCs(def.getKCs(level));
			}
			if (!root.getKCs().isEmpty()) nodes.add(root);
			
			// translation unit only contains only either functions or public variables
			for (MyDeclaration decl : tu.getDeclarations()) {
				if (decl != null) {
					if (decl.containsFunction()) { //declaration is a complex type/function
						constructFunctionBlock(decl,this);
					}
					else {
						constructSimpleNode(decl.getKCs(level), this, false);
					}
				}
			}
		}
		
		private void constructSimpleNode(Set<KnowledgeComponent> kcs, KAM krm, boolean root) {
			if (!krm.getNodes().isEmpty() && !root){
				KRMSimpleNode prev = krm.getNodes().getLast();
				if (prev.isSimple())((KRMSimpleNode) prev).addKCs(kcs); // add KCs to previous simple node
				else {
					KRMSimpleNode newSimple = new KRMSimpleNode();
					newSimple.addKCs(kcs);
					krm.addNode(newSimple);
				}
			} else { // create new simple node
				KRMSimpleNode newSimple = new KRMSimpleNode();
				newSimple.addKCs(kcs);
				krm.addNode(newSimple);
			}
		}
		
		private void constructFunctionBlock(MyDeclaration decl, KAM krm) {
			KRMComplexNode newComplex = new KRMComplexNode(KRMComplexType.FUNCTION);
			KAM embedded = new KAM(level,solutionId,studentId,decl.getRawSignature());

			// root of functions contains KCs of type specifier and parameters
			KRMSimpleNode root = new KRMSimpleNode();
			root.addKCs(decl.getKCsForFunction(level));
			
			// add krm components for function body
			embedded.addNode(root);
			KRMSimpleNode first = new KRMSimpleNode();
			if (decl.getFunctionBody() != null) first.addKCs(decl.getFunctionBody().getBasicKCs(level));
			embedded.addNode(first);
			constructBlock(decl.getFunctionBody(),embedded, false);
			
			newComplex.setEmbeddedKRM(embedded.createKRM());
			krm.addNode(newComplex);
		}
		
		private void constructBlock(MyBlock block, KAM krm, boolean root) {
			if (block != null) {
				int size = block.getStatements().size();
				if (size > 0) {
					MyStatement first = block.getStatements().get(0);
					constructStatement(first,krm,root);
					for (int i=1; i<size; i++) {
						constructStatement(block.getStatements().get(i),krm, false);
					}
				}
			}
		}

		private void constructStatement(MyStatement stmt, KAM krm, boolean root) {
			if (stmt instanceof MyBlock) constructBlock((MyBlock) stmt, krm, root);
			else if (stmt instanceof MyFor) constructFor((MyFor) stmt, krm);
			else if (stmt instanceof MyIf) constructIf((MyIf) stmt, krm);
			else if (stmt instanceof MyWhile) constructWhile((MyWhile) stmt, krm);
			else if (stmt instanceof MySwitch) constructSwitch((MySwitch) stmt, krm);
			else if (stmt != null) constructSimpleNode(stmt.getKCs(level), krm, root);		
		}

		private void constructWhile(MyWhile stmt,KAM krm) {
			KRMComplexType type = KRMComplexType.LOOP;
			KRMSimpleNode root = new KRMSimpleNode();
			root.addKCs(stmt.getBasicKCs(level));
			if (level > 1) {
				type = KRMComplexType.WHILE;
				if (stmt.getType().equals(WhileType.DO_WHILE)) {
					type = KRMComplexType.DO_WHILE;
				}
			}
			KRMComplexNode newComplex = new KRMComplexNode(type);
			KAM embedded = new KAM(level,solutionId,studentId,stmt.getRawSignature());

			// root of while contains KCs of conditions
			
			if (stmt.getCondition() != null) root.addKCs(stmt.getCondition().getKCs(level));
			
			// add krm components for while body
			embedded.addNode(root);
			
			boolean rroot = true;
			if (stmt.getBlock() instanceof MyBlock) {
				KRMSimpleNode first = new KRMSimpleNode();
				first.addKCs(((MyBlock) stmt.getBlock()).getBasicKCs(level));
				embedded.addNode(first);
				rroot = false;
			}		
			constructStatement(stmt.getBlock(),embedded, rroot);
			
			newComplex.setEmbeddedKRM(embedded.createKRM());
			krm.addNode(newComplex);
		}

		private void constructSwitch(MySwitch stmt,  KAM krm) {
			KRMComplexType type = KRMComplexType.SELECTION;
			if (level > 1) {
				type = KRMComplexType.SWITCH;
			}
			KRMComplexNode newComplex = new KRMComplexNode(type);
			KAM embedded = new KAM(level,solutionId, studentId, stmt.getRawSignature());
		
			// root of switch contains KCs of switch expression
			KRMSimpleNode root = new KRMSimpleNode();
			root.addKCs(stmt.getBasicKCs(level));
			root.addKCs(stmt.getSwitchExpression().getKCs(level));
			
			// add krm components for switch body
			embedded.addNode(root);
			constructSwitchBlock(stmt.getBlock(), embedded, true);
			
			newComplex.setEmbeddedKRM(embedded.createKRM());
			krm.addNode(newComplex);
		}

		private void constructSwitchBlock(MyStatement stmt, KAM krm, boolean root) {
			if (stmt instanceof MyBlock) {
			  MyBlock block = (MyBlock) stmt;
			  int size = block.getStatements().size();
			    if (size > 0) {
				  MyStatement first = block.getStatements().get(0);
				  constructStatement(first,krm,root);
				  for (int i=1; i<size; i++) {
					MyStatement next = block.getStatements().get(i);
					if (next.isLabel()) root = true;
					else root = false;
				    constructStatement(block.getStatements().get(i),krm, root);
				  }
				}
			
			} else constructStatement(stmt,krm,root);
			
		}

		private void constructIf(MyIf stmt, KAM krm) {
			KRMComplexType type = KRMComplexType.SELECTION;
			if (level > 1) {
				type = KRMComplexType.IF;
			}
			KRMComplexNode newComplex = new KRMComplexNode(type);			
			KAM embedded = new KAM(level, solutionId, studentId, stmt.getRawSignature());
		
			// root of if contains KCs of condition
			KRMSimpleNode root = new KRMSimpleNode();
			root.addKCs(stmt.getBasicKCs(level));
			if(stmt.getCondition() != null) root.addKCs(stmt.getCondition().getKCs(level));
			
			// add krm components for then block
			embedded.addNode(root);
			KRMSimpleNode thenN = new KRMSimpleNode();
			thenN.addKC(KnowledgeComponent.THEN);
			if (stmt.getThenBranch() instanceof MyBlock) {
				thenN.addKCs(((MyBlock) stmt.getThenBranch()).getBasicKCs(level));
			}	
			embedded.addNode(thenN);
			constructStatement(stmt.getThenBranch(), embedded, false);

			// add krm components for else block
			
			if (stmt.getElseBranch() != null) {
				KRMSimpleNode elseN = new KRMSimpleNode();
				elseN.addKC(KnowledgeComponent.ELSE);
				if (stmt.getElseBranch() instanceof MyBlock) {
					elseN.addKCs(((MyBlock) stmt.getElseBranch()).getBasicKCs(level));
				}	
				embedded.addNode(elseN);
				constructStatement(stmt.getElseBranch(),embedded, false);
			}
			
			newComplex.setEmbeddedKRM(embedded.createKRM());
			krm.addNode(newComplex);
		}

		private void constructFor(MyFor stmt, KAM krm) {
			KRMComplexType type = KRMComplexType.LOOP;
			if (level > 1) {
				type = KRMComplexType.FOR;
			}
			KRMComplexNode newComplex = new KRMComplexNode(type);
			KAM embedded = new KAM(level,solutionId,studentId,stmt.getRawSignature());
		
			// root of for contains KCs of conditions
			KRMSimpleNode root = new KRMSimpleNode();
			root.addKCs(stmt.getBasicKCs(level));
			if (stmt.getInit() != null) root.addKCs(stmt.getInit().getKCs(level));
			if (stmt.getCondition() != null) root.addKCs(stmt.getCondition().getKCs(level));
			if (stmt.getIterationExpression() != null) root.addKCs(stmt.getIterationExpression().getKCs(level));
			
			// add krm components for for body
			embedded.addNode(root);
			
			boolean rroot = true;
			if (stmt.getBlock() instanceof MyBlock) {
				KRMSimpleNode first = new KRMSimpleNode();
				first.addKCs(((MyBlock) stmt.getBlock()).getBasicKCs(level));
				embedded.addNode(first);
				rroot = false;
			}		
			constructStatement(stmt.getBlock(),embedded, rroot);
			
			newComplex.setEmbeddedKRM(embedded.createKRM());
			krm.addNode(newComplex);
		}

		public void exportToGraphML(String filepath) {
			System.out.println(generateGraphML());
		}
		
		private String generateGraphML() {
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
			xml += generateEmbeddedXML(createKRM(),null);
			xml += "</graphml>";
			return xml;
		}
		
		private String generateEmbeddedXML(KRM krm, KRMSimpleNode parent) {
			String xml = "<graph edgedefault=\"directed\" id=\"" + krm.getId() + "\">\n";
			for (KRMSimpleNode node : krm.getNodes()) {
				xml += generateNodeXML(node,parent);
			}
			for (KRMEdge edge :  krm.getEdges()) {
				xml += generateEdgeXML(edge,parent);
			}
			xml += "</graph>\n";
			return xml;
		}

		private String generateEdgeXML(KRMEdge edge, KRMSimpleNode parent) {
			String v1Id ="";
			String v2Id ="";
			if (parent != null) {
				v1Id = edge.getV1().getId();
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

		public void addNode(KRMSimpleNode node) {
			nodes.add(node);			
		}
		
		public Set<KRMEdge> generateEdges() {
			Set<KRMEdge> edges = new HashSet<KRMEdge>();
			KRMSimpleNode prev = null;
			for (KRMSimpleNode node : nodes) {
				if (prev != null) {
					edges.add(new KRMEdge(prev,node));
					prev.addSuccessor(node);
					//node.addPredecessor(prev);
				}
				prev = node;
			}
			return edges;
		}

		public Set<KRMEdge> getEdges() {
			return generateEdges();
		}
		
		public KRM createKRM(){
			KRM krm = new KRM(level);
			KRMSimpleNode prev = krm.getRoot();
			for (KRMSimpleNode node : nodes) {
				krm.addNode(node,solutionId);
				krm.addEdge(new KRMEdge(prev,node));
				prev = node;
			}
			return krm;
		}

		public int getSolutionId() {
			return solutionId;
		}

		public void setSolutionId(int solutionId) {
			this.solutionId = solutionId;
		}

		public String getSourceCode() {
			return sourceCode;
		}

		public void setSourceCode(String sourceCode) {
			this.sourceCode = sourceCode;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getStudentId() {
			return studentId;
		}

		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}
		
		public boolean equals(Object o) {
			if (o instanceof KAM) {
				return ((KAM)o).getSolutionId() == solutionId;
			}
			return false;
		}
		
		public int hashCode() {
			return solutionId;
		}
		
		public int getPathSize() {
			if (pathSize == 0) {
				pathSize = KRMAnalyzer.getPathForSingleKRM(this.createKRM()).size();
			}
			return pathSize;
		}
		
		
	}

	

