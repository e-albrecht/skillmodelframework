package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a sizeof operator that is applied to an expression<br /><br />
 * Syntax: <code>sizeof</code> <em>expression</em> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MySizeOfExpression extends MySizeOf{

	/**
	 * Expression describing the object of which the size is determined
	 */
	private MyExpression expression;

	/* The KCs defined for the size of expression element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SIZE_OF));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SIZE_OF_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new size of expression operator
	 * @param expression Expression describing the object of which the size is determined
	 */
	public MySizeOfExpression(MyExpression expression) {
		this.expression = expression;
	}
	
	/**
	 * 
	 * @return Expression describing the object of which the size is determined
	 */
	public MyExpression getExpression() {
		return expression;
	}

	/**
	 * 
	 * @param expression Expression describing the object of which the size is determined
	 */
	public void setExpression(MyExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public boolean isSizeOfType() {
		return false;
	}

	@Override
	public boolean isSizeOfExpression() {
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
				}
			}		
		}	
		kcs.addAll(expression.getKCs(level));
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MySizeOfExpression) {
			MySizeOfExpression expr = (MySizeOfExpression) o;
			return (expression.equals(expr.getExpression()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "SIZE OF EXPRESSION\n";
		text += "Expression: " + expression.toString() + "\n";
		return text;
	}


}
