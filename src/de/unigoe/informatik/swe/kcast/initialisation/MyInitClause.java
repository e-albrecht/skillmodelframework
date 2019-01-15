package de.unigoe.informatik.swe.kcast.initialisation;

import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an initializer clause <br /><br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]: <br />
 * (1) <em>expression</em><br />
 * (2) <code>{ </code><em>initializer-list</em><code> }</code><br />
 * (3) <em>designator-list</em><code> = </code><em>initializer</em>
 * @author Ella Albrecht
 *
 */
public interface MyInitClause {

	/**
	 * Returns the KCs on a given level used in the initialization
	 * @param level The level of KCs
	 * @return Set of KCs used in the initialization on the given KC level
	 */
	public Set<KnowledgeComponent> getKCs(int level);

}
