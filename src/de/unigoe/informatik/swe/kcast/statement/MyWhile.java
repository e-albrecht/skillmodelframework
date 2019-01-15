package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a while or do-while loop. <br />
 * Syntax [<a href="cppreference.com">cppreference.com</a>]:<br />
 * <code>while ( </code><em>expression</em><code> ) </code><em>statement</em><br />
 * <code>do </code><em>statement</em><code> while ( </code><em>expression</em><code> ) ;</code><br />
 * @author Ella Albrcht
 *
 */
public class MyWhile extends MyIteration {

	public enum WhileType {
		WHILE, DO_WHILE
	}
	
	/**
	 * Indicated whether the loop is a while or a do-while loop
	 */
	private WhileType type;
	
	/**
	 * The looping condition
	 */
	private MyExpression condition;
	
	/**
	 * The body of the loop
	 */
	private MyStatement block;

	/* The KCs defined for the while element on different levels */		
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ITERATION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new while statement
	 * @param type The type of the while statement, i.e., while or do-while
	 * @param condition The looping condition
	 * @param block The body of the while statement
	 */
	public MyWhile(WhileType type, MyExpression condition, MyStatement block) {
		this.type = type;
		this.condition = condition;
		this.block = block;
	}

	/**
	 * 
	 * @return The type of the while statement, i.e., while or do-while
	 */
	public WhileType getType() {
		return type;
	}

	/**
	 * 
	 * @param type The type of the while statement, i.e., while or do-while
	 */
	public void setType(WhileType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return the looping condition
	 */
	public MyExpression getCondition() {
		return condition;
	}

	/**
	 * 
	 * @param condition The lloping conndition to be set for the while statement
	 */
	public void setCondition(MyExpression condition) {
		this.condition = condition;
	}

	/**
	 * 
	 * @return The body of the while statement
	 */
	public MyStatement getBlock() {
		return block;
	}

	/**
	 * 
	 * @param block The body to be set for the while statement
	 */
	public void setBlock(MyStatement block) {
		this.block = block;
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
				if (type.equals(WhileType.WHILE)) kcs.add(KnowledgeComponent.WHILE);
				if (type.equals(WhileType.DO_WHILE)) kcs.add(KnowledgeComponent.DO_WHILE);				
				if (level > 2){ kcs.addAll(kcs3);
				}
			}
		}
		// add KCs for subelements
		if (condition != null) kcs.addAll(condition.getKCs(level));
		kcs.addAll(block.getKCs(level));
		
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
				if (type.equals(WhileType.WHILE)) kcs.add(KnowledgeComponent.WHILE);
				if (type.equals(WhileType.DO_WHILE)) kcs.add(KnowledgeComponent.DO_WHILE);				
				if (level > 2){ kcs.addAll(kcs3);
				}
			}
		}
		return kcs;
	}
	
	@Override
	public boolean isWhile() {
		return true;
	}

	@Override
	public boolean isFor() {
		return false;
	}	
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyWhile) {
			MyWhile whileS = (MyWhile) o;
			return (type.equals(whileS.getType()) && condition.equals(whileS.getCondition()) &&
					 block.equals(whileS.getBlock()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "";
		if (type.equals(WhileType.WHILE)) text += "WHILE\n";
		else text += "DO WHILE";
		text += "Condition: " + condition + "\n";
		text += "Body: " + block + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() + condition.hashCode() + block.hashCode();
	}
}
