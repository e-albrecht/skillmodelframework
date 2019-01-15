package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a comparison. Comparison operators are binary operators that test a condition and return 1 if that condition is logically true and 0 if that condition is false. We distinguish between six comparison operators [<a href="cppreference.com">cppreference.com</a>]:<br /><br />
 * (1) <em>lhs</em><code> < </code><em>rhs</em> (less)<br />
 * (2) <em>lhs</em><code> > </code><em>rhs</em> (greater)<br />
 * (3) <em>lhs</em><code> <= </code><em>rhs</em> (less or equal)<br />
 * (4) <em>lhs</em><code> >= </code><em>rhs</em> (greater or equal)<br />
 * (5) <em>lhs</em><code> == </code><em>rhs</em> (equal)<br />
 * (6) <em>lhs</em><code> != </code><em>rhs</em> (not equal)
 * @author Ella Albrecht
 *
 */
public class MyComparison extends MyExpression {

	public enum ComparisonType {
		EQUAL_TO, NOT_EQUAL_TO, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL_TO, GREATER_THAN_OR_EQUAL_TO
	}
	
	/**
	 * Type of the comparison
	 */
	private ComparisonType type;
	
	/**
	 * First operand
	 */
	private MyExpression operand1;
	
	/**
	 * Second operand
	 */
	private MyExpression operand2;

	/* The KCs defined for the comma operator element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.COMPARISON));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new comparison
	 * @param type Type of the comparison
	 * @param operand1 First operand
	 * @param operand2 Second operand
	 */
	public MyComparison(ComparisonType type, MyExpression operand1, MyExpression operand2) {
		this.type = type;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	/**
	 * 
	 * @return Type of the comparison
	 */
	public ComparisonType getType() {
		return type;
	}

	/**
	 * 
	 * @param type Type of the comparison
	 */
	public void setType(ComparisonType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return First operand
	 */
	public MyExpression getOperand1() {
		return operand1;
	}

	/**
	 * 
	 * @param operand1 First operand
	 */
	public void setOperand1(MyExpression operand1) {
		this.operand1 = operand1;
	}

	/**
	 * 
	 * @return Second operand
	 */
	public MyExpression getOperand2() {
		return operand2;
	}

	/**
	 * 
	 * @param operand2 Second operand
	 */
	public void setOperand2(MyExpression operand2) {
		this.operand2 = operand2;
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
		return true;
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
				if (type.equals(ComparisonType.EQUAL_TO)) kcs.add(KnowledgeComponent.EQUALS);
				if (type.equals(ComparisonType.NOT_EQUAL_TO)) kcs.add(KnowledgeComponent.NOT_EQUALS);
				if (type.equals(ComparisonType.LESS_THAN)) kcs.add(KnowledgeComponent.LESS);
				if (type.equals(ComparisonType.LESS_THAN_OR_EQUAL_TO)) kcs.add(KnowledgeComponent.LESS_EQUALS);
				if (type.equals(ComparisonType.GREATER_THAN)) kcs.add(KnowledgeComponent.GREATER);
				if (type.equals(ComparisonType.GREATER_THAN_OR_EQUAL_TO)) kcs.add(KnowledgeComponent.GREATER_EQUALS);
			
				if (level > 2) {
					kcs.addAll(kcs3);
				}
			}		
		}	
		if (operand1 != null) kcs.addAll(operand1.getKCs(level));
		if (operand2 != null) kcs.addAll(operand2.getKCs(level));
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyComparison) {
			MyComparison comp = (MyComparison) o;
			return (type.equals(comp.getType()) && operand1.equals(comp.getOperand1()) && operand2.equals(comp.getOperand2()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "COMPARISON\n";
		text += "Type: " + type + "\n";
		text += "Operand 1: " + operand1 + "\n";
		text += "Operand 2: " + operand2 + "\n";

		return text;
	}	
}
