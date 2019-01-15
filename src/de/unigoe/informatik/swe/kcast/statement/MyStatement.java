package de.unigoe.informatik.swe.kcast.statement;

import de.unigoe.informatik.swe.kcast.MyNode;

/**
 * Statements are fragments of the C program that are executed in sequence. [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public abstract class MyStatement extends MyNode{

	/**
	 * 
	 * @return Returns whether the statement is a case label
	 */
	public abstract boolean isCaseLabel();
	
	/**
	 * 
	 * @return Returns whether the statement is a block
	 */
	public abstract boolean isBlock();
	
	/**
	 * 
	 * @return Returns whether the statement is a declaration
	 */
	public abstract boolean isDeclaration();
	
	/**
	 * 
	 * @return Returns whether the statement is an expression
	 */
	public abstract boolean isExpression();
	
	/**
	 * 
	 * @return Returns whether the statement is a selection
	 */
	public abstract boolean isSelection();
	
	/**
	 * 
	 * @return Returns whether the statement is an iteration
	 */
	public abstract boolean isIteration();
	
	/**
	 * 
	 * @return Returns whether the statement is a jump
	 */
	public abstract boolean isJump();
	
	/**
	 * 
	 * @return Returns whether the statement is a label
	 */
	public abstract boolean isLabel();
	/**
	 * 
	 * @return Returns whether the statement is an empty statement
	 */
	public abstract boolean isEmpty();

	/**
	 * 
	 * @return Returns whether the statement is a default label
	 */
	public abstract boolean isDefaultLabel();
}
