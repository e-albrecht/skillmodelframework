package de.unigoe.informatik.swe.kcast.declaration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class MyUnion extends MyTypeSpecifier{

	
	private String name;
	private List<MyDeclaration> elements;
	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DATA_TYPE));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ELABORATED_TYPE_SPECIFIER, KnowledgeComponent.UNION));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();
	
	public MyUnion(String name) {
		this.setName(name);
		setElements(new ArrayList<MyDeclaration>());
	}
	
	public MyUnion(String name, List<MyDeclaration> elements) {
		this.setName(name);
		this.setElements(elements);
	}
	
	public MyUnion(List<MyDeclaration> elements) {
		this.setName("");
		this.setElements(elements);
	}
	
	@Override
	public boolean isVoidType() {
		return false;
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
		return true;
	}

	@Override
	public boolean isEnum() {
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MyDeclaration> getElements() {
		return elements;
	}

	public void setElements(List<MyDeclaration> elements) {
		this.elements = elements;
	}
	
	public void addElement(MyDeclaration decl) {
		elements.add(decl);
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
		// add KCs for subelements
		for (MyDeclaration decl : elements) {
			kcs.addAll(decl.getKCs(level));
		}
		
		return kcs;
	}
	
	public boolean equals(Object o) {
		if (o instanceof MyStruct) {
			MyStruct s = (MyStruct) o;
			return (name.equals(s.getName()) && elements.equals(s.getElements()));
		}
		return false;
	}
	
	public String toString() {
		String text = "UNION\n";
		text += "Name: " + name;
		text += "Elements: \n";
		for (MyDeclaration elem : elements) {
			text += elem + "\n";
		}
		return text;
	}
}
