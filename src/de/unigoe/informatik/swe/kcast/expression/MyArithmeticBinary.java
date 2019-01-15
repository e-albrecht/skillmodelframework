package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a binary arithmetic expression. We distinguish between ten binary arithmetic expressions.<br /><br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]:<br />
 * (1) <em>expression</em> <code>+</code> <em>expression</em> (addition)<br />
 * (2) <em>expression</em> <code>-</code> <em>expression</em> (subtraction)<br />
 * (3) <em>expression</em> <code>*</code> <em>expression</em> (multiplication)<br />
 * (4) <em>expression</em> <code>/</code> <em>expression</em> (division)<br />
 * (5) <em>expression</em> <code>%</code> <em>expression</em> (remainder)<br />
 * (6) <em>expression</em> <code>&</code> <em>expression</em> (bitwise AND)<br />
 * (7) <em>expression</em> <code>|</code> <em>expression</em> (bitwise OR)<br />
 * (8) <em>expression</em> <code>^</code> <em>expression</em> (bitwise XOR)<br />
 * (9) <em>expression</em> <code><<</code> <em>expression</em> (left shift)<br />
 * (10) <em>expression</em> <code>>></code> <em>expression</em> (right shift)<br />
 * @author Ella Albrecht
 *
 */
public class MyArithmeticBinary extends MyArithmetic {

	public enum ArithmeticBinaryType {
		ADDITION, SUBTRACTION, PRODUCT, DIVISION, MODULO, BITWISE_AND, BITWISE_OR, BITWISE_XOR, 
		BITWISE_LEFT_SHIFT, BITWISE_RIGHT_SHIFT 
	}

	/* The KCs defined for the arithmetic expression element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ARITHMETIC_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.BINARY_ARITHMETIC_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * The type of the binary arithmetic expression
	 */
	private ArithmeticBinaryType type;
	
	/**
	 * The first operand
	 */
	private MyExpression operand1;
	
	/**
	 * The second operand
	 */
	private MyExpression operand2;
	
	/**
	 * Creates a new binary arithmetic expression
	 * @param type The type of the binary arithmetic expression
	 * @param operand1 The first operand
	 * @param operand2 The second operand
	 */
	public MyArithmeticBinary(ArithmeticBinaryType type, MyExpression operand1, MyExpression operand2){
		this.type = type;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	/**
	 * 
	 * @return The second operand
	 */
	public MyExpression getOperand2() {
		return operand2;
	}

	/**
	 * 
	 * @param operand2 The second operand
	 */
	public void setOperand2(MyExpression operand2) {
		this.operand2 = operand2;
	}

	/**
	 * 
	 * @return The first operand
	 */
	public MyExpression getOperand1() {
		return operand1;
	}

	/**
	 * 
	 * @param operand1 The first operand
	 */
	public void setOperand1(MyExpression operand1) {
		this.operand1 = operand1;
	}

	/**
	 * 
	 * @return The type of the binary arithmetic expression
	 */
	public ArithmeticBinaryType getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of the binary arithmetic expression
	 */
	public void setType(ArithmeticBinaryType type) {
		this.type = type;
	}
	
	@Override
	public boolean isUnary() {
		return false;
	}

	@Override
	public boolean isBinary() {
		return true;
	}

	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (type.equals(ArithmeticBinaryType.BITWISE_AND)) kcs.add(KnowledgeComponent.BITWISE_OPERATION);
			if (type.equals(ArithmeticBinaryType.BITWISE_OR)) kcs.add(KnowledgeComponent.BITWISE_OPERATION);
			if (type.equals(ArithmeticBinaryType.BITWISE_XOR)) kcs.add(KnowledgeComponent.BITWISE_OPERATION);
			if (type.equals(ArithmeticBinaryType.BITWISE_LEFT_SHIFT)) kcs.add(KnowledgeComponent.BITWISE_OPERATION);
			if (type.equals(ArithmeticBinaryType.BITWISE_RIGHT_SHIFT)) kcs.add(KnowledgeComponent.BITWISE_OPERATION);
			if (level > 1) {
				kcs.addAll(kcs2);
				if (type.equals(ArithmeticBinaryType.ADDITION)) kcs.add(KnowledgeComponent.ADDITION);
				if (type.equals(ArithmeticBinaryType.SUBTRACTION)) kcs.add(KnowledgeComponent.SUBTRACTION);
				if (type.equals(ArithmeticBinaryType.PRODUCT)) kcs.add(KnowledgeComponent.PRODUCT);
				if (type.equals(ArithmeticBinaryType.DIVISION)) kcs.add(KnowledgeComponent.DIVISION);
				if (type.equals(ArithmeticBinaryType.MODULO)) kcs.add(KnowledgeComponent.MODULO);
				if (type.equals(ArithmeticBinaryType.BITWISE_AND)) kcs.add(KnowledgeComponent.AND);
				if (type.equals(ArithmeticBinaryType.BITWISE_OR)) kcs.add(KnowledgeComponent.OR);
				if (type.equals(ArithmeticBinaryType.BITWISE_LEFT_SHIFT)) {
					kcs.add(KnowledgeComponent.SHIFT);
				}
				if (type.equals(ArithmeticBinaryType.BITWISE_RIGHT_SHIFT)) {
					kcs.add(KnowledgeComponent.SHIFT);
				}
				if (level > 2) {
					kcs.addAll(kcs3);
					if (type.equals(ArithmeticBinaryType.BITWISE_AND)) kcs.add(KnowledgeComponent.BITWISE_AND);
					if (type.equals(ArithmeticBinaryType.BITWISE_OR)) kcs.add(KnowledgeComponent.BITWISE_OR);
					if (type.equals(ArithmeticBinaryType.BITWISE_XOR)) kcs.add(KnowledgeComponent.BITWISE_XOR);
					if (type.equals(ArithmeticBinaryType.BITWISE_LEFT_SHIFT)) kcs.add(KnowledgeComponent.BITWISE_LEFT_SHIFT);
					if (type.equals(ArithmeticBinaryType.BITWISE_RIGHT_SHIFT)) kcs.add(KnowledgeComponent.BITWISE_RIGHT_SHIFT);
				}
			}		
		}
		// add KCs for subelements
		kcs.addAll(operand1.getKCs(level));
		kcs.addAll(operand2.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyArithmeticBinary) {
			MyArithmeticBinary ari = (MyArithmeticBinary) o;
			return (type.equals(ari.getType()) && operand1.equals(ari.getOperand1()) && operand2.equals(ari.getOperand2()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "ARITHMETIC EXPRESSION\n";
		text += "Type: " + type + "\n";
		text += "Operand 1: " + operand1 + "\n";
		text += "Operand 2: " + operand2 + "\n";
		return text;
	}
}
