package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Representing a floating constant.<br /><br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]: <br />
 * <em>significand exponent(optional) suffix (optional)</em><br />
 * <em>significand</em> has the form:<br /> 
 * <em>whole number (optional)</em><code> . </code>(optional) <em>fraction (optional)</em><br />
 * <em>exponent</em> has the form:<br />
 * (1) <code>e | E </code><em>exponent-sign (optional) digit-sequence</em> (decimal)<br /> 
 * (2) <code>p | P </code><em>exponent-sign (optional) digit-sequence</em> (hexadecimal)
 * @author Ella Albrecht
 *
 */
public class MyFloatingConstant extends MyPrimaryExpression{

	public enum FloatingConstantType {
		DEC, HEX
	}
	
	public enum FloatingType {
		DOUBLE, FLOAT, LONG_DOUBLE
	}

	/**
	 * The type of the floating constant, i.e., decimal or hexadecimal
	 */
	private FloatingConstantType type;
	
	/**
	 * The floating type, i.e., double, float, or long double
	 */
	private FloatingType sizeType;
	
	/**
	 * The significand
	 */
	private double significand;
	
	/**
	 * The exponent
	 */
	private int exponent;
	
	/* The KCs defined for the floating constant element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PRIMARY_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.FLOATING_CONSTANT));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
		
	/**
	 * Creates a new floating constant
	 * @param type The type of the floating constant, i.e., decimal or hexadecimal
	 * @param sizeType The floating type, i.e., double, float, or long double
	 * @param significand The significand
	 * @param exponent The exponent
	 */
	public MyFloatingConstant(FloatingConstantType type, FloatingType sizeType, double significand, int exponent) {
		this.type = type;
		this.sizeType = sizeType;
		this.significand = significand;
		this.exponent = exponent;
	}
	
	/**
	 * Creates a new floating constant without an explicit exponent, i.e. exponent is 1
	 * @param type The type of the floating constant, i.e., decimal or hexadecimal
	 * @param sizeType The floating type, i.e., double, float, or long double
	 * @param significand The significand
	 */
	public MyFloatingConstant(FloatingConstantType type, FloatingType sizeType, double significand) {
		this.type = type; 
		this.sizeType = sizeType;
		this.significand = significand;
		this.exponent = 1;
	}
	
	/**
	 * Creates a decimal floating constant with exponent 1
	 * @param significand The significand
	 * @param exponent The exponent
	 */
	public MyFloatingConstant(double significand, int exponent) {
		this.type = FloatingConstantType.DEC;
		this.sizeType = FloatingType.DOUBLE;
		this.significand = significand;
		this.exponent = exponent;
	}
	
	/**
	 * 
	 * @return The type of the floating constant, i.e., decimal or hexadecimal
	 */
	public FloatingConstantType getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of the floating constant, i.e., decimal or hexadecimal
	 */
	public void setType(FloatingConstantType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The significand
	 */
	public double getSignificand() {
		return significand;
	}

	/**
	 * 
	 * @param significand The significand
	 */
	public void setSignificand(double significand) {
		this.significand = significand;
	}

	/**
	 * 
	 * @return The exponent
	 */
	public int getExponent() {
		return exponent;
	}

	/**
	 * 
	 * @param exponent The exponent
	 */
	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	/**
	 * 
	 * @return The floating type, i.e., double, float, or long double
	 */
	public FloatingType getSizeType() {
		return sizeType;
	}

	/**
	 * 
	 * @param sizeType The floating type, i.e., double, float, or long double
	 */
	public void setSizeType(FloatingType sizeType) {
		this.sizeType = sizeType;
	}
	
	@Override
	public boolean isIntegerConstant() {
		return false;
	}

	@Override
	public boolean isCharacterConstant() {
		return false;
	}

	@Override
	public boolean isFloatingConstant() {
		return true;
	}

	@Override
	public boolean isStringLiteral() {
		return false;
	}

	@Override
	public boolean isIdentifier() {
		return false;
	}

	@Override
	public boolean isPredefinedConstant() {
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
					if (type.equals(FloatingConstantType.HEX)) kcs.add(KnowledgeComponent.HEX);
					if (sizeType.equals(FloatingType.DOUBLE)) kcs.add(KnowledgeComponent.DOUBLE);
					if (sizeType.equals(FloatingType.LONG_DOUBLE)) kcs.add(KnowledgeComponent.LONG_DOUBLE);
				}
			}		
		}	
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyFloatingConstant) {
			MyFloatingConstant constant = (MyFloatingConstant) o;
			return (type.equals(constant.getType()) && sizeType.equals(constant.getSizeType()) && significand == constant.getSignificand() && exponent == constant.getExponent());
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "FLOATING CONSTANT\n";
		text += "Type: " + type + "\n";
		text += "Size Type: " + sizeType + "\n";
		text += "Significand: " + significand + "\n";
		text += "Exponent: " + exponent + "\n";
		return text;
	}

}
