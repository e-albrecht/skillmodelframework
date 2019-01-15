package de.unigoe.informatik.swe.kcast.statement;

/**
 * Represents a label
 * @author Ella Albrecht
 *
 */
public abstract class MyLabel extends MyStatement {

	/**
	 * 
	 * @return Returns whether the label is a named label
	 */
	public abstract boolean isIdLabel();
	
	/**
	 * 
	 * @return Returns whether the label is a case label in a switch statement
	 */
	public abstract boolean isCaseLabel();
	
	/**
	 * 
	 * @return Returns whether the label is a default label in a switch statement
	 */
	public abstract boolean isDefaultLabel();
	
	@Override
	public boolean isBlock() {
		return false;
	}

	@Override
	public boolean isDeclaration() {
		return false;
	}

	@Override
	public boolean isExpression() {
		return false;
	}

	@Override
	public boolean isSelection() {
		return false;
	}

	@Override
	public boolean isIteration() {
		return false;
	}

	@Override
	public boolean isJump() {
		return false;
	}

	@Override
	public boolean isLabel() {
		return true;
	}

}
