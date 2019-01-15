package de.unigoe.informatik.swe.kcast.statement;

/**
 * Represents a jump statement, which unconditionally transfers control flow [<a href="cppreference.com">cppreference.com</a>]. We distinguish between the following jump types:
 * <ul><li>break,</li><li>continue</li><li>return, and</li><li>goto</li></ul>
 * @author Ella Albrecht
 *
 */
public abstract class MyJump extends MyStatement {

	/**
	 * 
	 * @return Returns whether the jump statement is a break statement
	 */
	public abstract boolean isBreak();
	
	/**
	 * 
	 * @return Returns whether the jump statement is a continue statement
	 */
	public abstract boolean isContinue();
	
	/**
	 * 
	 * @return Returns whether the jump statement is a return statement
	 */
	public abstract boolean isReturn();
	
	/**
	 * 
	 * @return Returns whether the jump statement is a goto statement
	 */
	public abstract boolean isGoto();
	
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
		return false;
	}

	@Override
	public boolean isIteration() {
		return false;
	}

	@Override
	public boolean isJump() {
		return true;
	}

	@Override
	public boolean isLabel() {
		return false;
	}
	
}
