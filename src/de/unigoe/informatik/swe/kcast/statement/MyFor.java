package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a for loop<br /><br />
 * Syntax: <code>for ( </code><em>init_clause</em><code> ; </code><em>cond_expression</em><code> ; </code><em>iteration_expression</em><code> ) </code>loop_statement [<a href="cppreference.com">cppreference.com</a>]
 * @author Ella Albrecht
 *
 */
public class MyFor extends MyIteration{

	/**
	 * Initialization statement of the for loop
	 */
	private MyStatement init;
	
	/**
	 * The condition against which the loop is checked each iteration
	 */
	private MyExpression condition;
	
	/**
	 * The expression which is evaluated after each execution of the loop
	 */
	private MyExpression iterationExpression;
	
	/**
	 * The body of the for loop
	 */
	private MyStatement block;
	
	/* The KCs defined for the for loop element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ITERATION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.FOR));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new for loop
	 * @param init The initialization statement of the for loop
	 * @param condition The looping condition of the for loop
	 * @param iterationExpression The iteration expression of the for loop
	 * @param body The body of the for loop
	 */
	public MyFor(MyStatement init, MyExpression condition, MyExpression iterationExpression, MyStatement body){
		this.init = init;
		this.condition = condition;
		this.iterationExpression = iterationExpression;
		this.block = body;
	}

	/**
	 * 
	 * @return The initialization statement of the for loop
	 */
	public MyStatement getInit() {
		return init;
	}

	/**
	 * 
	 * @param init Initialization statement for the for loop
	 */
	public void setInit(MyStatement init) {
		this.init = init;
	}

	/**
	 * The looping condition of the for loop
	 * @return
	 */
	public MyExpression getCondition() {
		return condition;
	}

	/**
	 * 
	 * @param condition The looping condition to be set for the for loop
	 */
	public void setCondition(MyExpression condition) {
		this.condition = condition;
	}

	/**
	 * 
	 * @return The iteration expression of the for loop
	 */
	public MyExpression getIterationExpression() {
		return iterationExpression;
	}

	/**
	 * 
	 * @param iterationExpression The iteration expression to be set for the for loop
	 */
	public void setIterationExpression(MyExpression iterationExpression) {
		this.iterationExpression = iterationExpression;
	}

	/**
	 * 
	 * @return The body of the for loop
	 */
	public MyStatement getBlock() {
		return block;
	}

	/**
	 * 
	 * @param block The body to be set for the for loop
	 */
	public void setBlock(MyBlock block) {
		this.block = block;
	}

	@Override
	public boolean isWhile() {
		return false;
	}

	@Override
	public boolean isFor() {
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
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		// add KCs for subelements
		if (init != null) kcs.addAll(init.getKCs(level));
		if (condition != null) kcs.addAll(condition.getKCs(level));
		if (iterationExpression != null) kcs.addAll(iterationExpression.getKCs(level));
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
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyFor) {
			MyFor forS = (MyFor) o;
			return (init.equals(forS.getInit()) && condition.equals(forS.getCondition()) &&
					iterationExpression.equals(forS.getIterationExpression()) && block.equals(forS.getBlock()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "FOR\n";
		text += "Init: " + init + "\n" ;
		text += "Condition: " + condition + "\n";
		text += "Iteration Expression: " + iterationExpression + "\n";
		text += "Body: " + block + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		if (init != null) hash+=init.hashCode();
		if (condition != null) hash+=condition.hashCode();
		if (iterationExpression != null) hash+=iterationExpression.hashCode();
		if (block != null) hash+=block.hashCode();
		return hash;
	}

	@Override
	public boolean isDefaultLabel() {
		return false;
	}

}
