package de.unigoe.informatik.swe.kcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.unigoe.informatik.swe.kcast.declaration.MyDeclaration;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * An AST representation of a piece of code containing KC information
 * @author Ella Albrecht
 *
 */
public class MyKCAST extends MyNode{

	/**
	 * #include macros in the program
	 */
	private List<MyInclude> includeMacros;
	
	/**
	 * #define macros in the program
	 */
	private List<MyMacroDefinition> defineMacros;
	
	/**
	 * Declarations in the program
	 */
	private List<MyDeclaration> declarations;
	
	/**
	 * Creates a new KC-AST
	 */
	public MyKCAST() {
		includeMacros = new ArrayList<MyInclude>();
		defineMacros = new ArrayList<MyMacroDefinition>();
		declarations = new ArrayList<MyDeclaration>();
	}

	/**
	 * 
	 * @return List of #include macros
	 */
	public List<MyInclude> getIncludeMacros() {
		return includeMacros;
	}

	/**
	 * 
	 * @param includeMacros List of #include macros
	 */
	public void setIncludeMacros(List<MyInclude> includeMacros) {
		this.includeMacros = includeMacros;
	}
	
	/**
	 * Adds an #include macro
	 * @param includeMacro List #include macro
	 */
	public void addIncludeMacro(MyInclude includeMacro) {
		includeMacros.add(includeMacro);
	}

	/**
	 * 
	 * @return List of #define macros
	 */
	public List<MyMacroDefinition> getDefineMacros() {
		return defineMacros;
	}

	/**
	 * 
	 * @param defineMacros List of #define macros
	 */
	public void setDefineMacros(List<MyMacroDefinition> defineMacros) {
		this.defineMacros = defineMacros;
	}

	/**
	 * Add a #define macro
	 * @param macro #define macro
	 */
	public void addDefineMacro(MyMacroDefinition macro) {
		defineMacros.add(macro);
	}

	/**
	 * 
	 * @return List of declarations
	 */
	public List<MyDeclaration> getDeclarations() {
		return declarations;
	}

	/**
	 * 
	 * @param declarations List of declarations
	 */
	public void setDeclarations(List<MyDeclaration> declarations) {
		this.declarations = declarations;
	}
	
	/**
	 * Adds a declaration
	 * @param declaration Declaration
	 */
	public void addDeclaration(MyDeclaration declaration) {
		declarations.add(declaration);
	}

	@Override
	public Set<KnowledgeComponent> getKCs(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}
