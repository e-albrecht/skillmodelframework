package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a predefined constant, i.e., <code>null</code>, <code>true</code>, and <code>false</code>
 * @author Ella Albrecht
 *
 */
public class MyPredefinedConstant extends MyPrimaryExpression{

	public enum PredefinedConstantType {
		NULL, TRUE, FALSE
	}
	
	/**
	 * The type of the constant
	 */
	private PredefinedConstantType type;

	/* The KCs defined for the predefined constant element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PRIMARY_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PREDEFINED_CONSTANT));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new predefined constant
	 * @param type The type of the constant
	 */
	public MyPredefinedConstant(PredefinedConstantType type) {
		this.setType(type);
	}
	
	/**
	 * 
	 * @return The type of the constant
	 */
	public PredefinedConstantType getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of the constant
	 */
	public void setType(PredefinedConstantType type) {
		this.type = type;
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
		return false;
	}

	@Override
	public boolean isIdentifier() {
		return false;
	}

	@Override
	public boolean isPredefinedConstant() {
		return true;
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
					if (type.equals(PredefinedConstantType.FALSE)) kcs.add(KnowledgeComponent.FALSE);
					if (type.equals(PredefinedConstantType.TRUE)) kcs.add(KnowledgeComponent.TRUE);
					if (type.equals(PredefinedConstantType.NULL)) kcs.add(KnowledgeComponent.NULL);
				}
			}		
		}	
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyPredefinedConstant) {
			MyPredefinedConstant constant = (MyPredefinedConstant) o;
			return (type.equals(constant.getType()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "CHARACTER CONSTANT\n";
		text += "Type: " + type + "\n";
		return text;
	}

}
