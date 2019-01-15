package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an increment or decrement. Increment/decrement operators are unary operators that increment/decrement the value of a variable by 1. [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax: <br />
 *(1) <em>expression</em><code>++</code> (postfix increment)<br />
 *(1) <em>expression</em><code>--</code> (postfix decrement)<br />
 *(1) <code>++</code><em>expression</em> (prefix increment)<br />
 *(1) <code>--</code><em>expression</em> (prefix decrement)
 * @author Ella Albrecht
 *
 */
public class MyIncDecrement extends MyExpression{
	
	public enum IncDecrementType{
		POSTFIX_INCREMENT, PREFIX_INCREMENT, POSTFIX_DECREMENT, PREFIX_DECREMENT
	}
	
	/**
	 * Expression that has to be incremented/decremented
	 */
	private MyExpression operand;
	
	/**
	 * Type of the increment/decrement
	 */
	private IncDecrementType type;

	/* The KCs defined for the increment/decrement element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.INCREMENT_DECREMENT));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new increment/decrement
	 * @param type Type of the increment/decrement
	 * @param operand Expression that has to be incremented/decremented
	 */
	public MyIncDecrement(IncDecrementType type, MyExpression operand){
		this.type = type;
		this.operand = operand;
	}
	
	/**
	 * 
	 * @return Expression that has to be incremented/decremented
	 */
	public MyExpression getOperand() {
		return operand;
	}

	/**
	 * 
	 * @param operand Expression that has to be incremented/decremented
	 */
	public void setOperand(MyExpression operand) {
		this.operand = operand;
	}

	/**
	 * 
	 * @return Type of the increment/decrement
	 */
	public IncDecrementType getType() {
		return type;
	}

	/**
	 * 
	 * @param type Type of the increment/decrement
	 */
	public void setType(IncDecrementType type) {
		this.type = type;
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
		return true;
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
				if (type.equals(IncDecrementType.POSTFIX_INCREMENT) || type.equals(IncDecrementType.PREFIX_INCREMENT)) kcs.add(KnowledgeComponent.INCREMENT);
				if (type.equals(IncDecrementType.POSTFIX_DECREMENT) || type.equals(IncDecrementType.PREFIX_DECREMENT)) kcs.add(KnowledgeComponent.DECREMENT);
				if (type.equals(IncDecrementType.PREFIX_INCREMENT) || type.equals(IncDecrementType.PREFIX_DECREMENT)) kcs.add(KnowledgeComponent.PREFIX);
				if (type.equals(IncDecrementType.POSTFIX_INCREMENT) || type.equals(IncDecrementType.POSTFIX_DECREMENT)) kcs.add(KnowledgeComponent.POSTFIX);

				if (level > 2) {
					kcs.addAll(kcs3);
					if (type.equals(IncDecrementType.POSTFIX_INCREMENT)) kcs.add(KnowledgeComponent.POST_INCREMENT);
					if (type.equals(IncDecrementType.PREFIX_INCREMENT)) kcs.add(KnowledgeComponent.PRE_INCREMENT);
					if (type.equals(IncDecrementType.POSTFIX_DECREMENT)) kcs.add(KnowledgeComponent.POST_DECREMENT);
					if (type.equals(IncDecrementType.PREFIX_DECREMENT)) kcs.add(KnowledgeComponent.PRE_DECREMENT);

				}
			}		
		}	
		kcs.addAll(operand.getKCs(level));
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyIncDecrement) {
			MyIncDecrement incdec = (MyIncDecrement) o;
			return (type.equals(incdec.getType()) && operand.equals(incdec.getOperand()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "INCREMENT/DECREMENT\n";
		text += "Type: " + type + "\n";
		text += "Operand: " + operand +"\n";
		return text;
	}	

}
