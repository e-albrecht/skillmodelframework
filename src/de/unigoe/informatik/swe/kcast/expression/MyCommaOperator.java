package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a comma operator <br /><br />
 * Syntax: <em>lhs</em><code>, </code><em>rhs</em> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyCommaOperator extends MyExpression{

	/**
	 * First expression
	 */
	private MyExpression lhs;
	/**
	 * Second expression
	 */
	private MyExpression rhs;
	
	/* The KCs defined for the comma operator element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.COMMA_OPERATOR));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new comma operator
	 * @param lhs First expression
	 * @param rhs Second expression
	 */
	public MyCommaOperator(MyExpression lhs, MyExpression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	/**
	 * 
	 * @return First expression
	 */
	public MyExpression getLhs() {
		return lhs;
	}
	
	/**
	 * 
	 * @param lhs First expression
	 */
	public void setLhs(MyExpression lhs) {
		this.lhs = lhs;
	}
	
	/**
	 * 
	 * @return Second expression
	 */
	public MyExpression getRhs() {
		return rhs;
	}
	
	/**
	 * 
	 * @param rhs Second expression
	 */
	public void setRhs(MyExpression rhs) {
		this.rhs = rhs;
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
		return true;
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
		kcs.addAll(lhs.getKCs(level));
		kcs.addAll(rhs.getKCs(level));
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyCommaOperator) {
			MyCommaOperator constant = (MyCommaOperator) o;
			return (lhs.equals(constant.getLhs()) && rhs.equals(constant.getRhs()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "COMMA OPERATOR\n";
		text += "LHS: " + lhs + "\n";
		text += "RHS: " + rhs + "\n";
		return text;
	}	
}
