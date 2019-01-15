package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an elaborated type specifier, i.e., struct, enum, or union
 * @author Ella Albrecht
 *
 */
public class MyElaboratedTypeSpecifier extends MyTypeSpecifier{

	public enum ElaboratedType {STRUCT, ENUM, UNION}

	/* The KCs defined for the type cast element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DATA_TYPE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ELABORATED_TYPE_SPECIFIER));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Type of the elaborated type specifier
	 */
	private ElaboratedType type;
	
	/**
	 * Name of the defined type
	 */
	private String name;
	
	/**
	 * Creates a new elaborated type specifier
	 * @param name Name of the defined type
	 * @param type Type of the elaborated type specifier
	 */
	public MyElaboratedTypeSpecifier(String name, ElaboratedType type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * 
	 * @return Type of the elaborated type specifier
	 */
	public ElaboratedType getType() {
		return type;
	}

	/**
	 * 
	 * @param type Type of the elaborated type specifier
	 */
	public void setType(ElaboratedType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return Name of the defined type
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name Name of the defined type
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyElaboratedTypeSpecifier) {
			MyElaboratedTypeSpecifier spec = (MyElaboratedTypeSpecifier) o;
			return spec.getName().equals(name) && spec.getType().equals(type);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() + type.hashCode();
	}
	
	@Override
	public String toString() {
		return type + " " + name;
	}
	
	@Override
	public boolean isVoidType() {
		return false;
	}

	@Override
	public boolean isArithmeticType() {
		return false;
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
		return false;
	}

	@Override
	public boolean isStruct() {
		return false;
	}

	@Override
	public boolean isUnion() {
		return false;
	}

	@Override
	public boolean isEnum() {
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
				switch (type) {
					case ENUM: kcs.add(KnowledgeComponent.ENUM); break;
					case UNION: kcs.add(KnowledgeComponent.UNION); break;
					case STRUCT: kcs.add(KnowledgeComponent.STRUCT); break;
					default: ;
				}
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		
		return kcs;
	}
}
