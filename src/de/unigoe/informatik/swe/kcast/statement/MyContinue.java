package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a continue statement. Causes the remaining portion of the enclosing for, while or do-while loop body to be skipped. [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax: <code>continue ;</code>
 * @author Ella Albrecht
 *
 */
public class MyContinue extends MyJump {

	/**
	 * Creates a new continue statement
	 */
	public MyContinue() {}
	
	/* The KCs defined for the continue element on different levels */	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.JUMP));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.CONTINUE));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	
	@Override
	public boolean isBreak() {
		return false;
	}

	@Override
	public boolean isContinue() {
		return true;
	}

	@Override
	public boolean isReturn() {
		return false;
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
		return (o instanceof MyContinue);
	}
	
	@Override
	public String toString() {
		String text = "CONTINUE\n";
		return text;
	}

	@Override
	public boolean isDefaultLabel() {
		return false;
	}	
}
