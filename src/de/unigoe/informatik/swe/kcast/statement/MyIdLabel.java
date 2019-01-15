package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a named label<br /><br />
 * Syntax: <em>identifier</em><code> : </code><em>statement</em> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyIdLabel extends MyLabel {

	/**
	 * The name of the label
	 */
	private String identifier;
	
	/**
	 * The statement to which the label refers
	 */
	private MyStatement statement;
	
	/* The KCs defined for the named label element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.LABEL));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new named label
	 * @param identifier The name of the label
	 * @param statement The statement to which the label refers
	 */
	public MyIdLabel(String identifier, MyStatement statement) {
		this.identifier = identifier;
		this.statement = statement;
	}
	
	/**
	 * 
	 * @return The name of the label
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * 
	 * @param identifier The name of the label
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * 
	 * @return The statement to which the label refers
	 */
	public MyStatement getStatement() {
		return statement;
	}

	/**
	 * 
	 * @param statement The statement to which the label shall refer
	 */
	public void setStatement(MyStatement statement) {
		this.statement = statement;
	}
	
	@Override
	public boolean isIdLabel() {
		return true;
	}

	@Override
	public boolean isCaseLabel() {
		return false;
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
		kcs.addAll(statement.getKCs(level));		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyIdLabel) {
			MyIdLabel label = (MyIdLabel) o;
			return (identifier.equals(label.getIdentifier()) && statement.equals(label.getStatement()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "LABEL\n";
		text += "Label Name: " + identifier + "\n" ;
		text += "Statement: " + statement + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		return identifier.hashCode() + statement.hashCode();
	}

}
