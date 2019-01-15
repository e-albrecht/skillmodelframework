package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an if statement which conditionally executes code. [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax: <br />
 * <code>if ( </code><em>expression</em><code> ) </code><em>statement_true</em><br />
 * <code>if ( </code><em>expression</em><code> ) </code><em>statement_true</em><code> else </code>statement_false
 * @author Ella Albrecht
 *
 */
public class MyIf extends MySelection {

	/**
	 * The condition of the if statement
	 */
	private MyExpression condition;
	
	/**
	 * The body of the then branch of the if statement
	 */
	private MyStatement thenBranch;
	
	/**
	 * The body of the else branch of the if statement
	 */
	private MyStatement elseBranch;

	/* The KCs defined for the named label element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SELECTION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.IF));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates an if statement without an else branch
	 * @param condition The condition of the if statement
	 * @param thenBranch They body of the then branch
	 */
	public MyIf(MyExpression condition, MyStatement thenBranch) {
		this.condition = condition;
		this.thenBranch = thenBranch;
	}
	
	/**
	 * Creates an if statement with an else branch
	 * @param condition The condition of the if statement
	 * @param thenBranch The body of the then branch
	 * @param elseBranch The body of the else branch
	 */
	public MyIf(MyExpression condition, MyStatement thenBranch, MyStatement elseBranch) {
		this(condition, thenBranch);
		this.elseBranch = elseBranch;
	}

	/**
	 * 
	 * @return The condition of the if statement
	 */
	public MyExpression getCondition() {
		return condition;
	}

	/**
	 * 
	 * @param condition The condition which has to be set for the if statement
	 */
	public void setCondition(MyExpression condition) {
		this.condition = condition;
	}

	/**
	 * 
	 * @return The body of the then branch
	 */
	public MyStatement getThenBranch() {
		return thenBranch;
	}

	/**
	 * 
	 * @param thenBranch The body of the then branch to be set for the if statement
	 */
	public void setThenBranch(MyBlock thenBranch) {
		this.thenBranch = thenBranch;
	}

	/**
	 * 
	 * @return The body of the else branch
	 */
	public MyStatement getElseBranch() {
		return elseBranch;
	}

	/**
	 * 
	 * @param elseBranch The body of the else branch to be set for the if condition
	 */
	public void setElseBranch(MyBlock elseBranch) {
		this.elseBranch = elseBranch;
	}
	
	@Override
	public boolean isLabel() {
		return false;
	}

	@Override
	public boolean isIf() {
		return true;
	}

	@Override
	public boolean isSwitch() {
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
				if (level > 2) {
					kcs.addAll(kcs3);
					if (thenBranch != null) kcs.add(KnowledgeComponent.THEN);
					if (elseBranch != null) kcs.add(KnowledgeComponent.ELSE);			
				}
			}		
		}
		// add KCs for subelements
		if (condition != null) kcs.addAll(condition.getKCs(level));
		kcs.addAll(thenBranch.getKCs(level));
		if (elseBranch != null) kcs.addAll(elseBranch.getKCs(level));
		return kcs;
	}
	
	/**
	 * Returns the KCs only for this element, not including those of the sub-elements
	 * @param level The level of the KCs
	 * @return Set of KCs for this element
	 */	
	public Set<KnowledgeComponent> getBasicKCs(int level) {
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
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyIf) {
			MyIf ifS = (MyIf) o;
			return (condition.equals(ifS.getCondition()) &&
					thenBranch.equals(ifS.getThenBranch()) && elseBranch.equals(ifS.getElseBranch()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "IF\n";
		text += "Condition: " + condition + "\n";
		text += "Then: " + thenBranch.toString() + "\n";
		text += "Else: " + elseBranch.toString() + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		int hash = condition.hashCode() + thenBranch.hashCode();
		if (elseBranch != null) hash += elseBranch.hashCode();
		return hash;
	}

}
