package de.unigoe.informatik.swe.kcast.initialisation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a field designator for structs or unions.<br /><br />
 * Syntax: <code>.</code><em>fieldname</em><br />
 * Example: <code>struct {int day,mon,year;} z = {.day=31, .mon=12, .year=2014}; </code> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyFieldDesignator implements MyDesignator{
	
	/**
	 * The field name of the struct/union which is initialized
	 */
	private String fieldName;

	/* The KCs defined for the field designator element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.INITIALIZATION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DESIGNATOR));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.FIELD_DESIGNATOR));

	/**
	 * Creates a new field designator
	 * @param fieldName The field name of the struct/union which is initialized
	 */
	public MyFieldDesignator(String fieldName) {
		this.fieldName = fieldName;
	}
	
	/**
	 * 
	 * @return The field name of the struct/union which is initialized
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 
	 * @param fieldName The field name of the struct/union which is initialized
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public boolean isArrayDesignator() {
		return false;
	}

	@Override
	public boolean isFieldDesignator() {
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
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyFieldDesignator) {
			MyFieldDesignator desig = (MyFieldDesignator) o;
			return desig.getFieldName().equals(fieldName);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return fieldName.hashCode();
	}
	
	@Override
	public String toString() {
		return "FIELD DESIGNATOR \n Field name: " + fieldName;
	}
}
