package de.unigoe.informatik.swe.kcast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class MyMacroDefinition extends MyNode{

	private String name;
	private String expansion;
	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PREPROCESSOR));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DEFINE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	
	public MyMacroDefinition(String name, String expansion) {
		this.name = name;
		this.expansion = expansion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpansion() {
		return expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion = expansion;
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
	
	public boolean equals(Object o) {
		if (o instanceof MyInclude) {
			MyInclude include = (MyInclude) o;
			return (name.equals(include.getName()));
		}
		return false;
	}
	
	public String toString() {
		String text = "DEFINE\n";
		text += "Name: " + name + "\n";
		text += "Expansion: " + expansion + "\n";
		return text;
	}
}
