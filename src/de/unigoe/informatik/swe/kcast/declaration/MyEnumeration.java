package de.unigoe.informatik.swe.kcast.declaration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an enumeration<br /><br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]: <code>enum</code> <em>identifier</em>(optional)<code>{</code><em> enumerator-list </em><code>}</code>
 * @author Ella Albrecht
 *
 */
public class MyEnumeration extends  MyTypeSpecifier{

	/**
	 * Name of the enum
	 */
	private String identifier;
	
	/**
	 * List of enumerators
	 */
	private List<MyEnumerationElement> list;

	/* The KCs defined for the type cast element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.DATA_TYPE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(
			Arrays.asList(KnowledgeComponent.ELABORATED_TYPE_SPECIFIER, KnowledgeComponent.ENUM));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new enum without enumerators
	 * @param identifier Name of the enum
	 */
	public MyEnumeration(String identifier) {
		this.identifier = identifier;
		this.list = new ArrayList<MyEnumerationElement>();
	}
	
	/**
	 * Creates a new enum
	 * @param identifier Name of the enum
	 * @param list List of enumerators
	 */
	public MyEnumeration(String identifier, List<MyEnumerationElement> list) {
		this.identifier = identifier;
		this.list = list;
	}
	
	/**
	 * Creates an enum without a name
	 * @param list List of enumerators
	 */
	public MyEnumeration(List<MyEnumerationElement> list) {
		this.list = list;
		identifier = "";
	}
	
	/**
	 * 
	 * @return List of enumerators
	 */
	public List<MyEnumerationElement> getList() {
		return list;
	}

	/**
	 * 
	 * @param list List of enumerators
	 */
	public void setList(List<MyEnumerationElement> list) {
		this.list = list;
	}
	
	/**
	 * Add an enumerator to the enumerator list
	 * @param element Enumerator to be added
	 */
	public void addEnumerationElement(MyEnumerationElement element) {
		list.add(element);
	}

	/**
	 * 
	 * @return Name of the enum
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * 
	 * @param identifier Name of the enum
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
		
	@Override
	public boolean isVoidType() {
		return false;
	}

	@Override
	public boolean isArithmeticType() {
		return false;
	}

	@Override
	public boolean isCharacterType() {
		return false;
	}

	@Override
	public boolean isIntegerType() {
		return false;
	}

	@Override
	public boolean isFloatingType() {
		return false;
	}

	@Override
	public boolean isStruct() {
		return false;
	}

	@Override
	public boolean isUnion() {
		return false;
	}

	@Override
	public boolean isEnum() {
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
				if (level > 2)
					kcs.addAll(kcs3);
			}
		}
		for (MyEnumerationElement elem : list) {
			kcs.addAll(elem.getKCs(level));
		}
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyEnumeration) {
			MyEnumeration enumer = (MyEnumeration) o;
			return (identifier.equals(enumer.getIdentifier()) && list.equals(enumer.getList()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "ENUMERATION\n";
		text += "Identifier " + identifier + "\n";
		text += "Enumeration elements:\n";
		for (MyEnumerationElement elem : list) {
			text += elem.toString() + "\n";
		}
		return text;
	}
}
