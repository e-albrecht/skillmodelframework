package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.initialisation.MyInitialisation;
import de.unigoe.informatik.swe.kcast.statement.MyBlock;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class MyPointer extends MyDeclarator {

	private MyTypeQualifier qualifier;
	private MyDeclarator declarator;
	private MyInitialisation init;
	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATOR));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.POINTER));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	
	public MyPointer(MyTypeQualifier qualifier, MyDeclarator declarator) {
		this.qualifier = qualifier;
		this.declarator = declarator;
	}
	
	public MyPointer(MyTypeQualifier qualifier, MyDeclarator declarator, MyInitialisation init) {
		this.qualifier = qualifier;
		this.declarator = declarator;
		this.init = init;
	}
	
	public MyPointer(MyDeclarator declarator) {
		this.declarator = declarator;
		this.qualifier = new MyTypeQualifier();
	}
	
	public MyPointer() {
		qualifier = new MyTypeQualifier();
	}

	@Override
	public boolean isIdentifier() {
		return false;
	}

	@Override
	public boolean isPointer() {
		return true;
	}

	@Override
	public boolean isArray() {
		return false;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

	public MyTypeQualifier getQualifier() {
		return qualifier;
	}

	public void setQualifier(MyTypeQualifier qualifier) {
		this.qualifier = qualifier;
	}

	public MyDeclarator getDeclarator() {
		return declarator;
	}

	public void setDeclarator(MyDeclarator declarator) {
		this.declarator = declarator;
	}

	public MyInitialisation getInit() {
		return init;
	}

	public void setInit(MyInitialisation init) {
		this.init = init;
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
		// add KCs for subelements
		kcs.addAll(declarator.getKCs(level));
		kcs.addAll(qualifier.getKCs(level));
		if (init != null) kcs.addAll(init.getKCs(level));
		
		return kcs;
	}

	@Override
	public boolean containsFunction() {
		return declarator.containsFunction();
	}
	
	public boolean equals(Object o) {
		if (o instanceof MyPointer) {
			MyPointer p = (MyPointer) o;
			return (qualifier.equals(p.getQualifier()) && declarator.equals(p.getDeclarator()) && init.equals(p.getInit()));
		}
		return false;
	}
	
	public String toString() {
		String text = "POINTER\n";
		text += "Type Qualifier: " + qualifier + "\n";
		text += "Declarator: " + declarator + "\n";
		text += "Init: " + init + "\n";
		return text;
		
	}
	
	@Override
	public Set<KnowledgeComponent> getFunctionRootKCs(int level) {
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
		// add KCs for subelements
		kcs.addAll(declarator.getFunctionRootKCs(level));
		kcs.addAll(qualifier.getKCs(level));
		if (init != null) kcs.addAll(init.getKCs(level));
		
		return kcs;
	}

	@Override
	public MyBlock getFunctionBody() {
		return declarator.getFunctionBody();
	}

}
