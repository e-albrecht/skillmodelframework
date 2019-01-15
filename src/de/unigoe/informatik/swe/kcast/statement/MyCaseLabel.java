package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a case label in a switch statement<br /><br />
 * Syntax: <code>case</code> <em>constant_expression</em> <code>:</code> <em>statement</em> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyCaseLabel extends MyLabel {

	/**
	 * Expression to which the switch expression is matched and which defines the case
	 */
	private MyExpression caseExpression;
	
	/**
	 * The statement that is executed when the switch expression matches the case expression 
	 */
	private MyStatement statement;
	
	/* The KCs defined for the case label element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.LABEL));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SWITCH));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.CASE));

	/**
	 * Creates a new case label
	 * @param caseExpression An expression that describes the case and against which the switch expression is matched
	 * @param statement Statement that is executed when the switch expression matches the case expression
	 */
	public MyCaseLabel(MyExpression caseExpression, MyStatement statement) {
		this.caseExpression = caseExpression;
		this.statement = statement;
	}

	/**
	 * 
	 * @return Expression describing the case
	 */
	public MyExpression getCaseExpression() {
		return caseExpression;
	}

	/**
	 * 
	 * @param caseExpression Expression describing the case
	 */
	public void setCaseExpression(MyExpression caseExpression) {
		this.caseExpression = caseExpression;
	}

	/**
	 * 
	 * @return Statement that is executed when the case matches
	 */
	public MyStatement getStatement() {
		return statement;
	}

	/**
	 * 
	 * @param statement Statement that is executed when the case matches
	 */
	public void setStatement(MyStatement statement) {
		this.statement = statement;
	}
	
	@Override
	public boolean isIdLabel() {
		return false;
	}

	@Override
	public boolean isCaseLabel() {
		return true;
	}

	@Override
	public boolean isDefaultLabel() {
		return false;
	}

	@Override
	public boolean isEmpty() {
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
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		// add KCs for subelements
		if (caseExpression != null) kcs.addAll(caseExpression.getKCs(level));
		if (statement != null) kcs.addAll(statement.getKCs(level));		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyCaseLabel) {
			MyCaseLabel label = (MyCaseLabel) o;
			return (caseExpression.equals(label.getCaseExpression()) && statement.equals(label.getStatement()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "CASE\n";
		text += "Case Label: " + caseExpression.toString() + "\n" ;
		text += "Statement: " + statement + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		if (caseExpression != null) hash += caseExpression.hashCode();
		if (statement != null) hash += statement.hashCode();
		return hash;
	}

}
