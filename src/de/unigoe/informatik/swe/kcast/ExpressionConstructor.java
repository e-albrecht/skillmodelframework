package de.unigoe.informatik.swe.kcast;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTArraySubscriptExpression;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionList;
import org.eclipse.cdt.core.dom.ast.IASTFieldReference;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.c.ICASTArrayDesignator;
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignatedInitializer;
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator;
import org.eclipse.cdt.core.dom.ast.c.ICASTFieldDesignator;

import de.unigoe.informatik.swe.kcast.declaration.MyTypeSpecifier;
import de.unigoe.informatik.swe.kcast.expression.MyAddressOf;
import de.unigoe.informatik.swe.kcast.expression.MyArithmeticBinary;
import de.unigoe.informatik.swe.kcast.expression.MyArithmeticUnary;
import de.unigoe.informatik.swe.kcast.expression.MyArraySubscript;
import de.unigoe.informatik.swe.kcast.expression.MyAssignment;
import de.unigoe.informatik.swe.kcast.expression.MyCharacterConstant;
import de.unigoe.informatik.swe.kcast.expression.MyComparison;
import de.unigoe.informatik.swe.kcast.expression.MyConditionalOperator;
import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.kcast.expression.MyExpressionList;
import de.unigoe.informatik.swe.kcast.expression.MyFloatingConstant;
import de.unigoe.informatik.swe.kcast.expression.MyFunctionCall;
import de.unigoe.informatik.swe.kcast.expression.MyIdExpression;
import de.unigoe.informatik.swe.kcast.expression.MyIncDecrement;
import de.unigoe.informatik.swe.kcast.expression.MyIntegerConstant;
import de.unigoe.informatik.swe.kcast.expression.MyLogical;
import de.unigoe.informatik.swe.kcast.expression.MyMemberAccessDot;
import de.unigoe.informatik.swe.kcast.expression.MyMemberAccessPointer;
import de.unigoe.informatik.swe.kcast.expression.MyPointerDereference;
import de.unigoe.informatik.swe.kcast.expression.MyPredefinedConstant;
import de.unigoe.informatik.swe.kcast.expression.MySizeOfExpression;
import de.unigoe.informatik.swe.kcast.expression.MySizeOfType;
import de.unigoe.informatik.swe.kcast.expression.MyStringLiteral;
import de.unigoe.informatik.swe.kcast.expression.MyTypeCast;
import de.unigoe.informatik.swe.kcast.expression.MyArithmeticBinary.ArithmeticBinaryType;
import de.unigoe.informatik.swe.kcast.expression.MyArithmeticUnary.ArithmeticUnaryType;
import de.unigoe.informatik.swe.kcast.expression.MyAssignment.AssignmentType;
import de.unigoe.informatik.swe.kcast.expression.MyCharacterConstant.CharacterConstantType;
import de.unigoe.informatik.swe.kcast.expression.MyComparison.ComparisonType;
import de.unigoe.informatik.swe.kcast.expression.MyFloatingConstant.FloatingConstantType;
import de.unigoe.informatik.swe.kcast.expression.MyFloatingConstant.FloatingType;
import de.unigoe.informatik.swe.kcast.expression.MyIncDecrement.IncDecrementType;
import de.unigoe.informatik.swe.kcast.expression.MyIntegerConstant.IntegerConstantType;
import de.unigoe.informatik.swe.kcast.expression.MyLogical.LogicalType;
import de.unigoe.informatik.swe.kcast.expression.MyPredefinedConstant.PredefinedConstantType;
import de.unigoe.informatik.swe.kcast.initialisation.MyArrayDesignator;
import de.unigoe.informatik.swe.kcast.initialisation.MyDesignatedInitializer;
import de.unigoe.informatik.swe.kcast.initialisation.MyDesignator;
import de.unigoe.informatik.swe.kcast.initialisation.MyFieldDesignator;
import de.unigoe.informatik.swe.kcast.initialisation.MyInitClause;
import de.unigoe.informatik.swe.kcast.initialisation.MyInitialisationList;

public class ExpressionConstructor {

	public static MyExpression construct(IASTExpression expr) {
		if (expr instanceof IASTArraySubscriptExpression) return constructArraySubscript((IASTArraySubscriptExpression) expr);
		if (expr instanceof IASTExpressionList) return constructExpressionList((IASTExpressionList) expr);
		if (expr instanceof IASTBinaryExpression) return constructBinaryExpression((IASTBinaryExpression) expr);
		if (expr instanceof IASTCastExpression) return constructTypeCast((IASTCastExpression) expr);
		if (expr instanceof IASTConditionalExpression) return constructConditional((IASTConditionalExpression) expr);
		if (expr instanceof IASTFieldReference) return constructFieldReference((IASTFieldReference) expr);
		if (expr instanceof IASTFunctionCallExpression) return constructFunctionCall((IASTFunctionCallExpression) expr);
		if (expr instanceof IASTIdExpression) return constructId((IASTIdExpression) expr);
		if (expr instanceof IASTLiteralExpression) return constructLiteral((IASTLiteralExpression) expr);
		if (expr instanceof IASTTypeIdExpression) return constructSizeOfType((IASTTypeIdExpression) expr);
		if (expr instanceof IASTUnaryExpression) return constructUnaryExpression((IASTUnaryExpression) expr);

		return null;
	}
	
	private static MyExpression constructExpressionList(IASTExpressionList exprList) {
		MyExpressionList myExprList = new MyExpressionList();
		for (IASTExpression expr : exprList.getExpressions()) {
			myExprList.addExpression(construct(expr));
		}
		return myExprList;
	}

	private static MyExpression constructUnaryExpression(IASTUnaryExpression expr) {
		MyExpression operand = construct(expr.getOperand());
		switch(expr.getOperator()) {
		case IASTUnaryExpression.op_amper: return new MyAddressOf(operand);
		case IASTUnaryExpression.op_bracketedPrimary: return operand;
		case IASTUnaryExpression.op_minus: return new MyArithmeticUnary(ArithmeticUnaryType.UNARY_MINUS, operand);
		case IASTUnaryExpression.op_plus: return new MyArithmeticUnary(ArithmeticUnaryType.UNARY_PLUS, operand);
		case IASTUnaryExpression.op_not: return new MyLogical(LogicalType.LOGICAL_NOT, operand,null);	
		case IASTUnaryExpression.op_postFixDecr: return new MyIncDecrement(IncDecrementType.POSTFIX_DECREMENT, operand);
		case IASTUnaryExpression.op_postFixIncr: return new MyIncDecrement(IncDecrementType.POSTFIX_INCREMENT, operand);
		case IASTUnaryExpression.op_prefixDecr: return new MyIncDecrement(IncDecrementType.PREFIX_DECREMENT, operand);
		case IASTUnaryExpression.op_prefixIncr: return new MyIncDecrement(IncDecrementType.PREFIX_INCREMENT, operand);	
		case IASTUnaryExpression.op_sizeof: return new MySizeOfExpression(operand);
		case IASTUnaryExpression.op_star: return new MyPointerDereference(operand);
		case IASTUnaryExpression.op_tilde: return new MyArithmeticUnary(ArithmeticUnaryType.BITWISE_NOT, operand);			
		}
		return null;
	}

	private static MySizeOfType constructSizeOfType(IASTTypeIdExpression expr) {
		return new MySizeOfType(DeclarationConstructor.getTypeSpecifier(expr.getTypeId().getDeclSpecifier()));
	}

	private static MyExpression constructLiteral(IASTLiteralExpression expr) {
		switch(expr.getKind()) {
		case IASTLiteralExpression.lk_char_constant: return getCharacterConstant(expr.getValue());
		case IASTLiteralExpression.lk_false: return new MyPredefinedConstant(PredefinedConstantType.FALSE);
		case IASTLiteralExpression.lk_float_constant: return getFloatConstant(expr.getValue());
		case IASTLiteralExpression.lk_integer_constant: return getIntegerConstant(expr.getValue());
		case IASTLiteralExpression.lk_nullptr: return new MyPredefinedConstant(PredefinedConstantType.NULL);
		case IASTLiteralExpression.lk_string_literal: return new MyStringLiteral(String.valueOf(expr.getValue()));
		case IASTLiteralExpression.lk_true: return new MyPredefinedConstant(PredefinedConstantType.TRUE);
		}
		return null;
	}
	
	private static MyIntegerConstant getIntegerConstant(char[] value) {
		IntegerConstantType type;
		// get type
		if (value.length>2 && value[0]==0 && (value[1]=='x' || value[1]=='X')){ // is hex number
			type = IntegerConstantType.HEX;
			value = extractArray(2,value.length-1,value);
		} else if (value.length > 1 && value[0] == '0') { // is oct number
			type = IntegerConstantType.OCTAL;
			value = extractArray(1,value.length-1,value);
		}
		else type = IntegerConstantType.DECIMAL;
		//get suffix
		String s = new String(value);
		boolean u = false;
		boolean l = false;
		boolean ll = false;
		if (s.contains("u") || s.contains("U")) u = true;
		if (s.contains("ll")) ll = true;
		else if (s.contains("l")) l = true;
		s.replace("u", "");
		s.replace("l", "");
		return new MyIntegerConstant(type, s, u, l, ll);
	}

	private static MyFloatingConstant getFloatConstant(char[] value) {
		FloatingConstantType type;
		// get type
		if (value.length>2 && value[0]==0 && (value[1]=='x' || value[1]=='X')){ // is hex number
			type = FloatingConstantType.HEX;
			value = extractArray(2,value.length-1,value);
		}
		else type = FloatingConstantType.DEC;
		//get size type
		FloatingType sizeType;
		switch (value[value.length-1]){
		case 'f': sizeType = FloatingType.FLOAT; value = extractArray(0,value.length-2,value); break;
		case 'F': sizeType = FloatingType.FLOAT; value = extractArray(0,value.length-2,value); break;
		case 'l': sizeType = FloatingType.LONG_DOUBLE; value = extractArray(0,value.length-2,value); break;
		case 'L': sizeType = FloatingType.LONG_DOUBLE; value = extractArray(0,value.length-2,value); break;
		default: sizeType = FloatingType.DOUBLE;
		}
		
		//get significand and exponent
		String significand = new String(value);
		int exponent = 1;
		if (significand.contains("e") || significand.contains("E") || significand.contains("p") || significand.contains("P")) { // there is an exponent
			String[] parts = null;
			if (significand.contains("e")) parts = significand.split("e");
			else if (significand.contains("E")) parts = significand.split("E");
			else if (significand.contains("p")) parts = significand.split("e");
			else if (significand.contains("P")) parts = significand.split("P");
			significand = parts[0];
			exponent = Integer.parseInt(parts[1]);					
		}
		return new MyFloatingConstant(type, sizeType, Float.parseFloat(significand), exponent);
	}

	private static MyCharacterConstant getCharacterConstant(char[] arr) {
		switch(arr[0]) {
		case 'u': return new MyCharacterConstant(new String(extractArray(2,arr.length-2,arr)), CharacterConstantType.SIXTEEN_BIT);
		case 'U': return new MyCharacterConstant(new String(extractArray(2,arr.length-2,arr)), CharacterConstantType.THIRTYTWO_BIT);
		case 'L': return new MyCharacterConstant(new String(extractArray(2,arr.length-2,arr)), CharacterConstantType.WIDE);
		default: return new MyCharacterConstant(new String(extractArray(1,arr.length-2,arr)), CharacterConstantType.SINGLE_BYTE);
		}
	}
	
	private static char[] extractArray(int start, int end, char[] arr) {
		char[] newArr = new char[end-start+1];
		for (int i = start, j=0; i <= end; i++,j++) {
			newArr[j] = arr[i];
		}
		return newArr;
	}

	private static MyIdExpression constructId(IASTIdExpression expr) {
		return new MyIdExpression(expr.getName().toString(),true);
	}

	private static MyFunctionCall constructFunctionCall(IASTFunctionCallExpression expr) {
		MyExpression functionName = construct(expr.getFunctionNameExpression());
		if (functionName instanceof MyIdExpression) ((MyIdExpression)functionName).setVariable(false);
		List<MyInitClause> arguments = new LinkedList<MyInitClause>();
		for (IASTInitializerClause arg : expr.getArguments()){
			arguments.add(constructInitializerClause(arg));
		}
		return new MyFunctionCall(functionName, arguments);
	}

	private static MyExpression constructFieldReference(IASTFieldReference expr) {
		String member = expr.getFieldName().toString();
		MyExpression operand = construct(expr.getFieldOwner());
		if (expr.isPointerDereference()) return new MyMemberAccessPointer(operand, member);
		else return new MyMemberAccessDot(operand, member);
	}

	private static MyConditionalOperator constructConditional(IASTConditionalExpression expr) {
		MyExpression condition = construct(expr.getLogicalConditionExpression());
		MyExpression negative = construct(expr.getNegativeResultExpression());
		MyExpression positive = construct(expr.getPositiveResultExpression());
		return new MyConditionalOperator(condition, positive, negative);
	}
	
	private static MyTypeCast constructTypeCast(IASTCastExpression expr) {
		MyExpression operand = construct(expr.getOperand());
		MyTypeSpecifier type = DeclarationConstructor.getTypeSpecifier(expr.getTypeId().getDeclSpecifier());
		return new MyTypeCast(type, operand);
	}
	
	private static MyExpression constructBinaryExpression(IASTBinaryExpression expr) {
		MyExpression op1 = ExpressionConstructor.construct(expr.getOperand1());
		MyExpression op2 = ExpressionConstructor.construct(expr.getOperand2());
		switch(expr.getOperator()) {
		case IASTBinaryExpression.op_assign: return new MyAssignment(AssignmentType.BASIC_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_binaryAnd: return new MyArithmeticBinary(ArithmeticBinaryType.BITWISE_AND,op1,op2);
		case IASTBinaryExpression.op_binaryAndAssign: return new MyAssignment(AssignmentType.BITWISE_AND_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_binaryOr: return new MyArithmeticBinary(ArithmeticBinaryType.BITWISE_OR,op1,op2);
		case IASTBinaryExpression.op_binaryOrAssign: return new MyAssignment(AssignmentType.BITWISE_OR_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_binaryXor: return new MyArithmeticBinary(ArithmeticBinaryType.BITWISE_XOR,op1,op2);
		case IASTBinaryExpression.op_binaryXorAssign: return new MyAssignment(AssignmentType.BITWISE_XOR_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_divide: return new MyArithmeticBinary(ArithmeticBinaryType.DIVISION,op1,op2);
		case IASTBinaryExpression.op_divideAssign: return new MyAssignment(AssignmentType.DIVISION_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_equals: return new MyComparison(ComparisonType.EQUAL_TO,op1,op2);
		case IASTBinaryExpression.op_greaterEqual:return new MyComparison(ComparisonType.GREATER_THAN_OR_EQUAL_TO,op1,op2);
		case IASTBinaryExpression.op_greaterThan: return new MyComparison(ComparisonType.GREATER_THAN,op1,op2);
		case IASTBinaryExpression.op_lessThan: return new MyComparison(ComparisonType.LESS_THAN,op1,op2);
		case IASTBinaryExpression.op_lessEqual: return new MyComparison(ComparisonType.LESS_THAN_OR_EQUAL_TO,op1,op2);
		case IASTBinaryExpression.op_logicalAnd: return new MyLogical(LogicalType.LOGICAL_AND,op1,op2);
		case IASTBinaryExpression.op_logicalOr: return new MyLogical(LogicalType.LOGICAL_OR,op1,op2);
		case IASTBinaryExpression.op_minus: return new MyArithmeticBinary(ArithmeticBinaryType.SUBTRACTION,op1,op2);
		case IASTBinaryExpression.op_minusAssign: return new MyAssignment(AssignmentType.SUBTRACTION_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_modulo: return new MyArithmeticBinary(ArithmeticBinaryType.MODULO,op1,op2);
		case IASTBinaryExpression.op_moduloAssign: return new MyAssignment(AssignmentType.MODULO_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_multiply: return new MyArithmeticBinary(ArithmeticBinaryType.PRODUCT,op1,op2);
		case IASTBinaryExpression.op_multiplyAssign: return new MyAssignment(AssignmentType.MULTIPLICATION_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_notequals: return new MyComparison(ComparisonType.NOT_EQUAL_TO,op1,op2);
		case IASTBinaryExpression.op_plus: return new MyArithmeticBinary(ArithmeticBinaryType.ADDITION,op1,op2);
		case IASTBinaryExpression.op_plusAssign: return new MyAssignment(AssignmentType.ADDITION_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_shiftLeft: return new MyArithmeticBinary(ArithmeticBinaryType.BITWISE_LEFT_SHIFT,op1,op2);
		case IASTBinaryExpression.op_shiftLeftAssign: return new MyAssignment(AssignmentType.BITWISE_LEFT_SHIFT_ASSIGNMENT,op1,op2);
		case IASTBinaryExpression.op_shiftRight: return new MyArithmeticBinary(ArithmeticBinaryType.BITWISE_RIGHT_SHIFT,op1,op2);
		case IASTBinaryExpression.op_shiftRightAssign: return new MyAssignment(AssignmentType.BITWISE_RIGHT_SHIFT_ASSIGNMENT,op1,op2);
		}
		return null;
	}

	public static MyInitClause constructInitializerClause(IASTInitializerClause expr) {
		if (expr instanceof IASTArraySubscriptExpression) return constructArraySubscript((IASTArraySubscriptExpression) expr);
		if (expr instanceof IASTBinaryExpression) return constructBinaryExpression((IASTBinaryExpression) expr);
		if (expr instanceof IASTCastExpression) return constructTypeCast((IASTCastExpression) expr);
		if (expr instanceof IASTExpressionList) return constructExpressionList((IASTExpressionList) expr);
		if (expr instanceof IASTInitializerList) return constructInitializerList((IASTInitializerList) expr);
		if (expr instanceof IASTConditionalExpression) return constructConditional((IASTConditionalExpression) expr);
		if (expr instanceof IASTFieldReference) return constructFieldReference((IASTFieldReference) expr);
		if (expr instanceof IASTFunctionCallExpression) return constructFunctionCall((IASTFunctionCallExpression) expr);
		if (expr instanceof IASTIdExpression) return constructId((IASTIdExpression) expr);
		if (expr instanceof IASTLiteralExpression) return constructLiteral((IASTLiteralExpression) expr);
		if (expr instanceof IASTTypeIdExpression) return constructSizeOfType((IASTTypeIdExpression) expr);
		if (expr instanceof IASTUnaryExpression) return constructUnaryExpression((IASTUnaryExpression) expr);
		if (expr instanceof ICASTDesignatedInitializer) return constructDesignatedInitializer((ICASTDesignatedInitializer) expr);

		return null;
	}
	
	private static MyDesignatedInitializer constructDesignatedInitializer(
			ICASTDesignatedInitializer expr) {
		MyDesignatedInitializer init = new MyDesignatedInitializer(constructInitializerClause(expr.getOperand()));
		for (ICASTDesignator desig : expr.getDesignators()) {
			init.addDesignator(constructDesignator(desig));
		}
		return init;
	}

	private static MyDesignator constructDesignator(ICASTDesignator designator) {
		if (designator instanceof ICASTArrayDesignator) return constructArrayDesignator((ICASTArrayDesignator) designator);
		if (designator instanceof ICASTFieldDesignator) return constructFieldDesignator((ICASTFieldDesignator) designator);
		return null;
	}

	private static MyFieldDesignator constructFieldDesignator(
			ICASTFieldDesignator designator) {
		return new MyFieldDesignator(designator.getName().toString());
	}

	private static MyArrayDesignator constructArrayDesignator(
			ICASTArrayDesignator designator) {
		return new MyArrayDesignator(construct(designator.getSubscriptExpression()));
	}

	private static MyInitialisationList constructInitializerList(
			IASTInitializerList initList) {
		MyInitialisationList myExprList = new MyInitialisationList();
		for (IASTInitializerClause expr : initList.getClauses()) {
			myExprList.addInitClause(constructInitializerClause(expr));
		}
		return myExprList;
	}

	private static MyArraySubscript constructArraySubscript(IASTArraySubscriptExpression expr) {
		MyExpression arr = construct(expr.getArrayExpression());
		MyInitClause element = constructInitializerClause(expr.getArgument());
		return new MyArraySubscript(arr,element);
	}
}
