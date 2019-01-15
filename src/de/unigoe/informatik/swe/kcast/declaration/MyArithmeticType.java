package de.unigoe.informatik.swe.kcast.declaration;

public abstract class MyArithmeticType extends MyTypeSpecifier{

	/**
	 * Returns whether the arithmetic type is a character type
	 */
	public abstract boolean isCharacterType();

	/**
	 * Returns whether the arithmetic type is an integer type
	 */
	public abstract boolean isIntegerType();

	/**
	 * Returns whether the arithmetic type is a floating type
	 */
	public abstract boolean isFloatingType();
	
	@Override
	public boolean isVoidType() {
		return false;
	}

	@Override
	public boolean isArithmeticType() {
		return true;
	}

	@Override
	public boolean isStruct() {
		return false;
	}

	@Override
	public boolean isUnion() {
		return false;
	}

	@Override
	public boolean isEnum() {
		return false;
	}
}
