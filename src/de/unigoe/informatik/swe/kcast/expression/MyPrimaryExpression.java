package de.unigoe.informatik.swe.kcast.expression;

/**
 * Represents a primary expression
 * @author Ella Albrecht
 *
 */
public abstract class MyPrimaryExpression extends MyExpression{

	public abstract boolean isIntegerConstant(); 
	public abstract boolean isCharacterConstant();
	public abstract boolean isFloatingConstant();
	public abstract boolean isStringLiteral();
	public abstract boolean isIdentifier(); 
	public abstract boolean isPredefinedConstant();
	
	@Override
	public boolean isPrimary() {
		return true;
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
		return false;
	}
}
