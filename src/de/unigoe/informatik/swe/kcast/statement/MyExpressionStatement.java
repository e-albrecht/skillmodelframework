package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an expression statement<br /><br />
 * Syntax: <em>expression</em> <code>;</code> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyExpressionStatement extends MyStatement{
	
	/**
	 * The expression of the statement
	 */
	private MyExpression expression;
	
	/* The KCs defined for the for loop element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new expression statement
	 * @param expression The expression of the statement
	 */
	public MyExpressionStatement(MyExpression expression) {
		this.expression = expression;
	}
	
	/**
	 * 
	 * @return The expression of statement
	 */
	public MyExpression getExpression() {
		return expression;
	}

	/**
	 * 
	 * @param expression The expression of the statement
	 */
	public void setExpression(MyExpression expression) {
		this.expression = expression;
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
		kcs.addAll(expression.getKCs(level));
		
		return kcs;
	}
	
	@Override 
	public boolean isCaseLabel() {
		return false;
	}
	
	@Override 
	public boolean isDefaultLabel() {
		return false;
	}
	
	@Override
	public boolean isBlock() {
		return false;
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
		return true;
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
	public boolean equals(Object o) {
		if (o instanceof MyExpressionStatement) {
			MyExpressionStatement exp = (MyExpressionStatement) o;
			return (expression.equals(exp.getExpression()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "EXPRESSION STATEMENT\n";
		text += "Expression: " + expression + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		return expression.hashCode()+1;
	}
}
