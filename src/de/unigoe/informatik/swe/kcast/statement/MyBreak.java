package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a break statement. Causes the enclosing for, while or do-while loop or switch statement to terminate. [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax: <code>break ;</code>
 * @author Ella Albrecht
 *
 */
public class MyBreak extends MyJump{

	/* The KCs defined for the break element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.JUMP));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.BREAK));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a new break statement
	 */
	public MyBreak() {};
	
	@Override
	public boolean isBreak() {
		return true;
	}

	@Override
	public boolean isContinue() {
		return false;
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
		return (o instanceof MyBreak);
	}
	
	@Override
	public String toString() {
		String text = "BREAK\n";
		return text;
	}

	@Override
	public boolean isDefaultLabel() {
		return false;
	}
}
