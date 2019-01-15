package de.unigoe.informatik.swe.kcast.declaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.statement.MyBlock;
import de.unigoe.informatik.swe.kcast.statement.MyStatement;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a declaration. A declaration is a C language construct that introduces one or more identifiers into the program and specifies their meaning and properties. [<a href="cppreference.com">cppreference.com</a>]<br /><br />
 * Syntax: <em>specifiers-and-qualifiers declarators-and-initializers</em> <code>;</code>
 * @author Ella Albrecht
 *
 */
public class MyDeclaration extends MyStatement {
	
	/**
	 * The type specifier of the declaration
	 */
	private MyTypeSpecifier typeSpecifier;
	
	/**
	 * The storage class of the declaration
	 */
	private MyStorageClassSpecifier storageclassSpecifier;
	
	/**
	 * Type qualifiers of the declaration
	 */
	private Set<MyTypeQualifier> typeQualifiers;
	
	/**
	 * Declarators of the declaration
	 */
	private List<MyDeclarator> declarators;
	
	/* The KCs defined for the type cast element on different levels */
	private static final Set<KnowledgeComponent> kcs0 = new HashSet<KnowledgeComponent>(Arrays.asList(KnowledgeComponent.DECLARATION));
	private static final Set<KnowledgeComponent> kcs1 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs2 = new HashSet<KnowledgeComponent>();
	private static final Set<KnowledgeComponent> kcs3 = new HashSet<KnowledgeComponent>();

	/**
	 * Creates a new declaration of type void without any qualifiers, declarators, and storage class
	 */
	public MyDeclaration() {
		typeSpecifier = new MyVoidType();
		storageclassSpecifier = new MyStorageClassSpecifier();
		typeQualifiers = new HashSet<MyTypeQualifier>();
		declarators = new LinkedList<MyDeclarator>();
	}
	
	/**
	 * Creates a new declaration
	 * @param typeSpecifier The type specifier of the declaration
	 * @param typeQualifiers A set of type qualifiers
	 * @param declarators A set of declaration
	 * @param storageClass The storage class specifier of the declaration
	 */
    public MyDeclaration(MyTypeSpecifier typeSpecifier, Set<MyTypeQualifier> typeQualifiers, MyStorageClassSpecifier storageClass, List<MyDeclarator> declarators) {
	  this.setTypeSpecifier(typeSpecifier);
	  declarators = new LinkedList<MyDeclarator>();
	  this.storageclassSpecifier = storageClass;
	  this.declarators = declarators;
	  if (typeQualifiers == null) {
		  typeQualifiers = new HashSet<MyTypeQualifier>();
		  typeQualifiers.add(new MyTypeQualifier());
	  }
	  else this.setTypeQualifiers(typeQualifiers);
	}

    /**
     * 
     * @return Type qualifiers of the declaration
     */
	public Set<MyTypeQualifier> getTypeQualifiers() {
		return typeQualifiers;
	}

	/**
	 * 
	 * @param typeQualifiers A set of type qualifiers for the declaration
	 */
	public void setTypeQualifiers(Set<MyTypeQualifier> typeQualifiers) {
		this.typeQualifiers = typeQualifiers;
	}

	/**
	 * 
	 * @return Type specifier of the declaration
	 */
	public MyTypeSpecifier getTypeSpecifier() {
		return typeSpecifier;
	}

	/**
	 * 
	 * @param typeSpecifier Type specifier for the declaration
	 */
	public void setTypeSpecifier(MyTypeSpecifier typeSpecifier) {
		this.typeSpecifier = typeSpecifier;
	}

	/**
	 * 
	 * @return Storage class specifier of the declaration
	 */
	public MyStorageClassSpecifier getStorageclassSpecifier() {
		return storageclassSpecifier;
	}

	/**
	 * 
	 * @param storageclassSpecifier Storage class specifier for the declaration
	 */
	public void setStorageclassSpecifier(MyStorageClassSpecifier storageclassSpecifier) {
		this.storageclassSpecifier = storageclassSpecifier;
	}
	
	/**
	 * 
	 * @return List of declarators of the declaration
	 */
	public List<MyDeclarator> getDeclarators() {
		return declarators;
	}

	/**
	 * 
	 * @param declarators List of declarators for the declaration
	 */
	public void setDeclarators(List<MyDeclarator> declarators) {
		this.declarators = declarators;
	}
	
	/**
	 * Adds a declarator to the declaration
	 * @param declarator Declarator to be added
	 */
	public void addDeclarator(MyDeclarator declarator) {
		declarators.add(declarator);
	}
	
	/**
	 * Creates a new declaration which has the same storage class, type specifier, type qualifiers as this declaration but an empty set of declarators
	 * @return
	 */
	public MyDeclaration copyWithoutDeclarator() {
		MyDeclaration copy = new MyDeclaration();
		copy.setStorageclassSpecifier(storageclassSpecifier);
		copy.setTypeSpecifier(typeSpecifier);
		copy.setTypeQualifiers(typeQualifiers);
		return copy;
	}
	
	/**
	 * Returns KCs required for creating a function node in a KAM
	 * @param level Level of KCs
	 * @return KCs required for a function 
	 */
	public Set<KnowledgeComponent> getKCsForFunction(int level) {
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
		if (typeSpecifier != null) kcs.addAll(typeSpecifier.getKCs(level));
		kcs.addAll(storageclassSpecifier.getKCs(level));
		for (MyTypeQualifier quali : typeQualifiers) {
			kcs.addAll(quali.getKCs(level));
		}
		for (MyDeclarator decl : declarators) {
			kcs.add(KnowledgeComponent.DECLARATOR);
			kcs.addAll(decl.getFunctionRootKCs(level));
			kcs.remove(KnowledgeComponent.PRIMARY_EXPRESSION);
		}		
		return kcs;
	}
	
	/**
	 * Return the body of a function if this is a function declaration and <code>null</code> otherwise.
	 * @return The body of the inhibited function
	 */
	public MyBlock getFunctionBody() {
		if (containsFunction()) {
			for (MyDeclarator decl : declarators) {
				MyBlock fBody = decl.getFunctionBody();
				if (fBody != null) return fBody;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return Returns whether the declaration contains a function declarator 
	 */
	public boolean containsFunction() {
		for (MyDeclarator decl : declarators) {
			if (decl.containsFunction()) return true;
		}
		return false;
	}
	
	

	@Override
	public boolean isBlock() {
		return false;
	}

	@Override
	public boolean isDeclaration() {
		return true;
	}

	@Override
	public boolean isExpression() {
		return false;
	}

	@Override
	public boolean isSelection() {
		return false;
	}

	@Override
	public boolean isIteration() {
		return false;
	}

	@Override
	public boolean isJump() {
		return false;
	}

	@Override
	public boolean isLabel() {
		return false;
	}

	@Override
	public boolean isCaseLabel() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public boolean isDefaultLabel() {
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
		// add KCs for subelements
		kcs.addAll(typeSpecifier.getKCs(level));
		kcs.addAll(storageclassSpecifier.getKCs(level));
		for (MyTypeQualifier quali : typeQualifiers) {
			kcs.addAll(quali.getKCs(level));
		}
		for (MyDeclarator decl : declarators) {
			kcs.addAll(decl.getKCs(level));
		}		
		return kcs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MyDeclaration) {
			MyDeclaration decl = (MyDeclaration) o;
			return (typeSpecifier.equals(decl.getTypeSpecifier()) && storageclassSpecifier.equals(decl.getStorageclassSpecifier())
					 && typeQualifiers.equals(decl.getTypeQualifiers()) && declarators.equals(decl.getDeclarators()));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String text ="DECLARATION\n";
		text += "Storage Class: " + storageclassSpecifier + "\n";
		text += "Type Qualifier: "; 
		for (MyTypeQualifier quali : typeQualifiers) {
			text += quali + " ";
		}
		text += "\n";
		text += "Type Specifier: " + typeSpecifier + "\n";
		text += "Declarator: \n";
		for (MyDeclarator decl : declarators) {
			text+= decl + "\n";
		}
		return text;
	}
}
