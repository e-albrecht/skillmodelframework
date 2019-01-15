package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.initialisation.MyInitialisation;
import de.unigoe.informatik.swe.kcast.statement.MyBlock;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class MyIdentifier extends MyDeclarator{

	private String name;
	private MyInitialisation init;
	private boolean isVariable;
	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList());
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.PRIMARY_EXPRESSION));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.IDENTIFIER));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	
	public MyIdentifier(String name, boolean isVariable) {
		this.name = name;
		this.isVariable = isVariable;
	}
	
	public MyIdentifier(String name, boolean isVariable, MyInitialisation init) {
		this.name = name;
		this.init = init;
		this.isVariable = isVariable;
	}	

	@Override
	public boolean isIdentifier() {
		return true;
	}

	@Override
	public boolean isPointer() {
		return false;
	}

	@Override
	public boolean isArray() {
		return false;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
				if (isVariable) kcs.add(KnowledgeComponent.VARIABLE);
				if (level > 2) kcs.addAll(kcs3);
			}		
		}
		// add KCs for subelements
		if (init != null) kcs.addAll(init.getKCs(level));
		
		return kcs;
	}

	@Override
	public boolean containsFunction() {
		return false;
	}

	public boolean isVariable() {
		return isVariable;
	}

	public void setVariable(boolean isVariable) {
		this.isVariable = isVariable;
	}
	
	public boolean equals(Object o) {
		if (o instanceof MyIdentifier) {
			MyIdentifier id = (MyIdentifier) o;
			return (name.equals(id.getName()) && init.equals(id.getInit()));
		}
		return false;
	}
	
	public String toString() {
		String text = "DECLARATOR\n";
		if (isVariable) text+=" - VARIABLE\n";
		text += "Name: " + name + "\n";
		text += "Init: " + init + "\n";
		return text;
	}
	
	@Override
	public Set<KnowledgeComponent> getFunctionRootKCs(int level) {
		return getKCs(level);
	}

	@Override
	public MyBlock getFunctionBody() {
		return null;
	}
	
	
	
	
}
