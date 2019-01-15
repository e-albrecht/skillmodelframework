package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.initialisation.MyInitClause;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an array subscript<br /><br />
 * Syntax: <em>array-expression</em><code> [ </code><em>element-expression</em><code> ] </code>
 * @author Ella Albrecht
 *
 */
public class MyArraySubscript extends MyMemberAccess{
	
	/**
	 * An expression describing the array
	 */
	private MyExpression array;
	
	/**
	 * An expression describing which element of the array is accessed
	 */
	private MyInitClause element;

	/* The KCs defined for the array subscript element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.MEMBER_ACCESS));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ARRAY_SUBSCRIPT));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * 
	 * Creates a new array subscript expression
	 * @param array An expression describing the array
	 * @param elemen An expression describing which element of the array is accessed
	 */
	public MyArraySubscript(MyExpression array, MyInitClause elemen) {
		this.array = array;
		this.element = elemen;
	}
	
	/**
	 * 
	 * @return An expression describing the array
	 */
	public MyExpression getArray() {
		return array;
	}

	/**
	 * 
	 * @param array An expression describing the array
	 */
	public void setArray(MyExpression array) {
		this.array = array;
	}

	/**
	 * 
	 * @return An expression describing which element of the array is accessed
	 */
	public MyInitClause getElement() {
		return element;
	}

	/**
	 * 
	 * @param element An expression describing which element of the array is accessed
	 */
	public void setElement(MyInitClause element) {
		this.element = element;
	}

	@Override
	public String toString() {
		String text = "ARRAY SUBSCRIPT\n";
		text += "Array: " + array + "\n";
		text += "Element: " + element + "\n";
		return text;
	}

	@Override
	public boolean isArraySubscript() {
		return true;
	}

	@Override
	public boolean isPointerDereference() {
		return false;
	}

	@Override
	public boolean isAddressOf() {
		return false;
	}

	@Override
	public boolean isMemberAccessDot() {
		return false;
	}

	@Override
	public boolean isMemberAccessPointer() {
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
		kcs.addAll(array.getKCs(level));
		kcs.addAll(element.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyArraySubscript) {
			MyArraySubscript arrSub = (MyArraySubscript) o;
			return (array.equals(arrSub.getArray()) && element.equals(arrSub.getElement()));
		}
		return false;
	}
	
}
