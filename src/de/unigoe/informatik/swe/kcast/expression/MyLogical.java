package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a logical operator. Logical operators apply standard boolean algebra operations to their operands. [<a href="cppreference.com">cppreference.com</a>] We distinguish between three types of logical expression:<br /><br />
 * Syntax:<br />
 * (1) <code>!</code> <em>expression</em> (NOT)<br />
 * (2)<em>expression1</em><code> && </code><em>expression2</em> (AND)<br />
 * (3)<em>expression1</em><code> || </code><em>expression2</em> (OR)<br />
 * @author Ella Albrecht
 *
 */
public class MyLogical extends MyExpression {

	public enum LogicalType {
		LOGICAL_NOT, LOGICAL_AND, LOGICAL_OR
	}
	
	/**
	 * The type of the logical expression
	 */
	private LogicalType type;
	
	/**
	 * First operand
	 */
	private MyExpression operand1;
	
	/**
	 * Second operand
	 */
	private MyExpression operand2;

	/* The KCs defined for the logical operator element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.LOGICAL_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new logical operator
	 * @param type The type of the logical expression
	 * @param operand1 First operand
	 * @param operand2 Second operand
	 */
	public MyLogical(LogicalType type, MyExpression operand1, MyExpression operand2) {
		this.type = type;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	/**
	 * 
	 * @return Type of the logical expression
	 */
	public LogicalType getType() {
		return type;
	}

	/**
	 * 
	 * @param type Type of the logical expression
	 */
	public void setType(LogicalType type) {
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
		return true;
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
				if (type.equals(LogicalType.LOGICAL_AND)) kcs.add(KnowledgeComponent.AND);
				if (type.equals(LogicalType.LOGICAL_OR)) kcs.add(KnowledgeComponent.OR);
				if (type.equals(LogicalType.LOGICAL_NOT)) kcs.add(KnowledgeComponent.NOT);
			
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
		if (o instanceof MyLogical) {
			MyLogical log = (MyLogical) o;
			return (type.equals(log.getType()) && operand1.equals(log.getOperand1()) && operand2.equals(log.getOperand2()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "LOGICAL EXPRESSION\n";
		text += "Type: " + type + "\n";
		text += "Operand 1: " + operand1 + "\n";
		text += "Operand 2: " + operand2 + "\n";

		return text;
	}	
}
