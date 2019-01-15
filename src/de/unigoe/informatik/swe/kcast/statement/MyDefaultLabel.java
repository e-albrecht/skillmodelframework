package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents the default label in a switch statement, i.e., the label which is evaluated to true if none of the other cases matches.<br /><br />
 * Syntax: <code>default : </code> <em>statement</em> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyDefaultLabel extends MyLabel{

	/**
	 * The statement that is executed if none of the other cases matches
	 */
	private MyStatement statement;
	
	/* The KCs defined for the default label element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.LABEL));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SWITCH));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DEFAULT));

	/**
	 * Creates a new default label
	 * @param statement The statement that is executed if none of the other cases matches
	 */
	public MyDefaultLabel(MyStatement statement) {
		this.statement = statement;
	}
	
	/**
	 * 
	 * @return The statement that is executed if none of the other cases matches
	 */
	public MyStatement getStatement() {
		return statement;
	}

	/**
	 * 
	 * @param statement The statement that is executed if none of the other cases matches
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
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override 
	public boolean isDefaultLabel() {
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
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		// add KCs for subelements
		if (statement != null) kcs.addAll(statement.getKCs(level));		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyDefaultLabel) {
			MyDefaultLabel label = (MyDefaultLabel) o;
			return (statement.equals(label.getStatement()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "DEFAULT\n";
		text += "Statement: " + statement + "\n";
		return text;
	}
	
	@Override 
	public int hashCode() {
		if (statement != null) return statement.hashCode()+1;
		return super.hashCode();
	}

}
