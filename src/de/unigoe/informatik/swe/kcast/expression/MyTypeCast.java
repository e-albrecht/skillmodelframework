package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.declaration.MyTypeSpecifier;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a type cast. A type cast performs explicit type conversion [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax: <code>(</code><em>type-name</em><code>)</code> <em>expression</em>
 * @author Ella Albrecht
 *
 */
public class MyTypeCast extends MyExpression {
	
	/**
	 * Type to which the expression is converted
	 */
	private MyTypeSpecifier type;
	
	/**
	 * Expression of which the type is converted
	 */
	private MyExpression expression;
	
	/* The KCs defined for the type cast element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.TYPE_CAST));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new type cast
	 * @param type Type to which the expression is converted
	 * @param expression Expression of which the type is converted
	 */
	public MyTypeCast(MyTypeSpecifier type, MyExpression expression) {
		this.type = type;
		this.expression = expression;
	}
	
	/**
	 * 
	 * @return Type to which the expression is converted
	 */
	public MyTypeSpecifier getType() {
		return type;
	}
	
	/**
	 * 
	 * @param type Type to which the expression is converted
	 */
	public void setType(MyTypeSpecifier type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return Expression of which the type is converted
	 */
	public MyExpression getExpression() {
		return expression;
	}
	
	/**
	 * 
	 * @param expression Expression of which the type is converted
	 */
	public void setExpression(MyExpression expression) {
		this.expression = expression;
	}
	@Override
	public boolean isPrimary() {
		return false;
	}
	@Override
	public boolean isAssignment() {
		return false;
	}
	@Override
	public boolean isIncDecrement() {
		return false;
	}
	@Override
	public boolean isArithmetic() {
		return false;
	}
	@Override
	public boolean isLogical() {
		return false;
	}
	@Override
	public boolean isComparison() {
		return false;
	}
	@Override
	public boolean isMemberAccess() {
		return false;
	}
	@Override
	public boolean isFunctionCall() {
		return false;
	}
	@Override
	public boolean isCommaOperator() {
		return false;
	}
	@Override
	public boolean isTypeCast() {
		return true;
	}
	@Override
	public boolean isConditionalOperator() {
		return false;
	}
	@Override
	public boolean isSizeOf() {
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
		
		if (type != null)kcs.addAll(type.getKCs(level));
		kcs.addAll(expression.getKCs(level));
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyTypeCast) {
			MyTypeCast cast = (MyTypeCast) o;
			return (type.equals(cast.getType()) && expression.equals(cast.getExpression()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "TYPE CAST\n";
		text += "Type: " + type + "\n";
		text += "Variable: " + expression + "\n";
		return text;
	}
	
	
	
}
