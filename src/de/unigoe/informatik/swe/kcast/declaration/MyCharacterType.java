package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a character type specifier
 * @author Ella Albrecht
 *
 */
public class MyCharacterType extends MyArithmeticType {

	public enum CharacterType { CHAR, SIGNED_CHAR, UNSIGNED_CHAR}
	
	/**
	 * Type of character
	 */
	private CharacterType type;

	/* The KCs defined for the type cast element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DATA_TYPE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.CHARACTER, KnowledgeComponent.ARITHMETIC_TYPE));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new character type specifier of type <code>char</code>
	 */
	public MyCharacterType() {
		type = CharacterType.CHAR;
	}
	
	/**
	 * Creates a new character type specifier
	 * @param type Type of the character type specifier
	 */
	public MyCharacterType(CharacterType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return Type of the character type specifier
	 */
	public CharacterType getType() {
		return type;
	}

	/**
	 * 
	 * @param type Type of the character type specifier
	 */
	public void setType(CharacterType type) {
		this.type = type;
	}

	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		// add KCs for array
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (level > 1) {
				kcs.addAll(kcs2);
				if (level > 2) {
					kcs.addAll(kcs3);
					if (type.equals(CharacterType.SIGNED_CHAR)) kcs.add(KnowledgeComponent.SIGNED);
					if (type.equals(CharacterType.UNSIGNED_CHAR)) kcs.add(KnowledgeComponent.UNSIGNED);
				}
			}	
		}
		return kcs;
	}

	@Override
	public boolean isCharacterType() {
		return true;
	}

	@Override
	public boolean isIntegerType() {
		return false;
	}

	@Override
	public boolean isFloatingType() {
		return false;
	}
	
	@Override 
	public boolean equals(Object o) {
		if (o instanceof MyCharacterType) {
			MyCharacterType cType = (MyCharacterType) o;
			return (type.equals(cType.getType()));
		}
		return false;
	}
}

