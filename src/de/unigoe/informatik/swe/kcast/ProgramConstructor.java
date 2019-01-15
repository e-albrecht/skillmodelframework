package de.unigoe.informatik.swe.kcast;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ILanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.core.runtime.CoreException;

import de.unigoe.informatik.swe.kcast.declaration.MyDeclaration;

/**
 * A class used to extract the KC-AST from given source code
 * 
 * @author Ella Albrecht
 *
 */
public class ProgramConstructor {

	/**
	 * Builds a program structure for a given source code
	 * 
	 * @param code
	 *            Source code for which the KC-AST has to be
	 *            constructed
	 * @return Returns a program representation of a given source code similar to an AST with additional information on KCs
	 * @throws CoreException The exception is thrown when the program representation could not be constructed
	 */
	public static MyKCAST constructFromString(char[] code) throws CoreException  {
		IASTTranslationUnit tuAst = getTranslationUnitASTFromString(code);

		// create translation unit
		MyKCAST tu = new MyKCAST();

		// add macro definitions and includes
		IASTPreprocessorStatement[] preStatements = tuAst.getAllPreprocessorStatements();
		for (IASTPreprocessorStatement stmt : preStatements) {

			// macro definition
			if (stmt instanceof IASTPreprocessorMacroDefinition) {
				MyMacroDefinition macro = new MyMacroDefinition(((IASTPreprocessorMacroDefinition) stmt).getName().toString(),
						((IASTPreprocessorMacroDefinition) stmt).getExpansion());
				setNodeValues(stmt,macro);
				tu.addDefineMacro(macro);
			}
			// include
			if (stmt instanceof IASTPreprocessorIncludeStatement) {
				MyInclude include = new MyInclude(((IASTPreprocessorIncludeStatement) stmt).getName().toString());
				setNodeValues(stmt,include);
				tu.addIncludeMacro(include);
			}
		}

		// add declarations
		for (IASTDeclaration decl : tuAst.getDeclarations()) {
			MyDeclaration declaration = DeclarationConstructor.construct(decl);
			tu.addDeclaration(declaration);
		}
		return tu;
	}

	/**
	 * Returns an AST representation by CDT of the given source code
	 * @param code Source code for which the AST shall be constructed
	 * @return CDT AST representation of the given source code as a translation unit
	 * @throws CoreException The exception is thrown if the translation unit could not be obtained
	 */
	private static IASTTranslationUnit getTranslationUnitASTFromString(char[] code) throws CoreException{
		FileContent fc = FileContent.create("", code);
		Map<String, String> macroDefinitions = new HashMap<String, String>();
		String[] includeSearchPaths = new String[0];
		IScannerInfo si = new ScannerInfo(macroDefinitions, includeSearchPaths);
		IncludeFileContentProvider ifcp = IncludeFileContentProvider.getEmptyFilesProvider();
		IIndex idx = null;
		int options = ILanguage.OPTION_IS_SOURCE_UNIT;
		IParserLogService log = new DefaultLogService();
		return GCCLanguage.getDefault().getASTTranslationUnit(fc, si, ifcp, idx, options, log);
	}
	
	/**
	 * Sets values which are required for every node, i.e., raw signature, position, length
	 * @param node
	 * @param element
	 */
	public static void setNodeValues(IASTNode node, MyNode element) {
		element.setRawSignature(node.getRawSignature());
		element.setPosition(node.getFileLocation().getNodeOffset());
		element.setLength(node.getFileLocation().getNodeLength());
	}
}
