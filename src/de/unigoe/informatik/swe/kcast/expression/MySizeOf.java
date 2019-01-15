package de.unigoe.informatik.swe.kcast.expression;

/**
 * Represents a sizeof operator. The sizeof operator can be applied either to expression or to types.
 * @author Ella Albrecht
 *
 */
public abstract class MySizeOf extends MyExpression {

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
		return true;
	}
	
	/**
	 * 
	 * @return Returns whether the sizeof operator is applied to a type
	 */
	public abstract boolean isSizeOfType();
	
	/**
	 * 
	 * @return Returns whether the sizeof operator is applied to an expression
	 */
	public abstract boolean isSizeOfExpression();

}
