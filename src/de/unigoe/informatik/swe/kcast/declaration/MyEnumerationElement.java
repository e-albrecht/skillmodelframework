package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.MyNode;
import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an enumerator
 * @author Ella Albrecht
 *
 */
public class MyEnumerationElement extends MyNode{

	/**
	 * Name of the enumerator
	 */
	private String enumerator;
	
	/**
	 * Expression that represents the value of the enumerator
	 */
	private MyExpression constantExpression;
	
	/* The KCs defined for the type cast element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.DATA_TYPE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.ELABORATED_TYPE_SPECIFIER,KnowledgeComponent.ENUM));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new enumerator without value assignment
	 * @param enumerator Name of the enumerator
	 */
	public MyEnumerationElement(String enumerator) {
		this.enumerator = enumerator;
	}
	
	/**
	 * Creates a new enumerator
	 * @param enumerator Name of the enumerator
	 * @param constantExpression Expression that represents the value of the enumerator
	 */
	public MyEnumerationElement(String enumerator, MyExpression constantExpression) {
		this.enumerator = enumerator;
		this.constantExpression = constantExpression;
	}
	
	/**
	 * 
	 * @return Name of the enumerator
	 */
	public String getEnumerator() {
		return enumerator;
	}
	
	/**
	 * 
	 * @param enumerator Name of the enumerator
	 */
	public void setEnumerator(String enumerator) {
		this.enumerator = enumerator;
	}
	
	/**
	 * 
	 * @return Expression that represents the value of the enumerator
	 */
	public MyExpression getConstantExpression() {
		return constantExpression;
	}
	
	/**
	 * 
	 * @param constantExpression Expression that represents the value of the enumerator
	 */
	public void setConstantExpression(MyExpression constantExpression) {
		this.constantExpression = constantExpression;
	}
	
	/**
	 * 
	 * @return Returns whether the enumerator has a value assigned
	 */
	public boolean hasConstantExpression() {
		if (constantExpression == null) return false;
		return true;
	}
	
	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		// add KCs for array
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (level > 1) {
				kcs.addAll(kcs2);
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		// add KCs for subelements
		if (constantExpression != null) kcs.addAll(constantExpression.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyEnumerationElement) {
			MyEnumerationElement elem = (MyEnumerationElement) o;
			return (enumerator.equals(elem.getEnumerator()) && constantExpression.equals(elem.getConstantExpression()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "ENUMERATION ELEMENT: " + enumerator;
		if (constantExpression != null) text += " = " + constantExpression;
		return text;
	}
	
	
	
}
