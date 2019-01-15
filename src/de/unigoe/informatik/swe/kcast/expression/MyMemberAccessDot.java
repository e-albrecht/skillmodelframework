package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a member access of a struct or union by dot<br /><br />
 * Syntax: <em>expression</em><code>.</code><em>member-name</em><code> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyMemberAccessDot extends MyMemberAccess {
	
	/**
	 * An expression describing the struct/union whose member is accessed
	 */
	private MyExpression operand;
	
	/**
	 * The name of the member that is accessed
	 */
	private String member;

	/* The KCs defined for the array subscript element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.MEMBER_ACCESS));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DOT_OPERATOR));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	
	/**
	 * Creates a new member access
	 * @param operand Expression of type struct or union
	 * @param member Name of a member of the struct/union
	 */
	public MyMemberAccessDot(MyExpression operand, String member) {
		this.operand = operand;
		this.member = member;
	}
	
	/**
	 * 
	 * @return An expression describing the struct/union whose member is accessed
	 */
	public MyExpression getOperand() {
		return operand;
	}
	
	/**
	 * 
	 * @param operand An expression describing the struct/union whose member is accessed
	 */
	public void setOperand(MyExpression operand) {
		this.operand = operand;
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
		return true;
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
		kcs.addAll(operand.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyMemberAccessDot) {
			MyMemberAccessDot dot = (MyMemberAccessDot) o;
			return (member.equals(dot.getMember()) && operand.equals(dot.getOperand()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "MEMBER ACCESS - DOT\n";
		text += "Operand: " + operand.toString() + "\n";
		text += "Member: " + member + "\n";
		return text;
	}
	
	
}
