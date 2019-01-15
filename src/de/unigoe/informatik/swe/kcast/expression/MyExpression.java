package de.unigoe.informatik.swe.kcast.expression;

import de.unigoe.informatik.swe.kcast.MyNode;
import de.unigoe.informatik.swe.kcast.initialisation.MyInitClause;

/**
 * Represents an expression. An expression is a sequence of operators and their operands that specifies a computation. [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public abstract class MyExpression extends MyNode  implements MyInitClause{

	/**
	 * 
	 * @return Returns whether the expression is a primary expression
	 */
	public abstract boolean isPrimary();
	
	/**
	 * 
	 * @return Returns whether the expression is an assignment
	 */
	public abstract boolean isAssignment();
	
	/**
	 * 
	 * @return Returns whether the expression is an increment/decrement
	 */
	public abstract boolean isIncDecrement();
	
	/**
	 * 
	 * @return Returns whether the expression is an arithmetic expression
	 */
	public abstract boolean isArithmetic();
	
	/**
	 * 
	 * @return Returns whether the expression is a logical expression
	 */
	public abstract boolean isLogical();
	
	/**
	 * 
	 * @return Returns whether the expression is a relational expression
	 */
	public abstract boolean isComparison();
	
	/**
	 * 
	 * @return Returns whether the expression is a member access
	 */
	public abstract boolean isMemberAccess();
	
	/**
	 * 
	 * @return Returns whether the expression is a function call
	 */
	public abstract boolean isFunctionCall();
	
	/**
	 * 
	 * @return Returns whether the expression is a comma operator
	 */
	public abstract boolean isCommaOperator();
	
	/**
	 * 
	 * @return Returns whether the expression is a type cast
	 */
	public abstract boolean isTypeCast();
	
	/**
	 * 
	 * @return Returns whether the expression is a conditional operator
	 */
	public abstract boolean isConditionalOperator();
	
	/**
	 * 
	 * @return Returns whether the expression is a sizeof operator
	 */
	public abstract boolean isSizeOf();

}
