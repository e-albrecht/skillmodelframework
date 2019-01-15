package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a character constant.<br /><br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]:<br />
 * (1) <code>' </code><em>c-char</em><code> '</code><br />
 * (2) <code>u ' </code><em>c-char</em><code> '</code><br /> (16-bit character)
 * (3) <code>U ' </code><em>c-char</em><code> '</code><br /> (32-bit character)
 * (4) <code>L ' </code><em>c-char</em><code> '</code><br /> (wide character)
 * (5) <code>' </code><em>c-char-sequence</em><code> '</code><br />
 * @author Ella Albrecht
 *
 */
public class MyCharacterConstant extends MyPrimaryExpression{

	public enum CharacterConstantType{
		SINGLE_BYTE, SIXTEEN_BIT, THIRTYTWO_BIT,WIDE
	}
	
	/**
	 * The character of the character constant
	 */
	private String value;
	
	/**
	 * The type of the character constant
	 */
	private CharacterConstantType type;

	/* The KCs defined for the array subscript element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PRIMARY_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.CHARACTER_CONSTANT));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new character constant
	 * @param value The character of the character constant
	 * @param type The type of the character constant
	 */
	public MyCharacterConstant(String value, CharacterConstantType type) {
		this.value = value;
		this.type = type;
	}
	
	/**
	 * 
	 * @return The character of the character constant
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value The character of the character constant
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * @return The type of the character constant
	 */
	public CharacterConstantType getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of the character constant
	 */
	public void setType(CharacterConstantType type) {
		this.type = type;
	}
	
	@Override
	public boolean isIntegerConstant() {
		return false;
	}

	@Override
	public boolean isCharacterConstant() {
		return true;
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
					if (type.equals(CharacterConstantType.SINGLE_BYTE)) kcs.add(KnowledgeComponent.SINGLE_BYTE);
					if (type.equals(CharacterConstantType.SIXTEEN_BIT)) kcs.add(KnowledgeComponent.SIXTEEN_BIT);
					if (type.equals(CharacterConstantType.THIRTYTWO_BIT)) kcs.add(KnowledgeComponent.THIRTYTWO_BIT);
					if (type.equals(CharacterConstantType.WIDE)) kcs.add(KnowledgeComponent.WIDE);
				}
			}		
		}	
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyCharacterConstant) {
			MyCharacterConstant constant = (MyCharacterConstant) o;
			return (type.equals(constant.getType()) && value.equals(constant.getValue()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "CHARACTER CONSTANT\n";
		text += "Type: " + type + "\n";
		text += "Value: " + value + "\n";
		return text;
	}
}
