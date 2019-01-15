package de.unigoe.informatik.swe.kcast.initialisation;

import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a designator for a designated initializer
 * @author Ella Albrecht
 *
 */
public interface MyDesignator {

	/**
	 * 
	 * @return Return whether the designator is an array designator
	 */
	public boolean isArrayDesignator();
	
	/**
	 * 
	 * @return Returns whether the designator is a field designator
	 */
	public boolean isFieldDesignator();
	
	/**
	 * Returns the KCs on a given level used in the initialization
	 * @param level The level of KCs
	 * @return Set of KCs used in the initialization on the given KC level
	 */
	public Set<KnowledgeComponent> getKCs(int level);
}
