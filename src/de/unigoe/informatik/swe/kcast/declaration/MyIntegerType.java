package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an integer type specifier
 * @author Ella Albrecht
 *
 */
public class MyIntegerType extends MyArithmeticType {
	
	public enum IntegerType{SHORT, SIGNED_SHORT, UNSIGNED_SHORT,INT, SIGNED_INT, UNSIGNED_INT,LONG, SIGNED_LONG, UNSIGNED_LONG,LONG_LONG, SIGNED_LONG_LONG, UNSIGNED_LONG_LONG}

	/**
	 * Type of integer type specifier
	 */
	private IntegerType type;

	/* The KCs defined for the type cast element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DATA_TYPE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ARITHMETIC_TYPE,KnowledgeComponent.INTEGER));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new integer type specifier of type <code>int</code>
	 */
	public MyIntegerType() {
		type = IntegerType.INT;
	}
	
	/**
	 * Creates a new integer type specifier
	 * @param type Type of the integer type specifier
	 */
	public MyIntegerType(IntegerType type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return Type of the integer type specifier
	 */
	public IntegerType getType() {
		return type;
	}

	/**
	 * 
	 * @param type Type of the integer type specifier
	 */
	public void setType(IntegerType type) {
		this.type = type;
	}

	@Override
	public boolean isCharacterType() {
		return false;
	}

	@Override
	public boolean isIntegerType() {
		return true;
	}

	@Override
	public boolean isFloatingType() {
		return false;
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
					if (type.equals(IntegerType.SHORT))	kcs.add(KnowledgeComponent.SHORT);
					if (type.equals(IntegerType.SIGNED_SHORT)) {
						kcs.add(KnowledgeComponent.SHORT);
						kcs.add(KnowledgeComponent.SIGNED);
					}
					if (type.equals(IntegerType.UNSIGNED_SHORT)) {
						kcs.add(KnowledgeComponent.SHORT);
						kcs.add(KnowledgeComponent.UNSIGNED);
					}
					if (type.equals(IntegerType.INT))
						kcs.add(KnowledgeComponent.INT);
					if (type.equals(IntegerType.SIGNED_INT)) {
						kcs.add(KnowledgeComponent.INT);
						kcs.add(KnowledgeComponent.SIGNED);
					}
					if (type.equals(IntegerType.UNSIGNED_INT)) {
						kcs.add(KnowledgeComponent.INT);
						kcs.add(KnowledgeComponent.UNSIGNED);
					}
					if (type.equals(IntegerType.LONG)) kcs.add(KnowledgeComponent.LONG);
					if (type.equals(IntegerType.SIGNED_LONG)) {
						kcs.add(KnowledgeComponent.LONG);
						kcs.add(KnowledgeComponent.SIGNED);
					}
					if (type.equals(IntegerType.UNSIGNED_LONG)) {
						kcs.add(KnowledgeComponent.LONG);
						kcs.add(KnowledgeComponent.UNSIGNED);
					}
					if (type.equals(IntegerType.LONG_LONG))
						kcs.add(KnowledgeComponent.LONG_LONG);
					if (type.equals(IntegerType.SIGNED_LONG_LONG)) {
						kcs.add(KnowledgeComponent.LONG_LONG);
						kcs.add(KnowledgeComponent.SIGNED);
					}
					if (type.equals(IntegerType.UNSIGNED_LONG_LONG)) {
						kcs.add(KnowledgeComponent.LONG_LONG);
						kcs.add(KnowledgeComponent.UNSIGNED);
					}
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
