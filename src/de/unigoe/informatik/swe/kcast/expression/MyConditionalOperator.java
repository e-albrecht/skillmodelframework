package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Respresents a conditional operator<br /><br />
 * Syntax: <em>condition</em><code> ? </code><em>expression-true</em><code> : </code><em>expression-false</em> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyConditionalOperator extends MyExpression{

	/**
	 * The condition
	 */
	private MyExpression condition;
	
	/**
	 * Expression that is evaluated if the condition is true
	 */
	private MyExpression expressionTrue;
	
	/**
	 * Expression that is evaluated if the condition is false
	 */
	private MyExpression expressionFalse;
	
	/* The KCs defined for the conditional operator element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.CONDITIONAL_OPERATOR));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new conditional operator
	 * @param condition The condition of the operator
	 * @param expressionTrue Expression that is evaluated if the condition is true
	 * @param expressionFalse Expression that is evaluated if the condition is false
	 */
	public MyConditionalOperator(MyExpression condition, MyExpression expressionTrue, MyExpression expressionFalse) {
		this.condition = condition;
		this.expressionFalse = expressionFalse;
		this.expressionTrue = expressionTrue;
	}
	
	/**
	 * 
	 * @param condition The condition of the operator
	 */
	public void setCondition(MyExpression condition) {
		this.condition = condition;
	}

	/**
	 * 
	 * @return The condition of the operator
	 */
	public MyExpression getCondition() {
		return condition;
	}	
	
	/**
	 * 
	 * @return Expression that is evaluated if the condition is true
	 */
	public MyExpression getExpressionTrue() {
		return expressionTrue;
	}

	/**
	 * 
	 * @param expressionTrue Expression that is evaluated if the condition is true
	 */
	public void setExpressionTrue(MyExpression expressionTrue) {
		this.expressionTrue = expressionTrue;
	}

	/**
	 * 
	 * @return Expression that is evaluated if the condition is false
	 */
	public MyExpression getExpressionFalse() {
		return expressionFalse;
	}

	/**
	 * 
	 * @param expressionFalse Expression that is evaluated if the condition is false
	 */
	public void setExpressionFalse(MyExpression expressionFalse) {
		this.expressionFalse = expressionFalse;
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
		return true;
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
		kcs.addAll(condition.getKCs(level));
		kcs.addAll(expressionTrue.getKCs(level));
		kcs.addAll(expressionFalse.getKCs(level));
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyConditionalOperator) {
			MyConditionalOperator cond = (MyConditionalOperator) o;
			return (condition.equals(cond.getCondition())) && expressionTrue.equals(cond.getExpressionTrue()) && expressionFalse.equals(cond.getExpressionFalse());
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "CONDITIONAL OPERATOR\n";
		text += "Condition: " + condition + "\n";
		text += "If true: " + expressionTrue + "\n";
		text += "If false: " + expressionFalse + "\n";
		return text;
	}	
}
