package de.unigoe.informatik.swe.krm;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public enum KnowledgeComponent implements Serializable{
	PREPROCESSOR, DEFINE, INCLUDE,
	DECLARATION, DATA_TYPE, VOID, ARITHMETIC_TYPE, CHARACTER, INTEGER, FLOAT, SIGNED, UNSIGNED, SHORT, INT, LONG, LONG_LONG, DOUBLE, LONG_DOUBLE,
	ELABORATED_TYPE_SPECIFIER, STRUCT, UNION, ENUM,
	STORAGE_CLASS, TYPEDEF, AUTO, REGISTER, STATIC, EXTERN, THREAD_LOCAL,
	TYPE_QUALIFIER, CONST, VOLATILE, RESTRICT, ATOMIC,
	DECLARATOR, VARIABLE, POINTER, ARRAY, FUNCTION, IDENTIFIER, TYPEDEFNAME,
	STATEMENT, BLOCK, SELECTION, IF, THEN, ELSE, SWITCH, CASE, DEFAULT,
	ITERATION, WHILE, FOR, DO_WHILE, JUMP, BREAK, CONTINUE, RETURN, GOTO, LABEL,
	EXPRESSION, PRIMARY_EXPRESSION, FLOATING_CONSTANT, INTEGER_CONSTANT, OCT, HEX, CHARACTER_CONSTANT, SINGLE_BYTE, SIXTEEN_BIT, THIRTYTWO_BIT, WIDE,
	STRING_LITERAL, PREDEFINED_CONSTANT, FALSE, TRUE, NULL,
	ASSIGNMENT, BASIC_ASSIGNMENT, ADD_ASSIGNMENT, SUB_ASSIGNMENT, MULT_ASSIGNMENT, DIV_ASSIGNMENT, MOD_ASSIGNMENT, BITWISE_OR_ASSIGNMENT, BITWISE_XOR_ASSIGNMENT, BITWISE_AND_ASSIGNMENT, BITWISE_LEFT_SHIFT_ASSIGNMENT, BITWISE_RIGHT_SHIFT_ASSIGNMENT,
	ARITHMETIC_EXPRESSION, UNARY_ARITHMETIC_EXPRESSION, PLUS, MINUS, BITWISE_NOT, INCREMENT, PRE_INCREMENT, POST_INCREMENT, DECREMENT, PRE_DECREMENT, POST_DECREMENT,
	BINARY_ARITHMETIC_EXPRESSION, ADDITION, SUBTRACTION, PRODUCT, DIVISION, MODULO, BITWISE_AND, BITWISE_OR, BITWISE_XOR, BITWISE_LEFT_SHIFT, BITWISE_RIGHT_SHIFT,
	LOGICAL_EXPRESSION, AND, OR, NOT,
	COMPARISON, EQUALS, NOT_EQUALS, LESS, GREATER, LESS_EQUALS, GREATER_EQUALS,
	MEMBER_ACCESS, ADDRESS_OF, ARRAY_SUBSCRIPT, DOT_OPERATOR, POINTER_OPERATOR, POINTER_DEREFERNCE,
	FUNCTION_CALL, COMMA_OPERATOR, TYPE_CAST, CONDITIONAL_OPERATOR, SIZE_OF, SIZE_OF_EXPRESSION, SIZE_OF_TYPE, 
	BITWISE_OPERATION, SHIFT, PREFIX, POSTFIX,  INITIALIZATION, EQUALS_INITIALIZATION, INITIALIZATION_LIST, EXPRESSION_LIST, DESIGNATOR, ARRAY_DESIGNATOR, FIELD_DESIGNATOR, DESIGNATED_INITIALIZER, INCREMENT_DECREMENT;
	
	public static Set<KnowledgeComponent> getKCs(int level) {
		switch(level) {
		case 0: return getLevel0KCs();
		case 1: return getLevel1KCs();
		case 2: return getLevel2KCs();
		case 3: return getLevel3KCs();	
		}
		return null;
	}

	private static Set<KnowledgeComponent> getLevel3KCs() {
		Set<KnowledgeComponent> kcs = new LinkedHashSet<KnowledgeComponent>();
		kcs.add(CASE);
		kcs.add(DEFAULT);
		kcs.add(FIELD_DESIGNATOR);
		kcs.add(ARRAY_DESIGNATOR);
		kcs.add(PRE_INCREMENT);
		kcs.add(POST_INCREMENT);
		kcs.add(PRE_DECREMENT);
		kcs.add(POST_DECREMENT);
		kcs.add(BASIC_ASSIGNMENT);
		kcs.add(MOD_ASSIGNMENT);
		kcs.add(DIV_ASSIGNMENT);
		kcs.add(MULT_ASSIGNMENT);
		kcs.add(SUB_ASSIGNMENT);
		kcs.add(ADD_ASSIGNMENT);
		kcs.add(BITWISE_RIGHT_SHIFT_ASSIGNMENT);
		kcs.add(BITWISE_XOR_ASSIGNMENT);
		kcs.add(BITWISE_AND_ASSIGNMENT);
		kcs.add(BITWISE_OR_ASSIGNMENT);
		kcs.add(BITWISE_LEFT_SHIFT_ASSIGNMENT);
		kcs.add(BITWISE_LEFT_SHIFT);
		kcs.add(BITWISE_RIGHT_SHIFT);
		kcs.add(BITWISE_OR);
		kcs.add(BITWISE_XOR);
		kcs.add(BITWISE_AND);
		kcs.add(BITWISE_NOT);
		kcs.add(FALSE);
		kcs.add(TRUE);
		kcs.add(NULL);
		kcs.add(WIDE);
		kcs.add(THIRTYTWO_BIT);
		kcs.add(SIXTEEN_BIT);
		kcs.add(SINGLE_BYTE);
		kcs.add(HEX);
		kcs.add(OCT);
		kcs.add(DOUBLE);
		kcs.add(LONG_DOUBLE);
		kcs.add(LONG_LONG);
		kcs.add(LONG);
		kcs.add(UNSIGNED);
		kcs.add(INT);
		kcs.add(SHORT);
		kcs.add(SIGNED);
		kcs.add(THEN);
		kcs.add(ELSE);
		return kcs;
	}

	private static Set<KnowledgeComponent> getLevel2KCs() {
		Set<KnowledgeComponent> kcs = new LinkedHashSet<KnowledgeComponent>();
		kcs.add(BREAK);
		kcs.add(RETURN);
		kcs.add(CONTINUE);
		kcs.add(GOTO);	
		kcs.add(SWITCH);
		kcs.add(IF);
		kcs.add(DO_WHILE);
		kcs.add(FOR);
		kcs.add(WHILE);
		kcs.add(DESIGNATED_INITIALIZER);
		kcs.add(DESIGNATOR);
		kcs.add(INITIALIZATION_LIST);
		kcs.add(EQUALS_INITIALIZATION);
		kcs.add(INCREMENT);
		kcs.add(PREFIX);
		kcs.add(POSTFIX);
		kcs.add(DECREMENT);
		kcs.add(POINTER_OPERATOR);
		kcs.add(DOT_OPERATOR);
		kcs.add(ARRAY_SUBSCRIPT);
		kcs.add(ADDRESS_OF);
		kcs.add(POINTER_DEREFERNCE);
		kcs.add(SHIFT);
		kcs.add(LESS_EQUALS);
		kcs.add(GREATER);
		kcs.add(EQUALS);
		kcs.add(LESS);
		kcs.add(NOT_EQUALS);
		kcs.add(GREATER_EQUALS);
		kcs.add(BINARY_ARITHMETIC_EXPRESSION);
		kcs.add(PRODUCT);
		kcs.add(ADDITION);
		kcs.add(SUBTRACTION);
		kcs.add(MODULO);
		kcs.add(DIVISION);
		kcs.add(UNARY_ARITHMETIC_EXPRESSION);
		kcs.add(MINUS);
		kcs.add(PLUS);
		kcs.add(AND);
		kcs.add(NOT);
		kcs.add(OR);
		kcs.add(PREDEFINED_CONSTANT);
		kcs.add(CHARACTER_CONSTANT);
		kcs.add(STRING_LITERAL);
		kcs.add(FLOATING_CONSTANT);
		kcs.add(INTEGER_CONSTANT);
		kcs.add(IDENTIFIER);
		kcs.add(VARIABLE);
		kcs.add(CONST);
		kcs.add(RESTRICT);
		kcs.add(VOLATILE);
		kcs.add(ATOMIC);
		kcs.add(VOID);
		kcs.add(ELABORATED_TYPE_SPECIFIER);
		kcs.add(ENUM);
		kcs.add(STRUCT);
		kcs.add(UNION);
		kcs.add(ARITHMETIC_TYPE);
		kcs.add(FLOAT);
		kcs.add(INTEGER);
		kcs.add(CHARACTER);
		kcs.add(POINTER);
		kcs.add(FUNCTION);
		kcs.add(ARRAY);
		kcs.add(TYPEDEFNAME);
		kcs.add(AUTO);
		kcs.add(EXTERN);
		kcs.add(REGISTER);
		kcs.add(STATIC);
		kcs.add(THREAD_LOCAL);
		kcs.add(TYPEDEF);
		kcs.add(SIZE_OF_EXPRESSION);
		kcs.add(SIZE_OF_TYPE);
		return kcs;
	}

	private static Set<KnowledgeComponent> getLevel1KCs() {
		Set<KnowledgeComponent> kcs = new LinkedHashSet<KnowledgeComponent>();
		kcs.add(CONDITIONAL_OPERATOR);
		kcs.add(COMMA_OPERATOR);
		kcs.add(EXPRESSION_LIST);
		kcs.add(INCREMENT_DECREMENT);
		kcs.add(BITWISE_OPERATION);
		kcs.add(JUMP);
		kcs.add(SELECTION);
		kcs.add(LABEL);
		kcs.add(ITERATION);
		kcs.add(BLOCK);
		kcs.add(INITIALIZATION);
		kcs.add(ASSIGNMENT);
		kcs.add(MEMBER_ACCESS);
		kcs.add(SIZE_OF);
		kcs.add(COMPARISON);
		kcs.add(ARITHMETIC_EXPRESSION);
		kcs.add(LOGICAL_EXPRESSION);
		kcs.add(TYPE_CAST);
		kcs.add(FUNCTION_CALL);
		kcs.add(PRIMARY_EXPRESSION);
		kcs.add(TYPE_QUALIFIER);
		kcs.add(STORAGE_CLASS);
		kcs.add(DATA_TYPE);
		kcs.add(DECLARATOR);
		kcs.add(DEFINE);
		kcs.add(INCLUDE);
		return kcs;
	}

	private static Set<KnowledgeComponent> getLevel0KCs() {
		Set<KnowledgeComponent> kcs = new LinkedHashSet<KnowledgeComponent>();
		kcs.add(STATEMENT);
		kcs.add(EXPRESSION);
		kcs.add(DECLARATION);
		kcs.add(PREPROCESSOR);
		return kcs;
	}
}
