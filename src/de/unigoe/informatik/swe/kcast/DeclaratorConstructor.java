package de.unigoe.informatik.swe.kcast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointer;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

import de.unigoe.informatik.swe.exception.ParseErrorExeption;
import de.unigoe.informatik.swe.kcast.declaration.MyArray;
import de.unigoe.informatik.swe.kcast.declaration.MyDeclaration;
import de.unigoe.informatik.swe.kcast.declaration.MyDeclarator;
import de.unigoe.informatik.swe.kcast.declaration.MyFunction;
import de.unigoe.informatik.swe.kcast.declaration.MyIdentifier;
import de.unigoe.informatik.swe.kcast.declaration.MyPointer;
import de.unigoe.informatik.swe.kcast.declaration.MyTypeQualifier;
import de.unigoe.informatik.swe.kcast.declaration.MyTypeQualifier.TypeQualifier;
import de.unigoe.informatik.swe.kcast.expression.MyExpression;
import de.unigoe.informatik.swe.kcast.initialisation.MyEqualsInitialisation;
import de.unigoe.informatik.swe.kcast.initialisation.MyInitClause;
import de.unigoe.informatik.swe.kcast.initialisation.MyInitialisation;
import de.unigoe.informatik.swe.kcast.initialisation.MyInitialisationList;
import de.unigoe.informatik.swe.kcast.statement.MyBlock;

/**
 * Transforming CDT declarator elements into KC-AST representations
 * @author Ella Albrecht
 *
 */
public class DeclaratorConstructor {

	/**
	 * Transforms a CDT declarator into a KC-AST representation of the declarator. 
	 * It is distinguished between arrays, functions, and variables. <br>
	 * The syntax of a declaration is: <br /> <i>specifiers-and-qualifiers</i> <i>declarators-and-initializers</i>;
	 * @param declAst The CDT representation of a declaration
	 * @return The KC-AST representation of a declaration or <code>null</code> if no corresponding representation exists
	 */
	public static MyDeclarator construct(IASTDeclarator declarator) {
		MyDeclarator decl = null;
		if (declarator instanceof IASTArrayDeclarator) decl = constructArray((IASTArrayDeclarator) declarator);
		else if (declarator instanceof IASTStandardFunctionDeclarator) decl = constructFunction((IASTStandardFunctionDeclarator) declarator);
		else decl = constructVariable(declarator);
		//set initializer
		MyInitialisation init= constructInitializer(declarator.getInitializer()); 
		ProgramConstructor.setNodeValues(declarator.getInitializer(), init);
		decl.setInit(init);
		ProgramConstructor.setNodeValues(declarator, decl);
		return decl;
	}
	
	/**
	 * Constructs an initialization
	 * @param init
	 * @return
	 */
	private static MyInitialisation constructInitializer(IASTInitializer init){
		if (init instanceof IASTEqualsInitializer) return constructEqualsInit((IASTEqualsInitializer) init);
		if (init instanceof IASTInitializerList) return constructInitList((IASTInitializerList) init);
		return null;
	}
	
	/**
	 * Constructs an initialization list
	 * @param init
	 * @return
	 */
	private static MyInitialisationList constructInitList(IASTInitializerList init) {
		List<MyInitClause> clauses = new ArrayList<MyInitClause>();
		for (IASTInitializerClause i : init.getClauses()) {
			clauses.add(ExpressionConstructor.constructInitializerClause(i));
		}
		return new MyInitialisationList(clauses);
	}

	
	private static MyEqualsInitialisation constructEqualsInit(IASTEqualsInitializer init) {
		return new MyEqualsInitialisation(ExpressionConstructor.constructInitializerClause(init.getInitializerClause()));
	}

	private static MyDeclarator constructVariable(IASTDeclarator declarator) {
	  MyDeclarator decl = null;
	  MyDeclarator id = new MyIdentifier(declarator.getName().toString(),true);
	  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length == 0) { // not a pointer
	    decl = id;
	  } else { // pointer
			  IASTPointerOperator[] pointers = declarator.getPointerOperators();
			  IASTPointerOperator pointer = pointers[pointers.length-1];	
			  MyPointer myPointer = constructPointer(pointer);
			  decl = myPointer;
			  for (int i=pointers.length-2; i>=0;i--) {
			    MyPointer pointer2 = constructPointer(pointers[i]);
			    myPointer.setDeclarator(pointer2);
			    myPointer = pointer2;
			  }
			  myPointer.setDeclarator(id);
	}
	return decl;	
	}

	public static MyDeclarator construct(IASTDeclarator declarator, MyDeclarator child) {
		if (declarator instanceof IASTArrayDeclarator) return constructArray((IASTArrayDeclarator) declarator, child);
		if (declarator instanceof IASTStandardFunctionDeclarator) return constructFunction((IASTStandardFunctionDeclarator) declarator,child);
		else return child;
	
	}

	private static MyDeclarator constructFunction(
			IASTStandardFunctionDeclarator declarator, MyDeclarator child) {
		IASTDeclarator nested = declarator.getNestedDeclarator();
		MyDeclarator decl = null;
		// get function name 
		if (nested == null) { // not nested
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length == 0) { // not a pointer
			  decl = child;
		  } else { // pointer
			  IASTPointer[] pointers = (IASTPointer[]) declarator.getPointerOperators();
			  IASTPointer pointer = pointers[pointers.length-1];	
			  MyPointer myPointer = constructPointer(pointer);
			  decl = myPointer;
			  for (int i=pointers.length-2; i>=0;i--) {
			    MyPointer pointer2 = constructPointer(pointers[i]);
			    myPointer.setDeclarator(pointer2);
			    myPointer = pointer2;
			  }
			  myPointer.setDeclarator(child);
			}
		} else { // nesting
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length == 0) { // not a pointer
		    decl = construct(nested,child);
		  } else { // pointer
			IASTPointerOperator[] pointers =  declarator.getPointerOperators();
			IASTPointerOperator pointer = pointers[pointers.length-1];	
			MyPointer myPointer = constructPointer(pointer);
			decl = myPointer;
			for (int i=pointers.length-2; i>=0;i--) {
			  MyPointer pointer2 = constructPointer(pointers[i]);
			  myPointer.setDeclarator(pointer2);
			  myPointer = pointer2;
			}
			myPointer.setDeclarator(construct(nested,child));
		  }
		}
		// get parameters
		List<MyDeclaration> params = new LinkedList<MyDeclaration>();
		for (IASTParameterDeclaration parameter : declarator.getParameters()) {
			params.add(DeclarationConstructor.constructParameter(parameter));
		}			
		MyFunction func = new MyFunction(decl, params, null);
		return func;		  
	}

	private static MyDeclarator constructArray(IASTArrayDeclarator declarator,
			MyDeclarator child) {
		IASTDeclarator nested = declarator.getNestedDeclarator();
		MyDeclarator decl = null;
		// get function name 
		if (nested == null) { // not nested
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length == 0) { // not a pointer
			  decl = child;
		  } else { // pointer
			  IASTPointerOperator[] pointers = declarator.getPointerOperators();
			  IASTPointerOperator pointer = pointers[pointers.length-1];	
			  MyPointer myPointer = constructPointer(pointer);
			  decl = myPointer;
			  for (int i=pointers.length-2; i>=0;i--) {
			    MyPointer pointer2 = constructPointer(pointers[i]);
			    myPointer.setDeclarator(pointer2);
			    myPointer = pointer2;
			  }
			  myPointer.setDeclarator(child);
			}
		} else { // nesting
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length == 0) { // not a pointer
		    decl = construct(nested,child);
		  } else { // pointer
			IASTPointerOperator[] pointers = declarator.getPointerOperators();
			IASTPointerOperator pointer = pointers[pointers.length-1];	
			MyPointer myPointer = constructPointer(pointer);
			decl = myPointer;
			for (int i=pointers.length-2; i>=0;i--) {
			  MyPointer pointer2 = constructPointer(pointers[i]);
			  myPointer.setDeclarator(pointer2);
			  myPointer = pointer2;
			}
			myPointer.setDeclarator(construct(nested,child));
		}
	  }
		// get size
		IASTArrayModifier[] sizes = declarator.getArrayModifiers();
		int length = sizes.length;
		MyExpression[] sizeExpr = new MyExpression[length];
		for (int i = 0; i<length; i++) {
			sizeExpr[i] = ExpressionConstructor.construct(sizes[i].getConstantExpression());
		}
		MyArray arr = new MyArray(decl,sizeExpr);
		return arr;	
	}
	
	

	public static MyDeclarator constructFunction(IASTFunctionDefinition def) {
		IASTStandardFunctionDeclarator declarator = (IASTStandardFunctionDeclarator) def.getDeclarator();
		IASTNode parent = declarator.getParent();
		MyDeclarator decl = null;
		// get function name 
 	   	MyDeclarator id = new MyIdentifier(declarator.getName().toString(),false);
		if (!(parent instanceof IASTDeclarator)) { // not nested
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length == 0) { // not a pointer
			  decl = id;
		  } else { // pointer
			  IASTPointerOperator[] pointers = declarator.getPointerOperators();
			  IASTPointerOperator pointer = pointers[pointers.length-1];	
			  MyPointer myPointer = constructPointer(pointer);
			  decl = myPointer;
			  for (int i=pointers.length-2; i>=0;i--) {
			    MyPointer pointer2 = constructPointer(pointers[i]);
			    myPointer.setDeclarator(pointer2);
			    myPointer = pointer2;
			  }
			  myPointer.setDeclarator(id);
			}
		} else { // nesting
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length < 1) { // not a pointer
		    decl = construct((IASTDeclarator) parent, id);
		  } else { // pointer
			IASTPointerOperator[] pointers = declarator.getPointerOperators();
			IASTPointerOperator pointer = pointers[pointers.length-1];	
			MyPointer myPointer = constructPointer(pointer);
			decl = myPointer;
			for (int i=pointers.length-2; i>=0;i--) {
			  MyPointer pointer2 = constructPointer(pointers[i]);
			  myPointer.setDeclarator(pointer2);
			  myPointer = pointer2;
			}
			myPointer.setDeclarator(construct((IASTDeclarator) parent, id));
		}

	}
		// get parameters
		List<MyDeclaration> params = new LinkedList<MyDeclaration>();
		for (IASTParameterDeclaration parameter : declarator.getParameters()) {
		  params.add(DeclarationConstructor.constructParameter(parameter));
		}
		// get body
		IASTStatement body = def.getBody();
		MyBlock myBody;
		try {
			myBody = (MyBlock) StatementConstructor.construct(body);
		} catch (ParseErrorExeption e) {
			myBody = new MyBlock();
			System.err.println("Parse error");
		}
		MyFunction func = new MyFunction(decl, params, myBody);
		return func;
	}
	
	private static MyPointer constructPointer(IASTPointerOperator pointerO) {
		MyPointer newPointer = new MyPointer();
		IASTPointer pointer = (IASTPointer) pointerO;
		if (pointer.isConst()) newPointer.setQualifier(new MyTypeQualifier(TypeQualifier.CONST));
		if (pointer.isRestrict()) newPointer.setQualifier(new MyTypeQualifier(TypeQualifier.RESTRICT));
		if (pointer.isVolatile()) newPointer.setQualifier(new MyTypeQualifier(TypeQualifier.VOLATILE));
		return newPointer;
	}
	
	private static MyFunction constructFunction(IASTStandardFunctionDeclarator declarator) {
		IASTDeclarator nested = declarator.getNestedDeclarator();
		MyDeclarator decl = null;
		// get function name 
 	   	MyDeclarator id = new MyIdentifier(declarator.getName().toString(),false);
		if (nested == null) { // not nested
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length ==0) { // not a pointer
			  decl = id;
		  } else { // pointer
			  IASTPointerOperator[] pointers =  declarator.getPointerOperators();
			  IASTPointerOperator pointer = pointers[pointers.length-1];	
			  MyPointer myPointer = constructPointer(pointer);
			  decl = myPointer;
			  for (int i=pointers.length-2; i>=0;i--) {
			    MyPointer pointer2 = constructPointer(pointers[i]);
			    myPointer.setDeclarator(pointer2);
			    myPointer = pointer2;
			  }
			  myPointer.setDeclarator(id);
			}
		} else { // nesting
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length == 0) { // not a pointer
		    decl = construct(nested);
		  } else { // pointer
			IASTPointerOperator[] pointers = declarator.getPointerOperators();
			IASTPointerOperator pointer = pointers[pointers.length-1];	
			MyPointer myPointer = constructPointer(pointer);
			decl = myPointer;
			for (int i=pointers.length-2; i>=0;i--) {
			  MyPointer pointer2 = constructPointer(pointers[i]);
			  myPointer.setDeclarator(pointer2);
			  myPointer = pointer2;
			}
			myPointer.setDeclarator(construct(nested));
		  }
		}
		// get parameters
		List<MyDeclaration> params = new LinkedList<MyDeclaration>();
		for (IASTParameterDeclaration parameter : declarator.getParameters()) {
			params.add(DeclarationConstructor.constructParameter(parameter));
		}			
		MyFunction func = new MyFunction(decl, params, null);
		return func;		  
	}

	private static MyArray constructArray(IASTArrayDeclarator declarator) {
		IASTDeclarator nested = declarator.getNestedDeclarator();
		MyDeclarator decl = null;
		// get function name 
 	   	MyDeclarator id = new MyIdentifier(declarator.getName().toString(),true);
		if (nested == null) { // not nested
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length < 1) { // not a pointer
			  decl = id;
		  } else { // pointer
				IASTPointerOperator[] pointers = (IASTPointerOperator[]) declarator.getPointerOperators();
				IASTPointerOperator pointer = pointers[pointers.length-1];		
			  MyPointer myPointer = constructPointer(pointer);
			  decl = myPointer;
			  for (int i=pointers.length-2; i>=0;i--) {
			    MyPointer pointer2 = constructPointer(pointers[i]);
			    myPointer.setDeclarator(pointer2);
			    myPointer = pointer2;
			  }
			  myPointer.setDeclarator(id);
		
			}
		} else { // nesting
		  if (declarator.getPointerOperators() == null || declarator.getPointerOperators().length == 0) { // not a pointer
		    decl = construct(nested);
		  } else { // pointer
			IASTPointerOperator[] pointers = (IASTPointerOperator[]) declarator.getPointerOperators();
			IASTPointerOperator pointer = pointers[pointers.length-1];	
			MyPointer myPointer = constructPointer(pointer);
			decl = myPointer;
			for (int i=pointers.length-2; i>=0;i--) {
			  MyPointer pointer2 = constructPointer(pointers[i]);
			  myPointer.setDeclarator(pointer2);
			  myPointer = pointer2;
			}
			myPointer.setDeclarator(construct(nested));
		}
	  }
		// get size
		IASTArrayModifier[] sizes = declarator.getArrayModifiers();
		int length = sizes.length;
		MyExpression[] sizeExpr = new MyExpression[length];
		for (int i = 0; i<length; i++) {
			sizeExpr[i] = ExpressionConstructor.construct(sizes[i].getConstantExpression());
		}
		MyArray arr = new MyArray(decl,sizeExpr);
		return arr;	
	}
}
