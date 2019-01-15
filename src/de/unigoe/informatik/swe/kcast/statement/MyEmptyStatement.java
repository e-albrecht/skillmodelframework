package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an empty statement<br /><br />
 * Syntax: <code>;</code>
 * @author Ella Albrecht
 *
 */
public class MyEmptyStatement extends MyStatement {

	/* The KCs defined for the empty statement element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	
	public MyEmptyStatement(){}
	
	@Override
	public boolean isBlock() {
		return false;
	}

	@Override
	public boolean isDeclaration() {
		return false;
	}

	@Override
	public boolean isExpression() {
		return false;
	}

	@Override
	public boolean isSelection() {
		return false;
	}

	@Override
	public boolean isIteration() {
		return false;
	}

	@Override
	public boolean isJump() {
		return false;
	}

	@Override
	public boolean isLabel() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean isCaseLabel() {
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
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof MyEmptyStatement);
	}
	@Override
	public String toString() {
		String text = "EMPTY STATEMENT\n";
		return text;
	}

	@Override
	public boolean isDefaultLabel() {
		return false;
	}
}
