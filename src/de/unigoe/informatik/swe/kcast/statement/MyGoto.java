package de.unigoe.informatik.swe.kcast.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a goto statements which transfers control unconditionally to the desired location. [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax: <code>goto</code> <em>label</em> <code>;</code>
 * @author Ella Albrecht
 *
 */
public class MyGoto extends MyJump{

	/**
	 * The label to which has to be jumped
	 */
	private String label;
	
	/* The KCs defined for the goto statement element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STATEMENT));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.JUMP));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.GOTO));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new goto statement
	 * @param label The name of the label to which the goto refers
	 */
	public MyGoto(String label) {
		this.label = label;
	}
	
	/**
	 * 
	 * @return The name of the label to which the goto refers
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label The name of the label to which the goto shall refer
	 */
	public void setLabel(String label) {
		this.label = label;
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
		if (o instanceof MyGoto) {
			MyGoto go = (MyGoto) o;
			return (label.equals(go.getLabel()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text = "GOTO\n";
		text += "Label: " + label + "\n";
		return text;
	}
	
	@Override
	public int hashCode() {
		return label.hashCode() + 1;
	}

	@Override
	public boolean isDefaultLabel() {
		return false;
	}

}
