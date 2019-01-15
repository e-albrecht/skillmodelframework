package de.unigoe.informatik.swe.kcast.initialisation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a single equals initializaton. <br />
 * Syntax: <code>= </code><em>expression</em> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyEqualsInitialisation extends MyInitialisation{

	/**
	 * The expression to which a variable is initialized
	 */
	private MyInitClause initClause;
	
	/* The KCs defined for the equals initialization element on different levels */		
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.INITIALIZATION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EQUALS_INITIALIZATION));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new equals initialization
	 * @param myInitClause The expression to which a variable is initialized
	 */
	public MyEqualsInitialisation(MyInitClause myInitClause) {
		this.initClause = myInitClause;
	}

	/**
	 * 
	 * @return The expression to which a variable is initialized
	 */
	public MyInitClause getInitClause() {
		return initClause;
	}

	/**
	 * 
	 * @param initClause The expression to which a variable is initialized
	 */
	public void setInitClause(MyExpression initClause) {
		this.initClause = initClause;
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
		kcs.addAll(initClause.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyEqualsInitialisation) {
			MyEqualsInitialisation init = (MyEqualsInitialisation) o;
			return (initClause.equals(init.getInitClause()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "EQUALS INITILAZATION\n";
		text += "Init Clause: " + initClause.toString() + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		return initClause.hashCode()+1;
	}

	
	
}
