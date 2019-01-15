package de.unigoe.informatik.swe.kcast.expression;


/**
 * Represents an arithmetic expression. Arithmetic operators apply standard mathematical operations to their operands. [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public abstract class MyArithmetic extends MyExpression {
	
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
		return true;
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
	
	/**
	 * 
	 * @return Returns whether the arithmetic expression is unary
	 */
	public abstract boolean isUnary();
	
	/**
	 * 
	 * @return Returns whether the arithmetic expression is binary
	 */
	public abstract boolean isBinary();
}
