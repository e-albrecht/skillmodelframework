package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a switch statement, which executes code according to the value of an integral argument. [<a href="cppreference.com">cppreference.com</a>]<br /<br />
 * Syntax: <code>switch ( </code><em>expression</em><code> ) </code><em>statement</em>
 * @author Ella Albrecht
 *
 */
public class MySwitch extends MySelection{

	/**
	 * The integral value over which it is decided which statement to execute
	 */
	private MyExpression switchExpression;
	
	/**
	 * The body of the switch statement
	 */
	private MyStatement block;

	/* The KCs defined for the switch element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SELECTION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.SWITCH));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new switch statement
	 * @param switchExpression The expression over which the statement to be executed is selected
	 * @param myStatement The body of the switch statement
	 */
	public MySwitch(MyExpression switchExpression, MyStatement myStatement) {
		this.switchExpression = switchExpression;
		this.block = myStatement;
	}

	/**
	 * 
	 * @return The expression over which the statement to be executed is selected
	 */
	public MyExpression getSwitchExpression() {
		return switchExpression;
	}

	/**
	 * 
	 * @param switchExpression The expression over which the statement to be executed is selected
	 */
	public void setSwitchExpression(MyExpression switchExpression) {
		this.switchExpression = switchExpression;
	}

	/**
	 * 
	 * @return The body of the switch statement
	 */
	public MyStatement getBlock() {
		return block;
	}

	/**
	 * 
	 * @param block The body of the switch statement
	 */
	public void addStatement(MyStatement block) {
		this.block = block;
	}
	
	@Override
	public boolean isLabel() {
		return false;
	}

	@Override
	public boolean isIf() {
		return false;
	}

	@Override
	public boolean isSwitch() {
		return true;
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
				}
			}		
		}
		// add KCs for subelements
		kcs.addAll(switchExpression.getKCs(level));
		kcs.addAll(block.getKCs(level));
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MySwitch) {
			MySwitch switchS = (MySwitch) o;
			return (switchExpression.equals(switchS.getSwitchExpression()) &&
					block.equals(switchS.getBlock()));
		}
		return false;
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
	public String toString() {
		String text = "SWITCH\n";
		text += "Switch Expression: " + switchExpression.toString() + "\n";
		text += "Body: " + block.toString() + "\n";
		return text;
	}
	
	@Override 
	public int hashCode() {
		return switchExpression.hashCode() + block.hashCode();
	}
}
