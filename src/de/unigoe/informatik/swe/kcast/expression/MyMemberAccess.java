package de.unigoe.informatik.swe.kcast.expression;

/**
 * Represents a member access operator. Member access operators allow access to the members of their operands. [<a href="cppreference.com">cppreference.com</a>]
 * 
 * @author Ella Albrecht
 *
 */
public abstract class MyMemberAccess extends MyExpression {

	@Override
	public boolean isPrimary() {
		return false;
	}

	@Override
	public boolean isAssignment() {
		return false;
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
		return true;
	}
	
	@Override
	public boolean isTypeCast() {
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
	public boolean isConditionalOperator() {
		return false;
	}

	@Override
	public boolean isSizeOf() {
		return false;
	}
	
	/**
	 * 
	 * @return Return whether the member access operator is an array subscript
	 */
	public abstract boolean isArraySubscript();
	
	/**
	 * 
	 * @return Return whether the member access operator is a pointer dereference
	 */
	public abstract boolean isPointerDereference();
	
	/**
	 * 
	 * @return Return whether the member access operator is an address of operator
	 */
	public abstract boolean isAddressOf();
	
	/**
	 * 
	 * @return Return whether the member access operator is a dot operator
	 */
	public abstract boolean isMemberAccessDot();
	
	/**
	 * 
	 * @return Return whether the member access operator is a member access through pointer
	 */
	public abstract boolean isMemberAccessPointer();

}
