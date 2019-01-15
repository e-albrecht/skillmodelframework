package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Set;

import de.unigoe.informatik.swe.kcast.MyNode;
import de.unigoe.informatik.swe.kcast.initialisation.MyInitialisation;
import de.unigoe.informatik.swe.kcast.statement.MyBlock;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a declarator
 * @author Ella Albrecht
 *
 */
public abstract class MyDeclarator extends MyNode{
	
	/**
	 * 
	 * @return Returns whether the declarator is an identifier
	 */
	public abstract boolean isIdentifier();
	
	/**
	 * 
	 * @return Returns whether the declarator is a pointer
	 */
	public abstract boolean isPointer();
	
	/**
	 * 
	 * @return Returns whether the declarator is an array
	 */
	public abstract boolean isArray();
	
	/**
	 * 
	 * @return Returns whether the declarator is a function
	 */
	public abstract boolean isFunction();
	
	/**
	 * 
	 * @return Returns whether the declarator contains a function declaration
	 */
	public abstract boolean containsFunction();
	

	/**
	 * 
	 * @param level Level of KCs
	 * @return Returns set of KCs for the root of a function node in a KAM
	 */
	public abstract Set<KnowledgeComponent> getFunctionRootKCs(int level);
	
	/**
	 * 
	 * @return Returns the initialization of the declarator
	 */
	public abstract MyInitialisation getInit();
	
	/**
	 * 
	 * @param init Initialization of the declarator
	 */
	public abstract void setInit(MyInitialisation init);
	
	/**
	 * 
	 * @return Returns the body of a function if the declarator contains one
	 */
	public abstract MyBlock getFunctionBody();
	
}
