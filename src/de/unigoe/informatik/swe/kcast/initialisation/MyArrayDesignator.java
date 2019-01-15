package de.unigoe.informatik.swe.kcast.initialisation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.MyNode;
import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an array designator.<br /><br />
 * Syntax: <code>[ </code><em>expression</em><code> ]</code><br />
 * Example: <code>int n[5] = {[4]=5,[0]=1,2,3,4} // holds 1,2,3,4,5</code> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyArrayDesignator extends MyNode implements MyDesignator{

	/**
	 * An expression describing which element of the array is initialized
	 */
	private MyExpression arraySubscript;
	
	/* The KCs defined for the array designator element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.INITIALIZATION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DESIGNATOR));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ARRAY_DESIGNATOR));
	
	/**
	 * Creates a new array designator
	 * @param arraySubscript An expression describing which element of the array is initialized
	 */
	public MyArrayDesignator(MyExpression arraySubscript) {
		this.arraySubscript = arraySubscript;
	}
	
	/**
	 * 
	 * @return An expression describing which element of the array is initialized
	 */
	public MyExpression getArraySubscript() {
		return arraySubscript;
	}

	/**
	 * 
	 * @param arraySubscript An expression describing which element of the array is initialized
	 */
	public void setArraySubscript(MyExpression arraySubscript) {
		this.arraySubscript = arraySubscript;
	}
	
	@Override
	public boolean isArrayDesignator() {
		return true;
	}

	@Override
	public boolean isFieldDesignator() {
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
		kcs.addAll(arraySubscript.getKCs(level));
		
		return kcs;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof MyArrayDesignator) {
			MyArrayDesignator desig = (MyArrayDesignator) o;
			return desig.getArraySubscript().equals(arraySubscript);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return arraySubscript.hashCode()+1;
	}
	
	@Override
	public String toString() {
		return "ARRAY DESIGNATOR \n Array subscript: " + arraySubscript;
	}

}
