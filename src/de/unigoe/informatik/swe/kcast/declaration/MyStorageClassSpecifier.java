package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.MyNode;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class MyStorageClassSpecifier extends MyNode {
  
	public enum StorageClass {NONE, TYPEDEF, AUTO, REGISTER, STATIC, EXTERN, THREAD_LOCAL}
	
	private StorageClass type;
  
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.STORAGE_CLASS));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	public MyStorageClassSpecifier() {
		type = StorageClass.NONE;
	}
	
	public MyStorageClassSpecifier(StorageClass type) {
		this.type = type;
		setRawSignature("");
		setPosition(0);
		setLength(0);
	}
	
	public StorageClass getType() {
		return type;
	}
	
	public void setType(StorageClass type) {
		this.type = type;
	}

	public Set<KnowledgeComponent> getKCs(int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		// add KCs for array
		if (!type.equals(StorageClass.NONE)) {
			kcs.addAll(kcs0);
			if (level > 0) {
				kcs.addAll(kcs1);
				if (level > 1) {
					kcs.addAll(kcs2);
					if (type.equals(StorageClass.TYPEDEF)) kcs.add(KnowledgeComponent.TYPEDEF);
					if (type.equals(StorageClass.AUTO)) kcs.add(KnowledgeComponent.AUTO);
					if (type.equals(StorageClass.REGISTER)) kcs.add(KnowledgeComponent.REGISTER);
					if (type.equals(StorageClass.STATIC)) kcs.add(KnowledgeComponent.STATIC);
					if (type.equals(StorageClass.EXTERN)) kcs.add(KnowledgeComponent.EXTERN);
					if (type.equals(StorageClass.THREAD_LOCAL)) kcs.add(KnowledgeComponent.THREAD_LOCAL);
					if (level > 2){
						kcs.addAll(kcs3);
					}
				}		
			}		
		}

		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyStorageClassSpecifier) {
			MyStorageClassSpecifier sc = (MyStorageClassSpecifier) o;
			return type.equals(sc.getType());
		}
		return false;
	}
}
