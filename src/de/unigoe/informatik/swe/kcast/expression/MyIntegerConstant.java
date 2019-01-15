package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an integer constant.<br /><br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]:<br />
 * (1) <em>decimal-constant integer-suffix</em> (decimal digit (1, 2, 3, 4, 5, 6, 7, 8, 9), followed by zero or more decimal digits (0, 1, 2, 3, 4, 5, 6, 7, 8, 9))<br />
 * (2) <em>octal-constant integer-suffix</em> (digit zero (0) followed by zero or more octal digits (0, 1, 2, 3, 4, 5, 6, 7))<br />
 * (3) <em>hexadecimal-constant integer-suffix</em> (haracter sequence 0x or the character sequence 0X followed by one or more hexadecimal digits (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, A, b, B, c, C, d, D, e, E, f, F))<br />
 * <em>integer-suffix:</em> <br />
 * <code>u | U</code> (unsigned suffix)<br />
 * <code>l | L</code> (long suffix)<br />
 * <code>ll | LL</code> (long long suffix)
 * @author Ella Albrecht
 *
 */
public class MyIntegerConstant extends MyPrimaryExpression{

	public enum IntegerConstantType{
		DECIMAL, OCTAL, HEX
	}
	
	/**
	 * The type of the integer constant, i.e., decimal, octal, or hexadecimal
	 */
	private IntegerConstantType type;
	
	/**
	 * The value of the constant
	 */
	private String value;
	
	/**
	 * Defines whether constant is unsigned
	 */
	private boolean u;
	
	/**
	 * Defines whether constant is long
	 */
	private boolean l;
	
	/**
	 * Defines whether constant is long long
	 */
	private boolean ll;

	/* The KCs defined for the integer constant element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PRIMARY_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.INTEGER_CONSTANT));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new integer constant
	 * @param type The type of the integer constant, i.e., decimal, octal, or hexadecimal
	 * @param value The value of the constant
	 * @param u Defines whether constant is unsigned
	 * @param l Defines whether constant is long
	 * @param ll Defines whether constant is long long
	 */
	public MyIntegerConstant(IntegerConstantType type, String value, boolean u, boolean l, boolean ll) {
		this.type = type;
		this.value = value;
		this.u = u;
		this.l = l;
		this.setLl(ll);
	}

	/**
	 * 
	 * @return The type of the integer constant, i.e., decimal, octal, or hexadecimal
	 */
	public IntegerConstantType getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of the integer constant, i.e., decimal, octal, or hexadecimal
	 */
	public void setType(IntegerConstantType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The value of the constant
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value The value of the constant
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 * @return Returns whether the constant is unsigned
	 */
	public boolean isUnsigned() {
		return u;
	}

	/**
	 * 
	 * @param u Set constant as unsigned
	 */
	public void setU(boolean u) {
		this.u = u;
	}

	/**
	 * 
	 * @return Returns whether constant is long
	 */
	public boolean isLong() {
		return l;
	}

	/**
	 * 
	 * @param l Set constant as long
	 */
	public void setL(boolean l) {
		this.l = l;
	}
	
	/**
	 * 
	 * @return Returns whether constant is long long
	 */
	public boolean isLongLong() {
		return ll;
	}

	/**
	 * 
	 * @param ll Sets constant as long long
	 */
	public void setLl(boolean ll) {
		this.ll = ll;
	}
	
	@Override
	public boolean isIntegerConstant() {
		return true;
	}

	@Override
	public boolean isCharacterConstant() {
		return false;
	}

	@Override
	public boolean isFloatingConstant() {
		return false;
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
					if (type.equals(IntegerConstantType.HEX)) kcs.add(KnowledgeComponent.HEX);
					if (type.equals(IntegerConstantType.OCTAL)) kcs.add(KnowledgeComponent.OCT);
					if (u) kcs.add(KnowledgeComponent.UNSIGNED);
					if (l) kcs.add(KnowledgeComponent.LONG);
					if (ll) kcs.add(KnowledgeComponent.LONG_LONG);
				}
			}		
		}	
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyIntegerConstant) {
			MyIntegerConstant constant = (MyIntegerConstant) o;
			return (type.equals(constant.getType()) && value.equals(constant.getValue()) && u == constant.isUnsigned() && l == constant.isLong() && ll == constant.isLongLong());
		}
		return false;
	}

	public String toString() {
		String text = "INTEGER CONSTANT\n";
		text += "Type: " + type + "\n";
		text += "Value: " + value + "\n";
		text += "unsigned: " + u + "\n";
		text += "long: " + l + "\n";
		text += "long long: " + ll + "\n";
		return text;
	}
	
	
}
