package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.MyNode;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class MyTypeQualifier extends MyNode {
	public enum TypeQualifier { NONE, CONST, VOLATILE, RESTRICT, ATOMIC}
	
	private TypeQualifier type;
  
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.TYPE_QUALIFIER));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	/**
	 * Creates a type qualifier which represents that no type qualifier is needed
	 */
	public MyTypeQualifier() {
		type = TypeQualifier.NONE;
	}
	
	public MyTypeQualifier(TypeQualifier type) {
		this.setType(type);
	}



	public TypeQualifier getType() {
		return type;
	}

	public void setType(TypeQualifier type) {
		this.type = type;
	}

	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		kcs.addAll(kcs0);
		if (level > 0) {
			kcs.addAll(kcs1);
			if (level > 1) {
				kcs.addAll(kcs2);
				if (this.equals(TypeQualifier.CONST)) kcs.add(KnowledgeComponent.CONST);
				if (this.equals(TypeQualifier.VOLATILE)) kcs.add(KnowledgeComponent.VOLATILE);
				if (this.equals(TypeQualifier.RESTRICT)) kcs.add(KnowledgeComponent.RESTRICT);
				if (this.equals(TypeQualifier.ATOMIC)) kcs.add(KnowledgeComponent.ATOMIC);
				if (level > 2){
					kcs.addAll(kcs3);
				}
			}		
		}
		return kcs;
	}
}
