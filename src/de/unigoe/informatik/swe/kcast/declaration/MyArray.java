package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.kcast.initialisation.MyInitialisation;
import de.unigoe.informatik.swe.kcast.statement.MyBlock;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents an array
 * @author Ella Albrecht
 *
 */
public class MyArray extends MyDeclarator{

	private MyDeclarator declarator;
	private MyExpression[] size;
	private MyInitialisation init;
	
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATOR));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.ARRAY));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	public MyArray(MyDeclarator declarator, MyExpression[] size) {
		this.declarator = declarator;
		this.size = size;
	}
	
	public MyArray(MyDeclarator declarator, MyExpression[] size, MyInitialisation init) {
		this.declarator = declarator;
		this.size = size;
		this.init = init;
	}
	
	public Set<KnowledgeComponent> getKCs(int level){
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
		if (size != null) {
			for (MyExpression expr : size) {
				if (expr != null) kcs.addAll(expr.getKCs(level));
			}
		}
		if (init != null) kcs.addAll(init.getKCs(level));
		
		return kcs;
	}
	
	@Override
	public boolean isIdentifier() {
		return false;
	}

	@Override
	public boolean isPointer() {
		return false;
	}

	@Override
	public boolean isArray() {
		return true;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

	public MyDeclarator getDeclarator() {
		return declarator;
	}

	public void setDeclarator(MyDeclarator declarator) {
		this.declarator = declarator;
	}

	public MyExpression[] getSize() {
		return size;
	}

	public void setSize(MyExpression[] size) {
		this.size = size;
	}


	public MyInitialisation getInit() {
		return init;
	}


	public void setInit(MyInitialisation init) {
		this.init = init;
		
	}

	@Override
	public boolean containsFunction() {
		return declarator.containsFunction();
	}
	
	public String toString() {
		return "ARRAY\n Declarator: " + declarator + "\n Size: " + size + "\n Init: " + init + "\n";
	}
	
	public boolean equals(Object o) {
		if (o instanceof MyArray) {
			MyArray arr = (MyArray) o;
			return arr.getDeclarator().equals(declarator) && arr.getSize().equals(size) && arr.getInit().equals(init);
		}
		return false;
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
		for (MyExpression expr : size) {
			if (expr != null) kcs.addAll(expr.getKCs(level));
		}
		if (init != null) kcs.addAll(init.getKCs(level));
		return kcs;
	}

	@Override
	public MyBlock getFunctionBody() {
		return declarator.getFunctionBody();
	}

}
