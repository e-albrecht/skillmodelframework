package de.unigoe.informatik.swe.kcast.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.initialisation.MyInitClause;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a function call<br /><br />
 * Syntax: <em>expression</em><code>(</code><em>argument-list</em> (optional)<code>)</code> [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyFunctionCall extends MyExpression {

	/**
	 * Expression representing the function name
	 */
	private MyExpression functionName;
	
	/**
	 * The arguments of the function call
	 */
	private List<MyInitClause> arguments;
	
	/* The KCs defined for the function call element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.EXPRESSION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.FUNCTION_CALL));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new function call
	 * @param functionName Expression representing the function name 
	 * @param arguments The arguments of the function call
	 */
	public MyFunctionCall(MyExpression functionName, List<MyInitClause> arguments) {
		this.functionName = functionName;
		this.arguments = arguments;
	}
	
	/**
	 * Creates a new function call without arguments
	 * @param functionName Expression representing the function name 
	 */
	public MyFunctionCall(MyExpression functionName) {
		this.functionName = functionName;
		arguments = new ArrayList<MyInitClause>();
	}
	
	/**
	 * 
	 * @return Expression representing the function name 
	 */
	public MyExpression getFunctionName() {
		return functionName;
	}

	/**
	 * 
	 * @param functionName Expression representing the function name 
	 */
	public void setFunctionName(MyExpression functionName) {
		this.functionName = functionName;
	}

	/**
	 * 
	 * @return The arguments of the function call
	 */
	public List<MyInitClause> getArguments() {
		return arguments;
	}

	/**
	 * 
	 * @param arguments The arguments of the function call
	 */
	public void setArguments(List<MyInitClause> arguments) {
		this.arguments = arguments;
	}	
	
	/**
	 * 
	 * @param argument Adds an argument to the list of arguments of the function call
	 */
	public void addArgument(MyExpression argument) {
		arguments.add(argument);
	}
	
	
	@Override
	public boolean isPrimary() {
		return false;
	}

	@Override
	public boolean isAssignment() {
		return false;
	}

	@Override
	public boolean isIncDecrement() {
		return false;
	}

	@Override
	public boolean isArithmetic() {
		return false;
	}

	@Override
	public boolean isLogical() {
		return false;
	}

	@Override
	public boolean isComparison() {
		return false;
	}

	@Override
	public boolean isMemberAccess() {
		return false;
	}

	@Override
	public boolean isFunctionCall() {
		return true;
	}

	@Override
	public boolean isCommaOperator() {
		return false;
	}

	@Override
	public boolean isTypeCast() {
		return false;
	}

	@Override
	public boolean isConditionalOperator() {
		return false;
	}

	@Override
	public boolean isSizeOf() {
		return false;
	}

	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (level > 1) {
				kcs.addAll(kcs2);
				if (level > 2) {
					kcs.addAll(kcs3);
				}
			}		
		}	
		kcs.addAll(functionName.getKCs(level));
		for (MyInitClause arg : arguments) {
			kcs.addAll(arg.getKCs(level));
		}
		return kcs;

	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyFunctionCall) {
			MyFunctionCall fCall = (MyFunctionCall) o;
			return (functionName.equals(fCall.getFunctionName()) && arguments.equals(fCall.getArguments()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "FUNCTION CALL\n";
		text += "Function: " + functionName + "\n";
		text += "Arguments: \n";
		for (MyInitClause arg : arguments) {
			text += arg.toString() + "\n";
		}
		return text;
	}	
}
