package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a return statement, which terminates the current function and returns a specified value to the caller function. [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax:<br />
 * <code>return ;</code><br />
 * <code>return </code><em>expression</em> <code>;</code>
 * @author Ella Albrecht
 *
 */
public class MyReturn extends MyJump {

	/**
	 * The expression that is returned
	 */
	private MyExpression returnExpression;

	/* The KCs defined for the return element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.JUMP));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.RETURN));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates new empty return statement
	 */
	public MyReturn() {};
	
	/**
	 * Creates a return statement that returns an expression
	 * @param returnExpression The expression to be returned
	 */
	public MyReturn(MyExpression returnExpression) {
		this.returnExpression = returnExpression;
	}

	/**
	 * 
	 * @return The expression that is returned by the return statement
	 */
	public MyExpression getReturnExpression() {
		return returnExpression;
	}

	/**
	 * 
	 * @param returnExpression The expression to be set as returned by the return statement
	 */
	public void setReturnExpression(MyExpression returnExpression) {
		this.returnExpression = returnExpression;
	}

	@Override
	public boolean isBreak() {
		return false;
	}

	@Override
	public boolean isContinue() {
		return false;
	}

	@Override
	public boolean isReturn() {
		return true;
	}

	@Override
	public boolean isGoto() {
		return false;
	}
	
	@Override
	public boolean isEmpty() {
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
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		if (returnExpression != null) kcs.addAll(returnExpression.getKCs(level));
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyReturn) {
			MyReturn returnS = (MyReturn) o;
			return (returnExpression.equals(returnS.getReturnExpression()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "RETURN\n";
		text += "Return Value: " + returnExpression + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		if (returnExpression != null) return returnExpression.hashCode() + 1;
		return super.hashCode();
	}

}
