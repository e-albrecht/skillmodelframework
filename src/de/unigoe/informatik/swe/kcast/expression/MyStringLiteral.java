package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a string literal
 * Syntax: <code>"</code><em>char-sequence</em><code>"</code>
 * @author Ella Albrecht
 *
 */
public class MyStringLiteral extends MyPrimaryExpression{

	/**
	 * The value of the String literal
	 */
	private String value;

	/* The KCs defined for the string literal element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PRIMARY_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STRING_LITERAL));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new string literal
	 * @param value The value of the string literal
	 */
	public MyStringLiteral(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * @return The value of the string literal
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value The value of the string literal
	 */
	public void setValue(String value) {
		this.value = value;
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
		return false;
	}

	@Override
	public boolean isStringLiteral() {
		return true;
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
				}
			}		
		}	
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyStringLiteral) {
			MyStringLiteral lit = (MyStringLiteral) o;
			return (value.equals(lit.getValue()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "STRING LITERAL\n";
		text += "Value:: " + value + "\n";
		return text;
	}

}
