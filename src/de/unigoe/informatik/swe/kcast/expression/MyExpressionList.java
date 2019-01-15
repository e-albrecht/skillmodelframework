package de.unigoe.informatik.swe.kcast.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a list of expression
 * @author Ella Albrecht
 *
 */
public class MyExpressionList extends MyExpression {
	
	/**
	 * A list of the expression in the expression list
	 */
	private List<MyExpression> expressions;
	
	/* The KCs defined for the expression list element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION_LIST));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new expression list
	 * @param expressions Expression in the list
	 */
	public MyExpressionList(List<MyExpression> expressions) {
		this.expressions = expressions;
	}
	
	/**
	 * Creates a new empty expression list
	 */
	public MyExpressionList() {
		expressions = new ArrayList<MyExpression>();
	}
	
	/**
	 * 
	 * @return A list of expression contained in the expression list
	 */
	public List<MyExpression> getExpressions() {
		return expressions;
	}

	/**
	 * 
	 * @param expressions Expressions to be set as the expression list
	 */
	public void setExpressions(List<MyExpression> expressions) {
		this.expressions = expressions;
	}
	
	/**
	 * Adds an expression to the expression list
	 * @param expression The expression to be added
	 */
	public void addExpression(MyExpression expression) {
		expressions.add(expression);
	}
	
	@Override
	public boolean isPrimary() {
		return false;
	}

	@Override
	public boolean isAssignment() {
		return false;
	}

	@Override
	public boolean isIncDecrement() {
		return false;
	}

	@Override
	public boolean isArithmetic() {
		return false;
	}

	@Override
	public boolean isLogical() {
		return false;
	}

	@Override
	public boolean isComparison() {
		return false;
	}

	@Override
	public boolean isMemberAccess() {
		return false;
	}

	@Override
	public boolean isFunctionCall() {
		return false;
	}

	@Override
	public boolean isCommaOperator() {
		return false;
	}

	@Override
	public boolean isTypeCast() {
		return false;
	}

	@Override
	public boolean isConditionalOperator() {
		return false;
	}

	@Override
	public boolean isSizeOf() {
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
				if (level > 2) {
					kcs.addAll(kcs3);
				}
			}		
		}	
		for (MyExpression expr : expressions) {
			kcs.addAll(expr.getKCs(level));
		}
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyExpressionList) {
			MyExpressionList exprList = (MyExpressionList) o;
			return (exprList.getExpressions().equals(expressions));
		}
		return false;
	}
	
	@Override 
	public String toString() {
		String text = "EXPRESSION LIST\nExpressions:";
		for (MyExpression expression : expressions) {
			text += "\n" + expression;
		}
		return text;	}

	
}
