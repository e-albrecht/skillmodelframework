package de.unigoe.informatik.swe.kcast.declaration;

import de.unigoe.informatik.swe.kcast.MyNode;

/**
 * Represents a type specifier
 * @author Ella Albrecht
 *
 */
public abstract class MyTypeSpecifier extends MyNode{ 
	
	public abstract boolean isVoidType();
	public abstract boolean isArithmeticType();
	public abstract boolean isCharacterType();
	public abstract boolean isIntegerType();
	public abstract boolean isFloatingType();
	public abstract boolean isStruct();
	public abstract boolean isUnion();
	public abstract boolean isEnum();	
}