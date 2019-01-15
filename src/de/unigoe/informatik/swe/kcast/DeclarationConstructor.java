package de.unigoe.informatik.swe.kcast;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.c.ICASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTTypedefNameSpecifier;

import de.unigoe.informatik.swe.kcast.declaration.MyCharacterType;
import de.unigoe.informatik.swe.kcast.declaration.MyCharacterType.CharacterType;
import de.unigoe.informatik.swe.kcast.declaration.MyDeclaration;
import de.unigoe.informatik.swe.kcast.declaration.MyElaboratedTypeSpecifier;
import de.unigoe.informatik.swe.kcast.declaration.MyEnumeration;
import de.unigoe.informatik.swe.kcast.declaration.MyEnumerationElement;
import de.unigoe.informatik.swe.kcast.declaration.MyFloatingType;
import de.unigoe.informatik.swe.kcast.declaration.MyFloatingType.FloatingType;
import de.unigoe.informatik.swe.kcast.declaration.MyIntegerType;
import de.unigoe.informatik.swe.kcast.declaration.MyIntegerType.IntegerType;
import de.unigoe.informatik.swe.kcast.declaration.MyStorageClassSpecifier;
import de.unigoe.informatik.swe.kcast.declaration.MyStorageClassSpecifier.StorageClass;
import de.unigoe.informatik.swe.kcast.declaration.MyStruct;
import de.unigoe.informatik.swe.kcast.declaration.MyTypeDefName;
import de.unigoe.informatik.swe.kcast.declaration.MyTypeQualifier;
import de.unigoe.informatik.swe.kcast.declaration.MyTypeQualifier.TypeQualifier;
import de.unigoe.informatik.swe.kcast.declaration.MyTypeSpecifier;
import de.unigoe.informatik.swe.kcast.declaration.MyUnion;
import de.unigoe.informatik.swe.kcast.declaration.MyVoidType;
import de.unigoe.informatik.swe.kcast.declaration.MyElaboratedTypeSpecifier.ElaboratedType;

/**
 * Transforming CDT declaration elements into KC-AST representations
 * @author Ella Albrecht
 *
 */
public class DeclarationConstructor {

	/**
	 * Transforms a CDT declaration into a KC-AST representation of the declaration. 
	 * It is distinguished between function and simple declarations. <br>
	 * The syntax of a declaration is: <br /> <i>specifiers-and-qualifiers</i> <i>declarators-and-initializers</i>;
	 * @param declAst The CDT representation of a declaration
	 * @return The KC-AST representation of a declaration or <code>null</code> if no corresponding representation exists
	 */
	public static MyDeclaration construct(IASTDeclaration declAst) {
		if (declAst instanceof IASTFunctionDefinition) return constructFunction((IASTFunctionDefinition) declAst);
		if (declAst instanceof IASTSimpleDeclaration) return constructSimpleDeclaration((IASTSimpleDeclaration) declAst);
		return null;
	}
	
	/**
	 * Constructs a declaration representing a parameter of a function
	 * @param param The CDT representation of a parameter
	 * @return The KC-AST representation of a parameter
	 */
	public static MyDeclaration constructParameter(IASTParameterDeclaration param) {
		MyDeclaration decl = new MyDeclaration();
		// set declaration specifiers
		IASTDeclSpecifier spec = param.getDeclSpecifier();
		setDeclarationSpecifiers(spec,decl);
		// set declarator
		IASTDeclarator declarator = param.getDeclarator();
		decl.addDeclarator(DeclaratorConstructor.construct(declarator));
		ProgramConstructor.setNodeValues(param,decl);
		return decl;
	}

	 /**
	 * Constructs a simple declaration
	 * @param param The CDT representation of a simple declaration
	 * @return The KC-AST representation of a simple declaration
	 */
	private static MyDeclaration constructSimpleDeclaration(IASTSimpleDeclaration declAst) {
		MyDeclaration decl = new MyDeclaration();
		IASTDeclSpecifier spec = declAst.getDeclSpecifier();
		setDeclarationSpecifiers(spec,decl);
		// set declarator 
		if (!decl.getTypeSpecifier().isStruct() && !decl.getTypeSpecifier().isUnion()) { // structs and unions have only names, not a declarator
			for (IASTDeclarator declarator : declAst.getDeclarators()) {
				decl.addDeclarator(DeclaratorConstructor.construct(declarator));		
			}
		}
		ProgramConstructor.setNodeValues(declAst,decl);		
		return decl;
	}

	/**
	 * Constructs a function
	 * @param func The CDT representation of a function declaration
	 * @return A KC-AST representation of a function declaration
	 */
	private static MyDeclaration constructFunction(IASTFunctionDefinition func) {
		MyDeclaration decl = new MyDeclaration();
		// set declaration specifiers
		IASTDeclSpecifier spec = func.getDeclSpecifier();
		setDeclarationSpecifiers(spec,decl);
		// set declarator
		decl.addDeclarator(DeclaratorConstructor.constructFunction(func));
		ProgramConstructor.setNodeValues(func,decl);
		return decl;
	}
	
	/**
	 * Sets the declaration specifiers in a declaration
	 * @param spec The CDT representation of the declaration specifiers
	 * @param decl The declaration for which the specifiers shall be set
	 */
	private static void setDeclarationSpecifiers(IASTDeclSpecifier spec, MyDeclaration decl) {
		// get type qualifiers
		Set<MyTypeQualifier> typeQualifiers = new HashSet<MyTypeQualifier>();
		if (spec.isConst()) {
			MyTypeQualifier tq = new MyTypeQualifier(TypeQualifier.CONST);
			setNodeValues(spec,tq,"const");
			typeQualifiers.add(tq);
		}
		
		if (spec.isVolatile()) {
			MyTypeQualifier tq = new MyTypeQualifier(TypeQualifier.VOLATILE);
			setNodeValues(spec,tq,"volatile");
			typeQualifiers.add(tq);		
		}
		
		if (spec.isRestrict()) {
			MyTypeQualifier tq = new MyTypeQualifier(TypeQualifier.RESTRICT);
			setNodeValues(spec,tq,"restrict");
			typeQualifiers.add(tq);		
		}
		
		decl.setTypeQualifiers(typeQualifiers);
		
		// get storage class
		int sc = spec.getStorageClass();
		MyStorageClassSpecifier scSpec = new MyStorageClassSpecifier();
		switch(sc) {
		case IASTDeclSpecifier.sc_auto : scSpec = new MyStorageClassSpecifier(StorageClass.AUTO); setNodeValues(spec,scSpec,"auto");break;
		case IASTDeclSpecifier.sc_extern : scSpec = new MyStorageClassSpecifier(StorageClass.EXTERN); setNodeValues(spec,scSpec,"extern"); break;
		case IASTDeclSpecifier.sc_register : scSpec = new MyStorageClassSpecifier(StorageClass.REGISTER); setNodeValues(spec,scSpec,"register"); break;
		case IASTDeclSpecifier.sc_static : scSpec = new MyStorageClassSpecifier(StorageClass.STATIC); setNodeValues(spec,scSpec,"static"); break;
		case IASTDeclSpecifier.sc_typedef : scSpec = new MyStorageClassSpecifier(StorageClass.TYPEDEF); setNodeValues(spec,scSpec,"typedef"); break;
		}
		decl.setStorageclassSpecifier(scSpec);		
		// get type specifier
		decl.setTypeSpecifier(getTypeSpecifier(spec));
	}
	
	/**
	 * Constructs a type specifier
	 * @param spec The CDT representation of type specifier
	 * @return Returns the KC-AST representation of a type specifier
	 */
	public static MyTypeSpecifier getTypeSpecifier(IASTDeclSpecifier spec) {
		if (spec instanceof IASTSimpleDeclSpecifier) return getSimpleTypeSpecifier((IASTSimpleDeclSpecifier) spec);
		else if (spec instanceof IASTCompositeTypeSpecifier) return getCompositeTypeSpecifier((IASTCompositeTypeSpecifier) spec);
		else if (spec instanceof IASTEnumerationSpecifier) return getEnumerationTypeSpecifier((IASTEnumerationSpecifier) spec);
		else if (spec instanceof ICASTTypedefNameSpecifier) return getTypedefNameTypeSpecifier((ICASTTypedefNameSpecifier) spec);
		else if (spec instanceof ICASTElaboratedTypeSpecifier) return getElaboratedTypeSpecifier((ICASTElaboratedTypeSpecifier) spec);
		return null;
	}
	
	/**
	 * Constructs a type specifier for elaborated types, i.e., enum, struct, and union
	 * @param spec CDT representation of the elaborated type specifier
	 * @return Returns the KC-AST representation of an elaborated type specifier
	 */
	private static MyTypeSpecifier getElaboratedTypeSpecifier(
			ICASTElaboratedTypeSpecifier spec) {
		ElaboratedType type = null;
		switch (spec.getKind()) {
		  case ICASTElaboratedTypeSpecifier.k_enum : type = ElaboratedType.ENUM;
		  case ICASTElaboratedTypeSpecifier.k_struct : type = ElaboratedType.STRUCT;
		  case ICASTElaboratedTypeSpecifier.k_union : type = ElaboratedType.UNION;
		}
		MyElaboratedTypeSpecifier typeSpec = new MyElaboratedTypeSpecifier(spec.getName().toString(), type);
		ProgramConstructor.setNodeValues(spec,typeSpec);
		return typeSpec;
	}

	/**
	 * Constructs a type specifier for a typedef name
	 * @param spec CDT representation of the typedef name specifier
	 * @return Returns the KC-AST representation of a typedef name specifier
	 */
	private static MyTypeSpecifier getTypedefNameTypeSpecifier(
			ICASTTypedefNameSpecifier spec) {
		MyTypeDefName typeDef = new MyTypeDefName(spec.getName().toString());
		ProgramConstructor.setNodeValues(spec, typeDef);
		return typeDef;
	}

	/**
	 * Constructs a simple type specifier
	 * @param spec
	 * @return
	 */
	private static MyTypeSpecifier getSimpleTypeSpecifier(IASTSimpleDeclSpecifier spec) {
		MyTypeSpecifier tc = null;
		switch (spec.getType()) {
		case IASTSimpleDeclSpecifier.t_char: if (spec.isSigned()) tc = new MyCharacterType(CharacterType.SIGNED_CHAR);
											 else if (spec.isUnsigned()) tc = new MyCharacterType(CharacterType.UNSIGNED_CHAR);
											 else tc = new MyCharacterType(CharacterType.CHAR); break;
		case IASTSimpleDeclSpecifier.t_double: if (spec.isLong()) tc = new MyFloatingType(FloatingType.LONG_DOUBLE);
											   else tc = new MyFloatingType(FloatingType.DOUBLE); break;
		case IASTSimpleDeclSpecifier.t_float: tc = new MyFloatingType(FloatingType.FLOAT); break;
		case IASTSimpleDeclSpecifier.t_void: tc = new MyVoidType(); break;
		case IASTSimpleDeclSpecifier.t_int: if (spec.isSigned()) {
											  if (spec.isShort()) tc = new MyIntegerType(IntegerType.SIGNED_SHORT);
											  else if (spec.isLong()) tc = new MyIntegerType(IntegerType.SIGNED_LONG);
											  else if (spec.isLongLong()) tc = new MyIntegerType(IntegerType.SIGNED_LONG_LONG);
											  else tc = new MyIntegerType(IntegerType.SIGNED_INT);
											}
											else if (spec.isUnsigned()) {
												if (spec.isShort()) tc = new MyIntegerType(IntegerType.UNSIGNED_SHORT);
												else if (spec.isLong()) tc = new MyIntegerType(IntegerType.UNSIGNED_LONG);
												else if (spec.isLongLong()) tc = new MyIntegerType(IntegerType.UNSIGNED_LONG_LONG);
												else tc = new MyIntegerType(IntegerType.UNSIGNED_INT);
											} else {
												if (spec.isShort()) tc = new MyIntegerType(IntegerType.SHORT);
												else if (spec.isLong()) tc = new MyIntegerType(IntegerType.LONG);
												else if (spec.isLongLong()) tc = new MyIntegerType(IntegerType.LONG_LONG);
												else tc = new MyIntegerType();
											}
		}
		if (spec.isLong()) tc = new MyIntegerType(IntegerType.LONG);
		if (spec.isShort()) tc = new MyIntegerType(IntegerType.SHORT);
		if (spec.isLongLong()) tc = new MyIntegerType(IntegerType.LONG_LONG);
		ProgramConstructor.setNodeValues(spec, tc);
		return tc;
	}
	
	/**
	 * Constructs a composite type specifier
	 * @param spec
	 * @return
	 */
	private static MyTypeSpecifier getCompositeTypeSpecifier(IASTCompositeTypeSpecifier spec) {
		if (spec.getKey() == IASTCompositeTypeSpecifier.k_struct) { // construct struct
			MyStruct struct = new MyStruct(spec.getName().toString());
			for (IASTDeclaration e : spec.getMembers()) {
				struct.addElement(construct(e));
			}
			struct.setName(spec.getName().toString());
			ProgramConstructor.setNodeValues(spec, struct);
			return struct;
		} else { // construct union
				MyUnion union = new MyUnion(spec.getName().toString());
				for (IASTDeclaration e : spec.getMembers()) {
				  union.addElement(construct(e));
				}
				union.setName(spec.getName().toString());
				ProgramConstructor.setNodeValues(spec, union);
				return union;			
		}
	}
	
	/**
	 * Contruct an enum
	 * @param spec
	 * @return
	 */
	private static MyEnumeration getEnumerationTypeSpecifier(IASTEnumerationSpecifier spec) {
		MyEnumeration enumer = new MyEnumeration(spec.getName().toString());
		for (IASTEnumerationSpecifier.IASTEnumerator e : spec.getEnumerators()) {
			enumer.addEnumerationElement(new MyEnumerationElement(e.getName().toString(), ExpressionConstructor.construct(e.getValue())));
		}
		ProgramConstructor.setNodeValues(spec, enumer);
		return enumer;
	}
	
	/**
	 * Sets node values for nodes which are not represented as a single node in the CDT AST
	 * @param node The CDT node which contains the element
	 * @param element The KC-AST node
	 * @param substr A string defining the keyword of the element
	 */
	private static void setNodeValues(IASTNode node, MyNode element, String substr) {
		element.setRawSignature(substr);
		element.setLength(substr.length());		
		// calculate position
		int position = node.getFileLocation().getNodeOffset();
		String complete = node.getRawSignature();
		int subpos = complete.indexOf(substr);
		element.setPosition(position+subpos);
	}
}
