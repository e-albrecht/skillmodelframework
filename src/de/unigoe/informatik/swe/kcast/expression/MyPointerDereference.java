package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Describes a pointer dereference<br /><br />
 * Syntax: <code>*</code><em>pointer-expression</em> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyPointerDereference extends MyMemberAccess {
	
	/**
	 * An expression representing a pointer
	 */
	private MyExpression pointer;

	/* The KCs defined for the array subscript element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.MEMBER_ACCESS));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.POINTER_DEREFERNCE));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new pointer dereference
	 * @param pointer An expression representing a pointer
	 */
	public MyPointerDereference(MyExpression pointer) {
		this.pointer = pointer;
	}
	
	/**
	 * 
	 * @return An expression representing a pointer
	 */
	public MyExpression getPointer() {
		return pointer;
	}
	
	/**
	 * 
	 * @param pointer An expression representing a pointer
	 */
	public void setPointer(MyExpression pointer) {
		this.pointer = pointer;
	}

	@Override
	public boolean isArraySubscript() {
		return false;
	}

	@Override
	public boolean isPointerDereference() {
		return true;
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
		kcs.addAll(pointer.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyPointerDereference) {
			MyPointerDereference p = (MyPointerDereference) o;
			return (pointer.equals(p.getPointer()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "POINTER DEREFERENCE\n";
		text += "Pointer: " + pointer.toString() + "\n";
		return text;
	}
}
