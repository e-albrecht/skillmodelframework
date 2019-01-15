package de.unigoe.informatik.swe.kcast.initialisation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an initializer list, which is comma separarated list of initializers<br />
 * Syntax: <code>= { </code><em>initializer-list</em><code> }<code> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyInitialisationList extends MyInitialisation implements MyInitClause {

	/**
	 * The list of initialization clauses
	 */
	private List<MyInitClause> initClauses;

	/* The KCs defined for the initialization list element on different levels */		
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.INITIALIZATION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.INITIALIZATION_LIST));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new empty initialization list
	 */
	public MyInitialisationList() {
		initClauses = new ArrayList<MyInitClause>();
	}
	
	/**
	 * Creates a new initialization list
	 * @param initClauses The initialization clauses contained in the list
	 */
	public MyInitialisationList(List<MyInitClause> initClauses) {
		this.initClauses = initClauses;
	}

	/**
	 * 
	 * @return The initialization clauses contained in the list
	 */
	public List<MyInitClause> getInitClauses() {
		return initClauses;
	}

	/**
	 * 
	 * @param initClauses The initialization clauses to be set as contained in the list
	 */
	public void setInitClauses(List<MyInitClause> initClauses) {
		this.initClauses = initClauses;
	}
	
	/**
	 * Adds an initialization clause to the initialization list
	 * @param myInitClause The clause to be added ad the end of the initialization list
	 */ 
	public void addInitClause(MyInitClause myInitClause) {
		initClauses.add(myInitClause);
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
		for (MyInitClause initClause : initClauses) {
			kcs.addAll(initClause.getKCs(level));
		}
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyInitialisationList) {
			MyInitialisationList init = (MyInitialisationList) o;
			return (initClauses.equals(init.getInitClauses()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "INITIALIZATION LIST\n";
		text += "Init Clauses: \n" ;
		for (MyInitClause initClause : initClauses) {
			text += initClause.toString() + "\n";
		}
		return text;
	}
	
	@Override
	public int hashCode() {
		return initClauses.hashCode();
	}
}
