package de.unigoe.informatik.swe.kcast.initialisation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.MyNode;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a designated initializer. <br /><br />
 * Syntax: <em>designator-list</em><code> = </code><em>initializer</em>[<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyDesignatedInitializer extends MyNode implements MyInitClause{
	
	/**
	 * The designator list
	 */
	private List<MyDesignator> designators;
	
	/**
	 * The initialization clause belonging to the designators
	 */
	private MyInitClause initClause;
	
	/* The KCs defined for the designated initializer element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.INITIALIZATION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DESIGNATED_INITIALIZER));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new designated initializer with an empty designator list
	 * @param myInitClause The initialization clause for the initializer
	 */
	public MyDesignatedInitializer(MyInitClause myInitClause) {
		this.initClause = myInitClause;
		designators = new LinkedList<MyDesignator>();
	}
	
	/**
	 * 
	 * @return The designator list
	 */
	public List<MyDesignator> getDesignators() {
		return designators;
	}

	/**
	 * 
	 * @param designators The list of designators for the initializer
	 */
	public void setDesignators(List<MyDesignator> designators) {
		this.designators = designators;
	}
	
	/**
	 * Adds a designator to the designator list of the initializer
	 * @param designator
	 */
	public void addDesignator(MyDesignator designator) {
		designators.add(designator);
	}

	/**
	 * 
	 * @return The initialization clause for the initializer
	 */
	public MyInitClause getInitClause() {
		return initClause;
	}

	/**
	 * 
	 * @param initClause The initialization clause for the initializer
	 */
	public void setOperand(MyInitClause initClause) {
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
				if (level > 2) {
					kcs.addAll(kcs3);
				}
			}		
		}
		// add KCs for subelements
		kcs.addAll(initClause.getKCs(level));
		for (MyDesignator designator : designators) {
			kcs.addAll(designator.getKCs(level));
		}
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyDesignatedInitializer) {
			MyDesignatedInitializer init = (MyDesignatedInitializer) o;
			return designators.equals(init.getDesignators()) && initClause.equals(init.getInitClause());
		}
		return false;
	}
	
	public int hashCode() {
		return designators.hashCode() + initClause.hashCode();
	}
	
	public String toString() {
		String text = "DESIGNATED INITIALIZER\n";
		text += "Designators: \n";
		for (MyDesignator designator : designators) {
			text += designator + "\n";
		}
		text += "Init clause: " + initClause;
		return text;
	}

}
