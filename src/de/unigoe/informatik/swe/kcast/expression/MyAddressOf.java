package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an address of operator
 * Syntax: <code>&</code><em>expression</em>
 * @author Ella Albrecht
 *
 */
public class MyAddressOf extends MyMemberAccess {
	
	/**
	 * The object of which the address is accessed
	 */
	private MyExpression operand;

	/* The KCs defined for the address of element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.MEMBER_ACCESS));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ADDRESS_OF));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new address of operation
	 * @param operand An expression describing the object which address is accessed
	 */
	public MyAddressOf(MyExpression operand) {
		this.operand = operand;
	}
	
	/**
	 * 
	 * @return An expression describing the object which address is accessed
	 */
	public MyExpression getOperand() {
		return operand;
	}

	/**
	 * 
	 * @param operand An expression describing the object which address is accessed
	 */
	public void setOperand(MyExpression operand) {
		this.operand = operand;
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
		return true;
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
		kcs.addAll(operand.getKCs(level));
		
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyAddressOf) {
			MyAddressOf addr = (MyAddressOf) o;
			return (addr.getOperand().equals(operand));
		}
		return false;
	}

	@Override
	public String toString() {
		String text ="ADDRESS OF OPERATOR\n";
		text += "Operand: " + operand + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		return operand.hashCode() + 1;
	}
	
}
