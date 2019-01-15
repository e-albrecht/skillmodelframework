package de.unigoe.informatik.swe.kcast.statement;

/**
 * Represents a selection statement, which chooses between one of several statements depending on the value of an expression [<a href="cppreference.com">cppreference.com</a>]. We distinguish between the following selection types:
 * <ul><li>if and</li><li>switch</li></ul>
 * @author Ella Albrecht
 *
 */
public abstract class MySelection extends MyStatement{

	/**
	 * 
	 * @return Returns whether the selection is an if statement
	 */
	public abstract boolean isIf();
	
	/**
	 * 
	 * @return Returns whether the selection is a switch statement
	 */
	public abstract boolean isSwitch();
	
	@Override
	public boolean isCaseLabel() {
		return false;
	}
	
	@Override
	public boolean isDefaultLabel() {
		return false;
	}
	
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
		return true;
	}

	@Override
	public boolean isIteration() {
		return false;
	}

	@Override
	public boolean isJump() {
		return false;
	}

}
