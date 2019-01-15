package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a floating type specifier
 * @author Ella Albrecht
 *
 */
public class MyFloatingType extends MyArithmeticType {

	public enum FloatingType{FLOAT, DOUBLE, LONG_DOUBLE}
	
	/**
	 * Type of floating type specifier
	 */
	private FloatingType type;

	/* The KCs defined for the type cast element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.DATA_TYPE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.ARITHMETIC_TYPE,
					KnowledgeComponent.FLOAT));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new floating type specifier of type <code>float</code>
	 */
	public MyFloatingType() {
		type = FloatingType.FLOAT;
	}
	
	/**
	 * Creates a new floating type specifier
	 * @param type Type of the floating type specifier
	 */
	public MyFloatingType(FloatingType type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return Type of the floating type specifier
	 */
	public FloatingType getType() {
		return type;
	}

	/**
	 * 
	 * @param type Type of the floating type specifier
	 */
	public void setType(FloatingType type) {
		this.type = type;
	}

	@Override
	public boolean isCharacterType() {
		return false;
	}

	@Override
	public boolean isIntegerType() {
		return false;
	}

	@Override
	public boolean isFloatingType() {
		return true;
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
					if (type.equals(FloatingType.DOUBLE))
						kcs.add(KnowledgeComponent.DOUBLE);
					if (type.equals(FloatingType.LONG_DOUBLE))
						kcs.add(KnowledgeComponent.LONG_DOUBLE);
				}
			}
		}

		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyFloatingType) {
			MyFloatingType fType = (MyFloatingType) o;
			return (type.equals(fType.getType()));
		}
		return false;
	}
}
