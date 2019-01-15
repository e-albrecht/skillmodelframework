package de.unigoe.informatik.swe.kcast.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an assignment. We distinguish between eleven assignment expressions.<br /><br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]:<br />
 * (1) <em>lhs</em><code> = </code><em>rhs</em> (basic assignment)<br />
 * (2) <em>lhs</em><code> += </code><em>rhs</em> (addition assignment)<br />
 * (3) <em>lhs</em><code> -= </code><em>rhs</em> (subtraction assignment)<br />
 * (4) <em>lhs</em><code> *= </code><em>rhs</em> (multiplication assignment)<br />
 * (5) <em>lhs</em><code> /= </code><em>rhs</em> (division assignment)<br />
 * (6) <em>lhs</em><code> %= </code><em>rhs</em> (modulo assignment)<br />
 * (7) <em>lhs</em><code> &= </code><em>rhs</em> (bitwise AND assignment)<br />
 * (8) <em>lhs</em><code> |= </code><em>rhs</em> (bitwise OR assignment)<br />
 * (9) <em>lhs</em><code> ^= </code><em>rhs</em> (bitwise XOR assignment)<br />
 * (10) <em>lhs</em><code> <<= </code><em>rhs</em> (bitwise left shift assignment)<br />
 * (11) <em>lhs</em><code> >>= </code><em>rhs</em> (bitwise right assignment)<br />
 * @author Ella Albrecht
 *
 */
public class MyAssignment extends MyExpression{
	
	public enum AssignmentType {
		BASIC_ASSIGNMENT, ADDITION_ASSIGNMENT, SUBTRACTION_ASSIGNMENT, MULTIPLICATION_ASSIGNMENT,
		DIVISION_ASSIGNMENT, MODULO_ASSIGNMENT, BITWISE_AND_ASSIGNMENT, BITWISE_OR_ASSIGNMENT, BITWISE_XOR_ASSIGNMENT,
		BITWISE_LEFT_SHIFT_ASSIGNMENT, BITWISE_RIGHT_SHIFT_ASSIGNMENT
	}

	/* The KCs defined for the array subscript element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ASSIGNMENT));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * The expression to which a value is assigned
	 */
	private MyExpression lhs; 
	
	/**
	 * The value that is assigned
	 */
	private MyExpression rhs;
	
	/**
	 * The type of the assignment
	 */
	private AssignmentType type;

	/**
	 * Creates a new assignment expression
	 * @param type The type of the assignment
	 * @param lhs The expression to which a value is assigned
	 * @param rhs The value that is assigned
	 */
	public MyAssignment(AssignmentType type, MyExpression lhs, MyExpression rhs) {
		this.type = type;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	/**
	 * 
	 * @return The expression to which a value is assigned
	 */
	public MyExpression getLhs() {
		return lhs;
	}

	/**
	 * 
	 * @param lhs The expression to which a value is assigned
	 */
	public void setLhs(MyExpression lhs) {
		this.lhs = lhs;
	}

	/**
	 * 
	 * @return The value that is assigned
	 */
	public MyExpression getRhs() {
		return rhs;
	}

	/**
	 * 
	 * @param rhs The value that is assigned
	 */
	public void setRhs(MyExpression rhs) {
		this.rhs = rhs;
	}

	/**
	 * 
	 * @return The type of the assignment
	 */
	public AssignmentType getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of the assignment
	 */
	public void setType(AssignmentType type) {
		this.type = type;
	}
	
	@Override
	public boolean isPrimary() {
		return false;
	}

	@Override
	public boolean isAssignment() {
		return true;
	}

	@Override
	public boolean isIncDecrement() {
		return false;
	}

	@Override
	public boolean isArithmetic() {
		return false;
	}

	@Override
	public boolean isLogical() {
		return false;
	}

	@Override
	public boolean isComparison() {
		return false;
	}

	@Override
	public boolean isMemberAccess() {
		return false;
	}

	@Override
	public boolean isFunctionCall() {
		return false;
	}

	@Override
	public boolean isCommaOperator() {
		return false;
	}

	@Override
	public boolean isTypeCast() {
		return false;
	}

	@Override
	public boolean isConditionalOperator() {
		return false;
	}

	@Override
	public boolean isSizeOf() {
		return false;
	}

	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (type.equals(AssignmentType.BITWISE_AND_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_OPERATION); 
			if (type.equals(AssignmentType.BITWISE_OR_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_OPERATION); 
			if (type.equals(AssignmentType.BITWISE_XOR_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_OPERATION); 
			if (type.equals(AssignmentType.BITWISE_LEFT_SHIFT_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_OPERATION); 
			if (type.equals(AssignmentType.BITWISE_RIGHT_SHIFT_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_OPERATION); 

			if (level > 1) {
				kcs.addAll(kcs2);
				if (type.equals(AssignmentType.ADDITION_ASSIGNMENT)) kcs.add(KnowledgeComponent.ADDITION); 
				if (type.equals(AssignmentType.SUBTRACTION_ASSIGNMENT)) kcs.add(KnowledgeComponent.SUBTRACTION); 
				if (type.equals(AssignmentType.MULTIPLICATION_ASSIGNMENT)) kcs.add(KnowledgeComponent.PRODUCT); 
				if (type.equals(AssignmentType.DIVISION_ASSIGNMENT)) kcs.add(KnowledgeComponent.DIVISION); 
				if (type.equals(AssignmentType.MODULO_ASSIGNMENT)) kcs.add(KnowledgeComponent.MODULO); 
				if (type.equals(AssignmentType.BITWISE_AND_ASSIGNMENT)) kcs.add(KnowledgeComponent.AND); 
				if (type.equals(AssignmentType.BITWISE_OR_ASSIGNMENT)) kcs.add(KnowledgeComponent.OR); 
				if (type.equals(AssignmentType.BITWISE_LEFT_SHIFT_ASSIGNMENT)) {
					kcs.add(KnowledgeComponent.SHIFT); 				
				}
				if (type.equals(AssignmentType.BITWISE_RIGHT_SHIFT_ASSIGNMENT)) {
					kcs.add(KnowledgeComponent.SHIFT); 						}

				if (level > 2) {
					kcs.addAll(kcs3);
					if (type.equals(AssignmentType.BASIC_ASSIGNMENT)) kcs.add(KnowledgeComponent.BASIC_ASSIGNMENT); 
					if (type.equals(AssignmentType.ADDITION_ASSIGNMENT)) kcs.add(KnowledgeComponent.ADD_ASSIGNMENT); 
					if (type.equals(AssignmentType.SUBTRACTION_ASSIGNMENT)) kcs.add(KnowledgeComponent.SUB_ASSIGNMENT); 
					if (type.equals(AssignmentType.MULTIPLICATION_ASSIGNMENT)) kcs.add(KnowledgeComponent.MULT_ASSIGNMENT); 
					if (type.equals(AssignmentType.DIVISION_ASSIGNMENT)) kcs.add(KnowledgeComponent.DIV_ASSIGNMENT); 
					if (type.equals(AssignmentType.MODULO_ASSIGNMENT)) kcs.add(KnowledgeComponent.MOD_ASSIGNMENT); 
					if (type.equals(AssignmentType.BITWISE_AND_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_AND_ASSIGNMENT); 
					if (type.equals(AssignmentType.BITWISE_OR_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_OR_ASSIGNMENT); 
					if (type.equals(AssignmentType.BITWISE_XOR_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_XOR_ASSIGNMENT); 
					if (type.equals(AssignmentType.BITWISE_LEFT_SHIFT_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_LEFT_SHIFT_ASSIGNMENT); 
					if (type.equals(AssignmentType.BITWISE_RIGHT_SHIFT_ASSIGNMENT)) kcs.add(KnowledgeComponent.BITWISE_RIGHT_SHIFT_ASSIGNMENT); 
				}
			}		
		}
		// add KCs for subelements
		kcs.addAll(lhs.getKCs(level));
		kcs.addAll(rhs.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyAssignment) {
			MyAssignment ass = (MyAssignment) o;
			return (type.equals(ass.getType()) && lhs.equals(ass.getLhs()) && rhs.equals(ass.getRhs()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "ASSIGNMENT\n";
		text += "Type: " + type + "\n";
		text += "LHS: " + lhs.toString() + "\n";
		text += "RHS: " + rhs.toString() + "\n";
		return text;
	}

}
