package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.declaration.MyTypeSpecifier;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a sizeof operator that is applied to a type<br /><br />
 * Syntax: <code>sizeof(</code> <em>type</em> <code>)</code>[<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MySizeOfType extends MySizeOf{

	/**
	 * The type of which the size is determined
	 */
	private MyTypeSpecifier type;

	/* The KCs defined for the size of type element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SIZE_OF));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SIZE_OF_TYPE));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * 
	 * @param type The type of which the size is determined
	 */
	public MySizeOfType(MyTypeSpecifier type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return The type of which the size is determined
	 */
	public MyTypeSpecifier getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of which the size is determined
	 */
	public void setType(MyTypeSpecifier type) {
		this.type = type;
	}
	
	@Override
	public boolean isSizeOfType() {
		return true;
	}

	@Override
	public boolean isSizeOfExpression() {
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
		kcs.addAll(type.getKCs(level));
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MySizeOfType) {
			MySizeOfType expr = (MySizeOfType) o;
			return (type.equals(expr.getType()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "SIZE OF TYPE\n";
		text += "Type: " + type.toString() + "\n";
		return text;
	}

	
	
}
