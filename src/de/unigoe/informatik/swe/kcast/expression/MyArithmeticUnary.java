package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a unary arithmetic expression. We distinguish between three unary arithmetic expressions.<br /><br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]:<br />
 * (1) <code>+</code> <em>expression</em> (unary plus)<br />
 * (2) <code>-</code> <em>expression</em> (unary minus)<br />
 * (3) <code>~</code> <em>expression</em> (bitwise not)<br />
 * @author Ella Albrecht
 *
 */
public class MyArithmeticUnary extends MyArithmetic{

	public enum ArithmeticUnaryType {
		UNARY_PLUS, UNARY_MINUS, BITWISE_NOT
	}
	
	/**
	 * The type of the unary arithmetic expression
	 */
	private ArithmeticUnaryType type;
	
	/**
	 * The operand of the arithmetic expression
	 */
	private MyExpression operand;

	/* The KCs defined for the arithmetic expression element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ARITHMETIC_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.UNARY_ARITHMETIC_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new unary arithmetic expression
	 * @param type The type of the unary arithmetic expression
	 * @param operand The operand of the arithmetic expression
	 */
	public MyArithmeticUnary(ArithmeticUnaryType type, MyExpression operand){
		this.type = type;
		this.operand = operand;
	}
	
	/**
	 * 
	 * @return The type of the unary arithmetic expression
	 */
	public ArithmeticUnaryType getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of the unary arithmetic expression
	 */
	public void setType(ArithmeticUnaryType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The operand of the arithmetic expression
	 */ 
	public MyExpression getOperand() {
		return operand;
	}

	/**
	 * 
	 * @param operand The operand of the arithmetic expression
	 */
	public void setOperand(MyExpression operand) {
		this.operand = operand;
	}
	
	@Override
	public boolean isUnary() {
		return true;
	}

	@Override
	public boolean isBinary() {
		return false;
	}

	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (type.equals(ArithmeticUnaryType.BITWISE_NOT)) kcs.add(KnowledgeComponent.BITWISE_OPERATION);

			if (level > 1) {
				kcs.addAll(kcs2);
				if (type.equals(ArithmeticUnaryType.UNARY_PLUS)) kcs.add(KnowledgeComponent.PLUS);
				if (type.equals(ArithmeticUnaryType.UNARY_MINUS)) kcs.add(KnowledgeComponent.MINUS);
				if (type.equals(ArithmeticUnaryType.BITWISE_NOT)) kcs.add(KnowledgeComponent.NOT);

				if (level > 2) {
					kcs.addAll(kcs3);
					if (type.equals(ArithmeticUnaryType.BITWISE_NOT)) kcs.add(KnowledgeComponent.BITWISE_NOT);
				}
			}
		}		
		
		// add KCs for subelements
		kcs.addAll(operand.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyArithmeticUnary) {
			MyArithmeticUnary ari = (MyArithmeticUnary) o;
			return (type.equals(ari.getType()) && operand.equals(ari.getOperand()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "ARITHMETIC EXPRESSION\n";
		text += "Type: " + type + "\n";
		text += "Operand: " + operand + "\n";
		return text;
	}

}
