package de.unigoe.informatik.swe.kcast.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a block. A compound statement, or block, is a brace-enclosed sequence of statements and declarations. [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax: <code>{</code> <em>statement</em> ... <code>}</code>
 * @author Ella Albrecht
 *
 */
public class MyBlock extends MyStatement{
	
	/**
	 * The sequence of statements within the block
	 */
	private List<MyStatement> statements;

	/* The KCs defined for the block element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.BLOCK));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new empty block
	 */
	public MyBlock() {
		statements = new ArrayList<MyStatement>();
	}
	
	/**
	 * Creates a block which contains the given statements
	 * @param statements List of statements which represents the sequence of statements in the block
	 */
	public MyBlock(List<MyStatement> statements) {
		this.statements = statements;
	}
	
	/**
	 * 
	 * @return Sequence of statements contained in the block
	 */
	public List<MyStatement> getStatements() {
		return statements;
	}

	/**
	 * 
	 * @param statements Sequence of statements which has to be set in the block
	 */
	public void setStatements(List<MyStatement> statements) {
		this.statements = statements;
	}
	
	/**
	 * Adds a statement at the end of the sequence of statements in the block
	 * @param statement Statement to be added
	 */
	public void addStatement(MyStatement statement) {
		statements.add(statement);
	}

	@Override
	public boolean isBlock() {
		return true;
	}

	@Override
	public boolean isDeclaration() {
		return false;
	}

	@Override
	public boolean isExpression() {
		return false;
	}

	@Override
	public boolean isSelection() {
		return false;
	}

	@Override
	public boolean isIteration() {
		return false;
	}

	@Override
	public boolean isJump() {
		return false;
	}

	@Override
	public boolean isLabel() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isCaseLabel() {
		return false;
	}

	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (level > 1) {
				kcs.addAll(kcs2);
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		// add KCs for subelements
		for (MyStatement stmt : statements) {
			kcs.addAll(stmt.getKCs(level));
		}
		
		return kcs;
	}
	
	/**
	 * Returns the KCs only for this element, not including those of the sub-elements
	 * @param level The level of the KCs
	 * @return Set of KCs for this element
	 */
	public Set<KnowledgeComponent> getBasicKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (level > 1) {
				kcs.addAll(kcs2);
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyBlock) {
			MyBlock block = (MyBlock) o;
			return (statements.equals(block.getStatements()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "BLOCK\n";
		text += "Statements: \n" ;
		for (MyStatement stmt : statements) {
			text += stmt.toString() + "\n";
		}
		return text;
	}
	
	@Override
	public int hashCode(){
		return statements.hashCode();
	}

	@Override
	public boolean isDefaultLabel() {
		return false;
	}
}
