package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class MyVoidType extends MyTypeSpecifier{
	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DATA_TYPE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.VOID));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();


	@Override
	public boolean isVoidType() {
		return true;
	}

	@Override
	public boolean isArithmeticType() {
		return false;
	}

	@Override
	public boolean isCharacterType() {
		return false;
	}

	@Override
	public boolean isIntegerType() {
		return false;
	}

	@Override
	public boolean isFloatingType() {
		return false;
	}

	@Override
	public boolean isStruct() {
		return false;
	}

	@Override
	public boolean isUnion() {
		return false;
	}

	@Override
	public boolean isEnum() {
		return false;
	}

	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		// add KCs for array
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
		return (o instanceof MyVoidType);
	}
	
	public String toString() {
		return "VOID";
	}
	
}
