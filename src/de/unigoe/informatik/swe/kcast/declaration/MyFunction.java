package de.unigoe.informatik.swe.kcast.declaration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.initialisation.MyInitialisation;
import de.unigoe.informatik.swe.kcast.statement.MyBlock;
import de.unigoe.informatik.swe.kcast.statement.MyStatement;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

public class MyFunction extends MyDeclarator {

	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATOR));
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.FUNCTION));
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	
	private MyDeclarator declarator;
	private List<MyDeclaration> parameters;
	private MyBlock body;
	private MyInitialisation init;
	
	public MyFunction(MyDeclarator declarator, MyBlock body) {
		this.declarator = declarator;
		parameters = new ArrayList<MyDeclaration>();
		this.body = body;
	}
	
	public MyFunction(MyDeclarator declarator, MyBlock body, MyInitialisation init) {
		this.declarator = declarator;
		parameters = new ArrayList<MyDeclaration>();
		this.body = body;
		this.init = init;
	}
	
	public MyFunction(MyDeclarator declarator, List<MyDeclaration> parameters, MyBlock body) {
		this.declarator = declarator;
		this.parameters = parameters;
		this.body = body;
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
		return false;
	}

	@Override
	public boolean isFunction() {
		return true;
	}

	public MyDeclarator getDeclarator() {
		return declarator;
	}

	public void setDeclarator(MyDeclarator declarator) {
		this.declarator = declarator;
	}

	public List<MyDeclaration> getParameters() {
		return parameters;
	}

	public void setParameters(List<MyDeclaration> paramaters) {
		this.parameters = paramaters;
	}
	
	public void addParameter(MyDeclaration param) {
		parameters.add(param);
	}

	public MyStatement getBody() {
		return body;
	}

	public void setBody(MyBlock body) {
		this.body = body;
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
		if (body != null) kcs.addAll(body.getKCs(level));
		if (init != null) kcs.addAll(init.getKCs(level));
		for (MyDeclaration param : parameters) {
			kcs.addAll(param.getKCs(level));
		}
		
		return kcs;	
	}

	@Override
	public boolean containsFunction() {
		return true;
	}
	
	public boolean equals(Object o) {
		if (o instanceof MyFunction) {
			MyFunction func = (MyFunction) o;
			return (declarator.equals(func.getDeclarator()) && parameters.equals(func.getParameters()) && 
					body.equals(func.getBody()) && init.equals(func.getInit()));
		}
		return false;
	}
	
	public String toString() {
		String text = "FUNCTION\n";
		text += "Declarator: " + declarator + "\n";
		text += "Parameters:\n";
		for (MyDeclaration param : parameters) {
			text += param.toString();
		}
		text += "Body: " + body;
		text += "Init: " + init;
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
		if (init != null) kcs.addAll(init.getKCs(level));
		for (MyDeclaration param : parameters) {
			kcs.addAll(param.getKCsForFunction(level));
		}
		
		return kcs;	
	}

	@Override
	public MyBlock getFunctionBody() {
		return body;
	}

}
