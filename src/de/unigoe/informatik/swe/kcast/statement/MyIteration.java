package de.unigoe.informatik.swe.kcast.statement;

/**
 * Represents an iteration statement, which repeatedly execute a statement [<a href="cppreference.com">cppreference.com</a>].  We distinguish between the following iteration types:
 * <ul><li>while,</li><li>do-while, and</li><li>for</li></ul>
 * @author Ella Albrecht
 *
 */
public abstract class MyIteration extends MyStatement {

	/**
	 * 
	 * @return Returns whether the iteration is a while or do-while statement
	 */
	public abstract boolean isWhile();
	
	/**
	 * 
	 * @return Returns whether the iteration is a for statement
	 */
	public abstract boolean isFor();
	
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
		return true;
	}

	@Override
	public boolean isJump() {
		return false;
	}

	@Override
	public boolean isLabel() {
		return false;
	}

}
