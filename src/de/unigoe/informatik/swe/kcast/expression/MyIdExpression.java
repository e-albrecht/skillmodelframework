package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyPrimaryExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an identifier in an expression
 * @author Ella Albrecht
 *
 */
public class MyIdExpression extends MyPrimaryExpression{

	/**
	 * Identifier name
	 */
	private String name;
	
	/**
	 * Determines if identifier specifies a variable
	 */
	private boolean isVariable;

	/* The KCs defined for the address of element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList());
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PRIMARY_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.IDENTIFIER));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new identifier
	 * @param name Identifier name
	 * @param isVariable Identifier specifies a variable
	 */
	public MyIdExpression(String name, boolean isVariable) {
		this.name = name;
		this.setVariable(isVariable);
	}	
	
	/**
	 * 
	 * @return Identifier name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name Identifier name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return Returns if identifier specifies a variable
	 */
	public boolean isVariable() {
		return isVariable;
	}

	/**
	 * 
	 * @param isVariable Identifier specifies a variable
	 */
	public void setVariable(boolean isVariable) {
		this.isVariable = isVariable;
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
		return true;
	}

	@Override
	public boolean isPredefinedConstant() {
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
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		
		return kcs;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof MyIdExpression) {
			MyIdExpression id = (MyIdExpression) o;
			return (name.equals(id.getName()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "DECLARATOR\n";
		text += "Name: " + name + "\n";
		return text;
	}
}
