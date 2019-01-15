package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a member access of a struct or union by a pointer<br /><br />
 * Syntax: <em>expression</em><code>.</code><em>member-name</em><code> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyMemberAccessPointer extends MyMemberAccess {

	/**
	 * An expression describing a pointer to the struct/union whose member is accessed
	 */
	private MyExpression pointer;
	
	/**
	 * The name of the member that is accessed
	 */
	private String member;
	
	/* The KCs defined for the array subscript element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.MEMBER_ACCESS));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.POINTER_OPERATOR));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new member access by pointer
	 * @param operand Expression of type struct or union
	 * @param member Name of a member of the struct/union
	 */
	public MyMemberAccessPointer(MyExpression pointer, String member) {
		this.pointer = pointer;
		this.member = member;
	}

	/**
	 * 
	 * @return An expression describing a pointer to the struct/union whose member is accessed
	 */
	public MyExpression getPointer() {
		return pointer;
	}
	
	/**
	 * 
	 * @param pointer An expression describing a pointer to the struct/union whose member is accessed
	 */
	public void setPointer(MyExpression pointer) {
		this.pointer = pointer;
	}
	
	/**
	 * 
	 * @return The name of the member that is accessed
	 */
	public String getMember() {
		return member;
	}
	
	/**
	 * 
	 * @param member The name of the member that is accessed
	 */
	public void setMember(String member) {
		this.member = member;
	}
	
	@Override
	public boolean isArraySubscript() {
		return false;
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
		// add KCs for subelements
		kcs.addAll(pointer.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyMemberAccessPointer) {
			MyMemberAccessPointer p = (MyMemberAccessPointer) o;
			return (member.equals(p.getMember()) && pointer.equals(p.getPointer()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "MEMBER ACCESS - POINTER\n";
		text += "Pointer: " + pointer.toString() + "\n";
		text += "Member: " + member + "\n";
		return text;
	}
	
}
